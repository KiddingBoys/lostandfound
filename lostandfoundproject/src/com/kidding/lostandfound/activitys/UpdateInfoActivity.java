package com.kidding.lostandfound.activitys;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.utils.IsMobile;
import com.kidding.lostandfound.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2016-5-6 下午4:49:08
 * @version 1.0
 * @parameter
 * @return
 */
public class UpdateInfoActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	@ViewInject(R.id.tv_updateinfo_editnum)
	private TextView editNumTv;
	@ViewInject(R.id.et_updateinfo_signature)
	private EditText signatureEt;


	private String loginid;
	private String originalContent;
	private int code;
	private String titleString;
	private int num;
	private String resultcontent;
	private int len;
	private Intent intent;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_updateinfo);
		ViewUtils.inject(this);
		init();
		trueBtn.setOnClickListener(this);

		signatureEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				resultcontent = signatureEt.getText().toString();
				len = resultcontent.length();
				if (len <= num) {
					len = num - len;
					editNumTv.setTextColor(getResources()
							.getColor(R.color.gray));
					editNumTv.setText(String.valueOf(len) + "/" + num);
				}

				else {
					len = len - num;
					editNumTv.setTextColor(Color.RED);
					editNumTv.setText("-" + String.valueOf(len) + "/" + num);

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void init() {
		
		intent = this.getIntent();
		bundle = intent.getExtras();
		originalContent = bundle.getString("originalcontent");
		code = bundle.getInt("resultcode");
		titleString = bundle.getString("title");
		num = bundle.getInt("num");

		topTitleTv.setText(titleString);
		signatureEt.setText(originalContent);

		resultcontent = signatureEt.getText().toString();
		len = resultcontent.length();
		len = num - len;
		editNumTv.setText(len + "/" + num);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_true:
			resultcontent = signatureEt.getText().toString().trim();
			int length = resultcontent.length();
			
			if (length > num) {
				ToastUtil.toastshow(UpdateInfoActivity.this, "字数超过限制字数");
				return;
			}
			if(length==0){
				ToastUtil.toastshow(UpdateInfoActivity.this, "字数不能为0");
				return;
			}
			
			if (titleString.equals("名字")) {
				if (!resultcontent.matches("[\u4e00-\u9fa5\\w]+")) {
					ToastUtil.toastshow(UpdateInfoActivity.this, "输入的名字包含非法字符！");
					return ;
				}

			}
			if (titleString.equals("手机号")) {
				if (!IsMobile.isMobileNO(resultcontent)) {
					ToastUtil.toastshow(UpdateInfoActivity.this, "请正确填写手机号码");
					return;
				}

			}
			

			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", resultcontent);
			resultIntent.putExtras(bundle);
			UpdateInfoActivity.this.setResult(code, resultIntent);

			this.finish();

			break;

		default:
			break;
		}
	}

}