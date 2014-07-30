package com.zw.cjl.server;

public class MobileUrls {
	private static final String ip = "http://183.224.79.167/";
	
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
}
