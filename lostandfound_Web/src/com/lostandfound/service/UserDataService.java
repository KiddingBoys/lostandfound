package com.lostandfound.service;

import com.lostandfound.dao.UserDAO;
import com.lostandfound.entity.User;

public class UserDataService {
	private User user;
	private UserDAO userDao;
	
	public UserDataService() {
	}
	
	public UserDataService(User user) {
		this.user = user;
	}
	/**
	 * �޸��û�����
	 * @return �޸ĳɹ� ����true
	 */
	public boolean modifyData() {
		userDao = new UserDAO(user);
		if (userDao.modifyData()) {
			return true;
		}
		return false;
	}
	/**
	 * �޸��û�����
	 * @return �޸ĳɹ� ����true
	 */
	public boolean modifyPassword() {
		userDao = new UserDAO(user);
		if (userDao.modifyPassword()) {
			return true;
		}
		return false;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
}
