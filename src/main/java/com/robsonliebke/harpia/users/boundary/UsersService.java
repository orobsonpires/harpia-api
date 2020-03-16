package com.robsonliebke.harpia.users.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.robsonliebke.harpia.exceptions.ApplicationException;
import com.robsonliebke.harpia.users.control.UsersStore;
import com.robsonliebke.harpia.users.entity.User;

/**
 * @author robsonliebke
 */
@Stateless
public class UsersService {

	private static final Logger logger = LogManager.getLogger(UsersService.class);

	@Inject
	private UsersStore usersStore;

	public UsersService() {
		super();
		logger.debug("New UserService instance is created.");
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		try {
			return this.usersStore.getUserByUsernameAndPassword(username, password);
		} catch (Exception e) {
			throw new ApplicationException(Status.INTERNAL_SERVER_ERROR, "Ops! Something went wrong.", e);
		}
	}

	public User findByUserName(String username) {
		try {
			return this.usersStore.getUserByUserName(username);
		} catch (Exception e) {
			throw new ApplicationException(Status.INTERNAL_SERVER_ERROR, "Ops! Something went wrong.", e);
		}
	}
}
