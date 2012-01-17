package edu.boun.ssw.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginResponse {
	private int validUser;

	public int getValidUser() {
		return validUser;
	}

	public void setValidUser(int validUser) {
		this.validUser = validUser;
	}
	
}
