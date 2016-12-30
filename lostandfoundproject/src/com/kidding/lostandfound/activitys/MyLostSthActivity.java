package com.kidding.lostandfound.activitys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.MyLFMsgPresenter;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.utils.MyMessageAdapter;
import com.kidding.lostandfound.utils.XListView;
import com.kidding.lostandfound.utils.XListView.IXListViewListener;
import com.kidding.lostandfound.view.ILFMsgListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-9 下午8:29:02 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyLostSthActivity extends BaseActivity implements ILFMsgListView,
IXListViewListener{

	@ViewInject(R.id.lv_mylostfound_list)
	private XListView myMsgLv;
	@ViewInject(R.id.tv_topback_title)
	private TextView titleTv;

	private String type = "lost";
	private MyMessageAdapter msgAdapter;
	private List<LFMessage> msgList;
	private boolean isReFlashFlag = true;// 默认刷新
	private MyLFMsgPresenter myLFMsgPresenter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_mylost);
		ViewUtils.inject(this);
		init();
	}
	private void init(){
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		titleTv.setText(intent.getStringExtra("title"));
		
		myLFMsgPresenter = new MyLFMsgPresenter(this, this);
		myMsgLv.setPullLoadEnable(true);
		myMsgLv.setXListViewListener(this);
		myMsgLv.setRefreshTime(new Date().toLocaleString());

		msgList = new ArrayList<LFMessage>();

		msgAdapter = new MyMessageAdapter(this, msgList);
		msgAdapter.setIsMyMsgFlag(1);
		myMsgLv.setAdapter(msgAdapter);

		// 初次加载
		isReFlashFlag = true;
		myLFMsgPresenter.getLFMessage(isReFlashFlag,type);
	}
	

	@Override
	public void setData(List<LFMessage> messageList) {
		// TODO Auto-generated method stub
		if (messageList.size() != 0) {
			this.msgList = messageList;
			if (msgAdapter == null) {

				msgAdapter = new MyMessageAdapter(this, messageList);
				myMsgLv.setAdapter(msgAdapter);

			} else {
				msgAdapter.updateMsgList(msgList);
				showList();
			}
		}

		// 判断，关闭加载/刷新效果
		if (isReFlashFlag) {
			myMsgLv.stopRefresh();
			String label = DateUtils.formatDateTime(this,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			myMsgLv.setRefreshTime(label);
		} else {
			myMsgLv.stopLoadMore();
		}

	}

	@Override
	public void showList() {
		// TODO Auto-generated method stub
		if (msgAdapter != null) {
			msgAdapter.notifyDataSetChanged(); // 如果数据改变界面也会跟随改变
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		isReFlashFlag = true;
		myLFMsgPresenter.getLFMessage(isReFlashFlag,type);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		// 这里进行网络数据请求
		isReFlashFlag = false;
		myLFMsgPresenter.getLFMessage(isReFlashFlag,type);
	}
	@Override
	public void stopProgress() {
		// TODO Auto-generated method stub
		
	}
}

