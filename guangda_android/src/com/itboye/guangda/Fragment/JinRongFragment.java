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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.itboye.guangda.JiuGongActivity;
import com.itboye.guangda.WebViewActivity;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.utils.BitmapCache;
import com.itboye.guangda_android.R;

public class JinRongFragment extends Fragment implements OnClickListener,StrUIDataListener{
	private List<String> urList=new ArrayList<String>();
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Boolean YesOrNo;
	private String[] urlImage;
	private NetworkImageView[] imageViews;
	private View view;
	private int id[]={R.id.image31,R.id.image32,R.id.image33,R.id.image34,R.id.image35,R.id.image36,
			R.id.image37,R.id.image38,R.id.image39,R.id.image310,R.id.image311,};
	private String[] url={
			"",
			"https://xyk.cebbank.com/cebmms/apply/fz/card-apply-sim-req.htm?pro_code=FHZY230000SJ04HZLS",
			"",
			"",
			"https://wap.cebbank.com/pwap/MPFinance.do?Channel=0&openId=NEONMtD4TYQiqKohvNVprBB8BcwAcb1JHNi1qbHdubmo0VkFMT2ZrNFRuVjYyYmt6bw%3D%3D",
			"https://wap.cebbank.com/pwap/MPFinance.do?Channel=0&openId=NEONMtD4TYQiqKohvNVprBB8BcwAcb1JHNi1qbHdubmo0VkFMT2ZrNFRuVjYyYmt6bw%3D%3D",//这个链接还是有问题
			};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	   view = inflater.inflate(R.layout.fragment_jinrong, null);
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
		YesOrNo = appContext.getImage(getActivity(), 1+"", 20+"", "app_finance", networkHelper);
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
		
		case  R.id.image31:			
			startIntent(0);
			break;
		case  R.id.image32:
			startIntent(1);
			break;
		case  R.id.image33:
			//startIntent(2);
			startActivity(new Intent(getActivity(),JiuGongActivity.class));
			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case  R.id.image34:
			startIntent(3);
			break;
		case  R.id.image35:
			startIntent(4);
			break;
		case  R.id.image36:
			startIntent(5);
			break;
		case  R.id.image37:
			startIntent(6);
			break;
		case  R.id.image38:
			startIntent(7);
			break;
		case  R.id.image39:
			startIntent(8);
			break;
		case  R.id.image310:
			startIntent(9);
			break;
		case  R.id.image311:
			startIntent(10);
			break;
		default:
			break;
		}
	} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(), "无法访问", Toast.LENGTH_LONG).show();
		}
		
	}
//	
//	private void startHaiBao(int position) {
//		// TODO Auto-generated method stub
//		if (!urList.get(position).equals("")) {
//		Intent url=new Intent(getActivity(),HaiBaoActivity.class);
//		url.putExtra("Url", urList.get(position));
//		getActivity().startActivity(url);
//		getActivity().overridePendingTransition(R.anim.in_from_right,
//				R.anim.out_to_left);
//		}
//	}
	
	private void startIntent(int position){
		if (!urList.get(position).equals("")) {
			System.out.println(urList.get(position));
			Intent url=new Intent(getActivity(),WebViewActivity.class);
			url.putExtra("postion",position);
			url.putExtra("Url", urList.get(position));
			getActivity().startActivity(url);		
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Log.v("注册接口",error.toString());
	    Log.e("LOGIN-ERROR", error.getMessage(), error);
//	    byte[] htmlBodyBytes = error.networkResponse.data;
//	    Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
		Toast.makeText(getActivity(), "请重新尝试", Toast.LENGTH_LONG)
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
						System.out.println(temp.getString("url")+"金融");
					}
				initData();
			}
				} catch (Exception e) {
				// TODO: handle exception
					initData();
//					Toast.makeText(getActivity(), "访问服务器错误:金融" ,Toast.LENGTH_LONG)
//					.show();
			}
			
		} 
//			else {
//			Toast.makeText(getActivity(), "访问服务器错误:金融1" ,Toast.LENGTH_LONG)
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
			System.out.println("金融出现问题");
		}
		
	}
	
	
}
