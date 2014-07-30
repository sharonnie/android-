package com.zw.cjl.server;

public class BaseUrls {
	
	// 业务员登录
	public static String assistantLogin(String username, String password)
	{
		return "app/loadObj?type=3&username=" + username + "&password=" + password;
	}
	
	// 获取业务员详细信息
	public static String assistantDetail(String identity)
	{
		return "app/findAssistantDetail?identity=" + identity;
	}
	
	// 获取所有学员
	public static String allStudents(String orgId, String isPolice)
	{
		return "app/findAllStudents?orgId=" + orgId + "&isPolice=" + isPolice;
	}
}
