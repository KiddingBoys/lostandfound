package com.lostandfound.service;

import com.lostandfound.dao.PostDAO;

/**
 * �������ӵ�ɾ������
 * @author �ⳬ
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
	 * ��������Ϊ�����
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
