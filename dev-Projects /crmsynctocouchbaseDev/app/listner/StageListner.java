package listner;




import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import play.Logger;



public class StageListner extends Thread{
	
	private static org.slf4j.Logger logger = Logger.underlying();

	
	private Connection conn;
	private org.postgresql.PGConnection pgconn;

	public StageListner(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgconn = (org.postgresql.PGConnection)conn;
		Statement stmt = conn.createStatement();
		stmt.execute("LISTEN stage");
		stmt.close();
	}

	public void run() {
		while (true) {
			try {
				// issue a dummy query to contact the backend
				// and receive any pending notifications.
			Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT 1");
				rs.close();
				stmt.close();

				org.postgresql.PGNotification notifications[] = pgconn.getNotifications();
				if (notifications != null) {
					
					for (int i=0; i<notifications.length; i++) {
						
						logger.debug("indie stage Got notification: " + notifications[i].getParameter());
					}
				}

				// wait a while before checking again for new
				// notifications
				//Thread.sleep(500);
			} catch (SQLException e) {
				logger.error("Error  Getting  Postgres  Connection: " + e.getMessage());
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
