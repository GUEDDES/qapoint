package edu.boun.ssw.client;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserList {
	private ArrayList<Question> userList;

	public ArrayList<Question> getQuestionList() {
		return userList;
	}

	public void setQuestionList(ArrayList<Question> userList) {
		this.userList = userList;
	}
	
}
