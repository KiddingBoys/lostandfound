package com.kidding.lostandfound.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kidding.lostandfound.activitys.LoginActivity;
import com.kidding.lostandfound.presenter.RegisterPresenter;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.UrlUtils;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午7:10:13 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class RegisterModel implements IRegisterModel{

	private Context context;
	private RegisterPresenter registerPresenter;
	
	public RegisterModel(Context context,RegisterPresenter registerPresenter){
		this.context = context;
		this.registerPresenter = registerPresenter;
	}
	
	@Override
	public void pushData(User user) {
		// TODO Auto-generated method stub
		final long start = System.currentTimeMillis();
		        MyApplication.reQueue = Volley.newRequestQueue(context);
		        
		        Gson gson = new Gson();
		        String params = gson.toJson(user);
		        try {
					URLEncoder.encode(params,"GBK");
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        JSONObject jsonObject = null;
		        try {
		            jsonObject = new JSONObject(params);
		        } catch (JSONException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
		        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
		                Request.Method.POST, UrlUtils.registerUrl, jsonObject,
		                new Response.Listener<JSONObject>() {

		                    @Override
		                    public void onResponse(JSONObject response) {
		                        // TODO Auto-generated method stub
		                        String result = "";
		                        
		                        
		                        try {
		                            result = response.getString("msg");
		                        } catch (JsonSyntaxException e) {
		                            // TODO Auto-generated catch block
		                            e.printStackTrace();
		                        } catch (JSONException e) {
		                            // TODO Auto-generated catch block
		                            e.printStackTrace();
		                        }
		                        registerPresenter.showResult();
		                        if("success".equals(result)){
		                            Toast.makeText(context, "register success!", Toast.LENGTH_SHORT).show();
		                     
		                            //进行页面的跳转
		                            Intent intent = new Intent(context, LoginActivity.class);
		                            context.startActivity(intent);

		                        }else{
		                            Toast.makeText(context,"用户名已被注册!",Toast.LENGTH_SHORT).show();
		                        }

		                    }
		                }, new Response.ErrorListener() {
		    
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
		        });
		        MyApplication.reQueue.add(jsonRequest);

	}

}
