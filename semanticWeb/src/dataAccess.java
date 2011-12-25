import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.XSD;

public class dataAccess {

    static String QuestionClass = "Question";
    static String AnswerClass = "Answer";
    static String UserClass = "User";
    
	static dataAccess dbAccess = new dataAccess("ontos\\OntologyQAPoint.owl","http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl");
	
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
	
	//list all classes of model
	public void listClasses(){
	
	 ExtendedIterator iterator = currentModel.listClasses();
		
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
	
	
	

