package com.zw.cjl.server;

public class MobileUrls {
	private static final String ip = "http://183.224.79.167/";
	
	// 业务员
	public static String assistantLogin(String username, String password)
	{
		return ip + BaseUrls.assistantLogin(username, password);
	}
	
	public static String assistantDetail(String identity)
	{
		return ip + BaseUrls.assistantDetail(identity);
	}
	
	// 学员
	public static String allStudents(String orgId, String isPolice)
	{
		return ip + BaseUrls.allStudents(orgId, isPolice);
	}
	
	public static String studentLogin(String username, String password)
	{
		return ip + BaseUrls.studentLogin(username, password);
	}
	
	public static String studentRecord(String identity, String cityDivision)
	{
		return ip + BaseUrls.studentRecord(identity, cityDivision);
	}
	
	public static String studentSituation(String identity)
	{
		return ip + BaseUrls.studentSituation(identity);
	}
	
	// 助理考试员
	public static String coachLogin(String username, String password)
	{
		return ip + BaseUrls.coachLogin(username, password);
	}
	
	public static String allCoachs(String orgId, String isPolice)
	{
		return ip + BaseUrls.allCoachs(orgId, isPolice);
	}
	
	public static String coachAndStudents(String identity)
	{
		return ip + BaseUrls.coachAndStudents(identity);
	}
	
	// 车辆
	public static String allCars(String orgId, String isPolice)
	{
		return ip + BaseUrls.allCars(orgId, isPolice);
	}
	
	public static String carDetail(String jxid, String carNo)
	{
		return ip + BaseUrls.carDetail(jxid, carNo);
	}
	
	public static String historyTrace(String deviceNo,
								  	  String startTime,
								  	  String endTime)
	{
		return ip + BaseUrls.historyTrace(deviceNo, startTime, endTime);
	}
}
