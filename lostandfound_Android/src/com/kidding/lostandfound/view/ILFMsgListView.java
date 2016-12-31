package com.kidding.lostandfound.view;

import java.util.List;

import com.kidding.lostandfound.request.LFMessage;



/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-7 下午11:37:05 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public interface ILFMsgListView {

	public void setData(List<LFMessage> messageList);
	public void showList();
	public void stopProgress();
}
