package com.kidding.lostandfound.activitys;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

/**
 * Author: Administrator
 * Time: 2016-04-04.
 */
public class BaseActivity extends FragmentActivity {

	protected int mScreenWidth;
	protected int mScreenHeight;
	
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//获取当前屏幕宽高
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng

    }


    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
    
	/** 获取当前状态栏的高度
	  * getStateBar
	  * @Title: getStateBar
	  * @throws
	  */
	public  int getStateBar(){
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	public static int dip2px(Context context,float dipValue){
		float scale=context.getResources().getDisplayMetrics().density;		
		return (int) (scale*dipValue+0.5f);		
	}
   
}
