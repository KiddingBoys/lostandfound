package com.lostandfound.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.lostandfound.entity.ListReqData;
import com.lostandfound.entity.Post;
import com.lostandfound.global.Info;
import com.lostandfound.service.ListService;
import com.lostandfound.utils.JsonHelper;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ListAction extends ActionSupport {
	private String reqStr = JsonHelper.getJsonFromBody(ServletActionContext
			.getRequest());// 获取请求字符串
	private Info info = new Info(); // 要返回的信息
	private List<Post> postList; // 要返回的帖子列表
	private Gson gson = new Gson();
	private ListService service;

	// 获取主题帖列表
	public String subject() {
		// reqStr = "{pageNo: 0,pageCnt: 10,type:lost}";
		ListReqData listReqData = gson.fromJson(reqStr, ListReqData.class);
		service = new ListService(listReqData); // 注册服务
		postList = service.subject();
		info.setResult(postList);
		info.setCode(1);
		return SUCCESS;
	}

	// 获取评论帖列表
	public String comment() {
		// reqStr = "{pageNo: 0,pageCnt: 10,id: 1}";
		ListReqData listReqData = gson.fromJson(reqStr, ListReqData.class);
		service = new ListService(listReqData); // 注册服务
		postList = service.comment();
		info.setResult(postList);
		info.setCode(1);
		return SUCCESS;
	}

	public Info getInfo() {
		return this.info;
	}
}
