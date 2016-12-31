package com.lostandfound.action;

import com.lostandfound.service.DeleteService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DeleteAction extends ActionSupport{
	private int id; // ´ýÉ¾³ýµÄÌû×Ó
	
	private int code;
	
	public String delete() {
		DeleteService service = new DeleteService(id);
		code = service.delete() ? 1 : 0;
		return SUCCESS;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCode() {
		return code;
	}

}
