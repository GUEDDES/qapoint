package edu.boun.ssw.client;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WarmAnswerList {
	private ArrayList<WarmAnswer> warmAnswerList;

	public ArrayList<WarmAnswer> getWarmAnswerList() {
		return warmAnswerList;
	}

	public void setWarmAnswerList(ArrayList<WarmAnswer> warmAnswerList) {
		this.warmAnswerList = warmAnswerList;
	}
	
}
