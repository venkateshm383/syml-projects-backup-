/**
 * Testing DAO
 */
package postgresdao.testdao;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import postgresdao.PostgresDAOI;
import postgresdao.factory.DAOFactory;
import postgresdao.impl.PostgresDAO;
import document.RequiredDocumentBuilder;
/**
 * 
 * @author bizruntime
 *
 */
public class TestDao {
	private static org.slf4j.Logger logger = play.Logger.underlying();
	public static void main(String[] args) throws JSONException, IOException {
		logger.debug(" Inside TestDao main ");
		try{
			logger.info("------------------");
			//System.out.println(DBConnectionFactory.getConnection());	
		}catch(Exception e){
			logger.error("Error while processing TestDao"+e.getMessage());	
		}
		PostgresDAOI postgresDao=DAOFactory.getPostgresDAO();
	
		
	
	
		RequiredDocumentBuilder riBuilder=new RequiredDocumentBuilder();
		
		logger.debug(" list docs "+riBuilder.requiredDocumentBuilderMethod(postgresDao.getOpportunityDetails("737")));

			try{
		/*
			 * TPreparedStatement st=DBConnectionFactory.getConnection().prepareStatement("insert into crm_lead(name,stage_id,user_id,active) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	
		st.setString(1, "tests");
		st.setInt(2, 6);
		st.setInt(3, 26);
		st.setBoolean(4, true);


		st.executeUpdate();
ResultSet resultSet=		st.getGeneratedKeys();
resultSet.next();
System.out.println("result set "+resultSet.getInt(1));*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error while processing TestDao"+e.getMessage());
		}
		
		}
		
	}




