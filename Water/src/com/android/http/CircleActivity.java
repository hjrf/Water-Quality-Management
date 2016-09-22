package com.android.http;

import com.android.http.CircleMenuLayout.OnMenuItemClickListener;
import com.android.water.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CircleActivity extends Activity
{
	HttpSelect httpSelect = new HttpSelect();
	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "安全中心 ", "特色服务", "投资理财",
			"转账汇款", "我的账户", "信用卡" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//自已切换布局文件看效果
		setContentView(R.layout.http_child);
//		setContentView(R.layout.activity_main);

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{

				Toast.makeText(CircleActivity.this, mItemTexts[pos],
						Toast.LENGTH_SHORT).show();

			}
			
			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(CircleActivity.this,
						"you can do something just like ccb  ",
						Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

}

