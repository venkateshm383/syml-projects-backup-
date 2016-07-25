package listner;

import infrastracture.CouchBaseOperation;
import infrastracture.Odoo;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.*;
import org.codehaus.jettison.json.*;

import play.Logger;

import com.couchbase.client.CouchbaseClient;

import controllers.Application;
import controllers.GetPostgressConnection;

public class ApplicantListner extends Thread {
	
	private static org.slf4j.Logger logger = Logger.underlying();

	private Connection conn;
	private org.postgresql.PGConnection pgconn;
	CouchBaseOperation couchbase = new CouchBaseOperation();
	CouchbaseClient client = null;

	public ApplicantListner(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection) conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN applicant");
		stmt.close();
	}

	public void run() {
		while (true) {
			try {

				// issue a dummy query to contact the backend
				// and receive any pending notifications.
				Statement stmt = null;
				ResultSet rs = null;
				try {

					if (conn != null) {
						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} else {

						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN applicant");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					}

				} catch (SQLException e) {

					try {
						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN applicant");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} catch (Exception e1) {
						logger.error("Failed  To  query Applicant  Notification"
								+ e1.getMessage());
					}
					finally {
						rs.close();
						stmt.close();
					}

				}

				finally {
					rs.close();
					stmt.close();
				}
				pgconn = (org.postgresql.PGConnection) conn;

				org.postgresql.PGNotification notifications[] = pgconn
						.getNotifications();
				if (notifications != null) {

					for (int i = 0; i < notifications.length; i++) {

						logger.debug("inside Applicant Got notification: "
								+ notifications[i].getParameter());

						JSONObject json = null;
						String applicantId = notifications[i].getParameter();
						ResultSet rs11 = null;

						try {
							Connection connection = null;
							connection = GetPostgressConnection
									.getPostGressConnection();

							Statement stmt11 = conn.createStatement();
							rs11 = stmt11
									.executeQuery("select row_to_json(applicant_record) as applicant_record from applicant_record where id ="
											+ applicantId);
							// logger.debug("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
							while (rs11.next()) {
								logger.debug("*******applicant Data**************");
								logger.debug(rs11.getString("applicant_record"));
								json = new JSONObject(
										rs11.getString("applicant_record"));

							}

						} catch (JSONException e) {
							logger.error("Json  error  while  getting  applicant_record"
									+ e.getMessage());
						} finally {
							try {

								rs11.close();
							} catch (SQLException e) {
								logger.error("ResultSet  close  Erorr"
										+ e.getMessage());
							}
						}

						String aplicant_name = (String) json
								.get("applicant_name");
						String aplicant_lastname = (String) json
								.get("applicant_last_name");

						json.put("Type", "Applicant");
						json.put("Type_applicant", "Applicant");

						logger.debug("\t" + "aplicant_lastname"
								+ aplicant_lastname + "applicant_name"
								+ aplicant_name + "\t");

						String Id = "0";
						JSONObject oldData = null;
						Id = json.get("id").toString();

						String opprtunityName = "";
						try {
							opprtunityName = new Odoo().getOpprtunityName(Id);
						} catch (NullPointerException e) {
							logger.error("Null  value  While  Getting  OppertunityName"
									+ e.getMessage());
						}
						try {
							if (json.get("cell") == null
									&& json.get("cell").equals("")) {
								json.put("cell", "");
							}
						} catch (NullPointerException e) {
							json.put("cell", "");
							logger.error(" Getting  Null  While Cell  Value "
									+ e.getMessage());

						}

						try {
							if (json.get("home") == null
									&& json.get("home").equals("")) {
								json.put("home", "");
							}
						} catch (NullPointerException e) {
							json.put("home", "");
							logger.error(" Getting  Null  While home  Value "
									+ e.getMessage());

						}

						try {
							if (json.get("work") == null
									&& json.get("work").equals("")) {
								json.put("work", "");

							}
						} catch (NullPointerException e) {
							json.put("work", "");
							logger.error(" Getting  Null  While work  Value "
									+ e.getMessage());

						}

						try {
							if (json.get("beacon_5_score") != null) {

								Double double1 = json
										.getDouble("beacon_5_score");
								json.put("beacon_5_score", double1);
							}
						} catch (NullPointerException e) {
							json.put("beacon_5_score", 0);
							logger.error(" Getting  Null  While beacon_5_score  Value "
									+ e.getMessage());

						}

						json.put("opportunity_Name", opprtunityName);
						try {

							client = couchbase.getConnectionToCouchBase();

							try {
								oldData = new JSONObject(client.get(
										"ApplicantTrigerData_" + Id).toString());

							} catch (NullPointerException e) {
								logger.error(" Getting  Null  While ApplicantTrigerData_  Value "
										+ e.getMessage());
							}
							if (oldData != null && oldData.length() != 0) {
								oldData.put("Type", "Applicant");

								couchbase.updateDataInCouchBase(
										"ApplicantTrigerData_" + Id, json);
							} else {

								couchbase.storeDataInCouchBase(
										"ApplicantTrigerData_" + Id, json);
							}
							client.shutdown(9000l, TimeUnit.MILLISECONDS);

						} catch (Exception e) {
							logger.error(" Couchbase  Operation  Error   "
									+ e.getMessage());
						}
					}

					// wait a while before checking again for new
					// notifications
					 Thread.sleep(5000);

					/*
					 * finally{ try { conn.close(); } catch (Exception e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); } }
					 */
				}
			} catch (Exception e) {
				logger.error(" Postgres  Notification    Error   "
						+ e.getMessage());

			}
		}
	}

	public static String getOpportunityName() {

		return null;
	}

}
