package com.kidding.lostandfound.activitys;


import com.example.zhy_slidingmenu.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-6 上午10:58:53 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class PersonSetActivity extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.layout_personset_personinfo)
	private RelativeLayout personInfoRl;
	@ViewInject(R.id.layout_personset_changepsw)
	private RelativeLayout changePswRl;
	@ViewInject(R.id.btn_personset_exit)
	private Button exitBtn;
	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_personset);
		ViewUtils.inject(this);
		init();
	}
	private void init(){
		trueBtn.setVisibility(View.INVISIBLE);
		topTitleTv.setText("个人设置");
		
		personInfoRl.setOnClickListener(this);
		changePswRl.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_personset_personinfo:
			intent = new Intent(PersonSetActivity.this,PersonInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_personset_changepsw:
			intent = new Intent(PersonSetActivity.this,UpdatePasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_personset_exit:
			intent = new Intent(PersonSetActivity.this,LoginActivity.class);
			finish();
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
	
}
