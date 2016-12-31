package com.kidding.lostandfound.view;
/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-7 ����7:06:21 
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
