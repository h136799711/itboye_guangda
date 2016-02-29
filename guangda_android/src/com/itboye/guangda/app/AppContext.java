package com.itboye.guangda.app;

import android.app.Application;
import android.content.Context;
import android.hardware.Camera.Area;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itboye.guangda.api.ApiClient;
import com.itboye.guangda.api.StrVolleyInterface;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author gaojian
 * 
 */
public class AppContext extends Application {
	final static String TAG = "AppContext.java";
	private static String userIdCode="";
	public static String getUserIdCode() {
		return userIdCode;
	}

	public static void setUserIdCode(String userIdCode) {
		AppContext.userIdCode = userIdCode;
	}

	private static boolean login = false; // 登录状态
	private static  int loginUid = 0; // 登录用户的id
	public static String access_token; //访问令牌 
	public static RequestQueue queues;  //volley请求队列
	public static String password;//登陆密码
	public static String pathHeadImage;//头像存储路径
	public static  boolean hasHead=false;//是否已经设置头像
	 public static String getNickname() {
		return nickname;
	}

	public static void setNickname(String nickname) {
		AppContext.nickname = nickname;
	}

	public static final String APP_ID = "by56";//微信
	 public static final String AppSecret = "94124fb74284c8dae6f188c7e269a5a0";//微信
	 public static String nickname;

	public static boolean isHasHead() {
		return hasHead;
	}

	public static void setHasHead(boolean hasHead) {
		AppContext.hasHead = hasHead;
	}

	public static String getPathHeadImage() {
		return pathHeadImage;
	}

	public static void setPathHeadImage(String pathHeadImage) {
		AppContext.pathHeadImage = pathHeadImage;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		queues = Volley.newRequestQueue(getApplicationContext());
	}
	
	public boolean isLogin() {
		return login;
	}

	public void  setLogin(boolean login) {
		this.login = login;
	}

	public int getLoginUid() {
		return loginUid;
	}

	public void setLoginUid(int loginUid) {
		this.loginUid = loginUid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static RequestQueue getHttpQueues() {
		return queues;
	}
	
	public static String getAccess_token() {
		return access_token;
	}

	public static void setAccess_token(String access_token) {
		AppContext.access_token = access_token;
	}
	

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * 获取token
	 * @param mContext
	 * @param grant_type
	 * @param client_id
	 * @param client_secret
	 * @param networkHelper
	 * @throws Exception
	 */
	public void getToken(Context mContext, String grant_type, String client_id,
			 String client_secret,StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {

			try {
				ApiClient.getToken(mContext, grant_type, client_id, client_secret, networkHelper);
			} catch (Exception e) {
				Log.i(TAG, "readObject(key)");
				throw e;
			}
		}
	}
	
	public Boolean getImage(Context mContext,String curpage,String pagesize,String position,StrVolleyInterface networkHelper)throws Exception{
		if (isNetworkConnected()) {
			try {
				ApiClient.getImage(mContext,curpage,pagesize, position, networkHelper);
			} catch (Exception e) {
				Log.i(TAG, "readObject(key)");
				throw e;
			}
			return true;
		}else{
			return false;
		}
	}
}
