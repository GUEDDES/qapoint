package de.vogella.jersey.jaxb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.boun.ssw.client.ColdAnswer;
import edu.boun.ssw.client.WarmAnswer;
import edu.boun.ssw.client.WarmAnswerList;
import edu.boun.ssw.tdb.dataAccess;
import uni.boun.SentenceParser;

@Path("/qapoint/warmanswers/{username}/{question}")
public class WarmAnswerService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public WarmAnswerList getJSON(@PathParam("question") String question, @PathParam("username") String username) {
		
		// get answers from TDB
		ArrayList<WarmAnswer> warmAnswerList = dataAccess.dbAccess.getWarmAnswers(question);
		
		//FIXME
		WarmAnswer warmAnswer = new WarmAnswer();
		warmAnswer.setUsername("");
		warmAnswer.setAnswer("");
		warmAnswerList.add(warmAnswer);
		warmAnswerList.add(warmAnswer);
		//FIXME
		
		WarmAnswerList warmAnswers = new WarmAnswerList();
		warmAnswers.setWarmAnswerList(warmAnswerList);
		
		return warmAnswers;
	}

}

