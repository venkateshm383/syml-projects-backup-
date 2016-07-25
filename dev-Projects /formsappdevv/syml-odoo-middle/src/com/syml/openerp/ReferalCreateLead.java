package com.syml.openerp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.sendwithus.SendWithUsExample;
import com.syml.address.splitAddress.Address;
import com.syml.helper.GenericHelperClass;

import com.syml.mail.SendEmail;
import com.syml.mail.leadMailTemplate.ErrorInCreatingLeadMessageTemplate;
import com.syml.mail.leadMailTemplate.LeadChangingToPendingApplicationMessageTemplate;
import com.syml.mail.leadMailTemplate.MailToSupportOnReferralNotFound;
import com.syml.mail.leadMailTemplate.SendMessageToLead;

public class ReferalCreateLead {
	static Session openERPSession = null;
	SendEmail email = new SendEmail();
	static Logger log = LoggerFactory.getLogger(ReferalCreateLead.class);
	GenericHelperClass gHelperClass = new GenericHelperClass();

	public static void main(String[] args) {
		GenericHelperClass gHelperClass = new GenericHelperClass();
		int id= gHelperClass.lookupContactID("kendall@visdom.ca",
				"kendall","raessler", "res.partner");
	}
	
	/*
	 * This method is used to create lead based on various factor
	 */
	public ArrayList checkAndCreateLead(HashMap map) throws Exception {
		// id for create note list
		// id for create note list
		int id = 0;
		int exsist = 0;

		// 1 .this hashmap values has no use for developer referance will be
		// removed later
		log.debug("inside checkAndCreateLead method of ReferalCreateLead");
		String referralId=(String)map.get("referralIdFound");

		String RefFirstName = (String) map.get("FirstName_of_referal");
		String RefLastName = (String) map.get("LastName_of_referal");
		String phoneNumber = (String) map.get("phoneNumber_of_referal");
		String RefEmail = (String) map.get("Email_of_referal");
		String refAddress = (String) map.get("address_of_referal");
		String firstname = (String) map.get("Referal_Source_FirstName");
		String lastname = (String) map.get("Referal_Source_LastName");
		String email = (String) map.get("Referal_Source_Email");

		log.debug("referal frstname : " + RefFirstName
				+ ", referal lastname : " + RefLastName + ", phone no. :"
				+ phoneNumber + ", referal eamil : " + RefEmail
				+ ", referal source fname : " + firstname
				+ ", referal source lastname : " + lastname
				+ ", referal source email : " + email);

		// 2. calling Address class to split address into address1,city,state
		// etc
		log.debug("calling split address");

		// call method to split address
		Address ad = new Address();
		HashMap propadd = ad.getProperAddress(refAddress);
		log.debug("proper address : " + propadd);

		// check whether contact exist in odoo contact module with specified
		// email (checking based on
		// submitReferalForm2 email)

		RowCollection contactpartner = gHelperClass.lookupContactByEmail(
				RefEmail, "res.partner");
		log.debug("contact size with refral email : " + contactpartner.size());

		// this means referal property is new (means data in
		// submitresferalForm2)
		if (contactpartner.size() == 0) {
			log.debug("contact with referal email doesn't exist");

			// since referal property is new so, create contact under odoo
			// contact module and
			// create corresponding lead for it directly
			id = createContactAndLead(map, propadd, "res.partner", "crm.lead");
			// if lead not exsist
			exsist = 1;
		} else {
			// this means referal property already exist (means data in
			// submitresferalForm2 already exist)
			
			// now checking in human Resource module of odoo with contact id to
			// know referal-source exist or not
			

			
			
			int referalid =0; try{
				referalid= Integer.parseInt(   referralId);
			}catch(Exception e){
				
			}

		
			log.debug("referal id of referal person : " + referalid);

			int leadSize = 0;

			// this mean contact found odoo server with data comming from
			// submitReferalform1 but not its corresponing referal-source
			if (referalid == 0) {

				log.debug("referal id doesnt exist");

				// lead exist with email in submitReferalForm2 in lead
				// module
				leadSize = gHelperClass.lookupLeadByEmail(RefEmail);

				// this mean lead doestnot exit
				if (leadSize == 0) {

					// create lead without referal source id and send appropiate
					// message
					int refcontactid = gHelperClass.lookupContactID(RefEmail,
							RefFirstName, RefLastName, "res.partner");

					if (refcontactid != 0) {

						log.debug("referal with email : " + RefEmail
								+ " firstname : " + RefFirstName
								+ " lastname : " + RefLastName + " exist");

						id = createLeadWithoutReferalSource(map, propadd,
								refcontactid, "crm.lead");
						// if lead not exsist
						exsist = 1;

					} else {

						log.debug("referal with email : "
								+ RefEmail
								+ " firstname : "
								+ RefFirstName
								+ " lastname : "
								+ RefLastName
								+ "doesnt exist, so creating new contact for referal");
						int refcontactidnew = gHelperClass.createContact(
								RefFirstName, RefLastName, RefEmail,
								"res.partner");
						id = createLeadWithoutReferalSource(map, propadd,
								refcontactidnew, "crm.lead");
						// if lead not exsist
						exsist = 1;

					}
				} else {
					// lead exist with email in submitReferalForm2 in lead
					// module
					int crmLeadid = gHelperClass.searchForLead(RefEmail,
							RefFirstName, RefLastName, "crm.lead");

					if (crmLeadid == 0) {

						int refcontactid = gHelperClass.lookupContactID(
								RefEmail, RefFirstName, RefLastName,
								"res.partner");

						if (refcontactid != 0) {

							log.debug("referal with email : " + RefEmail
									+ " firstname : " + RefFirstName
									+ " lastname : " + RefLastName + " exist");

							id = createLeadWithoutReferalSource(map, propadd,
									refcontactid, "crm.lead");
							// if lead not exsist
							exsist = 1;

						} else {

							log.debug("referal with email : "
									+ RefEmail
									+ " firstname : "
									+ RefFirstName
									+ " lastname : "
									+ RefLastName
									+ "doesnt exist, so creating new contact for referal");
							int refcontactidnew = gHelperClass.createContact(
									RefFirstName, RefLastName, RefEmail,
									"res.partner");
							id = createLeadWithoutReferalSource(map, propadd,
									refcontactidnew, "crm.lead");
							// if lead not exsist
							exsist = 1;

						}
					} else {

						int daysDifference = gHelperClass
								.getDateDifference(crmLeadid);
						if (!propadd.isEmpty()) {

							// gHelperClass.updateLeadAddress(RefEmail,
							// RefFirstName,RefLastName, propadd);

							log.debug("lead with referal email exist");
							int leadAddressExist = gHelperClass
									.lookupLeadAddress(propadd);

							log.debug("checking with address to ensure once more time "
									+ leadAddressExist);

							if (leadAddressExist == 0) {

								int refcontactid = gHelperClass
										.lookupContactID(RefEmail,
												RefFirstName, RefLastName,
												"res.partner");

								if (refcontactid != 0) {

									log.debug("referal with email : "
											+ RefEmail + " firstname : "
											+ RefFirstName + " lastname : "
											+ RefLastName + " exist");

									id = createLeadWithoutReferalSource(map,
											propadd, refcontactid, "crm.lead");
									// if lead not exsist
									exsist = 1;

								} else {

									log.debug("referal with email : "
											+ RefEmail
											+ " firstname : "
											+ RefFirstName
											+ " lastname : "
											+ RefLastName
											+ "doesnt exist, so creating new contact for referal");
									int refcontactidnew = gHelperClass
											.createContact(RefFirstName,
													RefLastName, RefEmail,
													"res.partner");
									id = createLeadWithoutReferalSource(map,
											propadd, refcontactidnew,
											"crm.lead");
									// if lead not exsist
									exsist = 1;

								}

							} else {

								if (daysDifference < 31) {
									// if lead exsist
									exsist = 2;
									// getting exsist lead id for create note
									id = gHelperClass.getLeadId(RefFirstName,
											RefLastName, RefEmail.trim(),
											propadd);
									log.debug("lead already exist");
								} else {

									int refcontactid = gHelperClass
											.lookupContactID(RefEmail,
													RefFirstName, RefLastName,
													"res.partner");

									if (refcontactid != 0) {

										log.debug("referal with email : "
												+ RefEmail + " firstname : "
												+ RefFirstName + " lastname : "
												+ RefLastName + " exist");

										id = createLeadWithoutReferalSource(
												map, propadd, refcontactid,
												"crm.lead");
										// if lead not exsist
										exsist = 1;

									} else {

										log.debug("referal with email : "
												+ RefEmail
												+ " firstname : "
												+ RefFirstName
												+ " lastname : "
												+ RefLastName
												+ "doesnt exist, so creating new contact for referal");
										int refcontactidnew = gHelperClass
												.createContact(RefFirstName,
														RefLastName, RefEmail,
														"res.partner");
										id = createLeadWithoutReferalSource(
												map, propadd, refcontactidnew,
												"crm.lead");
										// if lead not exsist
										exsist = 1;

									}

								}
							}

						} else {

							if (daysDifference < 31) {
								// if lead exsist
								exsist = 2;
								// getting exsist lead id for create note
								id = gHelperClass.getLeadId(RefFirstName,
										RefLastName, RefEmail.trim(), propadd);
								log.debug("lead already exist");
							} else {

								int refcontactid = gHelperClass
										.lookupContactID(RefEmail,
												RefFirstName, RefLastName,
												"res.partner");

								if (refcontactid != 0) {

									log.debug("referal with email : "
											+ RefEmail + " firstname : "
											+ RefFirstName + " lastname : "
											+ RefLastName + " exist");

									id = createLeadWithoutReferalSource(map,
											propadd, refcontactid, "crm.lead");
									// if lead not exsist
									exsist = 1;

								} else {

									log.debug("referal with email : "
											+ RefEmail
											+ " firstname : "
											+ RefFirstName
											+ " lastname : "
											+ RefLastName
											+ "doesnt exist, so creating new contact for referal");
									int refcontactidnew = gHelperClass
											.createContact(RefFirstName,
													RefLastName, RefEmail,
													"res.partner");
									id = createLeadWithoutReferalSource(map,
											propadd, refcontactidnew,
											"crm.lead");
									// if lead not exsist
									exsist = 1;

								}

							}
						}
					}

				}

			} else {
				// referal source exist for contact(data comming from
				// submitReferalForm1)
				log.debug("referal exist with id : " + referalid);

				// search if lead exist with email comming from
				// submitReferalForm2
				leadSize = gHelperClass.lookupLeadByEmail(RefEmail);

				// this means contact exist with data in submitReferalForm2 but
				// not lead

				if (leadSize == 0) {

					// create lead without referal source id and send appropiate
					// message
					int refcontactid = gHelperClass.lookupContactID(RefEmail,
							RefFirstName, RefLastName, "res.partner");

					if (refcontactid != 0) {

						log.debug("referal with email : " + RefEmail
								+ " firstname : " + RefFirstName
								+ " lastname : " + RefLastName + " exist");

						id = createLeadWithReferalSource(map, propadd,
								referalid, refcontactid, "crm.lead");
						// createLeadWithoutReferalSource(map, propadd,
						// refcontactid, "crm.lead");
						// if lead not exsist
						exsist = 1;

					} else {

						log.debug("referal with email : "
								+ RefEmail
								+ " firstname : "
								+ RefFirstName
								+ " lastname : "
								+ RefLastName
								+ "doesnt exist, so creating new contact for referal");
						int refcontactidnew = gHelperClass.createContact(
								RefFirstName, RefLastName, RefEmail,
								"res.partner");

						id = createLeadWithReferalSource(map, propadd,
								referalid, refcontactidnew, "crm.lead");

						// createLeadWithoutReferalSource(map, propadd,
						// refcontactidnew, "crm.lead");
						// if lead not exsist
						exsist = 1;

					}
				} else {
					// lead exist now test with address
					log.debug("lead with email exist");

					int crmLeadid = gHelperClass.searchForLead(RefEmail,
							RefFirstName, RefLastName, "crm.lead");

					if (crmLeadid == 0) {

						int refcontactid = gHelperClass.lookupContactID(
								RefEmail, RefFirstName, RefLastName,
								"res.partner");

						if (refcontactid != 0) {

							log.debug("referal with email : " + RefEmail
									+ " firstname : " + RefFirstName
									+ " lastname : " + RefLastName + " exist");

							id = createLeadWithReferalSource(map, propadd,
									referalid, refcontactid, "crm.lead");
							// createLeadWithoutReferalSource(map, propadd,
							// refcontactid, "crm.lead");
							// if lead not exsist
							exsist = 1;

						} else {

							log.debug("referal with email : "
									+ RefEmail
									+ " firstname : "
									+ RefFirstName
									+ " lastname : "
									+ RefLastName
									+ "doesnt exist, so creating new contact for referal");
							int refcontactidnew = gHelperClass.createContact(
									RefFirstName, RefLastName, RefEmail,
									"res.partner");

							id = createLeadWithReferalSource(map, propadd,
									referalid, refcontactidnew, "crm.lead");

							// createLeadWithoutReferalSource(map, propadd,
							// refcontactidnew, "crm.lead");
							// if lead not exsist
							exsist = 1;
						}
					} else {

						// int
						// addressExsist=gHelperClass.lookupExsistingLeadAddress(RefEmail,
						// RefFirstName, RefLastName);
						// if(addressExsist!=0){

						// update lead address
						int dayDefference = gHelperClass
								.getDateDifference(crmLeadid);
						if (!propadd.isEmpty()) {

							// gHelperClass.updateLeadAddress(RefEmail,
							// RefFirstName,RefLastName, propadd);

							int leadAddressExist = gHelperClass
									.lookupLeadAddress(propadd);

							log.debug("checking with address to ensure once more time "
									+ leadAddressExist);

							if (leadAddressExist == 0) {

								int refcontactid = gHelperClass
										.lookupContactID(RefEmail,
												RefFirstName, RefLastName,
												"res.partner");

								if (refcontactid != 0) {

									log.debug("referal with email : "
											+ RefEmail + " firstname : "
											+ RefFirstName + " lastname : "
											+ RefLastName + " exist");

									id = createLeadWithReferalSource(map,
											propadd, referalid, refcontactid,
											"crm.lead");
									// createLeadWithoutReferalSource(map,
									// propadd,
									// refcontactid, "crm.lead");
									// if lead not exsist
									exsist = 1;

								} else {

									log.debug("referal with email : "
											+ RefEmail
											+ " firstname : "
											+ RefFirstName
											+ " lastname : "
											+ RefLastName
											+ "doesnt exist, so creating new contact for referal");
									int refcontactidnew = gHelperClass
											.createContact(RefFirstName,
													RefLastName, RefEmail,
													"res.partner");

									id = createLeadWithReferalSource(map,
											propadd, referalid,
											refcontactidnew, "crm.lead");

									// createLeadWithoutReferalSource(map,
									// propadd,
									// refcontactidnew, "crm.lead");
									// if lead not exsist
									exsist = 1;

								}

							} else {
								// if lead exsist
								if (dayDefference < 31) {
									exsist = 2;

									// getting exsist lead id for create note
									id = gHelperClass.getLeadId(RefFirstName,
											RefLastName, RefEmail.trim(),
											propadd);
									log.debug("lead already exist");
								} else {

									int refcontactid = gHelperClass
											.lookupContactID(RefEmail,
													RefFirstName, RefLastName,
													"res.partner");

									if (refcontactid != 0) {

										log.debug("referal with email : "
												+ RefEmail + " firstname : "
												+ RefFirstName + " lastname : "
												+ RefLastName + " exist");

										id = createLeadWithReferalSource(map,
												propadd, referalid,
												refcontactid, "crm.lead");
										// createLeadWithoutReferalSource(map,
										// propadd,
										// refcontactid, "crm.lead");
										// if lead not exsist
										exsist = 1;

									} else {

										log.debug("referal with email : "
												+ RefEmail
												+ " firstname : "
												+ RefFirstName
												+ " lastname : "
												+ RefLastName
												+ "doesnt exist, so creating new contact for referal");
										int refcontactidnew = gHelperClass
												.createContact(RefFirstName,
														RefLastName, RefEmail,
														"res.partner");

										id = createLeadWithReferalSource(map,
												propadd, referalid,
												refcontactidnew, "crm.lead");

										// createLeadWithoutReferalSource(map,
										// propadd,
										// refcontactidnew, "crm.lead");
										// if lead not exsist
										exsist = 1;

									}

								}
							}
						} else {

							if (dayDefference < 31) {
								exsist = 2;

								// getting exsist lead id for create note
								id = gHelperClass.getLeadId(RefFirstName,
										RefLastName, RefEmail.trim(), propadd);
								log.debug("lead already exist");
							} else {

								int refcontactid = gHelperClass
										.lookupContactID(RefEmail,
												RefFirstName, RefLastName,
												"res.partner");

								if (refcontactid != 0) {

									log.debug("referal with email : "
											+ RefEmail + " firstname : "
											+ RefFirstName + " lastname : "
											+ RefLastName + " exist");

									id = createLeadWithReferalSource(map,
											propadd, referalid, refcontactid,
											"crm.lead");
									// createLeadWithoutReferalSource(map,
									// propadd,
									// refcontactid, "crm.lead");
									// if lead not exsist
									exsist = 1;

								} else {

									log.debug("referal with email : "
											+ RefEmail
											+ " firstname : "
											+ RefFirstName
											+ " lastname : "
											+ RefLastName
											+ "doesnt exist, so creating new contact for referal");
									int refcontactidnew = gHelperClass
											.createContact(RefFirstName,
													RefLastName, RefEmail,
													"res.partner");

									id = createLeadWithReferalSource(map,
											propadd, referalid,
											refcontactidnew, "crm.lead");

									// createLeadWithoutReferalSource(map,
									// propadd,
									// refcontactidnew, "crm.lead");
									// if lead not exsist
									exsist = 1;

								}
							}
						}

					}

				}

			}

		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(exsist);
		list.add(id);
		return list;
	
	}

	/**
	 * This method is called when we need to create contact and corresponding
	 * lead at one short
	 * 
	 * @param map
	 * @param propAdd
	 * @param module1
	 * @param module2
	 * @throws Exception
	 */
	public int createContactAndLead(HashMap map, HashMap propAdd,
			String module1, String module2) throws Exception {
		// to create note list taking id
		int id = 0;
		String referralId=(String)map.get("referralIdFound");

		String RefFirstName = (String) map.get("FirstName_of_referal");
		String RefLastName = (String) map.get("LastName_of_referal");
		String phoneNumber = (String) map.get("phoneNumber_of_referal");
		String RefEmail = (String) map.get("Email_of_referal");
		String refAddress = (String) map.get("address_of_referal");
		String firstname = (String) map.get("Referal_Source_FirstName");
		String lastname = (String) map.get("Referal_Source_LastName");
		String email = (String) map.get("Referal_Source_Email");

		log.debug("inside createContactAndLead method of ReferalCreateLead");

		// creating contact from data comming from submitReferalForm2 under
		// odoo contact module
		openERPSession = gHelperClass.getOdooConnection();

		int contactId = gHelperClass.createContact(RefFirstName.trim(),
				RefLastName.trim(), RefEmail.trim(), "res.partner");
		log.debug("contact id : " + contactId);

		// checking whether contact exist in odoo contact module using data
		// comming from submitReferalForm1
		// reason is to check referal contact exist and get its referal
		// resource id
	int referalsource = 0;
		
try{
		referalsource= Integer.parseInt(   referralId);
	}catch(Exception e){
		
	}
		

		// this means contact exist but referal-resource in human resource
		// doestnot exist
		if (referalsource == 0) {

			log.debug("no reference exist");

			// create lead without referal source and send appropiate
			// message to support team

			/*
			 * email.sendEmailMortgage(
			 * "there has been a referral submitted, but we are unable to match to a referral source"
			 * , map.toString());
			 */
			id = createLeadWithoutReferalSource(map, propAdd, contactId,
					module2);

		} else {

			log.debug("referal exist : " + referalsource);

			// referal source exist in human department module of odoo

			// create lead with referal source and send appropiate mail
			id = createLeadWithReferalSource(map, propAdd, referalsource,
					contactId, module2);

		}// end of else
		return id;

	}

	/**
	 * create led without referal-source
	 * 
	 * @param map
	 * @param propAdd
	 * @param contactId
	 * @param module
	 * @throws XmlRpcException
	 * @throws OpeneERPApiException
	 */
	// create lead without referal source
	public int createLeadWithoutReferalSource(HashMap map, HashMap propAdd,
			int contactId, String module) throws XmlRpcException,
			OpeneERPApiException {

		log.debug("inside createLeadWithoutReferalSource method of ReferalCreateLead class");

		String Referal_Source_Email = (String) map.get("Referal_Source_Email");
		String Referal_Source_FirstName = (String) map
				.get("Referal_Source_FirstName");
		String Referal_Source_LastName = (String) map
				.get("Referal_Source_LastName");
		String FirstName_of_referal = (String) map.get("FirstName_of_referal");
		String LastName_of_referal = (String) map.get("LastName_of_referal");
		String Email_of_referal = (String) map.get("Email_of_referal");
		String phoneNo_of_referal = (String) map.get("phoneNumber_of_referal");

		log.debug("no reference exist");
		int stateid = gHelperClass.getStateCode(propAdd, "res.country.state");
		log.debug("state id : " + stateid);
		openERPSession = gHelperClass.getOdooConnection();
		ObjectAdapter Leadpartner = openERPSession.getObjectAdapter(module);
		// ObjectAdapter mail = openERPSession.getObjectAdapter("mail.message");

		log.debug("create lead : ");
		// create lead by taking customerid TODO
		Row newLead = Leadpartner.getNewRow(new String[] { "name",
				"email_from","mobile", "partner_id", "street", "street2", "city",
				"state_id", "stage_id" });
		newLead.put("name", (String) map.get("FirstName_of_referal") + "_ "
				+ (String) map.get("LastName_of_referal"));
		newLead.put("email_from", (String) map.get("Email_of_referal"));
		newLead.put("partner_id", contactId);
		newLead.put("mobile",phoneNo_of_referal);
		newLead.put("street", (String) propAdd.get("address1"));
		newLead.put("street2", (String) propAdd.get("street"));
		newLead.put("city", (String) propAdd.get("city"));
		newLead.put("state_id", stateid);
		//newLead.put("stage_id", 10);
		newLead.put("stage_id", 6);

		Leadpartner.createObject(newLead);
		int crmleadId = newLead.getID();
		log.debug("in createLeadWithoutReferalSource lead created  with this Id "
				+ crmleadId);
		log.debug("update lead with correct team");

		// UpdateLead so that it assign to Correct Team
		FilterCollection filter = new FilterCollection();
		filter.add("name", "=", (String) map.get("FirstName_of_referal") + "_ "
				+ (String) map.get("LastName_of_referal"));
		RowCollection partners = Leadpartner.searchAndReadObject(filter,
				new String[] { "name", "email", "hr_department_id" });

		// we should do some validation here to see if the Lead was found
		Row updateRow = partners.get(0);
		updateRow.put("hr_department_id", 1);
		// Tell writeObject to only write changes i.e the name isn't updated
		// because it wasn't changed,same for otherField.
		boolean success = Leadpartner.writeObject(updateRow, true);

		if (success)
			log.debug("create Lead was successful");
		// create stage of lead to pending Application
		// Leadpartner.executeWorkflow(newLead, "6");
		// to add notes

		// CreateNote.createNote(crmleadId, "referrar is not fornd",
		// "crm.lead");
		// if referralresoursce not found send mail to busdev@visdom.ca
		/*
		 * LeadChangingToPendingApplicationMessageTemplate
		 * .LeadChangingToPendingApplicationMessageTemplateMethod(
		 * Referal_Source_FirstName, Referal_Source_LastName,
		 * Referal_Source_Email, FirstName_of_referal, LastName_of_referal,
		 * Email_of_referal, phoneNo_of_referal, crmleadId, "crm.lead");
		 */
	/*	SendMessageToLead.SendMessageToLeadMethod(Referal_Source_FirstName,
				Referal_Source_LastName, Referal_Source_Email,
				FirstName_of_referal, LastName_of_referal, Email_of_referal,
				crmleadId, "crm.lead");*/

		
		try{
			new SendWithUsExample().sendTOclientMortgageApplication(crmleadId+"", FirstName_of_referal+" "+LastName_of_referal, Referal_Source_FirstName, Referal_Source_LastName, Email_of_referal, Referal_Source_Email);
			}catch(Exception e){
				
			}
		/*com.syml.mail.leadMailTemplate.RefrerralNotFoundMessageTemplate
				.RefrerralNotFoundMessageTemplateMethod(
						Referal_Source_FirstName, Referal_Source_LastName,
						Referal_Source_Email, FirstName_of_referal,
						LastName_of_referal, Email_of_referal, crmleadId,
						"crm.lead");*/
		//MailToSupportOnReferralNotFound.mailToSupportOnReferralNotFound(FirstName_of_referal, LastName_of_referal, "support@visdom.ca", Referal_Source_Email, Referal_Source_FirstName);
		try{
		new SendWithUsExample().sentToSupportReferralMissing(FirstName_of_referal +" "+LastName_of_referal,"support@visdom.ca");
		}catch(Exception e){
			
		}
		/*
		 * email.sendEmailMortgage( "Lead Creation Without Referal Source",
		 * "Hi, Creating lead without referal source, since no referal found with specified data"
		 * );
		 */

		return crmleadId;
	}

	/**
	 * create lead with referal source
	 * 
	 * @param map
	 * @param propAdd
	 * @param referalsource
	 * @param contactId
	 * @param module
	 * @throws XmlRpcException
	 * @throws OpeneERPApiException
	 */
	// create lead with referal source
	public int createLeadWithReferalSource(HashMap map, HashMap propAdd,
			int referalsource, int contactId, String module)
			throws XmlRpcException, OpeneERPApiException {

		log.debug("inside createLeadWithReferalSource method of ReferalCreateLead class ");

		int crmleadId = 0;
		String Referal_Source_Email = (String) map.get("Referal_Source_Email");
		String Referal_Source_FirstName = (String) map
				.get("Referal_Source_FirstName");
		String Referal_Source_LastName = (String) map
				.get("Referal_Source_LastName");
		String FirstName_of_referal = (String) map.get("FirstName_of_referal");
		String LastName_of_referal = (String) map.get("LastName_of_referal");
		String Email_of_referal = (String) map.get("Email_of_referal");
		String phoneNo_of_referal = (String) map.get("phoneNumber_of_referal");

		log.debug("referal exist : " + referalsource);

		int stateid = gHelperClass.getStateCode(propAdd, "res.country.state");
		log.debug("state id : " + stateid);
		openERPSession = gHelperClass.getOdooConnection();

		ObjectAdapter Leadpartner = openERPSession.getObjectAdapter(module);

		log.debug("create lead : ");
		// create lead by taking customerid TODO
		Row newLead = Leadpartner.getNewRow(new String[] { "name",
				"email_from","mobile", "partner_id", "referred_source", "street",
				"street2", "city", "state_id", "stage_id" });
		newLead.put("name", (String) map.get("FirstName_of_referal") + "_ "
				+ (String) map.get("LastName_of_referal"));
		newLead.put("email_from", (String) map.get("Email_of_referal"));
		newLead.put("partner_id", contactId);
		newLead.put("mobile",phoneNo_of_referal);
		
		newLead.put("referred_source", referalsource);
		newLead.put("street", (String) propAdd.get("address1"));
		newLead.put("street2", (String) propAdd.get("street"));
		newLead.put("city", (String) propAdd.get("city"));
		newLead.put("state_id", stateid);
		newLead.put("stage_id", 6);
		//newLead.put("stage_id", 10);

		Leadpartner.createObject(newLead);
		crmleadId = newLead.getID();
		log.debug("in createLeadReferalSource lead created with" + crmleadId);
		log.debug("update lead with correct team");

		// UpdateLead so that it assign to Correct Team
		FilterCollection filter = new FilterCollection();
		filter.add("name", "=", (String) map.get("FirstName_of_referal") + "_ "
				+ (String) map.get("LastName_of_referal"));
		RowCollection partners = Leadpartner.searchAndReadObject(filter,
				new String[] { "name", "email", "hr_department_id" });

		// we should do some validation here to see if the Lead was found
		Row updateRow = partners.get(0);
		updateRow.put("hr_department_id", 1);
		// Tell writeObject to only write changes i.e the name isn't updated
		// because it wasn't changed,same for otherField.
		boolean success = Leadpartner.writeObject(updateRow, true);

		if (success)
			log.debug("create Lead was successful");
		// create stage of lead to pending Application
		// Leadpartner.executeWorkflow(newLead, "6");

		// LeadChangingToPendingApplicationMessageTemplate.LeadChangingToPendingApplicationMessageTemplateMethod(
		// Referal_Source_FirstName,
		// Referal_Source_LastName,Referal_Source_Email, FirstName_of_referal,
		// LastName_of_referal, Email_of_referal,phoneNo_of_referal, crmleadId,
		// "crm.lead");
		try{
		new SendWithUsExample().sendTOclientMortgageApplication(crmleadId+"", FirstName_of_referal+" "+LastName_of_referal, Referal_Source_FirstName, Referal_Source_LastName, Email_of_referal, Referal_Source_Email);
		}catch(Exception e){
			
		}
		/*	SendMessageToLead.SendMessageToLeadMethod(Referal_Source_FirstName,
				Referal_Source_LastName, Referal_Source_Email,
				FirstName_of_referal, LastName_of_referal, Email_of_referal,
				crmleadId, "crm.lead");*/
		return crmleadId;

	}

}
