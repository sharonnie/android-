package com.zw.cjl.format;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
	
	@SuppressLint("SimpleDateFormat")
	public static String longToString(long time) {
		String result = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result = sdf.format(cal.getTime());
		return result;
	}
	
	public static String buildTimes(Integer val) {
		if(val==null) {return "0 秒";}
		String result = "";
		if(val<60) {
			result = val + " 秒";
		} else if(val>60 && val<3600) {
			Integer m, s;
			m = val/60; s = val%60;
			result = m + "分" + s + "秒";
		} else {
			Integer h, m, s;
			h = val/3600; //小时
			Integer temp = val%3600; //除掉小时的余数
			m = temp/60; //分钟
			s = temp%60; //秒
			result = h + "时" + m + "分" + s + "秒";
		}
		return result;
	}
}
