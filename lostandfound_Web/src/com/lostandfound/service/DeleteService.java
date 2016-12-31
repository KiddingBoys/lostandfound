package com.lostandfound.service;

import com.lostandfound.dao.PostDAO;

public class DeleteService {
	private int id; // ´ýÉ¾³ýµÄÌû×Ó
	private PostDAO postDAO;
	public DeleteService(int id) {
		this.id = id;
	}
	public boolean delete() {
		postDAO = new PostDAO(id);
		return postDAO.delete() ? true : false;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
