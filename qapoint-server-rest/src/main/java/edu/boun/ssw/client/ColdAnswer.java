package edu.boun.ssw.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ColdAnswer extends Answer {

	private String username;
	private String answer;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
