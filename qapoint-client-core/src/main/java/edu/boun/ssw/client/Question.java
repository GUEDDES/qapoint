package edu.boun.ssw.client;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Question {

	private String username;
	private String questionText;
	private ArrayList semanticTags= new ArrayList();

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

	public ArrayList getSemanticTags() {
		return semanticTags;
	}

	public void setSemanticTags(ArrayList semanticTags) {
		this.semanticTags = semanticTags;
	}
}
