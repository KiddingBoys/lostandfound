package com.kidding.lostandfound.utils;
/** 
 * @author  ���� : KiddingBoy
 * @date ����ʱ�䣺2016-5-5 ����7:24:22 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class UrlUtils {

	public static String urlIp = SPHelper.instance(null).getString("urlIp");
	public  static String lostandfoundUrl = "http://"+urlIp+"/lostandfound";

	 public static String registerUrl = lostandfoundUrl + "/register/";

	 public static String loginUrl = lostandfoundUrl + "/login/";
	 
	 public static String updateInfoUrl = lostandfoundUrl + "/user/modify_Data";
	 
	 public static String updatePwdUrl = lostandfoundUrl + "/user/modify_Password";
	 
	 public static String publishLFMegUrl = lostandfoundUrl + "/post/post_Subject";
	 //��ȡ������
	 public static String getLFMegUrl = lostandfoundUrl + "/post/subject";
	 //��ȡ����
	 public static String getCommentUrl = lostandfoundUrl + "/post/comment";
	 //��������
	 public static String sendCommentUrl = lostandfoundUrl + "/post/post_Comment";
	 //����֧��
	 public static String setSupportUrl = lostandfoundUrl + "/post/support";
	 //ɾ������
	 public static String deleteMsgUrl = lostandfoundUrl + "/post/delete";
	//�������
	 public static String completeMsgUrl = lostandfoundUrl + "/post/found";
	 

	 
}
