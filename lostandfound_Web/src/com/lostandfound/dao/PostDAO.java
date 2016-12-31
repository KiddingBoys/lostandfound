package com.lostandfound.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lostandfound.entity.Post;
import com.lostandfound.utils.DB;

public class PostDAO {
	private Post post;
	private Connection conn = DB.createConn(); // 获取数据库链接
	private int id;

	public PostDAO(int id) {
		this.id = id;
	}

	public PostDAO(Post post) {
		this.post = post;
	}

	// 保存主题帖
	public boolean saveSubject() {
		String sql = "insert into _post values(null, ?, ?, ?, ?, ?, ?, ?, ?, 1, null, 0, 0, ?, null)";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setString(1, post.getPoster());
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getContent());
			pstmt.setString(4, post.getImage());
			pstmt.setString(5, post.getTel());
			pstmt.setString(6, post.getTime());
			pstmt.setString(7, post.getType());
			pstmt.setString(8, post.getCategory());
			pstmt.setString(9, post.getPlace());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 保存评论帖
	public boolean saveComment() {
		String sql = "insert into _post values(null, ?, null, ?, null, null, ?, null, null, 0, ?, null, 0, null,?)";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setString(1, post.getPoster());
			pstmt.setString(2, post.getContent());
			pstmt.setString(3, post.getTime());
			pstmt.setString(4, post.get_to());
			pstmt.setInt(5, post.getParentId());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 通过帖子id将帖子置为已完成
	 * @param id
	 * @return 
	 */
	public boolean found() {
		String sql = "update _post set found=1 where id=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 通过帖子id将帖子删除
	 * @param id
	 * @return 
	 */
	public boolean delete() {
		String sql = "delete from _post where id=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
