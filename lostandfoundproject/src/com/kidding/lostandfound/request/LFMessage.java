package com.kidding.lostandfound.request;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-7 下午1:25:28 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class LFMessage {
	private int id;
	private String poster;
	private String title;
	private String content;
	private String image;
	private String tel;
	private String time;
	private String type;
	private String category;
	private int root;
	private String _to;
	private int found;
	private int support;
	private String place;
	
	public LFMessage(){
		
	}
	
	public LFMessage(int id, String poster, String title, String content,
			String image, String tel, String time, String type,
			String category, int root, String _to, int found, int support,String place) {
		super();
		this.id = id;
		this.poster = poster;
		this.title = title;
		this.content = content;
		this.image = image;
		this.tel = tel;
		this.time = time;
		this.type = type;
		this.category = category;
		this.root = root;
		this._to = _to;
		this.found = found;
		this.support = support;
		this.place = place;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getRoot() {
		return root;
	}
	public void setRoot(int root) {
		this.root = root;
	}
	public String get_to() {
		return _to;
	}
	public void set_to(String _to) {
		this._to = _to;
	}
	public int getFound() {
		return found;
	}
	public void setFound(int found) {
		this.found = found;
	}
	public int getSupport() {
		return support;
	}
	public void setSupport(int support) {
		this.support = support;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	
}
