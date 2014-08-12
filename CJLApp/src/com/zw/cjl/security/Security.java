package com.zw.cjl.security;

import java.io.UnsupportedEncodingException;

import android.util.Base64;


public class Security {
	//MessageDigest md = MessageDigest.getInstance("SHA");
	
	public static String strEncode (String str)
	{
		try {
			byte[] b_str = str.getBytes("UTF-8");	
			byte[] c_str = Base64.encode(b_str, Base64.URL_SAFE);
			for (int i=0; i<c_str.length; i++)
			{
				c_str[i] += 3;
			}
			return "Z" + new String(c_str);
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public static String strDecode (String str)
	{
		String sub = str.substring(1);
		
		byte[] bt;
		try {
			bt = sub.getBytes("UTF-8");
			for (int i=0; i<bt.length; i++)
			{
				bt[i] -= 3;
			}
			byte[] ct = Base64.decode(bt, Base64.URL_SAFE);
			return new String(ct);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
