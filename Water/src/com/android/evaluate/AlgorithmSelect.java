package com.android.evaluate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.android.config.HttpConf;
import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AlgorithmSelect extends Activity implements OnClickListener{
	private final static String TAG="AlgorithmSelect";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	Button button1 = null;
	Button button2 = null;
	EditText editText1 = null;
	EditText editText2 = null;
	EditText editText3 = null;
	EditText editText4 = null;
	EditText editText5 = null;
	
	TextView textView7 = null;
	TextView textView9 = null;
	TextView textView11 = null;
	TextView textView13 = null;
	TextView textView15 = null;
	TextView textView17 = null;
	TextView textView6 = null;
	TextView textView8 = null;
	TextView textView10 = null;
	TextView textView12 = null;
	TextView textView14 = null;
	TextView textView16 = null;
	TextView textView18 = null;
	protected Handler handler;
	protected String result = "";
	String algoName = null;
	String[] itemSelect = null;
	SimpleAdapter simpleAdapter = null;
	String [] strarray = null;
	protected String parameter = null;
	HttpConf hc = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.algorithm_select);
	     hc = new HttpConf(this);
	     FindAllView();
	 }
		private void FindAllView() {
//			ListView xxx = (ListView)findViewById(R.id.xxx);				
//			EditText xxx = (EditText) this.findViewById(R.id.xxx);			
//			Button xxx = (Button) this.findViewById(R.id.xxx);			
//			TextView xxx = (TextView) this.findViewById(R.id.xxx);
			
//			checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
//			button.setEnabled(false);		
//			ListView.setOnItemClickListener(this);	
//			button.setOnClickListener(this);	
			button1 = (Button) this.findViewById(R.id.button1);	
			button1.setOnClickListener(this);	
			button2 = (Button) this.findViewById(R.id.button2);	
			button2.setOnClickListener(this);	
			editText1 = (EditText) this.findViewById(R.id.editText1);	
			editText2 = (EditText) this.findViewById(R.id.editText2);	
			editText3 = (EditText) this.findViewById(R.id.editText3);	
			editText4 = (EditText) this.findViewById(R.id.editText4);	
			editText5 = (EditText) this.findViewById(R.id.editText5);	

			textView7 = (TextView) this.findViewById(R.id.textView7);	
			textView9 = (TextView) this.findViewById(R.id.textView9);	
			textView11 = (TextView) this.findViewById(R.id.textView11);	
			textView13 = (TextView) this.findViewById(R.id.textView13);	
			textView15 = (TextView) this.findViewById(R.id.textView15);	
			textView17 = (TextView) this.findViewById(R.id.textView17);	
			textView6 = (TextView) this.findViewById(R.id.textView6);	
			textView8 = (TextView) this.findViewById(R.id.textView8);	
			textView10 = (TextView) this.findViewById(R.id.textView10);	
			textView12 = (TextView) this.findViewById(R.id.textView12);	
			textView14 = (TextView) this.findViewById(R.id.textView14);	
			textView16 = (TextView) this.findViewById(R.id.textView16);	
			textView18 = (TextView) this.findViewById(R.id.textView18);	
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
			    	if(which == 0)
			    	{
			    		algoName="Bayes算法";
			    	}
			    	else if(which == 1)
			    	{
			    		algoName="Knn算法";
			    	}
			        dialog.dismiss();  
//			    	Intent intentTable = new Intent(ReceiveWaterData.this,EvaluateAdd.class);
//			    	startActivity(intentTable);
			     }  
			  }  
			)  
			.setNegativeButton("取消" ,  null )  
			.show();  
		}
		protected void receive(final String parameter)
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
						Toast.makeText(AlgorithmSelect.this,"接收的信息为："+result, Toast.LENGTH_SHORT).show();
					}
				    strarray=result.toString().trim().split("_"); 
				    if(strarray[0].equals("a"))
				    {
					    textView7.setText("一级前验概率");
					    textView9.setText("二级前验概率");
					    textView11.setText("三级前验概率");
					    textView13.setText("一级后验概率");
					    textView15.setText("二级后验概率");
					    textView17.setText("三级后验概率");
					    textView6.setText(strarray[1]);//预测结果
					    textView8.setText(strarray[2]);
					    textView10.setText(strarray[3]);
					    textView12.setText(strarray[4]);
					    textView14.setText(strarray[5]);
					    textView16.setText(strarray[6]);
					    textView18.setText(strarray[7]);
				    }
				    else if(strarray[0].equals("b"))
				    {
					    textView7.setText("欧式距离1");
					    textView9.setText("欧氏距离2");
					    textView11.setText("欧氏距离3");
					    textView6.setText(strarray[1]);//预测结果
					    textView8.setText(strarray[2]);
					    textView10.setText(strarray[3]);
					    textView12.setText(strarray[4]);
				    }
				    
					super.handleMessage(msg);
				}
			};
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
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button1:
				if("".equals(editText1.getText().toString().trim()) || "".equals(editText2.getText().toString().trim()) || "".equals(editText3.getText().toString().trim()) || "".equals(editText4.getText().toString().trim()) || "".equals(editText5.getText().toString().trim()))
				{
					Toast.makeText(this, "请参数信息填写完整！", Toast.LENGTH_SHORT).show();
					return;
				}
				itemSelect = new String [] {"Bayes算法","Knn算法"};
				DialogSelect(button1,itemSelect);
				//Log.i(FLAG,"打印的信息！"); 
				break;
			case R.id.button2:
				if("算法选择".equals(button1.getText().toString().trim()))
				{
					Toast.makeText(this, "请先选择好算法！", Toast.LENGTH_SHORT).show();
					return;
				}
				if("当前选择的是：Bayes算法".equals(button1.getText().toString().trim()))
				{
					parameter = "bayes.jsp?train_num="+editText5.getText().toString().trim()+"&parValue1="+editText1.getText().toString().trim()+"&parValue2="+editText2.getText().toString().trim()+"&parValue3="+editText3.getText().toString().trim()+"&parValue4="+editText4.getText().toString().trim()+"";
				}
				else if("当前选择的是：Knn算法".equals(button1.getText().toString().trim()))
				{
					parameter = "knn.jsp?train_num="+editText5.getText().toString().trim()+"&parValue1="+editText1.getText().toString().trim()+"&parValue2="+editText2.getText().toString().trim()+"&parValue3="+editText3.getText().toString().trim()+"&parValue4="+editText4.getText().toString().trim()+"";
				}
				receive(parameter);
				//Log.i(FLAG,"打印的信息！"); 
				break;
			default:
			//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
				break;
			}
		}
}
