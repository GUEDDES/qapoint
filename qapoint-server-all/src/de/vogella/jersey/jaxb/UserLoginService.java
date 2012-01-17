package de.vogella.jersey.jaxb;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.boun.ssw.client.LoginResponse;
import edu.boun.ssw.tdb.dataAccess;

@Path("/qapoint/loginuser/{username}/{password}")
public class UserLoginService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public LoginResponse getJSON(@PathParam("username") String username, @PathParam("password") String password ) {

		// get questions from TDB
		Boolean isValidUser =  dataAccess.dbAccess.logIn(username, password);
		LoginResponse resp = new LoginResponse();
		if(isValidUser){
			resp.setValidUser(1);
		} else {
			resp.setValidUser(0);
		}
		
		return resp;
	}
	
}
