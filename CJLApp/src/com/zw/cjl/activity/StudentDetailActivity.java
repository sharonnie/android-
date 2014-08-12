package com.zw.cjl.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zw.cjl.format.Meter;
import com.zw.cjl.format.Status;
import com.zw.cjl.format.Time;
import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.thread.HttpGetThread;

public class StudentDetailActivity extends Activity {
	private Activity detailActivity = this;
	private String identity = null;
	private String cityDivision = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_student_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.button_text_button_title);
		
		Intent intent = this.getIntent();
		identity = intent.getStringExtra("identity");
		cityDivision = intent.getStringExtra("cityDivision");
		
		TextView title = (TextView)findViewById(R.id.centerTextTitle);
		title.setText("������Ϣ");
		
		ImageButton btn = (ImageButton)findViewById(R.id.leftImageButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				detailActivity.finish();
			}
		});
		
		ImageButton rbtn = (ImageButton)findViewById(R.id.rightImageButton);
		rbtn.setImageResource(R.drawable.icon_record);
		rbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// ��ȡ���Լ�¼
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), StudentRecordActivity.class);
				intent.putExtra("identity", identity);
				intent.putExtra("cityDivision", cityDivision);
				startActivity(intent);	
			}
		});
		
		//progressDlg = ProgressDialog.show(this, "���Ժ�", "����������...", false, false); 
        Thread getStudentDetailThread = 
        		new Thread(new HttpGetThread(HttpGetType.GET_STUDENT_DETAIL, 
        									 getStudentDetailResultHandler,
        								     null,
        								     identity,
        								     null));  
        getStudentDetailThread.start();
	}
	
	// ������
	@SuppressLint("HandlerLeak")
	private Handler getStudentDetailResultHandler = new Handler() 
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
    			addStudentDetail(resultMsg);
    		}
    		
    	}
	};
	
	private void addStudentDetail(String details)
	{
		try {
			JSONObject jsonObj = new JSONObject(details);
			ListView myInfoList = (ListView)findViewById(R.id.student_detail_list);
			
			ArrayList<HashMap<String, String>> listItem = 
					new ArrayList<HashMap<String, String>>();
			
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("name", "ѧԱ����");
			map1.put("data", jsonObj.getString("stuName"));
			listItem.add(map1);
			
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("name", "���֤��");
			map2.put("data", identity);
			listItem.add(map2);
			
			HashMap<String, String> map3 = new HashMap<String, String>();
			map3.put("name", "��У����");
			map3.put("data", jsonObj.getString("schoolName"));
			listItem.add(map3);
			
			HashMap<String, String> map4 = new HashMap<String, String>();
			map4.put("name", "�����");
			map4.put("data", Meter.intToString(jsonObj.getInt("trainMeter")));
			listItem.add(map4);
			
			HashMap<String, String> map5 = new HashMap<String, String>();
			map5.put("name", "����ʱ��");
			map5.put("data", Time.buildTimes(jsonObj.getInt("trainTime")));
			listItem.add(map5);
			
			HashMap<String, String> map6 = new HashMap<String, String>();
			map6.put("name", "��һ�׶�");
			map6.put("data", Meter.intToString(jsonObj.getInt("meterStep1")));
			listItem.add(map6);
			
			HashMap<String, String> map7 = new HashMap<String, String>();
			map7.put("name", "�ڶ��׶�");
			map7.put("data", Meter.intToString(jsonObj.getInt("meterStep2")));
			listItem.add(map7);
			
			HashMap<String, String> map8 = new HashMap<String, String>();
			map8.put("name", "ҹ�����");
			map8.put("data", Meter.intToString(jsonObj.getInt("nightMeter")));
			listItem.add(map8);
			
			HashMap<String, String> map9 = new HashMap<String, String>();
			map9.put("name", "��������");
			map9.put("data", jsonObj.getString("continueTimes")+"��");
			listItem.add(map9);
			
			HashMap<String, String> map10 = new HashMap<String, String>();
			map10.put("name", "��Ч���");
			map10.put("data", Meter.intToString(jsonObj.getInt("invalidMeter")));
			listItem.add(map10);
			
			HashMap<String, String> map11 = new HashMap<String, String>();
			map11.put("name", "��ǰ״̬");
			map11.put("data", Status.validToString(jsonObj.getInt("verifyFlag")));
			listItem.add(map11);
			
			SimpleAdapter listItemAdapter = 
					new SimpleAdapter(this,listItem, 
							R.layout.student_detail_list_item,  
							new String[] {"name", "data"},
							new int[] {R.id.studentDetailName, R.id.studentDetailData});
			
			myInfoList.setAdapter(listItemAdapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
