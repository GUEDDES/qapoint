import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.net.SMTPAppender;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Node_Variable;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.DC;

public class denemeLoadOwl {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

//        String assemblerFile = "ontos/OntologyQAPoint.owl" ;
//
//        Dataset ds = TDBFactory.assembleDataset(assemblerFile) ;
		
        // Direct way: Make a TDB-back Jena model in the named directory.
        String directory = "MyDatabases/DB1" ;
//        Dataset ds = TDBFactory. createDataset(directory) ;
//        Model model = ds.getDefaultModel();
//		Graph grap = TDBFactory.createGraph(directory);
//		
//		Node s = new Node_Variable("http://example.org/bob");
//		Node p = new Node_Variable("pirasa");
//		Node o = new Node_Variable("vvvvv");
//
//		Triple t = new Triple(s, p, o);
//		grap.add(t);
//		
//		grap.close();
		
//        StmtIterator stBefore = model.listStatements();
//        
//        for (; stBefore.hasNext();) {
//        	Object obj = stBefore.next();
//			System.out.println(obj.toString());
//		}
//        
//        
//        Resource resx = model.createResource("Serkan");
//        resx.addProperty(DC.title, "SPARQL - the book")
//        .addProperty(DC.description, "A book about SPARQL") ;
//        // ... do work ...
//        
//        ds.listNames();
//        ExtendedIterator stAfter = ds.getDefaultModel().listStatements();
//        for (; stAfter.hasNext();) {
//			System.out.println(stBefore.next().toString());
//		}
//        
		InputStream in = new FileInputStream(new File(
				"ontos\\OntologyQAPoint.owl"));
		OntModel model2 = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM);
		model2.read(in, null);
		// prints out the RDF/XML structure
		
		ExtendedIterator iterator = model2.listClasses();
		
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
		
		Individual indv = model2.createIndividual("http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#tugce", res);
		Individual indv2 = model2.createIndividual("http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#serkan", res);

		
		iter = model2.listIndividuals(res);
		
		tmpInd = null;
		for (; iter.hasNext();) {
			tmpInd = (Individual) iter.next();
			System.out.println(tmpInd.getLocalName());
		}
		
//		indv.setPropertyValue(property, value);
		
		// Resource r1 = model2.createResource(type)
		// ("http://example.org/book#1") ;
		// Resource r2 = model2.createResource("http://example.org/book#2") ;
		//
		// r1.addProperty(DC.title, "SPARQL - the book")
		// .addProperty(DC.description, "A book about SPARQL") ;
		//
		// r2.addProperty(DC.title, "Advanced techniques for SPARQL") ;

		in.close();

		OutputStream out = new FileOutputStream(new File(
		"ontos\\OntologyQAPoint.owl"));
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
