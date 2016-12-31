package com.lostandfound.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.lostandfound.entity.User;
import com.lostandfound.service.UserDataService;
import com.lostandfound.utils.JsonHelper;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserDataAction extends ActionSupport{
	private UserDataService service;
	private User user; // 接收用户传输过来的修改信息
	private Gson gson = new Gson();
	private Map<String, Integer> info; // 返回给用户的信息
	public String modifyData() {
		String userStr = JsonHelper.getJsonFromBody(ServletActionContext.getRequest());
		// userStr ="{\"gender\":0,\"addr\":\"刚刚\",\"avatar\":\"\",\"password\":\"12345678\",\"name\":\"小隐\",\"tel\":\"12345678911\"}";
		user = gson.fromJson(userStr, User.class);
		service = new UserDataService(user);  // 注册服务
		info = new HashMap<String, Integer>();
		info.put("code", service.modifyData() ? 1 : 0);
		return SUCCESS;
	}
	public String modifyPassword() {
		String userStr = JsonHelper.getJsonFromBody(ServletActionContext.getRequest());
//		userStr ="{\"gender\":0,\"addr\":\"哈哈\",\"avatar\":\"\",\"password\":\"12345678\",\"name\":\"小隐\",\"tel\":\"12345678911\"}";
		user = gson.fromJson(userStr, User.class);
		service = new UserDataService(user);  // 注册服务
		info = new HashMap<String, Integer>();
		info.put("code", service.modifyPassword() ? 1 : 0);
		return SUCCESS;
	}
	public Map<String, Integer> getInfo() {
		return info;
	}
}
