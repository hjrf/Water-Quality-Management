package com.hjr.sqlitemanage;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class hjr_SqliteModel extends SQLiteOpenHelper{
	private static final int VERSION = 1;
	private static final String Sqlite="Sqlite";
	public hjr_SqliteModel(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	public hjr_SqliteModel(Context context,String name){
		this(context,name,VERSION);
	}
	public hjr_SqliteModel(Context context,String name,int version){
		this(context, name,null,version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table test(id int,name varchar(20))";
		db.execSQL(sql);
		Log.i(Sqlite,"创建数据库成功，自动生成表成功！");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(Sqlite,"更新数据库成功！");
	}
	public String[] GetdbName(Context context)
	{		
		String[] dbnames = context.databaseList();	
		return dbnames;
	}

}
