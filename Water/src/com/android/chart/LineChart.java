package com.android.chart;

import java.util.ArrayList;
import java.util.Arrays;

import com.android.sqlite.Sqlite;
import com.android.water.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import lecho.lib.hellocharts.model.ArcValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import lecho.lib.hellocharts.view.PieChartView.PieChartOnValueTouchListener;

public class LineChart extends Activity{

	private LineChartView lineChartView;
//	private PieChartView piechartView;
	String sql = null;
	Sqlite sqlite = new Sqlite(this,"Water");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_line);
		lineChartView = (LineChartView) findViewById(R.id.lineChartView);
		//传入的横坐标s
		String[][] paraData =  GetParaData();
		String[] days = new String[paraData.length];
		//三条折线的纵坐标的值
		Float []a = new Float[paraData.length];
		Float []b = new Float[paraData.length];
		Float []c = new Float[paraData.length];
		Float []d = new Float[paraData.length];
//		Log.i("aaa",Integer.valueOf(paraData[0][0]).toString());
		for(int i = 0;i<paraData.length;i++)
		{
			days[i] = (i+1)+"号";
			a[i] = Float.valueOf(paraData[i][0]); 
			b[i] = Float.valueOf(paraData[i][1]); 
			c[i] = Float.valueOf(paraData[i][2]); 
			d[i] = Float.valueOf(paraData[i][3]); 
		}

		SetLineChart.showTuBiao(lineChartView, Arrays.asList(a),  Arrays.asList(b),  Arrays.asList(c), Arrays.asList(d),days);
		
//		piechartView = (PieChartView) findViewById(R.id.pieChartView);

		
//		SetPieChart.drawView(piechartView);
		//饼图的点击监听
//		piechartView.setOnValueTouchListener(new PieChartOnValueTouchListener() {
//			
//			@Override
//			public void onValueTouched(int paramInt, ArcValue paramArcValue) {
//				// TODO Auto-generated method stub
//				paramArcValue.getValue();//这个模块所占的百分比
//			}
//			
//			@Override
//			public void onNothingTouched() {
//				// TODO Auto-generated method stub
//				
//			}
//		});

	}
	String [][] GetParaData()
	{
		sql = "select PH值,溶解氧,高锰酸盐指数,氨氮 from waterdata";
		Cursor cursor_para = sqlite.getReadableDatabase().rawQuery(sql, null);
		int recordNum = GetRecords(cursor_para);
		String[][] paraData = new String[recordNum][4];
		int num = 0;
		do{
			paraData[num][0] = cursor_para.getString(0);
			paraData[num][1] = cursor_para.getString(1);
			paraData[num][2] = cursor_para.getString(2);
			paraData[num][3] = cursor_para.getString(3);
			num++;
		}while(cursor_para.moveToNext());
		Log.i("a", String.valueOf(recordNum));
		return paraData;
	}
	
    int GetRecords(Cursor cursor)
    {
 	   ArrayList<String> Records = new ArrayList<String>(); 
 	   cursor.moveToFirst();
 	   do {
 	   Records.add("该记录ID为:"+cursor.getString(0));
 	   } while(cursor.moveToNext());
 	   cursor.moveToFirst();
 	   return Records.size();
    }
}
