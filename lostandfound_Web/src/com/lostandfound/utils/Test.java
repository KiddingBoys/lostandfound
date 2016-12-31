package com.lostandfound.utils;

import com.google.gson.Gson;
import com.lostandfound.entity.User;

public class Test {

	public static void main(String[] args) {
		String str = "{name:xxx,password:123456}";
		Gson gson = new Gson();
		User user = gson.fromJson(str, User.class);
		System.out.println(user.getName());
	}

}
