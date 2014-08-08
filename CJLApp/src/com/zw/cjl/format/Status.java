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
	
	public static String orderStatusToString(int zt) {
		String result = "驳回";
		if (0 == zt) {
			result = "未处理";
		} else if (1 == zt){
			result = "已处理";
		}
		return result;
	}
	
	public static String orderStatusToString(String jd) {
		String result = "安全文明";
		if (jd.equals("1")) {
			result = "科目一";
		} 
		else if (jd.equals("2")){
			result = "科目二";
		}
		else if (jd.equals("3")){
			result = "科目三";
		}
		return result;
	}
	
	public static String orderOrderTypeToString(String type) {
		String result = "其他预约";
		if (type.equals("1")) {
			result = "自助预约";
		} 
		else if (type.equals("2")){
			result = "驾校预约";
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
	
	public static String validToString(Integer isVal) {
		String result = "";
		if(isVal==0) {
			result = "[无效]";
		} else if(isVal==1) {
			result = "[有效]";
		} else if(isVal==2) {
			result = "[部份有效]";
		}
		return result;
	}
}
