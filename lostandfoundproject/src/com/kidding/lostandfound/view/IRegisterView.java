package com.kidding.lostandfound.view;
/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-5 ����7:04:55 
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
