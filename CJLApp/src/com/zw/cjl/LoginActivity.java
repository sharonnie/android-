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
			// ��ʾ������Ա��¼
			final TextView tv = (TextView)findViewById(R.id.loginText);
			tv.setText(R.string.coach_query);
			
			// ��ȡ�����������Ա��¼��Ϣ
			strUsername = shared.getString("coachIdentity", "");
			strPassword = shared.getString("coachPhone", "");
			bIfSave = shared.getBoolean("coachIfSave", false);
			
			cbRememberPassword.setChecked(bIfSave);
		} else if (userType.equals("2")) {
			// ��ʾѧԱ��¼
			final TextView tv = (TextView)findViewById(R.id.loginText);
			tv.setText(R.string.student_query);
			
			// ��ȡ�����ѧԱ��¼��Ϣ
			strUsername = shared.getString("studentIdentity", "");
			strPassword = shared.getString("studentPhone", "");
			bIfSave = shared.getBoolean("studentIfSave", false);
			
			cbRememberPassword.setChecked(bIfSave);
		} else if (userType.equals("3")) {
			// ��ʾҵ��Ա��¼
			final TextView tv = (TextView)findViewById(R.id.loginText);
			tv.setText(R.string.assistant_query);
			
			// ��ȡ�����ҵ��Ա��¼��Ϣ
			strUsername = shared.getString("assistantUsername", "");
			strPassword = shared.getString("assistantPassword", "");
			bIfSave = shared.getBoolean("assistantIfSave", false);
			
			cbRememberPassword.setChecked(bIfSave);
		}
		
		// ����û���������
		if (bIfSave) {
			etUsername.setText(strUsername);
			etPassword.setText(strPassword);	
		}
		
		// �����û���ʱ�Զ��������
		etUsername.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				etPassword.setText("");
				return false;
			}
		});
	}
	
	//��Ӧ�����¼
	public void onClickLogin(View v) {
		// ��������
		savePassword();
		
		// ��ʾ�ȴ���
		progressDlg = ProgressDialog.show(this, "���Ե�", "���ڵ�½��", false, true); 
				
		// ����һ���߳�loginThread�е�¼����¼�Ľ����resultHandler����
		Thread loginThread = new Thread(new LoginThread());
        loginThread.start();
	}
	
	// ��¼�߳�
	class LoginThread implements Runnable {
    	@Override
    	public void run() {
    		String result = HttpRequest.assistantLogin(strUsername, strPassword);
    		
    	}
	}
	
	// ������
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
		
		// ����������
		if (false == bIfSave) {
			return;
		}
		
		if (userType.equals("1")) {
			// ����������Ա��Ϣ
			editor.putBoolean("coachIfSave", true);
			editor.putString("coachIdentity", strUsername);
			editor.putString("coachPhone", strPassword);
		} else if (userType.equals("2")) {
			// ����ѧԱ��Ϣ
			editor.putBoolean("studentIfSave", true);			
			editor.putString("studentIdentity", strUsername);
			editor.putString("studentPhone", strPassword);
		} else if (userType.equals("2")) {
		// ����ѧԱ��Ϣ
			editor.putBoolean("assistantIfSave", true);			
			editor.putString("assistantUsername", strUsername);
			editor.putString("assistantPassword", strPassword);
		}
		
		editor.commit();
	}
}
