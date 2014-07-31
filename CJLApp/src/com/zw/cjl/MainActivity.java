package com.zw.cjl;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.ze.cjl.network.HttpRequest;

public class MainActivity extends Activity {
	
	private String userType = null;
	private String jxid = null;
	private String identity = null;
	
	private ProgressDialog progressDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_main); 
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.text_title);
		
		//EditText title = (EditText)findViewById(R.id.titleText);
		//title.setText(R.string.car_information);
		
		//addPages();
		
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
		/*
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		
        LayoutInflater lf = getLayoutInflater();
        
        ArrayList<View> viewList = new ArrayList<View>();
        viewList.add(lf.inflate(R.layout.student_test_info, null));
        viewList.add(lf.inflate(R.layout.student_test_record, null));
        
        TextView t1 = (TextView) findViewById(R.id.studentPageInfo);
        TextView t2 = (TextView) findViewById(R.id.studentPageData);
        
        t1.setOnClickListener(new PageTitleClick(0));
        t2.setOnClickListener(new PageTitleClick(1));
        
        viewPager.setAdapter(new MyPagerAdapter(viewList));   
        */  
	}
}
