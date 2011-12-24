
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.InputStream;

	import com.hp.hpl.jena.ontology.OntModel;
	import com.hp.hpl.jena.ontology.OntModelSpec;
	import com.hp.hpl.jena.query.Query;
	import com.hp.hpl.jena.query.QueryExecution;
	import com.hp.hpl.jena.query.QueryExecutionFactory;
	import com.hp.hpl.jena.query.QueryFactory;
	import com.hp.hpl.jena.query.ResultSetFormatter;
	import com.hp.hpl.jena.rdf.model.ModelFactory;

	public class denemeLoadOwl {

	    /**
	     * @param args
	     * @throws IOException 
	     */
	    public static void main(String[] args) throws IOException {
	        // TODO Auto-generated method stub


	        InputStream in = new FileInputStream(new File("ontos\\OntologyQAPoint.owl"));
	        OntModel model2 = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM);
	        model2.read( in, null );
	        //prints out the RDF/XML structure
	        in.close();
	        System.out.println(" ");


	        // Create a new query
	        String queryString =       
	        	 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
	             "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  "+
	             "select ?uri "+
	             "where { "+
	              "?uri rdfs:subClassOf <http://www.semanticweb.org/ontologies/2011/11/OntologyQAPoint.owl#User>  "+
	             "} \n ";
	        Query query = QueryFactory.create(queryString);

	        System.out.println("----------------------");

	        System.out.println("Query Result Sheet");

	        System.out.println("----------------------");

	        System.out.println("Direct&Indirect Descendants (model1)");

	        System.out.println("-------------------");


	        // Execute the query and obtain results
	        QueryExecution qe = QueryExecutionFactory.create(query, model2);
	        com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

	        // Output query results    
	        ResultSetFormatter.out(System.out, results, query);

	    }

	}
