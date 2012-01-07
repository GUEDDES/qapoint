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

@Path("/qapoint/addwarmanswer/{question}/{answer}/{username}")
public class AddWarmAnswerService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public WarmAnswer getJSON(@PathParam("question") String question, @PathParam("answer") String answer, @PathParam("username") String username) {
		
		WarmAnswer warmAnswer = new WarmAnswer();
		warmAnswer.setAnswer(answer);
		warmAnswer.setUsername(username);
		
		// add answer to TDB
		dataAccess.dbAccess.addWarmAnswer(warmAnswer , question);

		
		return warmAnswer;
	}

}

