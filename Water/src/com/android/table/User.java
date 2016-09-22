package com.android.table;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class User extends Activity implements OnClickListener,AdapterView.OnItemClickListener{
	private final static String TAG="SqliteTable";
	protected Sqlite sqlite = new Sqlite(this,"Water");
	protected ListView listView1 = null;  
	Button button1 = null;
	Button button2 = null;
	int clickFlag = 0;
	int clickItem = 0;
	String s = null;
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	    // TODO Auto-generated method stub  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.table_user);   
	    FindAllView(); 
	    listViewDataBind();
	}  
	
	 void FindAllView() {
//		listView1 = (ListView)findViewById(R.id.listView1);				
//		editText1 = (EditText) this.findViewById(R.id.editText1);			
//		button1 = (Button) this.findViewById(R.id.button1);			
//		textView1 = (TextView) this.findViewById(R.id.textView1);
//		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
//	 	checkBox1 = (CheckBox) findViewById(R.id.checkBox1);  
	
//		checkBox1.setChecked(true);  
//		button1.setEnabled(false);		
	
//		listView1.setOnItemClickListener(this);	
//		button1.setOnClickListener(this);	
//		checkBoxs.add(checkBox1);  
		button1 = (Button) this.findViewById(R.id.button1);	
		button2 = (Button) this.findViewById(R.id.button2);	
		listView1 = (ListView)findViewById(R.id.listView1);	
		button2.setEnabled(false);		
		button1.setOnClickListener(this);	
		button2.setOnClickListener(this);
		listView1.setOnItemClickListener(this);	
	
}
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
		//Log.i(FLAG,"打印的信息！"); 
			OpenDialog(0,0);
			break;
		case R.id.button2:
			//Log.i(FLAG,"打印的信息！"); 
			sqlite.getReadableDatabase().execSQL("delete from user where rowid in(select rowid from user  limit 1 offset '"+ clickItem +"')");
			button2.setEnabled(false);	
		    Intent intent = new Intent();  
//		        intent.putExtra("content", s);  
		    intent.setClass(User.this, User.class);  
		    startActivity(intent);  
		    break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	
	// 更新listview  
	public void listViewDataBind() {  
		Cursor cursor_user = sqlite.getReadableDatabase().rawQuery("SELECT * FROM user",null);
		String[] columnNames = new String[]{cursor_user.getColumnName(0),cursor_user.getColumnName(1),
		cursor_user.getColumnName(2),cursor_user.getColumnName(3),cursor_user.getColumnName(4),cursor_user.getColumnName(5)
		};
		int columnID [] = new int[] { R.id.id, R.id.par1, R.id.par2, R.id.par3, R.id.par4,R.id.par5};
		
//		int filedNum = GetFiledNum("user");
//		Log.i(TAG,String.valueOf(filedNum));
//		cursor_user.moveToFirst();
//		do {
//				for(int i = 0;i<filedNum;i++)
//				{
//					Log.i(TAG,cursor_user.getString(i));
//				}
//			} while(cursor_user.moveToNext());

//	 	Log.i(TAG,columnNames[0]);
//	 	Log.i(TAG,columnNames[1]);
//	 	Log.i(TAG,columnNames[2]);
//	 	Log.i(TAG,columnNames[3]);
//	 	Log.i(TAG,columnNames[4]);
	 	
		ListAdapter adapter = new SetTableStyle(this,  
	            R.layout.table_user_child,cursor_user,columnNames,columnID);  
	    // layout为listView的布局文件，包括三个TextView，用来显示三个列名所对应的值  
	    // ColumnNames为数据库的表的列名  
	    // 最后一个参数是int[]类型的，为view类型的id，用来显示ColumnNames列名所对应的值。view的类型为TextView  
	    listView1.setAdapter(adapter);  	
	}  
	int GetFiledNum(String tableName)
	{
		Cursor cursor_filedAttr = sqlite.getReadableDatabase().rawQuery("PRAGMA table_info("+ tableName +")", null);
		int filedNum = -1;
		do{
			filedNum++;
		}while(cursor_filedAttr.moveToNext());	
		return filedNum;
	}
	@Override  
	protected void onDestroy() {// 关闭数据库  
	    // TODO Auto-generated method stub  
	    super.onDestroy();  
	    if (sqlite != null) {  
	    	sqlite.close();  
	    }  
	}
	public void OpenDialog(final int way,int id)
	{
		final View viewEditTable = View.inflate(this, R.layout.dialog_user, null); 
		new AlertDialog.Builder(this).setTitle("编辑数据")  
		.setIcon(android.R.drawable.ic_dialog_info)  
		.setView(viewEditTable)  
		.setPositiveButton("确认",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
		        EditText editText1 = (EditText)viewEditTable.findViewById(R.id.editText1);
		        EditText editText2 = (EditText)viewEditTable.findViewById(R.id.editText2);
		        Spinner spinner1 = (Spinner)viewEditTable.findViewById(R.id.spinner1);
		        EditText editText3 = (EditText)viewEditTable.findViewById(R.id.editText3);
		        EditText editText4 = (EditText)viewEditTable.findViewById(R.id.editText4);
		    	String et1 = editText1.getText().toString();
		    	String et2 = editText2.getText().toString();
		    	String et3 = spinner1.getSelectedItem().toString();
		    	String et4 = editText3.getText().toString();
		    	String et5 = editText4.getText().toString();
		    	//执行数据库操作      //0增添	//1更新
		    	if(way == 0)
		    	{
			    	if("".equals(et1) || "".equals(et2) ||"".equals(et3) || "".equals(et4)||"".equals(et5))
			    	{
			    		Toast.makeText(getApplication(), "请将信息填写完整！", Toast.LENGTH_SHORT).show();
			    		return;
			    	}
			    	else 
					{
			    		String sql = "insert into user (用户姓名,用户密码,用户级别,电子邮箱,电话号码) values ('"+ et1 +"','"+ et2 +"','"+ et3 +"','"+ et4 +"','"+ et5 +"')";
			    		sqlite.getReadableDatabase().execSQL(sql);
			            Intent intent = new Intent();  
	//			        intent.putExtra("content", s);  
				        intent.setClass(User.this, User.class);  
				        startActivity(intent);  
					}
		    	}
		    	else if(way == 1)
				{		
		    		if(!"".equals(et1))
		    		{
		    			String sql = "update user set 用户姓名 ='"+ et1 +"' where rowid in(select rowid from user  limit 1 offset '"+ clickItem +"')";
			    		sqlite.getReadableDatabase().execSQL(sql);
		    		}
		    		if(!"".equals(et2))
		    		{
		    			String sql = "update user set 用户密码 ='"+ et2 +"' where rowid in(select rowid from user  limit 1 offset '"+ clickItem +"')";
			    		sqlite.getReadableDatabase().execSQL(sql);
		    		}
		    		if(!"".equals(et3))
		    		{
		    			String sql = "update user set 用户级别 ='"+ et3 +"' where rowid in(select rowid from user  limit 1 offset '"+ clickItem +"')";
			    		sqlite.getReadableDatabase().execSQL(sql);
		    		}
		    		if(!"".equals(et4))
		    		{
		    			String sql = "update user set 电子邮箱 ='"+ et4 +"' where rowid in(select rowid from user  limit 1 offset '"+ clickItem +"')";
			    		sqlite.getReadableDatabase().execSQL(sql);
		    		}
		    		if(!"".equals(et5))
		    		{
		    			String sql = "update user set 电话号码 ='"+ et5 +"' where rowid in(select rowid from user  limit 1 offset '"+ clickItem +"')";
			    		sqlite.getReadableDatabase().execSQL(sql);
		    		}
		    	       Intent intent = new Intent();  
		    	   	// intent.putExtra("content", s);  
		    	   	   intent.setClass(User.this, User.class);  
		    	   	   startActivity(intent);  
				}
			}  
		})			    		
		.setNegativeButton("取消",listener)  
		.show();  

	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO 自动生成的方法存根
		clickItem = position;
		button2.setEnabled(true);	
		clickFlag++;
		if(clickFlag%2==0)
		{
			OpenDialog(1,clickItem);
			button2.setEnabled(false);	
		}
		else if(clickFlag == 100)
		{
			clickFlag = 0;
		}
		Toast.makeText(this, "点击的是第"+ position +"行", Toast.LENGTH_SHORT).show();
	}  
	
	void DialogOK()
	{
		Toast.makeText(this, "点击的是确定！", Toast.LENGTH_SHORT).show();
	}
//	void DialogQuit()
//	{
//		Toast.makeText(this, "点击的是取消！", Toast.LENGTH_SHORT).show();
//	}
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() 
	{ 
		public void onClick(DialogInterface dialog, int which) 
		{ 
			switch (which) 
			{ 
				case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序 
			
					Log.i("测试", "点击了确认,文本框的值为：");
				break; 
				case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框 
					Log.i("测试", "点击了取消");
				break; 
				default: 
				break; 
			} 
		} 
	}; 
}  