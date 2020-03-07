package com.robsonliebke.harpia.users.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.robsonliebke.harpia.users.control.UserStore;
import com.robsonliebke.harpia.users.entity.User;

@Stateless
public class UsersService {

	@Inject
	private UserStore userStore;

	/**
	 * 
	 * Gets single {@link User} by username and password. Although, this method
	 * considers the uniqueness of username, in case there is more that one
	 * {@link User} matching the criteria passed, the first one is returned.
	 * 
	 * @param username
	 * @param password
	 * @return Single User that matches the parameters passed.
	 * @throws NotFoundException In case there is no {@link User} matching
	 *                           parameters passed.
	 */
	public User getUserByUserNameAndPassword(String username, String password) throws NotFoundException {
		// implement something generic here with some kind of filter
		return null;
	}
}
