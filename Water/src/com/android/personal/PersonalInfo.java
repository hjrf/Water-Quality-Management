package com.android.personal;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalInfo extends Activity implements OnClickListener{
	private final static String TAG="PersonalInfo";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
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
	TextView textView4 = null;
	TextView textView5 = null;
	String userName = null;
	Button button1 = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.personal_info);
	     FindAllView();
	     TextViewDataBind();
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
		textView4 = (TextView) this.findViewById(R.id.textView4);
		textView5 = (TextView) this.findViewById(R.id.textView5);
		textView1.setClickable(true);
		textView1.setOnClickListener(this);	
		textView2.setClickable(true);
		textView2.setOnClickListener(this);	
		textView3.setClickable(true);
		textView3.setOnClickListener(this);	
		textView4.setClickable(true);
		textView4.setOnClickListener(this);	
		textView5.setClickable(true);
		textView5.setOnClickListener(this);	
		button1 = (Button) this.findViewById(R.id.button1);		
		button1.setOnClickListener(this);	
	}
	void dialog(final String filedName)
	{
		final EditText et =  new EditText(this);
		new  AlertDialog.Builder(this)  
		.setTitle("请输入" )  
		.setIcon(android.R.drawable.ic_dialog_info)  
		.setView(et)  
		.setPositiveButton("确定" , new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String sql = "update user set "+ filedName +" ='"+ et.getText() +"' where 用户姓名 = '"+ userName +"'";
	    		sqlite.getReadableDatabase().execSQL(sql);
			}
		})	
		.setNegativeButton("取消" ,  null )  
		.show();  
	}
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
		//Log.i(FLAG,"打印的信息！"); 
		finish();
			break;
		case R.id.textView1:
			//Log.i(FLAG,"打印的信息！"); 
			dialog("用户姓名");
				break;
		case R.id.textView2:
			//Log.i(FLAG,"打印的信息！"); 
			dialog("用户密码");
				break;
		case R.id.textView3:
			//Log.i(FLAG,"打印的信息！"); 
			dialog("用户级别");
				break;
		case R.id.textView4:
			//Log.i(FLAG,"打印的信息！"); 
			dialog("电子邮箱");
				break;
		case R.id.textView5:
			//Log.i(FLAG,"打印的信息！"); 
			dialog("电话号码");
				break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	void TextViewDataBind()
	{
		
		String userPwd = null;
		String userLevel = null;
		String email = null;
		String phoneNumber = null;
		SharedPreferences preferences;
		preferences =this.getSharedPreferences("userLoginInfo",MODE_PRIVATE);
		userName = preferences.getString("userName", "");
		Cursor Cursor_userinfo = sqlite.getReadableDatabase().rawQuery("select * from user where 用户姓名 = '"+userName.toString().trim() +"'",null);

		while(Cursor_userinfo.moveToNext())
		{
			userPwd = Cursor_userinfo.getString(2);
			userLevel = Cursor_userinfo.getString(3);
			email = Cursor_userinfo.getString(4);
			phoneNumber =Cursor_userinfo.getString(5);
		}
		textView1.setText(userName);
		textView2.setText(userPwd);
		textView3.setText(userLevel);
		textView4.setText(email);
		textView5.setText(phoneNumber);
	}
}
