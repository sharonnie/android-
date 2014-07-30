package com.zw.cjl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 替换默认的Title
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
        setContentView(R.layout.activity_welcome);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.welcome_title);
    }
    
    // 助理考试员
    public void onClickCoach(View v) {
    	
    }
    
    // 学员
    public void onClickStudent(View v) {
    	
    }

    // 业务员
    public void onClickAssistant(View v) {
    	
    }
    
}
