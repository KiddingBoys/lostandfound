package com.lostandfound.action;

import com.lostandfound.service.SupportService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SupportAction extends ActionSupport{
	private int id;        // 要操作的帖子id
	private int support;   // 判断是点赞还是取消点赞,等于1时为支持,0时为取消支持
	
	private int code;         // 判断操作是否成功
	
	public String support() {
		SupportService service = new SupportService(id);
		switch (support) {
		case 1:
			code = service.giveSupport() ? 1 : 0;
			break;
		case 0:
			code = service.cancelSupport() ? 1 : 0;
			break;

		default:
			break;
		}
		return SUCCESS;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	public int getCode() {
		return code;
	}
}
