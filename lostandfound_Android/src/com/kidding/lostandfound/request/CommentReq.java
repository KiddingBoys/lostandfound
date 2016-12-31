package com.kidding.lostandfound.request;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-8 下午11:48:18 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class CommentReq {
	private String poster;
	private String content;
	private String time;
	private String _to;
	private int parentId;
	
	
	public CommentReq(String poster, String content, String time, String _to,
			int parentId) {
		super();
		this.poster = poster;
		this.content = content;
		this.time = time;
		this._to = _to;
		this.parentId = parentId;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String get_to() {
		return _to;
	}
	public void set_to(String _to) {
		this._to = _to;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	
}
