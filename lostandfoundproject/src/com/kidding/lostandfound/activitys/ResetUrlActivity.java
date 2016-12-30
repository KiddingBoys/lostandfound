package com.kidding.lostandfound.activitys;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-11 下午12:35:02 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class ResetUrlActivity extends BaseActivity{
	
	@ViewInject(R.id.tv_url_url_ip)
	private TextView ipTv;
	@ViewInject(R.id.et_url_newurl)
	private EditText newUrlEt;
	@ViewInject(R.id.layout_url_ok)
	private RelativeLayout okTv;
	
	private Context context;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_reseturl);
		ViewUtils.inject(this);
		this.context = this;
		
		ipTv.setText(UrlUtils.urlIp);
		okTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String tempUrlIp= newUrlEt.getText().toString().trim();
				if("".equals(tempUrlIp)){
					ToastUtil.toastshow(context, "不能为空");
				}else{
					UrlUtils.urlIp = tempUrlIp;
					UrlUtils.lostandfoundUrl = "http://"+UrlUtils.urlIp+"/lostandfound";
					UrlUtils.registerUrl = UrlUtils.lostandfoundUrl + "/register/";
					UrlUtils.loginUrl = UrlUtils.lostandfoundUrl + "/login/";
					UrlUtils.updateInfoUrl = UrlUtils.lostandfoundUrl + "/user/modify_Data";
					UrlUtils.updatePwdUrl = UrlUtils.lostandfoundUrl + "/user/modify_Password";
					UrlUtils.publishLFMegUrl = UrlUtils.lostandfoundUrl + "/post/post_Subject";
					UrlUtils.getLFMegUrl = UrlUtils.lostandfoundUrl + "/post/subject";
					UrlUtils.getCommentUrl = UrlUtils.lostandfoundUrl + "/post/comment";
					UrlUtils.sendCommentUrl = UrlUtils.lostandfoundUrl + "/post/post_Comment";
					UrlUtils.setSupportUrl = UrlUtils.lostandfoundUrl + "/post/support";
					UrlUtils.deleteMsgUrl = UrlUtils.lostandfoundUrl + "/post/delete";
					UrlUtils.completeMsgUrl = UrlUtils.lostandfoundUrl + "/post/found";
					 
					SPHelper.instance(context).setString("urlIp", tempUrlIp);
					ToastUtil.toastshow(context, "修改成功"+UrlUtils.loginUrl);
				}
			}
		});
		
	}

}
