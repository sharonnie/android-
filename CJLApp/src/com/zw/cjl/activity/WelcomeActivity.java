package com.zw.cjl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class WelcomeActivity extends Activity {
	public static Activity instance = null;
	// 用户类型
	private String userType;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        
        // 替换默认的Title
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
        setContentView(R.layout.activity_welcome);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.welcome_title);
    }
    
    // 助理考试员
    public void onClickCoach(View v) {
    	userType = "1";
    	startLogin();
    }
    
    // 学员
    public void onClickStudent(View v) {
    	userType = "2";
    	startLogin();
    }

    // 业务员
    public void onClickAssistant(View v) {
    	userType = "3";
    	startLogin();
    }
 
    private void startLogin() {
    	Intent intent=new Intent();
		intent.setClass(getApplicationContext(), LoginActivity.class);
		intent.putExtra("userType", userType);
		startActivity(intent);	
    }
}
