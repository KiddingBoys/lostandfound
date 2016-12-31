package com.kidding.lostandfound.view;

import java.util.List;

import android.widget.Button;
import android.widget.EditText;

import com.kidding.lostandfound.request.LFMessage;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-8 ����10:17:49 
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
	Button getSupportBtn(boolean supportFlag);//supportFlag���ж�supportNum�ļӼ�
	void setDelSuccess();
	void setCompleteSuccess();
}
