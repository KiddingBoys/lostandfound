package com.kidding.lostandfound.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-9 上午12:50:01 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MyListView extends ListView{


	//必须实现三个构造方法
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	                MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	
	}
}
