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

import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.couchbase.client.CouchbaseClient;

import controllers.Application;
import controllers.GetPostgressConnection;

public class OpprtunityRecomendations extends Thread{
	private static org.slf4j.Logger logger = Logger.underlying();
	
	private Connection conn;
	private org.postgresql.PGConnection pgconn;
	CouchBaseOperation couchbase=new CouchBaseOperation();
	CouchbaseClient client=null;
	public OpprtunityRecomendations(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection)conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN opp_recommendations");
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
						stmt1.execute("LISTEN opp_recommendations");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					}

				} catch (SQLException e) {

					try {
						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN opp_recommendations");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} catch (Exception e1) {
						logger.error("Error occured while connection with Postgress "+e1.getMessage());	
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
						
						Logger.debug("inside opprunity recomendatio Got notification: " + notifications[i].getParameter());
					
					
						JSONObject json=new JSONObject(notifications[i].getParameter());
									
						String Id="0";
				    	JSONObject oldData=null;
						Id=json.get("opp_id").toString();
						

						try{
							
							client=couchbase.getConnectionToCouchBase();
							try{
								oldData=new JSONObject( client.get("OppertunityRecomendtion"+Id).toString());
								
							}catch (Exception e) {
								logger.error("Error occured while connection with Couchbase "+e.getMessage());
							}
							if(oldData!=null&&oldData.length()!=0){
								
								couchbase.updateDataInCouchBase("OppertunityRecomendtion"+Id, json);
							}else{
								
								couchbase.storeDataInCouchBase("OppertunityRecomendtion"+Id, json);
							}
							client.shutdown(9000l, TimeUnit.MILLISECONDS);

						}catch (Exception e) {
							logger.error("Error occured while updating data in Couchbase "+e.getMessage());
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
