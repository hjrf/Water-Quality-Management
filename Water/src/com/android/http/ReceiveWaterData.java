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

public class ReceiveWaterData extends Activity implements OnClickListener{
	private final static String TAG="Receiver";
	protected Sqlite sqlite = new Sqlite(this,"Water");
	protected Handler handler;
	protected String result = "";
	protected Button button1 = null;
	protected Button button2 = null;
	protected Button button3 = null;
	protected Button button4 = null;
	HttpConf hc = null;
	EditText editText1 = null;
	protected String parameter = "waterdata.jsp?flag=1";
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.receive_waterdata);
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
		button3 = (Button) this.findViewById(R.id.button3);	
		button3.setOnClickListener(this);
		button4 = (Button) this.findViewById(R.id.button4);	
		button4.setOnClickListener(this);
		editText1 = (EditText) this.findViewById(R.id.editText1);
	}
	
	public void onClick(View v) {
		String[] selectItem = null;
		switch(v.getId()){
		case R.id.button1:
			selectItem = new String[]{"水域1", "水域2", "水域3" , "水域4"};
		DialogSelect(button1,selectItem);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button2:
			selectItem = new String[]{"河流1", "河流2", "河流3"};
			DialogSelect(button2,selectItem);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button3:
			selectItem = new String[]{"区段1", "区段2", "区段3"};
			DialogSelect(button3,selectItem);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button4:
			if(button1.getText().toString().equals("点击选择水域") || button2.getText().toString().equals("点击选择河流") || button3.getText().toString().equals("点击选择区段"))
			{
				Toast.makeText(this, "请先选择好要查询的参数位置！", Toast.LENGTH_SHORT).show();
			}
			else if("".equals(editText1.getText().toString()))
			{
				Toast.makeText(this, "请先填好想要获取的数据条目数！", Toast.LENGTH_SHORT).show();
			}
			else
			{
				parameter = "waterdata.jsp?flag="+editText1.getText().toString().trim()+"";
				receive();
			}
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
					Toast.makeText(ReceiveWaterData.this,"接收的信息为："+result, Toast.LENGTH_SHORT).show();
					
				    String[] strarray=result.split("_"); 
				  
					
					String sql = "create table if not exists waterdata (_id integer primary key autoincrement not null,水域名称 varchar(20),河流名称 varchar(20),区段名称 varchar(20),PH值 varchar(20),溶解氧 varchar(20),高锰酸盐指数 varchar(20),氨氮 varchar(20))";
					sqlite.getReadableDatabase().execSQL(sql);
					
					String sql1 = "delete from waterdata";
					sqlite.getReadableDatabase().execSQL(sql1);
					String sql2 = "update sqlite_sequence SET seq = 0 where name ='waterdata'";
					sqlite.getReadableDatabase().execSQL(sql2);
					
					for (int i = 0; i < strarray.length/4; i++) {
						sql = "insert into waterdata(水域名称,河流名称 ,区段名称 ,PH值 ,溶解氧 ,高锰酸盐指数 ,氨氮 ) values('"+ button1.getText().toString().substring(7,10) +"','"+ button2.getText().toString().substring(7,10) +"','"+ button3.getText().toString().substring(7,10) +"','"+ strarray[i*4].toString().trim() +"','"+ strarray[i*4+1].toString().trim() +"','"+ strarray[i*4+2].toString().trim() +"','"+ strarray[i*4+3].toString().trim() +"')";
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
