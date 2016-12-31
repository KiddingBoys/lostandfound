package com.lostandfound.entity;

public class ListReqData {
	private int parentId;         // 用于获取相应帖子的评论列表
	private String name;      // 用于获取某个人的发帖
	private int pageNo;      // 要获取的页码
	private int pageCnt;     // 每页要显示的个数
	private String type;     // 判断是寻物还是招领
	private String category;     // 类别
	
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
