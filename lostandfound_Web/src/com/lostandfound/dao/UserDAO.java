package com.lostandfound.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lostandfound.entity.User;
import com.lostandfound.utils.DB;

public class UserDAO {
	private User user;
	private Connection conn = DB.createConn(); // 获取数据库连接
	
	public UserDAO() {
	}
	
	public UserDAO(User user) {
		this.user = user;
	}
	// 修改资料
	public boolean modifyData() {
		String sql = "update _user set gender=?, tel=?, addr=?, avatar=? where name=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1, user.getGender());
			pstmt.setString(2, user.getTel());
			pstmt.setString(3, user.getAddr());
			pstmt.setString(4, user.getAvatar());
			pstmt.setString(5, user.getName());
			pstmt.executeUpdate();  // 数据库更新
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//修改密码
	public boolean modifyPassword() {
		String sql = "update _user set password=? where name=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getName());
			pstmt.executeUpdate();  // 数据库更新
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean save() {
		String sql = "insert into _user values (?,?,?,?,?,?)";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getGender());
			pstmt.setString(4, user.getTel());
			pstmt.setString(5, user.getAddr());
			pstmt.setString(6, user.getAvatar());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
