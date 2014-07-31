package com.zw.cjl.server;

public class TeleUrls {
	//private static final String ip = "http://220.163.43.27:12080/";
	private static final String ip = "http://192.168.1.117:8888/platform/";
	
	public static String assistantLogin(String username, String password)
	{
		return ip + BaseUrls.assistantLogin(username, password);
	}
	
	public static String assistantDetail(String identity)
	{
		return ip + BaseUrls.assistantDetail(identity);
	}
	
	public static String allStudents(String orgId, String isPolice)
	{
		return ip + BaseUrls.allStudents(orgId, isPolice);
	}
	
	public static String studentLogin(String username, String password)
	{
		return ip + BaseUrls.studentLogin(username, password);
	}
	
	public static String coachLogin(String username, String password)
	{
		return ip + BaseUrls.coachLogin(username, password);
	}
}
