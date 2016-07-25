package email;

import helper.Applicants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.core.enhancers.PropertiesEnhancer.RewrittenAccessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendwithus.SendWithUs;
import com.sendwithus.SendWithUsSendRequest;
import com.sendwithus.exception.SendWithUsException;
import com.sendwithus.model.DeactivatedDrips;
import com.sendwithus.model.Email;
import com.sendwithus.model.SendReceipt;

import document.MortgageDocumentConverter;
import dto.ApplicantDocument;

public class SendWithUsMail {

	static Logger log = LoggerFactory.getLogger(SendWithUsMail.class);

	public static final String SENDWITHUS_API_KEY = "live_a7c95c3b0fb3acb519463955b1a2be67b2299734";
	public static final String EMAIL_ID_WELCOME_EMAIL = "tem_Brw9vS6AxGCMw3UGHNszkB";
	public static final String EMAIL_ID_WELCOME_EMAIL1 = "tem_PQdWYdrHYFbNpJ6CsdKScT";
	public static final String EMAIL_ID_WELCOME_EMAIL2 = "tem_c6ApWRUiPtRZJXqgnik4hj";
	public static final String EMAIL_ID_WELCOME_EMAIL3 = "tem_su5msryYNEqXUvdV9CnNgb";
	public static final String EMAIL_ID_WELCOME_EMAIL4 = "tem_TyU3tNBCEXi5s78vDTMpze";
	public static final String EMAIL_ID_WELCOME_EMAIL5 = "tem_okPxEtZ4jLCQNb2n5Jy2Fd";

	public static String fromEmail = "support@visdom.ca";

	public static void main(String[] args) {
		getGreeting();
		System.out.println("" + getGreeting());
		// new SendWithUsMail().mailTemplateOfPreeApprovalDocuments(n,
		// applicantDocumentsArrayList, co_ApplicantDocumentsArrayList,
		// propertyDocumentsArrayList, downpaymentDocumentsArrayList,
		// creditDocArrayList)
	}

	public void mailTemplateOfPreeApprovalDocuments(ApplicantDocument docList,
			Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocArrayList) throws IOException {
		log.debug("inside mailTemplateOfPreeApprovalDocuments--------------");
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", docList.getFirstName()); // optional
	 recipientMap.put("address", docList.getEmailAddress());

		//recipientMap.put("address", "venkatesh.m@bizruntime.com");
		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if (docList.getCo_emailAddress() != null) { ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0] = ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Pre-Approval documents required"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getFirstName() + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getCo_firstName() + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!creditDocArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = creditDocArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (creditDocArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Credit Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {

				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);
				log.debug("mail sent including co-Applicants mailTemplateOfPreeApprovalDocuments--------------");

			} else {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL, recipientMap,
						senderMap, emailDataMap, null, bbMap

				);

				log.debug("mail sent to Applicants mailTemplateOfPreeApprovalDocuments--------------");

			}

		} catch (Exception e) {
			mailTemplateOfPreeApprovalDocuments(docList,
					applicantDocumentsArrayList,
					co_ApplicantDocumentsArrayList, propertyDocumentsArrayList,
					downpaymentDocumentsArrayList, creditDocArrayList);
			e.printStackTrace();
		}

	}

	public void mailTemplateOfPreeApprovalCoApplicantDocuments(
			ApplicantDocument docList, Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocArrayList) throws IOException {
		log.debug("inside mailTemplateOfPreeApprovalDocuments--------------");
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", docList.getCo_firstName()); // optional
		 recipientMap.put("address", docList.getCo_emailAddress());
		//recipientMap.put("address", "venkatesh.m@bizruntime.com");

		System.out.println("email co " + docList.getCo_emailAddress());
		// recipientMap.put("address", "venkatesh.m@bizruntime.com");
		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if (docList.getCo_emailAddress() != null) { ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0] = ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Pre-Approval documents required"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getFirstName() + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getCo_firstName() + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!creditDocArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = creditDocArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (creditDocArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Credit Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {

				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);
				log.debug("mail sent including co-Applicants mailTemplateOfPreeApprovalDocuments--------------");

			} else {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL, recipientMap,
						senderMap, emailDataMap, null, bbMap

				);

				log.debug("mail sent to Applicants mailTemplateOfPreeApprovalDocuments--------------");

			}

		} catch (Exception e) {
			mailTemplateOfPreeApprovalCoApplicantDocuments(docList,
					applicantDocumentsArrayList,
					co_ApplicantDocumentsArrayList, propertyDocumentsArrayList,
					downpaymentDocumentsArrayList, creditDocArrayList);
			e.printStackTrace();
		}

	}

	public static void mailTemplateOfPurchaseDocuments(
			ApplicantDocument docList, Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocArraylist) throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		log.debug("inside mailTemplateOfPurchaseDocuments--------------");

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", docList.getFirstName()); // optional
	//	recipientMap.put("address", "venkatesh.m@bizruntime.com");

		 recipientMap.put("address", docList.getEmailAddress());

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if (docList.getCo_emailAddress() != null) { ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0] = ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Purchase documents required "); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getFirstName() + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getCo_firstName() + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!creditDocArraylist.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = creditDocArraylist.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (creditDocArraylist.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Credit Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and  "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL1, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);

				log.debug("sent mail  mailTemplateOfPurchaseDocuments--------------");

			} else {
				SendReceipt sendReceipt1 = sendwithusAPI.send(
						EMAIL_ID_WELCOME_EMAIL1, recipientMap, senderMap,
						emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {

			mailTemplateOfPurchaseDocuments(docList,
					applicantDocumentsArrayList,
					co_ApplicantDocumentsArrayList, propertyDocumentsArrayList,
					downpaymentDocumentsArrayList, creditDocArraylist);

			e.printStackTrace();
		}

	}

	public static void mailTemplateOfCoApplicantPurchaseDocuments(
			ApplicantDocument docList, Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocArraylist) throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		log.debug("inside mailTemplateOfPurchaseDocuments--------------");

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", docList.getCo_firstName()); // optional
		//recipientMap.put("address", "venkatesh.m@bizruntime.com");

	 recipientMap.put("address", docList.getCo_emailAddress());

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if (docList.getCo_emailAddress() != null) { ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0] = ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Purchase documents required "); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getFirstName() + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getCo_firstName() + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!creditDocArraylist.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = creditDocArraylist.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (creditDocArraylist.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Credit Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL1, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);

				log.debug("sent mail  mailTemplateOfPurchaseDocuments--------------");

			} else {
				SendReceipt sendReceipt1 = sendwithusAPI.send(
						EMAIL_ID_WELCOME_EMAIL1, recipientMap, senderMap,
						emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {

			mailTemplateOfCoApplicantPurchaseDocuments(docList,
					applicantDocumentsArrayList,
					co_ApplicantDocumentsArrayList, propertyDocumentsArrayList,
					downpaymentDocumentsArrayList, creditDocArraylist);

			e.printStackTrace();
		}

	}

	public static void mailTemplateOfRefinanceDocuments(
			ApplicantDocument docList, Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocumentList) throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", docList.getFirstName()); // optional
		recipientMap.put("address", docList.getEmailAddress());
	//	recipientMap.put("address", "venkatesh.m@bizruntime.com");

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if (docList.getCo_emailAddress() != null) { ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0] = ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Refinance  documents required"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4 style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getFirstName() + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getCo_firstName() + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!creditDocumentList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = creditDocumentList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (creditDocumentList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Credit Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL2, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);
			} else {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL2, recipientMap,
						senderMap, emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {
			mailTemplateOfRefinanceDocuments(docList,
					applicantDocumentsArrayList,
					co_ApplicantDocumentsArrayList, propertyDocumentsArrayList,
					downpaymentDocumentsArrayList, creditDocumentList);
			e.printStackTrace();
		}

	}

	public static void mailTemplateOfCoApplicantRefinanceDocuments(
			ApplicantDocument docList, Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocumentList) throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", docList.getCo_firstName()); // optional
	recipientMap.put("address", docList.getCo_emailAddress());
	//	recipientMap.put("address", "venkatesh.m@bizruntime.com");

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if (docList.getCo_emailAddress() != null) { ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0] = ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Refinance  documents required"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4 style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getFirstName() + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ docList.getCo_firstName() + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!creditDocumentList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = creditDocumentList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (creditDocumentList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Credit Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL2, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);
			} else {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL2, recipientMap,
						senderMap, emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {
			mailTemplateOfCoApplicantRefinanceDocuments(docList,
					applicantDocumentsArrayList,
					co_ApplicantDocumentsArrayList, propertyDocumentsArrayList,
					downpaymentDocumentsArrayList, creditDocumentList);
			e.printStackTrace();
		}

	}

	public static void mailTemplateOfRemianderDocuments(
			ArrayList<Applicants> docList,
			Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> misslenDocumentsArrayList)
			throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		String applicantName = "";
		String applicantEmail = "";
		String coApplicantName = "";
		String coAapplicantEmail = "";
		String coapplicantNameThree = "";
		String coapplicantEmailthree = "";
		int k = 0;
		for (Iterator iterator = docList.iterator(); iterator.hasNext();) {
			Applicants applicants = (Applicants) iterator.next();
			++k;
			switch (k) {
			case 1:
				applicantName = applicants.getApplicantName();
				applicantEmail = applicants.getApplicantEmail();
				break;
			case 2:
				coApplicantName = applicants.getApplicantName();
				coAapplicantEmail = applicants.getApplicantEmail();

				break;
			case 3:
				coapplicantNameThree = applicants.getApplicantName();
				coapplicantEmailthree = applicants.getApplicantEmail();

				break;

			default:
				break;
			}

		}
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", applicantName); // optional
		// recipientMap.put("address", applicantEmail);
		recipientMap.put("address", "venkatesh.m@bizruntime.com");

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		if (!coAapplicantEmail.isEmpty()) {
			ccMap = (Map<String, Object>[]) new Map[1];
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", coAapplicantEmail);
			ccMap[0] = ccMap1;
		}
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Additional Documents required"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		String documentList = "";

		if (!applicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = applicantDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				// System.out.println("size "+data.size() +"=="+ i);
				if (applicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4 style=\"text-decoration: underline;font-weight: bold;\">"
					+ applicantName + " Documents :</h4> <br>";
			documentList += documents + "";

		}
		if (!co_ApplicantDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = co_ApplicantDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (co_ApplicantDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">"
					+ coApplicantName + " Documents :</h4> <br>";
			documentList += documents + "";
		}

		if (!propertyDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = propertyDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (propertyDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Property Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!downpaymentDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = downpaymentDocumentsArrayList
					.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (downpaymentDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Downpayment Documents :</h4><br>";
			documentList += documents + "";
		}

		if (!misslenDocumentsArrayList.isEmpty()) {

			String documents = "";
			Iterator<String> iterator = misslenDocumentsArrayList.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				if (misslenDocumentsArrayList.size() == i) {
					documents += (String) iterator.next() + "<br><br>";

				} else {
					documents += (String) iterator.next() + "<br>";

				}
				++i;
			}
			documentList += "<h4  style=\"text-decoration: underline;font-weight: bold;\">Miscellaneous Documents :</h4><br>";
			documentList += documents + "";
		}

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		if (coApplicantName.isEmpty()) {
			emailDataMap.put("ClientName", applicantName);
		} else {
			emailDataMap.put("ClientName", applicantName + " and "
					+ coApplicantName);
		}
		emailDataMap.put("DocumentsRequiredList", documentList);
		emailDataMap.put("DueDate", currentDateTime);
		emailDataMap.put("ProductCount", 348);
		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (ccMap != null) {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL5, recipientMap,
						senderMap, emailDataMap, ccMap, bbMap

				);
			} else {
				sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL5, recipientMap,
						senderMap, emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {
			e.printStackTrace();
		}

	}

	public static void mailTemplateOfRealtor(ApplicantDocument docList,
			String referrAlName, String email) throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", referrAlName); // optional
		recipientMap.put("address", email);

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if(docList.getCo_emailAddress()!=null){ ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0]=ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name",
				"Financing Documents needed for " + docList.getFirstName()); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		String documentList = "[Accepted Offer to Purchase including all waivers and addendums , MLS Listing].<br><br>";

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		emailDataMap.put("ReferralSourceName", referrAlName);
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}
		emailDataMap.put("ListOfRealtorDocs", documentList);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL3, recipientMap,
					senderMap, emailDataMap, null, bbMap

			);

		} catch (SendWithUsException e) {
			e.printStackTrace();
		}

	}

	public static void mailTemplateOfRealtorDoc(ApplicantDocument docList,
			String referrAlName, String email) throws JsonProcessingException {
		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("name", referrAlName); // optional
		recipientMap.put("address", email);

		// Send Welcome Email
		// Map<String, Object> ccMap = new HashMap<String, Object>();
		// ccMap.put("address", "deeplai.singh@bizruntime.com");

		/*
		 * if(docList.getCo_emailAddress()!=null){ ccMap = (Map<String,
		 * Object>[]) new Map[1]; Map<String, Object> ccMap1 = new
		 * HashMap<String, Object>(); ccMap1.put("address",
		 * docList.getCo_emailAddress()); ccMap[0]=ccMap1; }
		 */
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name",
				"Pre-Approval Financing for " + docList.getFirstName()); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = getGreeting();
		emailDataMap.put("Greeting", mesage + "\t");
		emailDataMap.put("ReferralSourceName", referrAlName);
		if (docList.getCo_firstName() == null) {
			emailDataMap.put("ClientName", docList.getFirstName());
		} else {
			emailDataMap.put("ClientName", docList.getFirstName() + " and "
					+ docList.getCo_firstName());
		}

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL4, recipientMap,
					senderMap, emailDataMap, null, bbMap

			);

		} catch (SendWithUsException e) {
			e.printStackTrace();
		}

	}

	public static String getGreeting() {
		String greeting = "";
		Calendar time = new GregorianCalendar();

		time.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));

		int hour = time.get(Calendar.HOUR_OF_DAY);
		int min = time.get(Calendar.MINUTE);
		int day = time.get(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH) + 1;
		int year = time.get(Calendar.YEAR);

		System.out.println("The current time is \t" + hour + ":" + min);
		System.out.println("Today's date is \t" + month + "/" + day + "/"
				+ year);

		if (hour < 12) {
			greeting = "Good Morning";
			System.out.println("Good Morning!");
		} else if (hour > 12 && hour < 16) {
			greeting = "Good Afternoon";
			System.out.println("Good Afternoon");
		} else if (hour == 12) {
			greeting = "Good Afternoon";

			System.out.println("Good Afternoon");

		} else if (hour == 16) {
			greeting = "Good Evening";

			System.out.println("Good Evening");

		}

		else if (hour > 16 && hour < 24) {
			greeting = "Good Evening";
		} else
			System.out.println("Good Evening");
		return greeting;

	}
}
