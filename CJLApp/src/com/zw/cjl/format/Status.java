package com.zw.cjl.format;

public class Status {
	
	public static String carStatusToString(String status) {
		String result = "δ֪";
		if (status.equals("1")) {
			result = "����";
		}
		return result;
	}
}
