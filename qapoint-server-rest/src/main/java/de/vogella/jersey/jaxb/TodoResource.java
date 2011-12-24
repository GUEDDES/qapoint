package de.vogella.jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import de.vogella.jersey.jaxb.model.Book;
import de.vogella.jersey.jaxb.model.Todo;
import edu.boun.ssw.client.ColdAnswer;

@Path("/todo")
public class TodoResource {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public ColdAnswer getJSON(@PathParam("todo") String question) {
		ColdAnswer coldAnswer = new ColdAnswer();
		coldAnswer.setAnswer("Toscana is cool! Worth to try.");
		coldAnswer.setUsername("Tugce");
		
		return coldAnswer;
	}
	
//	@GET
//	@Produces( { MediaType.APPLICATION_XML })
//	public Todo getXML() {
//		Todo todo = new Todo();
//		todo.setSummary("This is my first todo");
//		todo.setDescription("This is my first todo");
//		return todo;
//	}
//	
//	// This can be used to test the integration with the browser
//	@GET
//	@Produces( { MediaType.TEXT_XML })
//	public Todo getHTML() {
//		Todo todo = new Todo();
//		todo.setSummary("This is my first todo");
//		todo.setDescription("This is my first todo");
//		return todo;
//	}

}

