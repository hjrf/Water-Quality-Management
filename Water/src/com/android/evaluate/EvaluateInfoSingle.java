package com.android.evaluate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.android.config.HttpConf;
import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluateInfoSingle extends Activity implements OnClickListener{
	private final static String TAG="EvalueteInfoSingle";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	String sql = null;
	protected Handler handler;
	protected String result = "";
	Button button1 = null;
	HttpConf hc = null;
	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句",null);//查询语句就是SELECT
//	TextView textView1 = null;
//	Button button1 = null;
//	EditText editText1 = null;
//	ListView listView1 = null;
//	RadioGroup radioGroup1 = null;
//	private List<CheckBox> checkBoxs = new ArrayList<CheckBox>();  
	TextView textView1 = null;
	TextView textView2 = null;
	TextView textView3 = null;
	ListView listView1 = null;
	String paraName = null;
	double paraValue = 0;
	SimpleAdapter simpleAdapter = null;
	String [] strarray = null;
	protected String parameter = "waterdata.jsp?knowledge=1";
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.evaluate_info_single);
	     hc = new HttpConf(this);
	     Intent intent = getIntent();
	     paraName = intent.getStringExtra("paraName");
	     paraValue = Double.valueOf(intent.getStringExtra("paraValue"));
	     sql = "select 一级标准,二级标准,三级标准 from standard where 参数名称 ='"+ paraName +"'";
	     double[] standard = new double[3];
	     Cursor cursor_standard = sqlite.getReadableDatabase().rawQuery(sql,null);
	     while(cursor_standard.moveToNext())
	     {
	    	 standard[0] = Double.valueOf(cursor_standard.getString(0));
	    	 standard[1] = Double.valueOf(cursor_standard.getString(1));
	    	 standard[2] = Double.valueOf(cursor_standard.getString(2));
	     }
	     FindAllView();
	     textView1.setText(paraName.toString().trim());
	     textView2.setText(String.valueOf(paraValue).toString().trim());
	     if(paraValue>=standard[0] && paraValue<standard[1])
	     {
	    	 textView3.setText("一级标准");
	     }
	     else if(paraValue>=standard[1] && paraValue<standard[2])
	     {
	    	 textView3.setText("二级标准");
	     }
	     else if(paraValue>=standard[2])
	     {
	    	 textView3.setText("三级标准");
	     }
	 }
	private void FindAllView() {
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
		textView1 = (TextView) this.findViewById(R.id.textView1);
		textView2 = (TextView) this.findViewById(R.id.textView2);
		textView3 = (TextView) this.findViewById(R.id.textView3);
		listView1 = (ListView) this.findViewById(R.id.listView1);
		button1 = (Button) this.findViewById(R.id.button1);	
		button1.setOnClickListener(this);	
	}
	public void onClick(View v) {
		String[] selectItem = null;
		switch(v.getId()){
		case R.id.button1:	
		String str = textView1.getText().toString().trim();
		if(str.equals("PH值"))
		{
			str = "1";
		}
		else if(str.equals("溶解氧")){
			str = "2";
		}
		else if(str.equals("高锰酸盐指数")){
			str = "3";
		}
		else if(str.equals("氨氮")){
			str = "4";
		}	
		parameter = "knowledge.jsp?parName="+str+"";
		receive();
		DataBind(); 
		//Log.i(FLAG,"打印的信息！"); 
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
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
						Toast.makeText(EvaluateInfoSingle.this,"接收的信息为："+result, Toast.LENGTH_SHORT).show();
					}
				    strarray=result.split("_"); 
					super.handleMessage(msg);
				}
			};
		}
		 void DataBind()
		 {
		     //创建简单适配器SimpleAdapter
		        simpleAdapter = new SimpleAdapter(this, this.getItem(), R.layout.warninfo_child, 
		                new String[] {"itemTitle", "itemPublishtime"},
		                new int[] {R.id.title, R.id.publishtime});
		        
		      
		        //加载SimpleAdapter到ListView中
		        listView1.setAdapter(simpleAdapter);
		 }

		 public ArrayList<HashMap<String, Object>> getItem() {
		        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
			    HashMap<String, Object> map = new HashMap<String, Object>();  



			    if(strarray == null)
			    {
			    	 map.put("itemTitle","数据接收中");
			    	 map.put("itemPublishtime","请当出现提示后点击重试");
			    	 item.add(map);
			    	 map = new HashMap<String, Object>();
			    }
			    else
			    {
					for(int i = 0;i<strarray.length-2;i++)
					{
						 if(i%2 == 0)
						 {
					    	 map.put("itemTitle",strarray[i]);
				    	   	 map.put("itemPublishtime",strarray[i+1]);
				 	         item.add(map);
				 	         map = new HashMap<String, Object>();
						 }
					}
			    }
		        return item;
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
}