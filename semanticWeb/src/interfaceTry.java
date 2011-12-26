import java.io.IOException;
import java.util.ArrayList;

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
		 answerlari gez, tagleri içeren answerlari ve kimlerin sordugunu ekranda göster*/
		
		/*Suppose the question "What is the best Italian food restaurant in Taksim? "
		  tag1 = best
		  tag2 = Italian
		  tag3 = food
		  tag4 = restaurant
		 */ 
		  //question indiv create
		   String exampleQuestion = "QuestExample";
		   String quest = "What is the best Italian food restaurant in Taksim? ";
		   dbAccess.addIndivualToSpecifClass(exampleQuestion, dbAccess.QuestionClass);
		   dbAccess.addProperty("Text", quest, exampleQuestion);
		   
		   
		   /*ArrayList<String> listOfTags = new ArrayList<String>();
		   listOfTags.add("best"); listOfTags.add("Italian"); listOfTags.add("food"); listOfTags.add("restaurant");
		   
		   int i = 0;
		   for(String currentTag : listOfTags){
			   String newProp = "tag" + i++;
			   dbAccess.addDataPropToSpecificClass(newProp, dbAccess.QuestionClass);
		   }*/
		   
		   //isAskedBy
		   //asked
		   
		   dbAccess.setObjectPropOfSpecClass("isAskedBy","QuestExample","userTugce");
		
	
		
	}
}
