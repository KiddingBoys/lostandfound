package com.kidding.lostandfound.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhy_slidingmenu.R;
import com.kidding.lostandfound.presenter.PublishLFMessagePresenter;
import com.kidding.lostandfound.utils.DateTimeUtils;
import com.kidding.lostandfound.utils.ImageHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.view.ILFMessageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-7 ����2:36:15 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class PublishLFMessageActivity extends BaseActivity 
					implements OnClickListener,ILFMessageView{

	@ViewInject(R.id.tv_publish_lfmessage_time)
	private TextView timeTv;
	@ViewInject(R.id.tv_publish_lfmessage_updatetime)
	private TextView updateTimeTv;
	@ViewInject(R.id.tv_publish_lfmessage_category)
	private TextView categoryTv;
	
	@ViewInject(R.id.et_publish_lfmessage_title)
	private EditText titleEt;
	@ViewInject(R.id.et_publish_lfmessage_place)
	private EditText placeEt;
	@ViewInject(R.id.et_publish_lfmessage_content)
	private EditText contentEt;
	
	@ViewInject(R.id.iv_publish_lfmessage_image)
	private ImageView infoImgIv;
	
	@ViewInject(R.id.layout_publish_lfmessage_category)
	private RelativeLayout categoryRl;
	@ViewInject(R.id.layout_publish_lfmessage_image)
	private RelativeLayout imageRl;
	
	@ViewInject(R.id.btn_true)
	private Button trueBtn;
	@ViewInject(R.id.tv_add)
	private TextView topTitleTv;
	
	
	PopupWindow morePop;
	private Button layout_wallet;
	private Button layout_card;
	private Button layout_key;
	private Button layout_electric;
	private Button layout_ornaments;
	private Button layout_clothes;
	private Button layout_doc;
	private Button layout_book;
	private Button layout_pet;
	private Button layout_car;
	private Button layout_other;
	
	private Intent intent;
	private String type ="lost";//Ĭ��Ϊlost
	private ImageHelper imageHelper;
	private String imgPath ="";
	private ProgressDialog pd;
	private PublishLFMessagePresenter pLfMessagePresenter;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_publish_lfmessage);
		ViewUtils.inject(this);
		init();
	}
	private void init(){
		intent = getIntent();
		type = intent.getStringExtra("type");
		timeTv.setText(DateTimeUtils.getDate());
		
		if("lost".equals(type)){
			topTitleTv.setText("����Ѱ��");
		}else{
			topTitleTv.setText("��������");
		}
		trueBtn.setOnClickListener(this);
		categoryRl.setOnClickListener(this);
		imageRl.setOnClickListener(this);
		updateTimeTv.setOnClickListener(this);
	    
		pLfMessagePresenter = new PublishLFMessagePresenter(this, this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == layout_wallet||v ==layout_card||v == layout_key||
				v ==layout_electric||v == layout_ornaments||v ==layout_clothes||
				v ==layout_doc||v == layout_book||v ==layout_pet||
				v ==layout_car||v == layout_other) {
			changeTextView(v);
			morePop.dismiss();
		}
		
		switch (v.getId()) {
		case R.id.layout_publish_lfmessage_category://ѡ����Ʒ����
			showListPop();
			break;
		case R.id.layout_publish_lfmessage_image://ѡ��ͼƬ
			new ImageHelper().openPhoto(this);
			break;
		case R.id.btn_true://�������
			
			pLfMessagePresenter.uploadPhoto(imgPath);
			break;
		case R.id.tv_publish_lfmessage_updatetime://����ʱ��
			timeTv.setText(DateTimeUtils.getDate());
			break;
		default:
			break;
		}
	}
	
	
	private void changeTextView(View v) {
		if (v == layout_wallet) {
			categoryTv.setTag("Ǯ��");
			categoryTv.setText("Ǯ��");
		} else if(v == layout_card){
			categoryTv.setTag("֤��");
			categoryTv.setText("֤��");
		}else if (v == layout_key) {
			categoryTv.setTag("Կ��");
			categoryTv.setText("Կ��");
		}else if(v ==layout_electric){
			categoryTv.setTag("����");
			categoryTv.setText("����");
		}else if (v == layout_ornaments) {
			categoryTv.setTag("��Ʒ");
			categoryTv.setText("��Ʒ");
		}else if(v ==layout_clothes){
			categoryTv.setTag("�·�");
			categoryTv.setText("�·�");
		}else if (v == layout_doc) {
			categoryTv.setTag("�ļ�");
			categoryTv.setText("�ļ�");
		}else if (v == layout_book) {
			categoryTv.setTag("�鼮");
			categoryTv.setText("�鼮");
		}else if (v == layout_pet) {
			categoryTv.setTag("����");
			categoryTv.setText("����");
		}else if(v ==layout_car){
			categoryTv.setTag("����");
			categoryTv.setText("����");
		}else if (v == layout_other) {
			categoryTv.setTag("����");
			categoryTv.setText("����");
		}
		
		
	}

	@SuppressWarnings("deprecation")
	private void showListPop() {
		View view = LayoutInflater.from(this).inflate(R.layout.item_list_category, null);
		// ע��
		layout_wallet = (Button) view.findViewById(R.id.layout_wallet);
		layout_card = (Button) view.findViewById(R.id.layout_card);
		layout_key = (Button) view.findViewById(R.id.layout_key);
		layout_electric = (Button) view.findViewById(R.id.layout_electric);
		layout_ornaments = (Button) view.findViewById(R.id.layout_ornaments);
		layout_clothes = (Button) view.findViewById(R.id.layout_clothes);
		layout_doc = (Button) view.findViewById(R.id.layout_doc);
		layout_book = (Button) view.findViewById(R.id.layout_book);
		layout_pet = (Button) view.findViewById(R.id.layout_pet);
		layout_car = (Button) view.findViewById(R.id.layout_car);
		layout_other = (Button) view.findViewById(R.id.layout_other);
		
		layout_wallet.setOnClickListener(this);
		layout_card.setOnClickListener(this);
		layout_key.setOnClickListener(this);
		layout_electric.setOnClickListener(this);
		layout_ornaments.setOnClickListener(this);
		layout_clothes.setOnClickListener(this);
		layout_doc.setOnClickListener(this);
		layout_book.setOnClickListener(this);
		layout_pet.setOnClickListener(this);
		layout_car.setOnClickListener(this);
		layout_other.setOnClickListener(this);
		
		morePop = new PopupWindow(view, mScreenWidth, 600);

		morePop.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					morePop.dismiss();
					return true;
				}
				return false;
			}
		});

		morePop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		morePop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		morePop.setTouchable(true);
		morePop.setFocusable(true);
		morePop.setOutsideTouchable(true);
		morePop.setBackgroundDrawable(new BitmapDrawable());
		// ����Ч�� �Ӷ�������
		morePop.setAnimationStyle(R.style.MenuPop);
		morePop.showAsDropDown(categoryRl, 0, -dip2px(this, 2.0F));
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		imageHelper = new ImageHelper(this, infoImgIv, requestCode, resultCode, data);
	    String imgPath = imageHelper.getImgPath();
//	    ToastUtil.toastshow(this, "Log:::"+imgPath);
	    if (!"".equals(imgPath)) {
	      this.imgPath = imgPath;
	    }
//	    ToastUtil.toastshow(this, "Log---"+this.imgPath);
	}
	@Override
	public String getMessageTime() {
		// TODO Auto-generated method stub
		return timeTv.getText().toString().trim();
	}
	@Override
	public String getMessageTitle() {
		// TODO Auto-generated method stub
		return titleEt.getText().toString().trim();
	}
	@Override
	public String getMessagePlace() {
		// TODO Auto-generated method stub
		return placeEt.getText().toString().trim();
	}
	@Override
	public String getMessageContent() {
		// TODO Auto-generated method stub
		return contentEt.getText().toString().trim();
	}
	@Override
	public String getMessageCategory() {
		// TODO Auto-generated method stub
		return categoryTv.getText().toString().trim();
	}
	@Override
	public String getMessageType() {
		// TODO Auto-generated method stub
		return type;
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
	public void stopProgress(int code) {
		// TODO Auto-generated method stub
		pd.dismiss();
		if(code==1){
			finish();
		}
	}
		
	
	
}
