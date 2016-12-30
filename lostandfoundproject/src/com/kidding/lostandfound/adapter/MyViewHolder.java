package com.kidding.lostandfound.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author ���� : KiddingBoy
 * @date ����ʱ�䣺2015-8-27 ����10:52:31
 * @version 1.0
 * @parameter
 * @return
 */
public class MyViewHolder {

	private SparseArray<View> mViews;// ʹ��SparseArrayЧ�ʱ�Map��
	private int mPosition;
	private View mConvertView;

	/**
	 * ���캯��
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
	 * ͨ��viewId��ȡ�ؼ�
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

	/** ����Ϊ���ø��ֿؼ��ķ������ɲ���������Ʒ��� */

	/**
	 * ����TextView
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
