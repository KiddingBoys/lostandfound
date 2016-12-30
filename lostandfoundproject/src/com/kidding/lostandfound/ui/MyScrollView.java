package com.kidding.lostandfound.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-9 上午12:48:43 
 * @version 1.0 
 * @parameter   
 * @return 
 */
/**
* 解决ScrollView嵌套ViewPager出现的滑动冲突问题
*/
public class MyScrollView extends ScrollView {
View.OnTouchListener mGestureListener;
// 滑动距离及坐标
private float xDistance, yDistance, xLast, yLast;
public MyScrollView(Context context, AttributeSet attrs) {
super(context, attrs);
}
@Override
public boolean onInterceptTouchEvent(MotionEvent ev) {
switch (ev.getAction()) {
case MotionEvent.ACTION_DOWN:
xDistance = yDistance = 0f;
xLast = ev.getX();
yLast = ev.getY();
break;
case MotionEvent.ACTION_MOVE:
final float curX = ev.getX();
final float curY = ev.getY();

xDistance += Math.abs(curX - xLast);
yDistance += Math.abs(curY - yLast);
xLast = curX;
yLast = curY;

if(xDistance > yDistance){
return false;
} 
}
return super.onInterceptTouchEvent(ev);
} 
}
