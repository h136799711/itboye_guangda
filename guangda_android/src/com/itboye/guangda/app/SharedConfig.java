package com.itboye.guangda.app;

import android.content.Context;
import android.content.SharedPreferences;
//保存app中常用的一些变量
public class SharedConfig {

	Context context;
	static SharedPreferences shared;
	public SharedConfig(Context context){
		this.context=context;
		shared=context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	public static SharedPreferences GetConfig(){
		return shared;
	}
	public void ClearConfig(){
		shared.edit().clear().commit();
	}
}

