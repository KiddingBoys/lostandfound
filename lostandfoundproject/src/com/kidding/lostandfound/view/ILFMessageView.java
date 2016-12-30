package com.kidding.lostandfound.view;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-7 下午7:06:21 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public interface ILFMessageView {

	String getMessageTime();
	String getMessageTitle();
	String getMessagePlace();
	String getMessageContent();
	String getMessageCategory();
	String getMessageType();
	
	void startProgress();
	void stopProgress(int code);
}
