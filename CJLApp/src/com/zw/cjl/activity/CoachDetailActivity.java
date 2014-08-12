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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zw.cjl.network.HttpGetType;
import com.zw.cjl.thread.HttpGetThread;

public class CoachDetailActivity extends Activity {
	private String xm = null;
	private String identity = null;
	private String sjhm = null;
	private String cphm = null;
	private long xysl = 0;
	private long jxid = 0;
	private Activity detailActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_coach_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.button_text_button_title);
	
		TextView title = (TextView)findViewById(R.id.centerTextTitle);
		title.setText("助理考试员信息");
		
		ImageButton btn = (ImageButton)findViewById(R.id.leftImageButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				detailActivity.finish();
			}
		});
		
		Intent intent = this.getIntent();
		xm = intent.getStringExtra("xm");
		identity = intent.getStringExtra("identity");
		sjhm = intent.getStringExtra("sjhm");
		cphm = intent.getStringExtra("cphm");
		jxid = intent.getLongExtra("jxid", 0);
		xysl = intent.getLongExtra("xysl", 0);
		
		//progressDlg = ProgressDialog.show(this, "请稍候", "数据载入中...", false, false); 
        Thread getCoachDetailThread = 
        		new Thread(new HttpGetThread(HttpGetType.GET_COACH_DETAIL, 
        									 getCoachDetailResultHandler,
        								     null,
        								     identity,
        								     null));  
        getCoachDetailThread.start();
	}
	
	// 处理结果
	@SuppressLint("HandlerLeak")
	private Handler getCoachDetailResultHandler = new Handler() 
	{
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		boolean hasError = msg.getData().getBoolean("hasError");
    		
    		/*
    		 * 
    		 * coachAndStudentsDatas
    		if (progressDlg.isShowing())
    		{
    			progressDlg.dismiss();
    		}
    		*/
    		
    		if(hasError) {
    			Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    		} else {
    			addCoachDetail(resultMsg);
    		}
    		
    	}
	};
	
	private void addCoachDetail(String details)
	{
		try {
			JSONObject jsonObj = new JSONObject(details);
			ListView coachInfoList = (ListView)findViewById(R.id.coach_detail_list);
			
			
			ArrayList<HashMap<String, String>> listItem = 
					new ArrayList<HashMap<String, String>>();
			
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("name", "姓名");
			map1.put("data", xm);
			listItem.add(map1);
			
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("name", "身份证号");
			map2.put("data", identity);
			listItem.add(map2);
			
			HashMap<String, String> map3 = new HashMap<String, String>();
			map3.put("name", "手机号");
			map3.put("data", sjhm);
			listItem.add(map3);
			
			HashMap<String, String> map4 = new HashMap<String, String>();
			map4.put("name", "车牌号码");
			map4.put("data", cphm);
			listItem.add(map4);
			
			HashMap<String, String> map5 = new HashMap<String, String>();
			map5.put("name", "学员数量");
			map5.put("data", ""+xysl);
			listItem.add(map5);
			
			HashMap<String, String> map6 = new HashMap<String, String>();
			map6.put("name", "");
			map6.put("data", "");
			listItem.add(map6);
			
			SimpleAdapter listItemAdapter = 
					new SimpleAdapter(this,listItem, 
							R.layout.coach_detail_list_item,  
							new String[] {"name", "data"},
							new int[] {R.id.coachDetailName, R.id.coachDetailData});
			
			coachInfoList.setAdapter(listItemAdapter);
		
			coachInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					if (3 == position) {
						//
						Intent intent=new Intent();
						intent.setClass(getApplicationContext(), CarDetailActivity.class);
						intent.putExtra("jxid", jxid);
						intent.putExtra("carNo", cphm.substring(1, 6));
						startActivity(intent);	
					}
				}
			});
			
			JSONArray students = jsonObj.getJSONArray("coachAndStudentsDatas");
			
			ListView studentList = (ListView)findViewById(R.id.coach_student_list);
			
			ArrayList<HashMap<String, String>> studentListItem = 
					new ArrayList<HashMap<String, String>>();
			
			for (int i=0; i<students.length(); i++)
			{
				JSONObject j = students.getJSONObject(i);
				HashMap<String, String> smap = new HashMap<String, String>();
				smap.put("name", j.getString("studentName"));
				smap.put("identity", j.getString("sSfzmhm"));
				smap.put("cityDivision", j.getString("cityDivision"));
				studentListItem.add(smap);
			}
			
			SimpleAdapter studentListItemAdapter = 
					new SimpleAdapter(this,studentListItem, 
							R.layout.coach_detail_student_list_item,  
							new String[] {"name", "identity"},
							new int[] {R.id.studentName, R.id.studentIdentity});
			
			studentList.setAdapter(studentListItemAdapter);
			
			studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					ListAdapter adapter = (ListAdapter)arg0.getAdapter();
					@SuppressWarnings("unchecked")
					HashMap<String, String> map = (HashMap<String, String>)adapter.getItem(position);
					String identity = map.get("identity");
					String cityDivision = map.get("cityDivision");
					
					Intent intent=new Intent();
					intent.setClass(getApplicationContext(), StudentDetailActivity.class);
					intent.putExtra("identity", identity);
					intent.putExtra("cityDivision", cityDivision);
					startActivity(intent);	
				}
		    });
			
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
