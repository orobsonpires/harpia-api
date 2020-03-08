package com.robsonliebke.harpia.authentication.boundary;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.robsonliebke.harpia.users.boundary.UsersService;

/**
 * @author robsonliebke
 *
 */
public class AuthenticationService {
	
	private static final Logger logger = LogManager.getLogger(AuthenticationService.class);
	
	@Inject
	private UsersService usersService;

	public void authenticate(String username, String password) {
		
	}

}
