package com.zw.cjl;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ze.cjl.network.HttpRequest;

public class LoginActivity extends Activity {
	
	private String userType;
	
	private EditText etUsername;
	private EditText etPassword;
	private CheckBox cbRememberPassword;

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	
	String strUsername;
	String strPassword;
	boolean bIfSave;
	
	private ProgressDialog progressDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getViews();
		
		Intent intent = getIntent();
		userType = intent.getStringExtra("userType");
		
		shared = this.getPreferences(Activity.MODE_PRIVATE);
		editor = shared.edit();
		
		if (userType.equals("1")) {
			// 显示助理考试员登录
			final TextView tv = (TextView)findViewById(R.id.loginText);
			tv.setText(R.string.coach_query);
			
			// 读取保存的助理考试员登录信息
			strUsername = shared.getString("coachIdentity", "");
			strPassword = shared.getString("coachPhone", "");
			bIfSave = shared.getBoolean("coachIfSave", false);
			
			cbRememberPassword.setChecked(bIfSave);
		} else if (userType.equals("2")) {
			// 显示学员登录
			final TextView tv = (TextView)findViewById(R.id.loginText);
			tv.setText(R.string.student_query);
			
			// 读取保存的学员登录信息
			strUsername = shared.getString("studentIdentity", "");
			strPassword = shared.getString("studentPhone", "");
			bIfSave = shared.getBoolean("studentIfSave", false);
			
			cbRememberPassword.setChecked(bIfSave);
		} else if (userType.equals("3")) {
			// 显示业务员登录
			final TextView tv = (TextView)findViewById(R.id.loginText);
			tv.setText(R.string.assistant_query);
			
			// 读取保存的业务员登录信息
			strUsername = shared.getString("assistantUsername", "");
			strPassword = shared.getString("assistantPassword", "");
			bIfSave = shared.getBoolean("assistantIfSave", false);
			
			cbRememberPassword.setChecked(bIfSave);
		}
		
		// 填充用户名与密码
		if (bIfSave) {
			etUsername.setText(strUsername);
			etPassword.setText(strPassword);	
		}
		
		// 更改用户名时自动清空密码
		etUsername.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				etPassword.setText("");
				return false;
			}
		});
	}
	
	//响应点击登录
	public void onClickLogin(View v) {
		// 保存密码
		savePassword();
		
		// 显示等待框
		progressDlg = ProgressDialog.show(this, "请稍等", "正在登陆中", false, true); 
				
		// 在另一个线程loginThread中登录，登录的结果由resultHandler处理
		Thread loginThread = new Thread(new LoginThread());
        loginThread.start();
	}
	
	// 登录线程
	class LoginThread implements Runnable {
    	@Override
    	public void run() {
    		String result = null;
    		
    		// 助理考试员登录
    		if (userType.equals("1"))
    		{
    			result = HttpRequest.coachLogin(strUsername, strPassword);
    		}
    		// 学员登录
    		else if (userType.equals("2"))
    		{
    			result = HttpRequest.studentLogin(strUsername, strPassword);
    		}
    		// 业务员登录
    		else if (userType.equals("3"))
    		{
    			result = HttpRequest.assistantLogin(strUsername, strPassword);
    		}
    		
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
					
					if (userType.equals("2")) //学员界面
					{
						String cityDivision = null;
						try {
							cityDivision = jsonObj.getString("cityDivision");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						 
						startStudentActivity(cityDivision);
					}
					else if (userType.equals("1")) { // 助理考试员界面
						startCoachActivity();
					}
					else if (userType.equals("3")) { // 业务员界面
						String jxid = null;
						String sfzh = null;
						try {
							jxid = jsonObj.getString("jxid");
							sfzh = jsonObj.getString("sfzh");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
						startAssistantActivity(sfzh, jxid);
					}
					else
					{
						hasError = true;
						resultMsg = "不存在的用户类型";
					}
					
	                
	                // 登陆后关闭登陆页面和欢迎页面
	                LoginActivity.this.finish();
	                WelcomeActivity.instance.finish();
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
                loginResultHandler.sendMessage(message);
			}
    		
    	}
	}
	
	// 处理结果
	private Handler loginResultHandler = new Handler() {
    	public void handleMessage(Message msg) {
    		String resultMsg = msg.getData().getString("resultMsg");
    		
    		if(progressDlg.isShowing()) { 
    			progressDlg.dismiss(); 
    		}
    		
    		Toast.makeText(getApplicationContext(), resultMsg, Toast.LENGTH_SHORT).show();
    	}
	};
	
	private void startStudentActivity (String cityDivision)
	{
		if (null == cityDivision)
			return;
		
		Intent intent = new Intent();
		//intent.setClass(getApplicationContext(), StudentActivity.class);
		
		// 传入身份证和城市信息
		Bundle bundle = new Bundle();	
		bundle.putString("userType", "2");
        bundle.putString("identity", strUsername);
        bundle.putString("cityDivision", cityDivision);
        intent.putExtras(bundle);
        startActivity(intent);
	}
	
	private void startCoachActivity ()
	{
		Intent intent = new Intent();
		//intent.setClass(getApplicationContext(), CoachActivity.class);
		
		// 传入身份证和城市信息
		Bundle bundle = new Bundle();		
		bundle.putString("userType", "1");
        bundle.putString("identity", strUsername);
        //bundle.putString("cityDivision", cityDivision);
        intent.putExtras(bundle);
        startActivity(intent);
	}
	
	private void startAssistantActivity (String sfzh, String jxid)
	{
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), MainActivity.class);
		
		// 传入身份证和城市信息
		Bundle bundle = new Bundle();
		bundle.putString("userType", "3");
        bundle.putString("identity", sfzh);
        bundle.putString("jxid", jxid);
        intent.putExtras(bundle);
        startActivity(intent);
	}
	
	private void getViews() {
		cbRememberPassword = (CheckBox)findViewById(R.id.ifSave);
		etUsername = (EditText)findViewById(R.id.username);
		etPassword = (EditText)findViewById(R.id.password);
	}
	
	private void savePassword() {
		strUsername = etUsername.getText().toString();
		strPassword = etPassword.getText().toString();
		bIfSave = cbRememberPassword.isChecked();
		
		// 不保存密码
		if (false == bIfSave) {
			return;
		}
		
		if (userType.equals("1")) {
			// 保存助理考试员信息
			editor.putBoolean("coachIfSave", true);
			editor.putString("coachIdentity", strUsername);
			editor.putString("coachPhone", strPassword);
		} else if (userType.equals("2")) {
			// 保存学员信息
			editor.putBoolean("studentIfSave", true);			
			editor.putString("studentIdentity", strUsername);
			editor.putString("studentPhone", strPassword);
		} else if (userType.equals("3")) {
		// 保存学员信息
			editor.putBoolean("assistantIfSave", true);			
			editor.putString("assistantUsername", strUsername);
			editor.putString("assistantPassword", strPassword);
		}
		
		editor.commit();
	}
}
