package com.itboye.guangda.Fragment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;
import com.itboye.guangda.NickNameActivity;
import com.itboye.guangda.WebViewActivity;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda.app.Constant;
import com.itboye.guangda.geren.LoginActivity;
import com.itboye.guangda.geren.RegistActivity;
import com.itboye.guangda.utils.BitmapCache;
import com.itboye.guangda.view.CircleImg;
import com.itboye.guangda.view.FileUtil;
import com.itboye.guangda.view.NetUtil;
import com.itboye.guangda.view.SelectPicPopupWindow;
import com.itboye.guangda_android.R;


public class GeRenFragment extends Fragment implements OnClickListener {
	private static final String MODE_PRIVATE = null;
	private Context mContext;
	private View view ;
	private ImageView xiuga;
	private ImageView mima;
	private ImageView kefu;
	private ImageView guanyu;
	private ImageView kajuan;
	private ImageView shaomiao;
	private ImageView tuichu;
	private TextView mingzhi;
	private SharedPreferences sp;
	private AppContext appContext;
	private CircleImg avatarImg;// 头像图片
	private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
	// 上传服务器的路径【一般不硬编码到程序中】
	private String imgUrl = "";
	private static final String IMAGE_FILE_NAME = "head.jpg";// 头像文件名称
	private String urlpath;			// 图片本地路径
	private String resultStr = "";	// 服务端返回结果集
	private static ProgressDialog pd;// 等待进度圈
	private static final int REQUESTCODE_PICK = 0;		// 相册选图标记
	private static final int REQUESTCODE_TAKE = 1;		// 相机拍照标记
	private static final int REQUESTCODE_CUTTING = 2;	// 图片裁切标记

	Dialog choose;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view= inflater.inflate(R.layout.fragment_geren, null);
		initView(view);
		sp=getActivity().getSharedPreferences(Constant.MY_PREFERENCES, Activity.MODE_PRIVATE);
		mContext=getActivity();
		return view;		
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		imgUrl = Constant.URL+"/File/upload?access_token="+AppContext.getAccess_token()+"";
		appContext=(AppContext) this.getActivity().getApplication();
		avatarImg=(CircleImg)view.findViewById(R.id.head);
		avatarImg.setImageResource(R.drawable.logo);
		avatarImg.setOnClickListener(this);
		mingzhi=(TextView)view.findViewById(R.id.mingzhi);
		mingzhi.setOnClickListener(this);	
		kajuan=(ImageView)view.findViewById(R.id.kajuan);
		kajuan.setOnClickListener(this);
		tuichu=(ImageView)view.findViewById(R.id.tuichu);
		tuichu.setOnClickListener(this);	
		kefu=(ImageView)view.findViewById(R.id.kefu);
		kefu.setOnClickListener(this);
		shaomiao=(ImageView)view.findViewById(R.id.shaomiao);
		shaomiao.setOnClickListener(this);
		guanyu=(ImageView)view.findViewById(R.id.guanyu);
		guanyu.setOnClickListener(this);
		mima=(ImageView)view.findViewById(R.id.mima);
		mima.setOnClickListener(this);
		xiuga=(ImageView)view.findViewById(R.id.xiugai);
		xiuga.setOnClickListener(this);

	}
	@SuppressLint("ShowToast") @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head:

			if (!appContext.isLogin()) {
				startActivityForResult((new Intent(getActivity(),LoginActivity.class)),1000);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}


			break;
		case R.id.mingzhi:
			if (appContext.isLogin()==false) {
				startActivityForResult((new Intent(getActivity(),LoginActivity.class)),1000);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
			break;
		case R.id.xiugai:// 更换头像点击事件
			if (appContext.isLogin()) {
				menuWindow = new SelectPicPopupWindow(getActivity(), itemsOnClick);  
				menuWindow.showAtLocation(getActivity().findViewById(R.id.mainLayout), 
						Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
			}else {
				Toast.makeText(getActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.mima:
			if (appContext.isLogin()) {
				Intent newIntent=new Intent(getActivity(),NickNameActivity.class);
				startActivityForResult(newIntent, 1001);//请求码
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else {
				Toast.makeText(getActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.kefu:
			Intent phoneIntent1 = new Intent(Intent.ACTION_DIAL);
			Uri data1 = Uri.parse("tel:" + "95595");
			phoneIntent1.setData(data1);
			startActivity(phoneIntent1);

			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.guanyu:
			Intent url=new Intent(getActivity(),WebViewActivity.class);
			url.putExtra("postion","");
			url.putExtra("Url","http://202.99.20.186:8000/index.php/Cms/Post/view/id/25.shtml");
			startActivity(url);
			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.shaomiao:
			Intent url_shaomiao=new Intent(getActivity(),WebViewActivity.class);
			url_shaomiao.putExtra("postion",251);
			//url_shaomiao.putExtra("Url","http://xykbuy.cebbank.com/");
			startActivity(url_shaomiao);
			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.kajuan:
			Toast.makeText(getActivity(), "开发中", Toast.LENGTH_LONG).show();
			break;
		case R.id.tuichu:
			if (appContext.isLogin()) {
				sp.edit().clear().commit();//清空所有sp中的数据
				AppContext.setHasHead(false);
				appContext.setLogin(false);
				AppContext.setNickname("");
				Toast.makeText(getActivity(), "退出登录成功", Toast.LENGTH_SHORT).show();
				mingzhi.setText("登录与注册");
				avatarImg.setImageResource(R.drawable.logo);
				mingzhi.setClickable(true);
			}else {
				Toast.makeText(getActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}

	}	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (AppContext.getNickname()!=null&&appContext.isLogin()) {
			mingzhi.setText(AppContext.getNickname());
			System.out.println(AppContext.getNickname()+"进入");
			if (AppContext.hasHead==false) {
				ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
						new BitmapCache());
				try {
					//						avatarImg.setErrorImageResId(R.drawable.head); // 加载失败显示的图片
					//						avatarImg.setDefaultImageResId(R.drawable.head);
					//						avatarImg.setImageUrl(head, imageLoader);
					ImageListener listener = ImageLoader.getImageListener(avatarImg,R.drawable.logo, R.drawable.logo);  
					imageLoader.get(AppContext.getPathHeadImage(), listener);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}
	}
	//为弹出窗口实现监听类  
	private OnClickListener itemsOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//下面这句指定调用相机拍照后的照片存储的路径
				takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, 
						Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
				startActivityForResult(takeIntent, REQUESTCODE_TAKE);
				break;
				// 相册选择图片
			case R.id.pickPhotoBtn:
				Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
				// 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
				pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(pickIntent, REQUESTCODE_PICK);
				break;
			default:
				break;
			}
		}
	}; 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 1001:
			if (appContext.isLogin()) {
				if (data!=null) {
					String nickname=data.getStringExtra("nickName");
					mingzhi.setText(nickname);
					System.out.println(nickname+"名字");
				}
			}

			break;

		case 1000:
			if (data!=null) {
				String name=data.getStringExtra("NAME");
				mingzhi.setText(name);
				mingzhi.setClickable(false);
				String head=data.getStringExtra("Head");
				System.out.println("头像"+head+name);

				ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
						new BitmapCache());
				try {
					//							avatarImg.setErrorImageResId(R.drawable.head); // 加载失败显示的图片
					//							avatarImg.setDefaultImageResId(R.drawable.head);
					//							avatarImg.setImageUrl(head, imageLoader);
					ImageListener listener = ImageLoader.getImageListener(avatarImg,R.drawable.logo, R.drawable.logo);  
					imageLoader.get(head, listener);
					AppContext.setHasHead(true);
					System.out.println("头像"+head+name);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("头像图片加载出现问题");
				}
			}
			break;
		case REQUESTCODE_PICK:// 直接从相册获取
			try {
				startPhotoZoom(data.getData());
			} catch (NullPointerException e) {
				e.printStackTrace();// 用户点击取消操作
			}
			break;
		case REQUESTCODE_TAKE:// 调用相机拍照
			File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case REQUESTCODE_CUTTING:// 取得裁剪后的图片
			if (data != null) {
				setPicToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			// 取得SDCard图片路径做显示
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(null, photo);
			urlpath = FileUtil.saveFile(getActivity(), "temphead.jpg", photo);
			AppContext.setPathHeadImage(urlpath);
			avatarImg.setImageDrawable(drawable);

			// 新线程后台上传服务端
			pd = ProgressDialog.show(getActivity(), null, "正在上传图片，请稍候...");
			new Thread(uploadImageRunnable).start();
		}
	}

	/**
	 * 使用HttpUrlConnection模拟post表单进行文件
	 * 上传平时很少使用，比较麻烦
	 * 原理是： 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
	 */
	Runnable uploadImageRunnable = new Runnable() {
		@Override
		public void run() {

			if(TextUtils.isEmpty(imgUrl)){
				//Toast.makeText(mContext, "还没有设置上传服务器的路径！", Toast.LENGTH_SHORT).show();
				return;
			}

			Map<String, String> textParams1 = new HashMap<String, String>();
			Map<String, String> textParams = new HashMap<String, String>();
			Map<String, File> fileparams = new HashMap<String, File>();

			try {
				// 创建一个URL对象
				URL url = new URL(imgUrl);
				textParams1=new HashMap<String, String>();
				textParams = new HashMap<String, String>();
				fileparams = new HashMap<String, File>();
				// 要上传的图片文件
				System.out.println(appContext.getLoginUid());
				textParams.put("uid", appContext.getLoginUid()+"");
				textParams1.put("type", "avatar");
				File file = new File(urlpath);
				fileparams.put("image", file);
				// 利用HttpURLConnection对象从网络中获取网页数据
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
				conn.setConnectTimeout(5000);
				// 设置允许输出（发送POST请求必须设置允许输出）
				conn.setDoOutput(true);
				// 设置使用POST的方式发送
				conn.setRequestMethod("POST");
				// 设置不使用缓存（容易出现问题）
				conn.setUseCaches(false);
				conn.setRequestProperty("Charset", "UTF-8");//设置编码   
				// 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
				conn.setRequestProperty("ser-Agent", "Fiddler");
				// 设置contentType
				conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
				OutputStream os = conn.getOutputStream();
				DataOutputStream ds = new DataOutputStream(os);
				NetUtil.writeStringParams(textParams1, ds);
				NetUtil.writeStringParams(textParams, ds);
				NetUtil.writeFileParams(fileparams, ds);
				NetUtil.paramsEnd(ds);
				// 对文件流操作完,要记得及时关闭
				os.close();
				// 服务器返回的响应吗
				int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
				// 对响应码进行判断
				if (code == 200) {// 返回的响应码200,是成功
					// 得到网络返回的输入流
					InputStream is = conn.getInputStream();
					resultStr = NetUtil.readString(is);
				} else {
					//Toast.makeText(mContext, "请求URL失败！", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
		}
	};

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				pd.dismiss();

				try {
					System.out.println("根据需求和后台数据灵活处理");
					// 返回数据示例，根据需求和后台数据灵活处理
					// {"status":"1","statusMessage":"上传成功","imageUrl":"http://120.24.219.49/726287_temphead.jpg"}
					JSONObject jsonObject = new JSONObject(resultStr);
					JSONObject data=new JSONObject(jsonObject.getString("data"));
					// 服务端以字符串“1”作为操作成功标记
					Editor editor=sp.edit();
					System.out.println( data.getString("imgurl"));
					editor.putString(Constant.MY_HEAD_URL, data.getString("imgurl"));
					editor.commit();
					System.out.println( sp.getString(Constant.MY_HEAD_URL, ""));
					System.out.println(jsonObject.toString());
					//Toast.makeText(mContext, jsonObject.optString("statusMessage"), Toast.LENGTH_SHORT).show();

				} catch (JSONException e) {
					e.printStackTrace();
				}

				break;

			default:
				break;
			}
			return false;
		}
	});
}
