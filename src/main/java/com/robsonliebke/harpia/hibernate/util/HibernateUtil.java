package com.robsonliebke.harpia.hibernate.util;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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
public class HibernateUtil {
	private static volatile SessionFactory sessionFactory; // NOSONAR
	private static final Object mutex = new Object();
	private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

	private HibernateUtil() {
	}

	public static SessionFactory getSessionFactory() {
		SessionFactory sessionFactoryLocal = sessionFactory;

		if (sessionFactoryLocal != null) {
			return sessionFactoryLocal;
		}

		synchronized (mutex) { // NOSONAR
			if (sessionFactory == null) {
				try {
					final Configuration configuration = new Configuration();

					Properties settings = new Properties();
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
			}

			return sessionFactory;
		}
	}
}