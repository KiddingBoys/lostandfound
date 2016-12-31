package com.lostandfound.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lostandfound.entity.User;
import com.lostandfound.utils.DB;

public class LoginService{
	private User user; //��¼��Ϣ
	
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
			if (rs.next()) { // �в�ѯ��¼
				if (rs.getString(2).equals(password)) {
					user = new User(rs.getString(1), password, rs.getInt(3), rs.getString(4), 
							rs.getString(5));
					user.setAvatar(rs.getString(6));
					return 1; // ��¼�ɹ�
				} else {
					return 2; // �������
				} 
			}else {
				return 3;     // �û��������� 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��¼ʱ���������ݿ��ѯ����");
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
