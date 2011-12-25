import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class denemeLoadOwl {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

       
		InputStream in = new FileInputStream(new File("ontos\\OntologyQAPoint.owl"));
		OntModel model2 = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		model2.read(in, null);
		// prints out the RDF/XML structure
		
		/*ExtendedIterator iterator = model2.listClasses();
		
		Resource res = null;
		for (; iterator.hasNext();) {
			res = (Resource) iterator.next();
			if(res.getLocalName().equals("Question"))
				break;
		}
		
		ExtendedIterator iter = model2.listIndividuals(res);
		
		Individual tmpInd = null;
		for (; iter.hasNext();) {
			tmpInd = (Individual) iter.next();
			System.out.println(tmpInd.getLocalName());
		}
		
	    Individual indv = model2.createIndividual("http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#q4", res);
		Individual indv2 = model2.createIndividual("http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#q5", res);
        
		 */
		/*StmtIterator iterProp =  (StmtIterator)res.listProperties();
		Property prop = null;
		RDFNode node = null;
		
		for(; iterProp.hasNext();){
			Statement s = iterProp.next();
			if(s.getLiteral().equals("Text"))
				break;
		}*/
        
		
		
		
		Individual indiv3 = model2.getIndividual("http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#tugce");
		DatatypeProperty textProp = model2.getDatatypeProperty("http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#Text");
		indiv3.addProperty(textProp,"Question 3");
		
		
		
		//Question parcalandýktan sonra elimizde kalan tagler dataproperty olarak Question sýnýfýna eklenicek
		
		/*String baseUri = "http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl";
		DatatypeProperty newProp= model2.createDatatypeProperty(baseUri+"#tag1");
	    newProp.setDomain(res);
	    newProp.setRange(XSD.xstring);*/

		/*res.addProperty(RDF.type, "tag5");
		StmtIterator iterProp =  (StmtIterator)res.listProperties();
		Property prop = null;

	*/
		
		
	
		in.close();

		OutputStream out = new FileOutputStream(new File("ontos\\OntologyQAPoint.owl"));
		
		model2.write(out);
		model2.close();
//		System.out.println(" ");
//        // Close the dataset.
//        ds.close();
//
//
//		// Create a new query
//		/*
//		 * PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT {
//		 * <http://example/egbook3> dc:title "This is an example title" }
//		 */
//		/*
//		 * String queryString =
//		 * "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
//		 * "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
//		 * "select ?uri "+ "where { "+
//		 * "?uri rdfs:subClassOf <http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#Answer>  "
//		 * + "} \n ";
//		 */
//
//		/*
//		 * PREFIX dc: <http://purl.org/dc/elements/1.1/> PREFIX ns:
//		 * <http://example.org/ns#> INSERT DATA { GRAPH
//		 * <http://example/bookStore> { <http://example/book1> ns:price 42 } }
//		 */
//
//		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//				+ "INSERT DATA "
//				+ "GRAPH <http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#Question> "
//				+ "{ <http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#q1> rdf:Text  "
//				+ "This is an example title" + "} \n ";
//
//		Query query = QueryFactory.create(queryString);
//
//		System.out.println("----------------------");
//
//		System.out.println("Query Result Sheet");
//
//		System.out.println("----------------------");
//
//		System.out.println("Direct&Indirect Descendants (model1)");
//
//		System.out.println("-------------------");
//
//		// Execute the query and obtain results
//		QueryExecution qe = QueryExecutionFactory.create(query, model2);
//		com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
//
//		// Output query results
//		ResultSetFormatter.out(System.out, results, query);

	}

}
