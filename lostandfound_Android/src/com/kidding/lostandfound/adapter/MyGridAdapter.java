package com.kidding.lostandfound.adapter;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.activitys.ClassifySameOneActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-9 下午10:58:00 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;
	private Intent intent;
	
	public String[] img_text = { "全部", "钱包", "证件", "钥匙", "数码", "饰品",
			"衣服",  "文件", "书籍","宠物","车辆","其它"};
	public int[] imgs = { R.drawable.icon_classify_wallet, R.drawable.icon_classify_wallet,
			R.drawable.icon_classify_identification, R.drawable.icon_classify_key,
			R.drawable.icon_classify_digit, R.drawable.icon_classify_ornament,
			R.drawable.icon_classify_wallet, R.drawable.icon_classify_wallet, 
			R.drawable.icon_classify_wallet, R.drawable.icon_classify_wallet, 
			R.drawable.icon_classify_wallet, R.drawable.icon_classify_other};

	public MyGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return img_text[position];///
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MyViewHolder myHolder = MyViewHolder.getViewHolder(mContext
				, convertView, parent, R.layout.grid_item, position);
		
		TextView tv = (TextView) myHolder.getConvertView().findViewById(R.id.tv_item);
		ImageView iv = (ImageView) myHolder.getConvertView().findViewById(R.id.iv_item);
		RelativeLayout itemRl = (RelativeLayout) myHolder.getConvertView().findViewById(R.id.rl_grid_item);
		
		iv.setBackgroundResource(imgs[position]);

		final int i =  position;
		
		tv.setText(img_text[position]);
		itemRl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(mContext,ClassifySameOneActivity.class);
				intent.putExtra("category",img_text[i]);
				mContext.startActivity(intent);
			}
		});
		
		return myHolder.getConvertView();
	}

}
