package com.kidding.lostandfound.view;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午7:04:55 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public interface IRegisterView {

	String getUserName();
	String getPassword();
	int getGender();
	String getAddr();
	String getTel();
	void startProgress();
	void stopProgress();
}
