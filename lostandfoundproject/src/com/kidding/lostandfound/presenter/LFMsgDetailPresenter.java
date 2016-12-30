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
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-8 ����10:06:08 
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
	 * ��ȡ����
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
								//ˢ��/���سɹ�
								JSONArray resultArray = response.getJSONArray("result");
								if(resultArray.length()!=0){
									
									for (int i = 0; i < resultArray.length(); i++) {
										lfMessage = gson.fromJson(
												resultArray.getJSONObject(i).toString()
												, LFMessage.class);
										msgList.add(lfMessage);
									}
								}else{//û�У��£�����
									ToastUtil.toastshow(context, "��ʱû������");
								}
								
								
							}else{
								ToastUtil.toastshow(context, "��ȡʧ��");
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
	 * ��������
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
								ToastUtil.toastshow(context, "���ͳɹ�");						
							}else{
								ToastUtil.toastshow(context, "��ȡʧ��");
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
	 * ɾ������
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
								ToastUtil.toastshow(context, "ɾ���ɹ�");
								iMsgDetailView.setDelSuccess();
							}else{
								ToastUtil.toastshow(context, "ɾ��ʧ��");
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
	 * ����֧�����
	 * @param supportFlag
	 */
	public void setSupport(final boolean supportFlag){
		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";
		
		JSONObject jsonObject = new JSONObject();
		int support = 1;//֧��
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
									ToastUtil.toastshow(context, "֧�ֳɹ�");
								}else{
									iMsgDetailView.getSupportBtn(supportFlag).setBackgroundResource(R.drawable.feed_praise_nor);
									ToastUtil.toastshow(context, "ȡ��֧��");
								}
								
							}else{
								ToastUtil.toastshow(context, "����ʧ��");
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
	 * �����������
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
								ToastUtil.toastshow(context, "���óɹ�");
								iMsgDetailView.setCompleteSuccess();
							}else{
								ToastUtil.toastshow(context, "����ʧ��");
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
