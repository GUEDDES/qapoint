package com.android.qapoint.manager;

public class Session {
	private String username;
	
	private static Session instance = null;
	public static Session getInstance() {
		if(instance==null)
			instance = new Session();
		return instance;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
