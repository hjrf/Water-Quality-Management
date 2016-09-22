package com.hjr.sqlitemanage;

import com.android.water.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class hjr_DatabaseView extends Activity implements OnClickListener,AdapterView.OnItemClickListener{
	   private final static String Sqlite="Sqlite数据库视图";
	    hjr_SqliteModel sqliteModel = null;
	    EditText databaseName = null;
	    Button showDBNames = null; 
	    Button createDatabase = null; 
	    Button deleteDB = null;
	    Button controlSelectedTable =null;
	    TextView selectedDBName = null;
	    ListView dbNameList;
	    String[] dbNames = null;
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        GetDBContext("test");
	        setContentView(R.layout.hjr_databases_view);
	        FindAllView();
	    }
	    void GetDBContext(String dbName)
	    {
	    	sqliteModel = new hjr_SqliteModel(this,dbName);
	    }
		private void FindAllView() {
			dbNameList = (ListView)findViewById(R.id.dbNameList);				
			databaseName = (EditText) this.findViewById(R.id.databaseName);			
			showDBNames = (Button) this.findViewById(R.id.showDBNames);			
			createDatabase = (Button) this.findViewById(R.id.createDatabase);	
			selectedDBName = (TextView) this.findViewById(R.id.selectedDBName);
			deleteDB = (Button) this.findViewById(R.id.deleteDB);
			controlSelectedTable = (Button) this.findViewById(R.id.controlSelectedTable);
			deleteDB.setEnabled(false);
			controlSelectedTable.setEnabled(false);
			
			dbNameList.setOnItemClickListener(this);	
			showDBNames.setOnClickListener(this);		
			createDatabase.setOnClickListener(this);
			deleteDB.setOnClickListener(this);
			controlSelectedTable.setOnClickListener(this);
		}
		void GetDBNamesLV()
		{
			String dbNames_temp[] = sqliteModel.GetdbName(this);
			dbNames = new String [dbNames_temp.length/2];
			for(int i = 0 ;i<dbNames.length;i++)
			{
				dbNames[i] = dbNames_temp[i*2];
			}
			dbNameList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dbNames));
		}
		SQLiteDatabase GetDB()
		{
			return sqliteModel.getReadableDatabase();  
		}
		public boolean DeleteDatabase(Context context,String dbName) {
			return context.deleteDatabase(dbName);
		}
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.showDBNames:
				GetDBNamesLV();
				break;
			case R.id.createDatabase:
				if(databaseName.getText().toString().equals(""))
				{
					Toast.makeText(this, "必须先输入数据库名", Toast.LENGTH_SHORT).show();
				}
				else
				{
					sqliteModel = new hjr_SqliteModel(this,databaseName.getText().toString());//获取数据库上下文
					SQLiteDatabase db1 = sqliteModel.getReadableDatabase();//根据当前数据库上下文读取数据库，如果没有自动创建（这个是真正创建数据库指令）  
					Log.i(Sqlite,"创建数据库"+databaseName.getText().toString()+"成功！"); 
					Toast.makeText(this, "创建数据库"+databaseName.getText().toString()+"成功！", Toast.LENGTH_SHORT).show();
				}

				Log.i(Sqlite,"创建数据库按钮正确执行！");
				break;
			case R.id.deleteDB:
				DeleteDatabase(this,selectedDBName.getText().toString());
				selectedDBName.setText("");
				deleteDB.setEnabled(false);
				controlSelectedTable.setEnabled(false);
				Toast.makeText(this, "删除数据库"+selectedDBName.getText().toString()+"成功！", Toast.LENGTH_SHORT).show();
				break;
			case R.id.controlSelectedTable:
				Intent intentTable = new Intent(hjr_DatabaseView.this,hjr_TableView.class);
		    	Bundle bundle = new Bundle();
		    	bundle.putString("selectedDBName",selectedDBName.getText().toString());
		    	intentTable.putExtras(bundle);
		    	startActivity(intentTable);
				break;
			default:
				Log.e(Sqlite,"触发了没有定义的按钮的事件");
				break;
			}
			GetDBNamesLV();
		}
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    GetDBContext(dbNames[position]);
		    selectedDBName.setText(dbNames[position]);
		    
			deleteDB.setEnabled(true);
			controlSelectedTable.setEnabled(true);
		}
}
