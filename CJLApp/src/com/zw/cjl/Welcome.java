package com.zw.cjl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // �滻Ĭ�ϵ�Title
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
        setContentView(R.layout.activity_welcome);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.welcome_title);
    }
    
    // ������Ա
    public void onClickCoach(View v) {
    	
    }
    
    // ѧԱ
    public void onClickStudent(View v) {
    	
    }

    // ҵ��Ա
    public void onClickAssistant(View v) {
    	
    }
    
}
