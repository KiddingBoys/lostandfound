package com.kidding.lostandfound.utils;
/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-12-30 下午7:24:22 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class UrlUtils {

	public static String urlIp = "123.206.208.110:80";
	public  static String lostandfoundUrl = "http://"+urlIp+"/lostandfound";

	 public static String registerUrl = lostandfoundUrl + "/register/";

	 public static String loginUrl = lostandfoundUrl + "/login/";
	 
	 public static String updateInfoUrl = lostandfoundUrl + "/user/modify_Data";
	 
	 public static String updatePwdUrl = lostandfoundUrl + "/user/modify_Password";
	 
	 public static String publishLFMegUrl = lostandfoundUrl + "/post/post_Subject";
	 //获取主题帖
	 public static String getLFMegUrl = lostandfoundUrl + "/post/subject";
	 //获取评论
	 public static String getCommentUrl = lostandfoundUrl + "/post/comment";
	 //发布评论
	 public static String sendCommentUrl = lostandfoundUrl + "/post/post_Comment";
	 //设置支持
	 public static String setSupportUrl = lostandfoundUrl + "/post/support";
	 //删除帖子
	 public static String deleteMsgUrl = lostandfoundUrl + "/post/delete";
	//完成帖子
	 public static String completeMsgUrl = lostandfoundUrl + "/post/found";
	 

	 
}
