package com.lostandfound.service;

import com.lostandfound.dao.PostDAO;
import com.lostandfound.entity.Post;

public class PostService {
	private Post post; 
	private PostDAO pDao; 
	public PostService(Post post) {
		this.post = post;
	}
	/**
	 * 发主题帖
	 * @return
	 */
	public boolean postSubject() {
		pDao = new PostDAO(post);
		return pDao.saveSubject();
	}
	/**
	 * 发评论帖子
	 * @return
	 */
	public boolean postComment() {
		pDao = new PostDAO(post);
		return pDao.saveComment();
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public PostDAO getpDao() {
		return pDao;
	}
	public void setpDao(PostDAO pDao) {
		this.pDao = pDao;
	}
	
}
