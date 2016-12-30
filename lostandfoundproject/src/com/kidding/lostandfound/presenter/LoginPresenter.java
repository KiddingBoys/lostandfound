package com.kidding.lostandfound.presenter;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kidding.lostandfound.activitys.LoginActivity;
import com.kidding.lostandfound.activitys.MainActivity;
import com.kidding.lostandfound.model.LoginModel;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.view.ILoginView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午3:29:47 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class LoginPresenter {

	private LoginModel loginModel;
	private ILoginView iLoginView;
	private Context context;
	
	public LoginPresenter(Context context,ILoginView iLoginView){
		this.context = context;
		this.iLoginView = iLoginView;
		loginModel = new LoginModel(context, this);
	}
	
	public void pushData(String userName,String password){
		if("".equals(userName)||"".equals(password)){
			Toast.makeText(context, "用户名/密码不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!userName.matches("[\u4e00-\u9fa5\\w]+")) {
			Toast.makeText(context, "输入的名字包含非法字符！",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!password.matches("(\\p{Alnum})+")) {
			Toast.makeText(context, "输入的密码包含除了数字和字母字符之外的其他字符！",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!password.matches("(\\p{Alnum}){6,}")) {
			Toast.makeText(context, "输入的密码至少6位以上！",
					Toast.LENGTH_SHORT).show();
			return ;
		}
		
		iLoginView.startProgress();
		loginModel.pushLoginData(userName, password);
	}
	
	public void showResult(User user){
		iLoginView.stopProgress();
		if(user!=null){
			//緩存
			SPHelper.instance(context).setString("userName", user.getName());
			SPHelper.instance(context).setString("userPwd", user.getPassword());
			SPHelper.instance(context).setInt("userGender", user.getGender());
			SPHelper.instance(context).setString("userTel", user.getTel());
			SPHelper.instance(context).setString("userAddr", user.getAddr());
			SPHelper.instance(context).setString("userAvatar", user.getAvatar());
	
			//进行页面的跳转
			Intent intent = new Intent(context, MainActivity.class);
			context.startActivity(intent);
		}

	}
	
	
	
}
