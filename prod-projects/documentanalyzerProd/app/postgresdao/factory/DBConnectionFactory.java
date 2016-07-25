/**
 * DBConnectionFactory
 */
package postgresdao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import play.Logger;

/**
 * 
 * @author bizruntime
 *
 */
public class DBConnectionFactory {
	private static org.slf4j.Logger logger = play.Logger.underlying();

	public static  Connection getPostGressConnection() {

		Connection connection = null;

		try {
			Class.forName("org.postgresql.Driver");

			Properties prop = new Properties();
			try {

				prop.load(DBConnectionFactory.class.getClassLoader()
						.getResourceAsStream("config.properties"));

			} catch (Exception e) {
				logger.error("error in connecting postgress DB"+e.getMessage());

			}

			String url = prop.getProperty("postgresURL");
			String userName = prop.getProperty("postgresUserName");
			String userPassword = prop.getProperty("postgresPassword");

			try {

				// work fine with just one connection.
				connection = DriverManager.getConnection(url, userName, userPassword);

			} catch (Exception e) {
				try {
					url = prop.getProperty("postgresURL1");
					userName = prop.getProperty("postgresUserName1");
					userPassword = prop.getProperty("postgresPassword1");
					connection = DriverManager.getConnection(url, userName,
							userPassword);
				} catch (Exception e1) {
					logger.error("error in connecting postgress DB"+e.getMessage());

				}
			}
			logger.info("connection succes full with DB postgress");

		} catch (Exception e) {
			logger.error("error in connecting postgress DB"+e.getMessage());
		}
		return connection;
	}

	// Provides the Connection to the requested Database.
	public synchronized static Connection getConnection() throws SQLException {

		return getPostGressConnection();

	}// getConnection

}
