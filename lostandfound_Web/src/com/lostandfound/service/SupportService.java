package com.lostandfound.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lostandfound.utils.DB;

public class SupportService {
	private int id; // 帖子id
	private Connection conn = DB.createConn(); // 创建数据库连接
	
	public SupportService(int id) {
		this.id = id;
	}
	/**
	 * 给予支持
	 * @return 
	 */
	public boolean giveSupport() {
		String sql = "update _post set support=support+1 where id=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1, getId());
			pstmt.executeUpdate(); 
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 取消支持
	 * @return 
	 */
	public boolean cancelSupport() {
		String sql = "update _post set support=support-1 where id=?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1, getId());
			pstmt.executeUpdate(); 
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
