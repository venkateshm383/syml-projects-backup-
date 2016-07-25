package requireddoc.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.Applicants;


public class TestApplicants
{	
	 private String[] downpaymentDocuments;

	    private Applicants[] applicants;

	    private String applicationID;

	    private String[] propertyDocuments;

	    public String[] getDownpaymentDocuments ()
	    {
	        return downpaymentDocuments;
	    }

	    public void setDownpaymentDocuments (String[] downpaymentDocuments)
	    {
	        this.downpaymentDocuments = downpaymentDocuments;
	    }

	    public Applicants[] getApplicants ()
	    {
	        return applicants;
	    }

	    public void setApplicants (Applicants[] applicants)
	    {
	        this.applicants = applicants;
	    }

	    public String getApplicationID ()
	    {
	        return applicationID;
	    }

	    public void setApplicationID (String applicationID)
	    {
	        this.applicationID = applicationID;
	    }

	    public String[] getPropertyDocuments ()
	    {
	        return propertyDocuments;
	    }

	    public void setPropertyDocuments (String[] propertyDocuments)
	    {
	        this.propertyDocuments = propertyDocuments;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [downpaymentDocuments = "+downpaymentDocuments+", applicants = "+applicants+", applicationID = "+applicationID+", propertyDocuments = "+propertyDocuments+"]";
	    }
}
			
			


