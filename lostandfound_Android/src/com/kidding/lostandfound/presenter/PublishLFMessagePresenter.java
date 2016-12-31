package com.kidding.lostandfound.presenter;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.aliyun.mbaas.oss.model.OSSException;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.utils.ImageUploder;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.OSSUtils;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;
import com.kidding.lostandfound.view.ILFMessageView;


/**
 * @author 作者 : KiddingBoy
 * @date 创建时间：2016-5-7 下午6:58:04
 * @version 1.0
 * @parameter
 * @return
 */
public class PublishLFMessagePresenter {

	private Context context;
	private ILFMessageView iLView;
	private boolean isErr;
	private boolean isPublish;

	public PublishLFMessagePresenter(Context context, ILFMessageView iLView) {
		this.context = context;
		this.iLView = iLView;
	}

	// 上传图片,photo为图片路径
	public void uploadPhoto(final String photo) {

		if(iLView.getMessageTitle().equals("")||iLView.getMessagePlace().equals("")
				||iLView.getMessageContent().equals("")){
			ToastUtil.toastshow(context, "需要填写的信息不能为空！"+photo);
			return;
		}
		iLView.startProgress();
		
		if (!"".equals(photo)) {
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
						publishLFMessage(result);
					}
					isPublish = false;

				};

			}.execute(photo);
		} else {
			publishLFMessage("");
		}
	}

	// 上传Message
	public void publishLFMessage(String imgUrl) {
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		final Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject();

		LFMessage lfMessage = new LFMessage(0,SPHelper.instance(context).getString("userName")
				,iLView.getMessageTitle(),iLView.getMessageContent()
				,imgUrl,SPHelper.instance(context).getString("userTel")
				,iLView.getMessageTime(),iLView.getMessageType()
				,iLView.getMessageCategory(),0,"",0,0,iLView.getMessagePlace());
		params = gson.toJson(lfMessage);
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.publishLFMegUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						try {
							code = response.getInt("code");
							if(code==1){
								ToastUtil.toastshow(context, "发布成功");									
							}else{
								ToastUtil.toastshow(context, "发布失败");
							}
							iLView.stopProgress(code);
							
						} catch (JsonSyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						error.getMessage();
					}
				});
		MyApplication.reQueue.add(jsonRequest);

	}

}
