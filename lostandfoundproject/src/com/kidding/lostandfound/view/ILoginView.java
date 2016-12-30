package com.kidding.lostandfound.view;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午9:08:04 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public interface ILoginView {

	String getUserName();
	String getPassword();
	void startProgress();
	void stopProgress();
}
