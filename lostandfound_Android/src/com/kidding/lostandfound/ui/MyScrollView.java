package com.kidding.lostandfound.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-9 ����12:48:43 
 * @version 1.0 
 * @parameter   
 * @return 
 */
/**
* ���ScrollViewǶ��ViewPager���ֵĻ�����ͻ����
*/
public class MyScrollView extends ScrollView {
View.OnTouchListener mGestureListener;
// �������뼰����
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
