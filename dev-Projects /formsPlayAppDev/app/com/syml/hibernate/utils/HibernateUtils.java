package com.syml.hibernate.utils;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import play.Logger;

import com.syml.Constants;

public class HibernateUtils {
	
	private static  SessionFactory SESSION_FACTORY ;
	
	public static  synchronized  SessionFactory getSessionFactory(){
	
		
	if(SESSION_FACTORY == null){
		Logger.debug("Before creating SESSION_FACTORY "+ SESSION_FACTORY);
		SESSION_FACTORY = new Configuration().configure(Constants.HIBERNET_CONFIG_FILE).buildSessionFactory();
		Logger.debug("After creating SESSION_FACTORY "+ SESSION_FACTORY);
	}
	
	return SESSION_FACTORY;	
	}
	 
}
