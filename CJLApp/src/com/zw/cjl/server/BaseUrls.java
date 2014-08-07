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
	
	// 3-1. ��ȡѧԱ����
	public static String allStudentNumber(String orgId)
	{
		return "app/findAllStudentsNo?orgId=" + orgId;
	}
	
	// 3-2. ��ȡ����ѧԱ
	public static String allStudents(String orgId, String offset ,String limit)
	{
		return "app/findAllStudents?orgId=" + orgId + "&start=" + offset + "&limit=" + limit;
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
	
	// 6-1. ��ȡ������Ա����
	public static String allCoachNumber(String orgId)
	{
		return "app/findAllCoachesNo?orgId=" + orgId;
	}
	
	// 6-2. ��ȡ����������Ա
	public static String allCoachs(String orgId, String offset, String limit)
	{
		return "app/findAllCoaches?orgId=" + orgId + "&start=" + offset + "&limit=" + limit;
	}
	
	// 7. ��ȡ������Ա������ѧԱ
	public static String coachAndStudents(String identity)
	{
		return "app/findCoachAndStudents?identity=" + identity;
	}
	
	// 8-1. ��ȡ��������
	public static String allCarNumber(String orgId)
	{
		return "app/findAllCarsNo?orgId=" + orgId;
	}
	
	// 8-2. ��ȡ���г���
	public static String allCars(String orgId, String offset, String limit)
	{
		return "app/findAllCars?orgId=" + orgId + "&start=" + offset + "&limit=" + limit;
	}
	
	// 9. ��ȡ������ϸ��Ϣ
	public static String carDetail(String jxid, String carNo)
	{
		return "app/findCar?jxid=" + jxid + "&car_no=" + carNo;
	}
	
	// 10. ��ȡ����GPS��Ϣ
	public static String historyTrace(String deviceNo, 
									  String limit)
	{
		return "app/historyTrace?deviceNo=" + deviceNo 
				+ "&limit=" + limit;
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
