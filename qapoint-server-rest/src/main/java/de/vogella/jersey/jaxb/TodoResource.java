package de.vogella.jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.boun.ssw.client.ColdAnswer;

@Path("/todo/{question}/{username}/{location}")
public class TodoResource {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public ColdAnswer getJSON(@PathParam("question") String question, @PathParam("username") String username, @PathParam("location") String location) {
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

