package com.zw.cjl.server;

public class BaseUrls {
	
	// 1. ҵ��Ա��¼
	public static String assistantLogin(String username, String password)
	{
		return "app/loadObj?type=3&username=" + username + "&password=" + password;
	}
	
	// 2. ��ȡҵ��Ա��Ϣ
	public static String assistantDetail(String identity)
	{
		return "app/findAssistantDetail?identity=" + identity;
	}
	
	// 3. ��ȡ����ѧԱ
	public static String allStudents(String orgId, String isPolice)
	{
		return "app/findAllStudents?orgId=" + orgId + "&isPolice=" + isPolice;
	}
	
	// 4. ��ȡѧԱ�������
	public static String studentSituation(String identity)
	{
		return "app/findSituation?identity=" + identity;
	}
	
	// 5. ��ȡѧԱ���Լ�¼
	public static String studentRecord(String identity, String cityDivision)
	{
		return "app/findRecord?identity=" + identity + "&cityDivision=" + cityDivision;
	}
	
	// 6. ��ȡ����������Ա
	public static String allCoachs(String orgId, String isPolice)
	{
		return "app/findAllCoaches?orgId=" + orgId + "&isPolice=" + isPolice;
	}
	
	// 7. ��ȡ������Ա������ѧԱ
	public static String coachAndStudents(String identity)
	{
		return "app/findCoachAndStudents?identity=" + identity;
	}
	
	// 8. ��ȡ���г�����Ϣ
	public static String allCars(String orgId, String isPolice)
	{
		return "app/findAllCars?orgId=" + orgId + "&isPolice=" + isPolice;
	}
	
	// 9. ��ȡ������ϸ��Ϣ
	public static String carDetail(String jxid, String carNo)
	{
		return "app/findCar?jxid=" + jxid + "&car_no=" + carNo;
	}
	
	// 10. ��ȡ����GPS��Ϣ
	public static String historyTrace(String deviceNo, 
									  String startTime, 
									  String endTime)
	{
		return "app/historyTrace?deviceNo=" + deviceNo 
				+ "&startTime=" + startTime 
				+ "&endTime=" + endTime;
	}
	
	// 11. ѧԱ��¼
	public static String studentLogin(String identity, String phone)
	{
		return "app/loadObj?type=2&identity=" + identity + "&phone=" + phone;
	}
	
	// 12. ������Ա��¼
	public static String coachLogin(String identity, String phone)
	{
		return "app/loadObj?type=1&identity=" + identity + "&phone=" + phone;
	}
}
