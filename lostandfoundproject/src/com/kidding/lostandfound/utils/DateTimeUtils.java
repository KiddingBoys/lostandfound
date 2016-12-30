package com.kidding.lostandfound.utils;

import java.util.Calendar;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-7 下午5:33:02 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class DateTimeUtils {
	
	public static  String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		
		String senonds= String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
//		if(Integer.parseInt(month)<10){
//			month = "0"+month;
//		}
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins +":"+senonds);
		return sbBuffer.toString();
	}
}
