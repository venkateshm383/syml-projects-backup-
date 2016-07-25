package com.syml.jotform.visdomReferrelResoursce2;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.RowCollection;
import com.syml.address.splitAddress.Address;
import com.syml.couchbase.CouchBaseOperation;

import com.syml.helper.GenericHelperClass;

import com.syml.openerp.ReferalToCreateReferralResoursce;

public class FirstVisidamReferalFormServlet extends HttpServlet {

	static Logger log = LoggerFactory
			.getLogger(FirstVisidamReferalFormServlet.class);
@Override
protected void doPost (HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	// TODO Auto-generated method stub


		HttpSession session = req.getSession(true);
		UUID id=UUID.randomUUID();
		String uniqueId = id.toString();
			
		String role = req.getParameter("q39_whatBest");
		String name = req.getParameter("q14_firstname");
		String lastname = req.getParameter("q15_lastname");
		String email = req.getParameter("q16_email").toLowerCase();
		String phoneNumber = req.getParameter("q20_mobilenumber");
		String nameOfBuilderYouWork = req.getParameter("q23_realtorBrokerage");
		String nameOfFinancialPlanningCompanyYouWork = req
				.getParameter("q34_realtorBrokerage34");
		String compnesation = req.getParameter("q33_inSome");
		String company=req.getParameter("q42_realtorBrokerage42");
		String AreYouLicensedAsRealtor = req.getParameter("q27_areYou");
		String nameOfTheBrokerageWhereYouWork = req
				.getParameter("q28_realtorBrokerage28");
		String AddressOfTheBrokerageWhereYouWork = req.getParameter("q24_whatIs24");

		HashMap<String, String> address = new Address()
			.getProperAddressTwo(AddressOfTheBrokerageWhereYouWork);

		String brokerPhoneNumber = req.getParameter("q32_brokerphone");
		String WhoIsThebrokerThatManagesTheBrokerageWhereYouWork = req
				.getParameter("q26_realtorBrokerage26");
		String referrer = req.getParameter("q30_whoReferred");

		//String oldUniqueId = (String) session.getAttribute("id");
		//String oldRole = (String) session.getAttribute("role");

		
		
	
				log.debug("inside second visdom referrel servlet");


			log.debug("both forms are filled by same person");

		log.info("unique Id "+uniqueId);

			log.info("Role" +role);

			log.info("First Name" + name);

			log.info("Last Name" + lastname);

			log.info("Email" + email);
			log.info("phone number" +phoneNumber);
			log.info(" Name of Builder" + nameOfBuilderYouWork);
			log.info("	nameOfFinancialPlanningCompanyYouWork "
					+ nameOfFinancialPlanningCompanyYouWork);

			log.info("compnesation is Given " + compnesation);

			log.info("are you licensed" + AreYouLicensedAsRealtor);

			log.info("realtorBrokerage Work address "
					+ AddressOfTheBrokerageWhereYouWork);

			log.info("name of the brokarage where you work"
					+ nameOfTheBrokerageWhereYouWork);
			log.info("realtorBroker Phone" + brokerPhoneNumber);
			log.info("company name" + company);


			log.info("WhoIsThebrokerThatManagesTheBrokerageWhereYouWork"
					+ WhoIsThebrokerThatManagesTheBrokerageWhereYouWork);
			log.info("Who Referrer To Visdom " + referrer);
			
			
			
			// creating objrcts of supporting classes
			ReferalToCreateReferralResoursce referalToCreateReferralResoursce = new ReferalToCreateReferralResoursce();
			GenericHelperClass gHelperClass = new GenericHelperClass();

			// store value in hashmap to send data to second and third form
			// storeSubmitReferalDataInCouchBase()
			HashMap data = new HashMap();
			data.put("firstname", name);
			data.put("lastName", lastname);
			data.put("email", email);
			data.put("phoneNumber", phoneNumber);
			data.put("companyname", referalToCreateReferralResoursce.getCompanyName(
					nameOfBuilderYouWork,
					nameOfFinancialPlanningCompanyYouWork,
					nameOfTheBrokerageWhereYouWork,company));
			data.put("address", AddressOfTheBrokerageWhereYouWork);
			data.put("compansation", compnesation);
			data.put("licensed", AreYouLicensedAsRealtor);
			data.put("brokerage_PhoneNumber", brokerPhoneNumber);
			data.put("broker_Manages_urwork",
					WhoIsThebrokerThatManagesTheBrokerageWhereYouWork);
			data.put("referrer_Tovisdom", referrer);

			// storing value to hash map to send data to thrird form
			HashMap message = new HashMap();
			String msg = "";
			int referrelID = 0;
			int contactId = 0;
			try {

			
				int referrerResPartnerId = 0;

				// checking contact exsist are not by email id in res.partner
				RowCollection rowResPartners = gHelperClass
						.lookupContactByEmail(email, "res.partner");

				// Getting the partnerId of the one who referrer to visdom

				referrerResPartnerId = referalToCreateReferralResoursce
						.getExsistingResPartnerId(referrer);

				if (referrerResPartnerId == 0) {
					log.debug("The Referrer for Visdom with this name "
							+ referrer + " is not exsist ");
					
				}else if(referrerResPartnerId==1){
					log.debug("The Referrer for Visdom with this name "
							+ referrer + " is  exsist more than one" );
					
				}

				if (rowResPartners.size() == 0) {
					log.debug("if contact(res.partner) and referrel(hr.applicant) not exsist");
					// creating new contact and returning the contactid
					contactId = gHelperClass.createContact(name, lastname,
							email, "res.partner");
					log.info("Contact is created with this Id " + contactId);
					// creating new referrer and returning the referrelId
					referrelID = gHelperClass.createRefereal(name,
							lastname, email, contactId, phoneNumber, referrer,
							role, referalToCreateReferralResoursce.getCompanyName(
									nameOfBuilderYouWork,
									nameOfFinancialPlanningCompanyYouWork,
									nameOfTheBrokerageWhereYouWork,company),
							brokerPhoneNumber, referrerResPartnerId, address,
							"hr.applicant");
					log.info("Referrer is created with this Id " + referrelID);
				} else {

					log.debug("if contact is Exsist");

					// retrving the contact id based on firstname lastname and
					// email
					contactId = gHelperClass.lookupContactID(email, name,
							lastname, "res.partner");
					
					//if contact id is not exsist for firstname and lastname  
					if (contactId == 0) {
						// creating new contact and returning the contactid
						contactId = gHelperClass.createContact(name, lastname,
								email, "res.partner");
						log.info("Contact is created with this Id " + contactId);
						// creating new referrer and returning the referrelId
						referrelID = gHelperClass.createRefereal(name,
								lastname, email, contactId, phoneNumber, referrer,
								role, referalToCreateReferralResoursce.getCompanyName(
										nameOfBuilderYouWork,
										nameOfFinancialPlanningCompanyYouWork,
										nameOfTheBrokerageWhereYouWork,company),
								brokerPhoneNumber, referrerResPartnerId, address,
								"hr.applicant");
						log.info("Referrer is created with this Id " + referrelID);
						
					}else{

						// Checking contact is exsist in referrerl Resource
						// based on firstname or last name or fullname and
						// returning the referrelid if exsist else returning 0
						referrelID = gHelperClass.searchContactAndGetReferalID(
								email, name, lastname, "res.partner");

						if (referrelID == 0) {
							log.debug("if contact(res.partner) is Exsist");
							// retrving the contact id based on firstname
							// lastname and email
							contactId = gHelperClass.lookupContactID(email,
									name, lastname, "res.partner");

							// inserting data to referrla resoursce and
							// returning referral id
							referrelID = gHelperClass
									.createRefereal(
											name,
											lastname,
											email,
											contactId,
											phoneNumber,
											referrer,
											role,
											referalToCreateReferralResoursce
													.getCompanyName(
															nameOfBuilderYouWork,
															nameOfFinancialPlanningCompanyYouWork,
															nameOfTheBrokerageWhereYouWork,company),
											brokerPhoneNumber,
											referrerResPartnerId, address,
											"hr.applicant");
							log.info("Referrer is created with this Id "
									+ referrelID);

						} else {
							// retrving the contact id based on firstname
							// lastname and email

							contactId = gHelperClass.lookupContactID(email,
									name, lastname, "res.partner");
							
							log.info("Referrer is exsist  with this Id "
									+ referrelID);
							// updating the referrer and returning referrel id
							message = referalToCreateReferralResoursce
									.updateReferrralDetails(
											name,
											lastname,
											email,
											contactId,
											phoneNumber,
											referrer,
											role,
											referalToCreateReferralResoursce
													.getCompanyName(
															nameOfBuilderYouWork,
															nameOfFinancialPlanningCompanyYouWork,
															nameOfTheBrokerageWhereYouWork,company),
											brokerPhoneNumber,
											referrerResPartnerId, address,
											"hr.applicant");
							log.info("Referrer details are updted  based on this Id "
									+ message.get("referrelId"));
													}

					}

					// updated referrelid
					if (!message.isEmpty()) {
						try {
							referrelID = new Integer(message.get("referrelId")
									.toString());
							msg = message.get("message").toString();
						} catch (Exception e) {
								log.error("error in reading referralid and message from hashmap "+e);
						}
					}
				}
				String formType = "Visdom_Referrel";

				CouchBaseOperation storeData = new CouchBaseOperation();
				String key = formType+"_"+ referrelID;

				storeData.storeDataInCouchBase(key, formType, data);

			} catch (Exception e) {
				// TODO: handle exception
				log.error("erro in visdomReferralServlet2D "+e);

			}
			// storing value to hash map to send data to thrird form
			
			
			req.setAttribute("uniqueid", uniqueId);
			req.setAttribute("role", role);
			req.setAttribute("referrelid", referrelID);
			req.setAttribute("message", msg);
			session.setAttribute("contactId", contactId);
			session.setAttribute("visdomreferreldata", data);
			session.setAttribute("uniqueId", uniqueId);
			session.setAttribute("mytyped", name);
			session.setAttribute("Visdommessage", msg);

			/*resp.sendRedirect("http://form.jotformpro.com/form/50344103613946?uniqueid="
					+ uniqueId
					+ "&referrelid="
					+ referrelID
					+ "&message="
					+ msg);*/

			req.getRequestDispatcher("VisdomReferralSource21.jsp").forward(req, resp);
		}
}

	

