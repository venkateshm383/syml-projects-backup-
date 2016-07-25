package com.syml.util;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
		
		UnderwriteAppConfig config = UnderwriteAppConfig.getInstance();
		final String url = "jdbc:postgresql://" + config.getDbURL();
		final String username = config.getDbUsername();
		final String password = config.getDbPassword();
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	
        	Properties hibernateProperties = new Properties();
        	hibernateProperties.setProperty("hibernate.connection.url", url);
        	hibernateProperties.setProperty("hibernate.connection.username", username);
        	hibernateProperties.setProperty("hibernate.connection.password", password);
        	hibernateProperties.setProperty("hibernate.format_sql", "false");
        	hibernateProperties.setProperty("hibernate.show_sql", "false");

			AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
        	annotationConfiguration.setProperties(hibernateProperties);
        	return annotationConfiguration.configure().buildSessionFactory();
        	}
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

}
