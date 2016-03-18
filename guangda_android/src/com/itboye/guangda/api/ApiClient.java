package com.itboye.guangda.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.app.Constant;

/**
 * API瀹㈡埛绔帴鍙ｏ細鐢ㄤ簬璁块棶缃戠粶鏁版嵁
 * @author gaojian
 *
 */
public class ApiClient {
	public static final String UTF_8 = "UTF-8";
	
	public static StringRequest stringRequest;
	
	public static void getImage(Context mContext,String curpage,String pagesize,String position,StrVolleyInterface networkHelper){
	    String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Banners/query?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		params.put("curpage", curpage);
		params.put("pagesize", pagesize);
		params.put("position", position);
        VolleyRequest.StrRequestPost(mContext, url, "getImage",params, networkHelper);
	}
	
	public static void getToken(Context mContext, String grant_type, String client_id,
			String client_secret, StrVolleyInterface networkHelper) {
		String url = Constant.URL+"/Token/index ";
		Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type",grant_type);
        params.put("client_id",client_id);
        params.put("client_secret", client_secret);
        VolleyRequest.StrRequestPost(mContext, url, "getToken",params, networkHelper);
		
	} 

	//获取验证码
	
	public static void getCheckCode(Context context,String mobile,String type,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Message/send?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("mobile",mobile);
        params.put("type",type);
        VolleyRequest.StrRequestPost(context, url, "getCode",params, networkHelper);
	}
	
	//验证验证码
	public static void judgeCheckCode(Context context,String username,String checkcode,String type,String userId, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Message/checkCode?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("type",type);
        params.put("code", checkcode);
       params.put("uid", userId);
        VolleyRequest.StrRequestPost(context, url, "judgeCode",params, networkHelper);
	}
	
	//用户忘记密码 更新密码
	public static void forgetPassword(Context context,String username,String password,String  code, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/updatePsw?access_token="+access_token+"";
		System.out.println(url);
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		Log.v("用户名", username);
		
		Log.v("验证码", code);
			byte[] encode;
			try {
			 encode = Base64.encode(password.getBytes(UTF_8), Base64.NO_WRAP);
			 password = new String(encode);  
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	       
	        Log.v("密码","base 64 encode = " + password);  
	
		Log.v("原密码", password);
        params.put("username",username);
        params.put("psw",password);
        params.put("code", code);
        VolleyRequest.StrRequestPost(context, url, "forgetPass",params, networkHelper);
	}
	
	//发送密码，用户完成用手机号的注册
	public static void finishRegisit(Context context,String username,
			String  password, String idcode,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/register?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		System.out.println(username);
		System.out.println(password);
        params.put("username",username);  
        params.put("password",password);
        params.put("invite_code", idcode);
        params.put("from", "0");
        params.put("type", "3");
        VolleyRequest.StrRequestPost(context, url, "finishRegisit",params, networkHelper);
	}
	

	//发送密码，用户完成用手机号的注册
	public static void finishRegisit(Context context,String username,
			String  password, String idcode,String device_id,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/register?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		System.out.println(username);
		System.out.println(password);
        params.put("username",username);  
        params.put("password",password);
        params.put("invite_code", idcode);
        params.put("from", "0");
        params.put("device_id", device_id);
        params.put("device_type", "0");
        params.put("type", "3");
        VolleyRequest.StrRequestPost(context, url, "finishRegisit",params, networkHelper);
	}

	
	
	//用户信息修改
		public static void modifyPersonal(Context context,String uid,String  sex, String nickname,
				String realname,String idnumber,String qq,String head,String birthday,String email,
				String moble,String card_no ,StrVolleyInterface networkHelper){
			String access_token=AppContext.getAccess_token();
			String url = Constant.URL+"/User/update?access_token="+access_token;
			Map<String,String> params = new HashMap<String, String>();
			//params.put("access_token", access_token);
	        params.put("uid",uid);  
	        params.put("sex",sex);
	        params.put("nickname", nickname);
	        params.put("realname", realname);
	        params.put("idnumber",idnumber);  //身份证号
	        params.put("qq",qq);
	        params.put("head",head);  
	        params.put("birthday",birthday);
	        params.put("email", email);
	        params.put("moble", moble);
	        params.put("card_no", card_no);
	        VolleyRequest.StrRequestPost(context, url, "modifyPersonal",params, networkHelper);
		}
	//通过账号和密码，完成用户登陆
	public static void Login(Context context,String name,String password,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/login?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("username",name);
        params.put("password",password);
    //    params.put("from", "0");
    //    params.put("type", "4");
        VolleyRequest.StrRequestPost(context, url, "Login",params, networkHelper);
        //VolleyRequest.StrRequestPost(context, url, "getCheckCode",params, networkHelper);
	}
}
