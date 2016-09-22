package com.android.fragment.syslayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WarnInfoList extends Fragment {
	Button button1 = null;
	String sql = null;
	private ListView listView1 = null;
	private SimpleAdapter simpleAdapter = null;
	String [][] waterdata = null;
	String [][] standard = null;
	WarnItemData warnItemData = new WarnItemData();
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 

		View view = inflater.inflate(R.layout.warninfo,container,false);
        listView1 = (ListView)view.findViewById(R.id.listView1);
        button1 = (Button)view.findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
       		 InitData();   
    		 check(standard,waterdata);
    		 DataBind();
            }  
        });  

		return view;
    }
	 void DataBind()
	 {
	     //创建简单适配器SimpleAdapter
	        simpleAdapter = new SimpleAdapter(this.getActivity().getApplicationContext(), this.getItem(), R.layout.warninfo_child, 
	                new String[] {"itemTitle","itemPhoto", "itemSummary", "itemAuthor", "itemPublishtime"},
	                new int[] {R.id.title, R.id.photograph, R.id.summary, R.id.author, R.id.publishtime});
	        
	        //加载SimpleAdapter到ListView中
	        listView1.setAdapter(simpleAdapter);
	 }
		void InitData()
		{	
			String parameter = "PH值";
			Sqlite sqlite = new Sqlite(getActivity(),"Water");
			standard = new String [4][3];
			sql = "select 一级标准,二级标准,三级标准  from standard where 参数名称  = 'PH值' or 参数名称  = '溶解氧' or 参数名称  = '高猛酸盐指数' or 参数名称  = '氨氮'";
			Cursor cursor_standard = sqlite.getReadableDatabase().rawQuery(sql,null);
			sql = "SELECT * FROM waterdata";
			Cursor cursor_waterdata = sqlite.getReadableDatabase().rawQuery(sql,null);
	 		int recordNum = GetRecords(cursor_waterdata).size();
			waterdata = new String [recordNum][8];
			int num = 0;
			while(cursor_standard.moveToNext())
			{
				standard[num][0] = cursor_standard.getString(0);
				standard[num][1] = cursor_standard.getString(1);
				standard[num][2] = cursor_standard.getString(2);
				num++;
			}
			num = 0;
			do
			{
				waterdata[num][0] = cursor_waterdata.getString(0);
				waterdata[num][1] = cursor_waterdata.getString(1);
				waterdata[num][2] = cursor_waterdata.getString(2);
				waterdata[num][3] = cursor_waterdata.getString(3);
				waterdata[num][4] = cursor_waterdata.getString(4);
				waterdata[num][5] = cursor_waterdata.getString(5);
				waterdata[num][6] = cursor_waterdata.getString(6);
				waterdata[num][7] = cursor_waterdata.getString(7);
				num++;
			}while(cursor_waterdata.moveToNext());
//			Log.i("asd",waterdata[0][0]);
//			Log.i("asd",waterdata[0][1]);
//			Log.i("asd",waterdata[0][2]);
//			Log.i("asd",waterdata[0][3]);
//			Log.i("asd",String.valueOf(recordNum));
//			Log.i("asd",String.valueOf(num));
		}	
		String[][] check(String[][] standard,String[][] waterdata)
		{
			Sqlite sqlite = new Sqlite(getActivity(),"Water");
			String water = null;
			String river = null;
			String section = null;
			sql = "create table if not exists warning (_id integer primary key autoincrement not null,水域名称 varchar(20),河流名称 varchar(20),区段名称 varchar(20),报警时间 varchar(20),参数名称 varchar(20),参数数值 varchar(20),报警级别 varchar(20))";
			sqlite.getReadableDatabase().execSQL(sql);		
			sql = "delete from warning";
			sqlite.getReadableDatabase().execSQL(sql);
			sql = "update sqlite_sequence SET seq = 0 where name ='warning'";
			sqlite.getReadableDatabase().execSQL(sql);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String parName = null;
			for(int i = 0 ;i<waterdata.length;i++)
			{
				for(int j = 0;j<standard.length;j++)
				{
						if(Double.valueOf(waterdata[i][j+4]) >= Double.valueOf(standard[j][0]) && Double.valueOf(waterdata[i][j+4]) < Double.valueOf(standard[j][1]))
						{
							//一级报警   参数正常
							
							if(j == 0)
							{
								parName = "PH值";
							}
							else if(j == 1)
							{
								parName = "溶解氧";
							}
							else if(j == 2)
							{
								parName = "高锰酸盐指数";
							}
							else if(j == 3)
							{
								parName = "氨氮";
							}
							sql= "insert into warning(水域名称 ,河流名称 ,区段名称 ,报警时间,参数名称,参数数值 ,报警级别 ) values ('"+ waterdata[i][1].toString().trim() +"','"+ waterdata[i][2].toString().trim() +"','"+ waterdata[i][3].toString().trim() +"','"+ df.format(new Date()) +"','"+ parName.toString().trim() +"','"+ waterdata[i][j+4].toString().trim() +"','蓝色报警')";
							sqlite.getReadableDatabase().execSQL(sql);
							Log.i("asd","出现一次1级报警");
						}
						else if(Double.valueOf(waterdata[i][j+4])>Double.valueOf(standard[j][1]) && Double.valueOf(waterdata[i][j+4])<Double.valueOf(standard[j][2]))
						{
							//二级报警
							
							if(j == 0)
							{
								parName = "PH值";
							}
							else if(j == 1)
							{
								parName = "溶解氧";
							}
							else if(j == 2)
							{
								parName = "高锰酸盐指数";
							}
							else if(j == 3)
							{
								parName = "氨氮";
							}
							sql= "insert into warning(水域名称 ,河流名称 ,区段名称 ,报警时间,参数名称,参数数值 ,报警级别 ) values ('"+ waterdata[i][1].toString().trim() +"','"+ waterdata[i][2].toString().trim() +"','"+ waterdata[i][3].toString().trim() +"','"+ df.format(new Date()) +"','"+ parName.toString().trim() +"','"+ waterdata[i][j+4].toString().trim() +"','黄色报警')";
							sqlite.getReadableDatabase().execSQL(sql);
							Log.i("asd","出现一次2级报警");
						}
						else if(Double.valueOf(waterdata[i][j+4])>=Double.valueOf(standard[j][2]))
						{
							//三级报警
							if(j == 0)
							{
								parName = "PH值";
							}
							else if(j == 1)
							{
								parName = "溶解氧";
							}
							else if(j == 2)
							{
								parName = "高锰酸盐指数";
							}
							else if(j == 3)
							{
								parName = "氨氮";
							}
							sql= "insert into warning(水域名称 ,河流名称 ,区段名称 ,报警时间,参数名称,参数数值 ,报警级别 ) values ('"+ waterdata[i][1].toString().trim() +"','"+ waterdata[i][2].toString().trim() +"','"+ waterdata[i][3].toString().trim() +"','"+ df.format(new Date()) +"','"+ parName.toString().trim() +"','"+ waterdata[i][j+4].toString().trim() +"','红色报警')";
							sqlite.getReadableDatabase().execSQL(sql);
							Log.i("asd","出现一次3级报警");
						}
				}

			}
			return null;
			
		}
		   ArrayList<String> GetRecords(Cursor cursor)
		    {
		 	   ArrayList<String> Records = new ArrayList<String>(); 
		 	   cursor.moveToFirst();
		 	   do {
		 	   Records.add("该记录ID为:"+cursor.getString(0));
		 	   } while(cursor.moveToNext());
//		 	   Records.add("共"+(Records.size())+"条记录");
		 	   cursor.moveToFirst();
		 	   return Records;
		    }		
    public ArrayList<HashMap<String, Object>> getItem() {
        ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
    	Sqlite sqlite = new Sqlite(getActivity(),"Water");
    	sql = "select * from warning";
    	Cursor cursor_warning = sqlite.getReadableDatabase().rawQuery(sql,null);
    	int num = 0;
    	while(cursor_warning.moveToNext())
    	{
    		//索引0是序号,排除，1-7水域名称 ,河流名称 ,区段名称 ,报警时间,参数名称,参数数值 ,报警级别
    		 HashMap<String, Object> map = new HashMap<String, Object>();
             map.put("itemTitle", cursor_warning.getString(1)+"水质报警");
             if(cursor_warning.getString(7).toString().trim().equals("红色报警"))
             {
            	 map.put("itemPhoto", R.drawable.warning_red); 
             }
             else if(cursor_warning.getString(7).toString().trim().equals("黄色报警"))
             {
            	 map.put("itemPhoto", R.drawable.warning_yellow); 
             }
             else
             {
            	 map.put("itemPhoto", R.drawable.warning_blue); 
             }
             map.put("itemSummary", cursor_warning.getString(2)+"、"+cursor_warning.getString(3));
             map.put("itemAuthor", cursor_warning.getString(5));
             map.put("itemPublishtime", cursor_warning.getString(6)+"-----"+cursor_warning.getString(4));
             item.add(map);
             num++;
    	}
        return item;
    }
}
