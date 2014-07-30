package com.ze.cjl.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.zw.cjl.server.MobileUrls;
import com.zw.cjl.server.TeleUrls;

public class HttpRequest {
	static boolean userTeleNetwork = false;
	
	public static String assistantLogin(String username, String password) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(MobileUrls.assistantLogin(username, password));
			conn = (HttpURLConnection)url.openConnection();  
			conn.setConnectTimeout(4000);  
			conn.setRequestMethod("GET");
			conn.connect();
			
			result = changeInputStream(conn.getInputStream(), "UTF-8");
		} catch (MalformedURLException e) {
			result = "连接地址异常";
		} catch (ProtocolException e) {
			result = "传输协议异常";
		} catch (IOException e) {
			
			try {
				URL url = new URL(TeleUrls.assistantLogin(username, password));
				conn = (HttpURLConnection)url.openConnection();  
				conn.setConnectTimeout(4000);  
				conn.setRequestMethod("GET");
				conn.connect();
				result = changeInputStream(conn.getInputStream(), "UTF-8");
				userTeleNetwork = true;
			} catch (IOException e1) {
				result = "网络异常";
			}
			
		} catch(Exception e) {
			result = "请求异常";
		}
		
		return result;
	}
	
	private static String changeInputStream(InputStream inputStream, String encode) {
		String result = "";
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte [] datas = new byte[1024];
			int len = 0;
			if(inputStream!=null) {
				while((len=inputStream.read(datas)) !=-1) {
					outputStream.write(datas, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
