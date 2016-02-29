package com.itboye.guangda;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.guangda.api.ApiClient;
import com.itboye.guangda.api.StrUIDataListener;
import com.itboye.guangda.api.StrVolleyInterface;
import com.itboye.guangda.app.AppContext;
import com.itboye.guangda_android.R;

public class NickNameActivity extends Activity implements StrUIDataListener {
	private EditText etTrueName;//昵称t
	private EditText etNickname;
	private Button btnXiuGai;//修改按钮
	private TextView tvTitle;//top中textview
	private ImageView ivBack;
	private StrVolleyInterface networkHelper;
	private AppContext appContext;
	private ProgressDialog dialog;
	private EditText et_shengfen,et_shouji,et_yinhang;
	//private ImageView ivBack;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		ActionBar actionBar = getActionBar();  
//		actionBar.hide();
		setContentView(R.layout.activity_nickname);
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		appContext=(AppContext) getApplication();
		dialog = new ProgressDialog(NickNameActivity.this);
		initId();
		
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		
		btnXiuGai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String yinhangString=et_yinhang.getText().toString();
				String shenfenString=et_shengfen.getText().toString();
				String shoujiString=et_shouji.getText().toString();
				// TODO Auto-generated method stub
				if (et_shengfen.getText().toString().equals("")&&et_shouji.getText().toString().equals("")&&
						et_yinhang.getText().toString().equals("")&&etNickname.getText().toString().equals("")&&
						etTrueName.getText().toString().equals("")) {
					Toast.makeText(NickNameActivity.this, "请填写个人信息",Toast.LENGTH_SHORT).show();
					
				}else if(!shoujiString.equals("")&&shoujiString.length()!=11){
					Toast.makeText(NickNameActivity.this, "请输入正确的手机号",Toast.LENGTH_SHORT).show();
				}else if(!shenfenString.equals("")&&shenfenString.length()!=18){
					Toast.makeText(NickNameActivity.this, "请输入正确的身份证号",Toast.LENGTH_SHORT).show();
				}
//					else if(!yinhangString.equals("")&&yinhangString.length()!=11){
//					Toast.makeText(NickNameActivity.this, "请输入正确的银行卡号",Toast.LENGTH_SHORT).show();
//				}
					else {
					ApiClient.modifyPersonal(NickNameActivity.this,appContext.getLoginUid()+"", "",etNickname.getText().toString(),
							etTrueName.getText().toString(), et_shengfen.getText().toString(), "", "", "", "",
							et_shouji.getText().toString(),et_yinhang.getText().toString(), networkHelper);
					dialog.setMessage("正在修改个人信息");
			        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					dialog.show();
				}
			}
		});
	}
	private void initId() {
		// TODO Auto-generated method stub
		etTrueName=(EditText)findViewById(R.id.et_true_name);
		et_shengfen=(EditText)findViewById(R.id.et_shenfen_number);
		et_shouji=(EditText)findViewById(R.id.et_shoujihao);
		et_yinhang=(EditText)findViewById(R.id.et_yinhangka);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivBack.setVisibility(View.VISIBLE);
		tvTitle=(TextView)findViewById(R.id.title);
		tvTitle.setText("个人设置");
		etNickname=(EditText)findViewById(R.id.et_nickname);
		Intent intent=getIntent();
		String name=intent.getStringExtra("nickname");
		etNickname.setText(name);
		btnXiuGai=(Button)findViewById(R.id.btn_xiugai);
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
				dialog.dismiss();
				Toast.makeText(NickNameActivity.this, content.toString(), Toast.LENGTH_SHORT).show();
				Intent intent=getIntent();
				if (!etNickname.getText().toString().equals("")) {
					intent.putExtra("nickName", etNickname.getText().toString());
					NickNameActivity.this.setResult(1001,intent);
					AppContext.setNickname(etNickname.getText().toString());
					System.out.println(etNickname.getText().toString()+"名字");
				}
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				
	     	}else {
	     		dialog.dismiss();
			}
		}
}
