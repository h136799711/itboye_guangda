package com.itboye.guangda.geren;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.guangda.api.ApiClient;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda_android.R;

public class PasswordActivity extends Activity implements StrUIDataListener{
	EditText etName;//用户名
	EditText etPassword;//用户密码
	Button btnRegist;//注册按钮
	ImageView ivBack;//返回按钮
	String username;//用户名，需要传递到其他activity
	private int state;//判断从上个activity传递来的验证码接口状态
   TextView  tvPassword;//title文字
	String checkCode;//验证码
	Intent intent;
	private StrVolleyInterface networkHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getActionBar();  
//		actionBar.hide();
        setContentView(R.layout.activity_password);
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		 initId(this);
		btnRegist.setOnClickListener(regisitOnClickListener);
		ivBack.setOnClickListener(backOnClickListener);
    }
    
    //返回按钮
    OnClickListener backOnClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
    //注册按钮
    OnClickListener regisitOnClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String name=etName.getText().toString();
			String password=etPassword.getText().toString();
			String idcode="";
			checkCode=intent.getStringExtra("code");
			username=intent.getStringExtra("username");
			if (intent.getStringExtra("idcode")!=null) {
				idcode=intent.getStringExtra("idcode");
			}
			System.out.println(idcode+"PPPPPPPPPPPPPPPPPPPP");
			System.out.println(username);
			System.out.println(password);
			if (name.equals(password)) {
				switch (state) {
				case 1:
					ApiClient.finishRegisit(PasswordActivity.this,username, password,idcode, networkHelper);//用户注册来的
					break;
				case 2:
					ApiClient.forgetPassword(PasswordActivity.this, username, password, checkCode, networkHelper);
					break;
				default:
					break;
				}
			}	
			else {
				Toast.makeText(PasswordActivity.this, "两次密码输入不一致" , Toast.LENGTH_LONG)
				.show();
			}
		}
	};

	private void initId(PasswordActivity regsitedActivity) {
		// TODO Auto-generated method stub
		etName=(EditText)findViewById(R.id.et_name);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		etPassword=(EditText)findViewById(R.id.et_password);
		btnRegist=(Button)findViewById(R.id.btn_regist);
		tvPassword=(TextView)findViewById(R.id.tv_password);
		intent=getIntent();
		state=intent.getIntExtra("Flags", 0);
		System.out.println("state:"+state);
		switch (state) {
		case 2:
			tvPassword.setText("重设密码");
			btnRegist.setText("确定");
			break;

		default:
			break;
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Log.v("注册接口",error.toString());
	    Log.e("LOGIN-ERROR", error.getMessage(), error);
	    byte[] htmlBodyBytes = error.networkResponse.data;
	    Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
		Toast.makeText(PasswordActivity.this, "验证密码请求发生异常，请重新尝试" , Toast.LENGTH_LONG)
		.show();
	}

	@SuppressLint("CommitPrefEdits")
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		String userId = null;
		int code = -1;
		try {
			jsonObject=new JSONObject(data);
			userId=jsonObject.getString("data");
			code=jsonObject.getInt("code");
			System.out.println("code:"+code);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if (code == 0) {
			switch (state) {
			case 1:
				Toast.makeText(PasswordActivity.this, "注册成功" , Toast.LENGTH_LONG).show();
				state=0;
				//发送广播结束前两个activity
				Intent intent = new Intent();  
				intent.setAction("KILL_ACTIVITY");  
				sendBroadcast(intent); 
//				finish();
//				startActivity(new Intent(PasswordActivity.this,LoginActivity.class));
//				finish();
//				
				break;
			case 2:
				Toast.makeText(PasswordActivity.this, "密码更新成功" , Toast.LENGTH_LONG).show();
				state=0;
				Intent intent1 = new Intent();  
				intent1.setAction("KILL_ACTIVITY");  
				sendBroadcast(intent1); 
				break;

			default:
				break;
			}
			Intent intent=new Intent(PasswordActivity.this,LoginActivity.class);
			intent.putExtra("username", username);
			finish();
			startActivity(intent);
		} 
		else {
			if(userId != null){
				Toast.makeText(PasswordActivity.this, userId,Toast.LENGTH_LONG)
				.show();
			}else{
				Toast.makeText(PasswordActivity.this, "该账户已经注册" ,Toast.LENGTH_LONG)
				.show();
			}
		}
	}
}
