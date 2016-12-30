package com.kidding.lostandfound.activitys;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.PersonInfoPresenter;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.ui.SexySlipButton;
import com.kidding.lostandfound.ui.SexySlipButton.OnChangedListener;
import com.kidding.lostandfound.utils.ImageHelper;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.view.IPersonInfoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2016-5-6 下午3:44:03
 * @version 1.0
 * @parameter
 * @return
 */
public class PersonInfoActivity extends BaseActivity implements OnClickListener,IPersonInfoView {

	@ViewInject(R.id.btn_personinfo_showsex)
	private SexySlipButton showSexBtn;
	@ViewInject(R.id.layout_personinfo_avatar)
	private RelativeLayout avatarRl;
	@ViewInject(R.id.layout_personinfo_username)
	private RelativeLayout nameRl;
	@ViewInject(R.id.layout_personinfo_addr)
	private RelativeLayout addrRl;
	@ViewInject(R.id.layout_personinfo_tel)
	private RelativeLayout telRl;

	@ViewInject(R.id.iv_personinfo_avatar)
	private ImageView avatarIv;
	@ViewInject(R.id.tv_personinfo_username)
	private TextView nameTv;
	@ViewInject(R.id.tv_personinfo_addr)
	private TextView addrTv;
	@ViewInject(R.id.tv_personinfo_tel)
	private TextView telTv;
	@ViewInject(R.id.tv_personinfo_sexy)
	private TextView sexyTv;
	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;

	private String imgPath="";
	private User tempUser;
	private String originalContent;// 当前未修改的原始数据
	private Intent intent;
	private Bundle bundle;
	private ProgressDialog pd;
	private ImageHelper imageHelper;
	private PersonInfoPresenter personInfoPresenter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_personinfo);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		topTitleTv.setText("个人设置");
		personInfoPresenter = new PersonInfoPresenter(this, this);
		
		tempUser = new User(SPHelper.instance(this).getString("userName"),
				SPHelper.instance(this).getString("userPsw"), SPHelper
						.instance(this).getInt("userGender"), SPHelper
						.instance(this).getString("userTel"), SPHelper
						.instance(this).getString("userAddr"), SPHelper
						.instance(this).getString("userAvatar"));

		showSexBtn.setOnClickListener(this);
		avatarRl.setOnClickListener(this);
		nameRl.setOnClickListener(this);
		addrRl.setOnClickListener(this);
		telRl.setOnClickListener(this);
		trueBtn.setOnClickListener(this);

		// avatarIv头像这里
//		Glide.with(this)
//		.load(SPHelper.instance(this)
//				.getString("userAvatar"))
//		.placeholder(R.drawable.ic_launcher)
//		.centerCrop()
//		.into(avatarIv);
		Glide.with(this).load(SPHelper.instance(this).getString("userAvatar"))
				    .dontAnimate()
					.placeholder(R.drawable.ic_launcher)
					.centerCrop()
					.into(avatarIv);
		
		nameTv.setText(tempUser.getName());
		addrTv.setText(tempUser.getAddr());
		telTv.setText(tempUser.getTel());
		if (tempUser.getGender() == 0) {
			sexyTv.setText("男");
			showSexBtn.setCheck(true);
		} else {
			sexyTv.setText("女");
			showSexBtn.setCheck(false);
		}

		showSexBtn.SetOnChangedListener(new OnChangedListener() {
			public void OnChanged(boolean CheckState) {
				if (CheckState) {
					sexyTv.setText("男");
					tempUser.setGender(0);
				} else {
					sexyTv.setText("女");
					tempUser.setGender(1);
				}
			}
		});

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.layout_personinfo_avatar:
			new ImageHelper().openPhoto(this);
			break;
		case R.id.layout_personinfo_username:
			ToastUtil.toastshow(this, "名字不可修改");
			break;
		case R.id.layout_personinfo_addr:
			originalContent = addrTv.getText().toString().trim();
			intent = new Intent(PersonInfoActivity.this,
					UpdateInfoActivity.class);
			bundle = new Bundle();
			bundle.putInt("resultcode", 3);
			bundle.putString("title", "地址");
			bundle.putString("originalcontent", originalContent);
			bundle.putInt("num", 50);
			intent.putExtras(bundle);
			startActivityForResult(intent, 3);
			break;
		case R.id.layout_personinfo_tel:
			originalContent = telTv.getText().toString().trim();
			intent = new Intent(PersonInfoActivity.this,
					UpdateInfoActivity.class);
			bundle = new Bundle();
			bundle.putInt("resultcode", 4);
			bundle.putString("title", "手机号");
			bundle.putString("originalcontent", originalContent);
			bundle.putInt("num", 11);
			intent.putExtras(bundle);
			startActivityForResult(intent, 4);
			break;
		case R.id.btn_true:
			startProgress();
			personInfoPresenter.pushNewData(imgPath,tempUser);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null)
		{				
			return;
		}
		String tempString = "";
		switch (requestCode) {
		case 2:

			tempString = data.getStringExtra("result");
			nameTv.setText(tempString);
			tempUser.setName(tempString);
			break;
		case 3:

			tempString = data.getStringExtra("result");
			addrTv.setText(tempString);
			tempUser.setAddr(tempString);
			break;
		case 4:

			tempString = data.getStringExtra("result");
			telTv.setText(tempString);
			tempUser.setTel(tempString);
			break;

		
		default:
			imageHelper = new ImageHelper(this, avatarIv, requestCode, resultCode, data);
            String imgPath = imageHelper.getImgPath();
            if (!"".equals(imgPath)) {
            	this.imgPath = imgPath;
            }
            
			
			break;
		}

	}

	@Override
	public void startProgress() {
		// TODO Auto-generated method stub
		pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Loading...");
        pd.show();
	}

	@Override
	public void stopProgress() {
		// TODO Auto-generated method stub
		pd.dismiss();
	}
	
	

}
