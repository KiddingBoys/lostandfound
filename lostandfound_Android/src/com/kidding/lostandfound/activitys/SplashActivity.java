package com.kidding.lostandfound.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.utils.SPHelper;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-10 ����9:13:52 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class SplashActivity extends BaseActivity{

	private static final int sleepTime = 2000;
	private Context mContext;
	private TextView versionTv;
	private RelativeLayout rootLayout;
	
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);
		this.mContext = this;

		versionTv = (TextView) findViewById(R.id.tv_version);
		versionTv.setText(getVersion());
		rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
		
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		rootLayout.startAnimation(animation);
		
		costTime();
	}
	
	private void costTime(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(SPHelper.instance(mContext).getString("userName")!=null
						&&!"".equals(SPHelper.instance(mContext).getString("userName"))){
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
				}else{
					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
					finish();
				}
				
			}
		}).start();
	}
	
	/**
	 * ��ȡ��ǰӦ�ó���İ汾��
	 */
	private String getVersion() {
		String st = "Version number is wrong";
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return st;
		}
	}
}
