package com.kidding.lostandfound.fragments;

import java.util.Date;

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
import com.kidding.lostandfound.adapter.MyNewsAdapter;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.XListView;
import com.kidding.lostandfound.utils.XListView.IXListViewListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-4 下午7:52:53 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class LostPeopleFragment extends Fragment implements IXListViewListener{
	 private View rootView;
		@ViewInject(R.id.btn_true)
		private Button trueBtn;
		@ViewInject(R.id.btn_back)
		private Button backBtn;
		@ViewInject(R.id.tv_add)
		private TextView topTitleTv;
		@ViewInject(R.id.layout_action)
		private RelativeLayout actionRl;
		
	 @ViewInject(R.id.lv_fragment_news)
	 private XListView newsLv;
	 
	 private MyNewsAdapter myNewsAdapter;
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    // return super.onCreateView(inflater, container, savedInstanceState);
	    rootView = (View) inflater.inflate(R.layout.fragment_people, container, false);
	    ViewUtils.inject(this, rootView);

	    init();
	    return rootView;
	 }
	    
	 private void init(){
			topTitleTv.setText("新闻时事");
			topTitleTv.setTextColor(color.black);
			trueBtn.setVisibility(View.INVISIBLE);
			backBtn.setVisibility(View.INVISIBLE);
			actionRl.setBackgroundColor(Color.WHITE);
			
			myNewsAdapter  = new MyNewsAdapter(getActivity());
			newsLv.setAdapter(myNewsAdapter);
			newsLv.setPullLoadEnable(true);
			newsLv.setXListViewListener(this);
			newsLv.setRefreshTime(new Date().toLocaleString());
	 }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
			newsLv.stopRefresh();
			String label = DateUtils.formatDateTime(getActivity(),
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			newsLv.setRefreshTime(label);
			ToastUtil.toastshow(getActivity(), "已是最新数据");
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		newsLv.stopLoadMore();
		ToastUtil.toastshow(getActivity(), "暂无数据");
	}
}
