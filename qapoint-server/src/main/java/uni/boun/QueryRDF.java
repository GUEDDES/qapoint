/**
 * Querying DBpedia by using Jena-ARQ 
 */
package uni.boun;

import java.util.Hashtable;

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
