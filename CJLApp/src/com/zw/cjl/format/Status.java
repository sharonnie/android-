package com.zw.cjl.format;

public class Status {
	
	public static String carStatusToString(String status) {
		String result = "δ֪";
		if (status.equals("1")) {
			result = "����";
		} else {
			result = "ͣ��";
		}
		return result;
	}
	
	public static String carOnlineToString(String status) {
		String result = "����";
		if (status.equals("0")) {
			result = "����";
		} 
		return result;
	}
}
