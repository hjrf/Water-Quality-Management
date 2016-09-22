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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ReceiveStandard extends Activity implements OnClickListener{
	private final static String TAG="Receiver";
	protected Sqlite sqlite = new Sqlite(this,"Water");
	protected Handler handler;
	protected String result = "";
	protected Button button1 = null;
	protected String parameter = "standard.jsp?flag=1";
	HttpConf hc = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.receive_standard);
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
	}
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
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
					Toast.makeText(ReceiveStandard.this,"接收的信息为："+result, Toast.LENGTH_SHORT).show();
					
				    String[] strarray=result.split("_"); 
				  
					
					String sql = "create table if not exists standard (_id integer primary key autoincrement not null,参数名称 varchar(20),一级标准 varchar(20),二级标准 varchar(20),三级标准 varchar(20))";
					sqlite.getReadableDatabase().execSQL(sql);
					
					String sql1 = "delete from standard";
					sqlite.getReadableDatabase().execSQL(sql1);
					String sql2 = "update sqlite_sequence SET seq = 0 where name ='standard'";
					sqlite.getReadableDatabase().execSQL(sql2);
					
					for (int i = 0; i < strarray.length/4; i++) {
						sql = "insert into standard(参数名称,一级标准,二级标准,三级标准) values('"+ strarray[i*4].toString().trim() +"','"+ strarray[i*4+1].toString().trim()  +"','"+ strarray[i*4+2].toString().trim()  +"','"+ strarray[i*4+3].toString().trim() +"')";
						sqlite.getReadableDatabase().execSQL(sql);		
					}
					result = "";
				}
				super.handleMessage(msg);
			}
		};
	}
		

	

	
	
}
