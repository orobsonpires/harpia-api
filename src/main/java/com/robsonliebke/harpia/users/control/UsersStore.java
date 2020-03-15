package com.robsonliebke.harpia.users.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Null;
import com.robsonliebke.harpia.users.entity.User;

/**
 * @author robsonliebke
 */
@Stateless
public class UsersStore {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Queries {@link User} by username and password, and return single user if
	 * parameters match.
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return single user in case parameters match, otherwise {@link Null}.
	 */
	public User getUserByUsernameAndPassword(String username, String password) {
		if (username == null || password == null) {
			return null;
		}

		return em.createQuery("FROM User u WHERE u.username = :username AND u.username = :password", User.class)
				.setParameter("username", username).setParameter("password", password).getResultStream().findAny()
				.orElse(null);
	}
}
