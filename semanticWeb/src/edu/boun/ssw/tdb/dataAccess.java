package edu.boun.ssw.tdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import edu.boun.ssw.client.User;
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
    String QuestionLatitude = "LatitudeOfQuestion";
    String QuestionLongitude = "LongitudeOfQuestion";
    
    
    
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
			//in = this.getClass().getClassLoader().getResourceAsStream(pathToOwl);
			in = new FileInputStream(new File(pathToOwl));
		    currentModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
			currentModel.read(in, null);
			
		}catch(Exception ex){
			
		}finally{
			
		}
	}
	
	//add warm answer for the question
	public void addWarmAnswer(WarmAnswer warmAnswer, String questionText){
		String newAnswerID="Answer"+ new Date().getTime();
		this.addIndivualToSpecifClass(newAnswerID,AnswerClass);
		String answerText = warmAnswer.getAnswer();
		this.addProperty(AnswerText,answerText, newAnswerID);
		
		//yeni eklenen answerla ilgili ili�kiler setlenir
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
	  
	  String newQuestionId = "Question" + new Date().getTime();
	  this.addIndivualToSpecifClass(newQuestionId, QuestionClass);
	  String questionText = question.getQuestionText();
	  this.addProperty(QuestionText, questionText, newQuestionId);
	  this.addProperty(QuestionLatitude, Float.toString(question.getLat()), newQuestionId);
	  this.addProperty(QuestionLongitude, Float.toString(question.getLngt()), newQuestionId);
	  this.addProperty(QuestionTags, question.getSemanticTags(), newQuestionId);
	  //yeni eklenen answerla ilgili ili�kiler setlenir
		//question-user  asked/isAskedBy
	  String userName = question.getUsername();
      String questionName = newQuestionId;
      
	  this.setRelationBetweenClasses(QuestionUser, questionName, userName);
	  this.setRelationBetweenClasses(UserQuestion, userName,questionName);
	  
	  this.addUserInterest(userName,question.getSemanticTags());
	  writeToOwl();
		
	}

	
	//get warm answers for the question
	public ArrayList<WarmAnswer> getWarmAnswers(String questionText){
		ArrayList<WarmAnswer> warmAnswerList = new ArrayList<WarmAnswer>();
		
		//get only answers of that question
		/*
		 * question-answer -> isAnswerOf
		 * question�n textinden, question�n 'id ye git
		 * answer instancelerini gezip , isAnswerOf range = questionId olanlar� getir
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
		
		String pathToQuest = baseUri + "#" + QuestionClass;
		Resource resQuest = currentModel.getResource(pathToQuest);
		String pathToTagsOfQuestProp = baseUri+ "#" + QuestionTags;
		DatatypeProperty propTags = currentModel.getDatatypeProperty(pathToTagsOfQuestProp);
		String pathToIsAskedBy = baseUri + "#" + QuestionUser;
		ObjectProperty isAskedByProp = currentModel.getObjectProperty(pathToIsAskedBy);
		String pathToQuestText = baseUri + "#" + QuestionText;
		DatatypeProperty propText = currentModel.getDatatypeProperty(pathToQuestText);
		String pathToLong = baseUri + "#" + QuestionLongitude;
		String pathToLat = baseUri + "#" + QuestionLatitude;
		DatatypeProperty propLong = currentModel.getDatatypeProperty(pathToLong);
		DatatypeProperty propLat = currentModel.getDatatypeProperty(pathToLat);
		ExtendedIterator<Individual> iteratorForQuests = currentModel.listIndividuals(resQuest);
		for(;iteratorForQuests.hasNext();){
		Question newQuest = new Question();
		Individual indivQuest = iteratorForQuests.next();
		if(indivQuest.getPropertyValue(propTags) != null){
		String tagsOfIndiv = indivQuest.getPropertyValue(propTags).toString();
		String questText="";
		String userName= "";
		float longitude,latitude;
		if(contains(arrayList,tagsOfIndiv)){
		  questText = indivQuest.getPropertyValue(propText).toString();
		  longitude = Float.parseFloat(indivQuest.getPropertyValue(propLong).toString());
		  latitude = Float.parseFloat(indivQuest.getPropertyValue(propLat).toString());
		  Resource resUser = indivQuest.getPropertyResourceValue(isAskedByProp);
		  Individual indivUser = currentModel.getIndividual(baseUri+"#"+resUser.getLocalName().toString());
		  userName = indivUser.getLocalName();
		  newQuest.setQuestionText(questText);
		  newQuest.setUsername(userName);
		  newQuest.setLat(latitude);
		  newQuest.setLngt(longitude);
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
		//instance.userName = username olan userlar�n asked propertylerine eslesen questonlari instance yarat�p getir
		ArrayList<Question> questionList = new ArrayList<Question>();
		String pathToQuestClass = baseUri + "#" + QuestionClass;
		String pathToUserNameProp = baseUri + "#" + UserName;
		String pathToIsAskedProp = baseUri + "#" + QuestionUser;
		String pathToQuestionText = baseUri+ "#" + QuestionText;
		Resource resAnswer = currentModel.getResource(pathToQuestClass);
		
	    DatatypeProperty pUserName = currentModel.getDatatypeProperty(pathToUserNameProp);
	    DatatypeProperty pText = currentModel.getDatatypeProperty(pathToQuestionText);
	    ObjectProperty pIsAsked = currentModel.getObjectProperty(pathToIsAskedProp);
	    ExtendedIterator<Individual> iteratorForQuest= currentModel.listIndividuals(resAnswer);
	    
		for(;iteratorForQuest.hasNext();){
			Question newQuest = new Question();
			Individual indivQuestion= iteratorForQuest.next();
			Resource resUser = indivQuestion.getPropertyResourceValue(pIsAsked);
			     
			 if(resUser.getLocalName().toString().equals(username)){
				newQuest.setQuestionText(indivQuestion.getPropertyValue(pText).toString());
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
	
	public void addUserInterest(String userName,String newUserInterest){
		//userin interestleri alinacak,listeye eklenicekler, newUserInterestListide oluscak,
		//temp String olusturulacak
		//userin kendi interestleri i�inde olmayan interestler eklenip,update edilecek
		String pathToInterestProp = baseUri + "#" + UserInterests;
		Property resProp = currentModel.getProperty(pathToInterestProp);
		String pathToIndiv = baseUri + "#" + userName;
		Individual indivUser = currentModel.getIndividual(pathToIndiv);
		String temp = indivUser.getPropertyValue(resProp).toString();
		ArrayList<String> alreadyInterests = new ArrayList<String>();
		ArrayList<String> newInterestList = new ArrayList<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(temp);
		while(tokenizer.hasMoreTokens()){
			String nextToken = tokenizer.nextToken(";;");
			alreadyInterests.add(nextToken);
		}
		
	    StringTokenizer tokenizerNew = new StringTokenizer(newUserInterest);
		while(tokenizerNew.hasMoreTokens()){
			String nextToken = tokenizerNew.nextToken(";;");
			newInterestList.add(nextToken);
		}
		
		for(int i = 0; i < alreadyInterests.size();i++){
			for(int j =0; j < newInterestList.size(); j++){
				if(!contains(alreadyInterests,newInterestList.get(j))){
					temp += newInterestList.get(j) + ";;"; 
				}
			}
		}
		
		System.out.println(temp);
		//RDFNode nd = new dataAccess(pathToIndiv, temp)
		indivUser.addProperty(resProp, temp);
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
   
  public ArrayList<User> recommend(Question quest){
	  ArrayList<User> userList = new ArrayList<User>();
	  String pathToUserClass = baseUri + "#" + UserClass;
	  Resource resUser = currentModel.getResource(pathToUserClass);
	  String pathToInterest = baseUri+ "#" + UserInterests;
	  DatatypeProperty propInterest = currentModel.getDatatypeProperty(pathToInterest);
	  ExtendedIterator<Individual> iteratorUser = currentModel.listIndividuals(resUser);
	  //questinin taglerinden herhangi biri user Interestlerinde varsa recommend et
	  ArrayList<String> questTagList = new ArrayList<String>();
	  StringTokenizer tokenTags = new StringTokenizer(quest.getSemanticTags());
	  while(tokenTags.hasMoreTokens()){
		  String nextToken = tokenTags.nextToken(";;");
		  questTagList.add(nextToken);
	  }
	  
	  for(;iteratorUser.hasNext();){
		  String interestOfCurrentIndividual="";
		  Individual currentUser = iteratorUser.next();
		  interestOfCurrentIndividual = currentUser.getPropertyValue(propInterest).toString();
		  ArrayList<String> userInt = new ArrayList<String>();
		  StringTokenizer tokenUser = new StringTokenizer(interestOfCurrentIndividual);
		  while(tokenUser.hasMoreTokens()){
			  String nextToken = tokenUser.nextToken(";;");
			  userInt.add(nextToken);
		  }
		 
		  for(int i= 0; i < questTagList.size(); i++){
			  if(contains(userInt,questTagList.get(i))){
				  User newUser = new User();
				  newUser.setUsername(currentUser.getLocalName());
				  newUser.setInterests(interestOfCurrentIndividual);
				  userList.add(newUser);
				  break;
			  }
		  }
		  
		  return userList;
		  
	  }
	  
	  
	  
	  return userList;
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

	
}
	
	
	

