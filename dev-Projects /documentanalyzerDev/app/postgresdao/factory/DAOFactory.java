/**
 * DAOFactory
 */
package postgresdao.factory;

import postgresdao.PostgresDAOI;
import postgresdao.impl.PostgresDAO;

/**
 * 
 * @author bizruntime
 *
 */
public class DAOFactory {

	// PostgressDAOI object
	private static PostgresDAOI pdao;
	static {
		pdao = new PostgresDAO();
	}

	// Provides the PostgressDAO object.
	public static PostgresDAOI getPostgresDAO() {
		return pdao;
	}

}