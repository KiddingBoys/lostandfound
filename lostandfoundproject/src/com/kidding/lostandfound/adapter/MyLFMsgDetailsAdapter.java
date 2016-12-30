package com.kidding.lostandfound.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.LFMsgDetailPresenter;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.view.ILFMsgDetailView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-8 下午6:03:51 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyLFMsgDetailsAdapter extends BaseAdapter{

	private Context context;
	private Intent intent;
	private List<LFMessage> msgDataList = new ArrayList<LFMessage>();
	private ILFMsgDetailView lDetailView;
	
	
	public MyLFMsgDetailsAdapter(Context context,List<LFMessage> msgDataList
			,ILFMsgDetailView lDetailView){
		this.context =context;
		this.msgDataList = msgDataList;
		this.lDetailView = lDetailView;
		
	}
	
	public void updateMsgList(List<LFMessage> msgDataList){
		this.msgDataList = msgDataList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgDataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return msgDataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder holder = new MyViewHolder(context, parent, R.layout.item_lfmsg_details_comment, position);
		
		ImageView infoPicIv = (ImageView) holder.getConvertView().findViewById(R.id.iv_main_listitem_infopic);

		TextView posterTv = (TextView) holder.getConvertView().findViewById(R.id.tv_lfmsg_details_comment_poster);
		TextView contentTv = (TextView) holder.getConvertView().findViewById(R.id.tv_lfmsg_details_comment_content);
		TextView timeTv = (TextView) holder.getConvertView().findViewById(R.id.tv_lfmsg_details_comment_time);
		TextView replyTv = (TextView) holder.getConvertView().findViewById(R.id.tv_lfmsg_details_comment_reply);
		
		final LFMessage lfMessageCurrent = (LFMessage) this.getItem(position);
		
		
		posterTv.setText(lfMessageCurrent.getPoster());
		timeTv.setText(lfMessageCurrent.getTime());
		contentTv.setText(lfMessageCurrent.getContent());
		//回复按钮
		replyTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//回复poster
				String tempString = "@"+lfMessageCurrent.getPoster()+"：";
				lDetailView.getEditTextView().setText(tempString);
				//设置光标位置
				lDetailView.getEditTextView().setSelection(tempString.length());
				lDetailView.getEditTextView().setSelected(true);
			}
		});
		
		
		return holder.getConvertView();
	}

}
