package com.android.user;

import java.util.Timer;
import java.util.TimerTask;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegSuccess extends Activity implements OnClickListener{
	private final static String FLAG="RegSuccess";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句");//查询语句就是SELECT
	
	TextView textView1 = null;
	Button button1 = null;
	Button button2 = null;
	int timeS = 5;
	Timer timer = null;
				
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.reg_success);
	     FindAllView();
	     delay();
	 }	
	private void FindAllView() {
//			ListView xxx = (ListView)findViewById(R.id.xxx);				
//			EditText xxx = (EditText) this.findViewById(R.id.xxx);			
//			Button xxx = (Button) this.findViewById(R.id.xxx);			
//			TextView xxx = (TextView) this.findViewById(R.id.xxx);

//			button.setEnabled(false);		
//			ListView.setOnItemClickListener(this);	
//			button.setOnClickListener(this);	
		textView1= (TextView) this.findViewById(R.id.textView1);
		button1 = (Button) this.findViewById(R.id.button1);		
		button2 = (Button) this.findViewById(R.id.button2);	
		button1.setOnClickListener(this);	
		button2.setOnClickListener(this);	
	}
	Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                update();
                break;
            }
            super.handleMessage(msg);
        }
        void update() {
        	if(timeS == 1)
        	{
        		timer.cancel();
        		MoveLogin();
        	}
	        timeS--;
            textView1.setText(timeS+"秒钟自动进入登录界面！");
        }
    };
    
    TimerTask task = new TimerTask(){   
	    public void run(){   
	    	//operate
	    	Message message = new Message();
	        message.what = 1;
	        handler.sendMessage(message);
	    }   
	};   
	void delay()
	{			
		timer = new Timer();
		timer.schedule(task,1000,1000); 
	}
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			MoveLogin();
		//Log.i(className,"打印的信息！"); 
			break;
		case R.id.button2:
			QuitSys();
		//Log.i(className,"打印的信息！"); 
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	void MoveLogin()
	{
 		timer.cancel();
		Intent intentTable = new Intent(RegSuccess.this,Login.class);
    	startActivity(intentTable);
	}
	void QuitSys()
	{
		 android.os.Process.killProcess(android.os.Process.myPid());    //获取PID 
		 System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
	}
}