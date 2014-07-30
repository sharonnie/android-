package com.zw.cjl.server;

public class TeleUrls {
	private static final String ip = "http://220.163.43.27:12080/";
	BaseUrls base;
	
	public String assistantLogin(String username, String password)
	{
		return ip + base.assistantLogin(username, password);
	}
	
	public String assistantDetail(String identity)
	{
		return ip + base.assistantDetail(identity);
	}
	
	public String allStudents(String orgId, String isPolice)
	{
		return ip + base.allStudents(orgId, isPolice);
	}
	
}
