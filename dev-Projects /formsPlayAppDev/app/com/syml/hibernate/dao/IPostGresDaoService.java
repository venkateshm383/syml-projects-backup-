package com.syml.hibernate.dao;


import java.util.ArrayList;
import java.util.List;

import controllers.Applicant;
import controllers.ApplicantOpportunityRelationShip;
import controllers.Contact;
import controllers.Lead;
import controllers.Opportunity;
import controllers.Referral_Source;

public interface IPostGresDaoService {
	
	
	//CREATE NEW APPLICANT(S) AND UPDATE OPPORTUNITY DETAILS
	public Opportunity createApplicant(Opportunity opprotunity) throws PostGressDaoServiceException;
	
	//UPDATE OPPORTUNITY DETAILS OF PAGE2 INTO crm_lead Table
	public void updateOpportunityPage2(Opportunity opprotunity) throws PostGressDaoServiceException;
	
	//UPDATE OPPORTUNITY DETAILS OF PAGE3 INTO crm_lead TABLE
	public void updateOpportuinityPage3(Opportunity opportunity) throws PostGressDaoServiceException;
	
	//UPDATE OPPORTUNITY DETAILS OF PAGE4 INTO crm_lead TABLE
	public void updateOpportunityPage4(Opportunity opportunity) throws PostGressDaoServiceException;
	
	//UPDATE OPPORTUNITY DETAILS OF PAGE5a INTO crm_lead TABLE
	public void updateOpportunityPage5a(Opportunity opportunity) throws PostGressDaoServiceException;
	
	//UPDATE OPPORTUNITY DETAILS OF PAGE5b INTO crm_lead TABLE
	public void updateOpportunityPage5b(Opportunity opportunity)throws PostGressDaoServiceException;
	
	//UPDATE APPLICANT(S) DETAILS OF PAGE6 INTO applicant_record TABLE.
	public void updateApplicantPage6(Opportunity opportunity)throws PostGressDaoServiceException;	
	
	//UPDATE APPLICANT(S) DETAILS OF PAGE7 INTO applicant_address TABLE
	public void updateApplicantPage7(Applicant applicant)throws PostGressDaoServiceException;
	
	//UPDATE APPLICANT(S) DETAILS OF PAGE8 INTO income_employer TABLE
	public void updateApplicantIncomePage8or9(Opportunity opportunity) throws PostGressDaoServiceException;
		
	//UPDATE APPLICANT(S) DETAILS OF PAGE8 INTO applicant_property TABLE
	public void updateApplicantAssetsPage10(Applicant applicant);	
	public void updateApplicantAssetsPage10(Opportunity opportunity)throws PostGressDaoServiceException;
	
	//UPDATE APPLICANT(S) DETAILS OF PAGE10 INTO income_employer TABLE
	public Opportunity updateApplicantPropertyPage11(Opportunity opportunity)throws PostGressDaoServiceException;
	public Opportunity updateApplicantMortgagePage11(Opportunity opportunity) throws PostGressDaoServiceException ;
	public void deleteApplicantRelationshipWithOpportunity(ApplicantOpportunityRelationShip apShip)throws PostGressDaoServiceException;
	public Applicant insertApplicantPageOneDetails (Applicant applicant);
	public void updateOpportunitySignDetails(Opportunity opportunity) throws PostGressDaoServiceException;
	
	public void deleteApplicantAssets(Opportunity opportunity) throws PostGressDaoServiceException;
	public void deleteApplicantPropertys(Opportunity opportunity) throws PostGressDaoServiceException;
	public void deleteApplicantIncome(Applicant applicant)
			throws PostGressDaoServiceException;
	public void deleteApplicantIncomeForSupplementary(Applicant applicant) throws PostGressDaoServiceException;
	
	public void deleteApplicantAddress(Applicant applicant) throws PostGressDaoServiceException;
	//TO READ THE OPPORTUNITY DETAILS
	//public void getOpportunityDetails(int id);
	
	//public void insertLead(Lead crm);
	
	//public Referral_Source getReferralSourceById(int id);
	
	//INSERT ONLY APPLICANT DETAILS INTO applicant_record table
	//public void insertApplicantRecord(Applicant opportunity);	
	
	
	
	public Contact insertContact(Contact contact)throws PostGressDaoServiceException;
	public List<Contact> getContact(Contact contact)throws PostGressDaoServiceException;
	public Contact getContactById(int id)throws PostGressDaoServiceException;

	public List<Contact> getContactByEmailAndLastName(Contact contact)throws PostGressDaoServiceException;
	public Lead insertLead(Lead crm) throws PostGressDaoServiceException;
	
	public Lead getLeadByConatctId(Contact contact)throws PostGressDaoServiceException;
	
	public List<Lead> getLead(Lead lead) throws PostGressDaoServiceException;
	
	public Referral_Source getReferralSourceById(int id)throws PostGressDaoServiceException;

	public ArrayList<Referral_Source>  getReferral_SourceByPartnerID(int partner_id)throws PostGressDaoServiceException;

	public ArrayList<Referral_Source>  getReferral_SourceByEmailAndName(String email,String name)throws PostGressDaoServiceException;
	public ArrayList<Referral_Source> getReferral_SourceByEmail(String email)throws PostGressDaoServiceException;
	public Referral_Source insertReferral(Referral_Source Referral_Source)throws PostGressDaoServiceException;
	public Referral_Source updateReferral(Referral_Source Referral_Source)throws PostGressDaoServiceException;

public int getStateID(String province)throws PostGressDaoServiceException;





}
