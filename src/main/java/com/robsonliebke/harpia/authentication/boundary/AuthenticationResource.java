package com.robsonliebke.harpia.authentication.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.robsonliebke.harpia.exceptions.ApplicationException;
import com.robsonliebke.harpia.users.boundary.UsersService;
import com.robsonliebke.harpia.users.entity.User;

/**
 * 
 * Boundary api to authenticate actors/rest adapter.
 * 
 * @author robsonliebke
 *
 */
@Path("auth")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

	@Inject
	private UsersService userService;

	private static final Logger logger = LogManager.getLogger(AuthenticationResource.class);

	@POST
	public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {

		logger.info("got a authentication  request");

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Required parameter is missing.").build();
		}

		final User user = this.userService.getUserByUsernameAndPassword(username, password);

		if (user == null) {
			throw new ApplicationException(Status.NOT_FOUND,
					"Incorrect username or password. Ensure that the username and password included in the request are correct.");
		}

		logger.info("The user '{}' was sucessfully authenticated.", user.getUsername());

		return Response.ok().build();
	}

}