import java.io.IOException;
import java.util.ArrayList;

import edu.boun.ssw.client.Question;
import edu.boun.ssw.client.User;
import edu.boun.ssw.tdb.dataAccess;


public class interfaceTry {
 
	
	public static void main(String[] args) throws IOException {
	
		
		//addQuestion "What is the best Italian rest? "
		//addUser
		//addUserInterest
		//addAnswer
		
		
		dataAccess dbAccess = dataAccess.dbAccess;
		
		/*add Question Individual questionNew
		  dbAccess.addIndivualToSpecifClass("questionNew", dbAccess.QuestionClass);
		
		add Text to questionNew
		 dbAccess.addProperty("Text", "Where is the best Italian rest in Taksim?", "questionNew");
		
		 addUser tugce
		   dbAccess.addIndivualToSpecifClass("userTugce", dbAccess.UserClass);
		
		 addUserInterest
		  dbAccess.addProperty("interestOfUser", "pizza", "userTugce");
		
		addAnswer Individual
		 dbAccess.addIndivualToSpecifClass("answer1", dbAccess.AnswerClass);
	   
		add answer text 
		 dbAccess.addProperty("AnswerText", "Toscana", "answer1");
		  * */
		  
		
		 /*1= user soru sordugunda
		 parcala,question individualini olustur,ekle
		 user soru sordugunda user-question relationlari update et
		 answerlari gez, tagleri içeren answerlari ve kimlerin sordugunu ekranda göster
		
		  Suppose the question "What is the best Italian food restaurant in Taksim? "
		  tag1 = best
		  tag2 = Italian
		  tag3 = food
		  tag4 = restaurant
		 
		  //question indiv create
		   
		   
		   String exampleQuestion = "QuestExample1";
		   String quest = "What is the best Italian food restaurant in Taksim? ";
		   dbAccess.addIndivualToSpecifClass(exampleQuestion, dbAccess.QuestionClass);
		   dbAccess.addProperty("Text", quest, exampleQuestion);
		   
		   //isAskedBy
		   //asked
		   
		   dbAccess.setRelationBetweenClasses("isAskedBy","QuestExample1","userTugce");
		   dbAccess.setRelationBetweenClasses("asked", "userTugce", "QuestExample1");
		   
		   
		   ArrayList<String> listOfTags = new ArrayList<String>();
		   listOfTags.add("best"); listOfTags.add("Italian"); listOfTags.add("food"); listOfTags.add("restaurant");
		   
		   String tagsOfQuestion="";
		   for(String currentTag : listOfTags){
			   tagsOfQuestion += currentTag + "-";
		   }
		
		   dbAccess.addProperty("TagsOfQuestion", tagsOfQuestion, "QuestExample1");
		   
		    * */
		  
		//denenecek metotlar
		//addWarmAnswer
		//addQuestion
		//getWarmAnswer
		
		
		//1.add2User
		//2.addQuestion
		//3.addWarmAnswer
		//4.getWarmAnswer
		/*User askedUser = new User("askedUser");
		User answeredUser = new User("answeredUser");
		Question questNew = new Question(askedUser.getUsername(), "What is the best place for eating burger in Alsancak?");
		WarmAnswer warmAns = new WarmAnswer();
		warmAns.setAnswer("Hi, Sunset is the best!");
		warmAns.setUsername(answeredUser.getUsername());
		*/
		User askedUser = new User("askedUser");
		//User answeredUser = new User("answeredUser");
		//dbAccess.addUser(askedUser.getUsername());
		//dbAccess.addUser(answeredUser.getUsername());
		//Question questNew = new Question(askedUser.getUsername(), "What is the best place for eating burger in Alsancak?");
		//dbAccess.addQuestion(questNew);
		//WarmAnswer warmAns = new WarmAnswer();
		//warmAns.setAnswer("Hi, Sunset is the best!");
		//warmAns.setUsername(answeredUser.getUsername());
		
		//dbAccess.addWarmAnswer(warmAns, questNew.getQuestionText());
		
		//dbAccess.getWarmAnswers(questNew.getQuestionText());
		//dbAccess.getQuestionsByUserName("askedUser");

		 /*ArrayList<String> listOfTags = new ArrayList<String>();
		 listOfTags.add("best"); listOfTags.add("Italian"); listOfTags.add("food"); listOfTags.add("restaurant");
		   
		 String tagsOfQuestion="";
		   for(String currentTag : listOfTags){
			   tagsOfQuestion += currentTag + ";;";
		   }
		
		 dbAccess.addProperty("TagsOfQuestion", tagsOfQuestion, "QuestExample1");*/
		 ArrayList<String> listOfTagsEx1 = new ArrayList<String>();
		 ArrayList<String> listOfTagsEx2 = new ArrayList<String>();
		 
		 listOfTagsEx1.add("neither"); listOfTagsEx1.add("nor");
		 
		 dbAccess.getQuestionsWithProperties(listOfTagsEx1);
	}
}
