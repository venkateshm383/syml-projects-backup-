package controllers;

import infrastracture.CouchBaseOperation;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

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
import listner.PropertyListner;
import listner.ReferralResourceListner;
import listner.StageListner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.mvc.*;



import views.html.*;

public class Application extends Controller {

	public static Result index() {
/*
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
			Connection con1 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con2 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con3 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con4 =DriverManager.getConnection(url, userName,
					userPassword);
			Connection con5 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con6 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con7 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con8 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con9 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con10 = DriverManager.getConnection(url, userName,
					userPassword);
			Connection con11 = DriverManager.getConnection(url, userName,
					userPassword);

			Connection con12 = DriverManager.getConnection(url, userName,
					userPassword);
			
			Connection con13 = DriverManager.getConnection(url, userName,
					userPassword);
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
			StageListner stageListner = new StageListner(con10);
			LeadListner  leadListner=new LeadListner(con11);
			AddressListner addressListner=new AddressListner(con12);
			DealListner dealListner = new DealListner(con13);
			// Notifier notifier = new Notifier(nConn);
			applicantListner.start();
			assetListner.start();
			collectionListner.start();
			incomeListener.start();
			liabilityListner.start();
			opprtunityRecomendations.start();
			propertyListner.start();
			referralResourceListner.start();
			stageListner.start();
			leadListner.start();
			addressListner.start();
			mortgageListner.start();
			// notifier.start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		return ok(index.render("Hi"));
	}

}
