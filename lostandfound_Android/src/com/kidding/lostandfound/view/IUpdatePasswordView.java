package com.kidding.lostandfound.view;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-6 下午8:06:23 
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
