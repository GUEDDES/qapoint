/**
 * Querying DBpedia by using Jena-ARQ 
 */
package uni.boun;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

/**
 * @author serkankirbas
 */
public class QueryRDF {

	public static final String RDF_SUBCLASS_OF = "http://www.w3.org/2000/01/rdf-schema#subClassOf";
	public static final String RDF_SAME_AS = "http://www.w3.org/2002/07/owl#sameAs";
    public static final String RDFS_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String OWL_CLASS = "http://www.w3.org/2002/07/owl#Class";
    
	public Hashtable queryService(String service, String sparqlQueryString,
			String solutionConcept) {

		Hashtable returnValues = new Hashtable();
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,
				query);

		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String x = soln.get(solutionConcept).toString();
				System.out.print(x + "\n");
				returnValues.put(x, x);
			}

		} finally {
			qexec.close();
		}
		
		return returnValues;

	}

	public ArrayList<PropertyAndValue> queryService(String service, String sparqlQueryString,
			String solutionConcept, String otherConcept) {

		ArrayList returnValues = new ArrayList();
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service,
				query);

		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String x = soln.get(solutionConcept).toString();
				String y = soln.get(otherConcept).toString();
				
				System.out.print(x + " -> " + y + " \n");
				returnValues.add(new PropertyAndValue(x, y));
			}

		} finally {
			qexec.close();
		}
		
		return returnValues;

	}
	
	public void checkService(String service) {

		String query = "ASK { }";

		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try {
			if (qe.execAsk()) {
				System.out.println(service + " is UP");
			}
		} catch (QueryExceptionHTTP e) {
			System.out.println(service + " is DOWN");
		} finally {
			qe.close();
		} // end try/catch/finally
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Configure the proxy settings
//		System.setProperty("socksProxyHost", "11.222.33.44");
//		System.setProperty("socksProxyPort", "80");
//		System.setProperty("http.proxyHost", "11.222.33.44");
//		System.setProperty("http.proxyPort", "80");

		Hashtable<String, String> equivalentClasses = new Hashtable<String, String>();
		LinkedList newlyAddedClasses = new LinkedList();
		
		String resURI = "http://sw.opencyc.org/2008/06/10/concept/Mx4rwQCZUJwpEbGdrcN5Y29ycA";
		newlyAddedClasses.add(resURI);
		equivalentClasses.put(resURI, resURI);
		
		for (int i = 0; i < newlyAddedClasses.size(); i++) {		
			ArrayList<PropertyAndValue> properties = QueryRDF.getPropertiesOfResource((String)newlyAddedClasses.get(i));
			
			for (PropertyAndValue propertyAndValue : properties) {
				if( !equivalentClasses.contains(propertyAndValue.getValue()) && (!propertyAndValue.getValue().equals(OWL_CLASS)) && (propertyAndValue.getPropertyURI().equals(RDFS_TYPE)) ){
					System.out.println(propertyAndValue.getValue());
					equivalentClasses.put(propertyAndValue.getValue(), propertyAndValue.getValue());
					newlyAddedClasses.add(propertyAndValue.getValue());
				}
				else if(propertyAndValue.getPropertyURI().equals(RDF_SAME_AS) && propertyAndValue.getValue().indexOf("dbpedia")!=-1){
					equivalentClasses.put(propertyAndValue.getValue(), propertyAndValue.getValue());
					return;
				}
			}
		}		
	}
	
	/**
	 * @param args
	 */
	public static void main2(String[] args) {
		// Configure the proxy settings
//		System.setProperty("socksProxyHost", "11.222.33.44");
//		System.setProperty("socksProxyPort", "80");
//		System.setProperty("http.proxyHost", "11.222.33.44");
//		System.setProperty("http.proxyPort", "80");

		String service = "http://dbpedia.org/sparql";
		QueryRDF queryDBPedia = new QueryRDF();
		queryDBPedia.checkService(service);

		String prefix = "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ '\n' + "PREFIX dbpedia: <http://dbpedia.org/resource/> ";

		String sparqlQueryString = prefix + '\n'
				+ "select distinct ?mosque where { "
				+ "?mosque dbpprop:architectureType  \"Mosque\"@en. "
				+ "?mosque dbpprop:location dbpedia:Istanbul. " + "} "
				+ "LIMIT 100";

		queryDBPedia.queryService(service, sparqlQueryString, "mosque");
	}
	
	/**
	 * @param args
	 */
	public static Hashtable getSemanticInfo(String concept) {
		// Configure the proxy settings
//		System.setProperty("socksProxyHost", "11.222.33.44");
//		System.setProperty("socksProxyPort", "80");
//		System.setProperty("http.proxyHost", "11.222.33.44");
//		System.setProperty("http.proxyPort", "80");

		String service = "http://dbpedia.org/sparql";
		QueryRDF queryDBPedia = new QueryRDF();
		queryDBPedia.checkService(service);

		String prefix = "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ '\n' + "PREFIX dbpedia: <http://dbpedia.org/resource/> "
				+ '\n' + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema/> ";

		String sparqlQueryString = prefix + '\n'
				+ "select distinct ?mosque where { "
				+ "?mosque <http://www.w3.org/2000/01/rdf-schema#label>  \"" + concept +"\"@en. "
//				+ "?mosque <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Thing>. "
				
//				+ "?mosque dbpprop:location dbpedia:Istanbul. "
				+ "} "
				+ "LIMIT 100";

		return queryDBPedia.queryService(service, sparqlQueryString, "mosque");
		
		// check the ambiguities -> dbpedia-owl:wikiPageDisambiguates attribute
	}
	
	public static Hashtable getSemanticInfo(String concept, String propertyURI, String propertyValue) {
		// Configure the proxy settings
//		System.setProperty("socksProxyHost", "11.222.33.44");
//		System.setProperty("socksProxyPort", "80");
//		System.setProperty("http.proxyHost", "11.222.33.44");
//		System.setProperty("http.proxyPort", "80");

		String service = "http://dbpedia.org/sparql";
		QueryRDF queryDBPedia = new QueryRDF();
		queryDBPedia.checkService(service);

		String prefix = "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ '\n' + "PREFIX dbpedia: <http://dbpedia.org/resource/> "
				+ '\n' + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema/> ";

		String sparqlQueryString = prefix + '\n'
				+ "select distinct ?mosque where { "
				+ "?mosque <http://www.w3.org/2000/01/rdf-schema#label>  \"" + concept +"\"@en. "
				+ "?mosque <" + propertyURI +">  <" + propertyValue +"> ."
//				+ "?mosque <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Thing>. "
				
//				+ "?mosque dbpprop:location dbpedia:Istanbul. "
				+ "} "
				+ "LIMIT 100";

		return queryDBPedia.queryService(service, sparqlQueryString, "mosque");
		
		// check the ambiguities -> dbpedia-owl:wikiPageDisambiguates attribute
	}

	/**
	 * @param args
	 */
	public static ArrayList<PropertyAndValue> getPropertiesOfResource(String concept) {
		// Configure the proxy settings
//		System.setProperty("socksProxyHost", "11.222.33.44");
//		System.setProperty("socksProxyPort", "80");
//		System.setProperty("http.proxyHost", "11.222.33.44");
//		System.setProperty("http.proxyPort", "80");

		String service = "http://dbpedia.org/sparql";
		QueryRDF queryDBPedia = new QueryRDF();
		queryDBPedia.checkService(service);

		String prefix = "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ '\n' + "PREFIX dbpedia: <http://dbpedia.org/resource/> "
				+ '\n' + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema/> ";

		String sparqlQueryString = prefix + '\n'
				+ "select distinct ?property ?value where { "
				+ "<"  + concept + ">" + " ?property ?value. "
				+ "} "
				+ "LIMIT 100";

		System.out.println(sparqlQueryString);
		
		return queryDBPedia.queryService(service, sparqlQueryString, "property", "value");
		
		// check the ambiguities -> dbpedia-owl:wikiPageDisambiguates attribute
	}
	
	public static void getAnimalSemanticInfo(String concept) {
		// Configure the proxy settings
//		System.setProperty("socksProxyHost", "11.222.33.44");
//		System.setProperty("socksProxyPort", "80");
//		System.setProperty("http.proxyHost", "11.222.33.44");
//		System.setProperty("http.proxyPort", "80");

		String service = "http://dbpedia.org/sparql";
		QueryRDF queryDBPedia = new QueryRDF();
		queryDBPedia.checkService(service);

		String prefix = "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ '\n' + "PREFIX dbpedia: <http://dbpedia.org/resource/> "
				+ '\n' + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema/> ";

		String sparqlQueryString = prefix + '\n'
				+ "select distinct ?mosque where { "
				+ "http://dbpedia.org/ontology/Animal ?xx ?mosque. " 
//				+ "?mosque dbpprop:location dbpedia:Istanbul. "
				+ "} "
				+ "LIMIT 100";

		queryDBPedia.queryService(service, sparqlQueryString, "mosque");
	}

}
