package com.kidding.lostandfound.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;



import java.io.FileNotFoundException;

import com.aliyun.mbaas.oss.model.OSSException;

public abstract class ImageUploder extends AsyncTask<Object, Void, String>{

	protected Context context;
	protected String errMsg;
	
	public ImageUploder(Context context){
		this.context = context;
	}
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub
		String path = (String) params[0];
		OSSUtils ossUtil = new OSSUtils(context);
		String result = "";
		try {
			result = ossUtil.uploadFile(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			errMsg = "±§Ç¸Î´ÕÒµ½ÕÕÆ¬";
			cancel(true);
			e.printStackTrace();
		} catch (OSSException e) {
			// TODO Auto-generated catch block
			errMsg = "ÉÏ´«³ö´í";
			cancel(true);
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		if(!TextUtils.isEmpty(errMsg));
		Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
	}
}
