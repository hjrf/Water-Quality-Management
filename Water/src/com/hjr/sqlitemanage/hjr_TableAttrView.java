package com.hjr.sqlitemanage;

import java.util.ArrayList;

import com.android.water.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class hjr_TableAttrView extends Activity implements OnClickListener {
	   private final static String Sqlite = "Sqlite数据表属性";
	   ListView filedList = null;
	   TextView currTableName = null;
	   Button showFiledList = null;
	   Button enterAllRecordView = null;
	   Cursor filedCursor = null;
	   Cursor recordCursor = null;
	   hjr_SqliteModel sqliteModel = null;
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.hjr_table_attr_view);
	        FindAllView();
	        GetDBContext(ReceiveIntentBundle("selectedDBName"));
	   }
	   void GetDBContext(String dbName) {
	    	sqliteModel = new hjr_SqliteModel(this,dbName);
	   }
	   String ReceiveIntentBundle(String bundleName)
	   {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			return bundle.getString(bundleName);
	   }
	   private void FindAllView() {
		    filedList = (ListView)findViewById(R.id.filedList);	
		    currTableName = (TextView) findViewById(R.id.currTableName);	
		    showFiledList = (Button) this.findViewById(R.id.showFiledList);
		    enterAllRecordView = (Button) this.findViewById(R.id.enterAllRecordView);

		    showFiledList.setOnClickListener(this);
		    enterAllRecordView.setOnClickListener(this);
		    
		    currTableName.setText(ReceiveIntentBundle("selectedTableName"));
	   }
	   SQLiteDatabase GetDB() {
			return sqliteModel.getReadableDatabase();  
	   }
	   void GetFiledLV()
	   {
		   filedCursor = GetDB().rawQuery("PRAGMA table_info("+ReceiveIntentBundle("selectedTableName") +")", null);
		   filedList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,GetFileds(filedCursor)));
	   }
	   void GetRecordLV()
	   {
		   recordCursor = GetDB().rawQuery("SELECT * FROM "+ReceiveIntentBundle("selectedTableName"), null);
		   filedList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,GetRecords(recordCursor)));
	   }
	   public void onClick(View v) {
			switch(v.getId()){
			case R.id.showFiledList:
				GetFiledLV();
				break;
			case R.id.enterAllRecordView:
				GetRecordLV();
				break;
			default:
				break;
			}
	   }
	    ArrayList<String> GetFileds(Cursor cursor)
	    {
	 	   ArrayList<String> Fileds = new ArrayList<String>(); 
	 	   cursor.moveToFirst();
	 	   do {
	 		   Fileds.add(cursor.getString(0)+"、字段名:"+cursor.getString(1)+" 数据类型:"+cursor.getString(2));//0返回索引，1返回字段名，2返回数据类型，3返回数据长度
	 	   }while(cursor.moveToNext());
	 	   Fileds.add("共"+Fileds.size()+"个字段");
	 	   return Fileds;
	    }
	    ArrayList<String> GetRecords(Cursor cursor)
	    {
	 	   ArrayList<String> Records = new ArrayList<String>(); 
	 	   cursor.moveToFirst();
	 	   do {
	 	   Records.add("该记录ID为:"+cursor.getString(0));
	 	   } while(cursor.moveToNext());
	 	   Records.add("共"+(Records.size())+"条记录");
	 	   return Records;
	    }

	   
}
