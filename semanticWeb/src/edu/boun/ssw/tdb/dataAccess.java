package edu.boun.ssw.tdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.XSD;

import edu.boun.ssw.client.Question;
import edu.boun.ssw.client.WarmAnswer;

public class dataAccess {

	//classes
    public String QuestionClass = "Question";
    String AnswerClass = "Answer";
    String UserClass = "User";
    
    //dataProperties
    String AnswerText = "AnswerText";
    String QuestionText = "Text";
    String UserName = "userName";
    String UserCurrentLocation = "currentLocation";
    String UserInterests = "interestsOfUser";
    String AnswerTags = "TagsOfAnswer";
    String QuestionTags =	"TagsOfQuestion";
    
    
    
    //objectProperties
    String UserAnswer = "answered";
    String UserQuestion = "asked";
    String AnswerQuestion = "isAnswerOf";
    String QuestionAnswer = "isQuestionOf";
    String QuestionUser = "isAskedBy";
    String AnswerUser = "isAnsweredBy";
    //????similar?????
    

    
	public static dataAccess dbAccess = new dataAccess("ontos\\OntologyQAPoint.owl","http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl");
	
	int idForAnswers=0;
	int idForQuestions=0;

	
	OntModel currentModel;
	String pathToOwl;
	InputStream in ;
	OutputStream out;
	String baseUri;
	

	private dataAccess(String pathToOwl,String baseUri){
		this.pathToOwl = pathToOwl;
		this.baseUri = baseUri;
		try{
			in = new FileInputStream(new File(pathToOwl));
		    currentModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
			currentModel.read(in, null);
			
		}catch(Exception ex){
			
		}finally{
			
		}
	}
	
	//add warm answer for the question
	public void addWarmAnswer(WarmAnswer warmAnswer, String questionText){
		String newAnswerID="Answer"+ idForAnswers++;
		this.addIndivualToSpecifClass(newAnswerID,AnswerClass);
		String answerText = warmAnswer.getAnswer();
		this.addProperty(AnswerText,answerText, newAnswerID);
		
		//yeni eklenen answerla ilgili iliþkiler setlenir
		//question-answer isAnswerOf/isQuestionOf
		//answer-user  answered/isAnsweredBy
		String userName = warmAnswer.getUsername();
		String pathToRes = baseUri + "#" + QuestionClass;
		Resource resQuestion = currentModel.getResource(pathToRes);
		
			
		Individual indivQuestion = null;
		String pathToProp = baseUri + "#" + QuestionText;
		
		DatatypeProperty p = (DatatypeProperty)currentModel.getDatatypeProperty(pathToProp);
		String pVal="";
		
		ExtendedIterator<Individual> iterator = currentModel.listIndividuals(resQuestion);	
			
		 for (; iterator.hasNext();) {
			indivQuestion = (Individual) iterator.next();
			pVal = indivQuestion.getPropertyValue(p).toString();
			if(pVal.equals(questionText))
			  break;
			}
        String questionName = indivQuestion.getLocalName();
			
		this.setRelationBetweenClasses(AnswerQuestion, newAnswerID,questionName );
		this.setRelationBetweenClasses(QuestionAnswer, questionName, newAnswerID);
		this.setRelationBetweenClasses(AnswerUser, newAnswerID, userName);
		this.setRelationBetweenClasses(UserAnswer, userName,newAnswerID);
		writeToOwl();
	}

	//add question
	public void addQuestion(Question question){
	  String newQuestionId = "Question" + idForQuestions++;
	  this.addIndivualToSpecifClass(newQuestionId, QuestionClass);
	  String questionText = question.getQuestionText();
	  this.addProperty(QuestionText, questionText, newQuestionId);
	
	  //yeni eklenen answerla ilgili iliþkiler setlenir
		//question-user  asked/isAskedBy
	  String userName = question.getUsername();
      String questionName = newQuestionId;
	  this.setRelationBetweenClasses(QuestionUser, questionName, userName);
	  this.setRelationBetweenClasses(UserQuestion, userName,questionName);
	  
	  writeToOwl();
		
	}

	
	//get warm answers for the question
	public ArrayList<WarmAnswer> getWarmAnswers(String questionText){
		ArrayList<WarmAnswer> warmAnswerList = new ArrayList<WarmAnswer>();
		
		//get only answers of that question
		/*
		 * question-answer -> isAnswerOf
		 * questionýn textinden, questionýn 'id ye git
		 * answer instancelerini gezip , isAnswerOf range = questionId olanlarý getir
		 * */
		String pathToQuestClass = baseUri + "#" +  QuestionClass;
		Resource resQuestion = currentModel.getResource(pathToQuestClass);
		String pathToAnswer = baseUri + "#" + AnswerClass;
		Resource resAns = currentModel.getResource(pathToAnswer);
		ExtendedIterator iterator = currentModel.listIndividuals(resQuestion);
			
		Individual indivQuestion = null;
		String pathToProp = baseUri + "#" + QuestionText;
		DatatypeProperty p = currentModel.getDatatypeProperty(pathToProp);
		String pVal="";
			
		 for(; iterator.hasNext();) {
				indivQuestion =  (Individual) iterator.next();
				pVal = indivQuestion.getPropertyValue(p).toString();
				if(pVal.equals(questionText))
					break;
		  }
        String questionName = indivQuestion.getLocalName();
		ExtendedIterator<Individual> iterAnswers = currentModel.listIndividuals(resAns);
		String pathToObjectProp = baseUri + "#" + AnswerQuestion;
		Property pIsAnswerOf = (Property) currentModel.getObjectProperty(pathToObjectProp);
		Individual indivAnswer = null;
		
		String answerText = "";
		String userName = "";
		String pathToAnswerTextProp = baseUri + "#" + AnswerText;
		String pathToAnswerUserProp = baseUri + "#" + AnswerUser;
		String pathToUserNameProp = baseUri + "#" + UserName;
		
		for(;iterAnswers.hasNext();){
			WarmAnswer newWarm = new WarmAnswer();
			indivAnswer = (Individual)iterAnswers.next();
			Resource resRange = indivAnswer.getPropertyResourceValue(pIsAnswerOf);
			if(resRange != null){
			if(resRange.getLocalName().equals(questionName)){
			   DatatypeProperty answerTextProp = currentModel.getDatatypeProperty(pathToAnswerTextProp);
			   answerText = indivAnswer.getPropertyValue(answerTextProp).toString();
			   
			   ObjectProperty isAnsweredByProp = currentModel.getObjectProperty(pathToAnswerUserProp) ;
			   Resource answeredUser = indivAnswer.getPropertyResourceValue(isAnsweredByProp);
			   DatatypeProperty userNameProp = currentModel.getDatatypeProperty(pathToUserNameProp);
			   userName = answeredUser.getLocalName().toString();
			   
			   newWarm.setAnswer(answerText);
			   newWarm.setUsername(userName);
			   warmAnswerList.add(newWarm);
			}
			
			for(WarmAnswer wa: warmAnswerList)
				System.out.println(wa.getUsername()+ "  :" + wa.getAnswer() );
			}
		}
		
		return warmAnswerList;
	}
	

	public ArrayList<Question> getQuestionsWithProperties(ArrayList<String> arrayList) {
		ArrayList<Question> questionList = new ArrayList<Question>();
		//questionListin individuallarýný gezip, tag stringi içinde bunlardan en az biri olan questionlarý dondur
		String pathToQuest = baseUri + "#" + QuestionClass;
		Resource resQuest = currentModel.getResource(pathToQuest);
		String pathToTagsOfQuestProp = baseUri+ "#" + QuestionTags;
		DatatypeProperty propTags = currentModel.getDatatypeProperty(pathToTagsOfQuestProp);
		String pathToIsAskedBy = baseUri + "#" + QuestionUser;
		ObjectProperty isAskedByProp = currentModel.getObjectProperty(pathToIsAskedBy);
		String pathToQuestText = baseUri + "#" + QuestionText;
		DatatypeProperty propText = currentModel.getDatatypeProperty(pathToQuestText);
		
		ExtendedIterator<Individual> iteratorForQuests = currentModel.listIndividuals(resQuest);
		
		for(;iteratorForQuests.hasNext();){
			Question newQuest = new Question();
			Individual indivQuest = iteratorForQuests.next();
			if(indivQuest.getPropertyValue(propTags) != null){
			String tagsOfIndiv = indivQuest.getPropertyValue(propTags).toString();
			String questText="";
			String userName= "";
			if(contains(arrayList,tagsOfIndiv)){
			   questText = indivQuest.getPropertyValue(propText).toString();
			   Resource resUser = indivQuest.getPropertyResourceValue(isAskedByProp);
			   Individual indivUser = currentModel.getIndividual(baseUri+"#"+resUser.getLocalName().toString());
			   userName = indivUser.getLocalName();
			   newQuest.setQuestionText(questText);
			   newQuest.setUsername(userName);
			   questionList.add(newQuest);
			}
		 }
		}
		
		for(Question q: questionList){
			System.out.println(q.getUsername()+ "   :" + q.getQuestionText());
		}
		return questionList;
	}
	
	private boolean contains(ArrayList<String> listOfTags,String indivTags){
		boolean val = false;
		ArrayList<String> indivTagList = new ArrayList<String>();
		StringTokenizer stTokenizer = new StringTokenizer(indivTags);
		
		while(stTokenizer.hasMoreTokens()){
			String nextToken = stTokenizer.nextToken(";;");
			indivTagList.add(nextToken);
		}
		
		for(int i = 0; i < listOfTags.size(); i++){
			String currentOutTag = listOfTags.get(i);
			for(int j = 0; j < indivTagList.size(); j++){
				if(currentOutTag.equals(indivTagList.get(j))){
					val=true;
					break;
				}
						
			}
		}
		return val;
	}
	
	public ArrayList<Question> getQuestionsByUserName(String username) {
		//User instancelari gezilcek
		//instance.userName = username olan userlarýn asked propertylerine eslesen questonlari instance yaratýp getir
		ArrayList<Question> questionList = new ArrayList<Question>();
		String pathToUserClass = baseUri + "#" + UserClass;
		String pathToUserNameProp = baseUri + "#" + UserName;
		String pathToAskedProp = baseUri + "#" + UserQuestion;
		String pathToQuestionText = baseUri+ "#" + QuestionText;
		Resource resUser = currentModel.getResource(pathToUserClass);
		
	    DatatypeProperty pUserName = currentModel.getDatatypeProperty(pathToUserNameProp);
	    DatatypeProperty pText = currentModel.getDatatypeProperty(pathToQuestionText);
	    ObjectProperty pAsked = currentModel.getObjectProperty(pathToAskedProp);
	    ExtendedIterator<Individual> iteratorForUser = currentModel.listIndividuals(resUser);
	    
		for(;iteratorForUser.hasNext();){
			Question newQuest = new Question();
			Individual indivUser = iteratorForUser.next();
			
			if(indivUser.getLocalName().toString().equals(username)){
			Resource resQuest = indivUser.getPropertyResourceValue(pAsked);	
			String pathToQuestInd = baseUri+ "#" + resQuest.getLocalName();
			Individual indivSpecificUser = currentModel.getIndividual(pathToQuestInd);
			newQuest.setQuestionText(indivSpecificUser.getPropertyValue(pText).toString());
			newQuest.setUsername(username);
			questionList.add(newQuest);
			}
		}
		
		for(Question q: questionList){
			System.out.println(q.getUsername() + "  :" + q.getQuestionText());
		}
		
		return questionList;
	}
	
	public void addUser(String userName){
		this.addIndivualToSpecifClass(userName, UserClass);
		writeToOwl();
	}
	
	public void addUserInterest(String userInterest){
		
		
	}
	
	//list all classes of model
	public void listClasses(){
	
	 ExtendedIterator<OntClass> iterator = currentModel.listClasses();
		
		Resource res = null;
		
		for (; iterator.hasNext();) {
			res = (Resource) iterator.next();
			System.out.println(res.getLocalName());
		}
		
		
	}
	
	//add value of property of individual
	public void addProperty(String propertyName,String value,String indivName){
		String pathToIndiv = baseUri + "#" + indivName;
		Individual indiv = currentModel.getIndividual(pathToIndiv);
		String pathToProp= baseUri + "#" + propertyName;
		DatatypeProperty prop = currentModel.getDatatypeProperty(pathToProp);
		indiv.addProperty(prop,value);
	    writeToOwl();
	}
	
	//add individual to specific class
	public void addIndivualToSpecifClass(String newIndivName, String className){
		
		String newIndivPath = baseUri + "#"+ newIndivName;
		ExtendedIterator iterator = currentModel.listClasses();
		Resource res = null;
		for (; iterator.hasNext();) {
			res = (Resource) iterator.next();
			if(res.getLocalName().equals(className))
				break;
		}
		
		
	    Individual newIndiv = currentModel.createIndividual(newIndivPath, res);
	    writeToOwl();
	    
	}
	
	//add new dataproperty to specific class
	public void addDataPropToSpecificClass(String newPropName, String className){
		
		String pathToNewProp = baseUri + "#" + newPropName;
		
		ExtendedIterator iterator = currentModel.listClasses();
		Resource res = null;
		for (; iterator.hasNext();) {
			res = (Resource) iterator.next();
			if(res.getLocalName().equals(className))
				break;
		}
		
		DatatypeProperty newProp = currentModel.createDatatypeProperty(pathToNewProp);
	    newProp.setDomain(res);
	    newProp.setRange(XSD.xstring);
	    
	    writeToOwl();
	}

	
	
     //setRelation
   public void setRelationBetweenClasses(String propName,String nameDom,String nameRange){
	   String pathToDom = baseUri + "#" + nameDom;
	   String pathToRange = baseUri + "#" + nameRange;
	   Individual domain = currentModel.getIndividual(pathToDom);
	   Individual range = currentModel.getIndividual(pathToRange);
	   String pathToProp= baseUri + "#" + propName;
	   ObjectProperty p = currentModel.getObjectProperty(pathToProp); 
	
	   Statement newStmt = currentModel.createStatement(domain, p, range);
	   currentModel.add(newStmt);
	    writeToOwl();
	}
	
  public void dene(){
	  String pathToProp= baseUri + "#" +"isQuestionOf";
	  ObjectProperty p = currentModel.getObjectProperty(pathToProp); 
	  
  }
   
	//write to owl
	private void writeToOwl(){
		 
		try{
			in.close();
		    out = new FileOutputStream(new File(pathToOwl));
			currentModel.write(out);
		}catch(Exception ex){
			
		}finally{
			
		}
		//model2.close();
	}

	
	   /*
	     add new DataProperty
	   public void addDataPropertyToIndiv(String newDataPropName,String indivName,String value){
		   String pathToNewProp = baseUri + "#" + newDataPropName;
		   String pathToIndiv = baseUri + "#" + indivName;
		   DatatypeProperty p = currentModel.createDatatypeProperty(pathToNewProp);
		   Individual indiv = currentModel.getIndividual(pathToIndiv);
		   p.addDomain(indiv);
		   p.addRange(XSD.xstring);
		   
		  // this.addProperty(newDataPropName, value, indivName);
		   writeToOwl(); 
	   }
	   
	    set object property value
	   public void setObjectPropValue(String propName,String value,String indivName){
	     String pathToIndiv = baseUri + "#" + indivName;
		 String pathToProp= baseUri + "#" + propName;
		 ObjectProperty p = currentModel.getObjectProperty(pathToProp); 
		 Individual indiv = currentModel.getIndividual(pathToIndiv);
			
		 Statement newStmt = currentModel.createStatement(indiv, p, value);
		 currentModel.add(newStmt);
	     writeToOwl();
			
	   }*/
}
	
	
	

