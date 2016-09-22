package com.android.templet;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;

public class Event extends Activity implements OnClickListener,AdapterView.OnItemClickListener{
	private final static String TAG="Normal";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句");//查询语句就是SELECT
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     //setContentView(R.layout.xxx);
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
		}
		
		public void onClick(View v) {
//			switch(v.getId()){
//			case R.id.button1:
//			//Log.i(FLAG,"打印的信息！"); 
//				break;
//			default:
//			//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
//				break;
//			}
		}
		
		@SuppressLint("NewApi")
//		void showNotification(){   
//	        //初始化NotificationManager对象   
//	        m_NotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);   
//	           
//	        //点击通知时转移内容   
//	    //  m_Intent = new Intent(LoginActivity.this, MainActivity.class);   
//	           
//	        //主要是设置点击通知时显示内容的类   
//	        m_PendingIntent = PendingIntent.getActivity(MainLayout.this, 0, getIntent(), 0); //如果轉移內容則用m_Intent();   
//	           
//	        //构造Notification对象   
//	        m_Notification = new Notification.Builder(getBaseContext())    
//	                .setAutoCancel(true)    
//	                .setContentTitle("警报")    
//	                .setContentText("点击查看详细信息")    
//	                .setContentIntent(m_PendingIntent)    
//	                .setSmallIcon(R.drawable.ic_launcher)    
//	                .setWhen(System.currentTimeMillis())    
//	                .build();      
//	 
//	        //可以理解为执行这个通知   
//	        m_NotificationManager.notify(0, m_Notification);   
//	    }   	
		
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		}
}