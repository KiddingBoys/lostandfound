package com.kidding.lostandfound.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.adapter.MyGridAdapter;
import com.kidding.lostandfound.ui.MyGridView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-9 下午9:13:47 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class ClassifyActivity extends BaseActivity{

	@ViewInject(R.id.gv_classify)
	private MyGridView classifyGv;
	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_classify);
		ViewUtils.inject(this);
		init();
	}
	private void init(){
		trueBtn.setVisibility(View.INVISIBLE);
		topTitleTv.setText("分类");
		classifyGv.setAdapter(new MyGridAdapter(this));
	}
}
