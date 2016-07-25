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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.couchbase.client.CouchbaseClient;

import controllers.Application;
import controllers.GetPostgressConnection;

public class AssetListner extends Thread{
	private static org.slf4j.Logger logger = Logger.underlying();
	
	
	private Connection conn;
	private org.postgresql.PGConnection pgconn;
	CouchBaseOperation couchbase=new CouchBaseOperation();
	CouchbaseClient client=null;
	
	public AssetListner(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection)conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN asset");
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
						stmt1.execute("LISTEN asset");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					}

				} catch (SQLException e) {

					try {
						conn = GetPostgressConnection.getPostGressConnection();
						pgconn = (org.postgresql.PGConnection) conn;
						Statement stmt1 = conn.createStatement();
						stmt1.execute("LISTEN asset");
						stmt1.close();

						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT 1");
					} catch (Exception e1) {
						 logger.error("Error occured in JSON object "+e1.getMessage());
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
						
						logger.debug("inside asset Got notification: " + notifications[i].getParameter());
						JSONObject json=new JSONObject(notifications[i].getParameter());
						logger.debug("#####################Applicant Asset is###################");
						
			String id=json.get("opportunity_id").toString();
						
			JSONArray addressData=null;
			JSONObject oldData=null;


				try{
					
					client=couchbase.getConnectionToCouchBase();

					try{
						oldData=new JSONObject( client.get("ApplicantTrigerData_"+id).toString());
						try{
							addressData=new JSONArray(oldData.get("Asset_list").toString());
						}catch (Exception e) {
							 logger.error("Error occured while processing JSON object of applicant"+e.getMessage());
						}
					}catch (Exception e) {
						logger.error("Error occured while connection with couchbase "+e.getMessage());
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
							oldData.put("Asset_list", addressData);
						}else{
							addressData=new JSONArray();
							addressData.put(json);
							oldData.put("Asset_list", addressData);

							
						}
						
						couchbase.updateDataInCouchBase("ApplicantTrigerData_"+id, oldData);
					}else{
						addressData=new JSONArray();
						addressData.put(json);
						oldData=new JSONObject();
						oldData.put("Asset_list", addressData);
						couchbase.storeDataInCouchBase("ApplicantTrigerData_"+id, oldData);
					}
					

				}catch (Exception e) {
					logger.error("--------------Couchbase  Operation  Eror------------"+e.getMessage());

				}	
				finally{
					try{
					client.shutdown(9000l, TimeUnit.MILLISECONDS);
					}catch(Exception e){
						logger.error("-------------Error  while  shuting  down  Coucbase  Connection------------"+e.getMessage());
					}
					}					
					}
				}
				// wait a while before checking again for new
				// notifications
				Thread.sleep(500);
			} catch (Exception e) {
				logger.error("-------------Error  in  statement execution------------"+e.getMessage());
			}
		}
	}
}
