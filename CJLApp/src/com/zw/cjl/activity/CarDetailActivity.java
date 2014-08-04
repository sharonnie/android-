package com.zw.cjl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class CarDetailActivity extends Activity {
	
	private String carNo;
	private String jxid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_car_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.text_title);
		
		Intent intent = this.getIntent();
		jxid = intent.getStringExtra("jxid");
		carNo = intent.getStringExtra("carNo");
	}
}
