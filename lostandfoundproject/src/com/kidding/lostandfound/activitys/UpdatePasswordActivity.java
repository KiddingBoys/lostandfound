package com.kidding.lostandfound.activitys;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.UpdatePasswordPresenter;
import com.kidding.lostandfound.view.IUpdatePasswordView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-6 下午6:50:08 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class UpdatePasswordActivity extends BaseActivity implements IUpdatePasswordView{
	
	@ViewInject(R.id.et_updatepassword_originalpwd)
	private EditText originalpwdEt;
	@ViewInject(R.id.et_updatepassword_newpwd)
	private EditText newpwdEt;
	@ViewInject(R.id.et_updatepassword_newpwd_again)
	private EditText newpwdAgainpwdEt;
	@ViewInject(R.id.btn_updatepassword_update)
	private Button updateBtn;
	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	
	private ProgressDialog pd;
	private UpdatePasswordPresenter upPasswordPresenter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	    setContentView(R.layout.activity_updatepassword);
	    ViewUtils.inject(this);
	    init();
	}
	private void init(){
		topTitleTv.setText("修改密码");
		trueBtn.setVisibility(View.INVISIBLE);
		upPasswordPresenter = new UpdatePasswordPresenter(this, this);
		
		updateBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				upPasswordPresenter.pushNewPwd();
			}
		});
	}
	@Override
	public String getOriginalPwd() {
		// TODO Auto-generated method stub
		return originalpwdEt.getText().toString().trim();
	}
	@Override
	public String getNewPwd() {
		// TODO Auto-generated method stub
		return newpwdEt.getText().toString().trim();
	}
	@Override
	public String getAgainNewPwd() {
		// TODO Auto-generated method stub
		return newpwdAgainpwdEt.getText().toString().trim();
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
	public void stopProgress(int flag) {
		// TODO Auto-generated method stub
		pd.dismiss();
		//flag为0表示修改失败，1表示修改成功
		if(flag==1){
			finish();
		}
		
	}

}
