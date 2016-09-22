package com.android.http;

import com.android.http.CircleMenuLayout.OnMenuItemClickListener;
import com.android.water.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HttpSelect extends Fragment
{
	private View view;
	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "水质数据 ", "评价标准", "文档下载","待命名","待命名","待命名"};
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
				Bundle savedInstanceState) {  
		        // TODO Auto-generated method stub 	
			 	view = inflater.inflate(R.layout.http_child,container,false); 
			 	//http_child//自已切换布局文件看效果
			 	InitView();
			 	return view;
		 }  
	protected void InitView()
	{
		mCircleMenuLayout = (CircleMenuLayout)view.findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void itemClick(View view, int pos)
			{
				if(mItemTexts[pos].toString().trim().equals("评价标准"))
				{
			    	Intent intentTable = new Intent(getActivity(),ReceiveStandard.class);
			    	startActivity(intentTable);
				}
				else if(mItemTexts[pos].toString().trim().equals("水质数据"))
				{
					Intent intentTable = new Intent(getActivity(),ReceiveWaterData.class);
			    	startActivity(intentTable);
				}
				else if(mItemTexts[pos].toString().trim().equals("文档下载"))
				{
					Intent intentTable = new Intent(getActivity(),ReceiveDown.class);
			    	startActivity(intentTable);
				}
				Toast.makeText(getActivity(), mItemTexts[pos],
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(getActivity(),
						"你可以选择一种要传输的数据！",
						Toast.LENGTH_SHORT).show();				
			}
		});
		
	}

}

