package com.android.personal;

import com.android.water.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class GetCurrentUser extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.evaluate_select);
	     Log.i("123",GetUserName());
	 }
	
	 public String GetUserName()
	{
		SharedPreferences preferences;
		preferences =this.getSharedPreferences("userLoginInfo",MODE_PRIVATE);
		String userName = preferences.getString("userName", "");
		return userName;
	}
}
