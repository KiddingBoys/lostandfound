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
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-5 ����3:29:47 
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
			Toast.makeText(context, "�û���/���벻��Ϊ��",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!userName.matches("[\u4e00-\u9fa5\\w]+")) {
			Toast.makeText(context, "��������ְ����Ƿ��ַ���",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!password.matches("(\\p{Alnum})+")) {
			Toast.makeText(context, "�������������������ֺ���ĸ�ַ�֮��������ַ���",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!password.matches("(\\p{Alnum}){6,}")) {
			Toast.makeText(context, "�������������6λ���ϣ�",
					Toast.LENGTH_SHORT).show();
			return ;
		}
		
		iLoginView.startProgress();
		loginModel.pushLoginData(userName, password);
	}
	
	public void showResult(User user){
		iLoginView.stopProgress();
		if(user!=null){
			//����
			SPHelper.instance(context).setString("userName", user.getName());
			SPHelper.instance(context).setString("userPwd", user.getPassword());
			SPHelper.instance(context).setInt("userGender", user.getGender());
			SPHelper.instance(context).setString("userTel", user.getTel());
			SPHelper.instance(context).setString("userAddr", user.getAddr());
			SPHelper.instance(context).setString("userAvatar", user.getAvatar());
	
			//����ҳ�����ת
			Intent intent = new Intent(context, MainActivity.class);
			context.startActivity(intent);
		}

	}
	
	
	
}
