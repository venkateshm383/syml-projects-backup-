package controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import play.Logger;
import listner.AddressListner;
import listner.ApplicantListner;
import listner.AssetListner;
import listner.CollectionListner;
import listner.DealListner;
import listner.IncomeListener;
import listner.LeadListner;
import listner.LiabilityListner;
import listner.MortgageListner;
import listner.OpprtunityRecomendations;
import listner.PaymentLisnter;
import listner.PropertyListner;
import listner.ReferralResourceListner;
import listner.StageListner;

public class TrigerListener {
	private static org.slf4j.Logger logger = play.Logger.underlying();

	
static	Connection con1=null;
static	Connection con2=null;
static	Connection con3=null;
static	Connection con4=null;
static	Connection con5=null;
static	Connection con6=null;
static	Connection con7=null;
static	Connection con8=null;
static	Connection con9=null;
static	Connection con10=null;
static	Connection con11=null;
static	Connection con12=null;
static	Connection con13=null;
	
	public void callListner(){
		
		
		
		try {
			Class.forName("org.postgresql.Driver");
			
			
			Properties prop = new Properties();
			ArrayList<URI> nodes = new ArrayList<URI>();
			try {

				// getting connection parameter
				try {
					prop.load(Application.class.getClassLoader()
							.getResourceAsStream("config.properties"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("config.properties  Resource  Un-available"+e.getMessage());

				}

			} catch (IllegalStateException e) {
				logger.error("Class  Loading  Fails"+e.getMessage());
				
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
			 con2 = DriverManager.getConnection(url, userName,
					userPassword);
			 con3 = DriverManager.getConnection(url, userName,
					userPassword);
			 con4 =DriverManager.getConnection(url, userName,
					userPassword);
			 con5 = DriverManager.getConnection(url, userName,
					userPassword);
			 con6 = DriverManager.getConnection(url, userName,
					userPassword);
			 con7 = DriverManager.getConnection(url, userName,
					userPassword);
			 con8 = DriverManager.getConnection(url, userName,
					userPassword);
			 con9 = DriverManager.getConnection(url, userName,
					userPassword);
			 con10 = DriverManager.getConnection(url, userName,
					userPassword);
			 con11 = DriverManager.getConnection(url, userName,
					userPassword);

			 con12 = DriverManager.getConnection(url, userName,
					userPassword);
			
			 con13 = DriverManager.getConnection(url, userName,
					userPassword);
			}catch(SQLException e){
					try{
				 url =prop.getProperty("postgresURL1");
				 userName=prop.getProperty("postgresUserName1");	
			 userPassword=prop.getProperty("postgresPassword1");
			 
				// work fine with just one connection.
			 con1 = DriverManager.getConnection(url, userName,
					userPassword);
			 con2 = DriverManager.getConnection(url, userName,
					userPassword);
			 con3 = DriverManager.getConnection(url, userName,
					userPassword);
			 con4 =DriverManager.getConnection(url, userName,
					userPassword);
			 con5 = DriverManager.getConnection(url, userName,
					userPassword);
			 con6 = DriverManager.getConnection(url, userName,
					userPassword);
			 con7 = DriverManager.getConnection(url, userName,
					userPassword);
			 con8 = DriverManager.getConnection(url, userName,
					userPassword);
			 con9 = DriverManager.getConnection(url, userName,
					userPassword);
			 con10 = DriverManager.getConnection(url, userName,
					userPassword);
			 con11 = DriverManager.getConnection(url, userName,
					userPassword);

			 con12 = DriverManager.getConnection(url, userName,
					userPassword);
			
			 con13 = DriverManager.getConnection(url, userName,
					userPassword);
					}catch(SQLException e11){
						logger.error("Connection  Not  success"+e11.getMessage());
						
					}
			}
			// Create two threads, one to issue notifications and
			// the other to receive them.
			ApplicantListner applicantListner = new ApplicantListner(con1);
			
			AssetListner assetListner = new AssetListner(con2);
			CollectionListner collectionListner = new CollectionListner(con3);
			IncomeListener incomeListener = new IncomeListener(con4);
			LiabilityListner liabilityListner = new LiabilityListner(con5);
			MortgageListner mortgageListner = new MortgageListner(con6);
			OpprtunityRecomendations opprtunityRecomendations = new OpprtunityRecomendations(
					con7);
			PropertyListner propertyListner = new PropertyListner(con8);
			ReferralResourceListner referralResourceListner = new ReferralResourceListner(
					con9);
			PaymentLisnter paymentLisnter=new PaymentLisnter(con10);
			LeadListner  leadListner=new LeadListner(con11);
			AddressListner addressListner=new AddressListner(con12);
			DealListner dealListner = new DealListner(con13);
			// Notifier notifier = new Notifier(nConn);
			collectionListner.start();
			
			applicantListner.start();
			assetListner.start();
			incomeListener.start();
			liabilityListner.start();
			opprtunityRecomendations.start();
			propertyListner.start();
			referralResourceListner.start();
		    paymentLisnter.start();
			leadListner.start();
			addressListner.start();
			mortgageListner.start();
			dealListner.start();
			 //notifier.start();
		
		} catch (IllegalThreadStateException e1) {
			
			logger.error("IllegalThreadStateException  Occurs  while  starting  Thread"+e1.getMessage());

		}catch (SQLException e2) {
			logger.error("SQL  Exception  Occurs  while  getting  Connection"+e2.getMessage());
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			logger.error("Connection  Class  Not  found"+e1.getMessage());
		}
	}
	
	public void closeDbConnection(){
		
			try {
				con1.close();
			
			con2.close();
			con3.close();
			con4.close();
			con5.close();
			con6.close();
			con7.close();
			con8.close();
			con9.close();
			con10.close();
			con11.close();
			con12.close();
			con13.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Connection  Closing  error"+e.getMessage());
			}
	}
}
