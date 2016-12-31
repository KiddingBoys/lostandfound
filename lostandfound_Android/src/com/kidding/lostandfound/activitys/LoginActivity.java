package com.kidding.lostandfound.activitys;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.LoginPresenter;
import com.kidding.lostandfound.view.ILoginView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午3:29:47 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class LoginActivity extends BaseActivity implements OnClickListener,ILoginView{

    @ViewInject(R.id.edtTxt_login_account)
    private EditText userNameEdit;
    @ViewInject(R.id.edtTxt_login_pwd)
    private EditText passwordEdit;
    @ViewInject(R.id.btn_login_login)
    private Button loginBtn;
    @ViewInject(R.id.btn_login_rigister)
    private Button registerBtn;
    @ViewInject(R.id.iv_login_face)
    private ImageView faceIv;

    
    private Intent intent;
    private ProgressDialog pd;
    private LoginPresenter loginPresenter;
    
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
 		init();
    }
	
	private void init(){
		loginBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
//		faceIv.setOnClickListener(this);
		loginPresenter = new LoginPresenter(this, this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_login_rigister:
			intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_login_login:
			loginPresenter.pushData(getUserName(), getPassword());
			break;
		case R.id.iv_login_face:
			intent = new Intent(LoginActivity.this,ResetUrlActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userNameEdit.getText().toString().trim();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return passwordEdit.getText().toString().trim();
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			int currentVersion = android.os.Build.VERSION.SDK_INT; 
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) { 
                Intent startMain = new Intent(Intent.ACTION_MAIN); 
                startMain.addCategory(Intent.CATEGORY_HOME); 
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                startActivity(startMain); 
                System.exit(0); 
            } else {// android2.1 
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
                am.restartPackage(getPackageName()); 
            }
    }
		return super.onKeyDown(keyCode, event);
	}
	
}
