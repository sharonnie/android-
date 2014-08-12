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
		if(val==null) {return "0 ��";}
		String result = "";
		if(val<60) {
			result = val + " ��";
		} else if(val>60 && val<3600) {
			Integer m, s;
			m = val/60; s = val%60;
			result = m + "��" + s + "��";
		} else {
			Integer h, m, s;
			h = val/3600; //Сʱ
			Integer temp = val%3600; //����Сʱ������
			m = temp/60; //����
			s = temp%60; //��
			result = h + "ʱ" + m + "��" + s + "��";
		}
		return result;
	}
}
