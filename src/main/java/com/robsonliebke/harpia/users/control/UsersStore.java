package com.robsonliebke.harpia.users.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

		final TypedQuery<User> query = em
				.createQuery("FROM User u WHERE u.username = :username AND u.username = :password", User.class);

		query.setParameter("username", username);
		query.setParameter("password", password);

		final List<User> users = query.getResultList();

		if (!users.isEmpty()) {
			return users.get(0);
		}

		return null;

	}
}
