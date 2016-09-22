package com.hjr.sqlitemanage;

import java.util.ArrayList;

import com.android.water.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class hjr_TableView extends Activity implements OnClickListener,AdapterView.OnItemClickListener {
	   private final static String Sqlite="Sqlite数据表视图";
	   hjr_SqliteModel sqliteModel = null;
	   EditText tableName = null;
	   Button showTableNames = null;
	   Button createTable = null;
	   Button deleteTable = null;
	   Button controlCurrTable =null;
	   TextView selectedTableName = null;
	   ListView tableNameList;
	   Cursor tableNameCursor;

	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        GetDBContext(ReceiveIntentBundle("selectedDBName"));
	        setContentView(R.layout.hjr_tables_view);
	        FindAllView();
	   }
	   String ReceiveIntentBundle(String bundleName)
	   {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			return bundle.getString(bundleName);
	   }
	   void GetDBContext(String dbName) {
	    	sqliteModel = new hjr_SqliteModel(this,dbName);
	   }
	   private void FindAllView() {
		    tableNameList = (ListView)findViewById(R.id.tableNameList);		
		    tableName = (EditText) this.findViewById(R.id.tableName);	
		    showTableNames = (Button) this.findViewById(R.id.showTableNames);
		    createTable = (Button) this.findViewById(R.id.createTable);
		    selectedTableName = (TextView) this.findViewById(R.id.selectedTableName);
		    deleteTable = (Button) this.findViewById(R.id.deleteTable);
			controlCurrTable = (Button) this.findViewById(R.id.controlCurrTable);
			
			deleteTable.setEnabled(false);
			controlCurrTable.setEnabled(false);
			
		    tableNameList.setOnItemClickListener(this);
		    showTableNames.setOnClickListener(this);
			createTable.setOnClickListener(this);
			deleteTable.setOnClickListener(this);
			controlCurrTable.setOnClickListener(this);
	   }
	   SQLiteDatabase GetDB() {
			return sqliteModel.getReadableDatabase();  
	   }
	   void GetTableNamesLV()
	   {
		   tableNameCursor = GetDB().rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
		   new tableNameListAdapter(this, tableNameCursor);//重新获取数据表集合
		   tableNameList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,GetTableNames(tableNameCursor)));
	   }
	   public void onClick(View v) {
			switch(v.getId()){
			case R.id.showTableNames:	
				break;
			case R.id.deleteTable:
				String sqlDeleteTable = "DROP TABLE "+selectedTableName.getText().toString();
				GetDB().execSQL(sqlDeleteTable);
				Toast.makeText(this, "删除数据表"+selectedTableName.getText().toString()+"成功！", Toast.LENGTH_SHORT).show();
				selectedTableName.setText("");
				deleteTable.setEnabled(false);
				controlCurrTable.setEnabled(false);
				break;
			case R.id.controlCurrTable:
				Intent intentTable = new Intent(hjr_TableView.this,hjr_TableAttrView.class);
		    	Bundle bundle = new Bundle();
		    	bundle.putString("selectedDBName",ReceiveIntentBundle("selectedDBName"));
		    	bundle.putString("selectedTableName",selectedTableName.getText().toString());
		    	intentTable.putExtras(bundle);
		    	startActivity(intentTable);
				break;
			case R.id.createTable:
				if(tableName.getText().toString().equals(""))
				{
					Toast.makeText(this, "必须先输入数据表名", Toast.LENGTH_SHORT).show();
				}
				else
				{	
					String sqlCreatTable = "CREATE TABLE "+tableName.getText().toString()+" (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,pwd TEXT)";
					GetDB().execSQL(sqlCreatTable);
					Log.i(Sqlite,"创建数据表"+tableName.getText().toString()+"成功！"); 
					Toast.makeText(this, "创建数据表"+tableName.getText().toString()+"成功！", Toast.LENGTH_SHORT).show();	
				}
				Log.i(Sqlite,"创建数据表按钮正确执行！");
				break;
			default:
				Log.e(Sqlite,"触发了没有定义的按钮的事件");
				break;
			}
			GetTableNamesLV();//更新ListVe
	   }
	   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
		    ArrayList<String> tableName = GetTableNames(tableNameCursor);		    
			selectedTableName.setText(tableName.get(position)); 
			deleteTable.setEnabled(true);
			controlCurrTable.setEnabled(true);			
		}
	    ArrayList<String> GetTableNames(Cursor cursor)
	    {
	 	   ArrayList<String> tableNames = new ArrayList<String>(); 
	 	   cursor.moveToFirst();
	 	   while(cursor.moveToNext())
	 	   {		
	 		   if(!cursor.getString(0).equals("android_metadata") && !cursor.getString(0).equals("sqlite_sequence"))
	 		   {
	 			   tableNames.add(cursor.getString(0));
	 		   }
	 	   }
	 	   return tableNames;
	    }
		public class tableNameListAdapter extends BaseAdapter{
				private Context adapterContext;
				private Cursor adapterCursor;
				public tableNameListAdapter(Context context,Cursor cursor) {		 
					adapterContext = context;
					adapterCursor = cursor;
				}
				@Override
				public int getCount() {
					return adapterCursor.getCount();
				}
				@Override
				public Object getItem(int position) {
					return null;
				}
				@Override
				public long getItemId(int position) {
					return 0;
				}
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					TextView mTextView = new TextView(adapterContext);
					adapterCursor.moveToPosition(position);		
					mTextView.setText(adapterCursor.getString(0));
					return mTextView;
				}
		}		
}
