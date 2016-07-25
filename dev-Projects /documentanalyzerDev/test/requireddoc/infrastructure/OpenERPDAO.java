package requireddoc.infrastructure;



import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * Created by xsalefter
 */
public class OpenERPDAO {
    public static String getApplicantId(final String opportunityId) {
        // TODO: Do logic to retrieve applicantId from OpenERP, and then change the return
        // value from null to string of applicantId that comes from OpenERP.
    	String id=null;
    	Session openERpSession=new Session("crm1.visdom.ca", 8069, "symlsys", "admin", "BusinessPlatform@Visdom1");
    	try {
			openERpSession.startSession();
			ObjectAdapter opprtunity=openERpSession.getObjectAdapter("crm.lead");
			
			FilterCollection filter=new FilterCollection();
			filter.add("id", "=", opportunityId);
			RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"app_rec_ids"});
			Row row1=row.get(0);
			Object [] object=(Object[])row1.get("app_rec_ids");
		id=object[0].toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        return id;
    }
}
