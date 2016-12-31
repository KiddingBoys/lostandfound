package com.kidding.lostandfound.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.activitys.LFMessageDetailsActivity;
import com.kidding.lostandfound.adapter.MyViewHolder;
import com.kidding.lostandfound.request.LFMessage;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-12-28 ����11:33:03 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyMessageAdapter extends BaseAdapter{

	private Context context;
	private Intent intent;
	private int isMyMsgFlag=0;//�Ƿ�Ϊ�ҵ����ӽ���
	private List<LFMessage> msgDataList = new ArrayList<LFMessage>();
	
	
	public MyMessageAdapter(Context context,List<LFMessage> msgDataList){
		this.context =context;
		this.msgDataList = msgDataList;
	}
	
	public void updateMsgList(List<LFMessage> msgDataList){
		this.msgDataList = msgDataList;
	}
	public void setIsMyMsgFlag(int isMyMsgFlag){
		this.isMyMsgFlag = isMyMsgFlag;
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
		MyViewHolder holder = MyViewHolder.getViewHolder(context, convertView,parent, R.layout.item_main_list, position);
		
		ImageView infoPicIv = (ImageView) holder.getConvertView().findViewById(R.id.iv_main_listitem_infopic);

		TextView titleTv = (TextView) holder.getConvertView().findViewById(R.id.tv_main_listitem_title);
		TextView posterTv = (TextView) holder.getConvertView().findViewById(R.id.tv_main_listitem_poster);
		TextView placeTv = (TextView) holder.getConvertView().findViewById(R.id.tv_main_listitem_place);
		TextView timeTv = (TextView) holder.getConvertView().findViewById(R.id.tv_main_listitem_time);
		TextView telTv = (TextView) holder.getConvertView().findViewById(R.id.tv_main_listitem_tel);
		RelativeLayout lfMsgRl = (RelativeLayout) holder.getConvertView().findViewById(R.id.rl_item_main_lfmessage);
		
		final LFMessage lfMessageCurrent = (LFMessage) this.getItem(position);
		titleTv.setText(lfMessageCurrent.getTitle());
		posterTv.setText(lfMessageCurrent.getPoster());
		placeTv.setText(lfMessageCurrent.getPlace());
		timeTv.setText(lfMessageCurrent.getTime());
		telTv.setText(lfMessageCurrent.getTel());
		Glide.with(context).load(lfMessageCurrent.getImage())
		.placeholder(R.drawable.ic_personset_changepsw).centerCrop().into(infoPicIv);
		//�������ݵĴ洢
		lfMsgRl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(context,LFMessageDetailsActivity.class);
				intent.putExtra("title",lfMessageCurrent.getTitle());
				intent.putExtra("poster",lfMessageCurrent.getPoster());
				intent.putExtra("place",lfMessageCurrent.getPlace());
				intent.putExtra("tel",lfMessageCurrent.getTel());
				intent.putExtra("time",lfMessageCurrent.getTime());
				intent.putExtra("content",lfMessageCurrent.getContent());
				intent.putExtra("image",lfMessageCurrent.getImage());
				intent.putExtra("id",lfMessageCurrent.getId());
				intent.putExtra("ismymsg", isMyMsgFlag);
				
				intent.putExtra("found",lfMessageCurrent.getFound());
				intent.putExtra("support",lfMessageCurrent.getSupport());
				
				context.startActivity(intent);
			}
		});
		
		
		return holder.getConvertView();
	}

}
