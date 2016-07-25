package com.syml.submitreferral.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.data.DynamicForm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sendwithus.SendWithUsExample;
import com.syml.Constants;
import com.syml.SplitAddress;
import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.dao.impl.PostgresDAO;

import controllers.Contact;
import controllers.Lead;
import controllers.Referral_Source;

public class SubmitReferralPageService {

	
	
	
	
	public  Lead getLeadDataFromForm(DynamicForm dynamicForm) {

		Lead leadPojo = new Lead();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // get

		Calendar cal = Calendar.getInstance();
		String currentDateTime = (dateFormat.format(cal.getTime()));

		leadPojo.setSubmission_Date_Time1b(currentDateTime);
		leadPojo.setEmail_from(dynamicForm.get("email"));
		leadPojo.setName(dynamicForm.get("fname") + "_"
				+ dynamicForm.get("lname"));
		leadPojo.setMobile(dynamicForm.get("phone"));
		leadPojo.setIp(dynamicForm.get("ip"));
		leadPojo.setFirstName(dynamicForm.get("fname"));
		leadPojo.setLastName(dynamicForm.get("lname"));

		int referralId = 0;
		try {
			referralId = Integer.parseInt(dynamicForm.get("referralId"));
		} catch (Exception e) {
			Logger.error("Error in parsing referralid to integer ,Referralid="
					+ dynamicForm.get("referralId"), e);
		}
		leadPojo.setReferref_source(referralId);
		leadPojo.setReferral_Source_First_Name(dynamicForm.get("reffirstname"));
		leadPojo.setReferral_Source_Last_Name(dynamicForm.get("reflastname"));
		leadPojo.setReferral_Source_Email(dynamicForm.get("refemail"));
		/*leadPojo.setUser_id(MortgageApplicationConstants.USER_ID);
		leadPojo.setHr_department_id(MortgageApplicationConstants.HR_DEPARTMENT_ID);
		leadPojo.setCreate_uid(MortgageApplicationConstants.USER_ID);
	*/	// call method to split address
		SplitAddress ad = new SplitAddress();
		Map<String, String> addressMap = ad.getProperAddress(dynamicForm
				.get("formatted_address"));
		if (!addressMap.isEmpty()) {
			leadPojo.setCity(addressMap.get("city"));
			leadPojo.setStreet2(addressMap.get("address1"));
			leadPojo.setPostal_code(addressMap.get("postalcode"));
			leadPojo.setCountry(addressMap.get("country"));
			int statId = 0;
			try {
				statId = new PostgresDAO().getStateID(addressMap
						.get("Province"));
			} catch (PostGressDaoServiceException e) {
				Logger.error("error in getting the Province from DB for Given Province= "+addressMap
						.get("Province"),e);
			}
			leadPojo.setState_id(statId);

			if (statId == 0)
				leadPojo.setState_id(null);
		}
		return leadPojo;
	}
	
	
	public void sendMailForLeadAndReferral(Lead leadPojo, Contact contact) throws JsonProcessingException {
		SendWithUsExample sendWithus = new SendWithUsExample();


			

			sendWithus.sendTOclientMortgageApplication(leadPojo.getId() + "",
					leadPojo.getFirstName(),
					leadPojo.getReferral_Source_First_Name(),
					leadPojo.getReferral_Source_Last_Name(),
					leadPojo.getEmail_from(),
					leadPojo.getReferral_Source_Email());
			
	}

	
	public static Contact checkContactExist(DynamicForm dynamicForm) throws PostGressDaoServiceException {
		Contact contact = new Contact();
		contact.setName(dynamicForm.get("fname"));
		contact.setLast_name(dynamicForm.get("lname"));
		contact.setEmail(dynamicForm.get("email"));
		IPostGresDaoService postGresDaoService = new PostgresDAO();
		List<Contact> list = postGresDaoService.getContact(contact);
		if (list.isEmpty())
			return contact;

		return list.get(0);

	}


	public  Lead toFindREferralSource(Lead leadPojo) throws JsonProcessingException, PostGressDaoServiceException  {

	
		/**
		 * If referralId from url is not given
		 */
		if (leadPojo.getReferref_source()==null ) {
			
				leadPojo=toFindReferrallSourceByEmail(leadPojo);
			
		}else if( leadPojo.getReferref_source() == 0){
			leadPojo=toFindReferrallSourceByEmail(leadPojo);

		}
		else {
			leadPojo=findReferralSourceById(leadPojo);
		}
		if (leadPojo.getReferref_source()  != null && leadPojo.getReferref_source()==0 )
			leadPojo.setReferref_source(null);
		
		return leadPojo;
		
	}

	
	/**
	 * to find Referral source by given referral Id and send mail based on the scenarios
	 * @param leadPojo
	 * @return Lead Pojo
	 * @throws JsonProcessingException
	 * @throws PostGressDaoServiceException 
	 */
	private Lead findReferralSourceById(Lead leadPojo) throws JsonProcessingException, PostGressDaoServiceException{
		
		Logger.info("inside (.) findReferralSourceById method ");
		
		Referral_Source referral_Source = null;
		IPostGresDaoService iPostGresDaoService=new PostgresDAO();
		SendWithUsExample sendWithUsExample=new SendWithUsExample();
		referral_Source = iPostGresDaoService.getReferralSourceById(leadPojo.getReferref_source());
		if (referral_Source != null) {
			Contact contact = iPostGresDaoService.getContactById(referral_Source.getPartner_id());

			if (contact != null&& leadPojo.getReferral_Source_Email() != null&& leadPojo.getReferral_Source_Email().equalsIgnoreCase(contact.getEmail())) {
				leadPojo.setReferral_Source_Phone(referral_Source.getPartner_mobile());

			} else {
				if (contact.getLast_name() != null&& leadPojo.getReferral_Source_Last_Name() != null&& contact.getLast_name().equalsIgnoreCase(leadPojo.getReferral_Source_Last_Name())) {
					sendWithUsExample.sentToReferralSubmitReferralEMailChangeID(leadPojo);
				} else {
					sendWithUsExample.sentToReferralEMailChange(leadPojo);
					sendWithUsExample.sendTOreferalSubmittedReferral(leadPojo.getFirstName(),leadPojo.getReferral_Source_First_Name(),leadPojo.getReferral_Source_Email());
				}
			}

		} else {

			sendWithUsExample.sentToSupportReferralMissing(leadPojo.getName(), Constants.SUPPORT_MAIL_ID);
			sendWithUsExample.sendTOreferalSubmittedReferral(leadPojo.getFirstName(),leadPojo.getReferral_Source_First_Name(),leadPojo.getReferral_Source_Email());

		}return leadPojo;
	
	
		
	}
	
	/**
	 * To find referral Source by mail ID, if we found more than will search by last 
	 * @param leadPojo
	 * @return
	 * @throws JsonProcessingException
	 * @throws PostGressDaoServiceException 
	 */
	private Lead toFindReferrallSourceByEmail(Lead leadPojo) throws JsonProcessingException, PostGressDaoServiceException{
		Logger.debug("Inisd (.) toFindReferrallSourceByEmail  method ");
		IPostGresDaoService iPostGresDaoService=new PostgresDAO();
		SendWithUsExample sendWithUsExample=new SendWithUsExample();
		
		ArrayList<Referral_Source> referral_SourceList = iPostGresDaoService.getReferral_SourceByEmail(leadPojo.getReferral_Source_Email());
		if (referral_SourceList != null && referral_SourceList.size() == 1) {
			Logger.debug(" Referral Exist only One ");

			leadPojo.setReferral_Source_Phone(referral_SourceList.get(0).getPartner_mobile());
		} else if (referral_SourceList != null&& referral_SourceList.size() > 1) {
			// if referral exist more than one with same emailid
			Logger.debug(" Referral Exist More than one  ");

			Contact contact = new Contact();
			contact.setLast_name(leadPojo.getReferral_Source_Last_Name());
			contact.setEmail(leadPojo.getEmail_from());
			contact.setName(leadPojo.getReferral_Source_First_Name());
			ArrayList<Contact> contactList = (ArrayList<Contact>) iPostGresDaoService.getContactByEmailAndLastName(contact);
			if (contactList != null && contactList.size() == 1)
			referral_SourceList = iPostGresDaoService.getReferral_SourceByPartnerID(contactList.get(0).getId() );


			if (referral_SourceList != null&& referral_SourceList.size() == 1) {
				Logger.debug(" referral_SourceList   Exist  only one when search By conatct ID  ");

				// matching referral Exist
				leadPojo.setReferral_Source_Phone(referral_SourceList.get(0).getPartner_mobile());
			} else if (referral_SourceList != null
					&& referral_SourceList.size() > 1) {
				Logger.debug(" referral_SourceList   Exist  more than One sendingmail ToSupportAboutDuplicateReferralSource and submitreferral Details");

				sendWithUsExample.sentToSupportAboutDuplicateReferralSource(	leadPojo.getName(),	Constants.SUPPORT_MAIL_ID);
				sendWithUsExample.sendTOreferalSubmittedReferral(leadPojo.getFirstName(),leadPojo.getReferral_Source_First_Name(),leadPojo.getReferral_Source_Email());

			} else {
				Logger.debug(" referral_SourceList  Not found sendMail  ToSupportReferralMissing  ");

				sendWithUsExample.sentToSupportReferralMissing(leadPojo.getName(), Constants.SUPPORT_MAIL_ID);
				sendWithUsExample.sendTOreferalSubmittedReferral(leadPojo.getFirstName(),leadPojo.getReferral_Source_First_Name(),leadPojo.getReferral_Source_Email());

			}

		} else {
			Logger.debug(" referral_SourceList  Not found sendMail  ToSupportReferralMissing  ");

			sendWithUsExample.sentToSupportReferralMissing(leadPojo.getName(), Constants.SUPPORT_MAIL_ID);
			sendWithUsExample.sendTOreferalSubmittedReferral(leadPojo.getFirstName(),leadPojo.getReferral_Source_First_Name(),leadPojo.getReferral_Source_Email());

		}
	return leadPojo;
	}
	
	
	
	
	
	
	public   boolean validate(Lead lead) {
		boolean sucess = false;
		if (lead.getName() != null && !lead.getName().isEmpty()) {
			sucess = true;
		}
		if (lead.getMobile() != null && !lead.getMobile().isEmpty()) {
			sucess = true;

		}
		if (lead.getEmail_from() != null && !lead.getEmail_from().isEmpty()) {
			sucess = true;

		}

		if (lead.getReferral_Source_First_Name() != null
				&& !lead.getReferral_Source_First_Name().isEmpty()) {
			sucess = true;

		}

		if (lead.getReferral_Source_Last_Name() != null
				&& !lead.getReferral_Source_Last_Name().isEmpty()) {
			sucess = true;

		}

		if (lead.getReferral_Source_Email() != null
				&& !lead.getReferral_Source_Email().isEmpty()) {
			sucess = true;

		}

		return sucess;
	}
}
