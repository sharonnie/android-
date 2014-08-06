package com.zw.cjl.format;

public class Status {
	
	public static String carStatusToString(String status) {
		String result = "未知";
		if (status.equals("1")) {
			result = "启用";
		} else {
			result = "停用";
		}
		return result;
	}
	
	public static String carOnlineToString(String status) {
		String result = "在线";
		if (status.equals("0")) {
			result = "离线";
		} 
		return result;
	}
}
