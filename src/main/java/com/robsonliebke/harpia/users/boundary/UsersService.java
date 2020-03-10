package com.robsonliebke.harpia.users.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import com.robsonliebke.harpia.users.control.UsersStore;
import com.robsonliebke.harpia.users.entity.User;

/**
 * @author robsonliebke
 */
@Stateless
public class UsersService {
	@Inject
	private UsersStore usersStore;

	public User getUserByUsernameAndPassword(String username, String password) {
		return this.usersStore.getUserByUsernameAndPassword(username, password);
	}
}
