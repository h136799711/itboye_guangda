package com.itboye.guangda;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.guangda.Fragment.GeRenFragment;
import com.itboye.guangda.Fragment.GouWuFragment;
import com.itboye.guangda.Fragment.JinRongFragment;
import com.itboye.guangda.Fragment.ShouYeFragment;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda_android.R;

public class HomePageActivity extends FragmentActivity  implements OnClickListener{
	private ProgressBar dialog;//显示正在加载
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private ShouYeFragment shouYeFragment;
	private GouWuFragment gouWuFragment;
	private JinRongFragment jinRongFragment;
	private GeRenFragment geRenFragment;
	private TextView title;
	private List<LinearLayout> mTabIndicator = new ArrayList<LinearLayout>();
	private List<ImageView> imageViewlist = new ArrayList<ImageView>();
	private List<TextView> textViewlist = new ArrayList<TextView>();
	private int[] ic = {R.drawable.shouye_un, R.drawable.gouwu_un, R.drawable.jinrong_un,R.drawable.geren_un};
	private int[] ic_sel = {R.drawable.shouye, R.drawable.gouwu, R.drawable.jinrong,R.drawable.geren};
	private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
//    	ActionBar actionBar = getActionBar();  
//		actionBar.hide();
		setContentView(R.layout.activity_homepage);
	
        initView();
     
    }

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		dialog=(ProgressBar) findViewById(R.id.progressBar);
		title=(TextView)findViewById(R.id.title);
		shouYeFragment = new ShouYeFragment();
		gouWuFragment = new GouWuFragment();
		jinRongFragment = new JinRongFragment();
		geRenFragment=new GeRenFragment();
		mTabs.add(shouYeFragment);
		mTabs.add(gouWuFragment);
		mTabs.add(jinRongFragment);
		mTabs.add(geRenFragment);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();
	    mViewPager.setOffscreenPageLimit(4);
		mViewPager.setAdapter(mAdapter);

	}
	
	
	private void initTabIndicator()
	{
		LinearLayout one = (LinearLayout) findViewById(R.id.tab_one);
		LinearLayout two = (LinearLayout) findViewById(R.id.tab_two);
		LinearLayout three = (LinearLayout) findViewById(R.id.tab_three);
		LinearLayout four=(LinearLayout)findViewById(R.id.tab_four);
		ImageView ima_one = (ImageView) findViewById(R.id.ic_tab_one);
		ImageView ima_two = (ImageView) findViewById(R.id.ic_tab_two);
		ImageView ima_three = (ImageView) findViewById(R.id.ic_tab_three);
		ImageView ima_four=(ImageView)findViewById(R.id.ic_tab_four);
		TextView tex_one = (TextView) findViewById(R.id.tex_tab_one);
		TextView tex_two = (TextView) findViewById(R.id.tex_tab_two);
		TextView tex_three = (TextView) findViewById(R.id.tex_tab_three);
		TextView text_four=(TextView)findViewById(R.id.tex_tab_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);
		
		imageViewlist.add(ima_one);
		imageViewlist.add(ima_two);
		imageViewlist.add(ima_three);
		imageViewlist.add(ima_four);
		
		textViewlist.add(tex_one);
		textViewlist.add(tex_two);
		textViewlist.add(tex_three);
		textViewlist.add(text_four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		
		switch (v.getId())
		{
		case R.id.tab_one:
			changTabColor(0);
			title.setText("阳光惠生活");
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.tab_two:
			changTabColor(1);
			title.setText("阳光惠生活");
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.tab_three:
			changTabColor(2);
			title.setText("阳光惠生活");
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.tab_four:
			changTabColor(3);
			title.setText("阳光惠生活");
			mViewPager.setCurrentItem(3, false);
			break;
		
		}

	}
	/**
	 * 改变Tab颜色
	 * @param arg0
	 */
	private void changTabColor(int arg0) {
		for(int k=0; k<4; k++){
			imageViewlist.get(k).setImageResource(ic[k]);
			textViewlist.get(k).setTextColor(this.getResources().getColor(R.color.tab_text));
		}
		imageViewlist.get(arg0).setImageResource(ic_sel[arg0]);
		textViewlist.get(arg0).setTextColor(this.getResources().getColor(R.color.tab_text_sel));
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            //System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
