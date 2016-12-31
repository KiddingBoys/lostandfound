package com.lostandfound.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lostandfound.entity.User;
import com.lostandfound.utils.DB;

public class LoginService{
	private User user; //登录信息
	
	public LoginService() {
	}
	public LoginService(User user) {
		this.user = user;
	}
	public int login() {
		String name = user.getName();
		String password = user.getPassword();
		Connection conn = DB.createConn();
		String sql = "select * from _user where name=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // 有查询记录
				if (rs.getString(2).equals(password)) {
					user = new User(rs.getString(1), password, rs.getInt(3), rs.getString(4), 
							rs.getString(5));
					user.setAvatar(rs.getString(6));
					return 1; // 登录成功
				} else {
					return 2; // 密码错误
				} 
			}else {
				return 3;     // 用户名不存在 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("登录时：发生数据库查询错误");
			return 4;    
		}
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
