package com.zw.cjl.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OrderDetailActivity extends Activity {
	private Activity detailActivity = null;
	private String name = null;
	private String identity = null;
	private String stage = null;
	private String type = null;
	private String sqcx = null;
	private String examDate = null;
	private String examLocation = null;
	private String commitDate = null;
	private String status = null;
	private String reason = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_order_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.button_text_button_title);
	
		detailActivity = this;
		
		TextView title = (TextView)findViewById(R.id.centerTextTitle);
		title.setText("Լ����Ϣ");
		
		ImageButton btn = (ImageButton)findViewById(R.id.leftImageButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				detailActivity.finish();
			}
		});
		
		Intent intent = this.getIntent();
		name = intent.getStringExtra("name");
		identity = intent.getStringExtra("identity");
		stage = intent.getStringExtra("stage");
		type = intent.getStringExtra("type");
		sqcx = intent.getStringExtra("sqcx");
		examDate = intent.getStringExtra("examDate");
		examLocation = intent.getStringExtra("examLocation");
		commitDate = intent.getStringExtra("commitDate");
		status = intent.getStringExtra("status");
		reason = intent.getStringExtra("reason");
		
		addDetails();
	}
	
	public void addDetails()
	{
		ListView orderInfoList = (ListView)findViewById(R.id.order_detail_list);
		
		
		ArrayList<HashMap<String, String>> listItem = 
				new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "ѧԱ����");
		map1.put("data", name);
		listItem.add(map1);
		
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("name", "���֤��");
		map2.put("data", identity);
		listItem.add(map2);
		
		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("name", "�׶�");
		map3.put("data", stage);
		listItem.add(map3);
		
		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put("name", "ԤԼ����");
		map4.put("data", type);
		listItem.add(map4);
		
		HashMap<String, String> map5 = new HashMap<String, String>();
		map5.put("name", "���복��");
		map5.put("data", sqcx);
		listItem.add(map5);
		
		HashMap<String, String> map6 = new HashMap<String, String>();
		map6.put("name", "����ʱ��");
		map6.put("data", examDate);
		listItem.add(map6);
		
		HashMap<String, String> map7 = new HashMap<String, String>();
		map7.put("name", "��������");
		map7.put("data", examLocation);
		listItem.add(map7);
		
		HashMap<String, String> map8 = new HashMap<String, String>();
		map8.put("name", "�ύʱ��");
		map8.put("data", commitDate);
		listItem.add(map8);
		
		HashMap<String, String> map9 = new HashMap<String, String>();
		map9.put("name", "״̬");
		map9.put("data", status);
		listItem.add(map9);
		
		if (status.equals("����"))
		{
			HashMap<String, String> map10 = new HashMap<String, String>();
			map10.put("name", "ԭ��");
			map10.put("data", reason);
			listItem.add(map10);
		}
		
		SimpleAdapter listItemAdapter = 
				new SimpleAdapter(this,listItem, 
						R.layout.order_detail_list_item,  
						new String[] {"name", "data"},
						new int[] {R.id.orderDetailName, R.id.orderDetailData});
		
		orderInfoList.setAdapter(listItemAdapter);
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
