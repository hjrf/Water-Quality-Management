package com.android.templet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Move extends Activity {
	
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);//调用Activity构造方法
	    	Intent intentTable = new Intent(Move.this,Move.class);
	    	startActivity(intentTable);
	    }
}
