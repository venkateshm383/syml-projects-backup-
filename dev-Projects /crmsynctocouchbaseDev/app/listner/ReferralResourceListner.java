package listner;

import infrastracture.CouchBaseOperation;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.couchbase.client.CouchbaseClient;

import controllers.Application;
import controllers.GetPostgressConnection;

public class ReferralResourceListner extends Thread {
	
	private static org.slf4j.Logger logger = Logger.underlying();

	
	private Connection conn;
	private org.postgresql.PGConnection pgconn;
	CouchBaseOperation couchbase=new CouchBaseOperation();
	CouchbaseClient client=null;

	public ReferralResourceListner(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection)conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN hr_applicant");
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
						stmt1.execute("LISTEN hr_applicant");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					}

				} catch (SQLException e) {

					try {
						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN hr_applicant");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} catch (Exception e1) {
						logger.error("Failed  To  query Referral Soursce  Notification"
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

				org.postgresql.PGNotification notifications[] = pgconn.getNotifications();
				if (notifications != null) {
					
					for (int i=0; i<notifications.length; i++) {
						
									
						logger.debug("inside Referral Got notification: " + notifications[i].getParameter());
						
						JSONObject json=new JSONObject(notifications[i].getParameter());
								String Id="0";
				    	JSONObject oldData=null;
						Id=json.get("id").toString();
						json.put("Type", "Referral");
						json.put("Type_Referral", "Referral");
						json.put("ReferralUrl", Id);
String ReferralFullName="";
						try{
							ReferralFullName=json.getString("name");
						}catch(Exception e){
							logger.error("Error occured while processing JsonData "+e.getMessage());			
						}
						logger.debug("referral full name------------------------------->"+ReferralFullName);
						json.put("ReferralFullName",ReferralFullName);

						try{
							
							client=couchbase.getConnectionToCouchBase();
							try{
								oldData=new JSONObject( client.get("ReferralTrigerData_"+Id).toString());
								
							}catch (Exception e) {
								logger.error("Error occured getting Connection with couchbase "+e.getMessage());
							}
							if(oldData!=null&&oldData.length()!=0){
								oldData.put("Type", "ReferralData");

								couchbase.updateDataInCouchBase("ReferralTrigerData_"+Id, json);
							}else{
								json.put("Type", "ReferralData");

								couchbase.storeDataInCouchBase("ReferralTrigerData_"+Id, json);
							}
						

						}catch (JSONException e) {
							logger.error("Error occured getting processing JsonData "+e.getMessage());
						}	 finally{
							
							client.shutdown(9000l, TimeUnit.MILLISECONDS);
							}
						
						
					}
				}

				// wait a while before checking again for new
				// notifications
				Thread.sleep(500);
			} catch (Exception sqle) {
				logger.error("Error occured while Thread Sleeping "+sqle.getMessage());
			} /*finally{
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
	}
	


}
