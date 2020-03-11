package com.robsonliebke.harpia.users.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
		return this.usersStore.getUserByUsernameAndPassword(username, password);
	}
}
