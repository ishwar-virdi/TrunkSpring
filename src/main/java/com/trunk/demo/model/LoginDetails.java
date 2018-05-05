package com.trunk.demo.model;

public class LoginDetails {

	private String token;
	private String username;
	private String password;
	
	public LoginDetails(String token, String username, String password) {
		super();
		this.token = token;
		this.username = username;
		this.password = password;
	}

	public LoginDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
