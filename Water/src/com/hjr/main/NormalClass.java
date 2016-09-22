package com.hjr.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NormalClass extends Activity {
	
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);//调用Activity构造方法
	    	Intent intentTable = new Intent(NormalClass.this,NormalClass.class);
	    	startActivity(intentTable);
	    }
}
