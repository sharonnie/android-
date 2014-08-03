package com.zw.cjl.format;

public class Status {
	
	public static String carStatusToString(String status) {
		String result = "Î´Öª";
		if (status.equals("1")) {
			result = "ÔÚÏß";
		}
		return result;
	}
}
