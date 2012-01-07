package edu.boun.ssw.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String username;
	private String interestsOfUser;

	public User(String username) {
		this.username = username;
		
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInterests() {
		return interestsOfUser;
	}

	public void setInterests(String interests) {
		this.interestsOfUser = interests;
	}
}



