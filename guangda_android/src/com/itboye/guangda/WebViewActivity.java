package com.itboye.guangda;
/*
 * 主要用于显示webview相关的链接界面
 */
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.guangda.app.AppContext;
import com.itboye.guangda_android.R;

public class WebViewActivity extends Activity{
	WebView wvShow;//显示web页面
	ImageView ivBackWeb;//web页面的返回按钮
	ProgressBar dialog;//显示正在加载
	TextView  tvTitle;//titlebar标示
	private String url;
	private int postion;//用于标示url,251表示个人优惠码
	private AppContext appContext;
	@SuppressLint("SetJavaScriptEnabled") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        appContext = (AppContext) getApplication();
        wvShow=(WebView)findViewById(R.id.wv_show);
        ivBackWeb=(ImageView)findViewById(R.id.iv_back_web);
        tvTitle=(TextView)findViewById(R.id.tv_title);
        dialog=(ProgressBar)findViewById(R.id.progressBar);
        //dialog.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
      //  String uid=appContext.getLoginUid()+"";
        
        String url=intent.getStringExtra("Url"); 
        postion=intent.getIntExtra("postion", -1);
        switch (postion) {
		case 251:
			url="http://120.199.28.62:8000/index.php/Home/Index/"
					+ "invite_code?id_code="+AppContext.getUserIdCode();
			break;

		default:
			break;
		}
     //   Toast.makeText(this, "链接"+url, Toast.LENGTH_LONG).show();
        
        wvShow.setHorizontalScrollBarEnabled(false);
        wvShow.setHorizontalScrollbarOverlay(false);  
        wvShow.getSettings().setBuiltInZoomControls(false); //会出现放大缩小的按钮
        wvShow.getSettings().setSupportZoom(true);
        wvShow.getSettings().setUseWideViewPort(true);
        wvShow.getSettings().setAppCacheEnabled(true);
        wvShow.setInitialScale(39);
        wvShow.getSettings().setJavaScriptEnabled(true);
        wvShow.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wvShow.loadUrl(url);
        
        wvShow.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		// TODO Auto-generated method stub
        		super.onPageFinished(view, url);
        		view.setVisibility(View.VISIBLE);
                dialog.setVisibility(View.GONE);
        	}
        	//为true表示不跳转其他浏览器
        	  public boolean shouldOverrideUrlLoading(WebView view, String url) { 
                  view.loadUrl(url);
                  return true;
             }
        	 
        });
    

        ivBackWeb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();;//结束当前webview Activity
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
    }
	  @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK) && wvShow.canGoBack()) { 
				wvShow.goBack();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
}
