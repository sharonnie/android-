package com.zw.cjl.format;

public class Status {
	
	public static String carStatusToString(String status) {
		String result = "Î´Öª";
		if (status.equals("1")) {
			result = "ÆôÓÃ";
		} else {
			result = "Í£ÓÃ";
		}
		return result;
	}
}
