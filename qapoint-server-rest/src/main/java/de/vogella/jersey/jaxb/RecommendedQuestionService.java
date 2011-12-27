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
import edu.boun.ssw.client.Question;
import edu.boun.ssw.client.QuestionList;
import edu.boun.ssw.tdb.dataAccess;
import uni.boun.SentenceParser;

@Path("/qapoint/getrecommendedquestions/{username}")
public class RecommendedQuestionService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public QuestionList getJSON(@PathParam("username") String username) {

		// get questions from TDB
		ArrayList<Question> questions =  dataAccess.dbAccess.getQuestionsByUserName(username);

		//FIXME
		Question question = new Question();
		question.setUsername("");
		question.setQuestionText("");
		questions.add(question);
		//FIXME
		
		QuestionList questionList = new QuestionList();
		questionList.setQuestionList(questions);
		
		return questionList;
	}
	
}

