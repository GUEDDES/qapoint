import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

public class owlCreate {
	  public static void main(String[] argv)
	    {
	        // Direct way: Make a TDB-back Jena model in the named directory.
//	        String directory = "MyDatabases/DBOwl" ;
//	        Dataset ds = TDBFactory.createDataset(directory) ;
//	        Model model = ds.getDefaultModel() ;
	        
//	        String ns = new String("http://www.semanticweb.org/ontologies/2011/11/QA#");
//	        OntModel xOntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model); 
//	        xOntModel.createClass(ns + "Person");
	        Model model = TDBFactory.createModel("MyDatabases/DBOwl"); 
	        model.read("file:C:/Users/tugce/Downloads/pizzaowl.owl");
	        // ... do work ...
	        
	        // Close the dataset.
	       model.close();
	       
	       
//	       OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null); 
//	       ontologyModel.read("http://www.co-ode.org/ontologies/pizza/pizza.owl", "RDF/XML-ABBREV");
//	       ontologyModel.close();
	       
	       
//	    // Create an empty ontology model
//	       OntModel ontModel = ModelFactory.createOntologyModel();
//	       String ns = new String("http://www.semanticweb.org/ontologies/2011/11/QA#");
//	       String baseURI = new String("http://www.semanticweb.org/ontologies/2011/11/QA");
//	       Ontology onto = ontModel.createOntology(baseURI);
//
//	       // Create ‘Person’, ‘MalePerson’ and ‘FemalePerson’ classes
//	       OntClass person = ontModel.createClass(ns + "Person");
//	       OntClass malePerson = ontModel.createClass(ns + "MalePerson");
//	       OntClass femalePerson = ontModel.createClass(ns + "FemalePerson");
//
//	       // FemalePerson and MalePerson are subclasses of Person
//	       person.addSubClass(malePerson);
//	       person.addSubClass(femalePerson);
//
//	       // FemalePerson and MalePerson are disjoint
//	       malePerson.addDisjointWith(femalePerson);
//	       femalePerson.addDisjointWith(malePerson);
//	    // Create datatype property 'hasAge'
//	       DatatypeProperty hasAge =
//	       	ontModel.createDatatypeProperty(ns + "hasAge");
//	       // 'hasAge' takes integer values, so its range is 'integer'
//	       // Basic datatypes are defined in the ‘vocabulary’ package
//	       hasAge.setDomain(person);
//	       hasAge.setRange(XSD.integer); // com.hp.hpl.jena.vocabulary.XSD
//
//	       // Create individuals
//	       Individual john = malePerson.createIndividual(ns + "John");
//	       Individual jane = femalePerson.createIndividual(ns + "Jane");
//	       Individual bob = malePerson.createIndividual(ns + "Bob");
//
//	       // Create statement 'John hasAge 20'
//	       Literal age20 =	ontModel.createTypedLiteral("20", XSDDatatype.XSDint);
//	       Statement johnIs20 = 	ontModel.createStatement(john, hasAge, age20);
//	       ontModel.add(johnIs20);
//	       
//	       ontModel.close();


	    }
}
