import java.io.IOException;

import edu.boun.ssw.tdb.dataAccess;


public class interfaceTry {
 
	
	public static void main(String[] args) throws IOException {
	
		
		//addQuestion "What is the best Italian rest? "
		//addUser
		//addUserInterest
		//addAnswer
		
		
		dataAccess dbAccess = dataAccess.dbAccess;
		
		//add Question Individual questionNew
		  //dbAccess.addIndivualToSpecifClass("questionNew", dbAccess.QuestionClass);
		
		//add Text to questionNew
		 //dbAccess.addProperty("Text", "Where is the best Italian rest in Taksim?", "questionNew");
		
		//addUser tugce
		  //dbAccess.addIndivualToSpecifClass("userTugce", dbAccess.UserClass);
		
		//addUserInterest
		  //dbAccess.addProperty("interestOfUser", "pizza", "userTugce");
		
		//addAnswer Individual
		 //dbAccess.addIndivualToSpecifClass("answer1", dbAccess.AnswerClass);
	   
		//add answer text 
		 //dbAccess.addProperty("AnswerText", "Toscana", "answer1");
		
		
		
		
		
		
	}
}
