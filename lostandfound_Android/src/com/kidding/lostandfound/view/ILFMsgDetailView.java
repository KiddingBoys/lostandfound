package com.kidding.lostandfound.view;

import java.util.List;

import android.widget.Button;
import android.widget.EditText;

import com.kidding.lostandfound.request.LFMessage;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-8 下午10:17:49 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public interface ILFMsgDetailView {

	void startProgress();
	void stopProgress(List<LFMessage> msgList);
	int getMsgId();
	void setNewCommentData(List<LFMessage> msgList);
	void setToUserName(String toName);
	EditText getEditTextView();
	Button getSupportBtn(boolean supportFlag);//supportFlag是判断supportNum的加减
	void setDelSuccess();
	void setCompleteSuccess();
}
