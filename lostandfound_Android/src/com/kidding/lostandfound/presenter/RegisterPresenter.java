package com.kidding.lostandfound.presenter;

import android.content.Context;
import android.widget.Toast;

import com.kidding.lostandfound.model.RegisterModel;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.view.IRegisterView;


/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午7:11:29 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class RegisterPresenter {

	private IRegisterView iRegisterView;
	private RegisterModel registerModel;
	private Context context;
	
	public RegisterPresenter(IRegisterView iRegisterView,Context context){
		this.iRegisterView = iRegisterView;
		registerModel = new RegisterModel(context,this);
		this.context = context;
	}
	
	public void pushRegisterData(User user){
		//对User参数进行判断
		if (!user.getName().matches("[\u4e00-\u9fa5\\w]+")) {
			Toast.makeText(context, "输入的名字包含非法字符！",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!user.getPassword().matches("(\\p{Alnum})+")) {
			Toast.makeText(context, "输入的密码包含除了数字和字母字符之外的其他字符！",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!user.getPassword().matches("(\\p{Alnum}){6,}")) {
			Toast.makeText(context, "输入的密码至少6位以上！",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!user.getTel().matches("(\\d){11}")) {
			Toast.makeText(context, "输入的号码格式不正确！",
					Toast.LENGTH_SHORT).show();
			return ;
		}
		
		iRegisterView.startProgress();
		registerModel.pushData(user);
	}
	
	public void showResult(){
		iRegisterView.stopProgress();
	}
}
