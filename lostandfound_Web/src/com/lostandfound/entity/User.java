package com.lostandfound.entity;

public class User {
	private String name;
	private String password;
	private int gender;
	private String tel;
	private String addr;
	private String avatar;
	
	public User() {
	}
	
	public User(String name, String password, int gender, String tel,
			String addr) {
		super();
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.tel = tel;
		this.addr = addr;
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
