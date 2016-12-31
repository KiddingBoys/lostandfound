package com.kidding.lostandfound.ui;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;


public class StatusSmoothImageView extends ImageView {

	private static final int STATE_NORMAL = 0;
	private static final int STATE_TRANSFORM_IN = 1;
	private static final int STATE_TRANSFORM_OUT = 2;
	private int mOriginalWidth;
	private int mOriginalHeight;
	private int mOriginalLocationX;
	private int mOriginalLocationY;
	private int mState = STATE_NORMAL;
	private Matrix mSmoothMatrix;
	private Bitmap mBitmap;
	private boolean mTransformStart = false;
	private Transfrom mTransfrom;
	private final int mBgColor = 0xFF000000;
	private int mBgAlpha = 0;
	private Paint mPaint;
	
	public StatusSmoothImageView(Context context) {
		super(context);
		init();
	}

	public StatusSmoothImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StatusSmoothImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mSmoothMatrix = new Matrix();
		mPaint=new Paint();
		mPaint.setColor(mBgColor);
		mPaint.setStyle(Style.FILL);
//		setBackgroundColor(mBgColor);
	}

	public void setOriginalInfo(int width, int height, int locationX, int locationY) {
		mOriginalWidth = width;
		mOriginalHeight = height;
		mOriginalLocationX = locationX;
		mOriginalLocationY = locationY;
		// 鍥犱负鏄睆骞曞潗鏍囷紝鎵�浠ヨ杞崲涓鸿瑙嗗浘鍐呯殑鍧愭爣锛屽洜涓烘垜鎵�鐢ㄧ殑璇ヨ鍥炬槸MATCH_PARENT锛屾墍浠ヤ笉鐢ㄥ畾浣嶈瑙嗗浘鐨勪綅缃�,濡傛灉涓嶆槸鐨勮瘽锛岃繕闇�瑕佸畾浣嶈鍥剧殑浣嶇疆锛岀劧鍚庤绠梞OriginalLocationX鍜宮OriginalLocationY
		mOriginalLocationY = mOriginalLocationY - getStatusBarHeight(getContext());
	}

	/**
	 * 鑾峰彇鐘舵�佹爮楂樺�?
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 鐢ㄤ簬寮�濮嬭繘鍏ョ殑鏂规硶銆� 璋冪敤姝ゆ柟鍓嶏紝闇�宸茬粡璋冪敤杩噑etOriginalInfo
	 */
	public void transformIn() {
		mState = STATE_TRANSFORM_IN;
		mTransformStart = true;
		invalidate();
	}

	/**
	 * 鐢ㄤ簬寮�濮嬮��鍑虹殑鏂规硶銆�? 璋冪敤姝ゆ柟鍓嶏紝闇�宸茬粡璋冪敤杩噑etOriginalInfo
	 */
	public void transformOut() {
		mState = STATE_TRANSFORM_OUT;
		mTransformStart = true;
		invalidate();
	}

	private class Transfrom {
		float startScale;// 鍥剧墖寮�濮嬬殑缂╂斁鍊�?
		float endScale;// 鍥剧墖缁撴潫鐨勭缉鏀惧��?
		float scale;// 灞炴�alueAnimator璁＄畻鍑烘潵鐨勫��
		LocationSizeF startRect;// 寮�濮嬬殑鍖哄煙
		LocationSizeF endRect;// 缁撴潫鐨勫尯鍩�
		LocationSizeF rect;// 灞炴�alueAnimator璁＄畻鍑烘潵鐨勫��

		void initStartIn() {
			scale = startScale;
			try {
				rect = (LocationSizeF) startRect.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}

		void initStartOut() {
			scale = endScale;
			try {
				rect = (LocationSizeF) endRect.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 鍒濆鍖栬繘鍏ョ殑鍙�?噺淇℃伅
	 */
	private void initTransform() {
		if (getDrawable() == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		//闃叉mTransfrom閲嶅鐨勫仛鍚屾牱鐨勫垵濮嬪�?
		if (mTransfrom != null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		mTransfrom = new Transfrom();

		/** 涓嬮潰涓虹缉�?剧殑璁＄�? */
		/* 璁＄畻鍒濆鐨勭缉鏀惧�硷紝鍒濆鍊煎洜涓烘槸CENTR_CROP鏁堟灉锛屾墍浠ヨ淇濊瘉鍥剧墖鐨勫鍜岄珮鑷冲皯1涓兘鍖归厤鍘熷鐨勫鍜岄珮锛屽彟1涓ぇ浜�? */
		float xSScale = mOriginalWidth / ((float) mBitmap.getWidth());
		float ySScale = mOriginalHeight / ((float) mBitmap.getHeight());
		float startScale = xSScale > ySScale ? xSScale : ySScale;
		mTransfrom.startScale = startScale;
		/* 璁＄畻缁撴潫鏃跺�欑殑缂╂斁鍊硷紝缁撴潫鍊煎洜涓鸿杈惧埌FIT_CENTER鏁堟灉锛屾墍浠ヨ淇濊瘉鍥剧墖鐨勫鍜岄珮鑷冲皯1涓兘鍖归厤鍘熷鐨勫鍜岄珮锛屽彟1涓皬浜�? */
		float xEScale = getWidth() / ((float) mBitmap.getWidth());
		float yEScale = getHeight() / ((float) mBitmap.getHeight());
		float endScale = xEScale < yEScale ? xEScale : yEScale;
		mTransfrom.endScale = endScale;

		/**
		 * 涓嬮潰璁＄畻Canvas Clip鐨勮寖鍥达紝涔熷氨鏄浘鐗囩殑鏄剧ず鐨勮寖鍥达紝鍥犱负鍥剧墖鏄參鎱㈠彉澶э紝骞朵笖鏄瓑姣斾緥鐨勶紝鎵�浠ヨ繖涓晥鏋滆繕闇�瑕佽鍑忓浘鐗囨樉�?虹殑鍖哄�?
		 * 锛岃�屾樉绀哄尯鍩熺殑鍙樺寲鑼冨洿鏄湪鍘熷CENTER_CROP鏁堟灉鐨勮寖鍥村尯鍩�?
		 * 锛屽埌鏈�缁堢殑FIT_CENTER鐨勮寖鍥翠箣闂寸殑锛屽尯鍩熸垜鐢↙ocationSizeF鏇村ソ璁＄畻
		 * 锛屼粬灏卞寘鎷乏涓婇�?�鐐瑰潗鏍囷紝鍜屽楂橈紝鏈�鍚庤浆涓篊anvas瑁佸噺鐨凴ect.
		 */
		/* 寮�濮嬪尯鍩�? */
		mTransfrom.startRect = new LocationSizeF();
		mTransfrom.startRect.left = mOriginalLocationX;
		mTransfrom.startRect.top = mOriginalLocationY;
		mTransfrom.startRect.width = mOriginalWidth;
		mTransfrom.startRect.height = mOriginalHeight;
		/* 缁撴潫鍖哄煙 */
		mTransfrom.endRect = new LocationSizeF();
		float bitmapEndWidth = mBitmap.getWidth() * mTransfrom.endScale;// 鍥剧墖鏈�缁堢殑瀹藉�?
		float bitmapEndHeight = mBitmap.getHeight() * mTransfrom.endScale;// 鍥剧墖鏈�缁堢殑瀹藉�?
		mTransfrom.endRect.left = (getWidth() - bitmapEndWidth) / 2;
		mTransfrom.endRect.top = (getHeight() - bitmapEndHeight) / 2;
		mTransfrom.endRect.width = bitmapEndWidth;
		mTransfrom.endRect.height = bitmapEndHeight;

		mTransfrom.rect = new LocationSizeF();
	}

	private class LocationSizeF implements Cloneable{
		float left;
		float top;
		float width;
		float height;
		@Override
		public String toString() {
			return "[left:"+left+" top:"+top+" width:"+width+" height:"+height+"]";
		}
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}
		
	}

	/* 涓嬮潰�?�炵幇浜咰ENTER_CROP鐨勫姛鑳�? 鐨凪atrix锛屽湪浼樺寲鐨勮繃绋嬩腑锛屽凡缁忎笉鐢ㄤ�? */
	private void getCenterCropMatrix() {
		if (getDrawable() == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		/* 涓嬮潰�?�炵幇浜咰ENTER_CROP鐨勫姛鑳�? */
		float xScale = mOriginalWidth / ((float) mBitmap.getWidth());
		float yScale = mOriginalHeight / ((float) mBitmap.getHeight());
		float scale = xScale > yScale ? xScale : yScale;
		mSmoothMatrix.reset();
		mSmoothMatrix.setScale(scale, scale);
		mSmoothMatrix.postTranslate(-(scale * mBitmap.getWidth() / 2 - mOriginalWidth / 2), -(scale * mBitmap.getHeight() / 2 - mOriginalHeight / 2));
	}

	private void getBmpMatrix() {
		if (getDrawable() == null) {
			return;
		}
		if (mTransfrom == null) {
			return;
		}
		if (mBitmap == null || mBitmap.isRecycled()) {
			mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		}
		/* 涓嬮潰�?�炵幇浜咰ENTER_CROP鐨勫姛鑳�? */
		mSmoothMatrix.setScale(mTransfrom.scale, mTransfrom.scale);
		mSmoothMatrix.postTranslate(-(mTransfrom.scale * mBitmap.getWidth() / 2 - mTransfrom.rect.width / 2),
				-(mTransfrom.scale * mBitmap.getHeight() / 2 - mTransfrom.rect.height / 2));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return; // couldn't resolve the URI
		}

		if (mState == STATE_TRANSFORM_IN || mState == STATE_TRANSFORM_OUT) {
			if (mTransformStart) {
				initTransform();
			}
			if (mTransfrom == null) {
				super.onDraw(canvas);
				return;
			}

			if (mTransformStart) {
				if (mState == STATE_TRANSFORM_IN) {
					mTransfrom.initStartIn();
				} else {
					mTransfrom.initStartOut();
				}
			}

			if(mTransformStart){
				Log.d("Dean", "mTransfrom.startScale:"+mTransfrom.startScale);
				Log.d("Dean", "mTransfrom.startScale:"+mTransfrom.endScale);
				Log.d("Dean", "mTransfrom.scale:"+mTransfrom.scale);
				Log.d("Dean", "mTransfrom.startRect:"+mTransfrom.startRect.toString());
				Log.d("Dean", "mTransfrom.endRect:"+mTransfrom.endRect.toString());
				Log.d("Dean", "mTransfrom.rect:"+mTransfrom.rect.toString());
			}
			
			mPaint.setAlpha(mBgAlpha);
			canvas.drawPaint(mPaint);
			
			int saveCount = canvas.getSaveCount();
			canvas.save();
			// 鍏堝緱鍒板浘鐗囧湪姝ゅ埢鐨勫浘鍍廙atrix鐭╅�?
			getBmpMatrix();
			canvas.translate(mTransfrom.rect.left, mTransfrom.rect.top);
			canvas.clipRect(0, 0, mTransfrom.rect.width, mTransfrom.rect.height);
			canvas.concat(mSmoothMatrix);
			getDrawable().draw(canvas);
			canvas.restoreToCount(saveCount);
			if (mTransformStart) {
				mTransformStart=false;
				startTransform(mState);
			} 
		} else {
			//褰揟ransform In鍙樺寲�?�屾垚鍚庯紝鎶婅儗鏅敼涓洪粦鑹诧紝浣垮緱Activity涓嶉�忔�?
			mPaint.setAlpha(255);
			canvas.drawPaint(mPaint);
			super.onDraw(canvas);
		}
	}

	@SuppressLint("NewApi") private void startTransform(final int state) {
		if (mTransfrom == null) {
			return;
		}
		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(300);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		if (state == STATE_TRANSFORM_IN) {
			PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.startScale, mTransfrom.endScale);
			PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", mTransfrom.startRect.left, mTransfrom.endRect.left);
			PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", mTransfrom.startRect.top, mTransfrom.endRect.top);
			PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", mTransfrom.startRect.width, mTransfrom.endRect.width);
			PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", mTransfrom.startRect.height, mTransfrom.endRect.height);
			PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 0, 255);
			valueAnimator.setValues(scaleHolder, leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
		} else {
			PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.endScale, mTransfrom.startScale);
			PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", mTransfrom.endRect.left, mTransfrom.startRect.left);
			PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", mTransfrom.endRect.top, mTransfrom.startRect.top);
			PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", mTransfrom.endRect.width, mTransfrom.startRect.width);
			PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", mTransfrom.endRect.height, mTransfrom.startRect.height);
			PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0);
			valueAnimator.setValues(scaleHolder, leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
		}

		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public synchronized void onAnimationUpdate(ValueAnimator animation) {
				mTransfrom.scale = (Float) animation.getAnimatedValue("scale");
				mTransfrom.rect.left = (Float) animation.getAnimatedValue("left");
				mTransfrom.rect.top = (Float) animation.getAnimatedValue("top");
				mTransfrom.rect.width = (Float) animation.getAnimatedValue("width");
				mTransfrom.rect.height = (Float) animation.getAnimatedValue("height");
				mBgAlpha = (Integer) animation.getAnimatedValue("alpha");
				invalidate();
				((Activity)getContext()).getWindow().getDecorView().invalidate();
			}
		});
		valueAnimator.addListener(new ValueAnimator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				/*
				 * 濡傛灉鏄繘鍏ョ殑璇濓紝褰撶劧鏄笇鏈涙渶鍚庡仠鐣欏湪center_crop鐨勫尯鍩熴�備絾鏄鏋滄槸out鐨勮瘽锛屽氨涓嶅簲璇ユ槸center_crop鐨勪綅缃簡
				 * 锛� 鑰屽簲璇ユ槸鏈�鍚庡彉鍖栫殑浣嶇疆锛屽洜涓哄綋out鐨勬椂鍊欑粨鏉熸椂锛屼笉鍥炲瑙嗗浘鏄疦ormal锛岃涓嶇劧浼氭湁涓�涓獊鐒堕棯鍔ㄥ洖鍘荤殑bug
				 */
				// TODO 杩欎釜鍙互鏍规嵁�?�為檯闇�姹傛潵淇�?
				if (state == STATE_TRANSFORM_IN) {
					mState = STATE_NORMAL;
				}
				if (mTransformListener != null) {
					mTransformListener.onTransformComplete(state);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		valueAnimator.start();
	}

	public void setOnTransformListener(TransformListener listener) {
		mTransformListener = listener;
	}

	private TransformListener mTransformListener;

	public static interface TransformListener {
		/**
		 * 
		 * @param mode
		 *            STATE_TRANSFORM_IN 1 ,STATE_TRANSFORM_OUT 2
		 */
		void onTransformComplete(int mode);// mode 1
	}

}
