package com.kidding.lostandfound.activitys;



import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhy_slidingmenu.R;
import com.example.zhy_slidingmenu.SlidingMenu;
import com.kidding.lostandfound.fragments.FoundSthFragment;
import com.kidding.lostandfound.fragments.LostPeopleFragment;
import com.kidding.lostandfound.fragments.LostSthFragment;
import com.kidding.lostandfound.fragments.MainFragment;
import com.kidding.lostandfound.utils.SPHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends BaseActivity implements OnClickListener{
	private SlidingMenu mMenu;

	private long mExitTime;
	 private int index;
	    private int currentTabIndex;
	    private Button[] mTabs;
	    private Fragment[] fragments;
	    private MainFragment mainFragment;
	    private LostPeopleFragment lostPeopleFragment;
	    private LostSthFragment lostSthFragment;
	    private FoundSthFragment foundSthFragment;

//	    private ImageHelper imageHelper;

	    @ViewInject(R.id.btn_main)
	    private Button mainBtn;
	    @ViewInject(R.id.btn_lostpeople)
	    private Button peopleBtn;
	    @ViewInject(R.id.btn_loststh)
	    private Button sthBtn;
	    @ViewInject(R.id.btn_found)
	    private Button foundsthBtn;
	  
	    
	    private TextView userNameTv;
	    private ImageView userAvatarIv;
	    private TextView searchSthTv;
	    private TextView foundTv;
	    private TextView personSetTv;
	    
	    private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		init();
	}
	
	private void init(){
		
		mainFragment = new MainFragment();
		lostPeopleFragment = new LostPeopleFragment();
		lostSthFragment = new LostSthFragment();
		foundSthFragment = new FoundSthFragment();
		
		fragments = new Fragment[]{mainFragment , lostPeopleFragment
				, lostSthFragment,foundSthFragment};
		
		 mTabs = new Button[4];
	     mTabs[0] = mainBtn;
	     mTabs[1] = peopleBtn;
	     mTabs[2] = sthBtn;
	     mTabs[3] = foundsthBtn;
	     //设置初始页,并显示初始页
	     mTabs[0].setSelected(true);
	     registerForContextMenu(mTabs[0]); 	     
         FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
         trx.hide(fragments[currentTabIndex]);
         if (!fragments[index].isAdded()) {  
             trx.add(R.id.fragment_container, fragments[index]);
         }
         trx.show(fragments[index]).commit();

	     
//	     DbUtils db = DbUtils.create(this);
//			db.configAllowTransaction(true);
//			db.configDebug(true);
//		 User userBean = null;
//		try {
//			userBean = db.findById(User.class,
//						MyApplication.userName);
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	     userNameTv = (TextView) mMenu.findViewById(R.id.tv_slidemenu_username);
	     userAvatarIv = (ImageView) mMenu.findViewById(R.id.iv_slidemenu_userhead);
	    
	     searchSthTv = (TextView) mMenu.findViewById(R.id.tv_slidemenu_mysearchsth);
	     foundTv = (TextView) mMenu.findViewById(R.id.tv_slidemenu_myfound);
	     personSetTv = (TextView) mMenu.findViewById(R.id.tv_slidemenu_myinformation);
	     
	     userNameTv.setText(SPHelper.instance(this).getString("userName"));

	     searchSthTv.setOnClickListener(this);
	     foundTv.setOnClickListener(this);
	     personSetTv.setOnClickListener(this);
	     
	   //清除Glide缓存
//			Glide.get(MainActivity.this).clearMemory();
//			File cache = Glide.getPhotoCacheDir(MainActivity.this);
//			FileUtils.delAllFile(cache.getAbsolutePath());
						
//			Glide.with(this)
//			.load(SPHelper.instance(this)
//					.getString("userAvatar"))
//			.placeholder(R.drawable.ic_launcher)
//			.centerCrop()
//			.into(userAvatarIv);
	     Glide.with(this).load(SPHelper.instance(this).getString("userAvatar"))
	     		 .dontAnimate()
	    		 .placeholder(R.drawable.ic_launcher)
	    		 .centerCrop()
	    		 .into(userAvatarIv);
			
	}

	 public void onTabClicked(View view) {
	        switch (view.getId()) {
	            case R.id.btn_main:
	                index = 0;
	                break;
	            case R.id.btn_lostpeople:
	                index = 1;
	                break;
	            case R.id.btn_loststh:
	                index = 2;
	                break;
	            case R.id.btn_found:
	                index = 3;
	                break;
	        }
	        if (currentTabIndex != index) {
	              FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
	            trx.hide(fragments[currentTabIndex]);
	             if (!fragments[index].isAdded()) {  
	                  trx.add(R.id.fragment_container, fragments[index]);
	             }
	             trx.show(fragments[index]).commit();
	         }
	         mTabs[currentTabIndex].setSelected(false);

	         mTabs[index].setSelected(true);
	         currentTabIndex = index;
	    }

	/**
	 * 打开/关闭策划栏
	 * @param view
	 */
	public void toggleMenu(View view)
	{
		mMenu.toggle();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
        case R.id.tv_slidemenu_mysearchsth:
        	intent = new Intent(MainActivity.this,MyLostSthActivity.class);
        	intent.putExtra("type", "lost");
        	intent.putExtra("title", "我的寻物");
        	startActivity(intent);
			break;
        case R.id.tv_slidemenu_myfound:
        	intent = new Intent(MainActivity.this,MyLostSthActivity.class);
        	intent.putExtra("type", "found");
        	intent.putExtra("title", "我的招领");
        	startActivity(intent);
			break;
        case R.id.tv_slidemenu_myinformation:
        	intent = new Intent(MainActivity.this,PersonSetActivity.class);
        	startActivity(intent);
	        break;
		default:
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Object mHelperUtils;
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();

            } else {
                    finish();
                    int currentVersion = android.os.Build.VERSION.SDK_INT; 
                    if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) { 
                        Intent startMain = new Intent(Intent.ACTION_MAIN); 
                        startMain.addCategory(Intent.CATEGORY_HOME); 
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                        startActivity(startMain); 
                        System.exit(0); 
                    } else {// android2.1 
                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
                        am.restartPackage(getPackageName()); 
                    }
            }
            return true;
    }
		return super.onKeyDown(keyCode, event);
	}
}
