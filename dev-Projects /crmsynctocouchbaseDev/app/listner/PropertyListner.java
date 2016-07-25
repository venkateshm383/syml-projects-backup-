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

import org.codehaus.jettison.*;
import org.codehaus.jettison.json.*;

import play.Logger;

import com.couchbase.client.CouchbaseClient;

import controllers.Application;
import controllers.GetPostgressConnection;


public class PropertyListner extends Thread {
	private static org.slf4j.Logger logger = Logger.underlying();

	private Connection conn;
	private org.postgresql.PGConnection pgconn;
	CouchBaseOperation couchbase=new CouchBaseOperation();
	CouchbaseClient client=null;

	public PropertyListner(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection)conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN property");
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
						stmt1.execute("LISTEN property");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					}

				} catch (SQLException e) {

					try {
						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN property");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} catch (Exception e1) {
						 logger.error("Error occured while connection with postgress "+e1.getMessage());			
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
						
						logger.debug(" insde property Got notification: " + notifications[i].getParameter());
						
						
						JSONObject json=new JSONObject(notifications[i].getParameter());

						logger.debug("#####################Applicant Property is###################");
						
						
			int id=(int)json.get("applicant_id");
						
			JSONArray addressData=null;
			JSONObject oldData=null;


				try{
					
					client=couchbase.getConnectionToCouchBase();
					try{
						oldData=new JSONObject( client.get("ApplicantTrigerData_"+id).toString());
						try{
							addressData=new JSONArray(oldData.get("Property_list").toString());
						}catch (Exception e) {
							logger.error("Error occured while processing Json Data "+e.getMessage());
						}
					}catch (Exception e) {
						 logger.error("Error occured while get connecting with couchbase "+e.getMessage());
					}
					if(oldData!=null&&oldData.length()!=0){
						if(addressData!=null){
							boolean duplicate=false;
							for(int j=0;j<addressData.length();j++){
								JSONObject jsonData=addressData.getJSONObject(j);
								if(json.get("id").toString().equalsIgnoreCase(jsonData.get("id").toString())){
									addressData.put(j, json);
									duplicate=true;
								}
							}
							if(duplicate==false){
							addressData.put(json);
							}
							oldData.put("Property_list", addressData);
						}else{
							addressData=new JSONArray();
							addressData.put(json);
							
							oldData.put("Property_list", addressData);

							
						}
						
						couchbase.updateDataInCouchBase("ApplicantTrigerData_"+id, oldData);
					}else{
						addressData=new JSONArray();
						addressData.put(json);
						oldData=new JSONObject();
						oldData.put("Property_list", addressData);
						couchbase.storeDataInCouchBase("ApplicantTrigerData_"+id, oldData);
					}
				
					client.shutdown(9000l, TimeUnit.MILLISECONDS);

					logger.debug("---------------applicantPropertyData sucesssssssssssssssssssssss--------------");
				}catch (Exception e) {
					logger.error("Error occured while closing connection"+e.getMessage());
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
