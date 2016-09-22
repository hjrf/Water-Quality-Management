package com.android.fragment.chart;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.graphics.Color;
import lecho.lib.hellocharts.model.ArcValue;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.util.Utils;
import lecho.lib.hellocharts.view.PieChartView;

public class ViewPieChart {

	public static void drawView(PieChartView chart) {
		PieChartData data;

		boolean hasLabels = false;
		boolean hasLabelsOutside = false;
		boolean isExploaded = true;//是否有间隙
		boolean hasLabelForSelected = false;

		int numValues = 4;
		
		List<ArcValue> values = new ArrayList<ArcValue>();
		//这里的0.1；0.2；0.3；0.4为各种颜色所占整个的比例
		for (int i = 0; i < numValues; ++i) {
			ArcValue arcValue;
			if(i==0){
				 arcValue = new ArcValue((float) 0.1 , Color.parseColor("#FFAD0E"));
			}else if(1==i){
				 arcValue = new ArcValue((float) 0.2, Color.parseColor("#F98C3A"));
			}else if(2==i){
				
				arcValue = new ArcValue((float) 0.3, Color.parseColor("#FFC600"));
			}else{
				 arcValue = new ArcValue((float) 0.4, Color.parseColor("#9E9E9E"));
			}
			if (isExploaded) {
				//设置间隙的
				arcValue.setArcSpacing(0);
			}

			arcValue.setArcSpacing(0);

			values.add(arcValue);
		}

		data = new PieChartData(values);
		data.setHasLabels(hasLabels);
		data.setHasLabelsOnlyForSelected(hasLabelForSelected);
		data.setHasLabelsOutside(hasLabelsOutside);

		

		chart.setPieChartData(data);
	}

}
