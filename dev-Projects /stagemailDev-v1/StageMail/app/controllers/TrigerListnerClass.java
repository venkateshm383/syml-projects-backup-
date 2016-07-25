package controllers;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

public class TrigerListnerClass {
	
	
	static Connection con1=null;

	public void callListner(){
		
		
		
		try {
			Class.forName("org.postgresql.Driver");
			
			
			Properties prop = new Properties();
			ArrayList<URI> nodes = new ArrayList<URI>();
			try {

				// getting connection parameter
				prop.load(Application.class.getClassLoader()
						.getResourceAsStream("config.properties"));

			} catch (Exception e) {
				
			}

			String url =prop.getProperty("postgresURL");
			String userName=prop.getProperty("postgresUserName");	
			String userPassword=prop.getProperty("postgresPassword");
			// Create two distinct connections, one for the notifier
			// and another for the listener to show the communication
			// works across connections although this example would
			// work fine with just one connection.
			 con1 = DriverManager.getConnection(url, userName,
					userPassword);
			/*
			Connection con2 = DriverManager.getConnection(url, userName,
					userPassword);

			Connection con3 = DriverManager.getConnection(url, userName,
					userPassword);
			ReferralListner referralListner=new ReferralListner(con1);*/
			/*LeadStageListner leadStageListner=new LeadStageListner(con1);

			
			LeadListener leadListener=new LeadListener(con2);
			
			//referralListner.start();
			//leadListener.start();
			leadStageListner.start();*/
		}catch(Exception e){
			
		}
	
	
	
	
	
	}
	
	public void closeDbConnection(){
		try{
			con1.close();
		}catch(Exception e){
			
		}
	}

	
	
}
