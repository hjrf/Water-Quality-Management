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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluateAdd extends Activity implements OnClickListener{
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
	Button button3 = null;
	SeekBar seekBar1 = null;
	TextView textView1 = null;
	float seekBarValue = 0;
	String paraName = null;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.evaluate_add);
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
		textView1 =(TextView)this.findViewById(R.id.textView1);
		seekBar1 =(SeekBar) this.findViewById(R.id.seekBar1);
		button1 = (Button) this.findViewById(R.id.button1);	
		button1.setOnClickListener(this);	
		button2 = (Button) this.findViewById(R.id.button2);		
		button2.setOnClickListener(this);	
		button3 = (Button) this.findViewById(R.id.button3);	
		button3.setOnClickListener(this);	
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());
	}
	
	void DialogSelect(final Button button,final String [] selectItem)
	{
		new  AlertDialog.Builder(this)  
		.setTitle("请选择" )  
		.setIcon(android.R.drawable.ic_dialog_info)                  
		.setSingleChoiceItems(selectItem, 0 ,   
		  new  DialogInterface.OnClickListener() {  	                              
		     public void onClick(DialogInterface dialog,  int  which) {  
		    	button.setText("当前选择的是："+selectItem[which]);
		    	if(which == 0)
		    	{
		    		seekBar1.setMax(90);
		    		paraName="PH值";
		    	}
		    	else if(which == 1)
		    	{
		    		paraName="溶解氧";
		    		seekBar1.setMax(160);
		    	}
		    	else if(which == 2)
		    	{
		    		paraName="高锰酸盐指数";
		    		seekBar1.setMax(1100);
		    	}
		    	else if(which == 3)
		    	{
		    		paraName="氨氮";
		    		seekBar1.setMax(50);
		    	}
		        dialog.dismiss();  
//		    	Intent intentTable = new Intent(ReceiveWaterData.this,EvaluateAdd.class);
//		    	startActivity(intentTable);
		     }  
		  }  
		)  
		.setNegativeButton("取消" ,  null )  
		.show();  
	}
	
private class OnSeekBarChangeListenerImp implements
    SeekBar.OnSeekBarChangeListener {

// 触发操作，拖动
public void onProgressChanged(SeekBar seekBar, int progress,
        boolean fromUser) {	   
	seekBarValue = seekBar.getProgress();
	seekBarValue = seekBarValue / 10f;
	textView1.setText(String.valueOf(seekBarValue));
	}
	// 表示进度条刚开始拖动，开始拖动时候触发的操作
	public void onStartTrackingTouch(SeekBar seekBar) {
	
	}	
	// 停止拖动时候
	public void onStopTrackingTouch(SeekBar seekBar) {
	    // TODO Auto-generated method stub
	}
}
	
	public void onClick(View v) {
		String [] selectItem = null;
		switch(v.getId()){
		case R.id.button1:
			selectItem = new String[]{"PH值","溶解氧","高猛酸盐指数","氨氮"};
			DialogSelect(button1, selectItem);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.button2:
		//Log.i(FLAG,"打印的信息！"); 
		if(button1.getText().toString().trim().equals("选择参数"))
		{
			Toast.makeText(this, "请先选择好水质参数！", Toast.LENGTH_SHORT).show();
			return;
		}
			Intent intentTable = new Intent(EvaluateAdd.this,EvaluateInfoSingle.class);
			intentTable.putExtra("paraName",paraName);
			intentTable.putExtra("paraValue",String.valueOf(seekBarValue));
	    	startActivity(intentTable);
			break;
		case R.id.button3:
		//Log.i(FLAG,"打印的信息！"); 
			Intent intentTable1 = new Intent(EvaluateAdd.this,AlgorithmSelect.class);
	    	startActivity(intentTable1);
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	
}
