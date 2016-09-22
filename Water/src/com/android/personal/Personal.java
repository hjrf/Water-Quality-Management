package com.android.personal;

import com.android.water.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class Personal extends Fragment implements OnClickListener{
	private static final String FLAG = "Personal";
	View view = null;
//	Button button1 = null;
	TextView textView1 = null;
	TextView textView2 = null;
	TextView textView3 = null;
	TextView textView4 = null;
	TextView textView5 = null;
	

	  @Override
    public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	Bundle savedInstanceState) {
		GetCurrentUser gcu = new GetCurrentUser();
	// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_personal,container,false);
		FindAllView();
		SharedPreferences preferences = getActivity().getSharedPreferences("userLoginInfo",0);
		String userName = preferences.getString("userName", "");
		String userLevel = preferences.getString("userLevel", "");
		textView3.setText(userName);
//		textView1.setText(userLevel);
		return view;
	}	
	private void FindAllView() {
//		listView1 = (ListView)findViewById(R.id.listView1);				
//		editText1 = (EditText) this.findViewById(R.id.editText1);			
//		button1 = (Button) this.findViewById(R.id.button1);			
//		textView1 = (TextView) this.findViewById(R.id.textView1);
//		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
//	 	checkBox1 = (CheckBox) findViewById(R.id.checkBox1);  
	
//		checkBox1.setChecked(true);  
//		button1.setEnabled(false);		
	
//		ListView1.setOnItemClickListener(this);	
//		button1.setOnClickListener(this);	
//		checkBoxs.add(checkBox1);  
		textView1 = (TextView)view.findViewById(R.id.personal_setting);	
		textView1.setClickable(true);
		textView1.setOnClickListener(this);	
		
		textView2 = (TextView)view.findViewById(R.id.txt_album);	
		textView2.setClickable(true);
		textView2.setOnClickListener(this);	
		
		textView3 = (TextView)view.findViewById(R.id.photo);
		
	}
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.personal_setting:
			Intent intentTable = new Intent(getActivity(),Setting.class);
	    	startActivity(intentTable);
		//Log.i(FLAG,"打印的信息！"); 
			break;
		case R.id.txt_album:
			Intent intentTable1 = new Intent(getActivity(),PersonalInfo.class);
	    	startActivity(intentTable1);
		default:
		//Toast.makeText(this, "提示信息！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
