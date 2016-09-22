package com.android.evaluate;

import com.android.sqlite.Sqlite;
import com.android.templet.Move;
import com.android.water.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EvaluateSelect extends Activity implements OnClickListener{
	private final static String TAG="Normal";
	Sqlite sqlite = new Sqlite(this,"Water");//例化一个object对象//每次要用到执行或者查询都写一次//databaseName写你要操作的数据库名
	//getReadableDatabase().execSQL("SQL执行语句");//执行语句就是建表，INSERT,UPDATA等
	//Cursor cursor = object.getReadableDatabase().rawQuery("SQL查询语句",null);//查询语句就是SELECT
//	TextView textView1 = null;
//	Button button1 = null;
//	EditText editText1 = null;
//	ListView listView1 = null;
//	RadioGroup radioGroup1 = null;
//	private List<CheckBox> checkBoxs = new ArrayList<CheckBox>(); 

	Button button1 = null;
	Button button2 = null;	
	TextView textView1 = null;
	
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.evaluate_select);
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
		
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		textView1 = (TextView) this.findViewById(R.id.textView1);
		
		button1.setOnClickListener(this);	
		button2.setOnClickListener(this);	
		
		
	}
	void DialogSelect()
	{
		new  AlertDialog.Builder(this)  
		.setTitle("请选择" )  
		.setIcon(android.R.drawable.ic_dialog_info)                  
		.setSingleChoiceItems(new  String[] {"吉林水域", "长春水域", "黑龙江水域" , "长白山水域" },  0 ,   
		  new  DialogInterface.OnClickListener() {  
		                              
		     public void onClick(DialogInterface dialog,  int  which) {  
		    	textView1.setText("当前选择的水域是："+which);
		        dialog.dismiss();  
		    	Intent intentTable = new Intent(EvaluateSelect.this,EvaluateAdd.class);
		    	startActivity(intentTable);
		     }  
		  }  
		)  
		.setNegativeButton("取消" ,  null )  
		.show();  
	}
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
	    	Intent intentTable = new Intent(EvaluateSelect.this,EvaluateAdd.class);
	    	startActivity(intentTable);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button2:
			DialogSelect();
		//Log.i(FLAG,"打印的信息！"); 
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	
}
