package com.android.systemlayout;

import java.util.ArrayList;
import java.util.List;

import com.android.fragment.syslayout.HomePage;
import com.android.fragment.syslayout.WarnInfoList;
import com.android.http.HttpSelect;
import com.android.personal.Personal;
import com.android.water.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;


public class MainLayout extends FragmentActivity implements
		OnPageChangeListener, OnClickListener
{
	private ViewPager mViewPager;
	private ArrayList<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private String[] mTitles = new String[] { "First Fragment!",
			"Second Fragment!", "Third Fragment!", "Fourth Fragment!" };

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		getActionBar().setDisplayShowHomeEnabled(false);
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		initDatas();

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initDatas()
	{
		int num = 0;
		for (String title : mTitles)
		{
			
//			TabFragment tabFragment = new TabFragment();
//			Bundle args = new Bundle();
//			args.putString("title", title);
//			tabFragment.setArguments(args);
			if(num == 0)
			{
				HomePage homePage = new HomePage();
				mTabs.add(homePage);
			}
			else if(num == 1)
			{
				WarnInfoList warnInfoList = new WarnInfoList();
				mTabs.add(warnInfoList);
			}
			else if(num == 2)
			{
				HttpSelect httpSelect = new HttpSelect();
				mTabs.add(httpSelect);
			}
			else
			{
				Personal personal = new Personal();
				mTabs.add(personal);
			}
			num++;
		}

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{
			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();
	}


	private void initTabIndicator()
	{
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		one.setIconAlpha(1.0f);
	}

	@Override
	public void onPageSelected(int arg0)
	{
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		// Log.e("TAG", "position = " + position + " , positionOffset = "
		// + positionOffset);

		if (positionOffset > 0)
		{
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	@Override
	public void onClick(View v)
	{

		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;

		}

	}

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicator.size(); i++)
		{
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

}