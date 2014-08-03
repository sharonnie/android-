package com.zw.cjl.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.network.HttpRequest;

public class HttpGetThread implements Runnable {
	HttpGetType mType = HttpGetType.GET_TYPE_INVALID;
	String mArg0 = null;
	String mArg1 = null;
	Handler mHandler = null;
	
	
	public HttpGetThread(HttpGetType type, 
						Handler handler,
						String arg0,
						String arg1)
	{
		mType = type;
		mHandler = handler;
		mArg0 = arg0;
		mArg1 = arg1;
	}
	
	@Override
	public void run() {
		
		String result = null;
		switch (mType)
		{
		case GET_ASSISTANT_DETAIL:
			result = HttpRequest.assistantDetail(mArg0);
			break;
			
		case GET_ALL_STUDENTS:
			result = HttpRequest.allStudents(mArg0);
			break;
		case GET_ALL_CARS:
			result = HttpRequest.allCars(mArg0);
			break;
		default:
			break;
		}
		
		boolean hasError = false; 
		String resultMsg = "";
		
		// resultCode=1时表示成功
		if (0 >= result.indexOf("resultCode")) {
			hasError = true; 
			resultMsg = result;
		} 
		else 
		{
			JSONObject jsonObj = null;
			Integer resultCode = 0;
			try {
				jsonObj = new JSONObject(result);
				resultCode = jsonObj.getInt("resultCode");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			if(1 == resultCode) {
				resultMsg = jsonObj.toString();
			} else {
				hasError = true; 
				try {
					resultMsg = jsonObj.getString("resultMsg");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("resultMsg", resultMsg);
        bundle.putBoolean("hasError", hasError);
        message.setData(bundle);
        mHandler.sendMessage(message);
	}

}
