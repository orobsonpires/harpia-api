package com.robsonliebke.harpia.hibernate.util;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.runner.notification.RunListener.ThreadSafe;

import static org.hibernate.cfg.Environment.DRIVER;
import static org.hibernate.cfg.Environment.URL;
import static org.hibernate.cfg.Environment.USER;
import static org.hibernate.cfg.Environment.PASS;
import static org.hibernate.cfg.Environment.SHOW_SQL;
import static org.hibernate.cfg.Environment.DIALECT;
import static org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS;
import static org.hibernate.cfg.Environment.HBM2DDL_AUTO;

import com.robsonliebke.harpia.users.entity.User;

/**
 * 
 * Hibernate utility class, which also contains the hibernate settings
 * equivalent to hibernate.cfg.xml's properties
 * 
 * @author robsonliebke
 *
 */
@ThreadSafe
public class HibernateUtil {
	private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

	private HibernateUtil() {
	}

	/**
	 * @return singleton instance of {@link SessionFactory}
	 */
	public static SessionFactory getSessionFactoryInstance() {
		return LazyHolder.sessionFactory;
	}

	/**
	 * 
	 * 
	 * Initialization on demand holder, a way to initialize singleton objects that
	 * enables a safe, highly concurrent lazy initialization of static fields with
	 * good performance.
	 * 
	 * @see https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
	 *      https://rules.sonarsource.com/java/tag/multi-threading/RSPEC-2168
	 *      https://stackoverflow.com/questions/1625118/java-double-checked-locking
	 *      http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
	 *
	 */
	private static class LazyHolder {
		private static final SessionFactory sessionFactory = initializeInstance(); // This will be lazily initialized

		private static SessionFactory initializeInstance() {
			SessionFactory sessionFactory = null;

			try {
				final Configuration configuration = new Configuration();

				final Properties settings = new Properties();
				settings.put(DRIVER, "oracle.jdbc.OracleDriver");
				settings.put(URL, "jdbc:oracle:thin:@localhost:1521:xe");
				settings.put(USER, "harpia");
				settings.put(PASS, "admin");
				settings.put(DIALECT, "org.hibernate.dialect.OracleDialect");
				settings.put(SHOW_SQL, "true");
				settings.put(CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(HBM2DDL_AUTO, "create-drop");
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(User.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (HibernateException e) {
				logger.error("The session factory could not be initialized.");
			}

			return sessionFactory;
		}

	}
}