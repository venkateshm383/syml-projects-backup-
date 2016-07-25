package listner;

import infrastracture.CouchBaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.couchbase.client.CouchbaseClient;

import controllers.GetPostgressConnection;
import controllers.RestCallClass;

public class LeadListner extends Thread {

	private static org.slf4j.Logger logger = Logger.underlying();

	private Connection conn;
	private org.postgresql.PGConnection pgconn;
	CouchBaseOperation couchbase = new CouchBaseOperation();
	CouchbaseClient client = null;

	public LeadListner(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection) conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN lead");
		stmt.close();
	}

	@SuppressWarnings("resource")
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
						stmt1.execute("LISTEN lead");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					}

				} catch (SQLException e) {

					try {
						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN lead");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} catch (Exception e1) {
						logger.error("Failed  To  query  Lead  Notification"
								+ e1.getMessage());
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

						JSONObject json = new JSONObject(
								notifications[i].getParameter());
						int stage_id = (int) json.get("stage_id");

						json.put("Type", "Opportunity");
						json.put("Type_Opportunity", "Opportunity");

						String Id = "0";
						JSONObject olddata = null;
						Id = json.get("id").toString();
						int referralID = 0;
						String referralName = "";
						json.put("LeadURl", Id);
						String leadName = "";
						try {
							leadName = json.getString("name");
						} catch (Exception e) {
							logger.error("Error occured while processing JsonData "
									+ e.getMessage());
						}
						Calendar cal = Calendar.getInstance();

						json.put("LeadFullName", leadName);

						try {
							referralID = json.getInt("referred_source");
							referralName = getReferralName(referralID);
						} catch (Exception e) {
							logger.error("Error occured while processing Json Referraldata"
									+ e.getMessage());
						}

						json.put("stage_Name", getstageId(stage_id));
						try {
							if (json.get("type").toString()
									.equalsIgnoreCase(("opportunity"))) {
								json.put("Type_convertedToOpportunity",
										"convertedToOpportunity");
							}
						} catch (Exception e) {
							logger.error("Error occured while processing Json Referraldata"
									+ e.getMessage());
						}

						logger.debug("referrral name--------------------------- "
								+ referralName);
						json.put("ReferralName", referralName);
						try {

							client = couchbase.getConnectionToCouchBase();

							try {
								olddata = new JSONObject(client.get(
										"OppertunityTrigerData_" + Id)
										.toString());
							} catch (NullPointerException e) {
								logger.error("Error  Getting OppertunityTrigerData_ name--------------------------- "
										+ e.getMessage());

							}

							try {
								if (json.get("condition_of_financing_date") == null
										&& json.get(
												"condition_of_financing_date")
												.toString().equals("")) {
									json.put("condition_of_financing_date", "");
								}
							} catch (Exception e) {
								logger.error("error while condition_of_financing_date"
										+ e.getMessage());

							}
							try {
								double desiredAmount = json
										.getDouble("desired_mortgage_amount");
								json.put("desired_mortgage_amount",
										desiredAmount);
							} catch (Exception e) {
								logger.error("desired_mortgage_amount"
										+ e.getMessage());

							}

							try {
								if (json.get("expected_closing_date") == null
										&& json.get("expected_closing_date")
												.toString().equals("")) {
									json.put("expected_closing_date", "");
								}
							} catch (Exception e) {
								logger.error("expected_closing_date"
										+ e.getMessage());

							}

							long diffHours = 0;
							Date lastStageDuration = null;
							SimpleDateFormat formatter = new SimpleDateFormat(
									"yy/MM/dd HH:mm:ss");

							try {

								lastStageDuration = formatter.parse(olddata
										.get("lastStageDateTime").toString());
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(lastStageDuration);

								Date currentdate = cal.getTime();

								long diff = currentdate.getTime()
										- lastStageDuration.getTime();
								diffHours = diff / (60 * 60 * 1000) % 24;
							} catch (Exception e) {
								logger.error("expected_closing_date"
										+ e.getMessage());
							}

							if (stage_id == 19) {
								Date today = Calendar.getInstance().getTime();
								DateFormat df = new SimpleDateFormat(
										"MM/dd/yyyy");
								Calendar cal1 = Calendar.getInstance(); // creates
																		// calendar
								cal1.setTime(new Date()); // sets calendar
															// time/date
								cal1.add(Calendar.HOUR_OF_DAY, 1); // adds one
																	// hour
								cal1.getTime();

								Date newDate = DateUtils.addHours(today, 84);

								String expiry_date = df.format(newDate);

								JSONObject jsonObj = new JSONObject();
								jsonObj.put("ExpiryDateTime", expiry_date);

								couchbase.storeDataInCouchBase(
										"ExpiryDateTime_" + Id, jsonObj);

							}
							Calendar calender = Calendar.getInstance();

							json.put("lastStageDateTime",
									formatter.format(calender.getTime()));
							json.put("Lending_Goal", "");

							json.put("stageDuration", diffHours);
							String lendinggoal = "";
							try {
								lendinggoal = getLeandingGoal(Integer
										.parseInt(json.get(
												"what_is_your_lending_goal")
												.toString()));
							} catch (Exception e) {
								logger.error("lending anme error  -------->"
										+ e.getMessage());
							}
							try {
								json.put("Lending_Goal", lendinggoal);
							} catch (Exception e) {
								logger.error("Error occur at lending Goal  -------->"
										+ e.getMessage());
							}
							if (olddata != null && olddata.length() != 0) {
								olddata.put("Type", "Opportunity");

								couchbase.updateDataInCouchBase(
										"OppertunityTrigerData_" + Id, json);
							} else {
								couchbase.storeDataInCouchBase(
										"OppertunityTrigerData_" + Id, json);
							}
							try {
								logger.debug("--------------------------------------lead id-----------------------------------------"
										+ Id);
								logger.debug("--------------------------------------stage_id----------------------------------------"
										+ stage_id);
								JSONObject awiatDocuments = null;

								if (stage_id == 20) {
									logger.debug("when stage id is 18");
									logger.debug("inside awiating docs");
									
									

											logger.debug("inside awiating docs rescall if condtion");

										
													

											JSONObject jsonDocId = new JSONObject();
											jsonDocId.put("id", "" + Id + "");
											new DocumentAnalyzerRestCall(
													jsonDocId.toString())
													.start();
											logger.debug("-------------------------Rest call documentanalyzer app  -------------------------------------------");
											 //RestCallAutomatedApp.restCallAutomatedApp(json.toString());
											// call automated creadit app
											logger.debug("-------------------------Rest call documentanalyzer app with data -------------------------------------------"
													+ jsonDocId.toString());
										//}
									/*} catch (NullPointerException e) {
										logger.error("-------------------------awiatDocuments  Null-------------------------------------------"
												+ e.getMessage());

									}*/
								}
/** end */
								JSONObject auomateddata = null;

							
								if (stage_id == 15) {
									try {

										auomateddata = new JSONObject(client
												.get("automatedData_" + Id)
												.toString());
										logger.debug("Credit stage chnaged -------------");
									} catch (NullPointerException e) {
										logger.error("error in get old stage data"
												+ e.getMessage());
									}

									try {
										if (auomateddata == null) {
											logger.debug(" Credit stage not chnaged  -------------");

											// call to automated app
											auomateddata = new JSONObject();
											auomateddata.put("automatedData",
													stage_id);
											couchbase.storeDataInCouchBase(
													"automatedData_" + Id,
													auomateddata);
											logger.debug("-------------------------Rest call AutomatedApppp -------------------------------------------");
										/*	RestCallAutomatedApp
													.restCallAutomatedApp(json
															.toString());
*/
										} else {

										}
									} catch (NullPointerException e) {
										logger.error("error in auomateddata  is  Null"
												+ e.getMessage());
									} catch (Exception e) {
										logger.error("error in calling making rest call to automated app"
												+ e.getMessage());
									}

								}
								/*RestCallClass.restcallTostagMail(json
										.toString());*/
							} catch (Exception e) {
								logger.error("rest cal restcallTostagMail  "
										+ e.getMessage());

							}

						} catch (Exception e) {
							logger.error("couchbase Connection  Exception  "
									+ e.getMessage());
							
						} finally {
							try {

								client.shutdown(9000l, TimeUnit.MILLISECONDS);
							} catch (Exception e) {
								logger.error("couchbase Connection  Exception    while  Closing "
										+ e.getMessage());

							}
						}

					}
				}

				// wait a while before checking again for new
				// notifications
				Thread.sleep(500);
			} catch (Exception e) {
				logger.error("SQL Connection  Exception    while  Closing "
						+ e.getMessage());
			}
		}

	}

	public String getReferralName(int id) {
		String referralName = "";

		try {
			Connection connection = GetPostgressConnection
					.getPostGressConnection();
			ResultSet rs11 = null;
			try {
				JSONObject referralData = null;
				Statement stmt11 = connection.createStatement();
				rs11 = stmt11
						.executeQuery("select row_to_json(hr_applicant) as hr_applicant from hr_applicant where id ="
								+ id);
				// log.debug("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
				while (rs11.next()) {
					referralData = new JSONObject(
							rs11.getString("hr_applicant"));
					referralName = referralData.getString("name");
				}

			} catch (JSONException e) {
				logger.error("Json  Exception    while  referralData "
						+ e.getMessage());

			} catch (SQLException e) {
				logger.error("SQL  Exception    while  getPostGressConnection "
						+ e.getMessage());

			} catch (Exception e) {
				logger.error("Exception   while  Closing " + e.getMessage());

			} finally {
				try {
					rs11.close();
				} catch (SQLException e) {
					logger.error("Exception   while  Closing " + e.getMessage());

				}
			}
		} catch (Exception e) {
			logger.error("Exception   while  Closing " + e.getMessage());

		}

		return referralName;
	}

	public static String getstageId(int stageId) {
		String stageName = null;
		switch (stageId) {
		case 9:
			stageName = "Gathering Info";
			break;

		case 6:
			stageName = "Pending Application";

			break;
		case 7:
			stageName = "Won";

			break;
		case 8:
			stageName = "Expired";

			break;
		case 10:
			stageName = "Partial App ";

			break;

		case 15:
			stageName = "Completed App";

			break;

		case 16:
			stageName = "Credit";

			break;
		case 22:
			stageName = "Lender Submission";

			break;

		case 23:
			stageName = "Commitment";

			break;
		case 24:
			stageName = "Compensation";

			break;
		case 20:
			stageName = "Proposal";

			break;
		case 25:
			stageName = "Paid";

			break;

		case 18:

			stageName = "Awaiting Docs";
			break;

		case 19:

			stageName = "All Product";
			break;
		case 21:

			stageName = "Post Selection";
			break;

		default:
			stageName = "Pending Application";

			break;
		}
		return stageName;

	}

	public static String getLeandingGoal(int id) {
		String leandingGoal = "";
		switch (id) {
		case 1:
			leandingGoal = "Pre_Approved";
			break;
		case 2:
			leandingGoal = "Purchase";
			break;

		case 3:
			leandingGoal = "Refinance";
			break;
		default:
			break;
		}

		logger.debug("Lending Goal ---------------------------------->"
				+ leandingGoal);
		return leandingGoal;
	}

}
