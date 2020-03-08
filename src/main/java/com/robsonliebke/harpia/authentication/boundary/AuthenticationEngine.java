package com.robsonliebke.harpia.authentication.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
public class AuthenticationEngine {

	@Inject
	private AuthenticationService authenticationService;

	@POST
	public Response authenticate(@FormParam("username") String username, @FormParam("password") String password) {

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Required parameter is missing.").build();
		}

		this.authenticationService.authenticate(username, password);
		
		

		return Response.ok().build();
	}
}