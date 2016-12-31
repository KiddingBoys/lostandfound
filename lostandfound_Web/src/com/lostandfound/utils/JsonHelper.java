package com.lostandfound.utils;

import java.io.*;

import javax.servlet.http.HttpServletRequest;

public class JsonHelper {
	public static String getJsonFromBody(HttpServletRequest request) {
		try {
		InputStream in = request.getInputStream();
		byte[] buffer = new byte[100*1024];
		in.read(buffer);
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		String line = "";
//		StringBuffer sb = new StringBuffer();
//		while ((line = br.readLine()) != null) {
//			sb.append(line);
//		}
		return new String(buffer,"utf-8").trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
