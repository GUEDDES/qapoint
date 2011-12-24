package edu.boun.ssw.client;

public class Question {

	private String username;
	private String questionText;

	public Question(String username, String questionText) {
		this.username = username;
		this.questionText = questionText;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
}
