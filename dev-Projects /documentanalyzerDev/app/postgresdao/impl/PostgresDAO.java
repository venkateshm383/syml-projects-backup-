/**
 * EmployeeDAO.java
 */
package postgresdao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;
import postgresdao.PostgresDAOI;
import postgresdao.factory.DBConnectionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Applicant;
import dto.Income;
import dto.Opportunity;
import dto.Property;

/**
 * 
 * @author bizruntime.
 *
 */
public class PostgresDAO implements PostgresDAOI {


	public static final String SELECT_OPPORTUNITY_DETAILS_SQL = "select row_to_json(crm_lead) as crm_lead from crm_lead where id =?";
	public static final String SELECT_APPLICANT_DETAILS_SQL = "select row_to_json(applicant_record) as applicant_record from applicant_record where id =?";
	public static final String SELECT_APPLICANTRELATION_DETAILS_SQL = "select row_to_json(opportunity_applicant_rel) as opportunity_applicant_rel from opportunity_applicant_rel where opp_id =?";
	public static final String SELECT_APPLICANT_PROPERTIES_SQL = "select row_to_json(applicant_property) as applicant_property from applicant_property where applicant_id =?";
	public static final String SELECT_APPLICANT_MORTGAGE_SQL = "select row_to_json(applicant_mortgage) as applicant_mortgage from applicant_mortgage where property_id =? and applicant_id =?";
	public static final String SELECT_APPLICANT_INCOME_SQL = "select row_to_json(income_employer) as income_employer from income_employer where applicant_id =?";
	public static final String SELECT_APPLICANT_COLLECTION_SQL = "select row_to_json(applicant_collection) as applicant_collection from applicant_collection where applicant_id =?";
	public static final String SELECT_APPLICANT_PAYMENT_SQL = "select row_to_json(applicant_payment) as applicant_payment from applicant_payment where applicant_id =?";

	ObjectMapper ObjectMapper = new ObjectMapper();

	@Override
	public Opportunity getOpportunityDetails(String opportunityId) {
		play.Logger.debug("inside getOpportunityDetails method of PostgresDAO");
		Opportunity oppurtunityDetails = null;
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String aliasName = "crm_lead";
		try {
			connection = DBConnectionFactory.getConnection();
			st = connection.prepareStatement(SELECT_OPPORTUNITY_DETAILS_SQL);
			st.setInt(1, new Integer(opportunityId));
			rs = st.executeQuery();

			String opporunityData = getResult(rs, aliasName);

			oppurtunityDetails = ObjectMapper.readValue(opporunityData,
					Opportunity.class);
		
		
			
			play.Logger.debug("Opportunity Details retrieved Successfully."
					+ opporunityData);
		} catch (SQLException | IOException sqle) {
			play.Logger.error("Error when connecting to the postgress db ", sqle);
		} finally {
			closeUp(connection, st,rs);

		}
		
		List<Applicant> lisApplicants=	getApplicantIds(opportunityId);
		oppurtunityDetails.setApplicants(lisApplicants);

		return oppurtunityDetails;
	}// read opportunity details

	
	private Applicant getApplicantDetailsByID(String applicantId) {

		Applicant applicants = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection connection = null;

		String aliasName = "applicant_record";
		try {
			connection = DBConnectionFactory.getConnection();

			st = connection.prepareStatement(SELECT_APPLICANT_DETAILS_SQL);
			st.setInt(1, new Integer(applicantId));
			rs = st.executeQuery();
			while (rs.next()) {
			applicants = ObjectMapper.readValue(rs.getString(aliasName),
						Applicant.class);
			
			}

		} catch (SQLException | IOException sqle) {
			play.Logger.error("Error when connecting to the postgress db ", sqle);
		} finally {
			closeUp(connection, st,rs);

		}
		
		applicants.setIncomes(	getApplicantIncomes(applicantId));
		applicants.setProperties(	getApplicantProperties(applicantId));
		applicants.setCollections(getApplicantCollection(applicantId));
		applicants.setPayment(getApplicantLatePayment(applicantId));
		return applicants;

	}
	
	
	private List<Applicant> getApplicantIds(String opportunityId) {

		List<Applicant> listOfApplicant =new ArrayList<Applicant>();
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String aliasName = "opportunity_applicant_rel";
		try {
			connection = DBConnectionFactory.getConnection();
			st = connection.prepareStatement(SELECT_APPLICANTRELATION_DETAILS_SQL);
			st.setInt(1, new Integer(opportunityId));
			rs = st.executeQuery();
			while (rs.next()) {
				play.Logger.info("applicant data before assinging to jsonBject "+rs.getString(aliasName));

				JSONObject applicantJSon=new JSONObject(rs.getString(aliasName));
				play.Logger.info("applicant data in json format "+applicantJSon);
				listOfApplicant.add(getApplicantDetailsByID(applicantJSon.getString("app_id")));
			}

		} catch (SQLException | JSONException sqle) {
			play.Logger.error("Error when connecting to the postgress db ", sqle);
		} finally {
			closeUp(connection, st,rs);

		}
		return listOfApplicant;

	}
	
	
	private List<Property> getApplicantProperties(String applicantId){
		
		
		List<Property> listOfApplicantPropertys =new ArrayList<Property>();
		ObjectMapper objectMapper=new ObjectMapper();
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection connection=null;
		Property pertProperty=null;
		String aliasName = "applicant_property";
		try {
			connection = DBConnectionFactory.getConnection();

			st = connection.prepareStatement(SELECT_APPLICANT_PROPERTIES_SQL);
			st.setInt(1, new Integer(applicantId));
			rs = st.executeQuery();
			while (rs.next()) {
				
				
				pertProperty=objectMapper.readValue(rs.getString(aliasName), Property.class);
				String mortagageExsist=getApplicantMortgageProperties(pertProperty.getProperty_id(),applicantId,connection);
				pertProperty.setMortgageYesNo(mortagageExsist);
				listOfApplicantPropertys.add(pertProperty);
			}
		} catch (Exception sqle) {
			play.Logger.error("Error when connecting to the postgress db ", sqle);
		} finally {
			closeUp(connection,st, rs);

		}
		return listOfApplicantPropertys;
		
	}
	
	
	private String getApplicantMortgageProperties(String property_id,String applicantId,Connection connection){
		
		String mortgageExist="No";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(SELECT_APPLICANT_MORTGAGE_SQL);
			st.setString(1, property_id);
			st.setInt(2,new Integer(applicantId));

			rs = st.executeQuery();
			while (rs.next()) {
				
				mortgageExist="Yes";
			}

		} catch (Exception sqle) {
			play.Logger.error("Error when connecting to the postgress db ", sqle);
		} finally {
			closeUp(st, rs);

		}
		return mortgageExist;
		
	}

	private List<Income> getApplicantIncomes(String applicantId){
	
		Connection connection=null;

	List<Income> listOfApplicantIncomes =new ArrayList<Income>();
	ObjectMapper objectMapper=new ObjectMapper();
	PreparedStatement st = null;
	ResultSet rs = null;
	Income income=null;
	String aliasName = "income_employer";
	try {
		connection = DBConnectionFactory.getConnection();

		st = connection.prepareStatement(SELECT_APPLICANT_INCOME_SQL);
		st.setInt(1, new Integer(applicantId));
		rs = st.executeQuery();
		while (rs.next()) {
			Logger.info("Applicant income data "+rs.getString(aliasName) );
			income=objectMapper.readValue(rs.getString(aliasName), Income.class);
			
			listOfApplicantIncomes.add(income);
		}
	} catch (Exception sqle) {
		play.Logger.error("Error when connecting to the postgress db ", sqle);
	} finally {
		closeUp(connection,st, rs);

	}
	return listOfApplicantIncomes;
	
}

	private List<String> getApplicantCollection(String applicantId){
	
		Connection connection=null;
	List<String> listOfCollection =new ArrayList<String>();
	PreparedStatement st = null;
	ResultSet rs = null;
	String aliasName = "applicant_collection";
	try {
		connection = DBConnectionFactory.getConnection();

		st = connection.prepareStatement(SELECT_APPLICANT_COLLECTION_SQL);
		st.setInt(1, new Integer(applicantId));
		rs = st.executeQuery();
		while (rs.next()) {
			listOfCollection.add(new JSONObject(rs.getString(aliasName)).getString("name"));
		}
	} catch (Exception sqle) {
		play.Logger.error("Error when connecting to the postgress db ", sqle);
	} finally {
		closeUp(connection,st, rs);

	}
	return listOfCollection;
	
}

	private int getApplicantLatePayment(String applicantId){
	
	
int payment=0;
Connection connection=null;

	PreparedStatement st = null;
	ResultSet rs = null;
	String aliasName = "applicant_payment";
	try {
		connection = DBConnectionFactory.getConnection();

		st = connection.prepareStatement(SELECT_APPLICANT_PAYMENT_SQL);
		st.setInt(1, new Integer(applicantId));
		rs = st.executeQuery();
		while (rs.next()) {
		try{
		int        days=0;
	    Calendar calendar1=Calendar.getInstance();
	    Calendar calendar2=Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd");

	    @SuppressWarnings("deprecation")
		Date dateString=new Date( new JSONObject(rs.getString(aliasName)).getString("date"));
	    calendar1.setTime(dateString);
	      String date2=formatter.format(calendar2.getTime());
	      String date1=formatter.format(calendar1.getTime());
	     // System.out.println("date 1"+ date2  +"dat3 "+ date1);
	     days=(int)( (formatter.parse(date2).getTime() - formatter.parse(date1).getTime())/ (1000 * 60 * 60 * 24) );

	     if(days<=730 || days<=731){
		++payment;
		break;
	     }
		}catch(Exception e){
			play.Logger.error("error in parsing date");
		}
		}
	} catch (Exception    sqle) {
		play.Logger.error("Error when connecting to the postgress db ", sqle);
	} finally {
		closeUp(connection,st, rs);

	}
	return payment;
	
}

	private void closeUp(Connection con, PreparedStatement st, ResultSet rs) {

		try {
			con.close();
			st.close();
			rs.close();
			play.Logger.info("Connection closed Successfully.");
		} catch (Exception sqle2) {
			play.Logger.error("Failed to Close Connection." + sqle2);
		}

	}

	public void closeUp(Connection con, PreparedStatement st) {

		try {
			con.close();
			st.close();

			play.Logger.info("Connection closed Successfully.");
		} catch (Exception sqle2) {
			play.Logger.error("Failed to Close Connection." + sqle2);
		}

	}
	private void closeUp( PreparedStatement st, ResultSet rs) {

		try {
			
			st.close();
			rs.close();
			play.Logger.info("Connection closed Successfully.");
		} catch (Exception sqle2) {
			play.Logger.error("Failed to Close Connection." + sqle2);
		}

	}
	

	private String getResult(ResultSet rs, String aliasName) throws SQLException {
		String resultData = null;
		while (rs.next()) {
			resultData = rs.getString(aliasName);
		}
		return resultData;
	}
}