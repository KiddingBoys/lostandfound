package com.kidding.lostandfound.presenter;

import android.content.Context;
import android.widget.Toast;

import com.kidding.lostandfound.model.RegisterModel;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.view.IRegisterView;


/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-5 ����7:11:29 
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
		//��User���������ж�
		if (!user.getName().matches("[\u4e00-\u9fa5\\w]+")) {
			Toast.makeText(context, "��������ְ����Ƿ��ַ���",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!user.getPassword().matches("(\\p{Alnum})+")) {
			Toast.makeText(context, "�������������������ֺ���ĸ�ַ�֮��������ַ���",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!user.getPassword().matches("(\\p{Alnum}){6,}")) {
			Toast.makeText(context, "�������������6λ���ϣ�",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!user.getTel().matches("(\\d){11}")) {
			Toast.makeText(context, "����ĺ����ʽ����ȷ��",
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
