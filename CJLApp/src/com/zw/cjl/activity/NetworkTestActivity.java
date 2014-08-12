package com.zw.cjl.activity;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NetworkTestActivity extends Activity {
	String strNSURL="http://www.yncjl.com/app/loadObj?zsl=1&type=2&identity=532901198808270028&phone=15123390897";
	String strCMURL="http://183.224.79.167/app/loadObj?zsl=1&type=2&identity=532901198808270028&phone=15123390897";
	String strCTURL="http://220.163.43.27:12080/app/loadObj?zsl=1&type=2&identity=532901198808270028&phone=15123390897";

	CheckBox cbNS;
	CheckBox cbCM;
	CheckBox cbCT;
	
	TextView tvNS;
	TextView tvCM;
	TextView tvCT;
	
	TextView tvNSRes;
	TextView tvCMRes;
	TextView tvCTRes;
	
	ProgressBar pbNS;
	ProgressBar pbCM;
	ProgressBar pbCT;
	Activity testActivity = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.activity_network_test);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.button_text_button_title);
		
		TextView title = (TextView)findViewById(R.id.centerTextTitle);
		title.setText("�������");
		testActivity = this;
		
		ImageButton btn = (ImageButton)findViewById(R.id.leftImageButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				testActivity.finish();
			}
		});
		
		cbNS = (CheckBox)findViewById(R.id.cb_nstest);
		cbCM = (CheckBox)findViewById(R.id.cb_cmtest);
		cbCT = (CheckBox)findViewById(R.id.cb_cttest);
		
		cbNS.setChecked(true);
		cbCM.setChecked(true);
		cbCT.setChecked(true);
		
		tvNS = (TextView)findViewById(R.id.tv_nstest);
		tvCM = (TextView)findViewById(R.id.tv_cmtest);
		tvCT = (TextView)findViewById(R.id.tv_cttest);
		
		tvNSRes = (TextView)findViewById(R.id.tv_nstest_result);
		tvCMRes = (TextView)findViewById(R.id.tv_cmtest_result);
		tvCTRes = (TextView)findViewById(R.id.tv_cttest_result);
		
		pbNS = (ProgressBar)findViewById(R.id.pb_nstest);
		pbCM = (ProgressBar)findViewById(R.id.pb_cmtest);
		pbCT = (ProgressBar)findViewById(R.id.pb_cttest);
		
		pbNS.setVisibility(View.GONE);
		pbCM.setVisibility(View.GONE);
		pbCT.setVisibility(View.GONE);
	}
	
public void startTest(View v) {
		
		tvNS.setText("");
		tvCM.setText("");
		tvCT.setText("");
		
		tvNSRes.setText("");
		tvCMRes.setText("");
		tvCTRes.setText("");
		
		if (cbNS.isChecked())
		{
			pbNS.setVisibility(View.VISIBLE);
		}
		
		if (cbCM.isChecked())
		{
			pbCM.setVisibility(View.VISIBLE);
		}
		
		if (cbCT.isChecked())
		{
			pbCT.setVisibility(View.VISIBLE);
		}
		
		Thread loginThread = new Thread(new LoginHandler());  
        loginThread.start();
	}
	
	// �������
	// strURL: ���ӵĵ�ַ
	// �ɹ�����true��ʧ�ܷ���false
	private boolean testConnect(String strURL) {
		try {
			HttpURLConnection conn = null;
			URL url = new URL(strURL);
			conn = (HttpURLConnection)url.openConnection();  
			conn.setConnectTimeout(3000);  
			conn.setRequestMethod("GET");
			conn.connect();
			
			//success
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return false;
	}
	
	// ��ȡ����ʱ��
	// strURL: ���ʵ�URL��ַ
	// ����ʧ�ܷ���-1���ɹ�����ʱ�ӵĺ���ֵ
	private long getConnectTimeDelay(String strURL) 
	{
		Calendar sendCalendar = Calendar.getInstance();
		long sendTime = sendCalendar.getTimeInMillis();
		
		if (testConnect(strURL)) {
			Calendar recvCalendar = Calendar.getInstance();
			long recvTime = recvCalendar.getTimeInMillis();
			
			return recvTime - sendTime;
		}
		
		return -1;
	}
	
	// �����������ʲ��Խ�������߳�
	private void sendNSMessage ()
	{
		Message message = new Message();
        Bundle bundle = new Bundle();
		for (int i=0; i<3; i++) 
		{
            bundle.putLong("nsDelay"+i, getConnectTimeDelay(strNSURL));
		}
		message.setData(bundle);
        uiUpdateHandler.sendMessage(message);
	}
	
	// �����ƶ�������ʲ��Խ�������߳�
	private void sendCMMessage ()
	{
		Message message = new Message();
        Bundle bundle = new Bundle();
		for (int i=0; i<3; i++) 
		{
            bundle.putLong("cmDelay"+i, getConnectTimeDelay(strCMURL));
		}
		message.setData(bundle);
        uiUpdateHandler.sendMessage(message);
	}
	
	// ���͵���������ʲ��Խ�������߳�
	private void sendCTMessage ()
	{
		Message message = new Message();
        Bundle bundle = new Bundle();
		for (int i=0; i<3; i++) 
		{
            bundle.putLong("ctDelay"+i, getConnectTimeDelay(strCTURL));
		}
		message.setData(bundle);
        uiUpdateHandler.sendMessage(message);
	}
	
	class LoginHandler implements Runnable {
    	@Override
    	public void run() 
    	{
    		if (cbNS.isChecked())
    		{
    			sendNSMessage();
    		}
    		
    		if (cbCM.isChecked())
    		{
    			sendCMMessage();
    		}
    		
    		if (cbCT.isChecked())
    		{
    			sendCTMessage();
    		}
    	}
    }
	
	@SuppressLint("HandlerLeak")
	Handler uiUpdateHandler = new Handler() {
    	@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
    		for (int i=0; i<3; i++)
    		{
    			long nsDelay = msg.getData().getLong("nsDelay"+i);
    			long cmDelay = msg.getData().getLong("cmDelay"+i);
    			long ctDelay = msg.getData().getLong("ctDelay"+i);
    			
    			if (-1 == nsDelay) 
    			{
    				pbNS.setVisibility(View.GONE);
    				tvNS.setText(tvNS.getText() + "ʧ�� ");
    				/*
    				if (!tvNSRes.getText().toString().matches("*ͨ��*"))
    				{
    					tvNSRes.setText("ʧ��");
    					tvNSRes.setTextColor(android.graphics.Color.RED);
    				}*/
    			}
    			else if (nsDelay > 0) // 0 means no such key
    			{
    				pbNS.setVisibility(View.GONE);
    				tvNS.setText(tvNS.getText() + Long.toString(nsDelay) + "���� ");
    				tvNSRes.setTextColor(android.graphics.Color.GREEN);
    				tvNSRes.setText("ͨ��");
    			}
    			
    			if (-1 == cmDelay) 
    			{
    				pbCM.setVisibility(View.GONE);
    				tvCM.setText(tvCM.getText() + "ʧ�� ");
    				
    				
    				/*
    				if (!tvCMRes.getText().toString().matches("*ͨ��*"))
    				{
    					tvCMRes.setText("ʧ��");
    					tvCMRes.setTextColor(android.graphics.Color.RED);
    				}*/
    			}
    			else if (cmDelay > 0) // 0 means no such key
    			{
    				pbCM.setVisibility(View.GONE);
    				tvCM.setText(tvCM.getText() + Long.toString(cmDelay) + "���� ");
    				tvCMRes.setTextColor(android.graphics.Color.GREEN);
    				tvCMRes.setText("ͨ��");
    			}
    			
    			if (-1 == ctDelay) 
    			{
    				pbCT.setVisibility(View.GONE);
    				tvCT.setText(tvCT.getText() + "ʧ�� ");
    				/*
    				if (!tvCTRes.getText().toString().matches("*ͨ��*"))
    				{
    					tvCTRes.setText("ʧ��");
    					tvCTRes.setTextColor(android.graphics.Color.RED);
    				}*/
    			}
    			else if (ctDelay > 0) // 0 means no such key
    			{
    				pbCT.setVisibility(View.GONE);
    				tvCT.setText(tvCT.getText() + Long.toString(ctDelay) + "���� ");
    				tvCTRes.setTextColor(android.graphics.Color.GREEN);
    				tvCTRes.setText("ͨ��");
    			}
    		}
    		
    		Button btn = (Button)findViewById(R.id.button);
    		btn.setText("���²���");
    	};
    };
}
