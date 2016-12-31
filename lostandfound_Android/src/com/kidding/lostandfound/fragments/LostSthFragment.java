package com.kidding.lostandfound.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.example.zhy_slidingmenu.R.color;
import com.kidding.lostandfound.presenter.LostFoundSthPresenter;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.utils.MyMessageAdapter;
import com.kidding.lostandfound.utils.XListView;
import com.kidding.lostandfound.utils.XListView.IXListViewListener;
import com.kidding.lostandfound.view.ILFMsgListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2016-5-4 下午7:53:09
 * @version 1.0
 * @parameter
 * @return
 */
public class LostSthFragment extends Fragment implements ILFMsgListView,
		IXListViewListener {
	private View rootView;

	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.btn_back)
	private Button backBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	@ViewInject(R.id.layout_action)
	private RelativeLayout actionRl;
	
	@ViewInject(R.id.lv_lost_sth_list)
	private XListView sthLv;

	final private String type = "lost";
	private MyMessageAdapter msgAdapter;
	private List<LFMessage> msgList;
	private boolean isReFlashFlag = true;// 默认刷新
	private LostFoundSthPresenter lfSthPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = (View) inflater.inflate(R.layout.fragment_sth, container,
				false);
		ViewUtils.inject(this, rootView);

		init();
		return rootView;
	}

	private void init() {
		topTitleTv.setText("寻物启事");
		topTitleTv.setTextColor(color.black);
		trueBtn.setVisibility(View.INVISIBLE);
		backBtn.setVisibility(View.INVISIBLE);
		actionRl.setBackgroundColor(Color.WHITE);

		lfSthPresenter = new LostFoundSthPresenter(getActivity(), this);
		sthLv.setPullLoadEnable(true);
		sthLv.setXListViewListener(this);
		sthLv.setRefreshTime(new Date().toLocaleString());

		msgList = new ArrayList<LFMessage>();

		msgAdapter = new MyMessageAdapter(getActivity(), msgList);
		sthLv.setAdapter(msgAdapter);

		// 初次加载
		isReFlashFlag = true;
		lfSthPresenter.getLFMessage(isReFlashFlag,type);

	}

	@Override
	public void setData(List<LFMessage> messageList) {
		// TODO Auto-generated method stub
		if (messageList.size() != 0) {
			this.msgList = messageList;
			if (msgAdapter == null) {

				msgAdapter = new MyMessageAdapter(getActivity(), messageList);
				sthLv.setAdapter(msgAdapter);

			} else {
				msgAdapter.updateMsgList(msgList);
				showList();
			}
		}

		// 判断，关闭加载/刷新效果
		if (isReFlashFlag) {
			sthLv.stopRefresh();
			String label = DateUtils.formatDateTime(getActivity(),
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			sthLv.setRefreshTime(label);
		} else {
			sthLv.stopLoadMore();
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
		lfSthPresenter.getLFMessage(isReFlashFlag,type);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		// 这里进行网络数据请求
		isReFlashFlag = false;
		lfSthPresenter.getLFMessage(isReFlashFlag,type);
	}

	@Override
	public void stopProgress() {
		// TODO Auto-generated method stub
		
	}
}
