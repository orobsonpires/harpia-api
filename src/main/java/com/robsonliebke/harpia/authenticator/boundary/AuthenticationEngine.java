/**
 * 
 */
package com.robsonliebke.harpia.authenticator.boundary;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.robsonliebke.harpia.authenticator.entity.Credential;

/**
 * 
 * Boundary api to authenticate actors.
 * 
 * @author robsonliebke
 *
 */
@Path("auth")
public class AuthenticationEngine {
	
	private static final Logger logger = LogManager.getLogger(AuthenticationEngine.class);
	
	@Inject
	AuthenticationService authenticationService;
	    
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(@FormParam("json") String credentialJson) {
		
		final Jsonb jsonb = JsonbBuilder.create(); //NOSONAR
		
		final Credential credential = jsonb.fromJson(credentialJson,
				Credential.class);
		
		if(authenticationService.validateCredential(credential) && authenticationService.authenticate(credential)) {
			
		}
		
		logger.debug("AuthenticationInfo sent: {}", credential);
		
		return Response.ok().build();
	}
}
