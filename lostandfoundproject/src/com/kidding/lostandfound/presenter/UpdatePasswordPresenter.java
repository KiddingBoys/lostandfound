package com.kidding.lostandfound.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kidding.lostandfound.request.User;
import com.kidding.lostandfound.utils.MyApplication;
import com.kidding.lostandfound.utils.SPHelper;
import com.kidding.lostandfound.utils.ToastUtil;
import com.kidding.lostandfound.utils.UrlUtils;
import com.kidding.lostandfound.view.IUpdatePasswordView;

/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-6 ����8:15:46 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class UpdatePasswordPresenter {

	private IUpdatePasswordView iUpdatePasswordView;
	private Context context;
	public UpdatePasswordPresenter(Context context,IUpdatePasswordView iUpdatePasswordView){
		this.iUpdatePasswordView = iUpdatePasswordView;
		this.context = context;
	}
	
	public void pushNewPwd(){
		//�ж��û�����
		String userName = SPHelper.instance(context).getString("userName");
		String userPwd = SPHelper.instance(context).getString("userPwd");
		
		if(!iUpdatePasswordView.getOriginalPwd().equals(userPwd)){
			ToastUtil.toastshow(context, "ԭʼ�����������");
			return;
		}
		if (!iUpdatePasswordView.getNewPwd().matches("(\\p{Alnum})+")) {
			Toast.makeText(context, "�������������������ֺ���ĸ�ַ�֮��������ַ���",
					Toast.LENGTH_SHORT).show();
			return ;
		} else if (!iUpdatePasswordView.getNewPwd().matches("(\\p{Alnum}){6,}")) {
			Toast.makeText(context, "�������������6λ���ϣ�",
					Toast.LENGTH_SHORT).show();
			return ;
		}else if(!iUpdatePasswordView.getNewPwd().equals(iUpdatePasswordView.getAgainNewPwd())){
			Toast.makeText(context, "���������������Ҫһ�£�",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		iUpdatePasswordView.startProgress();
		
        MyApplication.reQueue = Volley.newRequestQueue(context);             
        String params = "{name:"+ userName
        		+",password:"+iUpdatePasswordView.getNewPwd()+"}";
        
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST, UrlUtils.updatePwdUrl, jsonObject,
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
                    	   Toast.makeText(context, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
                    	   //����
                  	
                   		SPHelper.instance(context).setString("userPsw", iUpdatePasswordView.getNewPwd());
                    	   
                       }else if(result==0){
                    	   Toast.makeText(context, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
                       }
	                   iUpdatePasswordView.stopProgress(result);                    
                 
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
