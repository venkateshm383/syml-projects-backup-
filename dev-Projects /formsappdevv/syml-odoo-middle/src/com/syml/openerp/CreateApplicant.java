package com.syml.openerp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.syml.address.splitAddress.Address;
import com.syml.addressgroup.pojo.Incomes;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class CreateApplicant {
	Session openERPSession =null;
	static	Logger log = LoggerFactory.getLogger(CreateLead.class);
	SendEmail mail=new SendEmail();
Incomes income=new Incomes();
GenericHelperClass gHelperClass=new GenericHelperClass();
/*
	public Session getOdooConnection(){
		try{

			log.info("inside getOdooConnection method of CreateLead class");

			SymlConstant symlConstant=new SymlConstant();
			String ip=symlConstant.getOdooIP();
			int port=symlConstant.getOdooPort();
			String db=symlConstant.getOdooDB();
			String user=symlConstant.getOdooUsername();
			String pass=symlConstant.getOdoopassword();
			openERPSession = new Session(ip,port,db,user,pass);

			log.info("connection successful");

		}catch(Exception e){
			log.error("error in connectiong with odoo : "+e);
		}

		return openERPSession;
	}*/
	int id=0;
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public void createApplicant(String applicantFirstName,String applicantLastName,String applicantEmail){
		openERPSession = gHelperClass.getOdooConnection();
		try {

			log.debug("inside try");
			////openERPSession.startSession();
			ObjectAdapter applicantAd = openERPSession
					.getObjectAdapter("applicant.record");

			Row newPartner = applicantAd.getNewRow(new String[]{"applicant_name", "applicant_last_name","email_personal"});
			newPartner.put("applicant_name", applicantFirstName);
			newPartner.put("applicant_last_name", applicantLastName);
			newPartner.put("email_personal", applicantEmail);

			applicantAd.createObject(newPartner);
			log.debug("Record created");
			log.debug("New Row ID: " + newPartner.getID());
			id=newPartner.getID();

			


		}catch (Exception e) {
			log.info("Applicant Creation Exception");	}

	}

	public void createApplicantAddress(String applicantId,String name ,String city,String province,String postalcode, Date date){
		openERPSession = gHelperClass.getOdooConnection();
		try {
			

			log.debug("inside try");
			////openERPSession.startSession();
		       ObjectAdapter applicantAd1 = openERPSession.getObjectAdapter("applicant.address");
		       Row applicantRow = applicantAd1.getNewRow(new String[]{"name", "street", "city","province","postal_code","date","applicant_id"});
		       applicantRow.put("name",name);
		       applicantRow.put("city",city);
		       applicantRow.put("province",province);
		       applicantRow.put("postal_code",postalcode);

		       applicantRow.put("date", date);
		       applicantRow.put("applicant_id",applicantId);

		     	applicantAd1.createObject(applicantRow);


		}catch (Exception e) {
			log.debug("Applicant Creation Exception");	
			}

	}
	
	
	
	public void createApplicantMortgageRental(String applicant_id,String name,int propertycounter,String selling,String rental){
		  openERPSession = gHelperClass.getOdooConnection();
		  try {
		   log.debug("inside create Applicant mortgage method");
		  // //openERPSession.startSession();

		   ObjectAdapter propertyAd3 = openERPSession.getObjectAdapter("applicant.mortgage");

		   if(selling.equalsIgnoreCase("yes")){
		   
		   Boolean sellingBool=true;
		   log.debug("sellingBoll value : "+sellingBool);

		   
		   if(propertycounter == 0){
		    
		    log.debug("when mortgage set to true and selling is false");

		    
		    int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
		    income.setPropertyId(Integer.toString(propertyCounterLocal));
		    log.debug("--- setting income property id inside createApplicantMortgageRental--- "+income.getPropertyId());
		    
		     Row ad1 = propertyAd3.getNewRow(new String[]{"name","monthly_rent","selling","property_id","applicant_id"});
		      ad1.put("name", name);
		      ad1.put("monthly_rent", rental);
		      ad1.put("selling", sellingBool);
		    ad1.put("property_id", propertycounter);
		    ad1.put("applicant_id",applicant_id);

		     propertyAd3.createObject(ad1);
		      
		       log.debug("Mortgage Created");
		    
		   }else {
		    
		    log.debug("when mortgage set to true and selling is true");
		    Row ad1 = propertyAd3.getNewRow(new String[]{"name","monthly_rent","selling","property_id","applicant_id"});
		     ad1.put("name", name);
		     ad1.put("monthly_rent", rental);
		   ad1.put("selling", sellingBool);
		   ad1.put("property_id", propertycounter);
		   ad1.put("applicant_id",applicant_id);

		    propertyAd3.createObject(ad1);
		     
		      log.debug("Mortgage Created");
		      
		   }
		   }else{
		    Boolean sellingBool=false;

		    log.debug("sellingBoll value : "+sellingBool);
		    if(propertycounter == 0){
		     
		     log.debug("when mortgage set to true and selling is false");
		     int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
		      Row ad1 = propertyAd3.getNewRow(new String[]{"name","monthly_rent","selling","property_id","applicant_id"});
		       ad1.put("name", name);
		       ad1.put("monthly_rent", rental);
		     ad1.put("selling", sellingBool);
		     ad1.put("property_id", propertycounter);
		     ad1.put("applicant_id",applicant_id);

		      propertyAd3.createObject(ad1);
		       
		        log.debug("Mortgage Created");
		     
		    }else {
		     
		     log.debug("when mortgage set to true and selling is true");

		     
		    
		     
		      
		      Row ad1 = propertyAd3.getNewRow(new String[]{"name","monthly_rent","selling","property_id","applicant_id"});
		      ad1.put("name", name);
		      ad1.put("monthly_rent", rental);
		    ad1.put("selling", sellingBool);
		    ad1.put("property_id", propertycounter);
		    ad1.put("applicant_id",applicant_id);

		     propertyAd3.createObject(ad1);
		      
		       log.debug("Mortgage Created");
		       
		    }
		    
		   }
		   }catch (Exception e) {
		   log.info("Applicant Creation Exception for Mortgage"); }

		 }
	
	
	
public void createApplicantMortgage(String applicant_id,String name,int property_id,String selling){
		openERPSession = gHelperClass.getOdooConnection();
		try {
			log.debug("inside create Applicant mortgage method");
			////openERPSession.startSession();

			ObjectAdapter propertyAd3 = openERPSession.getObjectAdapter("applicant.mortgage");

			if(selling.equalsIgnoreCase("yes")){
			
			Boolean sellingBool=true;
			log.debug("sellingBoll value : "+sellingBool);

			
			if(property_id == 0){
				
				log.debug("when mortgage set to true and selling is false");

				
				int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
				 Row ad1 = propertyAd3.getNewRow(new String[]{"name","selling","property_id","applicant_id"});
				  ad1.put("name", name);
				ad1.put("selling", sellingBool);
				ad1.put("property_id", propertyCounterLocal);
				ad1.put("applicant_id",applicant_id);

					propertyAd3.createObject(ad1);
				  
					  log.debug("Mortgage Created");
				
			}else {
				
				log.debug("when mortgage set to true and selling is true");
 Row ad1 = propertyAd3.getNewRow(new String[]{"name","selling","property_id","applicant_id"});
			  ad1.put("name", name);
			ad1.put("selling", sellingBool);
			ad1.put("property_id", property_id);
			ad1.put("applicant_id",applicant_id);

				propertyAd3.createObject(ad1);
			  
				  log.debug("Mortgage Created");
				  
			}
			}else{
				Boolean sellingBool=false;

				log.debug("sellingBoll value : "+sellingBool);
				if(property_id == 0){
					
					log.debug("when mortgage set to true and selling is false");
int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
					 Row ad1 = propertyAd3.getNewRow(new String[]{"name","selling","property_id","applicant_id"});
					  ad1.put("name", name);
					ad1.put("selling", sellingBool);
					ad1.put("property_id", propertyCounterLocal);
					ad1.put("applicant_id",applicant_id);

						propertyAd3.createObject(ad1);
					  
						  log.debug("Mortgage Created");
					
				}else {
					
					log.debug("when mortgage set to true and selling is true");

					
				
				 
				  
				  Row ad1 = propertyAd3.getNewRow(new String[]{"name","selling","property_id","applicant_id"});
				  ad1.put("name", name);
				ad1.put("selling", sellingBool);
				ad1.put("property_id", property_id);
				ad1.put("applicant_id",applicant_id);

					propertyAd3.createObject(ad1);
				  
					  log.debug("Mortgage Created");
					  
				}
				
			}
			}catch (Exception e) {
			log.info("Applicant Creation Exception for Mortgage");	}

	}

	
	
	public void createApplicantProperties(String applicant_id,String name,String mo_condo_fees,int property_id,String selling){
		openERPSession = gHelperClass.getOdooConnection();
		try {
			log.debug("inside create Applicant Properties method");
			////openERPSession.startSession();
			ObjectAdapter propertyAd2 = openERPSession.getObjectAdapter("applicant.property");
			
			
			if(selling.equalsIgnoreCase("yes")){
				
			
		
			Boolean sellingBool=true;
			
			log.debug("sellingBool value : "+sellingBool);
			
			if(property_id == 0){
				log.debug("when mortgage set to false and selling is true");
				int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
				
				if(mo_condo_fees.equalsIgnoreCase("-1")){
				log.debug("when condo fee is false");
				  Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


				ad.put("name", name);
				ad.put("property_id", propertyCounterLocal);
				ad.put("selling", sellingBool);
				ad.put("applicant_id",applicant_id);
				propertyAd2.createObject(ad);
				  
				  log.debug("Income Created");
				}else{
					
					log.debug("when condo fee is true");
					Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


					ad.put("name", name);
					ad.put("mo_condo_fees", mo_condo_fees);
					ad.put("property_id", propertyCounterLocal);
					ad.put("selling", sellingBool);
					ad.put("applicant_id",applicant_id);
					propertyAd2.createObject(ad);
					  
					  log.debug("Income Created");
				}

			}else {
			
			log.debug("when mortgage set to true and selling is true");
			
			if(mo_condo_fees.equalsIgnoreCase("-1")){
				log.debug("when condo fee is false");
				  Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


				ad.put("name", name);
				ad.put("property_id", property_id);
				ad.put("selling", sellingBool);
				ad.put("applicant_id",applicant_id);
				propertyAd2.createObject(ad);
				  
				  log.debug("Income Created");
				}else{
					
					log.debug("when condo fee is true");
					Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


					ad.put("name", name);
					ad.put("mo_condo_fees", mo_condo_fees);
					ad.put("property_id", property_id);
					ad.put("selling", sellingBool);
					ad.put("applicant_id",applicant_id);
					propertyAd2.createObject(ad);
					  
					  log.debug("Income Created");
				}

			}
			}else{

				Boolean sellingBool=false;
				
				log.debug("sellingBool value : "+sellingBool);
				
				if(property_id == 0){
					log.debug("when mortgage set to false and selling is true");
					int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
					
					if(mo_condo_fees.equalsIgnoreCase("-1")){
					log.debug("when condo fee is false");
					  Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


					ad.put("name", name);
					ad.put("property_id", propertyCounterLocal);
					ad.put("selling", sellingBool);
					ad.put("applicant_id",applicant_id);
					propertyAd2.createObject(ad);
					  
					  log.debug("Income Created");
					}else{
						
						log.debug("when condo fee is true");
						Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


						ad.put("name", name);
						ad.put("mo_condo_fees", mo_condo_fees);
						ad.put("property_id", propertyCounterLocal);
						ad.put("selling", sellingBool);
						ad.put("applicant_id",applicant_id);
						propertyAd2.createObject(ad);
						  
						  log.debug("Income Created");
					}

				}else {
				
				log.debug("when mortgage set to true and selling is true");
				
				if(mo_condo_fees.equalsIgnoreCase("-1")){
					log.debug("when condo fee is false");
					  Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


					ad.put("name", name);
					ad.put("property_id", property_id);
					ad.put("selling", sellingBool);
					ad.put("applicant_id",applicant_id);
					propertyAd2.createObject(ad);
					  
					  log.debug("Income Created");
					}else{
						
						log.debug("when condo fee is true");
						Row ad = propertyAd2.getNewRow(new String[]{"name","mo_condo_fees","property_id","selling","applicant_id"});


						ad.put("name", name);
						ad.put("mo_condo_fees", mo_condo_fees);
						ad.put("property_id", property_id);
						ad.put("selling", sellingBool);
						ad.put("applicant_id",applicant_id);
						propertyAd2.createObject(ad);
						  
						  log.debug("Income Created");
					}

				}
				
				
			}
			
			}catch (Exception e) {
			log.info("Applicant Creation Exception for Properties");	}

	}


	public void updateApplicant(String applicantID,String mobilePhone,String workPhone,String homePhone,String insurance,String relationshipStatus,boolean nonResident,Date date){
		openERPSession = gHelperClass.getOdooConnection();
		try {

			log.info("inside try updateApplicant"+applicantID);
			////openERPSession.startSession();
			ObjectAdapter applicantAd = openERPSession
					.getObjectAdapter("applicant.record");

			FilterCollection applicantfilters = new FilterCollection();
			applicantfilters.add("id","=",applicantID);
			RowCollection partners = applicantAd.searchAndReadObject(applicantfilters, new String[]{"cell","work","home","sin","relationship_status","non_resident","dob"});

			Row applicantRow = partners.get(0);

			applicantRow.put("cell", mobilePhone);
			applicantRow.put("work", workPhone);
			applicantRow.put("home", homePhone);
			applicantRow.put("sin", insurance);
			applicantRow.put("relationship_status", relationshipStatus);
			
			applicantRow.put("non_resident", nonResident);
			applicantRow.put("dob", date);


			//id=applicantRow.getID();
			// Tell writeObject to only write changes ie the name isn't updated because it wasn't changed.
			boolean success = applicantAd.writeObject(applicantRow, true);

			if (success)
				System.out.println("Update was successful");
		}catch (Exception e) {
			e.printStackTrace();
			log.info("Applicant updating Exception");	}

	}
	
	
	
	
	public void updateApplicantIfNotCanad(String applicantID,String mobilePhone,String relationshipStatus,Date date){
		openERPSession = gHelperClass.getOdooConnection();
		try {

			log.info("inside try updateApplicant");
			//openERPSession.startSession();
			ObjectAdapter applicantAd = openERPSession
					.getObjectAdapter("applicant.record");

			FilterCollection applicantfilters = new FilterCollection();
			applicantfilters.add("id","=",applicantID);
			RowCollection partners = applicantAd.searchAndReadObject(applicantfilters, new String[]{"cell","dob","relationship_status"});

			Row applicantRow = partners.get(0);
			   applicantRow.put("dob", date);
			applicantRow.put("cell", mobilePhone);
			
			applicantRow.put("relationship_status", relationshipStatus);
			id=applicantRow.getID();
			// Tell writeObject to only write changes ie the name isn't updated because it wasn't changed.
			boolean success = applicantAd.writeObject(applicantRow, true);

			if (success)
				System.out.println("Update was successful");
		}catch (Exception e) {
			log.info("Applicant updating Exception");	}

	}
	
	
	
	
	
	
	
	

	public void createIncomeApplicant(String applicantID,int income_type,String business,String position,int month,boolean supplementary){
		openERPSession = gHelperClass.getOdooConnection();
		try {

			log.info("inside OpoenERP createIncomeApplicant");
			//openERPSession.startSession();
			
			ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("income.employer");

			Row ad = applicantAd2.getNewRow(new String[]{"income_type","business","position","industry","annual_income","historical","month","supplementary","property_id","applicant_id"});
				log.debug("serching Applicant row fileds");
				//int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
			  
			  ad.put("income_type", income_type);
				ad.put("business", business);
				ad.put("position", position);
				ad.put("month", month);
				ad.put("supplementary", supplementary);
				//ad.put("property_id", propertyCounterLocal);
				ad.put("applicant_id", applicantID);

				applicantAd2.createObject(ad);
			  
			  log.debug("Income Created successfully");
			
		}catch (Exception e) {
			e.printStackTrace();			}
	}
	
	
	//Method OverLoeading For Appnding Historical Data
	public void createIncomeApplicant(String applicantID,int income_type,String business,String position,int month,boolean supplementary,boolean history){
		openERPSession = gHelperClass.getOdooConnection();
		try {

			log.info("inside OpoenERP createIncomeApplicant");
			//openERPSession.startSession();
			
			ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("income.employer");

			Row ad = applicantAd2.getNewRow(new String[]{"income_type","business","position","industry","annual_income","historical","month","supplementary","property_id","applicant_id"});
				log.debug("serching Applicant row fileds");
				//int propertyCounterLocal = GenericHelperClass.getPropertyCounter();
			  
			  ad.put("income_type", income_type);
				ad.put("business", business);
				ad.put("position", position);
				ad.put("month", month);
				ad.put("supplementary", supplementary);
				ad.put("historical", history);
				ad.put("applicant_id", applicantID);

				applicantAd2.createObject(ad);
			  
			  log.debug("Income Created successfully");
			
		}catch (Exception e) {
			e.printStackTrace();			}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void createIncomeApplicantRental(String applicantID,int income_type,String business,String position,String annualIncome,boolean historical,boolean supplementary,int propertycounter){
		  openERPSession = gHelperClass.getOdooConnection();
		  try {

		   log.info("inside OpoenERP createIncomeApplicant");
		   //openERPSession.startSession();
		   
		   ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("income.employer");

		   Row ad = applicantAd2.getNewRow(new String[]{"income_type","business","position","industry","annual_income","historical","month","supplementary","property_id","applicant_id"});
		    log.debug("Renatl applicant field");
		    String propertyCounterLocal = income.getPropertyId();
		     log.debug("propertyCounterLocal inside createIncomeApplicantRental : "+propertyCounterLocal);
		     ad.put("income_type", income_type);
		    ad.put("business", business);
		    ad.put("position", position);
		    ad.put("annual_income", annualIncome);
		    ad.put("historical", historical);
		    ad.put("supplementary", supplementary);
		    ad.put("property_id", propertycounter);
		    ad.put("applicant_id", applicantID);

		    applicantAd2.createObject(ad);
		     
		     log.debug("Rental Income Created successfully");
		   
		  }catch (Exception e) {
		   e.printStackTrace();   }
		 }	
public void createAssetApplicant(String applicantId,String asset_Type,String name,String value){
		openERPSession = gHelperClass.getOdooConnection();
		try {

			log.info("inside try updateApplicant");
			//openERPSession.startSession();
			ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("crm.asset");

           Row ad = applicantAd2.getNewRow(new String[]{"asset_type", "name","value","opportunity_id"});
				float f=10.0f;
			  
				ad.put("asset_type",asset_Type);
				ad.put("name",name);
				ad.put("value", value);
				ad.put("opportunity_id", applicantId);

				applicantAd2.createObject(ad);
			  
			  log.debug("Assets Created for applicant");
		}catch (Exception e) {
			e.printStackTrace();			}
	}
//logic for Change to Applicant



public void createApplicantTOLead(String applicantId,String email,String name){
	openERPSession = gHelperClass.getOdooConnection();
	try {

		log.info("inside try updateApplicant");
		//openERPSession.startSession();
		ObjectAdapter lead = openERPSession.getObjectAdapter("crm.lead");
		
		
		FilterCollection filters11 = new FilterCollection();
		 // filters11.add("customer","=",true);
		
		  filters11.add("email_from","=",email);

       Row ad = lead.getNewRow(new String[]{"name","email_from"});
			
		  
			ad.put("asset_type",email);
			ad.put("name",name);
			ad.put("email_from",email);


			lead.createObject(ad);
		  
		  log.debug("Assets Created for applicant");
	}catch (Exception e) {
		e.printStackTrace();			}
}


public static void main(String[] args) {
	//new CreateApplicant().assignOppertunity(340, 1, 1, 1, 1, 1, 1, 1, 268);
//new CreateApplicant().assignOppertunityPreApproval(340, 268, "1",200000+"", 2+"", 1+"", 0+"", 2+"", 7+"", 0+"",5+"", 800000+"");
	new CreateApplicant().assignOppertunity(341, 0,0,0,0,0,0,0, 268);


}
//Methods Oveloading

public void assignOppertunity(int applicantId,int jobFiveYear,int incomeDcreased,int futureFamily,int buyNewVehicale,int lifeStyleChnage,int financialRiskLetter,int properrtyLessThanFiveyear,int leadId ){
	openERPSession = gHelperClass.getOdooConnection();

	try{

	log.debug("inside OpenERP AssignOppertunity");
	  //openERPSession.startSession();
		log.debug("startSession");

		ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("applicant.record");
		ObjectAdapter applicantAd3 = openERPSession.getObjectAdapter("crm.lead");
		
			log.debug("Using Many2Many fieleds");
			
			com.debortoliwines.openerp.api.FilterCollection filters1 = new com.debortoliwines.openerp.api.FilterCollection();
			filters1.add("id","=",applicantId);
			com.debortoliwines.openerp.api.RowCollection partners1 = applicantAd2.searchAndReadObject(filters1, new String[]{"applicant_name","id","opp_rec_ids"});

			com.debortoliwines.openerp.api.Row TestRow = partners1.get(0);
			TestRow.put("id", applicantId);
			TestRow.putMany2ManyValue("opp_rec_ids",new Object[]{leadId},false);
			
	applicantAd2.writeObject(TestRow, false);
			
			log.debug("Oppertunity created");
			log.debug("lead id "+ leadId  +"aplicant id"+ applicantId);
			
			com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
			filters11.add("id","=",leadId);
			com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"job_5_years","income_decreased_worried","future_family","buy_new_vehicle","lifestyle_change","financial_risk_taker","property_less_then_5_years","id","stage_id","type"});

		for (Row r:partners11){
			if(jobFiveYear!=0){
				log.debug("inside for loop" + jobFiveYear   +"  "+incomeDcreased+"  "+futureFamily+"  "+ buyNewVehicale+" "+ lifeStyleChnage+" "+financialRiskLetter+""+properrtyLessThanFiveyear);
			r.put("job_5_years", jobFiveYear);
			r.put("income_decreased_worried", incomeDcreased);
			r.put("future_family", futureFamily);
			r.put("buy_new_vehicle", buyNewVehicale);
			r.put("lifestyle_change", lifeStyleChnage);
			r.put("financial_risk_taker", financialRiskLetter);
			r.put("property_less_then_5_years",properrtyLessThanFiveyear);
			}
			//r.put("stage_id", 15);
			
			r.put("stage_id", 11);
			r.put("type","opportunity");
			r.put("id", leadId);

			applicantAd3.writeObject(r, true);
			log.debug("Lead with Year updated for current Applicant and lead changed to opporutnity");
			
		
			

			
			
		}
		
}catch(Exception e){
	e.printStackTrace();
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}






public void assignOppertunity(int applicantId,int userValues){
	openERPSession = gHelperClass.getOdooConnection();

	try{

	log.debug("inside OpenERP AssignOppertunity");
	  //openERPSession.startSession();
		log.debug("startSession");

		ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("applicant.record");
		ObjectAdapter applicantAd3 = openERPSession.getObjectAdapter("crm.lead");
		FilterCollection filters = new FilterCollection();
		filters.add("applicant_name","=","vikash");	
		filters.add("applicant_last_name","=","singh");	
		filters.add("email_personal","=","vi@gmail.com");	

		log.debug("Filter");

		RowCollection partners = applicantAd2.searchAndReadObject(filters, new String[]{"applicant_name","applicant_last_name", "email_personal", "opp_rec_ids"});
		log.debug("partners.size"+partners.size());
		for (Row row : partners){
			log.debug("ApplicantName"+row.get("applicant_name"));
			row.get("applicant_last_name");
			Object [] opp_rec_ids= (Object[]) row.get("opp_rec_ids");
			if (opp_rec_ids != null){
			    System.out.println("opp_rec_ids:"+opp_rec_ids.length);
			RowCollection partners1 = applicantAd3.readObject(opp_rec_ids, new String[]{"name", "email_from"});
			for(Row r : partners1){
				log.debug("name in crm : "+r.get("name"));
				log.debug("email from crm :"+r.get("email_from"));
				
				
				
			}
			FilterCollection filterlead = new FilterCollection();
			filterlead.add("id","=",4088);	
			RowCollection partners2 = applicantAd3.searchAndReadObject(filterlead, new String[]{"name","partner_id ","email_from"});
			String leadname=null;
			int partnerid=0;
			String emaillead=null;
			for(Row ro : partners2){
				leadname=(String)ro.get("name");
				//partnerid=(int)ro.get("partner_id");
				emaillead=(String)ro.get("email_from");
				log.debug("name in crmlead : "+leadname+" partnerid : "+partnerid+" emaillead : "+emaillead);
				

			}
			
			log.debug("write into applicant");
			Row newopps=applicantAd2.getNewRow(new String[]{"name", "email_from"});
			newopps.put("name",leadname);
			newopps.put("email_from",emaillead);
			
			
			applicantAd2.createObject(newopps);
			System.out.println("opp created");
			

			Object obj[] = new Object[5];
			obj[0]="4088";
			RowCollection partners3 = applicantAd2.searchAndReadObject(filters, new String[]{"applicant_name","applicant_last_name", "email_personal", "opp_rec_ids"});
			log.debug("patner3.size"+partners3.size());
			Row updateopps=partners3.get(0);
			System.out.println("row size"+row.getID());
			updateopps.put("applicant_name","dimple");
		updateopps.put("applicant_last_name","shaame");
			
			
			updateopps.put("opp_rec_ids", 4088);

			
			boolean succ=applicantAd2.writeObject(updateopps,true);
			System.out.println("opps create d1"+succ);
			

			
			
			
			}else{
				
				Object obj[] = new Object[5];
				obj[0]="4088";
				RowCollection partners3 = applicantAd2.searchAndReadObject(filters, new String[]{"applicant_name","applicant_last_name", "email_personal", "opp_rec_ids"});
				System.out.println("patner3.size"+partners3.size());
				Row updateopps=partners3.get(0);
				System.out.println("row size"+row.getID());
				
				
				updateopps.put("id", "2155");
				
				updateopps.put("opp_rec_ids", "3345");

				
			//updateopps.put("app_rec_ids", 3657);
				boolean succ=applicantAd2.writeObject(updateopps,true);

			System.out.println("opps create d1"+succ);
				
			FilterCollection filtersLead = new FilterCollection();
			filtersLead.add("id","=",4135);	
			
			log.debug("Using Many2Many fieleds");
			
			com.debortoliwines.openerp.api.FilterCollection filters1 = new com.debortoliwines.openerp.api.FilterCollection();
			filters1.add("id","=",applicantId);
			com.debortoliwines.openerp.api.RowCollection partners1 = applicantAd2.searchAndReadObject(filters1, new String[]{"applicant_name","id","opp_rec_ids"});

			com.debortoliwines.openerp.api.Row TestRow = partners1.get(0);
			TestRow.put("id", applicantId);
			TestRow.putMany2ManyValue("opp_rec_ids",new Object[]{"4135"},false);
			
	applicantAd2.writeObject(TestRow, false);
			
			log.debug("Oppertunity created");
			
			
			com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
			filters11.add("id","=",4135);
			com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"job_5_years","income_decreased_worried","future_family","buy_new_vehicle","lifestyle_change","financial_risk_taker","property_less_then_5_years","id"});

		for (Row r:partners11){
			
			r.put("job_5_years", userValues);
			r.put("income_decreased_worried", userValues);
			r.put("future_family", userValues);
			r.put("buy_new_vehicle", userValues);
			r.put("lifestyle_change", userValues);
			r.put("financial_risk_taker", userValues);
			r.put("property_less_then_5_years", userValues);

			
			r.put("id", 4135);

			applicantAd3.writeObject(r, true);
			log.debug("Lead with Year updated for current Applicant");
			
		}
			}

			
			
		}
		
}catch(Exception e){
	e.printStackTrace();
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

public  void updateOpportunity(int leadId,String propertystyle,String propertytype,String propertyHeating,String propertyWater,String propertySewage,String propertygarage,String propertygaragesize,String yesno,String square_footage ){
	
	
	try{
	
	int propertyStyleId=0;
	switch (propertystyle) {
	case "Bunglow":propertyStyleId=1;
		
		break;
	case "One Story":propertyStyleId=1;
	
	break;
	case "BiLevel":propertyStyleId=2;
	
	break;
	case "Two Story":propertyStyleId=3;

	break;
	case "Split Level":propertyStyleId=4;

	break;
	
	case "Story and A Half":propertyStyleId=5;

	break;
	case "Three Story":propertyStyleId=6;

	break;
	
	
	default:propertyStyleId=7;
		break;
	}
	
	int propertyTyeId = 0;
	// property type
	switch (propertytype) {
	case "House":propertyTyeId=1;
		
		break;
	case "Duplex":propertyTyeId=2;
	
	break;
	case "Appartment Condo":propertyTyeId=4;
	
	break;
	case "Townhouse":propertyTyeId=5;

	break;
	case "Rowhouse":propertyTyeId=5;

	break;
	
	case "Mobile Home":propertyTyeId=6;

	break;
	case "Modular Home":propertyTyeId=6;

	break;
	
	
	default:propertyTyeId=7;
		break;
	}
	
	int propertyHeatId = 0;
	// property type
	switch (propertyHeating) {
	case "Furnace":propertyHeatId=1;
		
		break;
	case "Electric":propertyHeatId=2;
	
	break;
	case "Hot Water":propertyHeatId=3;
	
	break;
	case "In Floor":propertyHeatId=3;

	break;
	
	
	default:propertyHeatId=4;
		break;
	}
	
	//4) Where does the property get the water?
	int propertywaterId = 0;
	// property type
	switch (propertyWater) {
	case "Municipal":propertywaterId=1;
		
		break;
	case "Private Well":propertywaterId=2;
	
	break;
	case "Shared Well":propertywaterId=2;
	break;
	
	
	default:propertywaterId=3;
		break;
	}
	
	//5) How does the property dispose of sewage and waste water?
	
	int propertysewageId = 0;
	// property type
	switch (propertySewage) {
	case "Municipality":propertysewageId=1;
		
		break;
	case "Septic System":propertysewageId=2;
	
	break;
	case "Holding Tank":propertysewageId=3;
	
	break;
	
	
	
	default:propertysewageId=4;
		break;
	}
	
	//What type of garage does your property have? * 
	
	int propertygargageId = 0;
	// property type
	switch (propertygarage) {
	case "Attached":propertygargageId=1;
		
		break;
	case "Detached":propertygargageId=2;
	
	break;
	case "None":propertygargageId=3;
	
	break;
	
	
	
	default:propertygargageId=3;
		break;
	}
	
	//7) What size is your garage?
	int propertygargageSizeId = 0;
	// property type
	switch (propertygaragesize) {
	case "Single":propertygargageSizeId=1;
		
		break;
	case "Double":propertygargageSizeId=2;
	
	break;
	case "Triple":propertygargageSizeId=3;
	
	break;
	case "quadruple":propertygargageSizeId=4;
	
	break;
	
	
	
	
	}
	
	//9) What is the square footage of the property? (above ground)
	
	
	try{
	openERPSession=gHelperClass.getOdooConnection();
	ObjectAdapter applicantAd3 = openERPSession.getObjectAdapter("crm.lead");
	com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
	filters11.add("id","=",leadId);
	Row row=null;
	if(propertygargageId==3){
		com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"property_style","property_type","apartment_style","heating","water","sewage","garage_type","square_footage","outbuildings_value","lot_size","id"});
		
		 row=partners11.get(0);
		row.put("property_style", propertyStyleId);
		row.put("property_type", propertyTyeId);
		row.put("heating", propertyHeatId);
		row.put("water", propertywaterId);
		row.put("sewage", propertysewageId);
		row.put("garage_type", propertygargageId);
	}else{
		com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"property_style","property_type","apartment_style","heating","water","sewage","garage_type","garage_size","square_footage","outbuildings_value","lot_size","id"});
		
		 row=partners11.get(0);
		row.put("property_style", propertyStyleId);
		row.put("property_type", propertyTyeId);
		row.put("heating", propertyHeatId);
		row.put("water", propertywaterId);
		row.put("sewage", propertysewageId);
		row.put("garage_type", propertygargageId);
	row.put("garage_size",propertygargageSizeId);
	}
	if(yesno.equalsIgnoreCase("yes")){
		row.put("outbuildings_value","1");
	}
	row.put("square_footage",square_footage);
	applicantAd3.writeObject(row, true);
	
	log.debug("Updating opporynity sucessfull");
	}catch(Exception e){
		e.printStackTrace();
		
	}
	
	}catch(Exception e){
		e.printStackTrace();
	}
	
}




public void updateApplicantSignatureAndIp(int applicantID,Date consent_dateTime,String signature_ip){
	  openERPSession = gHelperClass.getOdooConnection();
	  try {

	   log.info("inside try updateApplicant");
	   //openERPSession.startSession();
	   ObjectAdapter applicantAd = openERPSession
	     .getObjectAdapter("applicant.record");

	   FilterCollection applicantfilters = new FilterCollection();
	   applicantfilters.add("id","=",applicantID);
	   RowCollection partners = applicantAd.searchAndReadObject(applicantfilters, new String[]{"consent_dateTime","signature_ip"});

	   Row applicantRow = partners.get(0);

	   applicantRow.put("consent_dateTime", consent_dateTime);
	   
	   applicantRow.put("signature_ip", signature_ip);
	   id=applicantRow.getID();
	   // Tell writeObject to only write changes ie the name isn't updated because it wasn't changed.
	   boolean success = applicantAd.writeObject(applicantRow, true);

	   if (success)
	    System.out.println("Update was successful");
	  }catch (Exception e) {
	   log.info("Applicant updating Exception"); }

	 }

public void assignOppertunityPreApproval(int applicantId,int leadId ,String what_is_your_lending_goal,String down_payment_amount ,String propertybuying11_Test,String liveDetails,String monthlyRentalIncome,String desiredType,String desired_term,String desired_amortization,String lookingfor,String purchaseprice11){
	 openERPSession =gHelperClass.getOdooConnection();

	 try{

	 log.debug("inside OpenERP AssignOppertunity");
	  // openERPSession.startSession();
	  log.debug("applicantId "+applicantId +"leadId :"+leadId +"what_is_your_lending_goal :"+what_is_your_lending_goal +"down_payment_amount:"+down_payment_amount +"propertybuying11_Test:"+propertybuying11_Test +"liveDetails :"+liveDetails +"monthlyRentalIncome:"+monthlyRentalIncome +"desiredType :"+desiredType +"desired_term:"+ desired_term+"desired_amortization:"+desired_amortization);

	  ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("applicant.record");
	  ObjectAdapter applicantAd3 = openERPSession.getObjectAdapter("crm.lead");
	  
	   
	   log.debug("Using Many2Many fieleds");
	   
	   com.debortoliwines.openerp.api.FilterCollection filters1 = new com.debortoliwines.openerp.api.FilterCollection();
	   filters1.add("id","=",applicantId);
	   com.debortoliwines.openerp.api.RowCollection partners1 = applicantAd2.searchAndReadObject(filters1, new String[]{"applicant_name","id","opp_rec_ids"});

	   com.debortoliwines.openerp.api.Row TestRow = partners1.get(0);
	   TestRow.put("id", applicantId);
	   TestRow.putMany2ManyValue("opp_rec_ids",new Object[]{leadId},false);
	   
	 applicantAd2.writeObject(TestRow, false);
	   
	   log.debug("Oppertunity created");
	   
	   
	   com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
	   filters11.add("id","=",leadId);
	   com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"what_is_your_lending_goal","property_value","down_payment_amount","looking_fora","living_in_property","monthly_rental_income","desired_mortgage_type","desired_term","desired_amortization","id","monthly_rental_income","application_date","application_no","property_value"});

	  Row r=partners11.get(0);
	  
	   log.debug("in looop11 updated");
	  r.put("what_is_your_lending_goal", what_is_your_lending_goal);
	   log.debug("in looop11 updated");
	   //r.put("property_value", property_value);
	   r.put("down_payment_amount",down_payment_amount);
	   log.debug("in looop11 updated");
	   r.put("looking_fora",propertybuying11_Test);
	   log.debug("in looop11 updated");
	   if(!liveDetails.equals("0")){
		   r.put("living_in_property", liveDetails); 
		   }
	   log.debug("in looop11 updated");
	   r.put("monthly_rental_income",monthlyRentalIncome);
	   log.debug("in looop11 updated");
	   r.put("property_value", purchaseprice11);
	   Date date=Calendar.getInstance().getTime();
		 
	   r.put("application_no", leadId);
	    r.put("application_date", date);
	   r.put("desired_mortgage_type", desiredType);
	   log.debug("in looop11 updated");
	   r.put("desired_term", desired_term);
	   log.debug("in looop11 updated");
	  r.put("desired_amortization", lookingfor);
	  // r.put("id", leadId);

	   applicantAd3.writeObject(r, true);
	   log.debug("assignOppertunityReFinance updated");
	   
	  
	   

	   
	   
	  
	  
	}catch(Exception e){
	 e.printStackTrace();
	}
	 
	 }


	public void assignOppertunityPurchase(String applicantID,int leadId,String leadingGoal,String address,String purchaseprice11,String downpayment1,String down_payment_coming_from,Date expected_closing_date,Date condition_of_financing_date,String living_in_property,String monthlyRentalIncome,String desired_mortgage_type,String currentMortgageTerm,String lookingFor){
	 openERPSession = gHelperClass.getOdooConnection();

	 try{

	 log.debug("inside OpenERP AssignOppertunity");
	 //  openERPSession.startSession();
	  log.debug("applicantId "+applicantID +"leadId :"+leadId +"what_is_your_lending_goal :"+leadingGoal +"down_payment_amount:"+purchaseprice11 +"adress:"+address +"liveDetails :"+living_in_property +"monthlyRentalIncome:"+monthlyRentalIncome +"desiredType :"+desired_mortgage_type +"desired_term:"+ currentMortgageTerm+"SDate expected_closing_date:"+expected_closing_date +"Datecondition_of_financing_date,:"+condition_of_financing_date);

	  ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("applicant.record");
	  ObjectAdapter applicantAd3 = openERPSession.getObjectAdapter("crm.lead");
	  
	   
	   log.debug("Using Many2Many fieleds");
	   
	   com.debortoliwines.openerp.api.FilterCollection filters1 = new com.debortoliwines.openerp.api.FilterCollection();
	   filters1.add("id","=",applicantID);
	   com.debortoliwines.openerp.api.RowCollection partners1 = applicantAd2.searchAndReadObject(filters1, new String[]{"applicant_name","id","opp_rec_ids"});

	   com.debortoliwines.openerp.api.Row TestRow = partners1.get(0);
	   TestRow.put("id", applicantID);
	   TestRow.putMany2ManyValue("opp_rec_ids",new Object[]{leadId},false);
	   
	 applicantAd2.writeObject(TestRow, false);
	   
		HashMap<String, String> address1 = new Address()
		.getProperAddressTwo(address);

	   log.debug("Oppertunity created");
	   
	   
	   com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
	   filters11.add("id","=",leadId);
	   com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"what_is_your_lending_goal","address","property_value","down_payment_amount","down_payment_coming_from","bank_account","rrsp_amount","other_amount","borrowed_amount","sale_of_existing_amount","gifted_amount","personal_cash_amount","secondary_financing_amount","sweat_equity_amount","existing_equity_amount","living_in_property","desired_mortgage_type","desired_term","desired_amortization","id","expected_closing_date","condition_of_financing_date","monthly_rental_income","address","city","province","postal_code","application_date","application_no"});

	  for (Row r:partners11){
		   log.debug("in looop updated");
	   r.put("what_is_your_lending_goal",leadingGoal);
	   log.debug("in looop11 updated");
	   if(!address1.isEmpty()){
		   
		   r.put("address", address1.get("address1")); 
		   r.put("city", address1.get("city")); 
		   r.put("province", address1.get("Province")); 
		   r.put("postal_code", address1.get("postalcode")); 
	   }
	   

	   log.debug("in looop11 updated");
	   r.put("property_value", purchaseprice11);
	   log.debug("in looop11 updated");
	   r.put("down_payment_amount",downpayment1);
	   log.debug("in looop11 updated");
	 //  r.put("down_payment_coming_from", down_payment_coming_from);
	   
	   String[] downSourcePayment=down_payment_coming_from.split("\n");


		int downSourcePaymentLen=downSourcePayment.length;

		List<String> selectedValues=new ArrayList<String>();
		for(int i=0; i<=downSourcePaymentLen-1;i++ ){
			selectedValues.add(downSourcePayment[i].trim());
		}
		for(String selectedValue : selectedValues){
	   if(selectedValue.equalsIgnoreCase("Bank Account")){
		     log.debug("inside BankAccount PurChase");
		     r.put("bank_account", 1);
		     
		    }else if(selectedValue.equalsIgnoreCase("RRSPS")){
		     log.debug("inside RRSPS PurChase");

		     
		     r.put("rrsp_amount", 1);
		     
		    }else if (selectedValue.equalsIgnoreCase("Investments")){
		    	 r.put("other_amount", 1);
		     log.debug("inside Investments PurChase");

		    }else if(selectedValue.equalsIgnoreCase("Borrowed")){
		    	 r.put("borrowed_amount", 1);
		     log.debug("inside Borrowed PurChase");

		   }else if (selectedValue.equalsIgnoreCase("Sale of Property")) {
		    
		    	 r.put("sale_of_existing_amount", 1);

		    log.debug("inside Sale of Property PurChase");

		   }else if (selectedValue.equalsIgnoreCase("Gift")) {
		    
			    r.put("gifted_amount", 1);
		    log.debug("inside Gift PurChase");

		    
		   }else if (selectedValue.equalsIgnoreCase("Personal Cash")) {
		    
			   r.put("personal_cash_amount", 1);
		    log.debug("inside Personal Cash PurChase");

		    
		   } 
		   else if (selectedValue.equalsIgnoreCase("Existing Equity")) {
			    
			   r.put("existing_equity_amount", 1);
		    log.debug("inside Existing Equity");

		    
		   } 
		   else if (selectedValue.equalsIgnoreCase("Sweat Equity")) {
			    
			   r.put("sweat_equity_amount", 1);
		    log.debug("inside Sweat Equity");

		    
		   } 
		   else if (selectedValue.equalsIgnoreCase("2nd Mortgage")) {
			    
			   r.put("secondary_financing_amount", 1);
		    log.debug("inside 2nd  Mortgage");

		    
		   }  else if (selectedValue.equalsIgnoreCase("Other")) {
			    
			    r.put("other_amount", 1);
		    log.debug("inside Other");

		    
		   } 
		}
	   log.debug("in looop11 updated");
	   if(!living_in_property.equals("0")){
		   r.put("living_in_property", living_in_property); 
		   }  
	   log.debug("in looop11 updated");
	   r.put("desired_mortgage_type",desired_mortgage_type);
	   log.debug("in looop11 updated");
	   r.put("desired_term", currentMortgageTerm);
	   log.debug("in looop11 updated");
	   r.put("monthly_rental_income",monthlyRentalIncome);
	  
	   log.debug("in looop11 updated");
	   
	   
	  
	   r.put("desired_amortization", lookingFor);
	   r.put("expected_closing_date", expected_closing_date);
	   log.debug("in looop11 updated");
	   if(condition_of_financing_date!=null){
	   r.put("condition_of_financing_date", condition_of_financing_date);
	   }
	   Date date=Calendar.getInstance().getTime();
		 
	   r.put("application_no", leadId);
	    r.put("application_date", date);
	   log.debug("in looop11 updated");
	   r.put("id", leadId);

	   applicantAd3.writeObject(r, true);
	   log.debug("assignOppertunityReFinance updated");
	   
	  
	   

	   
	   
	  }
	  
	}catch(Exception e){
	 e.printStackTrace();
	}
	 
	 }




	public void assignOppertunityRefinance(int applicantId,int leadId,String leadingGoal,String address,String mortageamount1,String living_in_property,String monthlyRentalIncome,String desired_mortgage_type,String
			currentMortgageTerm,String refiAdditionalAmount,String lookingFor,String purchaseprice11){
	 openERPSession = gHelperClass.getOdooConnection();

	 try{

	 log.debug("inside OpenERP AssignOppertunity");
	 //  openERPSession.startSession();
	  log.debug("startSession");
	  log.debug("applicantId "+applicantId +"leadId :"+leadId +"what_is_your_lending_goal :"+leadingGoal +"mortageamount1:"+mortageamount1 +"adress:"+address +"liveDetails :"+living_in_property +"monthlyRentalIncome:"+monthlyRentalIncome +"desiredType :"+desired_mortgage_type +"desired_term:"+ currentMortgageTerm+"desired_amortization:"+refiAdditionalAmount);

	  ObjectAdapter applicantAd2 = openERPSession.getObjectAdapter("applicant.record");
	  ObjectAdapter applicantAd3 = openERPSession.getObjectAdapter("crm.lead");
	  
	   
	   log.debug("Using Many2Many fieleds");
		HashMap<String, String> address1 = new Address()
		.getProperAddressTwo(address);
	   
	   com.debortoliwines.openerp.api.FilterCollection filters1 = new com.debortoliwines.openerp.api.FilterCollection();
	   filters1.add("id","=",applicantId);
	   com.debortoliwines.openerp.api.RowCollection partners1 = applicantAd2.searchAndReadObject(filters1, new String[]{"applicant_name","id","opp_rec_ids"});

	   com.debortoliwines.openerp.api.Row TestRow = partners1.get(0);
	   TestRow.put("id", applicantId);
	   TestRow.putMany2ManyValue("opp_rec_ids",new Object[]{leadId},false);
	   
	 applicantAd2.writeObject(TestRow, false);
	   
	   log.debug("Oppertunity created");
	   
	   
	   com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
	   filters11.add("id","=",leadId);
	   com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3.searchAndReadObject(filters11, new String[]{"what_is_your_lending_goal","address","current_mortgage_amount","living_in_property","desired_mortgage_type","desired_term","desired_amortization","id","monthly_rental_income","address","city","province","postal_code","application_date","application_no","property_value"});

	  for (Row r:partners11){
		  
		   log.debug("in looop11 updated");
	   r.put("what_is_your_lending_goal",leadingGoal);
	   log.debug("in looop11 updated");
 if(!address1.isEmpty()){
		   
		   r.put("address", address1.get("address1")); 
		   r.put("city", address1.get("city")); 
		   r.put("province", address1.get("Province")); 
		   r.put("postal_code", address1.get("postalcode")); 
	   }
	   log.debug("in looop11 updated");
	   r.put("current_mortgage_amount", mortageamount1);
	   log.debug("in looop11 updated");
	   if(!living_in_property.equals("0")){
	   r.put("living_in_property", living_in_property); 
	   }
	   log.debug("in looop11 updated");
	   r.put("monthly_rental_income",monthlyRentalIncome);
	   log.debug("in looop11 updated");
	
	   r.put("desired_mortgage_type", desired_mortgage_type);
	   log.debug("in looop11 updated");
	   r.put("desired_term", currentMortgageTerm);
	   log.debug("in looop11 updated");
	   r.put("desired_amortization", lookingFor);
	   r.put("property_value", purchaseprice11);
	   Date date=Calendar.getInstance().getTime();
	 
	   r.put("application_no", leadId);
	    r.put("application_date", date);
	   log.debug("in looop11 updated");
	   r.put("id", leadId);
	   log.debug("in looop11 updated");
	   applicantAd3.writeObject(r, true);
	   log.debug("assignOppertunityReFinance updated");
	   
	  
	   

	   
	   
	  }
	  
	}catch(Exception e){
	 e.printStackTrace();
	}
	 
	 }

}
