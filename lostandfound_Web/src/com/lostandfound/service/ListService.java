package com.lostandfound.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lostandfound.entity.Post;
import com.lostandfound.entity.ListReqData;
import com.lostandfound.utils.DB;

public class ListService {
	private ListReqData listReqData; // 请求数据
	private Connection conn = DB.createConn(); // 获取数据库连接
	
	public ListService() {
	}
	public ListService(ListReqData listReqData) {
		this.listReqData = listReqData;
	}
	public List<Post> subject() {
		List<Post> list = new ArrayList<Post>();
		String sql = "";
		if (listReqData.getType().equals("all")) { // type = all
			if (listReqData.getCategory().equals("all")) {    // 1. 获取【所有】主题帖
				sql = "select * from _post where found=0 and root=1 order by support desc, id desc limit ?, ?";
			} else {                                          // 2. 获取【所有】分类后的主题帖
				sql = "select * from _post where found=0 and root=1 and category='"
						+ listReqData.getCategory()
						+ "' order by support desc, id desc limit ?, ?";
			}
		} else { // type = lost/found
			if (listReqData.getName() != null
					&& listReqData.getName().length() != 0) { // 3. 获取【我的】【寻物/招领】帖子
				sql = "select * from _post where root=1 and type='"
						+ listReqData.getType() + "' and poster='"
						+ listReqData.getName() + "'  order by id desc limit ?, ?";
			} else {                                          // 4. 获取【寻物/招领 】主题帖
				sql = "select * from _post where found=0 and root=1 and type='"
						+ listReqData.getType()
						+ "' order by support desc, id desc limit ?, ?";
			}
		}
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1,
					listReqData.getPageNo() * listReqData.getPageCnt());
			pstmt.setInt(2, listReqData.getPageCnt());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Post post = new Post(rs.getInt("id"), rs.getString("poster"),
						rs.getString("title"), rs.getString("content"),
						rs.getString("image"), rs.getString("tel"),
						rs.getString("time").substring(0, rs.getString("time").length() - 2), rs.getString("type"),
						rs.getString("category"), rs.getInt("root"),
						rs.getString("_to"), rs.getInt("found"),
						rs.getInt("support"), rs.getString("place")); // 待插入列表的帖子
				list.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Post> comment() {
		List<Post> list = new ArrayList<Post>();
		String sql = "select * from _post where parentId=? and root=0 order by id desc limit ?, ?";
		PreparedStatement pstmt = DB.prepare(conn, sql);
		try {
			pstmt.setInt(1, listReqData.getParentId());
			pstmt.setInt(2, listReqData.getPageNo() * listReqData.getPageCnt());
			pstmt.setInt(3, listReqData.getPageCnt());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Post post = new Post(rs.getInt("id"), rs.getString("poster"),
						rs.getString("title"), rs.getString("content"),
						rs.getString("image"), rs.getString("tel"),
						rs.getString("time").substring(0, rs.getString("time").length() - 2), rs.getString("type"),
						rs.getString("category"), rs.getInt("root"),
						rs.getString("_to"), rs.getInt("found"),
						rs.getInt("support"), rs.getString("place")); // 待插入列表的帖子
				list.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ListReqData getSubjectReqData() {
		return listReqData;
	}

	public void setSubjectReqData(ListReqData subjectReqData) {
		this.listReqData = subjectReqData;
	}
}
