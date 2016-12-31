package com.kidding.lostandfound.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kidding.lostandfound.activitys.LoginActivity;
import com.kidding.lostandfound.presenter.LoginPresenter;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午7:06:19 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class LoginModel implements ILoginModel{

	private Context context;
	private LoginPresenter loginPresenter;
	
	public LoginModel(Context context,LoginPresenter loginPresenter){
		this.context = context;
		this.loginPresenter = loginPresenter;
	}
	
	public void pushLoginData(String username,String password){
		final long start = System.currentTimeMillis();
        MyApplication.reQueue = Volley.newRequestQueue(context);
        
       
        String params = "{name:"+username+",password:"+password+"}";
        
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST, UrlUtils.loginUrl, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                    	
                        User user = null;
                        Gson gson = new Gson();
                        try {
                        	int code = response.getInt("code");
                        	if(code==201){
                        		ToastUtil.toastshow(context, "登陆成功");
                        		user = gson.fromJson(
                        				response.getJSONObject("result").toString(), User.class);
                        	}else if(code==202){
                        		ToastUtil.toastshow(context, "密码错误！");
                        	}else if(code==203){
                        		ToastUtil.toastshow(context, "用户名不存在！");
                        	}
                            
                        } catch (JsonSyntaxException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        loginPresenter.showResult(user);
                        

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
