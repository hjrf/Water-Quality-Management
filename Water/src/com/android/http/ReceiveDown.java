package com.android.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.config.HttpConf;
import com.android.water.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReceiveDown extends Activity {  
      
	String parameter = "WaterData.xls";
    private Button downFileBtn;  
    private Button downMP3Btn;  
    HttpConf hc = null;

    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.receive_down);  
        hc = new HttpConf(this); 
        downMP3Btn=(Button)this.findViewById(R.id.button1);  
        downMP3Btn.setOnClickListener(new DownMP3ClickListener());   
    }  
      
    /** 
     *  
     * @Project: Android_MyDownload 
     * @Desciption: 只能读取文本文件，读取mp3文件会出现内存溢出现象 
     * @Author: LinYiSong 
     * @Date: 2011-3-25~2011-3-25 
     */  
    class DownFileClickListener implements OnClickListener{  
        @Override  
        public void onClick(View v) {  
            String urlStr="http://172.27.35.1:8080/download/down.txt";  
            try {  
                /* 
                 * 通过URL取得HttpURLConnection 
                 * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置 
                 * <uses-permission android:name="android.permission.INTERNET" /> 
                 */  
                URL url=new URL(urlStr);  
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();  
                //取得inputStream，并进行读取  
                InputStream input=conn.getInputStream();  
                BufferedReader in=new BufferedReader(new InputStreamReader(input));  
                String line=null;  
                StringBuffer sb=new StringBuffer();  
                while((line=in.readLine())!=null){  
                    sb.append(line);  
                }  
                System.out.println(sb.toString());  
                  
            } catch (MalformedURLException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    /** 
     *  
     * @Project: Android_MyDownload 
     * @Desciption: 读取任意文件，并将文件保存到手机SDCard 
     * @Author: LinYiSong 
     * @Date: 2011-3-25~2011-3-25 
     */  
    
    private InputStream GetInputStream(String url_str) throws Exception{

    	URL url = new URL(url_str);
    	// 创建连接
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setReadTimeout(3000);
    	conn.setConnectTimeout(3000);
    	conn.connect();

    	// 获取文件大小
//    	length = conn.getContentLength();
    	// 创建输入流
    	InputStream is = conn.getInputStream();

    	return is;
    	}
    
    class DownMP3ClickListener implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  

        	new ThreadDown().start();
        }    
    }  
    class ThreadDown extends Thread{
    	   public void run(){
    	   //你要实现的代码   
//               String urlStr= "http://172.23.64.1:8080/test.txt";  
//               String fileName="test.txt";
    		   String ip = hc.ip;
               String urlStr= "http://"+ip+":8080/WaterData.xls";  
               String fileName="WaterData.xls";
               InputStream input = null;
               OutputStream output = null;  
               try {  
                   /* 
                    * 通过URL取得HttpURLConnection 
                    * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置 
                    * <uses-permission android:name="android.permission.INTERNET" /> 
                    */  
               	Log.i("aaa", "aa");
               	if(GetInputStream(urlStr) != null)
               	{
               		input = GetInputStream(urlStr);
               	}
                   Log.i("aaa", "没接收到");
                   //取得inputStream，并将流中的信息写入SDCard  
                     
                   /* 
                    * 写前准备 
                    * 1.在AndroidMainfest.xml中进行权限配置 
                    * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 
                    * 取得写入SDCard的权限 
                    * 2.取得SDCard的路径： Environment.getExternalStorageDirectory() 
                    * 3.检查要保存的文件上是否已经存在 
                    * 4.不存在，新建文件夹，新建文件 
                    * 5.将input流中的信息写入SDCard 
                    * 6.关闭流 
                    */  
                   String pathName = "/mnt/sdcard/water/"+fileName;//文件存储路径  
                   Log.i("aaa", pathName);
                   File file=new File(pathName);  
                       new File("/mnt/sdcard/water").mkdirs();//新建文件夹  
                       file.createNewFile();//新建文件  
                       output=new FileOutputStream(file);  
                       //读取大文件  
                       byte[] buffer=new byte[1024];  
//                       String info ="侯金瑞";
                       while(input.read(buffer)!=-1){  //读取输入流写入到输出流
//                         output.write(info.getBytes());  
                       	output.write(buffer);  
                       }  
                       output.flush();  
//                   }  
               } catch (Exception e) {  
     
               }
    		   
    	   }
    }
}  