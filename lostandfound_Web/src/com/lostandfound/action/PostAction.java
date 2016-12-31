package com.lostandfound.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.lostandfound.entity.Post;
import com.lostandfound.service.PostService;
import com.lostandfound.utils.JsonHelper;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PostAction extends ActionSupport{
	private Post post;  // 用于接收用户数据
	private Gson gson = new Gson();
	private PostService service;
	private Map<String, Integer> info; // 返回给用户的数据
	private String postStr = JsonHelper.getJsonFromBody(ServletActionContext.getRequest());
	public String postSubject() {
		info = new HashMap<String, Integer>();
		post = gson.fromJson(postStr, Post.class);
		service = new PostService(post);
		info.put("code", service.postSubject() ? 1 : 0); // 1表示成功
		return SUCCESS;
	}
	public String postComment() {
//		postStr = "{poster:admin,content:哈哈哈,time:\"2016-3-23 11:25:35\",_to:张光超}";
		info = new HashMap<String, Integer>();
		post = gson.fromJson(postStr, Post.class);
		service = new PostService(post);
		info.put("code", service.postComment() ? 1 : 0); // 1表示成功
		return SUCCESS;
	}
	public Map<String, Integer> getInfo() {
		return this.info;
	}
}
