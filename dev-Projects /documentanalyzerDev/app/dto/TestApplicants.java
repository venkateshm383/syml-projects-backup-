package dto;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties     
public class TestApplicants
{	
    private String[] PropertyDocuments;
	//@JsonIgnore
    private Applicants[] Applicants;
	//@JsonIgnore
    private String[] DownpaymentDocuments;
	//@JsonIgnore
    private String ApplicationID;

    public String[] getPropertyDocuments ()
    {
        return PropertyDocuments;
    }

    public void setPropertyDocuments (String[] PropertyDocuments)
    {
        this.PropertyDocuments = PropertyDocuments;
    }

    public Applicants[] getApplicants ()
    {
        return Applicants;
    }

    public void setApplicants (Applicants[] Applicants)
    {
        this.Applicants = Applicants;
    }

    public String[] getDownpaymentDocuments ()
    {
        return DownpaymentDocuments;
    }

    public void setDownpaymentDocuments (String[] DownpaymentDocuments)
    {
        this.DownpaymentDocuments = DownpaymentDocuments;
    }

    public String getApplicationID ()
    {
        return ApplicationID;
    }

    public void setApplicationID (String ApplicationID)
    {
        this.ApplicationID = ApplicationID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PropertyDocuments = "+PropertyDocuments+", Applicants = "+Applicants+", DownpaymentDocuments = "+DownpaymentDocuments+", ApplicationID = "+ApplicationID+"]";
    }
}
			
			


