package com.kidding.lostandfound.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2015-8-27 ����8:50:15 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{

	protected Context context;//����Ϊprotected�����Ա�̳�
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	private int layoutId;//listItem��layout��Դid
	
	public MyBaseAdapter(Context context,List<T> data,int layoutId){
		this.context = context;
		this.mDatas = data;
		this.layoutId = layoutId;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * ����getView�����ǲ�ͬ�ģ����Խ������
	 */
	@Override
	public  View getView(int position, View convertView, ViewGroup parent){
		MyViewHolder holder = new MyViewHolder(context, parent, layoutId, position);
		convert(holder,getItem(position));
		return holder.getConvertView();
	}
	public abstract void convert(MyViewHolder holder,T t);

}
