package controllers;


import helper.Opprtunity;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.google.gson.JsonObject;



public class Odoo extends Thread {
	
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	static Session openERPSession=null;
	
	
	public Session getOdooConnectionForProduction() {
		String ip = null;
		int port = 0;
		String host = null;
		String db = null;
		String user = null;
		String pass = null;
		

		try {

			Properties prop = readConfigfile();

			ip = prop.getProperty("odooIP");
			try{
			port = new Integer(prop.getProperty("odooPort"));
			}catch(Exception e){
				 logger.error("Error occured while configure with odoo "+e.getMessage());			
			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername");
			pass = prop.getProperty("odoopassword");

			openERPSession = new Session(ip, port, db, user, pass);
			openERPSession.startSession();

		} catch (Exception e) {
			 logger.error("Error occured in odoo connection "+e.getMessage());
		}

		return openERPSession;
	}

	// connection useed for production -----------------
		public Session getOdooConnection() {
			String ip = null;
			int port = 0;
			String host = null;
			String db = null;
			String user = null;
			String pass = null;
			int maximumRetry = 0;
			try {

				
				logger.info("inside getOdooConnection method of Generic Helper  class");
				Properties prop = readConfigfile();
                logger.info("stg---"+prop.getProperty("submitReferralstageId")+"---"+prop.getProperty("visdomreferralStageId")+"----"+prop.getProperty("opprtunitySatgeid"));
				// ip=prop.getProperty("odooIP");
				host = prop.getProperty("odooHost1");
				try{
				port = new Integer(prop.getProperty("odooPort"));
				}catch(Exception e){
			 logger.error("Error occured in odoo connection "+e.getMessage());
				}
				db = prop.getProperty("odooDB");
				user = prop.getProperty("odooUsername1");
				pass = prop.getProperty("odoopassword1");
				maximumRetry = new Integer(prop.getProperty("maximumRetry"));
			logger.info("");

				openERPSession = getCRMConnectionOne(host, port, db, user, pass,maximumRetry);
				if (openERPSession == null) {
				logger.info("error in getCRMConnectionOne");
					host = prop.getProperty("odooHost2");
					port = new Integer(prop.getProperty("odooPort2"));
					db = prop.getProperty("odooDB2");
					user = prop.getProperty("odooUsername2");
					pass = prop.getProperty("odoopassword2");
					

					openERPSession = getCRMConnectionTwo(host, port, db, user,
							pass, maximumRetry);

					if (openERPSession == null) {
					logger.info("error in getCRMConnectionTwo");
					} else {
					logger.info("connection successful");
					}

				}

			} catch (Exception e) {

			logger.error("error in connectiong with odoo : "+e.getMessage());
			}

			return openERPSession;
		}

		public Session getCRMConnectionOne(String host, int port, String db,
				String user, String pass, int maximumRetry) {

		logger.info("inside GetCRMConectionOne  method of GenericHelperClass");
			int retry = 1;
			Session openERPSession = null;
			while (retry <= maximumRetry && openERPSession == null) {
				try {
					openERPSession = new Session(host, port, db, user, pass);
					openERPSession.startSession();
				} catch (Exception e) {
					openERPSession = null;
				logger.info(" GetCRMConectionOne  Retry..    " + retry);
					retry += 1;
				logger.error("error in connecting GetCRMConectionOne "+e.getMessage());
				}
			}
			return openERPSession;
		}

		public Session getCRMConnectionTwo(String host, int port, String db,
				String user, String pass, int maximumRetry) {

		logger.info("inside GetCRMConectionTwo method of GenericHelperClass");
			int retry = 1;
			Session openERPSession = null;
			while (retry <= maximumRetry && openERPSession == null) {
				try {
					openERPSession = new Session(host, port, db, user, pass);
					openERPSession.startSession();
				} catch (Exception e) {
					openERPSession = null;
				logger.info(" GetCRMConectionTwo  Retry..    " + retry);
					retry += 1;
				logger.error("error in connecting GetCRMConectionTwo "+e.getMessage());
				}

			}
			return openERPSession;
		}
	
	public static ArrayList getReferralname(String opprtunityId){

	RowCollection referralList=null;
	ArrayList list=new ArrayList();
	try{
		
	Session	openERPSession=new Odoo().getOdooConnection();
	
	ObjectAdapter lead=openERPSession.getObjectAdapter("crm.lead");

	FilterCollection filters2=new FilterCollection();
	filters2.add("id","=",opprtunityId);
    
RowCollection   leadRow= lead.searchAndReadObject(filters2, new String[]{"id","referred_source"});
  
	
	
	for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
		Row row1 = (Row) iterator.next();
		

	
	ObjectAdapter referral=openERPSession.getObjectAdapter("hr.applicant");

	

    		
    			FilterCollection filters1=new FilterCollection();
				filters1.add("id","=",row1.get("referred_source"));
			    
			   referralList= referral.searchAndReadObject(filters1, new String[]{"email_from","name","role"});
			   logger.info(referralList.size()+"id");
    	
    
    
   
    for (Row row : referralList){
		list.add(row.get("name").toString());
		list.add(row.get("email_from").toString());
		list.add(row.get("role").toString());
	}
	}}catch(Exception e){
		logger.error("error while processing getReferralname of arraylist "+e.getMessage());
	}
	
	return list;
	}
	public Properties readConfigfile() {

		Properties prop = new Properties();
		try {
			prop.load(Odoo.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (Exception e) {
			logger.error("error while processing readConfigfile of arraylist "+e.getMessage());
		}
		return prop;
	}

	public ArrayList<OpportunityList> getOpprunityDetials(){
		ArrayList<OpportunityList> list=new ArrayList<OpportunityList>();
		try{
			
			Session	openERPSession=new Odoo().getOdooConnection();
			
			ObjectAdapter lead=openERPSession.getObjectAdapter("crm.lead");

			FilterCollection filters2=new FilterCollection();
		
		    
		RowCollection   leadRow= lead.searchAndReadObject(filters2, new String[]{"id","name","create_date"});
		  
			
			
			for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
				Row row1 = (Row) iterator.next();
			
			/*	int        days=0;
			    Calendar calendar1=Calendar.getInstance();
			    Calendar calendar2=Calendar.getInstance();
			    Date dateString=(Date) row1.get("create_date");
			    calendar1.setTime(dateString);
			        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
			      String date2=formatter.format(calendar2.getTime());
			      String date1=formatter.format(calendar1.getTime());
			     // logger.debug("date 1"+ date2  +"dat3 "+ date1);
			     days=(int)( (formatter.parse(date2).getTime() - formatter.parse(date1).getTime())/ (1000 * 60 * 60 * 24) );
			      

			if(days<200){*/
				
				OpportunityList opportunityList=new OpportunityList();
				try{
				opportunityList.setOpportunityId(row1.get("id").toString());
				opportunityList.setOpportunityName(row1.get("name").toString());
				list.add(opportunityList);
				}catch(Exception e){
					logger.error("error in getting the opporunity names and by adding list "+e.getMessage());

				}
			}
				
	/*	}*/}catch(Exception e){
		logger.error("error in getting the opporunity names "+e.getMessage());

			}
			return list;
	}
	
	public int getOpprunityID(String opporunityName){
		int id=0;
		try{
			Session	openERPSession=new Odoo().getOdooConnection();
			
			ObjectAdapter lead=openERPSession.getObjectAdapter("crm.lead");

			FilterCollection filters2=new FilterCollection();
		RowCollection   leadRow= lead.searchAndReadObject(filters2, new String[]{"id","name","create_date"});
		  for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
				Row row1 = (Row) iterator.next();
			
		/*		int        days=0;
			    Calendar calendar1=Calendar.getInstance();
			    Calendar calendar2=Calendar.getInstance();
			    Date dateString=(Date) row1.get("create_date");
			    calendar1.setTime(dateString);
			        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
			      String date2=formatter.format(calendar2.getTime());
			      String date1=formatter.format(calendar1.getTime());
			     // logger.debug("date 1"+ date2  +"dat3 "+ date1);
			     days=(int)( (formatter.parse(date2).getTime() - formatter.parse(date1).getTime())/ (1000 * 60 * 60 * 24) );
			      

			if(days<200){*/
				
			
				try{
					logger.info(opporunityName  +"  == "+row1.get("name").toString());
					if(opporunityName.trim().equalsIgnoreCase(row1.get("name").toString())){
						logger.info("exists");
						
						id=new Integer(row1.get("id").toString());
						break;
					}
						
				
				}catch(Exception e){
					logger.error("error in getting the opporunity names and by adding list "+e.getMessage());
				}
			}
				
		/*}*/}catch(Exception e){
			logger.error("error in getting the opporunity names "+e.getMessage());
			}
			
			return id;
	}
	
		public Opprtunity getOpprtunityApplicantData(String  name)
		{	
		Opprtunity opprunity=new Opprtunity();
		int id =getOpprunityID(name);
		try {
			Session openERPSession=	new Odoo().getOdooConnection();
		logger.info("Seesion is"+openERPSession);
		
	
		ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
		
		FilterCollection filter=new FilterCollection();
		filter.add("id", "=", id);
		RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","app_rec_ids","name","email_from"});
		Row row1=row.get(0);
		
		opprunity.setOpprtunityName(row1.get("name").toString());
		opprunity.setOpprtunityEmail(row1.get("email_from").toString());
		opprunity.setOpprtunityId(row1.get("id").toString());

		Object [] object=(Object[])row1.get("app_rec_ids");
	logger.info("Object is "+object);
		Set<Applicants> setApplicnats=null;
		ArrayList<Applicants> setApplicanList=new ArrayList<Applicants>();
	for (Object object2 : object) {
		try{
	logger.info("inside for Of Object");

		
	ObjectAdapter applicants=openERPSession.getObjectAdapter("applicant.record");
	logger.info(object2.toString());
		FilterCollection filter1=new FilterCollection();
		filter1.add("id", "=", object2.toString());
		RowCollection row2=applicants.searchAndReadObject(filter1,new String[]{"applicant_name","applicant_last_name","email_personal"});
		

		for (Row row3 : row2){
			Applicants applicant=new Applicants();

		 logger.info("Row ID: " + row3.getID());
		 logger.info("Name:" + row3.get("applicant_name"));
		 logger.info("ApplicantLast_Name:" + row3.get("applicant_last_name"));
		 logger.info("Email:" + row3.get("email_personal"));
			applicant.setApplicantFirstName(row3.get("applicant_name").toString());
			applicant.setApplicantId(object2.toString());
			applicant.setApplicantEmail(row3.get("email_personal").toString());

			setApplicanList.add(applicant);


		}

		
	logger.info("Applicant added");
		}catch(Exception e){
			logger.error("error while processing getOpprtunityApplicantData "+e.getMessage());
		}
		
		
	}
	setApplicnats =new LinkedHashSet<Applicants>(setApplicanList);
	opprunity.setApplicants(setApplicnats);

	} catch (Exception e) {
		logger.error("error while processing getOpprtunityApplicantData "+e.getMessage());
	}
		return opprunity;
		}

	
	
	public String getOpprtunityName(String  id){
			
    String opporunityName="";
		try {
			Session openERPSession=	new Odoo().getOdooConnection();
		logger.info("Seesion is"+openERPSession);
		ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
		
		FilterCollection filter=new FilterCollection();
		filter.add("id", "=", id);
		RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","name","email_from"});
		Row row1=row.get(0);
		opporunityName=row1.get("name").toString();
		} catch (Exception e) {
		logger.error("error while processing getOpprtunityApplicantData "+e.getMessage());
	}
			
			return opporunityName;
		}

	
	public int createContact(String name, String lastname, String email,

	String module) {

		int resId = 0;
		ObjectAdapter hrApplicant;
		try {
			openERPSession = getOdooConnection();
			// openERPSession.startSession();
			hrApplicant = openERPSession.getObjectAdapter(module);

			Row newResPartner = hrApplicant.getNewRow(new String[] { "name",
					"last_name", "email" });
			newResPartner.put("name", name);
			newResPartner.put("last_name", lastname);
			newResPartner.put("email", email);
			hrApplicant.createObject(newResPartner);
			resId = newResPartner.getID();
		} catch (Exception e) {
			// TODO Auto-generated catch block
        logger.error("error in creating contact"+e.getMessage());
		}
		return resId;
	}
	
	
	public int createReferral(String name, String email,int id,String module)
	{
				int resId = 0;
				ObjectAdapter hrApplicant;
				try {
					openERPSession = getOdooConnection();
					// openERPSession.startSession();
					hrApplicant = openERPSession.getObjectAdapter(module);

					Row newResPartner = hrApplicant.getNewRow(new String[] {"name",
							"email_from", "partner_id","stage_id","role" });
					newResPartner.put("name", name);
					newResPartner.put("email_from", email);
					newResPartner.put("partner_id", id);
					newResPartner.put("role", "realtor");

					hrApplicant.createObject(newResPartner);
					resId = newResPartner.getID();
					FilterCollection filter=new FilterCollection();
					filter.add("id", "=",resId );
					
					
					RowCollection rowcolletion=hrApplicant.searchAndReadObject(filter, new String[] {"name",
							"email_from", "partner_id","stage_id" });
					Row row=rowcolletion.get(0);
					row.put("stage_id", 6);
					hrApplicant.writeObject(row, true);

				} catch (Exception e) {
					// TODO Auto-generated catch block
				logger.error("error in creating referral"+e.getMessage());
				}
				return resId;
			}
	
	
	public boolean UpdateOpprtunityData(String  opportunityName,int referralId,String applicantEmailId){
		boolean sucess=false;
		try {
			Session openERPSession=	new Odoo().getOdooConnection();
			int id=	getOpprunityID(opportunityName);
					
					logger.info("opportunity found is id="+id +" with name ="+opportunityName);

		logger.info("inside UpdateOpprtunityData method assigning referral and applicant to opporunity  and opporunit name = "+opportunityName);
		ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
		
		FilterCollection filter=new FilterCollection();
		filter.add("id", "=", id);
		RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","app_rec_ids","referred_source","name","email_from"});
	
		logger.info("number of opporunitys fond size is "+row.size());
		
		Row row1=row.get(0);
		
		row1.put("referred_source", referralId);
		opprtunity.writeObject(row1, true);
	logger.info("updated with referral id "+opportunityName);
	FilterCollection filter2=new FilterCollection();

	filter2.add("id", "=", id);
		RowCollection row5=opprtunity.searchAndReadObject(filter2,new String[]{"id","app_rec_ids","referred_source","name","email_from"});
		Row row15=row5.get(0);
	
	logger.info("updated with referral id "+opportunityName);

		Object [] object=(Object[])row15.get("app_rec_ids");
		
	logger.info("applicant id"+object);
		for (int i=0;i<object.length;i++) {
			try{
			logger.info("applicant id"+object[i]);
         ObjectAdapter applicants=openERPSession.getObjectAdapter("applicant.record");
			FilterCollection filter1=new FilterCollection();
			filter1.add("id", "=", object[i]);
			RowCollection row2=applicants.searchAndReadObject(filter1,new String[]{"applicant_name","applicant_last_name","email_personal"});
			

			for (Row row3 : row2){

			 logger.info("Row ID: " + row3.getID());
			 logger.info("Name:" + row3.get("applicant_name"));
			 logger.info("ApplicantLast_Name:" + row3.get("applicant_last_name"));
			 logger.info("Email:" + row3.get("email_personal"));
				
				row3.put("email_personal",applicantEmailId);

				applicants.writeObject(row3, true);
            sucess=true;
    	logger.info("updated with Applicant email id "+applicantEmailId);

			}
			}catch(Exception e){
				logger.error("error while processing UpdateOpprtunityData"+e.getMessage());
			}
		}
	    }catch(Exception e){
		logger.error("error in updating applicant"+e.getMessage());
       }
		return sucess;
		}
	
	public static void main(String[] args) {
		getOpprunityData("4389");
	}
	
	public static JSONObject  getOpprunityData(String opporunityName){
	Properties prop = new Properties();
	ArrayList<URI> nodes = new ArrayList<URI>();
	Connection conn=null;
	try {

		// getting connection parameter
		prop.load(Odoo.class.getClassLoader()
				.getResourceAsStream("config.properties"));

	} catch (Exception e) {
		logger.error("error while processing JSONObject getOpprunityData "+e.getMessage());
	}

	String url = prop.getProperty("postgresURL");
	String userName = prop.getProperty("postgresUserName");
	String userPassword = prop.getProperty("postgresPassword");

	try {
		Class.forName("org.postgresql.Driver");

		// Create two distinct connections, one for the notifier
		// and another for the listener to show the communication
		// works across connections although this example would
		// work fine with just one connection.
		conn = DriverManager.getConnection(url, userName, userPassword);
	} catch (Exception e) {
		try {
			url = prop.getProperty("postgresURL");
			userName = prop.getProperty("postgresUserName");
			userPassword = prop.getProperty("postgresPassword");

			conn = DriverManager.getConnection(url, userName,
					userPassword);
			// TODO: handle exception
		} catch (Exception e11) {
			logger.error("error while processing JSONObject getOpprunityData "+e11.getMessage());
		}
		
	logger.error("error in connecting  postgress "+e.getMessage());
	}
	JSONObject opporunityData=null;
	try {
		Statement stmtRealtor = conn.createStatement();

		ResultSet rsRealtor = stmtRealtor
				.executeQuery("select row_to_json(crm_lead) as crm_lead from crm_lead where id="
						+opporunityName );
		// log.debug("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
		while (rsRealtor.next()) {
			logger.info("*******opportunity  Data**************");
			opporunityData = new JSONObject(
					rsRealtor.getString("crm_lead"));
			logger.debug("*******opportunity  Data**************"+opporunityData);

		}
		stmtRealtor.close();
		rsRealtor.close();
	} catch (Exception e) {
	logger.error("error in getting crm data "+e.getMessage());
	}
	return opporunityData;
	
	}
		
	public static String getOpprtunityID(String  name){
		
		String opporunityId="";
				try {
					Session openERPSession=	new Odoo().getOdooConnection();
				logger.info("Seesion is"+openERPSession);
				ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
				
				FilterCollection filter=new FilterCollection();
				filter.add("name", "=", name);
				RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","name","email_from"});
				Row row1=row.get(0);
				opporunityId=row1.get("id").toString();
				} catch (Exception e) {
				logger.error("error while processing getOpprtunityID"+e.getMessage());
			}
					
					return opporunityId;
				}
}
