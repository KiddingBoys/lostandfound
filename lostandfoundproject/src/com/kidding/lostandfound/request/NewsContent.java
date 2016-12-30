package com.kidding.lostandfound.request;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-10 上午11:40:25 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class NewsContent {

	private String newsImage;
	private String newsTitle;
	private String newsTime;
	private String newsContent;
	
	
	public NewsContent(String newsImage, String newsTitle, String newsTime,
			String newsContent) {
		super();
		this.newsImage = newsImage;
		this.newsTitle = newsTitle;
		this.newsTime = newsTime;
		this.newsContent = newsContent;
	}
	public String getNewsImage() {
		return newsImage;
	}
	public void setNewsImage(String newsImage) {
		this.newsImage = newsImage;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsTime() {
		return newsTime;
	}
	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	
	
}
