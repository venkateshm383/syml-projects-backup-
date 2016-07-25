package controllers;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

import play.Logger;

public class GetPostgressConnection {

	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	public static Connection getPostGressConnection(){
		
		Connection con1=null;
		

		try {
			Class.forName("org.postgresql.Driver");
			
			
			Properties prop = new Properties();
			ArrayList<URI> nodes = new ArrayList<URI>();
			try {

				// getting connection parameter
				prop.load(Application.class.getClassLoader()
						.getResourceAsStream("config.properties"));

			} catch (Exception e) {
				 logger.error("Error occured while configure the class "+e.getMessage());	
			}

			String url =prop.getProperty("postgresURL");
			String userName=prop.getProperty("postgresUserName");	
			String userPassword=prop.getProperty("postgresPassword");
			// Create two distinct connections, one for the notifier
			// and another for the listener to show the communication
			// works across connections although this example would
			try{
					
		
			// work fine with just one connection.
			 con1 = DriverManager.getConnection(url, userName,
					userPassword);
		
			}catch(Exception e){
				try{
					 url =prop.getProperty("postgresURL1");
					 userName=prop.getProperty("postgresUserName1");	
				 userPassword=prop.getProperty("postgresPassword1");
				 con1 = DriverManager.getConnection(url, userName,
							userPassword);
				}catch(Exception e1){
					 logger.error("Error occured while connecting to Postgress"+e.getMessage());	
				}
				logger.error("Error occured while get connection with Postgress"+e.getMessage());	
			}
			
		}catch(Exception e){
			logger.error("Error occured while connecting to Postgress"+e.getMessage());		
		}
		
		return con1;
	}
	
	
	
}
