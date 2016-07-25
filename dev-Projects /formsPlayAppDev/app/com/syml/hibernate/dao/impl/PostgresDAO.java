package com.syml.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import play.Logger;

import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.utils.HibernateUtils;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.Address;
import controllers.Applicant;
import controllers.ApplicantOpportunityRelationShip;
import controllers.Assetes;
import controllers.Contact;
import controllers.Income;
import controllers.Lead;
import controllers.Mortgages;
import controllers.Opportunity;
import controllers.Property;
import controllers.Referral_Source;
import controllers.State;

@SuppressWarnings("unused")
public class PostgresDAO implements IPostGresDaoService {

	// CREATE NEW APPLICANT(S) AND UPDATE OPPORTUNITY DETAILS
	@Override
	public Opportunity createApplicant(Opportunity opprotunity) throws PostGressDaoServiceException {
		Logger.info("createApplicant of PostgresDAO"+opprotunity);
		Logger.debug("size" + opprotunity.getApplicants().size());

		try{
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
		
		session.beginTransaction();
		Transaction tx = session.beginTransaction();
		Object o = session.load(Opportunity.class, opprotunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;

		List<Applicant> listOfApplicants = opprotunity.getApplicants();
		if(listOfApplicants.size()>0){
			
		}
		proxyOpportunity.setApplicants(listOfApplicants);
		proxyOpportunity.setWhat_is_your_lending_goal(opprotunity
				.getWhat_is_your_lending_goal());
		proxyOpportunity.setStage_id(opprotunity.getStage_id());
		proxyOpportunity.setType(opprotunity.getType());
		proxyOpportunity.setUser_id(opprotunity.getUser_id());
		proxyOpportunity.setApplication_no(opprotunity.getId()+"");
		proxyOpportunity.setApplication_date(opprotunity.getApplication_date());
		proxyOpportunity.setActive(true);

		session.getTransaction().commit();

		Logger.info("Update on Opportunity and Applicant is sucessful.");
		return opprotunity;
		
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in inserting applicants into DB for given opprotunityID = "+opprotunity.getId(),e);
		}
	}

	// UPDATE OPPORTUNITY DETAILS OF PAGE2 INTO crm_lead Table
	@Override
	public void updateOpportunityPage2(Opportunity opportunity) throws PostGressDaoServiceException{
		Logger.info("Inside updateOpportunityPage2 of PostgresDAO");
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Object o = session.load(Opportunity.class, opportunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;
		Logger.debug("What_is_your_lending_goal "
				+ proxyOpportunity.getWhat_is_your_lending_goal());
		if (proxyOpportunity.getWhat_is_your_lending_goal() != null
				&& proxyOpportunity.getWhat_is_your_lending_goal()
						.equalsIgnoreCase("1")) {
			Logger.info("PreApproval Selected");
			Logger.debug("Province " + opportunity.getProvince()
					+ "Purchase_price " + opportunity.getPurchase_price()
					+ "Down_payment_amount "
					+ opportunity.getDown_payment_amount() + "\n Bank_account "
					+ opportunity.getBank_account() + "\n Rrsp_amount "
					+ opportunity.getRrsp_amount() + "\n Borrowed_amount "
					+ opportunity.getBorrowed_amount()
					+ "\n Sale_of_existing_amount "
					+ opportunity.getSale_of_existing_amount()
					+ "\n Gifted_amount" + opportunity.getGifted_amount()
					+ "\n Personal_cash_amount "
					+ opportunity.getPersonal_cash_amount()
					+ "\n Existing_equity_amount "
					+ opportunity.getExisting_equity_amount()
					+ "\n Sweat_equity_amount "
					+ opportunity.getSweat_equity_amount()
					+ "\n etLiving_in_property "
					+ opportunity.getLiving_in_property()
					+ "\n Monthly_rental_income "
					+ opportunity.getMonthly_rental_income());
			proxyOpportunity.setProvince(opportunity.getProvince());
			proxyOpportunity.setPurchase_price(opportunity.getPurchase_price());
			proxyOpportunity.setDown_payment_amount(opportunity
					.getDown_payment_amount());

			proxyOpportunity.setBank_account(opportunity.getBank_account());
			proxyOpportunity.setRrsp_amount(opportunity.getRrsp_amount());
			proxyOpportunity.setBorrowed_amount(opportunity
					.getBorrowed_amount());
			proxyOpportunity.setSale_of_existing_amount(opportunity
					.getSale_of_existing_amount());
			proxyOpportunity.setGifted_amount(opportunity.getGifted_amount());
			proxyOpportunity.setPersonal_cash_amount(opportunity
					.getPersonal_cash_amount());
			proxyOpportunity.setExisting_equity_amount(opportunity
					.getExisting_equity_amount());
			proxyOpportunity.setSweat_equity_amount(opportunity
					.getSweat_equity_amount());

			proxyOpportunity.setLiving_in_property(opportunity
					.getLiving_in_property());
			if (opportunity.getLiving_in_property() != null
					&& opportunity.getLiving_in_property() != 1)
				proxyOpportunity.setMonthly_rental_income(opportunity
						.getMonthly_rental_income());

			Logger.debug("assignOppertunityPre-Approval updated");
		} else if (proxyOpportunity.getWhat_is_your_lending_goal() != null
				&& proxyOpportunity.getWhat_is_your_lending_goal()
						.equalsIgnoreCase("2")) {
			Logger.info("Purchase Selected");
			Logger.debug("Province " + opportunity.getProvince() + "Address "
					+ opportunity.getAddress() + "City "
					+ opportunity.getCity() + "Postal_code "
					+ opportunity.getPostal_code() + "Down_payment_amount "
					+ opportunity.getDown_payment_amount() + "\n Bank_account "
					+ opportunity.getBank_account() + "\n Rrsp_amount "
					+ opportunity.getRrsp_amount() + "\n Borrowed_amount "
					+ opportunity.getBorrowed_amount()
					+ "\n Sale_of_existing_amount "
					+ opportunity.getSale_of_existing_amount()
					+ "\n Gifted_amount" + opportunity.getGifted_amount()
					+ "\n Personal_cash_amount "
					+ opportunity.getPersonal_cash_amount()
					+ "\n Existing_equity_amount "
					+ opportunity.getExisting_equity_amount()
					+ "\n Sweat_equity_amount "
					+ opportunity.getSweat_equity_amount()
					+ "\n Living_in_property "
					+ opportunity.getLiving_in_property()
					+ "\n Monthly_rental_income "
					+ opportunity.getMonthly_rental_income());
			proxyOpportunity.setProvince(opportunity.getProvince());
			proxyOpportunity.setAddress(opportunity.getAddress());
			proxyOpportunity.setCity(opportunity.getCity());
			proxyOpportunity.setPostal_code(opportunity.getPostal_code());
			proxyOpportunity.setDown_payment_amount(opportunity
					.getDown_payment_amount());

			proxyOpportunity.setBank_account(opportunity.getBank_account());
			proxyOpportunity.setRrsp_amount(opportunity.getRrsp_amount());
			proxyOpportunity.setBorrowed_amount(opportunity
					.getBorrowed_amount());
			proxyOpportunity.setSale_of_existing_amount(opportunity
					.getSale_of_existing_amount());
			proxyOpportunity.setGifted_amount(opportunity.getGifted_amount());
			proxyOpportunity.setPersonal_cash_amount(opportunity
					.getPersonal_cash_amount());
			proxyOpportunity.setExisting_equity_amount(opportunity
					.getExisting_equity_amount());
			proxyOpportunity.setSweat_equity_amount(opportunity
					.getSweat_equity_amount());

			proxyOpportunity.setLiving_in_property(opportunity
					.getLiving_in_property());
			if (opportunity.getLiving_in_property() != null
					&& opportunity.getLiving_in_property() != 1)
				proxyOpportunity.setMonthly_rental_income(opportunity
						.getMonthly_rental_income());

			proxyOpportunity.setMls(opportunity.getMls());
			Logger.debug("assignOppertunityPurchase updated");
		} else if (proxyOpportunity.getWhat_is_your_lending_goal() != null
				&& proxyOpportunity.getWhat_is_your_lending_goal()
						.equalsIgnoreCase("3")) {
			Logger.info("Refinance Selected");
			Logger.debug("Province " + opportunity.getProvince() + "Address "
					+ opportunity.getAddress() + "City "
					+ opportunity.getCity() + "Postal_code "
					+ opportunity.getPostal_code() + "\n Property_value "
					+ opportunity.getProperty_value() + "\n Additional_amount "
					+ opportunity.getAdditional_amount()
					+ "\n Living_in_property "
					+ opportunity.getLiving_in_property()
					+ "\n Monthly_rental_income "
					+ opportunity.getMonthly_rental_income() + "LiveDetails "
					+ opportunity.getLiveDetails() + "mls "
					+ opportunity.getMls()+ "LTV"
					+opportunity.getLtv());
			proxyOpportunity.setLtv(opportunity.getLtv());
			proxyOpportunity.setAddress(opportunity.getAddress());
			proxyOpportunity.setCity(opportunity.getCity());
			proxyOpportunity.setProvince(opportunity.getProvince());
			proxyOpportunity.setPostal_code(opportunity.getPostal_code());
			proxyOpportunity.setProperty_value(opportunity.getProperty_value());
			proxyOpportunity.setAdditional_amount(opportunity
					.getAdditional_amount());

			proxyOpportunity.setLiving_in_property(opportunity
					.getLiving_in_property());
			if (opportunity.getLiving_in_property() != null
					&& opportunity.getLiving_in_property() != 1)
				proxyOpportunity.setMonthly_rental_income(opportunity
						.getMonthly_rental_income());

			Logger.debug("assignOppertunityReFinance updated");
		} else {
			Logger.debug("Lending Goal is not set Properly from the Controller "
					+ opportunity.getDesired_term());
		}

		session.getTransaction().commit();
		Logger.info("Page2 details updated successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error In updating OpportunityPage2 detasils for Given OpprunityID= "+opportunity.getId(),e);
		}
	}

	// UPDATE OPPORTUNITY DETAILS OF PAGE3 INTO crm_lead TABLE
	@Override
	public void updateOpportuinityPage3(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage3 of PostgresDAO");
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		Object o = session.load(Opportunity.class, opportunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;
		proxyOpportunity.setProperty_type(opportunity.getProperty_type());
		proxyOpportunity.setProperty_style(opportunity.getProperty_style());
		proxyOpportunity.setSquare_footage(opportunity.getSquare_footage());
		proxyOpportunity.setHeating(opportunity.getHeating());
		proxyOpportunity.setWater(opportunity.getWater());
		proxyOpportunity.setSewage(opportunity.getSewage());
		proxyOpportunity.setGarage_type(opportunity.getGarage_type());
		if (opportunity.getGarage_type() != null
				&& !opportunity.getGarage_type().equalsIgnoreCase("None"))
			proxyOpportunity.setGarage_size(opportunity.getGarage_size());

		session.getTransaction().commit();
		closeSession(session, factory);
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error In updating OpportuinityPage3 details for given OpportunityID="+opportunity.getId(),e);
		}

	}

	// UPDATE OPPORTUNITY DETAILS OF PAGE4 INTO crm_lead TABLE
	@Override
	public void updateOpportunityPage4(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage4 of PostgresDAO");
		
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		Object o = session.load(Opportunity.class, opportunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;
		proxyOpportunity.setDesired_mortgage_type(opportunity
				.getDesired_mortgage_type());
		proxyOpportunity.setDesired_term(opportunity.getDesired_term());
		proxyOpportunity.setDesired_amortization(opportunity
				.getDesired_amortization());
		session.merge(proxyOpportunity);
		session.getTransaction().commit();
		closeSession(session, factory);

		}catch(Exception e){
			throw new PostGressDaoServiceException("Error In updating OpportunityPage4 detasils for Given OpprunityID="+opportunity.getId(),e);
		}
	}

	// UPDATE OPPORTUNITY DETAILS OF PAGE5a INTO crm_lead TABLE
	@Override
	public void updateOpportunityPage5a(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage5a of PostgresDAO");
		
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		Object o = session.load(Opportunity.class, opportunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;
		proxyOpportunity.setIncome_decreased_worried(opportunity
				.getIncome_decreased_worried());
		proxyOpportunity.setFuture_family(opportunity.getFuture_family());
		proxyOpportunity.setBuy_new_vehicle(opportunity.getBuy_new_vehicle());
		proxyOpportunity.setLifestyle_change(opportunity.getLifestyle_change());
		proxyOpportunity.setFinancial_risk_taker(opportunity
				.getFinancial_risk_taker());
		session.getTransaction().commit();
		closeSession(session, factory);

		}catch(Exception e){
			throw new PostGressDaoServiceException("Error In updating OpportunityPage5a in Db for Given OpporunityID = "+opportunity.getId(),e);
		}
	}

	// UPDATE OPPORTUNITY DETAILS OF PAGE5b INTO crm_lead TABLE
	@Override
	public void updateOpportunityPage5b(Opportunity opportunity) throws PostGressDaoServiceException {
		try{
		Logger.info("Inside updateOpportunityPage5b of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		Object o = session.load(Opportunity.class, opportunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;
		proxyOpportunity.setProperty_less_then_5_years(opportunity
				.getProperty_less_then_5_years());
		proxyOpportunity.setJob_5_years(opportunity.getJob_5_years());

		session.getTransaction().commit();
		closeSession(session, factory);
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in Updating OpportunityPage5b  pages details for given OpportunityId =  "+opportunity.getId(),e);
		}

	}

	// UPDATE APPLICANT(S) DETAILS OF PAGE6 INTO applicant_record TABLE.
	@Override
	public void updateApplicantPage6(Opportunity opportunity) throws PostGressDaoServiceException {
		Logger.info("Inside updateOpportunityPage6 of PostgresDAO");
		
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		List<Applicant> applicants = opportunity.getApplicants();
		Logger.debug("applicants Size " + applicants.size());
		for (Applicant applicant : applicants) {
			Logger.debug("applicant ID -->"+applicant.getId());
			Object o = session.load(Applicant.class, applicant.getId());
			Applicant proxyApplicant = (Applicant) o;
			proxyApplicant.setWork(applicant.getWork());
			proxyApplicant.setDob(applicant.getDob());
			proxyApplicant.setSin(applicant.getSin());
			proxyApplicant.setNon_resident(applicant.getNon_resident());
		//	Logger.debug("proxyApplicant Name-->"+proxyApplicant.getApplicant_name());
			
		}
		
		session.getTransaction().commit();
		closeSession(session, factory);

		Logger.debug("Bio information data updated into Applicant_record Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in Updating applicant Details (6th page) to Db for give OpporunityID = "+opportunity.getId(),e);
		}
	}

	@Override
	public Applicant insertApplicantPageOneDetails(Applicant applicant) {
		Logger.info("Inside InsertApplicantPageOneDetails");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		return null;
	}
 

	@Override
	public void updateApplicantPage7(Applicant applicant) throws PostGressDaoServiceException {
		try{
		Logger.info("Inside updateOpportunityPage7 of PostgresDAO");

		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		List<Address> listOfAddress = applicant.getListOfAddress();
			for (Address address : listOfAddress) {
				address.setApplicant_id(applicant.getId());;
				Logger.debug("id" + address.getApplicant_id());
				session.save(address);
			}
		
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Address information data updated into Applicant_Address Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in Inserting address list for applicant for given OpportunityID="+applicant.getId());
		}
	}
	@Override
	public void updateApplicantIncomePage8or9(Opportunity opportunity)
			throws PostGressDaoServiceException {
		Logger.info("Inside updateApplicantIncomePage8Or9 of PostgresDAO");
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		List<Applicant> listOfApplicant=opportunity.getApplicants();
		for (Iterator iterator = listOfApplicant.iterator(); iterator.hasNext();) {
			Applicant applicant = (Applicant) iterator.next();
		//	Applicant applicant = (Applicant) listOfApplicant.get(0);
			List<Income> listOfIncome=applicant.getIncomes();
			Logger.debug("list Of Income " + listOfIncome.size());
			for (Income income : listOfIncome) {
			
				if(income.getId()==null){
				session.save(income);
				}
			}
			
		}
		
	
		
		tx.commit();
		closeSession(session, factory);
		Logger.debug("Income information data updated into Applicant_record Successfully.");
	}catch(Exception e){
		throw new PostGressDaoServiceException("Error in Inserting address list for applicant for given OpportunityID="+opportunity.getId());
	}
		
	}

	@Override
	public void deleteApplicantIncome(Applicant applicant)
			throws PostGressDaoServiceException {
		Logger.debug("Inside deleteApplicantIncome(Opportunity opportunity) of PostgresDAO");
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
	
			Logger.debug("app id: " + applicant.getId());
		
			List<Income> listOfIncomes = applicant.getIncomes();
			for (Income income : listOfIncomes) {
				
				Logger.debug("Income Id = "+income.getId());
				income.setApplicantId(applicant.getId());
				session.delete(income);
			}
		
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error deleting incomeof  applicants in  DB for given applicantId ="+applicant.getId(),e);
		}
		
	}
	
	@Override
	public void deleteApplicantIncomeForSupplementary(Applicant applicant)
			throws PostGressDaoServiceException {
		Logger.debug("Inside deleteApplicantIncome(Opportunity opportunity) of PostgresDAO");
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
	
			Logger.debug("app id: " + applicant.getId());
		
			List<Income> listOfIncomes = applicant.getIncomes();
			for (Income income : listOfIncomes) {
				Logger.debug("Income Id = "+income.getId());
				if(!(income.getName().contains("Employed")) && !(income.getName().contains("SlefEmployed"))){
					Logger.debug("Income Id = "+income.getId());
					income.setApplicantId(applicant.getId());
					session.delete(income);	
				}
			}
		
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error deleting incomeof  applicants in  DB for given applicantId ="+applicant.getId(),e);
		}
		
	}
	
	
	
	// INSTEAD OF THIS METHOD WE ARE USING Opportunity argumented method which
	// is declared in the below. can be delete.
	@Override
	public void updateApplicantAssetsPage10(Applicant applicant) {

		Logger.info("Inside updateApplicantAssetsPage10 of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Object o = session.load(Applicant.class, applicant.getId());
		Applicant proxyApplicant = (Applicant) o;
		proxyApplicant.setId(applicant.getId());
		List<Assetes> listOfAssets = applicant.getAssetList();
		Logger.debug("list Of Assets " + listOfAssets.size());
		for (Assetes assets : listOfAssets) {
			assets.setApplicant(proxyApplicant);
			proxyApplicant.getAssetList().add(assets);
			session.save(assets);
		}
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
	}

	@Override
	public void updateApplicantAssetsPage10(Opportunity opportunity) throws PostGressDaoServiceException {

		try{
		Logger.debug("Inside updateApplicantAssetsPage10(Opportunity opportunity) of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		List<Applicant> listOfApplicant = opportunity.getApplicants();
		for (Applicant applicant : listOfApplicant) {
			Logger.debug("app id: " + applicant.getId());
		
			List<Assetes> listOfAssets = applicant.getAssetList();
			for (Assetes assets : listOfAssets) {
					assets.setOpportunity_id(applicant.getId());
				session.save(assets);
			}
		}
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error Inserting assetesOf applicant to Db given opportunityId="+opportunity.getId(),e);
		}
	}
	
	@Override
	public void deleteApplicantAssets(Opportunity opportunity) throws PostGressDaoServiceException {

		try{
		Logger.debug("Inside deleteApplicantAssets(Opportunity opportunity) of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		List<Applicant> listOfApplicant = opportunity.getApplicants();
		for (Applicant applicant : listOfApplicant) {
			Logger.debug("app id: " + applicant.getId());
		
			List<Assetes> listOfAssets = applicant.getAssetList();
			for (Assetes assets : listOfAssets) {
					assets.setOpportunity_id(applicant.getId());
				session.delete(assets);
			}
		}
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error deleting assetesOf applicant in Db for given opportunityId="+opportunity.getId(),e);
		}
	}
	
	
	@Override
	public void deleteApplicantPropertys(Opportunity opportunity) throws PostGressDaoServiceException {

		try{
		Logger.debug("Inside deleteApplicantPropertys(Opportunity opportunity) of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		List<Applicant> listOfApplicant = opportunity.getApplicants();
		for (Applicant applicant : listOfApplicant) {
			Logger.debug("app id: " + applicant.getId());
		
			List<Property> listOfProperty= applicant.getProperties();
			List<Mortgages> lisMortgages=applicant.getListOfMortgages();
			for (Property property : listOfProperty) {
				session.delete(property);
			}
			
			for(Mortgages morgage:lisMortgages){
				Logger.debug("Mortgage Value "+morgage);
				session.delete(morgage);

			}
			
		}
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in  deleteApplicantPropertys of applicants in Db for given opportunityId="+opportunity.getId(),e);
		}
	}

	@Override
	public Opportunity updateApplicantPropertyPage11(Opportunity opportunity) throws PostGressDaoServiceException {
		try{
		Logger.info("Inside updateApplicantPropertyPage11 of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		
		List<Applicant> listOfApplicant = opportunity.getApplicants();
		for (Applicant applicant : listOfApplicant) {
			
			
			List<Property> listOfPropertys = applicant.getProperties();
			Logger.debug("list Of Propertys " + listOfPropertys.size());
			for (Property property : listOfPropertys) {
				session.save(property);
			}
			
			
			
		}
		
		    
		tx.commit();
		closeSession(session, factory);

		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in updating property p in DB  for given OpportunityID="+opportunity.getId(),e);
		}
		return opportunity;
	}
	
	
	@Override
	public Opportunity updateApplicantMortgagePage11(Opportunity opportunity) throws PostGressDaoServiceException {
		try{
		Logger.info("Inside updateApplicantPropertyPage11 of PostgresDAO");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		List<Applicant> listOfApplicant = opportunity.getApplicants();
		for (Applicant applicant : listOfApplicant) {

			List<Mortgages> listOfMortgage = applicant.getListOfMortgages();
			Logger.info("getListOfMortgages  "+listOfMortgage.size());
			for (Mortgages mortgage : listOfMortgage) {
				session.save(mortgage);
				Logger.info(" mortgage To be stored ="+mortgage);


			}
			

		}
		tx.commit();
		closeSession(session, factory);
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in updating Mortgage in DB  for given OpportunityID="+opportunity.getId(),e);
		}
		return opportunity;
	}
	
	

	public void updateOpportunitySignDetails(Opportunity opportunity) throws PostGressDaoServiceException{
		
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		Object o = session.load(Opportunity.class, opportunity.getId());
		Opportunity proxyOpportunity = (Opportunity) o;
		proxyOpportunity.setStage_id(opportunity.getStage_id());
		proxyOpportunity.setApplication_date(opportunity.getApplication_date());
		
		session.update(proxyOpportunity);
	

		tx.commit();
		closeSession(session, factory);
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in Updating Opporunity 12th page Details for  OpporunityID="+opportunity.getId(),e);
		}
	}
	
	

	@Override
	public void deleteApplicantRelationshipWithOpportunity(ApplicantOpportunityRelationShip apRelationShip)throws PostGressDaoServiceException {
		try{
		Logger.debug("Inside (.) deleteApplicantRelationshipWithOpportunity method  ");
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(apRelationShip);
		tx.commit();
		closeSession(session, factory);
		Logger.info("Applicant id ="+apRelationShip.getApp_id() +" Relation Ship with OpportunityID="+apRelationShip.getOpp_id() +" is deleted " );
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error in deleting Relationship between ApplicantID="+apRelationShip.getApp_id() +" and opporunityID= "+apRelationShip.getOpp_id(),e);
		}
		}
	
	
	
	@Override
	public Lead insertLead(Lead lead) throws PostGressDaoServiceException {
		Logger.debug("Inside (.) insertLead method ");
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			 session.save(lead);
			tx.commit();
			closeSession(session, factory);

			Logger.info("lead created sucess fully with id " + lead.getId());
			return lead;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in insertLead details  for given lead ID = " + lead.getId(), e);

		}
	}

	@Override
	public Contact insertContact(Contact contact)
			throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(contact);

			tx.commit();
			closeSession(session, factory);

			Logger.info("conatct created sucess fully with id "
					+ contact.getId());
			return contact;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in insertContact details  for given contactEmail = "+contact.getEmail()
							, e);

		}
	}

	@Override
	public List<Contact> getContact(Contact contact)
			throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Contact.class);
			criteria.add(Restrictions.eq("email", contact.getEmail()));
			criteria.add(Restrictions.eq("name", contact.getName()));
			criteria.add(Restrictions.eq("last_name", contact.getLast_name()));

			@SuppressWarnings("unchecked")
			List<Contact> list = (List<Contact>) criteria.list();

			session.getTransaction().commit();

			Logger.info("conatct list   " + list);

			closeSession(session, factory);

			return list;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getContactByEmailAndLastName details  for given contact Email = "
							+ contact.getEmail(), e);

		}
	}

	@Override
	public List<Contact> getContactByEmailAndLastName(Contact contact)
			throws PostGressDaoServiceException {

		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Contact.class);
			criteria.add(Restrictions.eq("email", contact.getEmail()));
			criteria.add(Restrictions.eq("last_name", contact.getLast_name()));

			@SuppressWarnings("unchecked")
			List<Contact> list = (List<Contact>) criteria.list();

			session.getTransaction().commit();

			Logger.info("conatct list   " + list);

			closeSession(session, factory);

			return list;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getContact details  for given contact Email = "
							+ contact.getEmail(), e);

		}

	}

	@Override
	public Lead getLeadByConatctId(Contact contact)
			throws PostGressDaoServiceException {
		try {
			Lead lead = null;
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Lead.class);
			criteria.add(Restrictions.eq("partner_id", contact.getId()));
			lead = (Lead) criteria.uniqueResult();
			Logger.info("lead details = " + lead);

			closeSession(session, factory);

			return lead;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getLeadByConatctId details  for given contact ID = "
							+ contact.getId(), e);

		}
	}

	@Override
	public Contact getContactById(int id) throws PostGressDaoServiceException {
		try {
			Contact contact = null;
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Contact.class);
			criteria.add(Restrictions.eq("id", id));
			contact = (Contact) criteria.uniqueResult();
			Logger.info("contact details = " + contact);

			closeSession(session, factory);

			return contact;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getContactById details  for given id = " + id, e);

		}
	}

	@Override
	public List<Lead> getLead(Lead lead) throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Lead.class);
			criteria.add(Restrictions.eq("email_from", lead.getEmail_from()));
			criteria.add(Restrictions.eq("name", lead.getName()));

			@SuppressWarnings("unchecked")
			List<Lead> list = (List<Lead>) criteria.list();

			session.getTransaction().commit();

			Logger.info("Lead list   " + list);

			closeSession(session, factory);

			return list;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getLead details  for given leadEMail = " + lead.getEmail_from(), e);

		}
	}

	@Override
	public Referral_Source insertReferral(Referral_Source referral_Source)
			throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(referral_Source);

			tx.commit();
			closeSession(session, factory);

			Logger.info("referral resource created sucess fully with id "
					+ referral_Source.getId());
			return referral_Source;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in insertReferral details  for given referralName = "
							+ referral_Source.getName() + " ReferralEmail="+referral_Source.getEmail_from(), e);

		}
	}

	@Override
	public Referral_Source getReferralSourceById(int id)
			throws PostGressDaoServiceException {

		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Referral_Source referral_Source = session.get(
					Referral_Source.class, id);

			closeSession(session, factory);
			Logger.info("Referral Source Deatials " + referral_Source);
			return referral_Source;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getting ReferralSourceById for given ReferralID= "
							+ id, e);

		}
	}

	@Override
	public ArrayList<Referral_Source> getReferral_SourceByEmailAndName(
			String email, String name) throws PostGressDaoServiceException {

		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Referral_Source.class);
			Referral_Source referral_Source = null;
			criteria.add(Restrictions.eq("email_from", email));
			criteria.add(Restrictions.eq("name", name));

			@SuppressWarnings("unchecked")
			List<Referral_Source> list = criteria.list();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator(); iterator.hasNext();) {
				referral_Source = (Referral_Source) iterator.next();

			}
			session.getTransaction().commit();

			Logger.info("referral details  " + referral_Source);

			closeSession(session, factory);

			return (ArrayList<Referral_Source>) list;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getting Referral_SourceByEmailAndName for given Referral email= "
							+ email + " Referral Name= " + name, e);
		}
	}

	@Override
	public ArrayList<Referral_Source> getReferral_SourceByEmail(String email)
			throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Referral_Source.class);
			Referral_Source referral_Source = null;
			criteria.add(Restrictions.eq("email_from", email));

			@SuppressWarnings("unchecked")
			List<Referral_Source> list = criteria.list();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator(); iterator.hasNext();) {
				referral_Source = (Referral_Source) iterator.next();

			}
			session.getTransaction().commit();

			Logger.info("referral details  " + referral_Source);

			closeSession(session, factory);

			return (ArrayList<Referral_Source>) list;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getting Referral_SourceByEmail for given Referral email= "
							+ email, e);

		}
	}

	@Override
	public ArrayList<Referral_Source> getReferral_SourceByPartnerID(
			int partner_id) throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Referral_Source.class);
			Referral_Source referral_Source = null;
			criteria.add(Restrictions.eq("partner_id", partner_id));

			@SuppressWarnings("unchecked")
			List<Referral_Source> list = criteria.list();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator(); iterator.hasNext();) {
				referral_Source = (Referral_Source) iterator.next();

			}
			session.getTransaction().commit();

			Logger.info("referral details  " + referral_Source);

			closeSession(session, factory);

			return (ArrayList<Referral_Source>) list;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getting Referral_SourceByPartnerID for given Referral PartnerId= "
							+ partner_id, e);
		}
	}

	@Override
	public Referral_Source updateReferral(Referral_Source referral_Source)
			throws PostGressDaoServiceException {
		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.update(referral_Source);

			tx.commit();
			closeSession(session, factory);

			Logger.info("referral resource updated sucess fully with id "
					+ referral_Source.getId());
			return referral_Source;
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in updateReferral for given Referral ID= "
							+ referral_Source.getId(), e);
		}
	}

	@Override
	public int getStateID(String province) throws PostGressDaoServiceException {

		try {
			SessionFactory factory = HibernateUtils.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(State.class);
			State state = null;
			criteria.add(Restrictions.eq("code", province));
			state = (State) criteria.uniqueResult();
			closeSession(session, factory);

			Logger.info("state details = " + state);
			if (state == null)
				return 0;
			return state.getId();
		} catch (Exception e) {
			throw new PostGressDaoServiceException(
					"Error in getStateID for given province= " + province, e);

		}
	}

	private void closeSession(Session session, SessionFactory sessionFactory) {
		session.close();
	}

	private void closeSession(Session session, SessionFactory sessionFactory,
			Transaction transaction) {
		session.close();
		transaction.commit();
	}
	
	private Opportunity mapUpdateApplicantDetails(Opportunity opportunity,Opportunity oldOpporunity){
		
		if(oldOpporunity.getApplicants().size()==1){
				oldOpporunity.getApplicants().set(0, opportunity.getApplicants().get(0));
				 
			}else if(oldOpporunity.getApplicants().size()==2){
				oldOpporunity.getApplicants().set(0, opportunity.getApplicants().get(0));

			}
		
		
		
		return opportunity;
	}

	@Override
	public void deleteApplicantAddress(Applicant applicant) throws PostGressDaoServiceException {
		Logger.debug("Inside deleteApplicantIncome(Opportunity opportunity) of PostgresDAO");
		try{
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
	
			Logger.debug("app id: " + applicant.getId());
		
			List<Address> listOfaddress = applicant.getListOfAddress();
			for (Address address : listOfaddress) {
				Logger.debug("Income Id = "+address.getId());
				
					Logger.debug("Income Id = "+address.getId());
					address.setApplicant_id(applicant.getId());
					session.delete(address);	
				
			}
		
		tx.commit();
		closeSession(session, factory);

		Logger.debug("Assets information data updated into crm_asset Successfully.");
		}catch(Exception e){
			throw new PostGressDaoServiceException("Error deleting incomeof  applicants in  DB for given applicantId ="+applicant.getId(),e);
		}
		
	
		
	}

	



	

}
