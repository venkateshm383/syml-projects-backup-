 package com.syml.test;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.response.DesiredProductResponse;
import com.syml.bean.response.FinalVerifyResponse;
import com.syml.bean.response.PostSelectionResponse;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.service.CrudServices;
import com.syml.service.FinalVerifyAlgorithm;
import com.syml.service.PostSelectionAlgorithm;
import com.syml.util.HibernateUtil;

public class TestFinalVerifyWS {
	Integer opportunityId = 1;
	Integer productId = 10;
	
	@Test
	public void testGetDesiredProduct() {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CrudServices<Opportunity> oppCrud = new CrudServices<Opportunity>(Opportunity.class, true);
			oppCrud.setHsession(session);
			Opportunity opportunity = oppCrud.get(opportunityId);
			
			CrudServices<Product> productCrud = new CrudServices<Product>(Product.class,true);
			productCrud.setHsession(session);
			Product product = productCrud.get(productId);
			
			BaseDAO<Applicant, Integer> appDAO = new BaseDAOImpl<Applicant, Integer>(Applicant.class, session);
			List<Applicant> applicants = appDAO.findByHql("from "+Applicant.class.getName()+" where id='15' or id='18'", null );
			BaseDAO<Income, Integer> incomeDAO = new BaseDAOImpl<Income, Integer>(Income.class, session);
			for (Applicant applicant : applicants) {
				List<Income> incomes = incomeDAO.findByHql("from "+Income.class.getName()+" where applicantId='"+applicant.getId()+"'", null);
				for(Income income: incomes)
				{
					income.typeOfIncome = AlgoIncome.IncomeType.Rental.name();
				}
				applicant.incomes = incomes;
				
				applicant.includeInOpportunity = true;
				applicant.beaconScore = 5;
				
			}
			
			System.out.println("size: "+applicants.size());
			opportunity.applicants = applicants;
			
			AlgoOpportunity algoOpportunity = new AlgoOpportunity(opportunity);
			AlgoProduct algoProduct = new AlgoProduct(product);
			
			FinalVerifyAlgorithm algorithm = new FinalVerifyAlgorithm(algoOpportunity,algoProduct, session);			
			
			FinalVerifyResponse finalVerifyResponse = new FinalVerifyResponse();
			finalVerifyResponse.setAssistantTasks(algorithm.AssistantTasks);
			finalVerifyResponse.setBrokerTasks(algorithm.BrokerTasks);
			finalVerifyResponse.setDealNotes(algorithm.DealNotes);
			finalVerifyResponse.setAmortization(algorithm.clientOpportunity.amortization);
			finalVerifyResponse.setBaseCompAmount(algorithm.clientOpportunity.baseCompAmount);
			finalVerifyResponse.setBrokerFee(algorithm.clientOpportunity.brokerFee);
			finalVerifyResponse.setCashBack(algorithm.clientOpportunity.cashBack);
			finalVerifyResponse.setDownpayment(algorithm.clientOpportunity.downpaymentAmount);
			finalVerifyResponse.setInsurerfee(algorithm.clientOpportunity.insurerfee);
			finalVerifyResponse.setLenderFee(algorithm.clientOpportunity.lenderFee);
			finalVerifyResponse.setLenderName(algorithm.clientOpportunity.lenderName);
			finalVerifyResponse.setMarketingCompAmount(algorithm.clientOpportunity.marketingCompAmount);
			finalVerifyResponse.setMonthlyPayment(algorithm.clientOpportunity.monthlyPayment);
			finalVerifyResponse.setMortgageType(algorithm.clientOpportunity.mortgageType);
			finalVerifyResponse.setOpenClosed(algorithm.clientOpportunity.openClosed);
			finalVerifyResponse.setRate(algorithm.clientOpportunity.rate);
			finalVerifyResponse.setTerm(algorithm.clientOpportunity.term);
			finalVerifyResponse.setTotalCompAmount(algorithm.clientOpportunity.totalCompAmount);
			finalVerifyResponse.setTotalMortgageAmount(algorithm.clientOpportunity.totalMortgageAmount);
			finalVerifyResponse.setTotalOneTimeFees(algorithm.clientOpportunity.totalOneTimeFees);
			finalVerifyResponse.setTrailerCompAmount(algorithm.clientOpportunity.trailerCompAmount);
			finalVerifyResponse.setVolumeBonusAmount(algorithm.clientOpportunity.volumeBonusAmount);
			
			System.out.println(finalVerifyResponse);
			//TODO
			//allProductResponse.setProductids(algorithm.)
			
//			allProductResponse.setTestData();
		} catch (Exception e) {
			e.printStackTrace();
			if(tx!=null)
				tx.rollback();
		}
	}
	
	
}
