package com.itboye.guangda.Fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.itboye.guangda.WebViewActivity;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.utils.BitmapCache;
import com.itboye.guangda_android.R;

public class GouWuFragment extends Fragment implements OnClickListener,StrUIDataListener{
	private List<String> urList=new ArrayList<String>();
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Boolean YesOrNo;
	private String[] urlImage;
	private NetworkImageView[] imageViews;
	private View view;
	private int id[]={R.id.image21,R.id.image22,R.id.image23,R.id.image24,R.id.image25,R.id.image26,
			R.id.image27,R.id.image28,R.id.image29,R.id.image210,R.id.image211,R.id.image212,R.id.image213};
	private String[] url={
			"http://m.kaola.com/activity/h5/4952.html",
			"http://m.kaola.com/activity/h5/4938.html",
			"http://m.kaola.com/product/9299.html",
			"http://m.kaola.com/product/11783.html",
			"http://mp.weixin.qq.com/s?__biz=MzA3NDY1NzAyNg==&mid=209401901&idx=1&sn=429bfab7fac42f46306f00035dfd9fdf#rd",
			"http://mp.weixin.qq.com/s?__biz=MzA3NDY1NzAyNg==&mid=209825204&idx=1&sn=ab1fc575a817335bc34bb2fcf57befe9#rd",
			"http://mp.weixin.qq.com/s?__biz=MzA3NDY1NzAyNg==&mid=209825514&idx=1&sn=6e9a345da6125718f602afb53740245b#rd",
			"http://mp.weixin.qq.com/s?__biz=MzA3NDY1NzAyNg==&mid=209826299&idx=1&sn=39a64f27b7fb222f2e9028803bfacc58#rd",
			"http://mp.weixin.qq.com/s?__biz=MzA5MDUwMzEwNQ==&mid=209334274&idx=1&sn=45af274a56be63708a9fbc258c6e421d&scene=0#rd",
			"http://mp.weixin.qq.com/s?__biz=MzA5MDUwMzEwNQ==&mid=204564795&idx=1&sn=459f1cdf149888ec4d9198c6ee3ea438#wechat_redirect",
			"http://mp.weixin.qq.com/s?__biz=MzA5MDUwMzEwNQ==&mid=204562416&idx=1&sn=5b89f85d6ff747fcf44e11bc3a10ad21#wechat_redirect",
			"http://mp.weixin.qq.com/s?__biz=MjM5MzQxMjE0MQ==&mid=253789446&idx=1&sn=a578cd9064af871525ae9ff0f2be1930&scene=0#rd",
			""
			};
	private ImageView imageView21,imageView22,imageView23,imageView24,imageView25,
	imageView26,imageView27,imageView28,imageView29,imageView210,imageView211,imageView212,
	imageView213;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_gouwu, null);
		initView(view);
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(this);
		appContext = (AppContext) getActivity().getApplication();
	}


	private void initView(View view) {
		// TODO Auto-generated method stub
		imageViews=new NetworkImageView[id.length];
		urlImage=new String[id.length];
		url=new String[id.length];
  try {
		YesOrNo = appContext.getImage(getActivity(), 1+"", 20+"", "app_life", networkHelper);
		if (!YesOrNo) { // 如果没联网
			Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT)
					.show();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			
		
		switch (v.getId()) {
		case  R.id.image21:
			startIntent(0);
			
			//Intent urlIntent = new Intent(Intent.ACTION_VIEW,
				//    Uri.parse(urList.get(0)));
			//	 startActivity(urlIntent);
			break;
		case  R.id.image22:
			startIntent(1);
			break;
		case  R.id.image23:
			startIntent(2);
			break;
		case  R.id.image24:
			startIntent(3);
			break;
		case  R.id.image25:
			startIntent(4);
			break;
		case  R.id.image26:
			startIntent(5);
			break;
		case  R.id.image27:
			startIntent(6);
			break;
		case  R.id.image28:
			startIntent(7);
			break;
		case  R.id.image29:
			startIntent(8);
			break;
		case  R.id.image210:
			startIntent(9);
			break;
		case  R.id.image211:
			startIntent(10);
			break;
		case  R.id.image212:
			startIntent(11);
			//startActivity(new Intent(getActivity(),ActionActivity.class));
			//overridePendingTransition(R.anim.push_right_in,
				//	R.anim.push_right_out);
			break;
		case  R.id.image213:
			startIntent(12);
			break;

		default:
			break;
		}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(), "无法访问", Toast.LENGTH_LONG).show();
		}
		
	}
	
	private void startIntent(int postion){
		if (!urList.get(postion).equals("")) {
			Intent url=new Intent(getActivity(),WebViewActivity.class);
			url.putExtra("postion",postion);
			url.putExtra("Url", urList.get(postion));
			startActivity(url);		
		}
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Log.v("注册接口",error.toString());
	    Log.e("LOGIN-ERROR", error.getMessage(), error);
//	    byte[] htmlBodyBytes = error.networkResponse.data;
//	    Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
		Toast.makeText(getActivity(), "请重新尝试" , Toast.LENGTH_LONG)
		.show();
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		try {
			jsonObject=new JSONObject(data);
			code=jsonObject.getInt("code");
			System.out.println("code:"+code);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			try {
			JSONArray jsonArray=new JSONArray(jsonObject.getString("data"));
			System.out.println("array转换成功");
			if (jsonArray!=null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject temp=(JSONObject) jsonArray.get(i);
						url[i]=temp.getString("url");
						urlImage[i]=temp.getString("img_url");		
						System.out.println(temp.getString("img_url"));
						System.out.println(temp.getString("url")+"生活");
					}
				initData();
			}
				} catch (Exception e) {
				// TODO: handle exception
					initData();
//					Toast.makeText(getActivity(), "访问服务器错误:生活" ,Toast.LENGTH_LONG)
//					.show();
			}
			
		}
//			else {
//			Toast.makeText(getActivity(), "访问服务器错误:生活1",Toast.LENGTH_LONG)
//			.show();
//		}
	}
	private void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < url.length; i++) {
			urList.add(i, url[i]);
		}
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		try {
			for (int i = 0; i < id.length; i++) {
				imageViews[i]=(NetworkImageView)view.findViewById(id[i]);
				imageViews[i].setErrorImageResId(R.drawable.image_load_fail); // 加载失败显示的图片
				imageViews[i].setImageUrl(urlImage[i], imageLoader);
				imageViews[i].setOnClickListener(this);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("生活出现问题");
		}
		
	}
}
