package com.android.fragment.syslayout;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.chart.LineChart;
import com.android.evaluate.EvaluateAdd;
import com.android.fragment.chart.ChartTabManage;
import com.android.http.ReceiveTest;
import com.android.table.Standard;
import com.android.table.User;
import com.android.table.WaterData;
import com.android.water.R;
import com.hjr.sqlitemanage.hjr_DatabaseView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HomePage extends Fragment{
	   View rootView = null;
	   String userLevel = null;
	   @Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	   
			SharedPreferences preferences = getActivity().getSharedPreferences("userLoginInfo",0);
			userLevel = preferences.getString("userLevel", "");
	        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
	        GridView gridview = (GridView)view.findViewById(R.id.gridView1);
	        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
	        for(int i = 0;i < 10;i++)
	        {
	            HashMap<String, Object> map = new HashMap<String, Object>();
	            if(i == 0)
	            {
		            map.put("ItemImage", R.drawable.grid14);
		            map.put("ItemText", "数据库");
	            }
	            else if(i == 1)
	            {
		            map.put("ItemImage", R.drawable.grid10);
		            map.put("ItemText", "用户管理");
	            }
	            else if(i == 2)
	            {
	            	map.put("ItemImage", R.drawable.grid13);
		            map.put("ItemText", "水质数据");
	            }
	            else if(i == 3)
	            {
	            	map.put("ItemImage", R.drawable.grid9);
		            map.put("ItemText", "评价标准");
	            }
	            else if(i == 4)
	            {
	            	map.put("ItemImage", R.drawable.grid11);
		            map.put("ItemText", "趋势图");
	            }
	            else if(i == 5)
	            {
	            	map.put("ItemImage", R.drawable.grid12);
		            map.put("ItemText", "水质评价");
	            }
	            else
	            {
		            map.put("ItemImage", R.drawable.grid8);
		            map.put("ItemText", "待命名"+i);
	            }
	            meumList.add(map);
	        }
	        SimpleAdapter saItem = new SimpleAdapter(this.getActivity().getApplicationContext(),
	                  meumList, //数据源
	                  R.layout.fragment_homepage_child, //xml实现
	                  new String[]{"ItemImage","ItemText"}, //对应map的Key
	                  new int[]{R.id.imageView1,R.id.textView1});  //对应R的Id
	  
	                //添加Item到网格中
	                gridview.setAdapter(saItem);
	                //添加点击事件
	                gridview.setOnItemClickListener(
	                    new OnItemClickListener()
	                    {
	                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
	                        {
	                            int index=arg2;//
	                            if(index == 0)
	                            {
	                            	Intent intentTable = new Intent(getActivity(),hjr_DatabaseView.class);
	                    	    	startActivity(intentTable);
	                            }
	                            else if(index == 1)
	                            {
	                            	if(!"管理用户".equals(userLevel.toString().trim()))
	                            	{
	                            		Toast.makeText(getActivity().getApplicationContext(), "权限不足，需要管理用户级别"+index, 0).show();
	                            		return;
	                            	}
	                            	Intent intentTable = new Intent(getActivity(),User.class);
	                    	    	startActivity(intentTable);
	                            }
	                            else if(index == 2)
	                            {
	                            	Intent intentTable = new Intent(getActivity(),WaterData.class);
	                    	    	startActivity(intentTable);
	                            }
	                            else if(index == 3)
	                            {
	                            	Intent intentTable = new Intent(getActivity(),Standard.class);
	                    	    	startActivity(intentTable);
	                            }
	                            else if(index == 4)
	                            {
	                            	Intent intentTable = new Intent(getActivity(),LineChart.class);
	                    	    	startActivity(intentTable);
	                            }
	                            else if(index == 5)
	                            {
	                            	Intent intentTable = new Intent(getActivity(),EvaluateAdd.class);
	                    	    	startActivity(intentTable);
	                            }
	                         
	                            Toast.makeText(getActivity().getApplicationContext(), "你按下了选项："+index, 0).show();
	                            //Toast用于向用户显示一些帮助/提示
	                        }
	                    }
	                );
					return view;
	    }
	
}
