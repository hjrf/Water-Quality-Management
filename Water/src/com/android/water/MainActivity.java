package com.android.water;

import com.android.config.HttpConf;
import com.android.sqlite.Sqlite;
import com.android.user.Login;
import com.hjr.sqlitemanage.hjr_DatabaseView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		new Sqlite(this,"Water"); 
		super.onCreate(savedInstanceState);//调用Activity构造方法
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Handler handler = new Handler();
        handler.postDelayed(new splashhandler(), 1000);
		
//	    Intent intentTable = new Intent(MainActivity.this,Login.class);
//	    startActivity(intentTable);	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				
				Intent intentDB = new Intent(MainActivity.this,hjr_DatabaseView.class);
		    	startActivity(intentDB);
				
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
	 
	 
	 class splashhandler implements Runnable{
		    public void run() {
		        startActivity(new Intent(getApplication(),Login.class));
		        MainActivity.this.finish();
		    }
		}	 
}


