package com.android.fragment.chart;

import com.android.water.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHistogram extends Fragment{
	 private ViewHistogram viewHistogram;  
	 View view = null;
	 @Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
			Bundle savedInstanceState) {  
	        // TODO Auto-generated method stub 	
		 	view = inflater.inflate(R.layout.fragment_histogram,container,false);  
		 	initView();
		 	return view;
	 }  
	 private void initView(){  
		 	viewHistogram = (ViewHistogram)view.findViewById(R.id.viewHistogram);
		 	viewHistogram.setAxisX(600, 9);  
		 	viewHistogram.setAxisY(500,7);  
	  
	        int columnInfo[][] = new int[][]{{600, Color.BLUE},{500, Color.GREEN},{400, Color.RED},{300, Color.BLUE},  
	                {500, Color.YELLOW},{300, Color.LTGRAY},{200, Color.BLUE}};  
	        viewHistogram.setColumnInfo(columnInfo);  
	 }  
}
