package edu.boun.ssw.client;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ColdAnswerList {
	private ArrayList<ColdAnswer> coldAnswerList;

	public ArrayList<ColdAnswer> getColdAnswerList() {
		return coldAnswerList;
	}

	public void setColdAnswerList(ArrayList<ColdAnswer> coldAnswerList) {
		this.coldAnswerList = coldAnswerList;
	}
	
}
