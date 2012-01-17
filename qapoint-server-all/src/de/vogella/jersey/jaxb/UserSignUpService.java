package de.vogella.jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.boun.ssw.client.User;
import edu.boun.ssw.tdb.dataAccess;

@Path("/qapoint/signupuser/{username}/{password}")
public class UserSignUpService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public User getJSON(@PathParam("username") String username, @PathParam("password") String password ) {

		// get questions from TDB
		dataAccess.dbAccess.addUser(username, password);
		return new User(username);
	}
}
