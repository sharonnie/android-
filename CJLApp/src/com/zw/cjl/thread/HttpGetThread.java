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
import com.zw.cjl.dto.Coach;
import com.zw.cjl.dto.Database;
import com.zw.cjl.dto.Student;
import com.zw.cjl.format.Status;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.network.HttpRequest;

public class HttpGetThread implements Runnable {
	private final String EVERY_TIME_GET_LIMIT = "100";
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
		_db = db;
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
			
		case GET_ALL_STUDENT_NUM:
			result = HttpRequest.allStudentNum(mArg0);
			break;
			
		case GET_ALL_STUDENTS:
			result = HttpRequest.allStudents(mArg0, mArg1, EVERY_TIME_GET_LIMIT);
			break;
			
		case GET_ALL_COACH_NUM:
			result = HttpRequest.allCoachNum(mArg0);
			break;
			
		case GET_ALL_COACHS:
			result = HttpRequest.allCoachs(mArg0, mArg1, EVERY_TIME_GET_LIMIT);
			break;
			
		case GET_ALL_CAR_NUM:
			result = HttpRequest.allCarNum(mArg0);
			break;
			
		case GET_ALL_CARS:
			result = HttpRequest.allCars(mArg0, mArg1, EVERY_TIME_GET_LIMIT);
			break;
		
		case GET_CAR_DETAIL:
			result = HttpRequest.carDetail(mArg0, mArg1);
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
		
		JSONObject jsonObj = null;
		
		switch (mType)
		{
		case GET_ALL_CARS:
			try {
				jsonObj = new JSONObject(resultMsg);
				JSONArray jsonArray = new JSONArray(jsonObj.getString("cars"));
				List<Car> carList = new ArrayList<Car>();
				int count = jsonArray.length();
				
				for (int i=0; i<count; i++)
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
				
				resultMsg="{\"offset\":"
						+mArg1
						+",\"count\":"
						+count
						+",\"total\":"
						+_db.getCarCount()
						+"}";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			break;
		case GET_ALL_STUDENTS:
			try {
				jsonObj = new JSONObject(resultMsg);
				JSONArray jsonArray = new JSONArray(jsonObj.getString("students"));
				List<Student> studentList = new ArrayList<Student>();
				int count = jsonArray.length();
				
				for (int i=0; i<count; i++)
				{
					JSONObject e = jsonArray.getJSONObject(i);
					Student s = new Student(e.getLong("id"),
								 		e.getLong("jxid"),
								 		e.getString("xykh"),
								 		e.getString("xbmc"),
								 		e.getString("sfzmhm"),
								 		e.getString("sqcxmc"),
								 		e.getString("sjhm"),
								 		e.getString("xm"),
								 		e.getString("sqcx"),
								 		e.getString("division"),
								 		e.getString("cityDivision"),
								 		e.getString("jxmc"));
					
					studentList.add(s);
				}
				_db.insertStudent(studentList);
				
				resultMsg="{\"offset\":"
						+mArg1
						+",\"count\":"
						+count
						+",\"total\":"
						+_db.getStudentCount()
						+"}";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case GET_ALL_COACHS:
			try {
				jsonObj = new JSONObject(resultMsg);
				JSONArray jsonArray = new JSONArray(jsonObj.getString("coaches"));
				List<Coach> coachList = new ArrayList<Coach>();
				int count = jsonArray.length();
				
				for (int i=0; i<count; i++)
				{
					JSONObject e = jsonArray.getJSONObject(i);
					Coach c = new Coach(e.getLong("id"),
								 		e.getLong("jxid"),
								 		e.getLong("xysl"),
								 		e.getString("xbry"),
								 		e.getString("xjcx"),
								 		e.getString("xm"),
								 		e.getString("sjhm"),
								 		e.getString("sfzmhm"),
								 		e.getString("jxmc"),
								 		e.getString("division"),
								 		e.getString("cityDivision"),
								 		e.getString("cphm"));
					
					coachList.add(c);
				}
				_db.insertCoachs(coachList);
				
				resultMsg="{\"offset\":"
						+mArg1
						+",\"count\":"
						+count
						+",\"total\":"
						+_db.getCoachCount()
						+"}";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			break;
		case GET_ALL_CAR_NUM:
			try {
				jsonObj = new JSONObject(resultMsg);
				_db.setCarCount(jsonObj.getLong("carAll"));
				resultMsg="获取数据成功";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
			
		case GET_ALL_COACH_NUM:
			try {
				jsonObj = new JSONObject(resultMsg);
				_db.setCoachCount(jsonObj.getLong("coachAll"));
				resultMsg="获取数据成功";
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
			
		case GET_ALL_STUDENT_NUM:
			try {
				jsonObj = new JSONObject(resultMsg);
				_db.setStudentCount(jsonObj.getLong("studentAll"));
				resultMsg="获取数据成功";
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
