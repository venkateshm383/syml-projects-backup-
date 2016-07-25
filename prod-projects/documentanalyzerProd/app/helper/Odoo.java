package helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import play.Logger;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

import controllers.OpportunityList;

public class Odoo extends Thread {
	private static org.slf4j.Logger logger = play.Logger.underlying();
	static Session openERPSession = null;

	public Session getOdooConnectionForProduction() {
		String ip = null;
		int port = 0;
		String db = null;
		String user = null;
		String pass = null;

		try {

			Properties prop = readConfigfile();

			ip = prop.getProperty("odooIP");
			try {
				port = new Integer(prop.getProperty("odooPort"));
			} catch (Exception e) {
				logger.error("Error occured while connection with odoo "+e.getMessage());	
			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername");
			pass = prop.getProperty("odoopassword");

			openERPSession = new Session(ip, port, db, user, pass);
			openERPSession.startSession();

		} catch (Exception e) {
			logger.error("Error occured while connection with odoo "+e.getMessage());
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
			logger.info("stg---" + prop.getProperty("submitReferralstageId")
					+ "---" + prop.getProperty("visdomreferralStageId")
					+ "----" + prop.getProperty("opprtunitySatgeid"));
			// ip=prop.getProperty("odooIP");
			host = prop.getProperty("odooHost1");
			try {
				port = new Integer(prop.getProperty("odooPort"));
			} catch (Exception e) {
				logger.error("Error occured while odooConnection "+e.getMessage());
			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername1");
			pass = prop.getProperty("odoopassword1");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));

			openERPSession = getCRMConnectionOne(host, port, db, user, pass,
					maximumRetry);
			if (openERPSession == null) {
				System.out.println("error in getCRMConnectionOne");
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

			logger.error("error in connectiong with odoo : " + e.getMessage());
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
				logger.error(" GetCRMConectionOne  Retry..    " + retry);
				retry += 1;
				logger.error("error in connecting GetCRMConectionOne " + e.getMessage());
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
				logger.error(" GetCRMConectionTwo  Retry..    " + retry);
				retry += 1;
				logger.error("error in connecting GetCRMConectionTwo " + e.getMessage());
			}

		}
		return openERPSession;
	}

	public static ArrayList<String> getReferralname(String opprtunityId) {

		RowCollection referralList = null;
		ArrayList<String> list = new ArrayList<String>();
		try {

			Session openERPSession = new Odoo().getOdooConnection();

			ObjectAdapter lead = openERPSession.getObjectAdapter("crm.lead");

			FilterCollection filters2 = new FilterCollection();
			filters2.add("id", "=", opprtunityId);

			RowCollection leadRow = lead.searchAndReadObject(filters2,
					new String[] { "id", "referred_source" });

			for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
				Row row1 = (Row) iterator.next();

				ObjectAdapter referral = openERPSession
						.getObjectAdapter("hr.applicant");

				FilterCollection filters1 = new FilterCollection();
				filters1.add("id", "=", row1.get("referred_source"));

				referralList = referral.searchAndReadObject(filters1,
						new String[] { "email_from", "name", "role" });
				logger.info(referralList.size() + "id");

				for (Row row : referralList) {
					list.add(row.get("name").toString());
					list.add(row.get("email_from").toString());
					list.add(row.get("role").toString());
				}
			}
		} catch (Exception e) {
			logger.error("error in getting referral Name"+e.getMessage());
		}

		return list;
	}

	public Properties readConfigfile() {

		Properties prop = new Properties();
		try {
			prop.load(Odoo.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (Exception e) {
			logger.error("error while performing readConfigfile"+e.getMessage());
		}
		return prop;
	}

	public ArrayList<OpportunityList> getOpprunityDetials() {
		ArrayList<OpportunityList> list = new ArrayList<OpportunityList>();
		try {

			Session openERPSession = new Odoo().getOdooConnection();

			ObjectAdapter lead = openERPSession.getObjectAdapter("crm.lead");

			FilterCollection filters2 = new FilterCollection();

			RowCollection leadRow = lead.searchAndReadObject(filters2,
					new String[] { "id", "name", "create_date" });

			for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
				Row row1 = (Row) iterator.next();

				int days = 0;
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				Date dateString = (Date) row1.get("create_date");
				calendar1.setTime(dateString);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yy/MM/dd HH:mm:ss");
				String date2 = formatter.format(calendar2.getTime());
				String date1 = formatter.format(calendar1.getTime());
				// System.out.println("date 1"+ date2 +"dat3 "+ date1);
				days = (int) ((formatter.parse(date2).getTime() - formatter
						.parse(date1).getTime()) / (1000 * 60 * 60 * 24));

				if (days < 150) {

					OpportunityList opportunityList = new OpportunityList();
					try {
						opportunityList.setOpportunityId(row1.get("id")
								.toString());
						opportunityList.setOpportunityName(row1.get("name")
								.toString());
						list.add(opportunityList);
					} catch (Exception e) {
						logger.error("error while performing in getOpprunityDetials"+e.getMessage());
					}
				}

			}
		} catch (Exception e) {
			logger.error("error in getting opporunity detials"+e.getMessage());
		}

		return list;

	}

	CouchBaseOperation couchbase = new CouchBaseOperation();

	public Opprtunity getOpprtunityData(String name) {

		Opprtunity opprunity = new Opprtunity();
		try {
			Session openERPSession = new Odoo().getOdooConnection();
			logger.info("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");

			FilterCollection filter = new FilterCollection();
			filter.add("name", "=", name);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "app_rec_ids", "name", "email_from" });
			Row row1 = row.get(0);

			opprunity.setOpprtunityName(row1.get("name").toString());
			opprunity.setOpprtunityEmail(row1.get("email_from").toString());
			opprunity.setOpprtunityId(row1.get("id").toString());

			Object[] object = (Object[]) row1.get("app_rec_ids");
			logger.info("Object is " + object);
			Set<Applicants> setApplicnats = null;
			ArrayList<Applicants> setApplicanList = new ArrayList<Applicants>();
			for (Object object2 : object) {
				try {
					logger.info("inside for Of Object");

					ObjectAdapter applicants = openERPSession
							.getObjectAdapter("applicant.record");
					logger.info(object2.toString());
					FilterCollection filter1 = new FilterCollection();
					filter1.add("id", "=", object2.toString());
					RowCollection row2 = applicants.searchAndReadObject(
							filter1, new String[] { "applicant_name",
									"applicant_last_name", "email_personal" });

					for (Row row3 : row2) {
						Applicants applicant = new Applicants();

						logger.info("Row ID: " + row3.getID());
						logger.info("Name:" + row3.get("applicant_name"));
						logger.info("ApplicantLast_Name:"
								+ row3.get("applicant_last_name"));
						logger.info("Email:"
								+ row3.get("email_personal"));
						applicant.setApplicantName(row3.get("applicant_name")
								.toString()
								+ "_"
								+ row3.get("applicant_last_name").toString());
						applicant.setApplicantId(object2.toString());
						applicant.setApplicantEmail(row3.get("email_personal")
								.toString());

						setApplicanList.add(applicant);

					}

					logger.info("Applicant added");
				} catch (Exception e) {
					logger.error("error in getting opporunity detials"+e.getMessage());
				}

			}
			setApplicnats = new LinkedHashSet<Applicants>(setApplicanList);
			opprunity.setApplicants(setApplicnats);

		} catch (Exception e) {
			logger.error("error in getting opporunity detials"+e.getMessage());
		}
		return opprunity;
	}

	
	public String getOpprtunityName(String id) {

		String opporunityName = "";
		try {
			Session openERPSession = new Odoo().getOdooConnection();
			logger.info("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", id);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "name", "email_from" });
			Row row1 = row.get(0);
			opporunityName = row1.get("name").toString();

		} catch (Exception e) {
			logger.error("error while processing  getOpprtunityName"+e.getMessage());
		}
		return opporunityName;
	}	
}
