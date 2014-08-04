package com.zw.cjl.thread;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zw.cjl.dto.Car;
import com.zw.cjl.dto.Database;
import com.zw.cjl.format.Status;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.network.HttpRequest;

public class HttpGetThread implements Runnable {
	private HttpGetType mType = HttpGetType.GET_TYPE_INVALID;
	private String mArg0 = null;
	private String mArg1 = null;
	private Handler mHandler = null;
	private Database _db = null;
	
	
	public HttpGetThread(HttpGetType type, 
						Handler handler,
						Database db,
						String arg0,
						String arg1)
	{
		mType = type;
		mHandler = handler;
		mArg0 = arg0;
		mArg1 = arg1;
		_db = db;
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
		switch (mType)
		{
		case GET_ALL_CARS:
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(resultMsg);
				JSONArray jsonArray = new JSONArray(jsonObj.getString("cars"));
				List<Car> carList = new ArrayList<Car>();
				
				for (int i=0; i<jsonArray.length(); i++)
				{
					JSONObject e = jsonArray.getJSONObject(i);
					Car c = new Car(e.getInt("id"),
									e.getInt("orgId"),
									e.getInt("schoolId"),
									e.getString("ownerName"),
									e.getString("schoolName"),
									e.getString("carKind"),
									Status.carStatusToString(e.getString("status")),
									e.getString("cityDivision"),
									e.getString("simNo"),
									e.getString("schoolCode"),
									e.getString("carType"),
									e.getString("carNo"),
									e.getString("division"),
									e.getString("deviceNo"),
									e.getString("ownerPhone"));
					
					carList.add(c);
				}
				_db.insertCars(carList);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			break;
			
		default:
			break;
		}
		
		
		Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("resultMsg", resultMsg);
        bundle.putBoolean("hasError", hasError);
        message.setData(bundle);
        mHandler.sendMessage(message);
	}

}
