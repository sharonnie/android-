package com.zw.cjl;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ze.cjl.network.HttpRequest;

public class MainActivity extends Activity {
	// 用于退出应用
	private long mExitTime = 0;
	
	private String userType = null;
	private String jxid = null;
	private String identity = null;
	
	private ProgressDialog progressDlg;
	
	ViewPager mainViewPager = null;
	ViewPager personalViewPager = null;
	
	EditText title = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_main); 
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.text_title);
		
		addPages();
		
		// 读取传入的数据
		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
        identity = intent.getStringExtra("identity");
        jxid = intent.getStringExtra("jxid");
        
        progressDlg = ProgressDialog.show(this, "请稍候", "数据载入中...", false, false); 
        
        // 获取业务员详细信息
        Thread getAssistantDetailThread = new Thread(new HttpGetThread());  
        getAssistantDetailThread.start();
	}
	
	// 登录线程
	class HttpGetThread implements Runnable {
    	@Override
    	public void run() {
    		String result = null;
    		
    		result = HttpRequest.assistantDetail(identity);
    		
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
					if (progressDlg.isShowing())
					{
						progressDlg.dismiss();
					}
					
					
					
				} else {
					hasError = true; 
					try {
						resultMsg = jsonObj.getString("resultMsg");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(hasError) {
				Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("resultMsg", resultMsg);
                message.setData(bundle);
                getAssistantDetailResultHandler.sendMessage(message);
			}
    		
    	}
	}
	
	// 处理结果
	private Handler getAssistantDetailResultHandler = new Handler() {
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		
    		if(progressDlg.isShowing()) { 
    			progressDlg.dismiss(); 
    		}
    		
    		Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    	}
	};
	
	private void addPages() {
		
        LayoutInflater lf = getLayoutInflater();
        
        ArrayList<View> mainViewList = new ArrayList<View>();
        mainViewList.add(lf.inflate(R.layout.all_cars, null));
        mainViewList.add(lf.inflate(R.layout.personal_page, null));
        mainViewList.add(lf.inflate(R.layout.all_orders, null));
        mainViewList.add(lf.inflate(R.layout.self_info, null));

        mainViewPager = (ViewPager) findViewById(R.id.mainViewpager);
        mainViewPager.setAdapter(new CustomPageAdapter(mainViewList));   
        
        //ArrayList<View> personalViewList = new ArrayList<View>();
        //personalViewList.add(lf.inflate(R.layout.all_students, null));
        //personalViewList.add(lf.inflate(R.layout.all_coachs, null));
        
        //personalViewPager = (ViewPager) findViewById(R.id.personalViewpager);
        //personalViewPager.setAdapter(new CustomPageAdapter(personalViewList));
        
        TextView t1 = (TextView) findViewById(R.id.cars);
        TextView t2 = (TextView) findViewById(R.id.personal);
        TextView t3 = (TextView) findViewById(R.id.orders);
        TextView t4 = (TextView) findViewById(R.id.my_center);
        
        t1.setOnClickListener(new PageTitleClick(0));
        t2.setOnClickListener(new PageTitleClick(1));
        t3.setOnClickListener(new PageTitleClick(2));
        t4.setOnClickListener(new PageTitleClick(3));
	}
	
	// 点击页面标题，切换到相应页面
	public class PageTitleClick implements View.OnClickListener {
		private int index = 0;
		
		public PageTitleClick(int i) {
			index = i;
			//title = (EditText)findViewById(R.id.titleText);
			//title.setText(R.string.car_information);
		}
		
		@Override
		public void onClick(View v) {
			mainViewPager.setCurrentItem(index);
		}
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
