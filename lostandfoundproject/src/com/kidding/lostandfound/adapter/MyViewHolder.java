package com.kidding.lostandfound.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2015-8-27 上午10:52:31
 * @version 1.0
 * @parameter
 * @return
 */
public class MyViewHolder {

	private SparseArray<View> mViews;// 使用SparseArray效率比Map高
	private int mPosition;
	private View mConvertView;

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param parent
	 * @param layoutId
	 * @param position
	 */
	public MyViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {

		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);

	}

	public static MyViewHolder getViewHolder(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new MyViewHolder(context, parent, layoutId, position);
		} else {
			MyViewHolder holder = (MyViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}

	}

	/**
	 * 通过viewId获取控件
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;

	}

	public View getConvertView() {
		return mConvertView;
	}

	/** 以下为设置各种控件的方法，可不断添加完善方法 */

	/**
	 * 设置TextView
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public MyViewHolder setText(int viewId, String text) {
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	// setImageView ...
}
