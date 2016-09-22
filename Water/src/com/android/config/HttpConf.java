package com.android.config;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

public class HttpConf extends Activity{
 //static public String url ="http://172.26.174.1/water/http.php?"; 
	public  String ip ="http://172.27.35.1:8080/Water/android/"; 
	public  String url ="http://172.27.35.1:8080/Water/android/"; 
	public HttpConf(Context context)
	{
//		ip = getIp();
		url = getUrl(context);
	}
//	public String getIp() {
//		String ip = GetServerIP();
//		return ip;
//	}
//	
	public String getUrl(Context context) {
		String url = GetServerIP(context);
		return "http://"+url+":8080/Water/android/";
	}
//
//	String GetWifiIP(Context context)//本机ip
//	{
//		WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
//		WifiInfo wifiinfo = wifiManager.getConnectionInfo();
//		int ipAddress = wifiinfo.getIpAddress();
//		String ip = intToIp(ipAddress);
//		return ip;
//	}
//	
	String GetServerIP(Context context)//服务器ip
	{
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcpinfo = wifiManager.getDhcpInfo();
		String serverIPAddress = intToIp(dhcpinfo.serverAddress);
		return serverIPAddress;
	}
	
	String intToIp(int ip)
	{
		return (ip&0xff)+"."+((ip>>8)&0xff)+"."+((ip>>16)&0xff)+"."+((ip>>24)&0xff);
	}
}
