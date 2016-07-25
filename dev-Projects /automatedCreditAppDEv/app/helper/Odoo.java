package helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import play.Logger;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

import controllers.ApplicantPojo;

//import controllers.OpportunityList;

public class Odoo extends Thread {
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

			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername");
			pass = prop.getProperty("odoopassword");

			openERPSession = new Session(ip, port, db, user, pass);
			openERPSession.startSession();

		} catch (Exception e) {
		}

		return openERPSession;
	}

	// connection useed for production -----------------
	public Session getOdooConnection() {

		int port = 0;
		String host = null;
		String db = null;
		String user = null;
		String pass = null;
		int maximumRetry = 0;
		try {

			System.out
					.println("inside getOdooConnection method of Generic Helper  class");
			Properties prop = readConfigfile();
			Logger.debug("stg---" + prop.getProperty("submitReferralstageId")
					+ "---" + prop.getProperty("visdomreferralStageId")
					+ "----" + prop.getProperty("opprtunitySatgeid"));
			// ip=prop.getProperty("odooIP");
			host = prop.getProperty("odooHost1");
			try {
				port = new Integer(prop.getProperty("odooPort"));
			} catch (Exception e) {

			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername1");
			pass = prop.getProperty("odoopassword1");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));
			Logger.debug("");

			openERPSession = getCRMConnectionOne(host, port, db, user, pass,
					maximumRetry);
			if (openERPSession == null) {
				Logger.debug("error in getCRMConnectionOne");
				host = prop.getProperty("odooHost2");
				port = new Integer(prop.getProperty("odooPort2"));
				db = prop.getProperty("odooDB2");
				user = prop.getProperty("odooUsername2");
				pass = prop.getProperty("odoopassword2");

				openERPSession = getCRMConnectionTwo(host, port, db, user,
						pass, maximumRetry);

				if (openERPSession == null) {
					Logger.error("error in getCRMConnectionTwo");
				} else {
					Logger.debug("connection successful");
				}

			}

		} catch (Exception e) {

			Logger.error("error in connectiong with odoo : " + e);
		}

		return openERPSession;
	}

	public Session getCRMConnectionOne(String host, int port, String db,
			String user, String pass, int maximumRetry) {

		System.out
				.println("inside GetCRMConectionOne  method of GenericHelperClass");
		int retry = 1;
		Session openERPSession = null;
		while (retry <= maximumRetry && openERPSession == null) {
			try {
				openERPSession = new Session(host, port, db, user, pass);
				openERPSession.startSession();
			} catch (Exception e) {
				openERPSession = null;
				Logger.debug(" GetCRMConectionOne  Retry..    " + retry);
				retry += 1;
				Logger.debug("error in connecting GetCRMConectionOne " + e);
			}
		}
		return openERPSession;
	}

	public Session getCRMConnectionTwo(String host, int port, String db,
			String user, String pass, int maximumRetry) {

		Logger.debug("inside GetCRMConectionTwo method of GenericHelperClass");
		int retry = 1;
		Session openERPSession = null;
		while (retry <= maximumRetry && openERPSession == null) {
			try {
				openERPSession = new Session(host, port, db, user, pass);
				openERPSession.startSession();
			} catch (Exception e) {
				openERPSession = null;
				Logger.error(" GetCRMConectionTwo  Retry..    " + retry);
				retry += 1;
				Logger.error("error in connecting GetCRMConectionTwo " + e);
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
				Logger.debug(referralList.size() + "id");

				for (Row row : referralList) {
					list.add(row.get("name").toString());
					list.add(row.get("email_from").toString());
					list.add(row.get("role").toString());
				}
			}
		} catch (Exception e) {
			Logger.error("error in getting the referral  Name " + e);
		}

		return list;
	}

	public Properties readConfigfile() {

		Properties prop = new Properties();
		try {
			prop.load(Odoo.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (Exception e) {
		}
		return prop;
	}

	/*
	 * public ArrayList<OpportunityList> getOpprunityDetials(){
	 * ArrayList<OpportunityList> list=new ArrayList<OpportunityList>(); try{
	 * 
	 * Session openERPSession=new Odoo().getOdooConnection();
	 * 
	 * ObjectAdapter lead=openERPSession.getObjectAdapter("crm.lead");
	 * 
	 * FilterCollection filters2=new FilterCollection();
	 * 
	 * 
	 * RowCollection leadRow= lead.searchAndReadObject(filters2, new
	 * String[]{"id","name","create_date"});
	 * 
	 * 
	 * 
	 * for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) { Row
	 * row1 = (Row) iterator.next();
	 * 
	 * int days=0; Calendar calendar1=Calendar.getInstance(); Calendar
	 * calendar2=Calendar.getInstance(); Date dateString=(Date)
	 * row1.get("create_date"); calendar1.setTime(dateString); SimpleDateFormat
	 * formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss"); String
	 * date2=formatter.format(calendar2.getTime()); String
	 * date1=formatter.format(calendar1.getTime()); //
	 * System.out.println("date 1"+ date2 +"dat3 "+ date1); days=(int)(
	 * (formatter.parse(date2).getTime() - formatter.parse(date1).getTime())/
	 * (1000 * 60 * 60 * 24) );
	 * 
	 * 
	 * if(days<150){
	 * 
	 * OpportunityList opportunityList=new OpportunityList(); try{
	 * opportunityList.setOpportunityId(row1.get("id").toString());
	 * opportunityList.setOpportunityName(row1.get("name").toString());
	 * list.add(opportunityList); }catch(Exception e){
	 * 
	 * } }
	 * 
	 * }}catch(Exception e){ e.printStackTrace(); }
	 * 
	 * return list;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * CouchBaseOperation couchbase=new CouchBaseOperation();
	 */

	/*
	 * public Opprtunity getOpprtunityData(String name){
	 * 
	 * Opprtunity opprunity=new Opprtunity(); try { Session openERPSession= new
	 * Odoo().getOdooConnection();
	 * System.out.println("Seesion is"+openERPSession); ObjectAdapter
	 * opprtunity=openERPSession.getObjectAdapter("crm.lead");
	 * 
	 * FilterCollection filter=new FilterCollection(); filter.add("name", "=",
	 * name); RowCollection row=opprtunity.searchAndReadObject(filter,new
	 * String[]{"id","app_rec_ids","name","email_from"}); Row row1=row.get(0);
	 * 
	 * opprunity.setOpprtunityName(row1.get("name").toString());
	 * opprunity.setOpprtunityEmail(row1.get("email_from").toString());
	 * opprunity.setOpprtunityId(row1.get("id").toString());
	 * 
	 * Object [] object=(Object[])row1.get("app_rec_ids");
	 * System.out.println("Object is "+object); Set<Applicants>
	 * setApplicnats=null; ArrayList<Applicants> setApplicanList=new
	 * ArrayList<Applicants>(); for (Object object2 : object) { try{
	 * System.out.println("inside for Of Object");
	 * 
	 * 
	 * ObjectAdapter
	 * applicants=openERPSession.getObjectAdapter("applicant.record");
	 * System.out.println(object2.toString()); FilterCollection filter1=new
	 * FilterCollection(); filter1.add("id", "=", object2.toString());
	 * RowCollection row2=applicants.searchAndReadObject(filter1,new
	 * String[]{"applicant_name","applicant_last_name","email_personal"});
	 * 
	 * 
	 * for (Row row3 : row2){ Applicants applicant=new Applicants();
	 * 
	 * System.out.println("Row ID: " + row3.getID()); System.out.println("Name:"
	 * + row3.get("applicant_name")); System.out.println("ApplicantLast_Name:" +
	 * row3.get("applicant_last_name")); System.out.println("Email:" +
	 * row3.get("email_personal"));
	 * applicant.setApplicantName(row3.get("applicant_name"
	 * ).toString()+"_"+row3.get("applicant_last_name").toString());
	 * applicant.setApplicantId(object2.toString());
	 * applicant.setApplicantEmail(row3.get("email_personal").toString());
	 * 
	 * setApplicanList.add(applicant);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * System.out.println("Applicant added"); }catch(Exception e){
	 * e.printStackTrace(); }
	 * 
	 * 
	 * } setApplicnats =new LinkedHashSet<Applicants>(setApplicanList);
	 * opprunity.setApplicants(setApplicnats);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return opprunity; }
	 * 
	 * 
	 * 
	 * public String getOpprtunityName(String id){
	 * 
	 * String opporunityName=""; try { Session openERPSession= new
	 * Odoo().getOdooConnection();
	 * System.out.println("Seesion is"+openERPSession); ObjectAdapter
	 * opprtunity=openERPSession.getObjectAdapter("crm.lead");
	 * 
	 * FilterCollection filter=new FilterCollection(); filter.add("id", "=",
	 * id); RowCollection row=opprtunity.searchAndReadObject(filter,new
	 * String[]{"id","name","email_from"}); Row row1=row.get(0);
	 * opporunityName=row1.get("name").toString();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return opporunityName; }
	 */

	public boolean analyzeCreditForApplicant(String opportunityId) {

		boolean beaconValueSucess = false;
		try {

			openERPSession = getOdooConnection();

			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", opportunityId);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "name", "email_from", "app_rec_ids" });
			Row rowData = row.get(0);
			Object[] object = (Object[]) rowData.get("app_rec_ids");
			ObjectAdapter applicantobject = openERPSession
					.getObjectAdapter("crm.lead");
			for (int i = 0; i < object.length; i++) {

				FilterCollection applicantFilter = new FilterCollection();
				applicantFilter.add("id", "=", object[i]);
				RowCollection applicantRow = applicantobject
						.searchAndReadObject(filter, new String[] { "id",
								"beacon_5_score" });
				Row rowApplicantdata = applicantRow.get(0);

				int beaconValue = 0;

				try {
					beaconValue = Integer.parseInt(rowApplicantdata.get(
							"beacon_5_score").toString());
				} catch (Exception e) {

				}
				if (beaconValue > 0) {
					beaconValueSucess = true;
				}

			}
		} catch (Exception e) {

		}

		return beaconValueSucess;
	}

	public String chnageToCreditStage(String oppId) {

		Logger.debug("inside method credit stage change");
		try {
			openERPSession = getOdooConnection();

			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");
			Logger.debug("connection sucessful to the lead module "
					+ opprtunity);

			try {
				FilterCollection filter = new FilterCollection();
				filter.add("id", "=", oppId);
				RowCollection row = opprtunity.searchAndReadObject(filter,
						new String[] { "stage_id", "id", "app_rec_ids" });
				Row rowData = row.get(0);
				rowData.put("stage_id", 16);

				opprtunity.writeObject(rowData, true);
				Logger.debug("Stage  chnages  To  Credit sucessfull  ");

			} catch (Exception e) {
				Logger.debug("error when stage chnageing Credit " + e);

			}

		} catch (Exception e) {
			Logger.debug("error when stage chnageing Credit " + e);
		}

		return oppId;

	}

	public ArrayList<ApplicantPojo> getCreditInformation(String opportunityId) {
		Logger.debug("inside get credit information method ");
		ArrayList<ApplicantPojo> listSucess = new ArrayList<ApplicantPojo>();

		ApplicantPojo applicant = null;
		try {

			openERPSession = getOdooConnection();

			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");
			Logger.debug("searching opporunit with this id " + opportunityId);

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", opportunityId);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "name", "email_from", "app_rec_ids" });
			Row rowData = row.get(0);
			Object[] object = (Object[]) rowData.get("app_rec_ids");
			ObjectAdapter applicantobject = openERPSession
					.getObjectAdapter("applicant.record");
			Logger.debug("Applicant founds size " + object.length);
			String applicant_name = "";

			for (int i = 0; i < object.length; i++) {
				Logger.debug("--------------applicant id--------" + object[i]);

				try {
					applicant = new ApplicantPojo();

					FilterCollection applicantFilter = new FilterCollection();
					applicantFilter.add("id", "=", object[i]);
					RowCollection applicantRow = applicantobject
							.searchAndReadObject(applicantFilter, new String[] {
									"id", "beacon_5_score", "applicant_name",
									"applicant_last_name" });
					Row rowApplicantdata = applicantRow.get(0);

					int beaconValue = 0;
					try {
						beaconValue = Integer.parseInt(rowApplicantdata.get(
								"beacon_5_score").toString());
					} catch (Exception e) {
						Logger.error("error in getting applicant Data " + e);
					}

					Logger.debug("Applicant beacon value  " + beaconValue);
					applicant_name = rowApplicantdata.get("applicant_name")
							.toString()
							+ " "
							+ rowApplicantdata.get("applicant_last_name")
									.toString();
					applicant.setApplicantName(applicant_name);
					applicant.setBeaconvalue(false);
					if (beaconValue > 0) {
						applicant.setBeaconvalue(true);

						/*
						 * beaconValueSucess = true;
						 * 
						 * if (i == 0) { applicant_name = rowApplicantdata.get(
						 * "applicant_name").toString() + " " +
						 * rowApplicantdata.get(
						 * "applicant_last_name").toString(); } else if (i == 1)
						 * { if (!applicant_name.isEmpty()) { applicant_name =
						 * applicant_name + " and " + rowApplicantdata
						 * .get("applicant_name") .toString() + " " +
						 * rowApplicantdata.get( "applicant_last_name")
						 * .toString();
						 * 
						 * } else { applicant_name = rowApplicantdata.get(
						 * "applicant_name").toString() + " " +
						 * rowApplicantdata.get( "applicant_last_name")
						 * .toString();
						 * 
						 * }
						 * 
						 * } Logger.debug(
						 * "Applicant beacon value  greater than zero  ");
						 * Logger.debug("Applicant Name  "+applicant_name);
						 * listSucess.add("true");
						 * listSucess.add(applicant_name);
						 */
					} else {
						/*
						 * if (i == 0) { applcantbeaconValuefailed =
						 * rowApplicantdata.get( "applicant_name").toString() +
						 * " " + rowApplicantdata.get(
						 * "applicant_last_name").toString(); } else if (i == 1)
						 * { if (!applcantbeaconValuefailed.isEmpty()) {
						 * applcantbeaconValuefailed = applicant_name + " and "
						 * + rowApplicantdata .get("applicant_name") .toString()
						 * + " " + rowApplicantdata.get( "applicant_last_name")
						 * .toString();
						 * 
						 * } else { applcantbeaconValuefailed = rowApplicantdata
						 * .get("applicant_name").toString() + " " +
						 * rowApplicantdata.get( "applicant_last_name")
						 * .toString();
						 * 
						 * }
						 * 
						 * }
						 * Logger.debug("Applicant beacon value less than  zero  "
						 * ); Logger.debug("Applicant Name  "+applicant_name);
						 * listSucess.add("false");
						 * listSucess.add(applcantbeaconValuefailed);
						 */
					}

					listSucess.add(applicant);

				} catch (Exception e) {
					Logger.error("error in getting applicant data " + e);
				}

			}
		} catch (Exception e) {
			Logger.error("error in getting applicant data " + e);

		}

		return listSucess;
	}
}
