package edu.boun.ssw.client;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Question {

	private String username;
	private String questionText;
	private ArrayList<String> semanticTags= new ArrayList<String>();

	public Question(String username, String questionText) {
		this.username = username;
		this.questionText = questionText;
	}
	
	public Question() {}

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

	public ArrayList<String> getSemanticTags() {
		return semanticTags;
	}

	public void setSemanticTags(ArrayList<String> semanticTags) {
		this.semanticTags = semanticTags;
	}
}
