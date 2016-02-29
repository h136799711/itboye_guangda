package com.itboye.guangda;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.guangda_android.R;

public class JiuGongActivity extends Activity implements OnClickListener {
	private List<String> urList=new ArrayList<String>();
	private String[] url={
			"https://xyk.cebbank.com/home/fz/card-app-status.htm",
			"http://xyk.cebbank.com/home/jfmallstatic/index.htm",
			"https://xyk.cebbank.com/cebmms/home/index.htm?toUrl=/home/main/oLZKojgUpUrXLeqVtfjIzUuI-gvk.html",
			"http://www.vxiaov.com/cases/guangda/appxiazai.html",
			"http://xyk.cebbank.com/home/dd/dealerList.htm?city=",
			"http://xyk.cebbank.com/home/slzc/index.htm",
			"http://xykbuy.cebbank.com/"
			};
	private ImageView imageView51,imageView52,imageView53,imageView54,imageView55,
	imageView56,imageView57;//,imageView8,imageView9,imageView10,imageView11,imageView12,
	//imageView13,imageView14;
	private Button button;
	private TextView mendian;
	private TextView title;
	private ImageView  iv_back;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar=getActionBar();
//        actionBar.hide();
        setContentView(R.layout.activity_jiugong);
        //dialog.setVisibility(View.VISIBLE);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        title=(TextView) findViewById(R.id.title);
        title.setText("阳光惠生活");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        initView();
     
	}
	private void initView() {
		// TODO Auto-generated method stub
		imageView51=(ImageView) findViewById(R.id.image51);
		imageView52=(ImageView) findViewById(R.id.image52);
		imageView53=(ImageView) findViewById(R.id.image53);
		imageView54=(ImageView) findViewById(R.id.image54);
		imageView55=(ImageView) findViewById(R.id.image55);
		imageView56=(ImageView) findViewById(R.id.image56);
		imageView57=(ImageView) findViewById(R.id.image57);
//		imageView8=(ImageView) view.findViewById(R.id.image8);
//		imageView9=(ImageView) view.findViewById(R.id.image9);
//		imageView10=(ImageView) view.findViewById(R.id.image10);
//		imageView11=(ImageView) view.findViewById(R.id.image11);
//		imageView12=(ImageView) view.findViewById(R.id.image12);
//		imageView13=(ImageView) view.findViewById(R.id.image13);
//		imageView14=(ImageView) view.findViewById(R.id.image14);
		imageView51.setOnClickListener(this);
		imageView52.setOnClickListener(this);
		imageView53.setOnClickListener(this);
		imageView54.setOnClickListener(this);
		imageView55.setOnClickListener(this);
		imageView56.setOnClickListener(this);
		imageView57.setOnClickListener(this);
//		imageView8.setOnClickListener(this);
//		imageView9.setOnClickListener(this);
//		imageView10.setOnClickListener(this);
//		imageView11.setOnClickListener(this);
//		imageView12.setOnClickListener(this);
//		imageView13.setOnClickListener(this);
//		imageView14.setOnClickListener(this);
//		
		for (int i = 0; i < url.length; i++) {
			urList.add(i, url[i]);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			
		
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
			
		case  R.id.image51:
			startIntent(0);
			break;
		case  R.id.image52:
			startIntent(1);
			break;
		case  R.id.image53:
			startIntent(2);
			break;
		case  R.id.image54:
			startIntent(3);
			break;
		case  R.id.image55:
			startIntent(4);
			break;
		case  R.id.image56:
			startIntent(5);
			break;
		case  R.id.image57:
			startIntent(6);
			break;
//		case  R.id.image8:
//			startIntent(7);
//			break;
//		case  R.id.image9:
//			startIntent(8);
//			break;
//		case  R.id.image10:
//			startIntent(9);
//			break;
//		case  R.id.image11:
//			startIntent(10);
//			break;
//		case  R.id.image12:
//			startIntent(11);
//			break;
//		case  R.id.image13:
//			startIntent(12);
//			break;
//		case  R.id.image14:
//			startIntent(13);
//			break;

		default:
			break;
		}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "无法访问", Toast.LENGTH_LONG).show();
		}
		
	}
	
	private void startIntent(int postion){
		if (urList.get(postion)!="") {
			System.out.println(urList.get(postion));
			Intent url=new Intent(this,WebViewActivity.class);
			url.putExtra("postion",postion);
			url.putExtra("Url", urList.get(postion));
			startActivity(url);		
		}
	}
}
