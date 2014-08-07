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
	
	// 3-1. 获取学员数量
	public static String allStudentNumber(String orgId)
	{
		return "app/findAllStudentsNo?orgId=" + orgId;
	}
	
	// 3-2. 获取所有学员
	public static String allStudents(String orgId, String offset ,String limit)
	{
		return "app/findAllStudents?orgId=" + orgId + "&start=" + offset + "&limit=" + limit;
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
	
	// 6-1. 获取助理考试员数量
	public static String allCoachNumber(String orgId)
	{
		return "app/findAllCoachesNo?orgId=" + orgId;
	}
	
	// 6-2. 获取所有助理考试员
	public static String allCoachs(String orgId, String offset, String limit)
	{
		return "app/findAllCoaches?orgId=" + orgId + "&start=" + offset + "&limit=" + limit;
	}
	
	// 7. 获取助理考试员及关联学员
	public static String coachAndStudents(String identity)
	{
		return "app/findCoachAndStudents?identity=" + identity;
	}
	
	// 8-1. 获取车辆总数
	public static String allCarNumber(String orgId)
	{
		return "app/findAllCarsNo?orgId=" + orgId;
	}
	
	// 8-2. 获取所有车辆
	public static String allCars(String orgId, String offset, String limit)
	{
		return "app/findAllCars?orgId=" + orgId + "&start=" + offset + "&limit=" + limit;
	}
	
	// 9. 获取车辆详细信息
	public static String carDetail(String jxid, String carNo)
	{
		return "app/findCar?jxid=" + jxid + "&car_no=" + carNo;
	}
	
	// 10. 获取车辆GPS信息
	public static String historyTrace(String deviceNo, 
									  String limit)
	{
		return "app/historyTrace?deviceNo=" + deviceNo 
				+ "&limit=" + limit;
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
