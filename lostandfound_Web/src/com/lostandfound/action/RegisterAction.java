package com.lostandfound.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.lostandfound.service.RegisterService;
import com.lostandfound.utils.JsonHelper;
import com.lostandfound.entity.User;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class RegisterAction extends ActionSupport {
	private RegisterService service;
	private User user;
	private Gson gson = new Gson();
	private Map<String, String> info = new HashMap<String, String>(); // 返回的信息

	public String register() {
		HttpServletResponse response = ServletActionContext.getResponse();// 获得响应response
		String userStr = JsonHelper.getJsonFromBody(ServletActionContext
				.getRequest());
		// userStr =
		// "{\"gender\":0,\"addr\":\"刚刚\",\"avatar\":\"\",\"password\":\"123456\",\"name\":\"小隐\",\"tel\":\"12345678911\"}";
		try {
			user = gson.fromJson(userStr, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setAvatar("http://mec.oss-cn-hangzhou.aliyuncs.com/default_avatar.jpg");
		service = new RegisterService(user); // 获得service
		int status = service.register(); // 执行注册并返回状态码
		response.setStatus(status);
		info.put("msg", status == 200 ? "success" : "error");
		return SUCCESS;
	}

	public Map<String, String> getInfo() {
		return this.info;
	}
}
