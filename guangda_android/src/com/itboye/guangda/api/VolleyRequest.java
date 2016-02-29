package com.itboye.guangda.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.itboye.guangda.app.AppContext;

public class VolleyRequest {
	public static StringRequest strRequest;
	public static JsonRequest<JSONObject> jsonRequest;
	public static JsonRequest<JSONArray> jsonArrayRequest;
	public static StringRequest stringRequest;
	public static Context context;
	public AppContext appContext;

	public static void StrRequestGet(Context mContext, String url, String tag,
			StrVolleyInterface vif) {
		AppContext.getHttpQueues().cancelAll(tag);
		strRequest = new StringRequest(Method.GET, url, vif, vif){

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "charset=UTF-8");

				return headers;
			}
			
		};
		strRequest.setTag(tag);
		AppContext.getHttpQueues().add(strRequest);
		AppContext.getHttpQueues().start();
	}

	public static void StrRequestPost(Context mContext, String url, String tag,
			final Map<String, String> params, StrVolleyInterface vif) {
		
		JSONObject jsonObject = new JSONObject(params);
		System.out.println("Map转化成JSON="+jsonObject);

		strRequest = new StringRequest(Method.POST, url, vif, vif){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				// TODO Auto-generated method stub
				return super.getHeaders();
			}
			
		};
		
		strRequest.setTag(tag);
		AppContext.getHttpQueues().add(strRequest);
		AppContext.getHttpQueues().start();
	}
	
}
