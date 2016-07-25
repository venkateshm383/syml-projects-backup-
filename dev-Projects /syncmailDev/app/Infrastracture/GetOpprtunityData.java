package Infrastracture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ch.qos.logback.core.db.dialect.HSQLDBDialect;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

import controllers.Applicants;
import controllers.Opprtunity;


public class GetOpprtunityData {
	CouchBaseOperation couchbase=new CouchBaseOperation();
	
	
	
	
	public Opprtunity getApplicantDetials(String  applicantEmail){
		
		Opprtunity opprunity=null;
		try{
		Session openERPSession=couchbase.getOdooConnection();
		
		
		
ObjectAdapter applicants=openERPSession.getObjectAdapter("applicant.record");
		FilterCollection filter1=new FilterCollection();
		filter1.add("email_personal", "=", applicantEmail.toLowerCase().trim());
		RowCollection row2=applicants.searchAndReadObject(filter1,new String[]{"applicant_name","applicant_last_name","email_personal","opp_rec_ids"});
		
		Object [] object=null;
		String opprunityId=null;
		for (Row row3 : row2){

			try{
		 object=(Object[])row3.get("opp_rec_ids");
		 System.out.println("opportunityId"+object[0]);

		 opprunityId=object[0].toString();
		 int opportunityMaximum=Integer.parseInt(object[0].toString());
		 int opprtunityValue=0;
		 for (int i = 0; i < object.length; i++) {
			try{
				opprtunityValue=Integer.parseInt(object[0].toString());
				if (opprtunityValue > opportunityMaximum)
			    {
					opportunityMaximum  = opprtunityValue;
			    }
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		 opprunityId=opportunityMaximum+"";

		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	
	
		
		 opprunity=getOpprtunityData(opprunityId);
		System.out.println("opporunityname "+opprunity.getOpprtunityName());
		System.out.println("opporunityemail "+opprunity.getOpprtunityEmail());
		System.out.println("opporunityid "+opprunity.getOpprtunityId());
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return opprunity;
	}
	
	
public Opprtunity getOpprtunityData(String  opprunityID){
		
	Opprtunity opprunity=new Opprtunity();
	opprunity.setOpprtunityId(opprunityID);
	try {
		Session openERPSession=	couchbase.getOdooConnection();
		System.out.println("Seesion is"+openERPSession);
	ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
	
	FilterCollection filter=new FilterCollection();
	filter.add("id", "=", opprunityID);
	RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","app_rec_ids","name","email_from"});
	Row row1=row.get(0);
	
	opprunity.setOpprtunityName(row1.get("name").toString());
	opprunity.setOpprtunityEmail(row1.get("email_from").toString());

	Object [] object=(Object[])row1.get("app_rec_ids");
	System.out.println("Object is "+object);
	
ArrayList<Applicants> arrayListdata=new ArrayList<Applicants>();
for (Object object2 : object) {
	System.out.println("inside for Of Object");

	
ObjectAdapter applicants=openERPSession.getObjectAdapter("applicant.record");
	System.out.println(object2.toString());
	FilterCollection filter1=new FilterCollection();
	filter1.add("id", "=", object2.toString());
	RowCollection row2=applicants.searchAndReadObject(filter1,new String[]{"applicant_name","applicant_last_name","email_personal"});
	
	for (Row row3 : row2){
		Applicants applicant=new Applicants();

	    System.out.println("Row ID: " + row3.getID());
	    System.out.println("Name:" + row3.get("applicant_name"));
	    System.out.println("ApplicantLast_Name:" + row3.get("applicant_last_name"));
	    System.out.println("Email:" + row3.get("email_personal"));
		applicant.setApplicantName(row3.get("applicant_name").toString()+"_"+row3.get("applicant_last_name").toString());
		applicant.setApplicantId(object2.toString());
		applicant.setApplicantEmail(row3.get("email_personal").toString());

		arrayListdata.add(applicant);


	}

	
	System.out.println("Applicant added");
	
}
Set<Applicants> setApplicnats=new LinkedHashSet<Applicants>(arrayListdata);
opprunity.setApplicants(setApplicnats);

	
} catch (Exception e) {
	e.printStackTrace();
}
		
		return opprunity;
	}

}
