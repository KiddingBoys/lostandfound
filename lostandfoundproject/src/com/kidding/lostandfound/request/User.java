package com.kidding.lostandfound.request;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;

/** 
 * @author  作者 : KiddingBoy
 * @date 创建时间：2016-5-5 下午6:50:37 
 * @version 1.0 
 * @parameter   
 * @return 
 */
public class User {
	
	@Id
	@NoAutoIncrement
	private String name;
	@Column(column = "password")
	private String password;
	@Column(column = "gender")
	private int gender;
	@Column(column = "tel")
	private String tel;
	@Column(column = "addr")
	private String addr;
	@Column(column = "avatar")
	private String avatar;
		
		public User() {
		}
		
		public User(String name, String password, int gender, String tel,
				String addr,String avatar) {
			super();
			this.name = name;
			this.password = password;
			this.gender = gender;
			this.tel = tel;
			this.addr = addr;
			this.avatar = avatar;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}


}
