package com.wch.connect;

import com.example.changepage1.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {
	private EditText user;
	private EditText password;
	private Button register;
	private String info;

	private ProgressDialog dialog;
	private static Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 提示框
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在注册，请稍等...");
		dialog.setCancelable(false);
		dialog.show();
		// 启动一个线程，来完成注册任务

		new Thread(new RegisterThread()).start();
	}

	// 初始化
	public void init() {
		user = (EditText) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.pass);
		register = (Button) findViewById(R.id.register);

		register.setOnClickListener(this);
	}

	class RegisterThread implements Runnable {

		@Override
		public void run() {
			info = WebService.executeHttpGet(user.getText().toString(), password.getText().toString(), 0);
			// 完成注册任务的线程toString());
			// info =
			// WebServicePost.executeHttpPost(username.getText().toString(),
			// password.getText().toString());
			System.out.print(info);
			handler.post(new Runnable() {
				@Override
				public void run() {
					// 将返回的信息显示在界面上
					// System.out.println(info);
					// infotv.setText(info);
					 dialog.dismiss();
//					 Toast toast = Toast.makeText(Register.this, info,Toast.LENGTH_SHORT);
					// toast.setGravity(Gravity.CENTER, 0, 0);
					// toast.show();

				}
			});
			Log.d("Register", info);
		}
	}
}