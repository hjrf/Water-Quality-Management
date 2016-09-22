package com.android.evaluate;

import com.android.sqlite.Sqlite;

import android.app.Activity;
import android.os.Bundle;

public class ReceiveBayesEvaluate extends Activity{
	private final static String TAG="ReceiveBayesEvaluate";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句",null);//查询语句就是SELECT
//	TextView textView1 = null;
//	Button button1 = null;
//	EditText editText1 = null;
//	ListView listView1 = null;
//	RadioGroup radioGroup1 = null;
//	private List<CheckBox> checkBoxs = new ArrayList<CheckBox>();  
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     //setContentView(R.layout.xxx);
	     FindAllView();
	 }
	private void FindAllView() {
//			listView1 = (ListView)findViewById(R.id.listView1);				
//			editText1 = (EditText) this.findViewById(R.id.editText1);			
//			button1 = (Button) this.findViewById(R.id.button1);			
//			textView1 = (TextView) this.findViewById(R.id.textView1);
//			radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
//		 	checkBox1 = (CheckBox) findViewById(R.id.checkBox1);  
		
//			checkBox1.setChecked(true);  
//			button1.setEnabled(false);		
		
//			ListView1.setOnItemClickListener(this);	
//			button1.setOnClickListener(this);	
//			checkBoxs.add(checkBox1);  
	}
}