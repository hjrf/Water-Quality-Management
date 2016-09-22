package com.android.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.android.config.HttpConf;
import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReceiveKnowledge extends Activity implements OnClickListener{
	private final static String TAG="Receiver";
	protected Sqlite sqlite = new Sqlite(this,"Water");
	protected Handler handler;
	protected String result = "";
	protected Button button1 = null;
	protected Button button2 = null;
	protected Button button3 = null;
	protected Button button4 = null;
	EditText editText1 = null;
	protected String parameter = "waterdata.jsp?knowledge=1";
	HttpConf hc = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.receive_knowledge);
	     hc = new HttpConf(this);
	     FindAllView();
	 }
	protected void FindAllView() {
//			listView1 = (ListView)findViewById(R.id.listView1);				
//			editText1 = (EditText) this.findViewById(R.id.editText1);			
//			button1 = (Button) this.findViewById(R.id.button1);			
//			textView1 = (TextView) this.findViewById(R.id.textView1);
//			radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
//		 	checkBox1 = (CheckBox) findViewById(R.id.checkBox1);  
		
//			checkBox1.setChecked(true);  
//			button1.setEnabled(false);		
		
//			ListView1.setOnItemClickListener(this);	
//			button1.setOnClickListener(this);	
//			checkBoxs.add(checkBox1);  
		button1 = (Button) this.findViewById(R.id.button1);	
		button1.setOnClickListener(this);
		button2 = (Button) this.findViewById(R.id.button2);	
		button2.setOnClickListener(this);
		editText1 = (EditText) this.findViewById(R.id.editText1);
	}
	
	public void onClick(View v) {
		String[] selectItem = null;
		switch(v.getId()){
		case R.id.button1:
			selectItem = new String[]{"PH值", "溶解氧", "高猛酸盐指数" , "氨氮"};
		DialogSelect(button1,selectItem);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button2:
			if("".equals(editText1))
			{
				Toast.makeText(this, "请先填好想要获取的数据条目数！", Toast.LENGTH_SHORT).show();
				break;
			}
			parameter = "waterdata.jsp?parName='"+ button1.getText().toString().trim().substring(7,button1.getText().toString().trim().length()) +"'&recordNum='"+ editText1.getText().toString().trim() +"'";
			receive();
		//Log.i(FLAG,"打印的信息！"); 
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
		
	public void send(String parameter) {
	
		String target = hc.url+parameter;
		URL url;
		try {
			url = new URL(target);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
			BufferedReader buffer = new BufferedReader(in);
			String inputLine = null;
			while((inputLine = buffer.readLine()) != null) {
				result += inputLine + "\n";
			}
			in.close();
			urlConn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	void DialogSelect(final Button button,final String [] selectItem)
	{
		new  AlertDialog.Builder(this)  
		.setTitle("请选择" )  
		.setIcon(android.R.drawable.ic_dialog_info)                  
		.setSingleChoiceItems(selectItem, 0 ,   
		  new  DialogInterface.OnClickListener() {  	                              
		     public void onClick(DialogInterface dialog,  int  which) {  
		    	button.setText("当前选择的是："+selectItem[which]);
		        dialog.dismiss();  
//		    	Intent intentTable = new Intent(ReceiveWaterData.this,EvaluateAdd.class);
//		    	startActivity(intentTable);
		     }  
		  }  
		)  
		.setNegativeButton("取消" ,  null )  
		.show();  
	}
	
	
	protected void receive()
	{
		new Thread(new Runnable() {
			public void run() {
				send(parameter);
				Message m = handler.obtainMessage();
				handler.sendMessage(m);
			}
		}).start();	
			     
	 	handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(result != null) {
					Log.i(TAG, result);
					Toast.makeText(ReceiveKnowledge.this,"接收的信息为："+result, Toast.LENGTH_SHORT).show();
					
				    String[] strarray=result.split("_"); 
				  
					
					String sql = "create table if not exists waterdata (_id integer primary key autoincrement not null,水域名称 varchar(20),河流名称 varchar(20),区段名称 varchar(20),PH值 varchar(20),溶解氧 varchar(20),高锰酸盐指数 varchar(20),氨氮 varchar(20))";
					sqlite.getReadableDatabase().execSQL(sql);
					
					String sql1 = "delete from standard";
					sqlite.getReadableDatabase().execSQL(sql1);
					String sql2 = "update sqlite_sequence SET seq = 0 where name ='standard'";
					sqlite.getReadableDatabase().execSQL(sql2);
					
					for (int i = 0; i < strarray.length/4; i++) {
						sql = "insert into standard(水域名称,河流名称 ,区段名称 ,PH值 ,溶解氧 ,高锰酸盐指数 ,氨氮 ) values('"+ button1.getText().toString() +"','"+ button2.getText().toString() +"','"+ button3.getText().toString() +"','"+ strarray[i*4] +"','"+ strarray[i*4+1] +"','"+ strarray[i*4+2] +"','"+ strarray[i*4+3] +"')";
						sqlite.getReadableDatabase().execSQL(sql);		
					}					
					result = "";
				}
				super.handleMessage(msg);
			}
		};
	}
		

	
	public String base64(String content) {
		try {
			content = Base64.encodeToString(content.getBytes("utf-8"),Base64.DEFAULT);
			content = URLEncoder.encode(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	
}
