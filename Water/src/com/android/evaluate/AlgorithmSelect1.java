package com.android.evaluate;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class AlgorithmSelect1 extends Activity implements OnClickListener{
	private final static String TAG="AlgorithmSelect";
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
	SeekBar seekBar1 = null;
	SeekBar seekBar2 = null;
	SeekBar seekBar3 = null;
	SeekBar seekBar4 = null;
	String algoName = null;
	TextView textView1 = null;
	TextView textView2 = null;
	TextView textView3 = null;
	TextView textView4 = null;
	int SeekBarValue = 0;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.algorithm_select1);
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
		button1.setOnClickListener(this);
		seekBar1 = (SeekBar) this.findViewById(R.id.seekBar1);
		seekBar2 = (SeekBar) this.findViewById(R.id.seekBar2);
		seekBar3 = (SeekBar) this.findViewById(R.id.seekBar3);
		seekBar4 = (SeekBar) this.findViewById(R.id.seekBar4);
		textView1 = (TextView) this.findViewById(R.id.textView1);
		textView2 = (TextView) this.findViewById(R.id.textView2);
		textView3 = (TextView) this.findViewById(R.id.textView3);
		textView4 = (TextView) this.findViewById(R.id.textView4);
		seekBar1.setMax(9);
		seekBar2.setMax(16);
		seekBar3.setMax(110);
		seekBar4.setMax(5);
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());
		seekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());
		seekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());
	}	
	public void onClick(View v) {
		String [] selectItem = null;
		switch(v.getId()){
		case R.id.button1:
		//Log.i(FLAG,"打印的信息！"); 
			selectItem = new String[] {"Bayse算法","Knn算法"};
			DialogSelect(button1,selectItem);
			break;
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
private class OnSeekBarChangeListenerImp implements
    SeekBar.OnSeekBarChangeListener {

	// 触发操作，拖动
		public void onProgressChanged(SeekBar seekBar, int progress,
	        boolean fromUser) {	  
			switch(seekBar.getId()){
			case R.id.seekBar1:
				//Log.i(FLAG,"打印的信息！"); 
				SeekBarValue = seekBar1.getProgress();
				textView1.setText(String.valueOf(SeekBarValue));
					break;
			case R.id.seekBar2:
				//Log.i(FLAG,"打印的信息！"); 
				SeekBarValue = seekBar2.getProgress();
				textView2.setText(String.valueOf(SeekBarValue));
					break;
			case R.id.seekBar3:
				//Log.i(FLAG,"打印的信息！");
				SeekBarValue = seekBar3.getProgress();
				textView3.setText(String.valueOf(SeekBarValue));
					break;
			case R.id.seekBar4:
				//Log.i(FLAG,"打印的信息！");
				SeekBarValue = seekBar4.getProgress();
				textView4.setText(String.valueOf(SeekBarValue));
					break;
				default:
				//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
					break;
			}
	//	seekBarValue = seekBar.getProgress();
	//	textView1.setText(String.valueOf(seekBarValue));
		}
		// 表示进度条刚开始拖动，开始拖动时候触发的操作
		public void onStartTrackingTouch(SeekBar seekBar) {
		
		}	
		// 停止拖动时候
		public void onStopTrackingTouch(SeekBar seekBar) {
		    // TODO Auto-generated method stub
		}

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
		    		algoName="Bayes算法";
		    	}
		    	else if(which == 1)
		    	{
		    		algoName="Knn算法";
		    		seekBar1.setMax(160);
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
}