package com.android.user;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Reg extends Activity implements OnClickListener{
	private final static String FLAG="Reg";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句");//查询语句就是SELECT
	
	EditText editText1 = null;
	EditText editText2 = null;	
	EditText editText3 = null;	
	EditText editText4 = null;	
	
	Button button1 = null;	
	Button button2 = null;	
	
	RadioGroup radioGroup1 = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.reg);
	     String sql = "create table if not exists user (_id integer primary key autoincrement not null,用户姓名 varchar(20),用户密码 varchar(20),用户级别 varchar(20),电子邮箱 varchar(20),电话号码 varchar(20))";
	     sqlite.getReadableDatabase().execSQL(sql);
	     FindAllView();
	 }
	private void FindAllView() {
//		xxx = (ListView)findViewById(R.id.xxx);				
//		xxx = (EditText) this.findViewById(R.id.xxx);			
//		xxx = (Button) this.findViewById(R.id.xxx);			
//		xxx = (TextView) this.findViewById(R.id.xxx);
		button1 = (Button) this.findViewById(R.id.button1);	
		button2 = (Button) this.findViewById(R.id.button2);	
		editText1 = (EditText) this.findViewById(R.id.editText1);	
		editText2 = (EditText) this.findViewById(R.id.editText2);	
		editText3 = (EditText) this.findViewById(R.id.editText3);	
		editText4 = (EditText) this.findViewById(R.id.editText4);	
		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
		
//		button.setEnabled(false);		
//		ListView.setOnItemClickListener(this);	
//		button.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}
	String GetRadioValue()
	{
		String level = "未知用户";
		for(int i=0;i<radioGroup1.getChildCount();i++) {
			RadioButton r = (RadioButton)radioGroup1.getChildAt(i);
			if(r.isChecked())
			{
				r.getText();
				if (r.getText().equals("普通用户")) {//判断用户级别
					level = "普通用户";
				}
				else if (r.getText().equals("管理用户")){
					level = "管理用户";
				}
				else if (r.getText().equals("科研用户")){
					level = "科研用户";
				}
				else {
					level = "未知用户";
				}
			}
		}		
		return level;
	}
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1://注册
			if(CheckReg())
			{
				ExeReg();
			}
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button2://取消注册
			QuitReg();
		//Log.i(FLAG,"打印的信息！"); 
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	boolean CheckReg()
	{
		String name = editText1.getText().toString();
		String pwd = editText2.getText().toString();
		String email = editText3.getText().toString();
		String phoneNumber = editText4.getText().toString();
		Cursor cursor_name = null;
		if("".equals(name))
		{
			Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
			return false;	
		}
		else if("".equals(pwd))
		{
			Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
			return false;	
		}
		else if("".equals(phoneNumber))
		{
			Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
			return false;	
		}
		else if("".equals(email))
		{
			Toast.makeText(this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
			return false;	
		}
		else
		{
			
			String sql = "SELECT * FROM user WHERE 用户姓名 ='"+ name +"'";
			cursor_name = sqlite.getReadableDatabase().rawQuery(sql,null);	
			String Check = null;
			try{
				while(cursor_name.moveToNext())
				{
					Check = cursor_name.getString(1);
				}
				if(!Check.equals(""))
				{
					Toast.makeText(this, "该用户已存在，请重新输入用户名", Toast.LENGTH_SHORT).show();
					editText1.setText("");
					return false;
				}
			}
			catch(Exception e)
			{
				Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;
		
	}
	void ExeReg()
	{
		Log.i(FLAG,"注册按钮执行成功！");
		String name = editText1.getText().toString();
		String pwd = editText2.getText().toString();
		String email = editText3.getText().toString();
		String phoneNumber = editText4.getText().toString();		
		String level = GetRadioValue();
	
		String sql = "create table if not exists user (_id integer primary key autoincrement not null,用户姓名 varchar(20),用户密码 varchar(20),用户级别 varchar(20),电子邮箱 varchar(20),电话号码 varchar(20))";
		sqlite.getReadableDatabase().execSQL(sql);
		sql = "insert into user(用户姓名,用户密码,用户级别,电子邮箱,电话号码) values('"+ name +"','"+ pwd +"','"+ level +"','"+ email +"','"+ phoneNumber +"')";
		sqlite.getReadableDatabase().execSQL(sql);		
		Intent intentTable = new Intent(Reg.this,RegSuccess.class);
    	startActivity(intentTable);
	}
	void QuitReg()
	{
	   	Intent intentTable = new Intent(Reg.this,Login.class);
    	startActivity(intentTable);
	}
}
