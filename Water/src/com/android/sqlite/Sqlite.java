package com.android.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Sqlite extends SQLiteOpenHelper{//必须继承SQLiteOpenHelper类
	private static final int VERSION = 1;//这个是版本，1，2，3等
	private static final String FLAG="Conn";//这个是Log的标签，直接用类名
	public Sqlite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	public Sqlite(Context context,String name){
		this(context,name,VERSION);
	}
	public Sqlite(Context context,String name,int version){
		this(context, name,null,version);
	}
//以上三个构造函数固定的不用改
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table test(id int,name varchar(20))";//创建一个表，SQL语句可以自己定制
		db.execSQL(sql);//执行Sql语句的格式
		Log.i(FLAG,"创建数据库成功，自动生成表成功！");//Log打印信息
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//这个不用管
		Log.i(FLAG,"更新数据库成功！");//Log打印信息</span>
	}
	
	
}
