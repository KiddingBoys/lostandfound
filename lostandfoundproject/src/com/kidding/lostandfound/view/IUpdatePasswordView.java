package com.kidding.lostandfound.view;
/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-6 ����8:06:23 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public interface IUpdatePasswordView {

	String getOriginalPwd();
	String getNewPwd();
	String getAgainNewPwd();
	void startProgress();
	void stopProgress(int flag);
}
