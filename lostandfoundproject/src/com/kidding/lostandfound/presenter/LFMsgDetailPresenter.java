package com.kidding.lostandfound.presenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.format.DateUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.zhy_slidingmenu.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kidding.lostandfound.request.CommentReq;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.utils.DateTimeUtils;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;
import com.kidding.lostandfound.view.ILFMsgDetailView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-8 下午10:06:08 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class LFMsgDetailPresenter {

	private Context context;
	private ILFMsgDetailView iMsgDetailView;
	private int number = 20;

	private List<LFMessage> msgList = new ArrayList<LFMessage>();
	
	public LFMsgDetailPresenter(Context context,ILFMsgDetailView iMsgDetailView){
		this.context = context;
		this.iMsgDetailView = iMsgDetailView;
	}
	
	/**
	 * 获取评论
	 */
	public void getNewComment(){
		
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		
		JSONObject jsonObject = new JSONObject();

		
		params = "{parentId:"+iMsgDetailView.getMsgId()+",pageCnt:"+number+",pageNo:0}";
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.getCommentUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						Gson gson = new Gson();
						LFMessage lfMessage = null;
						
						msgList.clear();
						try {
							code = response.getInt("code");
							if(code==1){
								//刷新/加载成功
								JSONArray resultArray = response.getJSONArray("result");
								if(resultArray.length()!=0){
									
									for (int i = 0; i < resultArray.length(); i++) {
										lfMessage = gson.fromJson(
												resultArray.getJSONObject(i).toString()
												, LFMessage.class);
										msgList.add(lfMessage);
									}
								}else{//没有（新）数据
									ToastUtil.toastshow(context, "暂时没有评论");
								}
								
								
							}else{
								ToastUtil.toastshow(context, "获取失败");
							}
							iMsgDetailView.stopProgress(msgList);
							
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
	
	/**
	 * 发送评论
	 */
	public void sendComment(final String content,String _toUserName){
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		
		JSONObject jsonObject = new JSONObject();

		final String poster = SPHelper.instance(context).getString("userName");
		final String timeLabel = DateTimeUtils.getDate();
		final String _to = _toUserName;
		final int parentId = iMsgDetailView.getMsgId();
		Gson gson = new Gson();
		CommentReq commentReq = new CommentReq(poster,content,timeLabel,_to,parentId);
		params = gson.toJson(commentReq);
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.sendCommentUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						Gson gson = new Gson();
						LFMessage lfMessage = new LFMessage();
						
						
						try {
							code = response.getInt("code");
							if(code==1){
								lfMessage.setPoster(poster);
								lfMessage.set_to(_to);
								lfMessage.setTime(timeLabel);
								lfMessage.setContent(content);
								lfMessage.setId(parentId);
								msgList.add(0, lfMessage);
//								msgList.add(lfMessage);
								iMsgDetailView.getEditTextView().setText("");
								ToastUtil.toastshow(context, "发送成功");						
							}else{
								ToastUtil.toastshow(context, "获取失败");
							}
							iMsgDetailView.stopProgress(msgList);
							
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
	
	/**
	 * 删除帖子
	 */
	public void deleteLFMsg(){
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		
		JSONObject jsonObject = new JSONObject();

		params = "{id:"+iMsgDetailView.getMsgId()+"}";
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.deleteMsgUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						Gson gson = new Gson();
						LFMessage lfMessage = null;
						
						msgList.clear();
						try {
							code = response.getInt("code");
							if(code==1){							
								ToastUtil.toastshow(context, "删除成功");
								iMsgDetailView.setDelSuccess();
							}else{
								ToastUtil.toastshow(context, "删除失败");
							}
							
							
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
	/**
	 * 设置支持与否
	 * @param supportFlag
	 */
	public void setSupport(final boolean supportFlag){
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		
		JSONObject jsonObject = new JSONObject();
		int support = 1;//支持
		if(!supportFlag){
			support = 0;
		}
		params = "{id:"+iMsgDetailView.getMsgId()+",support:"+support+"}";
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.setSupportUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						Gson gson = new Gson();
						LFMessage lfMessage = null;
						
						msgList.clear();
						try {
							code = response.getInt("code");
							if(code==1){
								if(supportFlag){
									iMsgDetailView.getSupportBtn(supportFlag).setBackgroundResource(R.drawable.feed_praise_select);
									ToastUtil.toastshow(context, "支持成功");
								}else{
									iMsgDetailView.getSupportBtn(supportFlag).setBackgroundResource(R.drawable.feed_praise_nor);
									ToastUtil.toastshow(context, "取消支持");
								}
								
							}else{
								ToastUtil.toastshow(context, "设置失败");
							}
							iMsgDetailView.stopProgress(msgList);
							
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
	
	/**
	 * 设置完成帖子
	 */
	public void completeLFMsg(){
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		
		JSONObject jsonObject = new JSONObject();

		params = "{id:"+iMsgDetailView.getMsgId()+"}";
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.completeMsgUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						Gson gson = new Gson();
						LFMessage lfMessage = null;
						
						msgList.clear();
						try {
							code = response.getInt("code");
							if(code==1){							
								ToastUtil.toastshow(context, "设置成功");
								iMsgDetailView.setCompleteSuccess();
							}else{
								ToastUtil.toastshow(context, "设置失败");
							}
							
							
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
