package com.itboye.guangda.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itboye.guangda.WebViewActivity;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.app.Constant;
import com.itboye.guangda.utils.BitmapCache;
import com.itboye.guangda_android.R;

public class ShouYeFragment extends Fragment implements OnClickListener,
		OnPageChangeListener,StrUIDataListener {
	private List<String> urList = new ArrayList<String>();
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Boolean YesOrNo;
	private String[] urlImage;
	private NetworkImageView[] imageViews;
	
	private int id[]={R.id.image4,R.id.image5,R.id.image6,R.id.image7,R.id.image8,R.id.image9,R.id.image10,
			R.id.image11,R.id.image12,R.id.image13,R.id.image14,R.id.image15,R.id.image16,R.id.image17,R.id.image18};
	private View view;
	private int requestState;
	private int currentItem=0;
	private String[] url = {
			"https://xyk.cebbank.com/cebmms/apply/fz/card-apply-sim-req.htm?pro_code=FHZY230000SJ04HZLS",
			"http://xykbuy.cebbank.com/",
			"http://mp.weixin.qq.com/s?__biz=MzA5MDUwMzEwNQ==&mid=209334274&idx=1&sn=45af274a56be63708a9fbc258c6e421d&scene=0#rd",
			"http://m.kaola.com/activity/h5/4952.html",
			"http://m.kaola.com/activity/h5/4938.html",
			"http://m.kaola.com/activity/h5/4937.html",
			"http://m.kaola.com/product/6906.html",
			"http://m.kaola.com/product/6637.html",
			"http://m.kaola.com/product/15512.html",
			"http://m.kaola.com/product/7009.html",
			"http://mp.weixin.qq.com/s?__biz=MzA3NDY1NzAyNg==&mid=209401901&idx=1&sn=429bfab7fac42f46306f00035dfd9fdf#rd",
			" ",
			" ",
			" ",
			" "};
	private ImageView imageView1, imageView2, imageView3, imageView4,
			imageView5, imageView6, imageView7, imageView8, imageView9,
			imageView10, imageView11, imageView12, imageView13, imageView14,
			mageView15, imageView16, imageView17, imageView18;
	private ViewPager viewPager;
	private ImageView[] tips;
	private NetworkImageView[] mImageViews;
	private String[] bannerUrl;//跳转url
	private String [] bannerImageUrl;//banner图片地址url
	private int[] imgIdArray = new int[3];
	private ViewGroup group;
	// 定时任务  
    private ScheduledExecutorService scheduledExecutorService;  

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_shouye, null);
		initId();
		initView(view);
		startAd();
		return view;
	}
	
	private Handler handler = new Handler() {  
        public void handleMessage(android.os.Message msg) {  
            viewPager.setCurrentItem(currentItem);  
        };  
    };  
    
    private void startAd() {  
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();  
        // 当Activity显示出来后，每两秒切换一次图片显示  
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,  
                TimeUnit.SECONDS);  
    }  
  
    private class ScrollTask implements Runnable {  
  
        @Override  
        public void run() {  
            synchronized (viewPager) {  
                currentItem = (currentItem + 1) % imgIdArray.length;  
                handler.obtainMessage().sendToTarget();  
            }  
        }  
    }  
  
    @Override
	public void onStop() {  
        super.onStop();  
        // 当Activity不可见的时候停止切换  
        scheduledExecutorService.shutdown();
    }  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(this);
		appContext = (AppContext) getActivity().getApplication();
	}

	private void initViewPager() {
		// TODO Auto-generated method stub
		// 灏嗙偣鐐瑰姞鍏ュ埌ViewGroup涓?
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			group.addView(imageView, layoutParams);
		}
		// 灏嗗浘鐗囪杞藉埌鏁扮粍涓?
		mImageViews = new NetworkImageView[imgIdArray.length];
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		for (int i = 0; i < mImageViews.length; i++) {
			NetworkImageView imageView = new NetworkImageView(getActivity());
			mImageViews[i] = imageView;
			imageView.setErrorImageResId(R.drawable.image_load_fail); // 加载失败显示的图片
			imageView.setImageUrl(bannerImageUrl[i], imageLoader);
		}

		// 璁剧疆Adapter
		// viewPager.setAdapter(new MyAdapter(getActivity()));

		viewPager.setAdapter(new MyAdapter(getActivity()));
		// 璁剧疆鐩戝惉锛屼富瑕佹槸璁剧疆鐐圭偣鐨勮儗鏅?
		viewPager.setOnPageChangeListener(this);
		// 璁剧疆ViewPager鐨勯粯璁ら」, 璁剧疆涓洪暱搴︾殑100鍊嶏紝杩欐牱瀛愬紑濮嬪氨鑳藉線宸︽粦鍔?
	//	viewPager.setCurrentItem((mImageViews.length) * 100);
		//viewPager.setCurrentItem(currentItem);  
	}

	/**
	 * 
	 * @author xiaanming
	 * 
	 */
	public class MyAdapter extends PagerAdapter {
		private Context context;

		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		@Override
		public int getCount() {
			//return Integer.MAX_VALUE;
			 return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (View) arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			 container.removeView((View) object);
		}

		/**
		 * 杞藉叆鍥剧墖杩涘幓锛岀敤褰撳墠鐨刾osition 闄や互 鍥剧墖鏁扮粍闀垮害鍙栦綑鏁版槸鍏抽敭
		 */
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			final int num = position % mImageViews.length;
			View child = mImageViews[num];
			if (child.getParent() != null) {
				((ViewPager) container).removeView(child);
			}

			View view = mImageViews[num];
			container.addView(view);
			//urList.add(num, url[num]);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/*Intent intent = new Intent(getActivity(),
							WebViewActivity.class);
					intent.putExtra("postion", "-1");
					intent.putExtra("Url", urList.get(num));
					startActivity(intent);*/
					startBannerIntent(num);
				}
			});

			return view;
		}

	}

	private void  initId(){
		//这里为了减少加载失败的次数
		requestBanners();
		
		group = (ViewGroup) view.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		
		imageViews=new NetworkImageView[id.length];
		urlImage=new String[id.length];
		url=new String[id.length];		
		
	}
	
	private void initView(View view) {
		// TODO Auto-generated method stub
		try {
			YesOrNo = appContext.getImage(getActivity(), 1+"", 20+"", "app_index", networkHelper);			
			//YesOrNo = appContext.getImage(getActivity(), 1+"", 20+"", "app_carousel", networkHelper);
			if (!YesOrNo) { // 如果没联网
				Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//banner的网络请求
	private void requestBanners() {
		// TODO Auto-generated method stub
		bannerImageUrl=new String[imgIdArray.length];
		bannerUrl=new String[imgIdArray.length];
		
		String access_token=AppContext.getAccess_token();
		String httpurl = Constant.URL+"/Banners/query?access_token="+access_token+"";
		RequestQueue requestQueue = Volley.newRequestQueue(appContext);  
		  
		StringRequest stringRequest = new StringRequest(Request.Method.POST,httpurl,  
		    new Response.Listener<String>() {  
			int code=-1;
		        @Override  
		        public void onResponse(String response) {  
		            Log.d("TAG", "response -> " + response);  
		           try {
					JSONObject jsonObject=new JSONObject(response);
					code=jsonObject.getInt("code");
					if (code==0) {
						try {
							JSONArray jsonArray=new JSONArray(jsonObject.getString("data"));
							System.out.println("banner转换成功");
							if (jsonArray!=null) {
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject temp=(JSONObject) jsonArray.get(i);
										bannerUrl[i]=temp.getString("url");
										bannerImageUrl[i]=temp.getString("img_url");		
										System.out.println(temp.getString("img_url"));
										System.out.println(temp.getString("url")+"首页");
									}
								initViewPager();
							}
								} catch (Exception e) {
								// TODO: handle exception
									e.printStackTrace();
//									Toast.makeText(getActivity(), "访问服务器错误:首页" ,Toast.LENGTH_LONG)
//									.show();
							}
							
					}else {
						System.out.println("banner出错");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        }  
		    }, new Response.ErrorListener() {  
		        @Override  
		        public void onErrorResponse(VolleyError error) {  
		            Log.e("TAG", error.getMessage(), error);  
		        }  
		    }) {  
		    @Override  
		    protected Map<String, String> getParams() {  
		        //在这里设置需要post的参数  
		            Map<String, String> map = new HashMap<String, String>();    
		            map.put("curpage", "1");    
		            map.put("pagesize", "20");  
		            map.put("position", "app_carousel");
		  
		          return map;  
		    }  
		};          
		  
		requestQueue.add(stringRequest);    
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {

			switch (v.getId()) {
			case R.id.image4:
				startIntent(0);
				break;
			case R.id.image5:
				startIntent(1);
				break;
			case R.id.image6:
				startIntent(2);
				break;
			case R.id.image7:
				startIntent(3);
				break;
			case R.id.image8:
				startIntent(4);
				break;
			case R.id.image9:
				startIntent(5);
				break;
			case R.id.image10:
				startIntent(6);
				break;
			case R.id.image11:
				startIntent(7);
				break;
			case R.id.image12:
				startIntent(8);
				break;
			case R.id.image13:
				startIntent(9);
				break;
			case R.id.image14:
				startIntent(10);
				break;
			case R.id.image15:
				startIntent(11);
				break;
			case R.id.image16:
				startIntent(12);
				break;
			case R.id.image17:
				startIntent(13);
				break;
			case R.id.image18:
				startIntent(14);
				break;


			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getActivity(), "请求发生异常", Toast.LENGTH_LONG).show();
		}

	}

	private void startIntent(int postion) {
		if (!urList.get(postion).equals("")) {
			Intent url = new Intent(getActivity(), WebViewActivity.class);
			url.putExtra("postion", postion);
			url.putExtra("Url", urList.get(postion));
			startActivity(url);
		}
	}
	
	private void startBannerIntent(int postion) {
		if (!bannerUrl[postion] .equals("")) {
			Intent url = new Intent(getActivity(), WebViewActivity.class);
			url.putExtra("postion", postion);
			url.putExtra("Url", bannerUrl[postion]);
			startActivity(url);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setImageBackground(arg0 % mImageViews.length);
		currentItem=arg0;
	}

	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Log.v("注册接口",error.toString());
	    Log.e("LOGIN-ERROR", error.getMessage(), error);
	    //加载失败 重新加载一次
//		Toast.makeText(getActivity(), "重新加载了"+error.toString() , Toast.LENGTH_LONG)
//		.show();
	    initView(view);
	    try {
	    	  byte[] htmlBodyBytes = error.networkResponse.data;
	 	     Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	  
//		Toast.makeText(getActivity(), "请重新尝试"+error.toString() , Toast.LENGTH_LONG)
//		.show();
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
				for (int i = 0; i <=jsonArray.length(); i++) {
					JSONObject temp=(JSONObject) jsonArray.get(i);
						url[i]=temp.getString("url");
						urlImage[i]=temp.getString("img_url");		
						System.out.println(temp.getString("img_url"));
						System.out.println(temp.getString("url")+"首页");
					}
				initData();
			}
				} catch (Exception e) {
				// TODO: handle exception
					initData();
					e.printStackTrace();
				//	Toast.makeText(getActivity(), "访问服务器错误:首页" ,Toast.LENGTH_LONG)
					//.show();
			}
			
		} 
//		else {
//			Toast.makeText(getActivity(), "访问服务器错误:首页1" ,Toast.LENGTH_LONG)
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
			System.out.println("首页出现问题");
		}
		
	}
}
