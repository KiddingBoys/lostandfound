package com.kidding.lostandfound.activitys;

import java.io.File;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.example.zhy_slidingmenu.R.color;
import com.kidding.lostandfound.adapter.MyLFMsgDetailsAdapter;
import com.kidding.lostandfound.presenter.LFMsgDetailPresenter;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.view.ILFMsgDetailView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2016-5-8 下午3:21:05
 * @version 1.0
 * @parameter
 * @return
 */
public class LFMessageDetailsActivity extends BaseActivity implements
		OnClickListener, ILFMsgDetailView {

	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	@ViewInject(R.id.tv_support_num)
	private TextView supportNumTv;

	@ViewInject(R.id.lv_lfmessage_details_list)
	private ListView detailsLv;
	@ViewInject(R.id.et_lfmsg_details_commentmessage)
	private EditText commentmessageEt;
	@ViewInject(R.id.btn_lfmsg_details_sendcomment)
	private Button sendcommentBtn;
	@ViewInject(R.id.iv_lfmsg_details_infoimg)
	private ImageView infoImgIv;
	@ViewInject(R.id.btn_lfmsg_details_delete)
	private Button deleteMsgBtn;
	@ViewInject(R.id.btn_lfmsg_details_complete)
	private Button completeMsgBtn;
	@ViewInject(R.id.rl_lfmsg_detail_sendcomment)
	private RelativeLayout sendCommentRl;

	@ViewInject(R.id.tv_lfmessage_details_title)
	private TextView titleTv;
	@ViewInject(R.id.tv_lfmessage_details_tel)
	private TextView telTv;
	@ViewInject(R.id.tv_lfmessage_details_poster)
	private TextView posterTv;
	@ViewInject(R.id.tv_lfmessage_details_place)
	private TextView placeTv;
	@ViewInject(R.id.tv_lfmessage_details_time)
	private TextView timeTv;
	@ViewInject(R.id.tv_lfmessage_details_content)
	private TextView contentTv;

	private Intent intent;
	private boolean supportFlag = false;
	private int supportNum;
	private int msgId;
	private String toUserName;
	private String imgUrl;
	private ProgressDialog pd;
	private LFMsgDetailPresenter lMsgDetailPresenter;
	private MyLFMsgDetailsAdapter msgDetailsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lfmessagedetails);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		intent = getIntent();
		toUserName = intent.getStringExtra("poster");
		imgUrl = intent.getStringExtra("image");
		titleTv.setText(intent.getStringExtra("title"));
		posterTv.setText(toUserName);
		placeTv.setText(intent.getStringExtra("place"));
		telTv.setText(intent.getStringExtra("tel"));
		timeTv.setText(intent.getStringExtra("time"));
		contentTv.setText(intent.getStringExtra("content"));
		Glide.with(this).load(imgUrl).placeholder(R.drawable.icon_main_topimg)
				.centerCrop().into(infoImgIv);
		msgId = intent.getIntExtra("id", 0);
		supportNum = intent.getIntExtra("support", 0);
		if(intent.getIntExtra("found", 0)==1){//帖子已经完成
			sendCommentRl.setVisibility(View.GONE);
			deleteMsgBtn.setText("已完成");
			deleteMsgBtn.setBackgroundColor(color.green);
			completeMsgBtn.setVisibility(View.GONE);
		}else{
			deleteMsgBtn.setOnClickListener(this);
			sendcommentBtn.setOnClickListener(this);		
			trueBtn.setOnClickListener(this);
		}
		if(intent.getIntExtra("ismymsg", 0)==0){//是否为侧边栏我的帖子界面，是1否0
			completeMsgBtn.setVisibility(View.GONE);			
		}else{
			completeMsgBtn.setOnClickListener(this);
		}		
		
		supportNumTv.setVisibility(View.VISIBLE);
		supportNumTv.setText("" + supportNum);
		
		telTv.setOnClickListener(this);
		
		if (!toUserName.equals(SPHelper.instance(this).getString("userName"))) {
			deleteMsgBtn.setVisibility(View.GONE);
		}

		topTitleTv.setText("详情");
		trueBtn.setBackgroundResource(R.drawable.feed_praise_nor);

		lMsgDetailPresenter = new LFMsgDetailPresenter(this, this);
		
		infoImgIv.setOnClickListener(this);

		initImageLoader(this);
		startProgress();
		lMsgDetailPresenter.getNewComment();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_lfmsg_details_sendcomment:// 发送评论
			String tempComment = commentmessageEt.getText().toString().trim();
			if (tempComment.equals("")) {
				ToastUtil.toastshow(LFMessageDetailsActivity.this, "评论不能为空");
			} else {
				startProgress();
				lMsgDetailPresenter.sendComment(tempComment, toUserName);
			}
			break;
		case R.id.btn_true:
			if (supportFlag) {
				supportFlag = false;
			} else {
				supportFlag = true;
			}
			lMsgDetailPresenter.setSupport(supportFlag);
			break;
		case R.id.btn_lfmsg_details_delete:// 删除帖子
			lMsgDetailPresenter.deleteLFMsg();
			break;
		case R.id.iv_lfmsg_details_infoimg:
			if (!"".equals(imgUrl)) {
				openZoomImg();
			}
			break;
		case R.id.btn_lfmsg_details_complete:
			lMsgDetailPresenter.completeLFMsg();
			break;
		case R.id.tv_lfmessage_details_tel:
			intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + telTv.getText().toString()));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}

	public static DisplayImageOptions mNormalImageOptions;
	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().toString();
	public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator
			+ "demo" + File.separator + "images" + File.separator;

	// 初始化图片加载的缓存
	private void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		mNormalImageOptions = new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.cacheOnDisc(true).resetViewBeforeLoading(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(mNormalImageOptions)
				.denyCacheImageMultipleSizesInMemory()
				.discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
				.memoryCache(memoryCache)

				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3)
				.build();

		ImageLoader.getInstance().init(config);
	}

	public void openZoomImg() {
		Intent intent = new Intent(LFMessageDetailsActivity.this,
				ZoomImageActivity.class);
		int[] location = new int[2];
		infoImgIv.getLocationOnScreen(location);
		intent.putExtra("locationX", location[0]);
		intent.putExtra("locationY", location[1]);

		intent.putExtra("width", infoImgIv.getWidth());
		intent.putExtra("height", infoImgIv.getHeight());

		intent.putExtra("imgurl", imgUrl);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	@Override
	public void startProgress() {
		// TODO Auto-generated method stub
		pd = new ProgressDialog(this);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("Loading...");
		pd.show();
	}

	@Override
	public void stopProgress(List<LFMessage> msgList) {
		// TODO Auto-generated method stub
		if (msgList.size() != 0) {
			setNewCommentData(msgList);
		}
		pd.dismiss();
	}

	@Override
	public int getMsgId() {
		// TODO Auto-generated method stub
		return msgId;
	}

	@Override
	public void setNewCommentData(List<LFMessage> msgList) {
		// TODO Auto-generated method stub
		if (msgDetailsAdapter == null) {
			msgDetailsAdapter = new MyLFMsgDetailsAdapter(this, msgList, this);
			detailsLv.setAdapter(msgDetailsAdapter);
		} else {
			msgDetailsAdapter.updateMsgList(msgList);
			msgDetailsAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void setToUserName(String toName) {
		// TODO Auto-generated method stub
		this.toUserName = toName;
	}

	@Override
	public EditText getEditTextView() {
		// TODO Auto-generated method stub
		return commentmessageEt;
	}

	@Override
	public Button getSupportBtn(boolean supportFlag) {
		// TODO Auto-generated method stub
		if (supportFlag) {
			this.supportNum++;
		} else {
			this.supportNum--;
		}
		supportNumTv.setText("" + supportNum);

		return trueBtn;
	}

	@Override
	public void setDelSuccess() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void setCompleteSuccess() {
		// TODO Auto-generated method stub
		sendCommentRl.setVisibility(View.GONE);
		deleteMsgBtn.setText("已完成");
		deleteMsgBtn.setClickable(false);
		trueBtn.setClickable(false);
		
		deleteMsgBtn.setBackgroundColor(color.green);
		completeMsgBtn.setVisibility(View.GONE);
	}

}
