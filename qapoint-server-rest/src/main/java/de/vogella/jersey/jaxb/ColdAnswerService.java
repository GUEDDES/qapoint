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
import edu.boun.ssw.client.ColdAnswerList;
import edu.boun.ssw.client.Question;
import edu.boun.ssw.tdb.dataAccess;
import uni.boun.SentenceParser;

@Path("/qapoint/askquestion/{question}/{username}/{location}")
public class ColdAnswerService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public ColdAnswerList getJSON(@PathParam("question") String question, @PathParam("username") String username, @PathParam("location") String location) {

		// store question to TDB
		Question newQuestion = new Question(username, question);
		//newQuestion
		dataAccess.dbAccess.addQuestion(newQuestion);

		ArrayList semanticTags = SentenceParser.sendQuestion(question);
		
		ArrayList<Question> relatedQuestions = dataAccess.dbAccess.getQuestionsWithProperties(semanticTags);
		
		// filter questions according to location
		if(location!=null && !location.equals("")){
			try {
				String[] locationArr = location.split(":");
				Float latitute = Float.valueOf(locationArr[0]);
				Float longitute = Float.valueOf(locationArr[1]);
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// get cold answers to TDB
		ColdAnswerList coldAnswerList = new ColdAnswerList();
		ColdAnswer coldAnswer = new ColdAnswer();
		coldAnswer.setAnswer("Toscana is cool! Worth to try.");
		coldAnswer.setUsername("Tugce");

		
		ArrayList<ColdAnswer> coldAnswers = new ArrayList<ColdAnswer>();
		
		for (int i = 0; i < relatedQuestions.size() ; i++) {
			Question tmpQuestion = relatedQuestions.get(i);
			dataAccess.dbAccess.getWarmAnswers(tmpQuestion);
		}
		
		
		
		coldAnswers.add(coldAnswer);
		
		
		//FIXME
		ColdAnswer coldAnswer2 = new ColdAnswer();
		coldAnswer2.setAnswer("");
		coldAnswer2.setUsername("");
		coldAnswers.add(coldAnswer2);
		//FIXME
		
		coldAnswerList.setColdAnswerList(coldAnswers);
		
		return coldAnswerList;
	}
	
//	@GET
//	@Produces( { MediaType.APPLICATION_XML })
//	public Todo getXML() {
//		Todo todo = new Todo();
//		todo.setSummary("This is my first todo");
//		todo.setDescription("This is my first todo");
//		return todo;
//	}
//	
//	// This can be used to test the integration with the browser
//	@GET
//	@Produces( { MediaType.TEXT_XML })
//	public Todo getHTML() {
//		Todo todo = new Todo();
//		todo.setSummary("This is my first todo");
//		todo.setDescription("This is my first todo");
//		return todo;
//	}

}

