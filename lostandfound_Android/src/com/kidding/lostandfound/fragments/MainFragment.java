package com.kidding.lostandfound.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.activitys.ClassifyActivity;
import com.kidding.lostandfound.activitys.PublishLFMessageActivity;
import com.kidding.lostandfound.adapter.MainMessageAdapter;
import com.kidding.lostandfound.presenter.MainFragmentPresenter;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.ui.MainRefreshableView;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.view.ILFMsgListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2016-5-4 下午7:51:58
 * @version 1.0
 * @parameter
 * @return
 */
public class MainFragment extends Fragment implements OnClickListener,
		ILFMsgListView, MainRefreshableView.RefreshListener {

	private View rootView;

	@ViewInject(R.id.btn_main_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_main_add)
	private TextView topTitleTv;
	@ViewInject(R.id.iv_main_open_slidemenu)
	private ImageView openSlidemenuIv;

	// @ViewInject(R.id.btn_main_publish_searchsth)
	private ImageButton publishSearchSthBtn;
	// @ViewInject(R.id.btn_main_publish_foundsth)
	private ImageButton publishFoundSthBtn;
	// @ViewInject(R.id.btn_main_publish_classify)
	private ImageButton classifyBtn;
	private ImageButton refreshBtn;

	@ViewInject(R.id.lv_main_list)
	private ListView mainAllLv;
	@ViewInject(R.id.refresh_main_root)
	MainRefreshableView mRefreshableView;

	private MainMessageAdapter msgAdapter;
	private List<LFMessage> msgList;
	private boolean isReFlashFlag = true;// 默认刷新
	private MainFragmentPresenter mainFragmentPresenter;

	private View circle_footer;
	private ProgressDialog pd;
	private Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		rootView = (View) inflater.inflate(R.layout.fragment_main, container,
				false);
		ViewUtils.inject(this, rootView);

		init(inflater);
		return rootView;
	}

	private void init(LayoutInflater inflater) {
		topTitleTv.setText("失物招领");
		trueBtn.setVisibility(View.INVISIBLE);
		Glide.with(this).load(SPHelper.instance(getActivity()).getString("userAvatar"))
		 .dontAnimate()
		 .placeholder(R.drawable.ic_launcher)
		 .centerCrop()
		 .into(openSlidemenuIv);
		

		mainFragmentPresenter = new MainFragmentPresenter(getActivity(), this);

		mRefreshableView.setRefreshListener(this);
		View cercle_header = inflater.inflate(R.layout.xlistview_main_header,
				null);
		circle_footer = inflater.inflate(R.layout.xlistview_footer, null);
		circle_footer.setOnClickListener(this);
		mainAllLv.addHeaderView(cercle_header, null, false);
		mainAllLv.addFooterView(circle_footer, null, false);

		publishSearchSthBtn = (ImageButton) cercle_header
				.findViewById(R.id.btn_main_publish_searchsth);
		publishFoundSthBtn = (ImageButton) cercle_header
				.findViewById(R.id.btn_main_publish_foundsth);
		classifyBtn = (ImageButton) cercle_header
				.findViewById(R.id.btn_main_publish_classify);
		refreshBtn = (ImageButton) cercle_header
				.findViewById(R.id.btn_main_publish_other);
		publishSearchSthBtn.setOnClickListener(this);
		publishFoundSthBtn.setOnClickListener(this);
		classifyBtn.setOnClickListener(this);
		refreshBtn.setOnClickListener(this);

		msgList = new ArrayList<LFMessage>();
		msgAdapter = new MainMessageAdapter(getActivity(), msgList);
		mainAllLv.setAdapter(msgAdapter);
		// 初次加载
		isReFlashFlag = true;
		mainFragmentPresenter.getLFMessage(isReFlashFlag);
		startProgress();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == circle_footer) {
			circle_footer.findViewById(R.id.xlistview_footer_progressbar)
					.setVisibility(View.VISIBLE);
			circle_footer.findViewById(R.id.xlistview_footer_hint_textview)
					.setVisibility(View.INVISIBLE);
			// 加载数据
			// 这里进行网络数据请求
			isReFlashFlag = false;
			mainFragmentPresenter.getLFMessage(isReFlashFlag);

		}
		switch (v.getId()) {
		case R.id.btn_main_publish_searchsth:
			intent = new Intent(getActivity(), PublishLFMessageActivity.class);
			intent.putExtra("type", "lost");
			startActivity(intent);
			break;
		case R.id.btn_main_publish_foundsth:
			intent = new Intent(getActivity(), PublishLFMessageActivity.class);
			intent.putExtra("type", "found");
			startActivity(intent);
			break;
		case R.id.btn_main_publish_classify:
			intent = new Intent(getActivity(), ClassifyActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_main_publish_other:
			startProgress();
			isReFlashFlag = true;
			mainFragmentPresenter.getLFMessage(isReFlashFlag);
			break;
		default:
			break;
		}
	}

	
	private void stopRefresh() {
		mRefreshableView.finishRefresh();
	}

	private void stopLoadMore() {
		circle_footer.findViewById(R.id.xlistview_footer_progressbar)
				.setVisibility(View.INVISIBLE);
		circle_footer.findViewById(R.id.xlistview_footer_hint_textview)
				.setVisibility(View.VISIBLE);
	}

	@Override
	public void setData(List<LFMessage> messageList) {
		// TODO Auto-generated method stub
		if (messageList.size() != 0) {
			this.msgList = messageList;
			if (msgAdapter == null) {

				msgAdapter = new MainMessageAdapter(getActivity(), messageList);
				mainAllLv.setAdapter(msgAdapter);

			} else {
				msgAdapter.updateMsgList(msgList);
				showList();
			}
		}

		// 判断，关闭加载/刷新效果
		if (isReFlashFlag) {
			stopRefresh();
			// String label = DateUtils.formatDateTime(getActivity(),
			// System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
			// | DateUtils.FORMAT_SHOW_DATE
			// | DateUtils.FORMAT_ABBREV_ALL);
			// mainAllLv.setRefreshTime(label);
		} else {
			stopLoadMore();
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
	public void onRefresh(MainRefreshableView view) {
		// TODO Auto-generated method stub
		isReFlashFlag = true;
		mainFragmentPresenter.getLFMessage(isReFlashFlag);
	}

	public void startProgress(){
		pd = new ProgressDialog(getActivity());
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在刷新");
		pd.show();
	} 
	@Override
	public void stopProgress() {
		// TODO Auto-generated method stub
		pd.dismiss();
	}
}
