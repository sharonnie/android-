package com.zw.cjl;

import com.ze.cjl.network.HttpRequest;

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
    		String result = HttpRequest.assistantLogin(strUsername, strPassword);
    		
    	}
	}
	
	// 处理结果
	private Handler loginResultHandler = new Handler() {
    	public void handleMessage(Message msg) {
    	
    	}
	};
	
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
		} else if (userType.equals("2")) {
		// 保存学员信息
			editor.putBoolean("assistantIfSave", true);			
			editor.putString("assistantUsername", strUsername);
			editor.putString("assistantPassword", strPassword);
		}
		
		editor.commit();
	}
}
