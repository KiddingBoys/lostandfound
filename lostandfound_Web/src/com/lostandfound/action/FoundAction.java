package com.lostandfound.action;

import com.lostandfound.service.FoundService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FoundAction extends ActionSupport{
	private int id;    // 要置为已完成的帖子id
	
	private int code;  // 1: success 2: failure
	
	public String found() {
		FoundService service = new FoundService(id);
		code = service.found() ? 1 : 0;
		return SUCCESS;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
}
