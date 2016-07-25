package com.syml.hibernate.service;

import play.Logger;

import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.dao.factory.DAOFactory;

import controllers.Applicant;
import controllers.ApplicantOpportunityRelationShip;
import controllers.Opportunity;

public class PostGresDaoService {
	IPostGresDaoService postGresDao = null;

	public Opportunity createApplicant(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside createApplicant() of Service Csass");
		postGresDao = DAOFactory.getInstance();
		Opportunity returnOpportunity = postGresDao
				.createApplicant(opportunity);
		return returnOpportunity;
	}

	public void insertOpportunityPage2Details(Opportunity opportunity) throws PostGressDaoServiceException{
		Logger.info("Inside insertOpportunityPage2Details() of Service Csass");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateOpportunityPage2(opportunity);
	}

	public void updateOpportunityPage3(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage3 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateOpportuinityPage3(opportunity);
	}

	public void updateOpportunityPage4(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage4 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateOpportunityPage4(opportunity);
	}

	public void updateOpportunityPage5a(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage5a method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateOpportunityPage5a(opportunity);
	}

	public void updateOpportunityPage5b(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage5b method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateOpportunityPage5b(opportunity);
	}

	public void updateApplicantPage6(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage5b method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateApplicantPage6(opportunity);
	}

	public void updateApplicantPage7(Applicant applicant) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage7 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateApplicantPage7(applicant);
	}

	public void updateApplicantIncomePage8Or9(Opportunity opportunity) throws PostGressDaoServiceException{
		Logger.info("Inside updateApplicantPage8Or9 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateApplicantIncomePage8or9(opportunity);
	}
	
	public void updateApplicantAssetsPage10(Opportunity opportunity) throws PostGressDaoServiceException{
		Logger.info("Inside updateOpportunityPage10 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateApplicantAssetsPage10(opportunity);
	}
	/*public void updateApplicantPropertyPage11(Applicant applicant) {
		Logger.info("Inside updateOpportunityPage5b method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateApplicantPropertyPage11(applicant);
	}*/
	public void updateApplicantPropertyPage11(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateApplicantPropertyPage11 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateApplicantPropertyPage11(opportunity);
		postGresDao.updateApplicantMortgagePage11(opportunity);
	}
	public void updateOpportunityPage12(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage12 method of PostGresDaoService class");
		postGresDao = DAOFactory.getInstance();
		postGresDao.updateOpportunitySignDetails(opportunity);
	}
	
	
	public void deleteApplicantRelationWithOpportunity(ApplicantOpportunityRelationShip apShip)throws PostGressDaoServiceException{
		
	Logger.debug("Inside (.) deleteApplicantRelationWithOpportunity method of PostGresDaoService class "+apShip);	
	postGresDao = DAOFactory.getInstance();
	postGresDao.deleteApplicantRelationshipWithOpportunity(apShip);
	
	}
	public void deleteApplicantById(){
		
	}
	
	public void  deleteApplicantAssets(Opportunity opportunity) throws PostGressDaoServiceException{
		Logger.debug("Inside (.) deleteApplicantAssets method of PostGresDaoService class ");	
		postGresDao = DAOFactory.getInstance();
		postGresDao.deleteApplicantAssets(opportunity);

	}
	
	
	public void  deleteApplicantProperty(Opportunity opportunity) throws PostGressDaoServiceException{
		Logger.debug("Inside (.) deleteApplicantProperty method of PostGresDaoService class ");	
		postGresDao = DAOFactory.getInstance();
		postGresDao.deleteApplicantPropertys(opportunity);

	}
	
	public void  deleteApplicantIncome(Applicant applicant) throws PostGressDaoServiceException{
		Logger.debug("Inside (.) deleteApplicantIncome method of PostGresDaoService class ");	
		postGresDao = DAOFactory.getInstance();
		postGresDao.deleteApplicantIncome(applicant);

	}
	
	
	public void  deleteApplicantIncomeForSuplementary(Applicant applicant) throws PostGressDaoServiceException{
		Logger.debug("Inside (.) deleteApplicantIncome method of PostGresDaoService class ");	
		postGresDao = DAOFactory.getInstance();
		postGresDao.deleteApplicantIncomeForSupplementary(applicant);

	}
	

	public void deleteApplicantAddress(Applicant applicant) throws PostGressDaoServiceException {
		Logger.debug("Inside (.) deleteApplicantIncome method of PostGresDaoService class ");	
		postGresDao = DAOFactory.getInstance();
		postGresDao.deleteApplicantAddress(applicant);
		
	}
}
