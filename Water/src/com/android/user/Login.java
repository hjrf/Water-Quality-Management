package com.android.user;


import com.android.sqlite.Sqlite;
import com.android.systemlayout.MainLayout;
import com.android.water.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{
	private final static String TAG="Login";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
//	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
//	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句");//查询语句就是SELECT
//	
////	TextView textView1 = null;
////	Button button1 = null;
////	EditText editText = null;
////	ListView listView = null;
	
		
	EditText editText1 = null;
	EditText editText2 = null;
	Button button1 = null;
	CheckBox checkBox1 = null;
	CheckBox checkBox2 = null;
	TextView textView1 = null;
	private SharedPreferences sp = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.login);
	     FindAllView();
	     AutoInit();
	 }
	private void FindAllView() {
//		listView1 = (ListView)findViewById(R.id.listView1);				
//		editText1 = (EditText) this.findViewById(R.id.editText1);			
//		button1 = (Button) this.findViewById(R.id.button1);			
//		textView1 = (TextView) this.findViewById(R.id.textView1);
//		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
//		
//		button1.setEnabled(false);		
//		ListView1.setOnItemClickListener(this);	
//		button1.setOnClickListener(this);		
		textView1 = (TextView) this.findViewById(R.id.register_link);
		editText1 = (EditText) this.findViewById(R.id.username_edit);	
		editText2 = (EditText) this.findViewById(R.id.password_edit);	
		button1 = (Button) this.findViewById(R.id.signin_button);		
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
		
		textView1.setOnClickListener(this);
		button1.setOnClickListener(this);
		checkBox2.setOnClickListener(this);
	}

	void AutoInit()
	{
		sp = getSharedPreferences("userLoginInfo", MODE_PRIVATE);
		boolean isAutoSave = true;
		boolean isAutoLogin = true;
		isAutoLogin = sp.getBoolean("autoLogin", false);
		isAutoSave = sp.getBoolean("autoSave", false);
		if(isAutoLogin)
		{
			checkBox1.setChecked(true);
			checkBox2.setChecked(true);
			String userName = sp.getString("userName", "");
			String userPwd = sp.getString("userPwd", "");
			Intent intentTable = new Intent(Login.this,MainLayout.class);
	    	startActivity(intentTable);
		}
		else if(isAutoSave){
			checkBox1.setChecked(true);
			checkBox2.setChecked(false);
			String userName = sp.getString("userName", "");
			String userPwd = sp.getString("userPwd", "");
			editText1.setText(userName);
			editText2.setText(userPwd);
		}
		else{
			checkBox1.setChecked(false);
			checkBox2.setChecked(false);
			editText1.setText("");
			editText2.setText("");
		}
	}

	void AutoCheck()
	{
		Editor editor = sp.edit();
		String userName =  editText1.getText().toString();
		String userPwd =  editText2.getText().toString();
		String userLevel = null;
		
		String sql = "SELECT 用户级别   FROM user WHERE 用户姓名 = '"+ userName +"'";
		Cursor cursor_level = sqlite.getReadableDatabase().rawQuery(sql, null);
		while(cursor_level.moveToNext())
		{
			userLevel = cursor_level.getString(0);
		}
		editor.putString("userLevel",userLevel);
		editor.commit();
		
		if(checkBox2.isChecked())
		{
			editor.putString("userName",userName);
			editor.putString("userPwd",userPwd);
			editor.putBoolean("autoSave", true);
			editor.putBoolean("autoLogin", true);
			editor.commit();
		}
		else if(checkBox1.isChecked())
		{
			editor.putString("userName",userName);
			editor.putString("userPwd",userPwd);
			editor.putBoolean("autoSave", true);
			editor.putBoolean("autoLogin", false);
			editor.commit();
		}
		else
		{
			editor.putBoolean("autoSave", false);
			editor.putBoolean("autoLogin", false);
			editor.commit();
		}
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.signin_button:
			if(LoginCheck())
			{			
				AutoCheck();
		    	Intent intentTable = new Intent(Login.this,MainLayout.class);
		    	startActivity(intentTable);
			}
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.register_link:
		    //Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
		    Intent intentTable1 = new Intent(Login.this,Reg.class);
		    startActivity(intentTable1);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.checkBox2:
		    //Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			if(checkBox2.isChecked())
			{
				checkBox1.setChecked(true);
			}
		//Log.i(FLAG,"打印的信息！"); 
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	boolean LoginCheck()
	{
		String userName = editText1.getText().toString();
		String userPwd = editText2.getText().toString();
		String checkPwd = null;
		Cursor cursor_pwd = null;
		if("".equals(userName))
		{
			Toast.makeText(this, "请输入用户名！", Toast.LENGTH_SHORT).show();
	    	return false;
		}
		else if("".equals(userPwd))
		{
			Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
	    	return false;
		}
		else
		{	
			
			String sql = "SELECT 用户密码   FROM user WHERE 用户姓名 ='"+ userName +"'";
			Log.i("asd", sql);
			try{
				cursor_pwd = sqlite.getReadableDatabase().rawQuery(sql,null);
				while(cursor_pwd.moveToNext())
				{
					checkPwd = cursor_pwd.getString(cursor_pwd.getColumnIndex("用户密码"));
				}
				if(checkPwd.equals(userPwd))
				{
					Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
					return true;
				}
				else
				{
					Toast.makeText(this, "输入密码错误！", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
			catch(Exception e)
			{
				Toast.makeText(this, "该用户不存在！", Toast.LENGTH_SHORT).show();
				return false;
			}	    
		}
	}
	
}
