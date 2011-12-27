package edu.boun.ssw.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Question {

	private String username;
	private String questionText;
	private String semanticTags;
	private float lat;
	private float lngt;

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

	public String getSemanticTags() {
		return semanticTags;
	}

	public void setSemanticTags(String semanticTags) {
		this.semanticTags = semanticTags;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLngt() {
		return lngt;
	}

	public void setLngt(float lngt) {
		this.lngt = lngt;
	}
	
}
