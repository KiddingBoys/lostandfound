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
			.getRequest());// ��ȡ�����ַ���
	private Info info = new Info(); // Ҫ���ص���Ϣ
	private List<Post> postList; // Ҫ���ص������б�
	private Gson gson = new Gson();
	private ListService service;

	// ��ȡ�������б�
	public String subject() {
		// reqStr = "{pageNo: 0,pageCnt: 10,type:lost}";
		ListReqData listReqData = gson.fromJson(reqStr, ListReqData.class);
		service = new ListService(listReqData); // ע�����
		postList = service.subject();
		info.setResult(postList);
		info.setCode(1);
		return SUCCESS;
	}

	// ��ȡ�������б�
	public String comment() {
		// reqStr = "{pageNo: 0,pageCnt: 10,id: 1}";
		ListReqData listReqData = gson.fromJson(reqStr, ListReqData.class);
		service = new ListService(listReqData); // ע�����
		postList = service.comment();
		info.setResult(postList);
		info.setCode(1);
		return SUCCESS;
	}

	public Info getInfo() {
		return this.info;
	}
}
