package com.lostandfound.service;

import com.lostandfound.dao.UserDAO;
import com.lostandfound.entity.User;

public class RegisterService {
	private User user;
	public static final int SUCCESS = 200;
	public static final int FAILURE = 202;
	
	public RegisterService() {
	}
	
	public RegisterService(User user) {
		this.user = user;
	}
	
	public int register() {
		UserDAO userDAO = new UserDAO(user);
		if (userDAO.save()) { // ×¢²á³É¹¦
			return SUCCESS;
		}
		return FAILURE; // ×¢²áÊ§°Ü
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
