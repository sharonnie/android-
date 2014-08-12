package com.zw.cjl.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.thread.HttpGetThread;

public class CarLocationActivity extends Activity {

	private Activity locationActivity = null;
	private MapView mMapView = null;  
	private String deviceNo = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_car_location);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.button_text_button_title);
		
		locationActivity = this;
		
		TextView title = (TextView)findViewById(R.id.centerTextTitle);
		title.setText("实时位置");
		
		Intent intent = this.getIntent();
		deviceNo = intent.getStringExtra("deviceNo");
		
		ImageButton btn = (ImageButton)findViewById(R.id.leftImageButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				locationActivity.finish();
			}
		});
		
		mMapView = (MapView) findViewById(R.id.bmapView); 
		
		Thread getCarLocationThread = 
        		new Thread(new HttpGetThread(HttpGetType.GET_CAR_LOCATION, 
        									 getCarLocationResultHandler,
        								     null,
        								     deviceNo,
        								     ""+10));  
		getCarLocationThread.start();
		
	}
	
	// 处理结果
	@SuppressLint("HandlerLeak")
	private Handler getCarLocationResultHandler = new Handler() 
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
    			addLocation(resultMsg);
    		}
    		
    	}
	};
	
	private void addLocation(String location)
	{
		try {
			JSONObject e = new JSONObject(location);
			JSONArray gpsArray = e.getJSONArray("gps");
			JSONObject gps = gpsArray.getJSONObject(0);
			double lng = gps.getDouble("longitude");
			double lat = gps.getDouble("latitude");
			BaiduMap mBaiduMap = mMapView.getMap();
			 
			LatLng point = new LatLng(lat, lng);
			
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.car_in_map);
		
			OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
			mBaiduMap.addOverlay(option);
			
			LatLng ll = new LatLng(lat, lng);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 15);
			mBaiduMap.animateMapStatus(u);  
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();  
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();  
	}

}
