package com.lostandfound.entity;

public class ListReqData {
	private int parentId;         // ���ڻ�ȡ��Ӧ���ӵ������б�
	private String name;      // ���ڻ�ȡĳ���˵ķ���
	private int pageNo;      // Ҫ��ȡ��ҳ��
	private int pageCnt;     // ÿҳҪ��ʾ�ĸ���
	private String type;     // �ж���Ѱ�ﻹ������
	private String category;     // ���
	
	public ListReqData(int pageNo, int cnt, String type) {
		super();
		this.pageNo = pageNo;
		this.pageCnt = cnt;
		this.category = type;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String type) {
		this.category = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String poster) {
		this.name = poster;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	
}
