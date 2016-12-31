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
	private Post post;  // ���ڽ����û�����
	private Gson gson = new Gson();
	private PostService service;
	private Map<String, Integer> info; // ���ظ��û�������
	private String postStr = JsonHelper.getJsonFromBody(ServletActionContext.getRequest());
	public String postSubject() {
		info = new HashMap<String, Integer>();
		post = gson.fromJson(postStr, Post.class);
		service = new PostService(post);
		info.put("code", service.postSubject() ? 1 : 0); // 1��ʾ�ɹ�
		return SUCCESS;
	}
	public String postComment() {
//		postStr = "{poster:admin,content:������,time:\"2016-3-23 11:25:35\",_to:�Źⳬ}";
		info = new HashMap<String, Integer>();
		post = gson.fromJson(postStr, Post.class);
		service = new PostService(post);
		info.put("code", service.postComment() ? 1 : 0); // 1��ʾ�ɹ�
		return SUCCESS;
	}
	public Map<String, Integer> getInfo() {
		return this.info;
	}
}
