package com.wch.connect;

import org.json.JSONObject;

import com.example.changepage1.R;
import com.example.sj.MainActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	// 登录按钮
	private Button logbtn;
	private Button qq_login;
	// 调试文本，注册文本
	private TextView infotv, regtv;
	// 显示用户名和密码
	EditText username, password;
	// 创建等待框
	private ProgressDialog dialog;
	// 返回的数据
	private String info;
	// 返回主线程更新数据
//	private static Handler handler = new Handler();

	//
	private static final String TAG = MainActivity.class.getName();
	public static String mAppid = "1105186875";
	
	private TextView mUserInfo;
	private ImageView mUserLogo;
	private UserInfo mInfo;
	
	public static Tencent mTencent;
	
	private static boolean isServerSideLogin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		initViews();

	}

	private void initViews() {

		// 获取控件
		username = (EditText) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.pass);
		logbtn = (Button) findViewById(R.id.loginning);
		regtv = (TextView) findViewById(R.id.register);
        qq_login = (Button) findViewById(R.id.qq_login);
		// 设置按钮监听器
		logbtn.setOnClickListener(this);
		regtv.setOnClickListener(this);
		qq_login.setOnClickListener(this);

		if (mTencent == null) {
			mTencent = Tencent.createInstance(mAppid, this);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginning:
			// 检测网络，无法检测wifi
			// if (!checkNetwork()) {
			// Toast toast = Toast.makeText(Login.this,网络未连接",
			// Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
			// toast.show();
			// break;
			// }
			// 提示框
			dialog = new ProgressDialog(this);
			dialog.setTitle("提示");
			dialog.setMessage("正在登录，请稍等...");
			dialog.setCancelable(false);
			dialog.show();
			// 创建子线程，分别进行get和Post传输数据
			new Thread(new LoginThread()).start();
			break;
		case R.id.register:
			Intent regItn = new Intent(Login.this, Register.class);
			startActivity(regItn);
			break;
		case R.id.qq_login:
			onClickLogin();
			break;
		}
	}

	// 子线程接收数据，主线程修改数据
	class LoginThread implements Runnable {
		@Override
		public void run() {
			info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString());
			System.out.println(info);
			if (info != null) {
				dialog.dismiss();
				Login.this.finish();
			}

		}
	}

	// 检测网络
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent userIntent = new Intent(Login.this, MainActivity.class);
		userIntent.putExtra("info", info);
		startActivity(userIntent);
	}

	
	// QQ 登录代码

//	private ArrayList<String> handlePrizeResponse(String response) {
//		ArrayList<String> shareIdList = new ArrayList<String>();
//		if (TextUtils.isEmpty(response)) {
//			return null;
//		}
//		try {
//			JSONObject obj = new JSONObject(response);
//			int code = obj.getInt("ret");
//			int subCode = obj.getInt("subCode");
//			if (code == 0 && subCode == 0) {
//				JSONObject data = obj.getJSONObject("data");
//				JSONArray prizeList = data.getJSONArray("prizeList");
//				int size = prizeList.length();
//				JSONObject prize = null;
//				for (int i = 0; i < size; i++) {
//					prize = prizeList.getJSONObject(i);
//					if (null != prize) {
//						shareIdList.add(prize.getString("shareId"));
//					}
//				}
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			return null;
//		}
//		return shareIdList;
//	}

	

	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Log.d("Login", "sendBroadcast");
//					Intent intent = new Intent("com.wch.broadcast.QQLOGINBROADCAST");
//					intent.putExtra("qq_info", (String)response);
//					sendBroadcast(intent);
					// Message msg = new Message();
					// msg.obj = response;
					// msg.what = 0;
					// mHandler.sendMessage(msg);
					// new Thread() {
					//
					// @Override
					// public void run() {
					// JSONObject json = (JSONObject) response;
					// if (json.has("figureurl")) {
					// Bitmap bitmap = null;
					// try {
					// bitmap = 
					// Util.getbitmap(json.getString("figureurl_qq_2"));
					// } catch (JSONException e) {
					//
					// }
					// Message msg = new Message();
					// msg.obj = bitmap;
					// msg.what = 1;
					// mHandler.sendMessage(msg);
					// }
					// }
					//
					// }.start();
					Login.this.finish();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);
		}
//		} else {
//			mUserInfo.setText("");
//			mUserInfo.setVisibility(android.view.View.GONE);
//			mUserLogo.setVisibility(android.view.View.GONE);
//		}
	}

//	Handler mHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == 0) {
//				JSONObject response = (JSONObject) msg.obj;
//				if (response.has("nickname")) {
//					try {
//						mUserInfo.setVisibility(android.view.View.VISIBLE);
//						mUserInfo.setText(response.getString("nickname"));
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			} else if (msg.what == 1) {
//				Bitmap bitmap = (Bitmap) msg.obj;
//				mUserLogo.setImageBitmap(bitmap);
//				mUserLogo.setVisibility(android.view.View.VISIBLE);
//			}
//		}
//
//	};

	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", loginListener);
			isServerSideLogin = false;
			Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
			if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
				mTencent.logout(this);
				mTencent.login(this, "all", loginListener);
				isServerSideLogin = false;
				Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
				return;
			}
			mTencent.logout(this);
			updateUserInfo();
		}
	}

	public static String getAppid() {
		if (TextUtils.isEmpty(mAppid)) {
			mAppid = "1105186875";
		}

		return mAppid;
	}

	public static boolean ready(Context context) {
		if (mTencent == null) {
			return false;
		}
		boolean ready = mTencent.isSessionValid() && mTencent.getQQToken().getOpenId() != null;
		if (!ready) {
			Toast.makeText(context, "login and get openId first, please!", Toast.LENGTH_SHORT).show();
		}
		return ready;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
		if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
			Login.this.finish();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
			updateUserInfo();
		}
	};

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {

				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {

				return;
			}
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
		}

		@Override
		public void onCancel() {

		}
	}
}
