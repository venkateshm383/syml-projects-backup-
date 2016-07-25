package com.syml.openerp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.syml.address.splitAddress.Address;
import com.syml.addressgroup.pojo.AddressGroup;
import com.syml.addressgroup.pojo.PropertyGrouping;
import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
/*
@author
vikash singh,bizruntime
 */
import com.syml.mail.SendEmail;

public class CreateLead {
	Session openERPSession =null;
	static	Logger log = LoggerFactory.getLogger(CreateLead.class);
	SendEmail mail=new SendEmail();


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
	}
	int id=0;
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
   public void checkAndCreateLead(String formFirstName,  String formLastName,String formcellnumber, String formEmailAddress,String formAddress){

		log.info("inside checkAndCreateLead method of CreateLead");

		openERPSession = getOdooConnection();
		try {

			log.debug("inside try");
			openERPSession.startSession();
			ObjectAdapter partnerAd = openERPSession.getObjectAdapter("crm.lead");
			ObjectAdapter resPartnerAD=openERPSession.getObjectAdapter("res.partner");

			FilterCollection filters = new FilterCollection();
			//filters.add("customer","=",true);
			filters.add("email_from","=",formEmailAddress);
			RowCollection leadCount = partnerAd.searchAndReadObject(filters, new String[]{"email_from","name","date_action"});
			log.debug("no. of lead count : "+leadCount.size());

			if(leadCount.size()==0){
				//ObjectAdapter partnerAd1 = openERPSession.getObjectAdapter("res.partner");
				FilterCollection contactfilter=new FilterCollection();
				contactfilter.add("customer","=",true);
				contactfilter.add("email","=",formEmailAddress);

				RowCollection contactpatner=resPartnerAD.searchAndReadObject(contactfilter, new String[]{"email","name","last_name"});

				log.debug("no of contacts with emailid : "+contactpatner.size());

				if(contactpatner.size()==0){

					Row newPartner = resPartnerAD.getNewRow(new String[]{"name", "last_name","mobile","email"});
					newPartner.put("name",formFirstName );
					newPartner.put("last_name",formLastName);
					newPartner.put("mobile",formcellnumber);
					newPartner.put("email",formEmailAddress);

					partnerAd.createObject(newPartner);
					int contactId=newPartner.getID();

					log.debug("New Row ID: " + contactId);

					//create lead by taking customerid TODO
					Row newLead = partnerAd.getNewRow(new String[]{"name", "email_from","partner_id"});
					newLead.put("name", formFirstName+"_ "+formLastName);
					newLead.put("email_from", formEmailAddress);
					newLead.put("partner_id", contactId);
					//TODO add contact id in referal resource while creating lead

					newLead.put("referred_source", contactId);

					partnerAd.createObject(newLead);
					//UpdateLead so that it assign to Correct Team
					FilterCollection filter = new FilterCollection();
					filter.add("name","=",formFirstName);
					RowCollection partners = partnerAd.searchAndReadObject(filter, new String[]{"name","email","hr_department_id"});

					// we should do some validation here to see if the Lead was found
					Row updateRow = partners.get(0);
					updateRow.put("hr_department_id", 7);
					// Tell writeObject to only write changes i.e the name isn't updated because it wasn't changed,same for otherField.
					boolean success = partnerAd.writeObject(updateRow, true);

					if (success)
						log.info("create Lead was successful");
					//create stage of lead to pending Application
					partnerAd.executeWorkflow(newLead, "6");
					mail.sendEmailMortgage("", "");
				}else{
					boolean contactFound = false;
					int newcontactId=0;
					for(Row row1:contactpatner){
						if(row1.get("name").equals(formFirstName) && row1.get("last_name").equals(formLastName)){
							log.info("contact is found");
							newcontactId=row1.getID();


							contactFound=true;


							break;
						}
					}
					if(contactFound){

						//TODO if check referal resource exist or not ,if exist
						//create lead by taking customerid TODO
						Row newLead = partnerAd.getNewRow(new String[]{"name", "email_from","partner_id"});
						newLead.put("name", formFirstName+"_ "+formLastName);
						newLead.put("email_from", formEmailAddress);
						newLead.put("partner_id", newcontactId);


						//TODO add contact id in referal resource while creating lead
						newLead.put("referred_source", newcontactId);

						partnerAd.createObject(newLead);

						//UpdateLead so that it assign to Correct Team
						FilterCollection filter = new FilterCollection();
						filter.add("name","=",formFirstName);
						RowCollection partners = partnerAd.searchAndReadObject(filter, new String[]{"name","email","hr_department_id"});

						// we should do some validation here to see if the Lead was found
						Row updateRow = partners.get(0);
						updateRow.put("hr_department_id", 7);
						// Tell writeObject to only write changes i.e the name isn't updated because it wasn't changed,same for otherField.
						boolean success = partnerAd.writeObject(updateRow, true);

						if (success)
							log.info("create Lead was successful");
						//To DO change of stage to Pending Level
						partnerAd.executeWorkflow(newLead, "6");

						//TODO else  send email with ryt body, create lead with contact id in contact feild, leave referal resorce as blank,
						//change stage of lead to pending application

					}else{
						//TODO 
						//sending mail
						mail.sendEmailMortgage("", "");
						//create lead with contact Id

						Row contactin_lead = partnerAd.getNewRow(new String[]{"partner_id"});
						contactin_lead.put("partner_id", newcontactId);
						contactin_lead.put("referred_source", "");
						//TODO add contact id in referal resource while creating lead

						//create lead
						partnerAd.createObject(contactin_lead);
						//executeFlow
						partnerAd.executeWorkflow(contactin_lead, "6");



						//TODO 
					}
				}

			}else{

				//TODO else part 

				boolean leadFound=false;
				for (Row row : leadCount) {
					if(row.get("name").equals(formFirstName+"_ "+formLastName)){
						log.debug("lead with name exist");
						leadFound=true;
						break;
					}
				}

				if(leadFound){
					//created Data
					for (Row row1 : leadCount) {
						if(row1.get("name").equals(formFirstName+"_ "+formLastName)){
							String leaddate=row1.get("date_action").toString();
						}		
					}//check for new one or old one Lead
					//Get the current date and subtract from 4 weeks
					Calendar calendar = Calendar.getInstance(); // this would default to now
					calendar.add(Calendar.DAY_OF_MONTH, -28);
					//changed logic to create data since its unReliable

					FilterCollection contactleadfilters = new FilterCollection();
					contactleadfilters.add("partner_id","=",7);
					RowCollection contactpartners = partnerAd.searchAndReadObject(contactleadfilters, new String[]{"name","email","hr_department_id","stage_id","partner_id"});

					if(contactpartners.size()==0){
						log.debug("inside Not found contact lead");
						Row newPartner = partnerAd.getNewRow(new String[]{"name", "email_from", "hr_department_id","partner_id"});
						newPartner.put("name", formFirstName);
						newPartner.put("email_from", formEmailAddress);
						newPartner.put("partner_id",7);
						System.out.println("Create Lead");
						partnerAd.createObject(newPartner);
						System.out.println("Lead created");

					}if(contactpartners.size()>0){
						log.debug("inside found contact lead");

						FilterCollection filters1 = new FilterCollection();
						filters1.add("city","=","Calgary");
						RowCollection citypartners = partnerAd.searchAndReadObject(filters1, new String[]{"name","email_from", "city"});


						for (Row row : citypartners){
							log.debug("Row ID: " + row.getID()+"\t"+"Name:"  + row.get("name")+"\t"+" city"+"\t"+ row.get("city"));

							if(row.get("city") == formAddress){
								//send mail
								mail.sendEmailMortgage("", "");

							}else{
								//create Lead
								Row newPartner = partnerAd.getNewRow(new String[]{"name", "email_from", "hr_department_id","partner_id"});
								newPartner.put("name", formFirstName);
								newPartner.put("email_from", formEmailAddress);
								newPartner.put("partner_id",131);
								//newPartner.put("hr_department_id",new Object[] {"7","BizRuntimeDev"});
								//newPartner.put("hr_department_id","7");
								log.info("Create Lead");
								partnerAd.createObject(newPartner);
								log.info("Lead created");
							}


						}

						log.info("Lead logic over");

					}



					//code for Check stage of Lead
					Row newLead = partnerAd.getNewRow(new String[]{"stage_id"});
					newLead.put("stage_id", "6");

					FilterCollection filt = new FilterCollection();
					filters.add("customer","=",true);
					RowCollection partners = partnerAd.searchAndReadObject(filt, new String[]{"stage_id","stage_id"});

					for (Row row : partners){
						row.put("stage_id", "6");
						row.put("stage_id", "1");
						System.out.println("pending:" + row.get("6"));
						System.out.println("Gathering"+row.get("1"));
						if(row.get("stage_id") == "6")
						{
							//sending mail
							mail.sendEmailMortgage("","");
							//changing workFlow
							partnerAd.executeWorkflow(row, "1");
							//Re-Trigger workFlow
							partnerAd.executeWorkflow(row, "6");
						}else {
							mail.sendEmailMortgage("", "");	
						}
					}
				}else{

				}
			}

		} catch (Exception e) {
			log.error("Error while reading data from server:\n\n" + e.getMessage());
		}
	}






}
