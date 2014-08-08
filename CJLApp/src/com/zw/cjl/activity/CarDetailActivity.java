package com.zw.cjl.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zw.cjl.format.Status;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.thread.HttpGetThread;

public class CarDetailActivity extends Activity {
	
	private String carNo;
	private long jxid;
	private Activity detailActivity = this;
	//private ProgressDialog progressDlg;
	private String deviceNo = null;
	private ImageButton rbtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_car_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.button_text_button_title);
		
		TextView title = (TextView)findViewById(R.id.centerTextTitle);
		title.setText("车辆信息");
		
		ImageButton btn = (ImageButton)findViewById(R.id.leftImageButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				detailActivity.finish();
			}
		});
		
		rbtn = (ImageButton)findViewById(R.id.rightImageButton);
		//rbtn.setBackgroundResource(R.drawable.icon_map);
		rbtn.setImageResource(R.drawable.icon_map);
		
		Intent intent = this.getIntent();
		jxid = intent.getLongExtra("jxid", 0);
		carNo = intent.getStringExtra("carNo");
		
		//progressDlg = ProgressDialog.show(this, "请稍候", "数据载入中...", false, false); 
        Thread getCarDetailThread = 
        		new Thread(new HttpGetThread(HttpGetType.GET_CAR_DETAIL, 
        									 getCarDetailResultHandler,
        								     null,
        								     ""+jxid,
        								     carNo));  
        getCarDetailThread.start();
	}
	
	// 处理结果
	private Handler getCarDetailResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		/*
    		if (progressDlg.isShowing())
    		{
    			progressDlg.dismiss();
    		}
    		*/
    		
    		if(hasError) {
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			addCarDetail(resultMsg);
    		}
    		
    	}
	};
	
	private void addCarDetail(String details)
	{
		try {
			JSONObject jsonObj = new JSONObject(details);
			ListView myInfoList = (ListView)findViewById(R.id.car_detail_list);
			
			ArrayList<HashMap<String, String>> listItem = 
					new ArrayList<HashMap<String, String>>();
			
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("name", "车牌号");
			map1.put("data", jsonObj.getString("carNo"));
			listItem.add(map1);
			
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("name", "管理员姓名");
			map2.put("data", jsonObj.getString("ownerName"));
			listItem.add(map2);
			
			HashMap<String, String> map3 = new HashMap<String, String>();
			map3.put("name", "管理员电话");
			map3.put("data", jsonObj.getString("ownerPhone"));
			listItem.add(map3);
			
			HashMap<String, String> map4 = new HashMap<String, String>();
			map4.put("name", "所属驾校");
			map4.put("data", jsonObj.getString("schoolName"));
			listItem.add(map4);
			
			HashMap<String, String> map5 = new HashMap<String, String>();
			map5.put("name", "设备编号");
			map5.put("data", jsonObj.getString("deviceNo"));
			listItem.add(map5);
			
			HashMap<String, String> map6 = new HashMap<String, String>();
			map6.put("name", "SIM卡号");
			map6.put("data", jsonObj.getString("simNo"));
			listItem.add(map6);
			
			HashMap<String, String> map7 = new HashMap<String, String>();
			map7.put("name", "状态");
			map7.put("data", Status.carStatusToString(jsonObj.getString("status")));
			listItem.add(map7);
			
			HashMap<String, String> map8 = new HashMap<String, String>();
			map8.put("name", "在线信息");
			map8.put("data", Status.carOnlineToString(jsonObj.getString("isrOnline")));
			listItem.add(map8);
			
			SimpleAdapter listItemAdapter = 
					new SimpleAdapter(this,listItem, 
							R.layout.car_detail_list_item,  
							new String[] {"name", "data"},
							new int[] {R.id.carDetailName, R.id.carDetailData});
			
			myInfoList.setAdapter(listItemAdapter);
			
			deviceNo = jsonObj.getString("deviceNo");
			
			rbtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent();
					intent.setClass(getApplicationContext(), CarLocationActivity.class);
					intent.putExtra("deviceNo", deviceNo);
					startActivity(intent);	
				}
			});
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
