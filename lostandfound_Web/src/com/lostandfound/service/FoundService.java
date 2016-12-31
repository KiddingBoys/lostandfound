package com.lostandfound.service;

import com.lostandfound.dao.PostDAO;

/**
 * 用于帖子的删除操作
 * @author 光超
 *
 */
public class FoundService {
	private int id;
	private PostDAO postDAO;
	
	public FoundService() {
	}
	public FoundService(int id) {
		this.id = id;
	}
	/**
	 * 将帖子置为已完成
	 * @return
	 */
	public boolean found() {
		postDAO = new PostDAO(id);
		return postDAO.found() ? true : false;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
