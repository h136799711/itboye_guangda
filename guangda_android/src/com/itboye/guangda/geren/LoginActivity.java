package com.itboye.guangda.geren;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.guangda.Fragment.GeRenFragment;
import com.itboye.guangda.api.ApiClient;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.app.Constant;
import com.itboye.guangda.bean.User;
import com.itboye.guangda_android.R;
public class LoginActivity extends Activity implements StrUIDataListener,OnClickListener {
	TextView tvRegist;//注册view
	Button btnLogin;//登陆按钮
	EditText etName;//用户账号，一般为手机号
	EditText etPassword;//用户密码/明文
   TextView tvForget;//忘记密码
   TextView tvQuXiao;//取消
   private ImageView ivWeixin;//微信登陆
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Gson gson = new Gson();
	private ProgressDialog dialog;
	private CloseReceiver closeReceiver;
	
//	private IWXAPI api;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		ActionBar actionBar = getActionBar();  
//		actionBar.hide();
		setContentView(R.layout.activity_login);
		initId(this);
		dialog = new ProgressDialog(LoginActivity.this);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		
		tvRegist.setOnClickListener(this);
		btnLogin.setOnClickListener(new LoginListener());
		tvForget.setOnClickListener(this);
		tvQuXiao.setOnClickListener(this);
		
		//接受结束activity的广播
		closeReceiver = new CloseReceiver();  
		IntentFilter intentFilter = new IntentFilter("KILL_ACTIVITY");  
		registerReceiver(closeReceiver, intentFilter);  
		//ivWeixin.setOnClickListener(this);
		//注册到微信
//		api=WXAPIFactory.createWXAPI(this, AppContext.APP_ID,true);
//		api.registerApp(AppContext.APP_ID);
	}
	
	 public class CloseReceiver extends BroadcastReceiver  
	 {   
	        @Override  
	        public void onReceive(Context context, Intent intent)  
	        {  
	         finish();  
	        }
	 }  
	 

	 @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
		 	unregisterReceiver(closeReceiver);
			super.onDestroy();
		}

	private void initId(LoginActivity loginActivity) {
		// TODO Auto-generated method stub
		
		SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
        String account = sp.getString(Constant.MY_ACCOUNT, "");
        String pass = sp.getString(Constant.MY_PASSWORD, "");  
        Log.v("账号",account);
        Log.v("密码", pass);
      /*  //对密码进行AES解密  
        try{  
            pass = AESEncryptor.decrypt("41227677", pass);  
        }catch(Exception ex){  
            Toast.makeText(this, "获取密码时产生解密错误!", Toast.LENGTH_LONG).show();
            pass = "";  
        }  */
  //      ivWeixin=(ImageView) findViewById(R.id.iv_weixin);
		 tvQuXiao=(TextView)findViewById(R.id.tv_quxiao);
        tvForget=(TextView)findViewById(R.id.tv_forget);
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		btnLogin=(Button)findViewById(R.id.btn_login);
		etName=(EditText)findViewById(R.id.et_name);
		etPassword=(EditText)findViewById(R.id.et_password);
		etName.addTextChangedListener(new TextChange());
		etPassword.addTextChangedListener(new TextChange());
		etName.setText(account);
		etPassword.setText(pass);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_forget:
			Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
			intent.putExtra("forgetFlag", "forget");
			startActivity(intent);
			break;
		case R.id.tv_regist:
			Intent intentRegist=new Intent(LoginActivity.this,RegistActivity.class);
			LoginActivity.this.startActivity(intentRegist);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;

		case R.id.tv_quxiao:
			finish();;//结束当前webview Activity
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
//		case R.id.tv_forget:
//			Intent forget=new Intent(LoginActivity.this,RegistActivity.class);
//			forget.putExtra("forgetFlag", "forget");
//			LoginActivity.this.startActivity(forget);
//			overridePendingTransition(R.anim.in_from_right,
//					R.anim.out_to_left);
//			break;

		default:
			break;
		}
	}
	//各种监听
	

	
	class LoginListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//登陆请求
	        String name = LoginActivity.this.etName.getText().toString();
	        String password = LoginActivity.this.etPassword.getText().toString();
			ApiClient.Login(LoginActivity.this, name, password, networkHelper);
			dialog.setMessage("正在登录...");
	        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        dialog.show();
		}
	}


	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		appContext.setLogin(false);
		Toast.makeText(LoginActivity.this, "登陆发生异常", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			//String userId=jsonObject.getString("data");
		//	System.out.println("code=" + data.toString());
			User user = gson.fromJson(content, User.class);
			appContext.setLogin(true);
			//appContext.setPathHeadImage(user.get)
			AppContext.setUserIdCode(user.getIdcode());
			AppContext.setHasHead(true);
			appContext.setLoginUid(user.getId());
			appContext.setPassword(user.getPassword());
			AppContext.setNickname(user.getNickname());
			AppContext.setPathHeadImage(user.getHead());
			String nickname=user.getNickname();
			String head=user.getHead();
			//返回显示昵称
			if (nickname.equals("")) {
				nickname=user.getUsername();
				System.out.println(nickname);
			}
			System.out.println(nickname);
			Intent intent=getIntent();
			intent.putExtra("NAME", nickname);
			intent.putExtra("Head", head);
			this.setResult(1000, intent);
			
			System.out.println(appContext.getPassword());
			Log.v("用户id", user.getId()+"");
		    String use = etName.getText().toString();   
		    String pas = etPassword.getText().toString(); 
		/*	//并使用AES加密算法给密码加密。
	        String use = etName.getText().toString().trim();   
	        String pas = etPassword.getText().toString().trim(); 
	       try{  
	        	pas = AESEncryptor.encrypt("41227677", pas);  
	        }catch(Exception ex){  
	            Toast.makeText(this, "给密码加密时产生错误!", Toast.LENGTH_SHORT).show();
	            pas = "";  
	        }  */
	        //获取名字为“MY_PREFERENCES”的参数文件对象。  
	        SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
	        //使用Editor接口修改SharedPreferences中的值并提交。  
	        Editor editor = sp.edit();  
	        editor.putString(Constant.MY_NICKNAME,user.getNickname());
	        editor.putString(Constant.MY_ACCOUNT, use);  
	        editor.putString(Constant.MY_PASSWORD,pas);  
	        editor.putString(Constant.MY_USERID, user.getId()+"");
	        editor.putBoolean(Constant.IS_LOGIN, true);
	        editor.commit();
	        dialog.dismiss();
	        sp.getString(Constant.MY_ACCOUNT, "");
	        System.out.println(data.toString());
	        finish();
	      //  startActivity(new Intent(LoginActivity.this,GeRenFragment.class));
	        overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
	        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
			.show();
		} else {
			dialog.dismiss();
			appContext.setLogin(false);
			Toast.makeText(LoginActivity.this, "登陆失败，请检查用户名和密码", Toast.LENGTH_LONG)
			.show();
			System.out.println("code=" + data.toString());
		}
	}
	
	// EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                int count) {

            boolean Sign2 = etName.getText().length() > 0;
            boolean Sign3 = etPassword.getText().length() > 0;

            if (Sign2 & Sign3) {
            	btnLogin.setTextColor(0xFFFFFFFF);
            	btnLogin.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
            	btnLogin.setTextColor(0xFF808080);
            	btnLogin.setEnabled(false);
            }
        }

    }



}
