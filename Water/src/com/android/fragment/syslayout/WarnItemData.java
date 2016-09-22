package com.android.fragment.syslayout;

import java.util.ArrayList;

import com.android.sqlite.Sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class WarnItemData extends Fragment{
	
	static int itemNum = 8;
	String sql = null;
	ArrayList<String> title = new ArrayList<String>(){};

	public WarnItemData()
	{
		for(int i = 0;i<itemNum;i++)
		{
			CreateOneMessage();
		}
	}
	void InitData()
	{	
		Sqlite sqlite = new Sqlite(getActivity(),"Water");
//		double [][] standard = new double [4][3];
		sql = "select * from standard";
		Cursor cursor_standard = sqlite.getReadableDatabase().rawQuery(sql,null);
//		sql = "SELECT * FROM waterdata";
//		Cursor cursor_waterdata = sqlite.getReadableDatabase().rawQuery(sql,null);
// 		int recordNum = GetRecords(cursor_waterdata).size();
//		double [][] waterdata = new double [7][recordNum];
//		int num = 0;
//		while(cursor_standard.moveToNext())
//		{
//			standard[num][0] = Double.valueOf(cursor_standard.getString(0));
//			standard[num][0] = Double.valueOf(cursor_standard.getString(1));
//			standard[num][0] = Double.valueOf(cursor_standard.getString(2));
//			num++;
//		}
//		Log.i("asd",String.valueOf(standard[0][0]));
		
	}
	
    ArrayList<String> GetRecords(Cursor cursor)
    {
 	   ArrayList<String> Records = new ArrayList<String>(); 
 	   cursor.moveToFirst();
 	   do {
 	   Records.add("该记录ID为:"+cursor.getString(0));
 	   } while(cursor.moveToNext());
// 	   Records.add("共"+(Records.size())+"条记录");
 	   return Records;
    }
	void CreateOneMessage()
	{
		
		title.add("龙潭区水质预警！");
	}
	public String getTitle(int i) {
		// TODO 自动生成的方法存根

		return title.get(i);
	}

	public  String getPhotoResId(int i) {
		// TODO 自动生成的方法存根
		return "图片id";
	}

	public String getSummary(int i) {
		// TODO 自动生成的方法存根
		return "具体地点";
	}

	public  String getAuthor(int i) {
		// TODO 自动生成的方法存根
		return "超标参数";
	}

	public  String getPublishtime(int i) {
		// TODO 自动生成的方法存根
		return "值";
	}

	public  int getItemNum() {
		// TODO 自动生成的方法存根
		return itemNum;
	}

}
