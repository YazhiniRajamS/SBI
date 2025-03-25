package com.example.netconf.model;

public class LoginData {
	private String username;
	private String sessionId;
	
	public LoginData(String username, String sessionId) {
		this.username = username;
		this.sessionId = sessionId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String username) {
		this.sessionId=username;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
