package com.zw.cjl.server;

public class BaseUrls {
	
	// ҵ��Ա��¼
	public String assistantLogin(String username, String password)
	{
		return "app/loadObj?username=" + username + "&password=" + password;
	}
	
	// ��ȡҵ��Ա��ϸ��Ϣ
	public String assistantDetail(String identity)
	{
		return "app/findAssistantDetail?identity=" + identity;
	}
	
	// ��ȡ����ѧԱ
	public String allStudents(String orgId, String isPolice)
	{
		return "app/findAllStudents?orgId=" + orgId + "&isPolice=" + isPolice;
	}
}
