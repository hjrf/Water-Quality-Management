package com.android.templet;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public class UIRefresh extends Activity{

	Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                update();
                break;
            }
            super.handleMessage(msg);
        }
        void update() {
        	//UI刷新
        }
    };
    
    TimerTask task = new TimerTask(){   
	    public void run(){   
	    	//operate
	    	Message message = new Message();
	        message.what = 1;
	        handler.sendMessage(message);
	    }   
	};   
	void Delay()
	{
		Timer timer = new Timer();
//		timer.schedule(task,xxx,xxx);//xxx之后，xxx间隔
	}
	
}
