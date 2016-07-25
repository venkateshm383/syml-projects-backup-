package com.syml.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.response.AllProductResponse;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Opportunity;
import com.syml.service.AllProductsAlgorithm;
import com.syml.service.CrudServices;
import com.syml.util.HibernateUtil;

public class TestAllProductsWS {
	Integer opportunityId = 1;
	
	@Test
	public void testGetDesiredProduct() {
//		Client client = Client.create();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/allProduct");
//		GenericType<JAXBElement<AllProductResponse>> generic = new GenericType<JAXBElement<AllProductResponse>>(){};
//		JAXBElement<AllProductResponse> jaxb = resource.path(opportunityId).accept(MediaType.APPLICATION_JSON).get(generic);
//		AllProductResponse allProductResponse = jaxb.getValue();
//		System.out.println(allProductResponse.getAssistantNotes().get(0).content);
		
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CrudServices<Opportunity> oppCrud = new CrudServices<Opportunity>(Opportunity.class, true);
			oppCrud.setHsession(session);
			Opportunity opportunity = oppCrud.get(opportunityId);
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
			
			AlgoOpportunity clientOpportunity = new AlgoOpportunity(opportunity);
			
			AllProductsAlgorithm algorithm = new AllProductsAlgorithm(clientOpportunity,session);
			
			
			AllProductResponse allProductResponse = new AllProductResponse();
			allProductResponse.setAssistantTasks(algorithm.assistantTasks);
			allProductResponse.setBrokerTasks(algorithm.brokerTasks);
			allProductResponse.setDealNotes(algorithm.dealNotes);
			allProductResponse.setMarketingTemplateNotes(algorithm.marketingTemplateNotes);
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

