/**
 * Testing DAO
 */
package postgresdao.testdao;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import document.RequiredDocumentBuilder;
import dto.ApplicantDocument;
import dto.Opportunity;
import postgresdao.PostgresDAOI;
import postgresdao.factory.DAOFactory;
import postgresdao.factory.DBConnectionFactory;
import postgresdao.impl.PostgresDAO;
/**
 * 
 * @author bizruntime
 *
 */
public class TestDao {
	static final Logger LOGGER = LoggerFactory.getLogger(PostgresDAO.class);
	public static void main(String[] args) throws JSONException {
		LOGGER.info(" Inside TestDao main ");
		try{
			System.out.println("------------------");
			System.out.println(DBConnectionFactory.getConnection());	
		}catch(Exception e){
		}
		PostgresDAOI postgresDao=DAOFactory.getPostgresDAO();
	Opportunity opportunity=	postgresDao.getOpportunityDetails("4337");
	
ApplicantDocument ap=	new RequiredDocumentBuilder().RequiredDocumentBuilderMethod(opportunity);
	//new PostgresDAO().getApplicantProperties("2894");
	System.out.println("opporunity datat "+ap);
		//System.out.println(employeeDao);
//		employeeDao.updateEmployee(104, "Ram");
		
		
		}
		
	}




