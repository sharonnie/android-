package com.zw.cjl.server;

public class BaseUrls {
	
	// ҵ��Ա��¼
	public static String assistantLogin(String username, String password)
	{
		return "app/loadObj?type=3&username=" + username + "&password=" + password;
	}
	
	// ��ȡҵ��Ա��ϸ��Ϣ
	public static String assistantDetail(String identity)
	{
		return "app/findAssistantDetail?identity=" + identity;
	}
	
	// ��ȡ����ѧԱ
	public static String allStudents(String orgId, String isPolice)
	{
		return "app/findAllStudents?orgId=" + orgId + "&isPolice=" + isPolice;
	}
}
