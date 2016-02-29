package com.itboye.guangda.welcome;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.guangda.HomePageActivity;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.app.SharedConfig;
import com.itboye.guangda.service.TokenIntentService;
import com.itboye.guangda_android.R;

public class StartActivity extends Activity implements StrUIDataListener{
	private boolean first=false;
	private View view;
	private Context context;
	private Animation animation;
	private SharedPreferences shared;
	private static int TIME = 2000; 
	private AppContext appContext;
	private StrVolleyInterface networkHelper;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActionBar actionBar = getActionBar();  
//		actionBar.hide();
		view = View.inflate(this, R.layout.activity_main, null);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		setContentView(view);
		new SharedConfig(this);
		shared = SharedConfig.GetConfig(); 
		into();
		this.startService(new Intent(this,TokenIntentService.class));
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
	private void into() {
		//璇锋眰token
		try {	
			if (appContext.isNetworkConnected()) {
				appContext.getToken(StartActivity.this,
						"client_credentials", "by56308c60c09521",
						"aea0129a6acf48b4613f9f9c6c9c6bb3",networkHelper);
			}else {
				Toast.makeText(StartActivity.this, "请检查网络连接",
						Toast.LENGTH_LONG).show();
//				startActivity();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.v("鑾峰彇token寮傚父",e+"" );
		}
		first = shared.getBoolean("First", false);	
		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}


			@Override
			public void onAnimationEnd(Animation arg0) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent;
						if (first) {
							intent = new Intent(StartActivity.this,
									WelcomeActivity.class);
						} else {
							intent = new Intent(StartActivity.this,
									HomePageActivity.class);
						}
						startActivity(intent);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						StartActivity.this.finish();
					}
				}, TIME);
			}
		});
	}

//	public void startActivity(){
//		
//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {	}
//		}, TIME);
//		
//		Intent intent;
//		if (first) {
//			intent = new Intent(StartActivity.this,
//					WelcomeActivity.class);
//		} else {
//			intent = new Intent(StartActivity.this,
//					HomePageActivity.class);
//		}
//		startActivity(intent);
//		overridePendingTransition(R.anim.in_from_right,
//				R.anim.out_to_left);
//		finish();	
//	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
//		startActivity();
		try {
			appContext.getToken(StartActivity.this,
					"client_credentials", "by56308c60c09521",
					"aea0129a6acf48b4613f9f9c6c9c6bb3",networkHelper);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Toast.makeText(StartActivity.this, "访问服务器失败", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		String access_token = null;
		JSONObject jsonObject = null;
		int code = -1;
		try {
			jsonObject=new JSONObject(data);
			code = jsonObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			try {
			JSONObject	tempdata=(JSONObject) jsonObject.get("data");
				access_token = tempdata.getString("access_token");
				Log.v("鑾峰彇token",access_token+"1");
				AppContext.setAccess_token(access_token);
//				startActivity();
			} catch (JSONException e) {
//				startActivity();
				e.printStackTrace();
			}
			//Toast.makeText(StartActivity.this, "鑾峰彇token鎴愬姛锛? + access_token, Toast.LENGTH_LONG)
			//.show();
		} else {
//		     startActivity();
		//	Toast.makeText(StartActivity.this, "璁块棶鏈嶅姟鍣ㄥけ璐ワ紝璇烽噸鏂板皾璇", Toast.LENGTH_LONG)
			//.show();
		}
	}

}
