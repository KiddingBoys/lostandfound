package com.kidding.lostandfound.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.utils.SPHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-12 下午2:54:51 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class NewsDetailActivity extends BaseActivity{

	@ViewInject(R.id.tv_newsdetail_title)
	private TextView titleTv;
	@ViewInject(R.id.tv_newsdetail_content)
	private TextView contentTv;
	@ViewInject(R.id.iv_newsdetail_newsimg)
	private ImageView newsIv;
	
	private Intent intent;
	private String imgUrl;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_newsdetail);
		ViewUtils.inject(this);
		
		intent = getIntent();
		imgUrl = intent.getStringExtra("newsimg");
		
		titleTv.setText(intent.getStringExtra("newstitle"));
		contentTv.setText(intent.getStringExtra("newscontent"));
		Glide.with(this).load(imgUrl)
		 .placeholder(R.drawable.icon_news_background)
		 .centerCrop()
		 .into(newsIv);
		
		newsIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				openZoomImg();
			}
		});
	}
	
	public void openZoomImg() {
		intent = new Intent(NewsDetailActivity.this,
				ZoomImageActivity.class);
		int[] location = new int[2];
		newsIv.getLocationOnScreen(location);
		intent.putExtra("locationX", location[0]);
		intent.putExtra("locationY", location[1]);

		intent.putExtra("width", newsIv.getWidth());
		intent.putExtra("height", newsIv.getHeight());

		intent.putExtra("imgurl", imgUrl);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}
}
