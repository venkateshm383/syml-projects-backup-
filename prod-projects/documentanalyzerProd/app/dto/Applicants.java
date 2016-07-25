package dto;

public class Applicants
{
	 private String lastName;

	    private String[] documents;

	    private String email;

	    private String firstName;

	    public String getLastName ()
	    {
	        return lastName;
	    }

	    public void setLastName (String lastName)
	    {
	        this.lastName = lastName;
	    }

	    public String[] getDocuments ()
	    {
	        return documents;
	    }

	    public void setDocuments (String[] documents)
	    {
	        this.documents = documents;
	    }

	    public String getEmail ()
	    {
	        return email;
	    }

	    public void setEmail (String email)
	    {
	        this.email = email;
	    }

	    public String getFirstName ()
	    {
	        return firstName;
	    }

	    public void setFirstName (String firstName)
	    {
	        this.firstName = firstName;
	    }
	    @Override
	    public String toString()
	    {
	        return "ClassPojo [lastName = "+lastName+", documents = "+documents+", email = "+email+", firstName = "+firstName+"]";
	    }
}
