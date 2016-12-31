package com.lostandfound.action;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.lostandfound.entity.User;
import com.lostandfound.global.Info;
import com.lostandfound.service.LoginService;
import com.lostandfound.utils.JsonHelper;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport{
	private LoginService service;
	private Gson gson = new Gson();
	private User user;  
	private Info info; // 要返回的数据
	public String login() {
		String loginDataStr = JsonHelper.getJsonFromBody(ServletActionContext.getRequest());
//		loginDataStr = "{\"name\":\"张光超\", \"password\":\"123456\"}";
		User loginUser = gson.fromJson(loginDataStr, User.class);
		service = new LoginService(loginUser);
		info = new Info();
		switch (service.login()) {
		case 1:
			info.setCode(201); // 登录成功
			setUser(service.getUser());
			info.setResult(user);
			break;
		case 2:
			info.setCode(202); // 密码错误
			break;
		case 3:
			info.setCode(203); // 用户名不存在 
			break;

		default:
			break;
		}
		return SUCCESS;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
}
