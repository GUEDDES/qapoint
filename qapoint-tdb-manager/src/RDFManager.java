///*******************************************************************************
// * Copyright (c) 2009  Egon Willighagen <egonw@users.sf.net>
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Affero General Public License as
// * published by the Free Software Foundation, either version 3 of the
// * License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU Affero General Public License for more details.
//
// * You should have received a copy of the GNU Affero General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// *
// * Contact: http://www.bioclipse.net/
// ******************************************************************************/
//
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.rmi.activation.Activator;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.hp.hpl.jena.n3.turtle.TurtleParseException;
//import com.hp.hpl.jena.query.Query;
//import com.hp.hpl.jena.query.QueryExecution;
//import com.hp.hpl.jena.query.QueryExecutionFactory;
//import com.hp.hpl.jena.query.QueryFactory;
//import com.hp.hpl.jena.query.QuerySolution;
//import com.hp.hpl.jena.query.ResultSet;
//import com.hp.hpl.jena.rdf.model.Model;
//import com.hp.hpl.jena.rdf.model.Property;
//import com.hp.hpl.jena.rdf.model.RDFNode;
//import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.rdf.model.Statement;
//import com.hp.hpl.jena.rdf.model.StmtIterator;
//import com.hp.hpl.jena.shared.NoReaderForLangException;
//import com.hp.hpl.jena.shared.PrefixMapping;
//import com.hp.hpl.jena.shared.SyntaxError;
//import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
//
//public class RDFManager  {
//
//    public String getManagerName() {
//        return "rdf";
//    }
//    public IRDFStore importFile(IRDFStore store, File target, String format,
//            Hashtable monitor)
//    throws IOException, Exception {
//        return importFromStream(store, target.getContents(), format, monitor);
//    }
//
//    public IRDFStore importFromStream(IRDFStore store, InputStream stream,
//            String format, Hashtable monitor)
//    throws IOException, Exception {
//        if (monitor == null) {
//            monitor = new NullProgressMonitor();
//        }
//        if (format == null) format = "RDF/XML";
//
//        if (!(store instanceof IJenaStore))
//            throw new RuntimeException(
//                "Can only handle IJenaStore's for now."
//            );
//        
//        Model model = ((IJenaStore)store).getModel();
//        try {
//        	model.read(stream, "", format);
//        } catch (SyntaxError error) {
//        	throw new Exception(
//        		"File format is not correct.",
//        		error
//        	);
//        } catch (NoReaderForLangException exception) {
//        	throw new Exception(
//            	"Unknown file format. Supported are \"RDF/XML\", " +
//            	"\"N-TRIPLE\", \"TURTLE\" and \"N3\".",
//            	exception
//        	);
//        } catch (TurtleParseException exception) {
//        	throw new Exception(
//                "Error while parsing file: " +
//                exception.getMessage(),
//                exception
//        	);
//        }
//        return store;
//    }
//
//    public IRDFStore importURL(IRDFStore store, String url, Hashtable monitor)
//            throws IOException, Exception {
//        URL realURL = new URL(url);
//        URLConnection connection = realURL.openConnection();
//        connection.setConnectTimeout(Activator.TIME_OUT);
//        connection.setRequestProperty(
//            "Accept",
//            "application/xml, application/rdf+xml"
//        );
//        importFromStream(store, connection.getInputStream(), null, monitor);
//        return store;
//    }
//
//    public String dump(IRDFStore store) {
//        if (!(store instanceof IJenaStore))
//            throw new RuntimeException(
//                "Can only handle IJenaStore's for now."
//            );
//
//        Model model = ((IJenaStore)store).getModel();
//        StringBuffer dump = new StringBuffer();
//
//        StmtIterator statements = model.listStatements();
//        while (statements.hasNext()) {
//            Statement statement = statements.nextStatement();
//            RDFNode object = statement.getObject();
//            dump.append(statement.getSubject().getLocalName())
//                .append(' ')
//                .append(statement.getPredicate().getLocalName())
//                .append(' ')
//                .append((object instanceof Resource ?
//                     object.toString() : '"' + object.toString() + "\""))
//                .append('\n');
//        }
//        return dump.toString();
//    }
//
//    public StringMatrix sparql(IRDFStore store, String queryString) throws IOException, Exception,
//    Exception {
//        if (!(store instanceof IJenaStore))
//            throw new RuntimeException(
//                "Can only handle IJenaStore's for now."
//            );
//
//        StringMatrix table = null;
//        Model model = ((IJenaStore)store).getModel();
//        Query query = QueryFactory.create(queryString);
//        PrefixMapping prefixMap = query.getPrefixMapping();
//        QueryExecution qexec = QueryExecutionFactory.create(query, model);
//        try {
//            ResultSet results = qexec.execSelect();
//            table = convertIntoTable(prefixMap, results);
//        } finally {
//            qexec.close();
//        }
//        return table;
//    }
//
//    private StringMatrix convertIntoTable(
//            PrefixMapping prefixMap, ResultSet results) {
//    	StringMatrix table = new StringMatrix();
//    	int rowCount = 0;
//        while (results.hasNext()) {
//        	rowCount++;
//            QuerySolution soln = results.nextSolution();
//            Iterator<String> varNames = soln.varNames();
//            while (varNames.hasNext()) {
//            	String varName = varNames.next();
//            	int colCount = -1;
//            	if (table.hasColumn(varName)) {
//            		colCount = table.getColumnNumber(varName);
//            	} else {
//            		colCount = table.getColumnCount() + 1;
//            		table.setColumnName(colCount, varName);
//            	}
//                RDFNode node = soln.get(varName);
//                if (node != null) {
//                    String nodeStr = node.toString();
//                    if (node.isResource()) {
//                        Resource resource = (Resource)node;
//                        // the resource.getLocalName() is not accurate, so I
//                        // use some custom code
//                        String[] uriLocalSplit = split(prefixMap, resource);
//                        if (uriLocalSplit[0] == null) {
//                        	if (resource.getURI() != null) {
//                        		table.set(rowCount, colCount, resource.getURI());
//                        	} else {
//                        		// anonymous node
//                        		table.set(rowCount, colCount, "" + resource.hashCode());
//                        	}
//                        } else {
//                        	table.set(rowCount, colCount,
//                                uriLocalSplit[0] + ":" + uriLocalSplit[1]
//                            );
//                        }
//                    } else {
//                    	if (nodeStr.endsWith("@en"))
//                    		nodeStr = nodeStr.substring(
//                    			0, nodeStr.lastIndexOf('@')
//                    		);
//                    	table.set(rowCount, colCount, nodeStr);
//                    }
//                }
//            }
//        }
//        return table;
//    }
//
//    /**
//     * Helper method that splits up a URI into a namespace and a local part.
//     * It uses the prefixMap to recognize namespaces, and replaces the
//     * namespace part by a prefix.
//     *
//     * @param prefixMap
//     * @param resource
//     */
//    public static String[] split(PrefixMapping prefixMap, Resource resource) {
//        String uri = resource.getURI();
//        if (uri == null) {
//            return new String[] {null, null};
//        }
//        Map<String,String> prefixMapMap = prefixMap.getNsPrefixMap();
//        Set<String> prefixes = prefixMapMap.keySet();
//        String[] split = { null, null };
//        for (String key : prefixes){
//            String ns = prefixMapMap.get(key);
//            if (uri.startsWith(ns)) {
//                split[0] = key;
//                split[1] = uri.substring(ns.length());
//                return split;
//            }
//        }
//        split[1] = uri;
//        return split;
//    }
//
//    public IRDFStore createInMemoryStore() {
//        return new JenaModel();
//    }
//
//    public IRDFStore createStore(String tripleStoreDirectoryPath) {
//    	return new TDBModel(tripleStoreDirectoryPath);
//    }
//
//    public void addObjectProperty(IRDFStore store,
//        String subject, String property, String object)
//        throws Exception {
//        if (!(store instanceof IJenaStore))
//            throw new RuntimeException(
//                "Can only handle IJenaStore's for now."
//            );
//        Model model = ((IJenaStore)store).getModel();
//        Resource subjectRes = model.createResource(subject);
//        Property propertyRes = model.createProperty(property);
//        Resource objectRes = model.createResource(object);
//        model.add(subjectRes, propertyRes, objectRes);
//    }
//
//    public void addDataProperty(IRDFStore store, String subject,
//            String property, String value) throws Exception {
//        if (!(store instanceof IJenaStore))
//            throw new RuntimeException(
//                "Can only handle IJenaStore's for now."
//            );
//        Model model = ((IJenaStore)store).getModel();
//        Resource subjectRes = model.createResource(subject);
//        Property propertyRes = model.createProperty(property);
//        model.add(subjectRes, propertyRes, value);
//    }
//
//    public long size(IRDFStore store) throws Exception {
//        if (!(store instanceof IJenaStore))
//            throw new RuntimeException(
//                "Can only handle IJenaStore's for now."
//            );
//        Model model = ((IJenaStore)store).getModel();
//        return model.size();
//    }
//
//    public void saveRDFXML(IRDFStore store, String fileName)
//        throws Exception {
//        File file = ResourcePathTransformer.getInstance().transform(fileName);
//        saveRDFXML(store, file, null);
//    };
//
//    public File saveRDFXML(IRDFStore store, File file,
//                            Hashtable monitor)
//        throws Exception {
//        return saveRDF(store, file, "RDF/XML-ABBREV", monitor);
//    }
//
//    public void saveRDFN3(IRDFStore store, String fileName)
//    throws Exception {
//        File file = ResourcePathTransformer.getInstance().transform(fileName);
//        saveRDFN3(store, file, null);
//    };
//
//    public File saveRDFN3(IRDFStore store, File file,
//            Hashtable monitor)
//    throws Exception {
//        return saveRDF(store, file, "N3", monitor);
//    }
//
//    public String asRDFN3(IRDFStore store)
//    throws Exception {
//        return asRDFN3(store, null);
//    };
//
//    public String asRDFN3(IRDFStore store, Hashtable monitor)
//    throws Exception {
//    	String type = "N3";
//
//    	if (monitor == null)
//    		monitor = new NullProgressMonitor();
//    	monitor.beginTask("Converting into N3", 1);
//
//    	try {
//    		ByteArrayOutputStream output = new ByteArrayOutputStream();
//    		if (store instanceof IJenaStore) {
//    			Model model = ((IJenaStore)store).getModel();
//    			model.write(output, type);
//    			output.close();
//    			String result = new String(output.toByteArray());
//    	    	monitor.worked(1);
//    	    	monitor.done();
//    	    	return result;
//    		} else {
//    			monitor.worked(1);
//    			monitor.done();
//    			throw new Exception("Only supporting IJenaStore!");
//    		}
//    	} catch (IOException e) {
//    		monitor.worked(1);
//    		monitor.done();
//    		throw new Exception("Error while writing RDF.", e);
//    	}
//    }
//
//    public void saveRDFNTriple(IRDFStore store, String fileName)
//        throws Exception {
//        File file = ResourcePathTransformer.getInstance().transform(fileName);
//        saveRDFNTriple(store, file, null);
//    };
//
//    public File saveRDFNTriple(IRDFStore store, File file,
//            Hashtable monitor)
//        throws Exception {
//        return saveRDF(store, file, "N-TRIPLE", monitor);
//    }
//
//    public File saveRDF(IRDFStore store, File file,
//                         String type,
//                         Hashtable monitor)
//        throws Exception {
//
//        if (type == null && !"RDF/XML-ABBREV".equals(type) &&
//                            !"N-TRIPLE".equals(type) &&
//                            !"N3".equals(type))
//            throw new Exception("Can only save RDF/XML-ABBREV, N3, " +
//            		"and N-TRIPLE.");
//
//        if (file.exists()) {
//            throw new Exception("File already exists!");
//        }
//        if (monitor == null)
//            monitor = new NullProgressMonitor();
//        monitor.beginTask("Writing file", 100);
//
//        try {
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            if (store instanceof IJenaStore) {
//                Model model = ((IJenaStore)store).getModel();
//                model.write(output, type);
//                output.close();
//                file.create(
//                        new ByteArrayInputStream(output.toByteArray()),
//                        false,
//                        monitor
//                );
//            } else {
//                monitor.worked(100);
//                monitor.done();
//                throw new Exception("Only supporting IJenaStore!");
//            }
//        } catch (Exception e) {
//            monitor.worked(100);
//            monitor.done();
//            throw new Exception("Error while writing RDF.", e);
//        } catch (IOException e) {
//            monitor.worked(100);
//            monitor.done();
//            throw new Exception("Error while writing RDF.", e);
//        }
//
//        monitor.worked(100);
//        monitor.done();
//        return file;
//    };
//
//    public void copy(IRDFStore targetStore, IRDFStore sourceStore)
//        throws Exception {
//        if (!(targetStore instanceof IJenaStore &&
//              sourceStore instanceof IJenaStore)) {
//            throw new Exception("Only supporting IJenaStore.");
//        }
//        ((IJenaStore)targetStore).getModel().add(
//            ((IJenaStore)sourceStore).getModel()
//        );
//    }
//
//    public void addPrefix(IRDFStore store, String prefix, String namespace)
//        throws Exception {
//        if (!(store instanceof IJenaStore)) {
//            throw new Exception("Only supporting IJenaStore.");
//        }
//        ((IJenaStore)store).getModel().setNsPrefix(prefix, namespace);
//    }
//
//    public StringMatrix sparqlRemote(
//           String serviceURL,
//           String sparqlQueryString, Hashtable monitor) {
//        if (monitor == null)
//            monitor = new NullProgressMonitor();
//
//        monitor.beginTask("Sparqling the remote service..", 100);
//        Query query = QueryFactory.create(sparqlQueryString);
//        monitor.worked(20);
//        QueryEngineHTTP qexec = (QueryEngineHTTP)QueryExecutionFactory.sparqlService(serviceURL, query);
//        qexec.addParam("timeout", "" + Activator.TIME_OUT);
//        PrefixMapping prefixMap = query.getPrefixMapping();
//        monitor.worked(60);
//
//        StringMatrix table = null;
//        try {
//            ResultSet results = qexec.execSelect();
//            table = convertIntoTable(prefixMap, results);
//        } finally {
//            qexec.close();
//        }
//        monitor.worked(20);
//        return table;
//    }
//    
//    public IRDFStore sparqlConstructRemote(
//    		String serviceURL,
//    		String sparqlQueryString, Hashtable monitor) 
//    throws Exception {
//    	if ( !sparqlQueryString.contains( "CONSTRUCT" ) ) 
//    		throw new Exception( "This method can only be used together " +
//    				"with CONSTRUCT queries." );
//    	if (monitor == null)
//    		monitor = new NullProgressMonitor();
//
//    	Model sparqlOutput = null;
//    	JenaModel rdfStore = null;
//
//    	monitor.beginTask("Sparqling the remote service..", 100);
//    	Query query = QueryFactory.create(sparqlQueryString);
//    	QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceURL, query);
//    	monitor.worked(80);
//
//    	try {
//    		sparqlOutput = qexec.execConstruct();
//        	monitor.worked(20);
//        	rdfStore = new JenaModel( sparqlOutput );
//    	} finally {
//    		qexec.close();
//        	monitor.done();
//    	}
//    	    	
//    	return rdfStore;
//    }
//    
//    public IRDFStore importRDFa(IRDFStore store, String url,
//            Hashtable monitor)
//    throws IOException, Exception {
//        new URL(url);
//        // if no exception was thrown,
//        // than the given url seems OK
//        
//        String pyrdfaURL = "http://www.w3.org/2007/08/pyRdfa/extract?uri="
//            + url;
//        return importURL(store, pyrdfaURL, monitor);
//    }
//    
//    public List<String> allClasses(IRDFStore store) throws Exception {
//    	try {
//			StringMatrix results = sparql(store,
//				"SELECT DISTINCT ?class WHERE {" +
//				" [] a ?class" +
//				"}"
//			);
//			return results.getColumn("class");
//		} catch (IOException exception) {
//			throw new Exception(
//			    "Could not query to store: " + exception.getMessage(),
//			    exception
//			);
//		} catch (Exception exception) {
//			throw new Exception(
//				"Could not query to store: " + exception.getMessage(),
//				exception
//			);
//		}
//    }
//
//    public List<String> allPredicates(IRDFStore store) throws Exception {
//    	try {
//			StringMatrix results = sparql(store,
//				"SELECT DISTINCT ?predicate WHERE {" +
//				" [] ?predicate []" +
//				"}"
//			);
//			return results.getColumn("predicate");
//		} catch (IOException exception) {
//			throw new Exception(
//			    "Could not query to store: " + exception.getMessage(),
//			    exception
//			);
//		} catch (Exception exception) {
//			throw new Exception(
//				"Could not query to store: " + exception.getMessage(),
//				exception
//			);
//		}
//    }
//
//    public List<String> allOwlSameAs(IRDFStore store, String resourceURI)
//    throws IOException, Exception {
//    	Set<String> resources = new HashSet<String>();
//    	resources.add(resourceURI);
//    	// implements a non-reasoning sameAs reasoner:
//    	// keep looking up sameAs relations, until we find no new ones
//    	List<String> newLeads = allOwlSameAsOneDown(store, resourceURI);
//    	newLeads.removeAll(resources); //
//    	while (newLeads.size() > 0) {
//    		List<String> newResources = new ArrayList<String>();
//        	for (String resource : newLeads) {
//        		System.out.println("Trying: " + resource);
//        		if (!resources.contains(resource)) {
//        			System.out.println("New: " + resource);
//        			resources.add(resource);
//        			newResources.addAll(
//        				allOwlSameAsOneDown(store, resource)
//        			);
//        		}
//        	}
//        	newResources.removeAll(resources);
//			newLeads = newResources;
//    	}
//    	List<String> finalList = new ArrayList<String>();
//    	finalList.addAll(resources);
//    	finalList.remove(resourceURI); // remove the source resource
//    	return finalList;
//    }
//    
//    public List<String> allOwlSameAsOneDown(IRDFStore store, String resourceURI)
//    throws IOException, Exception {
//    	// got no reasoner, so need implement inverse relation manually
//    	String sparql =
//    		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
//    		"SELECT ?resource WHERE {" +
//    		"  <" + resourceURI + "> owl:sameAs ?resource ." +
//    		"}";
//    	StringMatrix results = sparql(store, sparql);
//    	if (results.getRowCount() == 0) return Collections.emptyList();
//    	List<String> resources = results.getColumn("resource");
//    	sparql =
//    		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
//    		"SELECT ?resource WHERE {" +
//    		"  ?resource  owl:sameAs <" + resourceURI + ">." +
//    		"}";
//    	results = sparql(store, sparql);
//    	if (results.getRowCount() == 0) return resources;
//    	resources.addAll(results.getColumn("resource"));
//    	return resources;
//    }
//}