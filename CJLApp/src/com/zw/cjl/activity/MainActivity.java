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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zw.cjl.adapter.CarListAdapter;
import com.zw.cjl.adapter.CoachListAdapter;
import com.zw.cjl.adapter.CustomPageAdapter;
import com.zw.cjl.adapter.OrderListAdapter;
import com.zw.cjl.adapter.StudentListAdapter;
import com.zw.cjl.dto.Car;
import com.zw.cjl.dto.Coach;
import com.zw.cjl.dto.Database;
import com.zw.cjl.dto.Order;
import com.zw.cjl.dto.Student;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.pager.CustomViewPager;
import com.zw.cjl.thread.HttpGetThread;
import com.zw.cjl.watcher.CarTextWatcher;
import com.zw.cjl.watcher.CoachTextWatcher;
import com.zw.cjl.watcher.OrderTextWatcher;
import com.zw.cjl.watcher.StudentTextWatcher;

// TODO: 将人员页面放到一个新的ViewAdapter里面

@SuppressLint({ "HandlerLeak", "InflateParams" })
public class MainActivity extends Activity implements OnScrollListener{

	// 用于退出应用
	private long mExitTime = 0;

	//private String userType = null;
	private String identity = null;

	private ProgressDialog progressDlg;

	CustomViewPager mainViewPager = null;
	ViewPager personalViewPager = null;

	TextView tvMainTitle = null;
	LinearLayout mainTitleLayout = null;
	Database db = null;
	
	LinearLayout cars = null;
	LinearLayout personal = null;
	LinearLayout orders = null;
	LinearLayout my_center = null;
	
	ImageView car_image = null;
	ImageView personal_image = null;
	ImageView order_image = null;
	ImageView info_center_image = null;
	
	TextView car_text = null;
	TextView personal_text = null;
	TextView order_text = null;
	TextView info_center_text = null;
	
    TextView tvLeftTitle = null;
    TextView tvRightTitle = null;
    
    EditText searchCar = null;
    EditText searchStudent = null;
    EditText searchCoach = null;
    EditText searchOrder = null;
    
    String orgId = null;

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
		db = new Database(this);
		
		addPages();
	
		// 读取传入的数据
		Intent intent = this.getIntent();
		//userType = intent.getStringExtra("userType");
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
    		
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			
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
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {

    			try {
					JSONObject j = new JSONObject(resultMsg);

					long offset = j.getLong("offset");
					long count = j.getLong("count");
					long total = j.getLong("total");
					long newOffset = offset + count;
					
					if (0 == offset)
					{
						// 首次读取时初始化列表
						initCarList();
					}
					
					if (newOffset < total)
					{
						// 后台继续获取所有辆车
				        Thread getAllCars = 
				        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_CARS, 
				        									 getAllCarsResultHandler,
				        								     db,
				        								     orgId,
				        								     "" + newOffset));  
				        getAllCars.start();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    		
    	}
	};
	
	private Handler getAllStudentsResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			
    			try {
					JSONObject j = new JSONObject(resultMsg);

					long offset = j.getLong("offset");
					long count = j.getLong("count");
					long total = j.getLong("total");
					long newOffset = offset + count;
					
					if (0 == offset)
					{
						// 首次读取时初始化列表
						initStudentList();
					}
					
					if (newOffset < total)
					{
						// 后台继续获取所有学员
				        Thread getAllStudents = 
				        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_STUDENTS, 
				        									 getAllStudentsResultHandler,
				        								     db,
				        								     orgId,
				        								     "" + newOffset));  
				        getAllStudents.start();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    	}
	};
	
	private Handler getAllOrderResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			try {
					JSONObject j = new JSONObject(resultMsg);

					long offset = j.getLong("offset");
					long count = j.getLong("count");
					long total = j.getLong("total");
					long newOffset = offset + count;
					
					if (0 == offset)
					{
						// 首次读取时初始化列表
						initOrderList();
					}
					
					if (newOffset < total)
					{
						// 后台继续获取所有预约
				        Thread getAllOrder = 
				        		new Thread(new HttpGetThread(HttpGetType.GET_ORDER_STUDENT, 
				        									 getAllOrderResultHandler,
				        								     db,
				        								     orgId,
				        								     "" + newOffset));  
				        getAllOrder.start();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    	}
	};
	
	private Handler getAllCoachsResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			try {
					JSONObject j = new JSONObject(resultMsg);

					long offset = j.getLong("offset");
					long count = j.getLong("count");
					long total = j.getLong("total");
					long newOffset = offset + count;
					
					if (0 == offset)
					{
						// 首次读取时初始化列表
						initCoachList();
					}
					
					if (newOffset < total)
					{
						// 后台继续获取所有助理考试员
				        Thread getAllCoachs = 
				        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_COACHS, 
				        									 getAllCoachsResultHandler,
				        								     db,
				        								     orgId,
				        								     "" + newOffset));  
				        getAllCoachs.start();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    	}
	};
	
	private Handler getAllCarNumResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
  
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			try {
					JSONObject e = new JSONObject(resultMsg);
					String carNum = e.getString("carAll");
					searchCar = (EditText)findViewById(R.id.searchCar);
					searchCar.setHint(carNum+"条数据，您可以按[车牌]或[姓名]搜索");
				} catch (JSONException e) {
					e.printStackTrace();
				}

    			// 获取所有辆车
		        Thread getAllCars = 
		        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_CARS, 
		        									 getAllCarsResultHandler,
		        								     db,
		        								     orgId,
		        								     "0"));  
		        getAllCars.start();
    		}
    	}
	};
	
	private Handler getAllStudentNumResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
 
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			try {
					JSONObject e = new JSONObject(resultMsg);
					String studentNum = e.getString("studentAll");
					searchStudent = (EditText)findViewById(R.id.searchStudent);
					searchStudent.setHint(studentNum+"条数据，您可以按[姓名]或[手机]搜索");
				} catch (JSONException e) {
					e.printStackTrace();
				}
    			
				// 获取学员
		        Thread getAllStudents = 
		        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_STUDENTS, 
		        									 getAllStudentsResultHandler,
		        								     db,
		        								     orgId,
		        								     "0"));  
		        getAllStudents.start();
    		}
    	}
	};
	
	private Handler getAllOrderNumResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
 
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			try {
					JSONObject e = new JSONObject(resultMsg);
					String orderNum = e.getString("reserveRecordAll");
					searchOrder = (EditText)findViewById(R.id.searchOrder);
					searchOrder.setHint(orderNum+"条数据 您可以按[姓名]搜索");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// 获取所有助理考试员
    			
		        Thread getAllOrders = 
		        		new Thread(new HttpGetThread(HttpGetType.GET_ORDER_STUDENT, 
		        									 getAllOrderResultHandler,
		        								     db,
		        								     orgId,
		        								     "0"));  
		        getAllOrders.start();
    		}
    	}
	};
	
	private Handler getAllCoachNumResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
 
    		if(hasError) {
    			if (progressDlg.isShowing())
        		{
        			progressDlg.dismiss();
        		}
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			try {
					JSONObject e = new JSONObject(resultMsg);
					String coachNum = e.getString("coachAll");
					searchCoach = (EditText)findViewById(R.id.searchCoach);
					searchCoach.setHint(coachNum+"条数据，您可以按[姓名]或[手机]搜索");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// 获取所有助理考试员
    			
		        Thread getAllCoachs = 
		        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_COACHS, 
		        									 getAllCoachsResultHandler,
		        								     db,
		        								     orgId,
		        								     "0"));  
		        getAllCoachs.start();
    		}
    	}
	};
	
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
			
			orgId = "" + jsonObj.getInt("orgId");
			
			SimpleAdapter listItemAdapter = 
					new SimpleAdapter(this,listItem, 
							R.layout.self_info_list_item,  
							new String[] {"name", "data"},
							new int[] {R.id.selfInfoName, R.id.selfInfoData});
			
			myInfoList.setAdapter(listItemAdapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// 获取车辆总数
        Thread getCarNum = 
        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_CAR_NUM, 
        									 getAllCarNumResultHandler,
        								     db,
        								     orgId,
        								     null));  
        getCarNum.start();
        
        // 获取学员总数
        Thread getStudentNum = 
        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_STUDENT_NUM, 
        									 getAllStudentNumResultHandler,
        								     db,
        								     orgId,
        								     null));  
        getStudentNum.start();
        
        // 获取助理考试员总数
        Thread getCoachNum = 
        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_COACH_NUM, 
        									 getAllCoachNumResultHandler,
        								     db,
        								     orgId,
        								     null));  
        getCoachNum.start();
        
        // 获取预约总数
        Thread getOrderNum = 
        		new Thread(new HttpGetThread(HttpGetType.GET_ALL_ORDER_NUM, 
        									 getAllOrderNumResultHandler,
        								     db,
        								     orgId,
        								     null));  
        getOrderNum.start();
	}
	
	private void initCarList() {
		ListView carList = (ListView)findViewById(R.id.car_list);
		CarListAdapter carListAdapter = new CarListAdapter(this, db);
		carList.setAdapter(carListAdapter);
		carList.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (0 == searchCar.getText().length())
				{
					if (firstVisibleItem + visibleItemCount == totalItemCount)
					{
						CarListAdapter adapter = (CarListAdapter)view.getAdapter();
						adapter.loadNewItems();
					}
				}
				
			} 
			
		});
		
		EditText searchCar = (EditText)findViewById(R.id.searchCar);
	    searchCar.addTextChangedListener(new CarTextWatcher(carListAdapter));
	    
	    carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				CarListAdapter adapter = (CarListAdapter)arg0.getAdapter();
				List<Car> carlist = adapter.getmCarList();
				Car car = carlist.get(position);
				//Car car = (Car)adapter.getItem(position);
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), CarDetailActivity.class);
				intent.putExtra("jxid", car._schoolId);
				intent.putExtra("carNo", car._carNo.substring(1, 6));
				startActivity(intent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
	    });
	}
	
	private void initOrderList() {
		ListView orderList = (ListView)findViewById(R.id.order_list);
		OrderListAdapter orderListAdapter = new OrderListAdapter(this, db);
		orderList.setAdapter(orderListAdapter);
		orderList.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (0 == searchOrder.getText().length())
				{
					if (firstVisibleItem + visibleItemCount == totalItemCount)
					{
						OrderListAdapter adapter = (OrderListAdapter)view.getAdapter();
						adapter.loadNewItems();
					}
				}
			} 
			
		});
		
		EditText searchCar = (EditText)findViewById(R.id.searchOrder);
	    searchCar.addTextChangedListener(new OrderTextWatcher(orderListAdapter));
	    
	    orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				OrderListAdapter adapter = (OrderListAdapter)arg0.getAdapter();
				List<Order> orderlist = adapter.getmOrderList();
				Order order = orderlist.get(position);
				
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), OrderDetailActivity.class);
				intent.putExtra("name", order.name);
				intent.putExtra("identity", order.identity);
				intent.putExtra("stage", order.stage);
				intent.putExtra("type", order.type);
				intent.putExtra("sqcx", order.sqcx);
				intent.putExtra("examDate", order.examDate);
				intent.putExtra("examLocation", order.examLocation);
				intent.putExtra("commitDate", order.commitDate);
				intent.putExtra("status", order.status);
				intent.putExtra("reason", order.reason);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
	    });
	}
	
	private void initCoachList() {
		ListView coachList = (ListView)findViewById(R.id.coach_list);
		CoachListAdapter coachListAdapter = new CoachListAdapter(this, db);
		coachList.setAdapter(coachListAdapter);
		coachList.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (0 == searchCoach.getText().length())
				{
					if (firstVisibleItem + visibleItemCount == totalItemCount)
					{
						CoachListAdapter adapter = (CoachListAdapter)view.getAdapter();
						adapter.loadNewItems();
					}
				}
			} 
			
		});
		
		EditText searchCar = (EditText)findViewById(R.id.searchCoach);
	    searchCar.addTextChangedListener(new CoachTextWatcher(coachListAdapter));
	    
	    coachList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				CoachListAdapter adapter = (CoachListAdapter)arg0.getAdapter();
				List<Coach> coachlist = adapter.getmCoachList();
				Coach coach = coachlist.get(position);
				
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), CoachDetailActivity.class);
				intent.putExtra("xm", coach.xm);
				intent.putExtra("jxid", coach.jxid);
				intent.putExtra("identity", coach.sfzmhm);
				intent.putExtra("sjhm", coach.sjhm);
				intent.putExtra("cphm", coach.cphm);
				intent.putExtra("xysl", coach.xysl);
				startActivity(intent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
	    });
	}
	
	private void initStudentList() {
		ListView studentList = (ListView)findViewById(R.id.student_list);
		StudentListAdapter studentListAdapter = new StudentListAdapter(this, db);
		studentList.setAdapter(studentListAdapter);
		studentList.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (0 == searchStudent.getText().length())
				{
					if (firstVisibleItem + visibleItemCount == totalItemCount)
					{
						StudentListAdapter adapter = (StudentListAdapter)view.getAdapter();
						adapter.loadNewItems();
					}	
				}
			} 
			
		});
		
		EditText searchStudent = (EditText)findViewById(R.id.searchStudent);
		searchStudent.addTextChangedListener(new StudentTextWatcher(studentListAdapter));
	    
		studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				StudentListAdapter adapter = (StudentListAdapter)arg0.getAdapter();
				List<Student> studentlist = adapter.getmStudentList();
				Student student = studentlist.get(position);
				
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), StudentDetailActivity.class);
				intent.putExtra("identity", student.sfzmhm);
				intent.putExtra("cityDivision", student.cityDivision);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
	    });
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
        
        cars = (LinearLayout)findViewById(R.id.cars);
        personal = (LinearLayout)findViewById(R.id.personal);
        orders = (LinearLayout)findViewById(R.id.orders);
        my_center = (LinearLayout)findViewById(R.id.my_center);
        
        car_image = (ImageView)findViewById(R.id.cars_image);
        personal_image = (ImageView)findViewById(R.id.personal_image);
        order_image = (ImageView)findViewById(R.id.orders_image);
        info_center_image = (ImageView)findViewById(R.id.my_center_image);
        
        car_text = (TextView)findViewById(R.id.cars_text);
        personal_text = (TextView)findViewById(R.id.personal_text);
        order_text = (TextView)findViewById(R.id.orders_text);
        info_center_text = (TextView)findViewById(R.id.my_center_text);
        
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
				
				car_image.setBackgroundResource(R.drawable.car_select);
				car_text.setTextColor(getResources().getColor(R.color.welcome_blue));
				break;
			case 1:
				mainTitleLayout.setVisibility(View.VISIBLE);
				tvMainTitle.setVisibility(View.GONE);
				
				setTabsUnselected();
				
				personal_image.setBackgroundResource(R.drawable.personal_select);
				personal_text.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				tvLeftTitle.setBackgroundResource(R.drawable.tab_select);
				tvLeftTitle.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 2:
				mainTitleLayout.setVisibility(View.VISIBLE);
				tvMainTitle.setVisibility(View.GONE);
				
				setTabsUnselected();
				
				personal_image.setBackgroundResource(R.drawable.personal_select);
				personal_text.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				tvRightTitle.setBackgroundResource(R.drawable.tab_select);
				tvRightTitle.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 3:
				mainTitleLayout.setVisibility(View.GONE);
				tvMainTitle.setVisibility(View.VISIBLE);
				tvMainTitle.setText(R.string.order);
				
				setTabsUnselected();
				
				order_image.setBackgroundResource(R.drawable.order_select);
				order_text.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			case 4:
				mainTitleLayout.setVisibility(View.GONE);
				tvMainTitle.setVisibility(View.VISIBLE);
				tvMainTitle.setText(R.string.my_center);
				setTabsUnselected();
				
				info_center_image.setBackgroundResource(R.drawable.info_center_select);
				info_center_text.setTextColor(getResources().getColor(R.color.welcome_blue));
				
				break;
			}
			
			mainViewPager.setCurrentItem(index);
		}
	}
	
	private void setTabsUnselected()
	{
		car_image.setBackgroundResource(R.drawable.car_unselect);
		car_text.setTextColor(getResources().getColor(R.color.text_gray));
		
		personal_image.setBackgroundResource(R.drawable.personal_unselect);
		personal_text.setTextColor(getResources().getColor(R.color.text_gray));
		
		order_image.setBackgroundResource(R.drawable.order_unselect);
		order_text.setTextColor(getResources().getColor(R.color.text_gray));
		
		info_center_image.setBackgroundResource(R.drawable.info_center_unselect);
		info_center_text.setTextColor(getResources().getColor(R.color.text_gray));
		
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

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.v("MainActivity", "Scroll state changed!");
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.v("MainActivity", "from " + firstVisibleItem + " count " + visibleItemCount + "total " + totalItemCount);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

}
