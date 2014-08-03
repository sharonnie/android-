package com.zw.cjl.network;

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
	static boolean userTeleNetwork = true;
	static int loginType = 0;
	
	// 登录
	public static String coachLogin(String username, String password) {
		loginType = 1;
		return login(username, password);
	}
	
	public static String studentLogin(String username, String password) {
		loginType = 2;
		return login(username, password);
	}
	
	public static String assistantLogin(String username, String password) {
		loginType = 3;
		return login(username, password);
	}
	
	public static String assistantDetail(String identity) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = null;
			
			if (userTeleNetwork) {
				url = new URL(TeleUrls.assistantDetail(identity));
			}
			else {
				url = new URL(MobileUrls.assistantDetail(identity));
			}
			
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
			result = "网络异常";
		} catch(Exception e) {
			result = "请求异常";
		}
		
		return result;
	}
	
	public static String allStudents(String orgId) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = null;
			
			if (userTeleNetwork) {
				url = new URL(TeleUrls.allStudents(orgId));
			}
			else {
				url = new URL(MobileUrls.allStudents(orgId));
			}
			
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
			result = "网络异常";
		} catch(Exception e) {
			result = "请求异常";
		}
		
		return result;
	}
	
	public static String allCars(String orgId) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = null;
			
			if (userTeleNetwork) {
				url = new URL(TeleUrls.allCars(orgId));
			}
			else {
				url = new URL(MobileUrls.allCars(orgId));
			}
			
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
			result = "网络异常";
		} catch(Exception e) {
			result = "请求异常";
		}
		
		return result;
	}
	
	private static String login(String username, String password) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = null;
			
			// 助理考试员
			if (1 == loginType)
			{
				url = new URL(TeleUrls.coachLogin(username, password));
			}
			// 学员
			else if (2 == loginType)
			{
				url = new URL(TeleUrls.studentLogin(username, password));
			}
			// 业务员
			else if (3 == loginType)
			{
				url = new URL(TeleUrls.assistantLogin(username, password));
			}
			
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
				URL url = null;
				
				// 助理考试员
				if (1 == loginType)
				{
					url = new URL(MobileUrls.coachLogin(username, password));
				}
				// 学员
				else if (2 == loginType)
				{
					url = new URL(MobileUrls.studentLogin(username, password));
				}
				// 业务员
				else if (3 == loginType)
				{
					url = new URL(MobileUrls.assistantLogin(username, password));
				}
				
				
				conn = (HttpURLConnection)url.openConnection();  
				conn.setConnectTimeout(4000);  
				conn.setRequestMethod("GET");
				conn.connect();
				
				result = changeInputStream(conn.getInputStream(), "UTF-8");
				userTeleNetwork = false;
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
