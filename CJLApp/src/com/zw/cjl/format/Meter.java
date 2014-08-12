package com.zw.cjl.format;

import java.text.DecimalFormat;

public class Meter {
	public static String intToString(Integer meters) {
		String result = "";
		if(meters<1000) {
			result = meters+" Ã×";
		} else {
			DecimalFormat df = new DecimalFormat("###,##0.00");
			result = (df.format((float)meters/1000) + " ¹«Àï");
		}
		return result;
	}
}
