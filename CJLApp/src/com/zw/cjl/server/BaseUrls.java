package com.zw.cjl.server;

public class BaseUrls {
	
	// 1. 业务员登录
	public static String assistantLogin(String username, String password)
	{
		return "app/loadObj?type=3&username=" + username + "&password=" + password;
	}
	
	// 2. 获取业务员信息
	public static String assistantDetail(String identity)
	{
		return "app/findAssistantDetail?identity=" + identity;
	}
	
	// 3. 获取所有学员
	public static String allStudents(String orgId, String isPolice)
	{
		return "app/findAllStudents?orgId=" + orgId + "&isPolice=" + isPolice;
	}
	
	// 4. 获取学员测试情况
	public static String studentSituation(String identity)
	{
		return "app/findSituation?identity=" + identity;
	}
	
	// 5. 获取学员测试记录
	public static String studentRecord(String identity, String cityDivision)
	{
		return "app/findRecord?identity=" + identity + "&cityDivision=" + cityDivision;
	}
	
	// 6. 获取所有助理考试员
	public static String allCoachs(String orgId, String isPolice)
	{
		return "app/findAllCoaches?orgId=" + orgId + "&isPolice=" + isPolice;
	}
	
	// 7. 获取助理考试员及关联学员
	public static String coachAndStudents(String identity)
	{
		return "app/findCoachAndStudents?identity=" + identity;
	}
	
	// 8. 获取所有车辆信息
	public static String allCars(String orgId, String isPolice)
	{
		return "app/findAllCars?orgId=" + orgId + "&isPolice=" + isPolice;
	}
	
	// 9. 获取车辆详细信息
	public static String carDetail(String jxid, String carNo)
	{
		return "app/findCar?jxid=" + jxid + "&car_no=" + carNo;
	}
	
	// 10. 获取车辆GPS信息
	public static String historyTrace(String deviceNo, 
									  String startTime, 
									  String endTime)
	{
		return "app/historyTrace?deviceNo=" + deviceNo 
				+ "&startTime=" + startTime 
				+ "&endTime=" + endTime;
	}
	
	// 11. 学员登录
	public static String studentLogin(String identity, String phone)
	{
		return "app/loadObj?type=2&identity=" + identity + "&phone=" + phone;
	}
	
	// 12. 助理考试员登录
	public static String coachLogin(String identity, String phone)
	{
		return "app/loadObj?type=1&identity=" + identity + "&phone=" + phone;
	}
}
