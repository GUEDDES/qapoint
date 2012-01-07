package de.vogella.jersey.jaxb;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.boun.ssw.client.User;
import edu.boun.ssw.client.UserList;
import edu.boun.ssw.tdb.dataAccess;

@Path("/qapoint/getsimilarusers/{username}")
public class SimilarUserService {
	// This method is called if XMLis request
	@GET
	@Produces( { MediaType.APPLICATION_JSON })
	public UserList getJSON(@PathParam("username") String username) {

		// get questions from TDB
		ArrayList<User> users =  dataAccess.dbAccess.getSimilarUsers(username);

		//FIXME
		User user = new User();
		user.setUsername("");
		users.add(user);
		users.add(user);
		//FIXME
		
		UserList userList = new UserList();
		userList.setUserList(users);
		
		return userList;
	}
	
}
