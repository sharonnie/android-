package com.zw.cjl.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zw.cjl.adapter.CustomPageAdapter;
import com.zw.cjl.format.Meter;
import com.zw.cjl.format.Time;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.pager.CustomViewPager;
import com.zw.cjl.thread.HttpGetThread;

public class StudentRecordActivity extends Activity {
	private String identity = null;
	private String cityDivision = null;
	private Activity recordActivity = null;
	TextView tvValid = null;
	TextView tvPartialValid = null;
	TextView tvInvalid = null;
	CustomViewPager recordViewPager = null;
	
	private JSONArray validArray = null;
	private JSONArray parValidArray = null;
	private JSONArray inValidArray = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_student_record);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.text_text_text_title);
		
		recordActivity = this;
		
		Intent intent = this.getIntent();
		identity = intent.getStringExtra("identity");
		cityDivision = intent.getStringExtra("cityDivision");
		
		ImageButton btn = (ImageButton)findViewById(R.id.backButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				recordActivity.finish();
			}
		});
		
		addPages();
		
		Thread getStudentRecordThread = 
        		new Thread(new HttpGetThread(HttpGetType.GET_STUDENT_RECORD, 
        									 getStudentRecordResultHandler,
        								     null,
        								     identity,
        								     cityDivision));  
		getStudentRecordThread.start();
	}
	
	// 处理结果
	@SuppressLint("HandlerLeak")
	private Handler getStudentRecordResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if(hasError) {
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			addRecords(resultMsg);
    		}
    		
    	}
	};
	
	public void addRecords(String records)
	{
		try {
			JSONObject jsonObj = new JSONObject(records);
			JSONArray jsonArray = new JSONArray(jsonObj.getString("recordDatas"));
			
			validArray = new JSONArray();
	    	parValidArray = new JSONArray();
	    	inValidArray = new JSONArray();
	    	
	    	for (int i=0; i<jsonArray.length(); i++)
	    	{
	    		JSONObject ele = jsonArray.getJSONObject(i);
	    		switch (ele.getInt("isValid"))
	    		{
	    		case 1:
	    			validArray = validArray.put(validArray.length(), ele);
	    			break;
	    		case 2:
	    			parValidArray = parValidArray.put(parValidArray.length(), ele);
	    			break;
	    		case 0:
	    			inValidArray = inValidArray.put(inValidArray.length(), ele);
	    			break;
	    		}
	    	}
			
			ListView validList = (ListView)findViewById(R.id.student_record_valid_list);
			ListView partialValidList = (ListView)findViewById(R.id.student_record_partial_valid_list);
			ListView invalidList = (ListView)findViewById(R.id.student_record_invalid_list);
			
			ArrayList<HashMap<String, String>> listValid =  new ArrayList<HashMap<String, String>>();
			ArrayList<HashMap<String, String>> listPartialValid =  new ArrayList<HashMap<String, String>>();
			ArrayList<HashMap<String, String>> listInvalid =  new ArrayList<HashMap<String, String>>();
			
			for (int i=0; i<validArray.length(); i++){
				JSONObject e = validArray.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("licheng", Meter.intToString(e.getInt("trainMeter")));
				map.put("shichang", Time.buildTimes(e.getInt("trainTime")));
				map.put("zhulikaoshiyuan", e.getString("teaName"));
				map.put("pingjunsudu", ""+e.getInt("averageSpeed")+"公里/时");
				map.put("kaishiriqi", Time.longToString(e.getLong("longStartTime")));
				listValid.add(map);
			}
			
			for (int i=0; i<parValidArray.length(); i++){
				JSONObject e = parValidArray.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("licheng", Meter.intToString(e.getInt("trainMeter")));
				map.put("shichang", Time.buildTimes(e.getInt("trainTime")));
				map.put("zhulikaoshiyuan", e.getString("teaName"));
				map.put("pingjunsudu", ""+e.getInt("averageSpeed")+"公里/时");
				map.put("kaishiriqi", Time.longToString(e.getLong("longStartTime")));
				listValid.add(map);
			}
			
			for (int i=0; i<inValidArray.length(); i++){
				JSONObject e = inValidArray.getJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("licheng", Meter.intToString(e.getInt("trainMeter")));
				map.put("shichang", Time.buildTimes(e.getInt("trainTime")));
				map.put("zhulikaoshiyuan", e.getString("teaName"));
				map.put("pingjunsudu", ""+e.getInt("averageSpeed")+"公里/时");
				map.put("kaishiriqi", Time.longToString(e.getLong("longStartTime")));
				listValid.add(map);
			}
			
			SimpleAdapter listItemAdapter = 
					new SimpleAdapter(this,listValid, 
							R.layout.student_record_list_item,  
							new String[] {"licheng", 
										"shichang",
										"zhulikaoshiyuan",
										"pingjunsudu",
										"kaishiriqi"},
							new int[] {R.id.licheng, 
										R.id.shichang, 
										R.id.zhulikaoshiyuan, 
										R.id.pingjunsudu, 
										R.id.kaishiriqi});
			
			SimpleAdapter listItemAdapter2 = 
					new SimpleAdapter(this,listPartialValid, 
							R.layout.student_record_list_item,  
							new String[] {"licheng", 
										"shichang",
										"zhulikaoshiyuan",
										"pingjunsudu",
										"kaishiriqi"},
							new int[] {R.id.licheng, 
										R.id.shichang, 
										R.id.zhulikaoshiyuan, 
										R.id.pingjunsudu, 
										R.id.kaishiriqi});
			
			SimpleAdapter listItemAdapter3 = 
					new SimpleAdapter(this,listInvalid, 
							R.layout.student_record_list_item,  
							new String[] {"licheng", 
										"shichang",
										"zhulikaoshiyuan",
										"pingjunsudu",
										"kaishiriqi"},
							new int[] {R.id.licheng, 
										R.id.shichang, 
										R.id.zhulikaoshiyuan, 
										R.id.pingjunsudu, 
										R.id.kaishiriqi});
			
			validList.setAdapter(listItemAdapter);
			partialValidList.setAdapter(listItemAdapter2);
			invalidList.setAdapter(listItemAdapter3);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@SuppressLint("InflateParams")
	private void addPages() {
		
        LayoutInflater lf = getLayoutInflater();
        
        ArrayList<View> mainViewList = new ArrayList<View>();
        mainViewList.add(lf.inflate(R.layout.student_record_valid_list, null));
        mainViewList.add(lf.inflate(R.layout.student_record_partial_valid_list, null));
        mainViewList.add(lf.inflate(R.layout.student_record_invalid_list, null));

        recordViewPager = (CustomViewPager) findViewById(R.id.studentRecordViewpager);
        recordViewPager.setAdapter(new CustomPageAdapter(mainViewList));  
        // TODO: 优化后台页面数量
        recordViewPager.setOffscreenPageLimit(2);
        
        tvValid = (TextView) findViewById(R.id.validTitle);
        tvPartialValid = (TextView) findViewById(R.id.patialValidTitle);
        tvInvalid = (TextView) findViewById(R.id.invalidTitle);
        
        tvValid.setOnClickListener(new PageTitleClick(0));
        tvPartialValid.setOnClickListener(new PageTitleClick(1));
        tvInvalid.setOnClickListener(new PageTitleClick(2));
	}

	//点击页面标题，切换到相应页面
	public class PageTitleClick implements View.OnClickListener {
		private int index = 0;
		
		public PageTitleClick(int i) {
			index = i;
		}
		
		@Override
		public void onClick(View v) {
			switch (index)
			{
			case 0:
				setTabsUnselected();
				
				tvValid.setBackgroundResource(R.drawable.tab_select);
				tvValid.setTextColor(getResources().getColor(R.color.welcome_blue));
				break;
			case 1:
				setTabsUnselected();
				
				tvPartialValid.setBackgroundResource(R.drawable.tab_select);
				tvPartialValid.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 2:
				setTabsUnselected();
				
				tvInvalid.setBackgroundResource(R.drawable.tab_select);
				tvInvalid.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			
			}
			
			recordViewPager.setCurrentItem(index);
		}
	}
	
	private void setTabsUnselected()
	{
		tvValid.setBackgroundResource(R.drawable.tab_unselect);
		tvValid.setTextColor(getResources().getColor(R.color.white));
		
		tvPartialValid.setBackgroundResource(R.drawable.tab_unselect);
		tvPartialValid.setTextColor(getResources().getColor(R.color.white));
		
		tvInvalid.setBackgroundResource(R.drawable.tab_unselect);
		tvInvalid.setTextColor(getResources().getColor(R.color.white));
	}
	
}
