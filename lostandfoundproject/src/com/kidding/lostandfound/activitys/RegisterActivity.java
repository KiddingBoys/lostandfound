package com.kidding.lostandfound.activitys;


import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.RegisterPresenter;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.view.IRegisterView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午3:30:18 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class RegisterActivity extends BaseActivity implements IRegisterView{

	 @ViewInject(R.id.tv_topback_title)
	 private TextView titleTv;
	 @ViewInject(R.id.btn_register)
	 private Button registerBtn;
	 @ViewInject(R.id.register_username)
	 private TextView userNameTv;
	 @ViewInject(R.id.register_password)
	 private TextView passwordTv;
	 @ViewInject(R.id.register_phone)
	 private TextView phoneTv;
	 @ViewInject(R.id.register_addr)
	 private TextView addrTv;
	 @ViewInject(R.id.register_usergender)
	 private RadioGroup userGenderRg;
	 @ViewInject(R.id.male)
	 private RadioButton maleRb;
	 @ViewInject(R.id.female)
	 private RadioButton femaleRb;
	    
	private int userGender = 0;  
	private RegisterPresenter registerPresenter;
	private User user;
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_register);
		ViewUtils.inject(this);
		init();
	}
	
	private void init() {
        titleTv.setText("register");
        registerPresenter = new RegisterPresenter(this, this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	user = new User(getUserName(),getPassword(),getGender(),getTel(),getAddr(),"");
            	registerPresenter.pushRegisterData(user);
            }
        });
        userGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               
                RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
               
                userGender = "男".equals(rb.getText().toString())?0:1;
               
            }
        });


    }

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userNameTv.getText().toString().trim();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return passwordTv.getText().toString().trim();
	}

	@Override
	public int getGender() {
		// TODO Auto-generated method stub
		return userGender;
	}

	@Override
	public String getAddr() {
		// TODO Auto-generated method stub
		return addrTv.getText().toString().trim();
	}

	@Override
	public String getTel() {
		// TODO Auto-generated method stub
		return phoneTv.getText().toString().trim();
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
