package com.zw.cjl.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zw.cjl.adapter.CarListAdapter;
import com.zw.cjl.adapter.CustomPageAdapter;
import com.zw.cjl.dto.Car;
import com.zw.cjl.dto.Database;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.pager.CustomViewPager;
import com.zw.cjl.thread.HttpGetThread;
import com.zw.cjl.watcher.CarTextWatcher;

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class MainActivity extends Activity {
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}


	// 用于退出应用
	private long mExitTime = 0;

	private String userType = null;
	private String identity = null;

	private ProgressDialog progressDlg;

	CustomViewPager mainViewPager = null;
	ViewPager personalViewPager = null;

	TextView tvMainTitle = null;
	LinearLayout mainTitleLayout = null;
	Database db = null;
	
	TextView cars = null;
    TextView personal = null;
    TextView orders = null;
    TextView my_center = null;
    TextView tvLeftTitle = null;
    TextView tvRightTitle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_main); 
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.text_text_title);
		
		// 设置界面开始时的title
		tvMainTitle = (TextView)findViewById(R.id.mainTitle);
		tvMainTitle.setText(R.string.car_information);
		mainTitleLayout = (LinearLayout)findViewById(R.id.mainTitleLayout);
		mainTitleLayout.setVisibility(View.GONE);
		
		addPages();
		
		db = new Database(this);
		
		// 读取传入的数据
		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
        identity = intent.getStringExtra("identity");
        
        // 获取业务员详细信息
        progressDlg = ProgressDialog.show(this, "请稍候", "数据载入中...", false, false); 
        Thread getAssistantDetailThread = 
        		new Thread(new HttpGetThread(HttpGetType.GET_ASSISTANT_DETAIL, 
        								     getAssistantDetailResultHandler,
        								     db,
        								     identity,
        								     null));  
        getAssistantDetailThread.start();
	}
	
	
	// 处理结果
	private Handler getAssistantDetailResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if (progressDlg.isShowing())
    		{
    			progressDlg.dismiss();
    		}
    		
    		if(hasError) {
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			addMyCenter(resultMsg);
    		}
    		
    	}
	};
	
	private Handler getAllCarsResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if (progressDlg.isShowing())
    		{
    			progressDlg.dismiss();
    		}
    		
    		if(hasError) {
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			addCars(resultMsg);
    		}
    		
    	}
	};
	
	private Handler getAllStudentsResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if (progressDlg.isShowing())
    		{
    			progressDlg.dismiss();
    		}
    		
    		if(hasError) {
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			addStudents(resultMsg);
    		}
    	}
	};
	
	public void addCars(String cars){
		
		ListView carList = (ListView)findViewById(R.id.car_list);
		CarListAdapter carListAdapter = new CarListAdapter(this, db);
		carList.setAdapter(carListAdapter);
		
		EditText searchCar = (EditText)findViewById(R.id.searchCar);
	    searchCar.addTextChangedListener(new CarTextWatcher(carListAdapter));
	    
	    carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				CarListAdapter adapter = (CarListAdapter)arg0.getAdapter();
				List<Car> carlist = adapter.getmCarList();
				Car car = carlist.get(position);
				
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), CarDetailActivity.class);
				intent.putExtra("jxid", ""+car._schoolId);
				intent.putExtra("carNo", car._carNo);
				startActivity(intent);	
			}
	    });
		
		// 获取所有学员
        //progressDlg = ProgressDialog.show(this, "请稍候", "获取学员中...", false, false); 
		
        //Thread getAllStudents = 
        //		new Thread(new HttpGetThread(HttpGetType.GET_ALL_STUDENTS,
        //									 getAllStudentsResultHandler,
        //								     "118",
        //								     null));  
        //getAllStudents.start();
	}
	
	public void addStudents(String students){
		try {
			JSONObject jsonObj = new JSONObject(students);
			/*
			JSONArray jsonArray = new JSONArray(jsonObj.getString("cars"));

			ListView myInfoList = (ListView)findViewById(R.id.car_list);
			CarListAdapter carListAdapter = new CarListAdapter(this, jsonArray);
			myInfoList.setAdapter(carListAdapter);
			
			EditText searchCar = (EditText)findViewById(R.id.searchCar);
		    searchCar.addTextChangedListener(new CarTextWatcher(carListAdapter));
			*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void addMyCenter(String infos)
	{
		try {
			JSONObject jsonObj = new JSONObject(infos);
			ListView myInfoList = (ListView)findViewById(R.id.self_info_list);
			
			ArrayList<HashMap<String, String>> listItem = 
					new ArrayList<HashMap<String, String>>();
			
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("name", "驾校");
			map1.put("data", jsonObj.getString("jxmc"));
			listItem.add(map1);
			
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("name", "姓名");
			map2.put("data", jsonObj.getString("xm"));
			listItem.add(map2);
			
			HashMap<String, String> map3 = new HashMap<String, String>();
			map3.put("name", "身份证号");
			map3.put("data", jsonObj.getString("sfzh"));
			listItem.add(map3);
			
			HashMap<String, String> map4 = new HashMap<String, String>();
			map4.put("name", "手机号码");
			map4.put("data", jsonObj.getString("sjhm"));
			listItem.add(map4);
			
			SimpleAdapter listItemAdapter = 
					new SimpleAdapter(this,listItem, 
							R.layout.self_info_list_item,  
							new String[] {"name", "data"},
							new int[] {R.id.selfInfoName, R.id.selfInfoData});
			
			myInfoList.setAdapter(listItemAdapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// 获取所有车辆
        progressDlg = ProgressDialog.show(this, "请稍候", "获取车辆中...", false, false); 
        Thread getAllCars = 
        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_CARS, 
        									 getAllCarsResultHandler,
        								     db,
        								     "118",
        								     null));  
        getAllCars.start();
	}
	
	private void addPages() {
		
        LayoutInflater lf = getLayoutInflater();
        
        ArrayList<View> mainViewList = new ArrayList<View>();
        mainViewList.add(lf.inflate(R.layout.all_cars, null));
        mainViewList.add(lf.inflate(R.layout.all_students, null));
        mainViewList.add(lf.inflate(R.layout.all_coachs, null));
        mainViewList.add(lf.inflate(R.layout.all_orders, null));
        mainViewList.add(lf.inflate(R.layout.self_info, null));

        mainViewPager = (CustomViewPager) findViewById(R.id.mainViewpager);
        mainViewPager.setAdapter(new CustomPageAdapter(mainViewList));  
        // TODO: 优化后台页面数量
        mainViewPager.setOffscreenPageLimit(4);
        
        cars = (TextView) findViewById(R.id.cars);
        personal = (TextView) findViewById(R.id.personal);
        orders = (TextView) findViewById(R.id.orders);
        my_center = (TextView) findViewById(R.id.my_center);
        tvLeftTitle = (TextView)findViewById(R.id.leftTitle);
        tvRightTitle = (TextView)findViewById(R.id.rightTitle);
        
        cars.setOnClickListener(new PageTitleClick(0));
        personal.setOnClickListener(new PageTitleClick(1));
        tvLeftTitle.setOnClickListener(new PageTitleClick(1));
        tvRightTitle.setOnClickListener(new PageTitleClick(2));
        orders.setOnClickListener(new PageTitleClick(3));
        my_center.setOnClickListener(new PageTitleClick(4));
	}
	
	// 点击页面标题，切换到相应页面
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
				mainTitleLayout.setVisibility(View.GONE);
				tvMainTitle.setVisibility(View.VISIBLE);
				tvMainTitle.setText(R.string.car_information);
				
				setTabsUnselected();
				
				cars.setBackgroundResource(R.drawable.tab_select);
				cars.setTextColor(getResources().getColor(R.color.welcome_blue));
				break;
			case 1:
				mainTitleLayout.setVisibility(View.VISIBLE);
				tvMainTitle.setVisibility(View.GONE);
				
				setTabsUnselected();
				
				personal.setBackgroundResource(R.drawable.tab_select);
				personal.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				tvLeftTitle.setBackgroundResource(R.drawable.tab_select);
				tvLeftTitle.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 2:
				mainTitleLayout.setVisibility(View.VISIBLE);
				tvMainTitle.setVisibility(View.GONE);
				
				setTabsUnselected();
				
				personal.setBackgroundResource(R.drawable.tab_select);
				personal.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				tvRightTitle.setBackgroundResource(R.drawable.tab_select);
				tvRightTitle.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 3:
				mainTitleLayout.setVisibility(View.GONE);
				tvMainTitle.setVisibility(View.VISIBLE);
				tvMainTitle.setText(R.string.order);
				
				setTabsUnselected();
				
				orders.setBackgroundResource(R.drawable.tab_select);
				orders.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 4:
				mainTitleLayout.setVisibility(View.GONE);
				tvMainTitle.setVisibility(View.VISIBLE);
				tvMainTitle.setText(R.string.my_center);
				setTabsUnselected();
				
				my_center.setBackgroundResource(R.drawable.tab_select);
				my_center.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			}
			
			mainViewPager.setCurrentItem(index);
		}
	}
	
	public void setTabsUnselected()
	{
		cars.setBackgroundResource(R.drawable.tab_unselect);
		cars.setTextColor(getResources().getColor(R.color.white));
		
		personal.setBackgroundResource(R.drawable.tab_unselect);
		personal.setTextColor(getResources().getColor(R.color.white));
		
		orders.setBackgroundResource(R.drawable.tab_unselect);
		orders.setTextColor(getResources().getColor(R.color.white));
		
		my_center.setBackgroundResource(R.drawable.tab_unselect);
		my_center.setTextColor(getResources().getColor(R.color.white));
		
		tvLeftTitle.setBackgroundResource(R.drawable.tab_unselect);
		tvLeftTitle.setTextColor(getResources().getColor(R.color.white));
		
		tvRightTitle.setBackgroundResource(R.drawable.tab_unselect);
		tvRightTitle.setTextColor(getResources().getColor(R.color.white));
	}
	
	// 实现连按两次退出当前应用
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && 0 == event.getRepeatCount()) {
			if(1000 < (System.currentTimeMillis() - mExitTime)) {
				Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			} 
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
