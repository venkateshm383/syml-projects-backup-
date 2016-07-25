package com.syml.openerp;

import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ReferalToCreateReferralResoursce {
	
	static Session openERPSession = null;
	
	SendEmail email = new SendEmail();
	static Logger log = LoggerFactory.getLogger(ReferalToCreateReferralResoursce.class);
	GenericHelperClass gHelperClass=new GenericHelperClass();
	
	
	
	


	

	public String getCompanyName(String name1, String name2, String name3,String name4) {
		
		log.info("inside GetCompanyname method ");
		String companyName = "";
		if (!(name1.equals("") || name1.length() == 0 || name1 == "")) {
			companyName = name1;
		} else if (!(name2.equals("") || name2.length() == 0 || name2 == "")) {
			companyName = name2;
		} else if (!(name3.equals("") || name3.length() == 0 || name3 == "")) {
			companyName = name3;
		}else if (!(name4.equals("") || name4.length() == 0 || name4 == "")) {
			companyName = name4;
		}
		return companyName;

	}

	public int getExsistingResPartnerId(String fullName) {

		ObjectAdapter resPartner = null;
		log.info(" insid getExsistingRespartnerId by fullName or firstname or last Name ");
		String name[] = fullName.split(" ");
		String firstName = "";
		String lastName = "";
		int resPartnerId = 0;
		try {
			firstName = name[0];
			try {
				lastName = name[1];
			} catch (Exception e) {
				log.error("lastname not exsist in contact ");

			}

		} catch (Exception e) {

			log.error("firstname doesnot exist in who refered you : "+e);
			
			
		}
		
		try {
			openERPSession=gHelperClass.getOdooConnection();
		
			//openERPSession.startSession();

			resPartner = openERPSession.getObjectAdapter("res.partner");
			
			FilterCollection filterCollection = new FilterCollection();
			RowCollection rowCollection = null;
			if (!firstName.equals("") || firstName.length() != 0
					&& lastName.equals("") || lastName.length() == 0) {
				filterCollection.add("name", "=", firstName);

				rowCollection = resPartner.searchAndReadObject(
						filterCollection, new String[] { "name", "last_name" });

			} else if (firstName.equals("") || firstName.length() == 0
					&& !lastName.equals("") || lastName.length() != 0) {
				filterCollection.add("last_name", "=", lastName);

				rowCollection = resPartner.searchAndReadObject(
						filterCollection, new String[] { "name", "last_name" });
			} else {
				filterCollection.add("name", "=", firstName);
				filterCollection.add("last_name", "=", lastName);
				rowCollection = resPartner.searchAndReadObject(
						filterCollection, new String[] { "name", "last_name" });
			}

			log.info("number of referres are " + rowCollection.size());

			if (rowCollection.size()== 1) {
				for (Row row : rowCollection) {
					resPartnerId = row.getID();
				}
			} else if(rowCollection.size()>1)  {
				log.debug("number of Referrers  exsist more than one in contacts");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		return resPartnerId;
	}

	public HashMap updateReferrralDetails(String name, String lastName,
			String email, int partner_id, String phoneNumber, String referrer,
			String role, String comanyName, String brokerNumber,
			int referrerToVisdomHisId, HashMap address, String module) {
		HashMap map=new HashMap();
		
		Row newHrPartner = null;
		ObjectAdapter hrApplicant;
		int rferealid = 0;
		try {
			RowCollection row =null ;
			openERPSession=gHelperClass.getOdooConnection();
			//openERPSession.startSession();
			hrApplicant = openERPSession.getObjectAdapter(module);
			FilterCollection filterCollection=new FilterCollection();
			filterCollection.add("partner_id", "=", partner_id);
			row=hrApplicant.searchAndReadObject(filterCollection, new String[]{ "name",
					"email_from", "partner_id", "partner_phone",
					"partner_mobile", "referred_by",
					"candidate_street", "candidate_city",
					"candidate_state_id", "candidate_zip", "company" });
			newHrPartner = row.get(0);

			log.debug("inside UpdateRefererrel Details Method   and   referal id is : " + newHrPartner.getID());

			if (referrerToVisdomHisId == 0) {

				newHrPartner.put("name", name+"_"+lastName);
				newHrPartner.put("email_from", email);
				newHrPartner.put("partner_id", partner_id);
				newHrPartner.put("partner_phone", phoneNumber);
				newHrPartner.put("partner_mobile", brokerNumber);
				if (!address.isEmpty()) {
					newHrPartner.put("candidate_street",
							address.get("address1"));
					newHrPartner.put("candidate_city", address.get("city"));
					newHrPartner.put("candidate_state_id",
							gHelperClass.getStateCode(address, ""));
					newHrPartner
							.put("candidate_zip", address.get("postalcode"));

				}
				// newHrPartner.put("referred_by", referrerToVisdomHisId);
				// newHrPartner.put("role", role);
				newHrPartner.put("company", comanyName);
			} else {

				newHrPartner.put("name", name+"_"+lastName);
				newHrPartner.put("email_from", email);
				newHrPartner.put("partner_id", partner_id);
				newHrPartner.put("partner_phone", phoneNumber);
				newHrPartner.put("partner_mobile", brokerNumber);
				newHrPartner.put("referred_by", referrerToVisdomHisId);
				if (!address.isEmpty()) {
					newHrPartner.put("candidate_street",
							address.get("address1"));
					newHrPartner.put("candidate_city", address.get("city"));
					newHrPartner.put("candidate_state_id",
							gHelperClass.getStateCode(address, ""));
					newHrPartner
							.put("candidate_zip", address.get("postalcode"));

				}
				// newHrPartner.put("referred_by", referrerToVisdomHisId);
				// newHrPartner.put("role", role);
				newHrPartner.put("company", comanyName);
			}

			Boolean success = hrApplicant.writeObject(newHrPartner, true);
			
			
			String message="";
			if (success) {
				
				rferealid = newHrPartner.getID();
				message="You already registerd with Referrel resoursce  so  we updated with enterd details ";
				map.put("referrelId", rferealid);
				map.put("message", message);
				
			}
			log.info("updated with Referral details is : " + success);

		} catch (Exception e) {
			log.error(e.toString());
			// TODO Auto-generated catch block
			
		}

		return map;

	}

}
