package com.kidding.lostandfound.presenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kidding.lostandfound.request.LFMessage;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;
import com.kidding.lostandfound.view.ILFMsgListView;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-9 下午8:07:36 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class MainFragmentPresenter {
	private Context context;
	private ILFMsgListView iLSthView;
	private int number = 8;
	private int page = 0;
	private List<LFMessage> msgList = new ArrayList<LFMessage>();
	public MainFragmentPresenter(Context context, ILFMsgListView iLSthView) {
		this.context = context;
		this.iLSthView = iLSthView;
	}

	/**
	 * 获取MessageList(all所有帖子)
	 * @param isRefresh
	 */
	public void getLFMessage(final boolean isRefresh) {
		if (isRefresh) {
			page = 0;
		}

		MyApplication.reQueue = Volley.newRequestQueue(context);

		final long start = System.currentTimeMillis();
		String params = "";

		JSONObject jsonObject = new JSONObject();

		params = "{pageNo:" + page + ",pageCnt:" 
						+ number + ",type:all,category:all}";
		try {
			jsonObject = new JSONObject(params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Request.Method.POST, UrlUtils.getLFMegUrl, jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						int code = 0;
						Gson gson = new Gson();
						LFMessage lfMessage = null;
						if (page == 0) {
							msgList.clear();
						}

						try {
							code = response.getInt("code");
							if (code == 1) {
								// 刷新/加载成功
								JSONArray resultArray = response
										.getJSONArray("result");
								if (resultArray.length() != 0) {
									page++;
									for (int i = 0; i < resultArray.length(); i++) {
										lfMessage = gson.fromJson(resultArray
												.getJSONObject(i).toString(),
												LFMessage.class);
										msgList.add(lfMessage);
									}
								} else {// 没有（新）数据
									ToastUtil.toastshow(context, "暂时没有新数据");
								}

							} else {
								ToastUtil.toastshow(context, "获取失败");
							}
							iLSthView.setData(msgList);
							iLSthView.stopProgress();

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