package com.robsonliebke.harpia.users.control;

import java.util.List;

import javax.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.robsonliebke.harpia.users.entity.User;
import com.robsonliebke.harpia.util.HibernateUtil;

/**
 * @author robsonliebke
 *
 */
@Stateless
public class UsersStore {
	public User getUserByUsernameAndPassword(String username, String password) {
		if (username == null || password == null) {
			return null;
		}
		try (final Session session = HibernateUtil.getSessionFactoryInstance().openSession()) {

			final Query<User> query = session
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
}
