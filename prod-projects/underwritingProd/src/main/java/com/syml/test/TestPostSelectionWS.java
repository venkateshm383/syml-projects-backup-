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
import com.syml.bean.response.AllProductResponse;
import com.syml.bean.response.DesiredProductResponse;
import com.syml.bean.response.PostSelectionResponse;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.service.AllProductsAlgorithm;
import com.syml.service.CrudServices;
import com.syml.service.PostSelectionAlgorithm;
import com.syml.util.HibernateUtil;

public class TestPostSelectionWS {
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
				
			}
			
			System.out.println("size: "+applicants.size());
			opportunity.applicants = applicants;
			
			//AlgoOpportunity clientOpportunity = new AlgoOpportunity(opportunity);
			
			PostSelectionAlgorithm algorithm = new PostSelectionAlgorithm(opportunity,product, session);
			
			
			PostSelectionResponse postSelectionResponse = new PostSelectionResponse();
			postSelectionResponse.setAssistantTasks(algorithm.assistantTasks);
			postSelectionResponse.setBrokerTasks(algorithm.brokerTasks);
			postSelectionResponse.setDealNotes(algorithm.dealNotes);
			System.out.println(postSelectionResponse);
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
