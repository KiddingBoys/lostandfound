package com.lostandfound.action;

import com.lostandfound.service.SupportService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SupportAction extends ActionSupport{
	private int id;        // Ҫ����������id
	private int support;   // �ж��ǵ��޻���ȡ������,����1ʱΪ֧��,0ʱΪȡ��֧��
	
	private int code;         // �жϲ����Ƿ�ɹ�
	
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
