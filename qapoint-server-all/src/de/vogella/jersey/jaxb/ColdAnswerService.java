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
import edu.boun.ssw.client.WarmAnswer;
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
		
		try {
			String[] locationArr = location.split(":");
			float latitute = Float.parseFloat(locationArr[0]);
			float longitute = Float.parseFloat(locationArr[1]);
			
			newQuestion.setLat(latitute);
			newQuestion.setLngt(longitute);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList semanticTags = SentenceParser.sendQuestion(question);
		String semanticTagsStr = "";
		
		for (int i = 0; i < semanticTags.size(); i++) {
			if("".equals(semanticTagsStr))
				semanticTagsStr = (String)(semanticTags.get(i)) + ";;";
			else
				semanticTagsStr = semanticTagsStr + (String)(semanticTags.get(i)) + ";;";
		}
		
		newQuestion.setSemanticTags(semanticTagsStr);
		
		dataAccess.dbAccess.addQuestion(newQuestion);
		
		ArrayList<Question> relatedQuestions = dataAccess.dbAccess.getQuestionsWithProperties(semanticTags);
		
		// get cold answers to TDB
		ColdAnswerList coldAnswerList = new ColdAnswerList();
		ArrayList<ColdAnswer> coldAnswers = new ArrayList<ColdAnswer>();
		
		// filter questions according to location		
		for (int i = 0; i < relatedQuestions.size() ; i++) {
			
			Question tmpQuestion = relatedQuestions.get(i);
			
			if( Math.abs(tmpQuestion.getLat()-newQuestion.getLat())<0.03 && Math.abs(tmpQuestion.getLngt()-newQuestion.getLngt())<0.03){
				ArrayList<WarmAnswer> tmpWarmAnswers = dataAccess.dbAccess.getWarmAnswers(tmpQuestion.getQuestionText());
				
				for (int j = 0; j < tmpWarmAnswers.size(); j++) {
					ColdAnswer coldAnswer = new ColdAnswer();
					coldAnswer.setAnswer(tmpWarmAnswers.get(j).getAnswer());
					coldAnswer.setUsername(tmpWarmAnswers.get(j).getUsername());
					
					coldAnswers.add(coldAnswer);
				}
				
			}
		}		
		
		
		//FIXME
		ColdAnswer coldAnswer2 = new ColdAnswer();
		coldAnswer2.setAnswer("");
		coldAnswer2.setUsername("");
		coldAnswers.add(coldAnswer2);
		coldAnswers.add(coldAnswer2);
		
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

