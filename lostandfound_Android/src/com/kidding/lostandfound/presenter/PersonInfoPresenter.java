package com.kidding.lostandfound.presenter;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import com.aliyun.mbaas.oss.model.OSSException;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.utils.ImageUploder;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.OSSUtils;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;
import com.kidding.lostandfound.view.IPersonInfoView;

import android.content.Context;
import android.widget.Toast;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-6 下午5:53:53 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class PersonInfoPresenter {

	private Context context;
	private IPersonInfoView iPersonInfoView;
	private boolean isErr;
	private boolean isPublish;
	
	public PersonInfoPresenter(Context context,IPersonInfoView iPersonInfoView){
		this.context = context;
		this.iPersonInfoView = iPersonInfoView;
	}
	
	public void pushNewData(final String photo,final User user){
		if(photo==""){
			pushChangeData(user);
		}else{
			new ImageUploder(context) {

				@Override
				protected void onPreExecute() {
					if (isErr) {
						cancel(true);
					}
				};

				@Override
				protected String doInBackground(Object... params) {
					String path = (String) params[0];
					if (photo.equals(path)) {
						OSSUtils ossUtil = new OSSUtils(context);
						String result = "";
						try {
							result = ossUtil.uploadTempPic(path);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							errMsg = "未找到照片";
							cancel(true);
							e.printStackTrace();
							isPublish = false;
						} catch (OSSException e) {
							// TODO Auto-generated catch block
							errMsg = "上传图片出错";
							cancel(true);
							e.printStackTrace();
							isPublish = false;
						}

						return result;
					} else {
						return super.doInBackground(params);
					}
				}

				@Override
				protected void onCancelled() {
					super.onCancelled();
					isErr = true;
					isPublish = false;
				};

				@Override
				protected void onPostExecute(String result) {

					if (!result.equals("") && result != null) {
						user.setAvatar(result);
						pushChangeData(user);
					}
					isPublish = false;

				};

			}.execute(photo);
		}
	}
	
	public void pushChangeData(final User user){
		final long start = System.currentTimeMillis();
        MyApplication.reQueue = Volley.newRequestQueue(context);
        
        Gson gson = new Gson();
        String params = gson.toJson(user);
        
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST, UrlUtils.updateInfoUrl, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                    	int result = 0;
                       
                        	try {
								result = response.getInt("code");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        
                       if(result==1){
                    	   ToastUtil.toastshow(context, "修改成功");
                    	   //缓存
                    	SPHelper.instance(context).setString("userName", user.getName());
                   		SPHelper.instance(context).setString("userPwd", user.getPassword());
                   		SPHelper.instance(context).setInt("userGender", user.getGender());
                   		SPHelper.instance(context).setString("userTel", user.getTel());
                   		SPHelper.instance(context).setString("userAddr", user.getAddr());
                   		SPHelper.instance(context).setString("userAvatar", user.getAvatar());
                    	   
                       }else if(result==0){
                    	   ToastUtil.toastshow(context, "修改失败");
                       }
                       iPersonInfoView.stopProgress();                    

                    }
                }, new Response.ErrorListener() {
    
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				int code = error.networkResponse.statusCode;
				Toast.makeText(context, "状态码："+code, Toast.LENGTH_SHORT).show();
			}
			
        });
        MyApplication.reQueue.add(jsonRequest);
		
	}
}
