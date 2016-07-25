 package com.syml.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.response.DesiredProductResponse;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Opportunity;
import com.syml.service.CrudServices;
import com.syml.service.DesiredProductAlgorithm;
import com.syml.util.HibernateUtil;

public class TestDesiredProductWS {
	String opportunityId = "1";
	
//	@Test
	public static void main(String[] args) {
//		Client client = Client.create();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/desiredProduct");
//		GenericType<JAXBElement<DesiredProductResponse>> generic = new GenericType<JAXBElement<DesiredProductResponse>>(){};
//		JAXBElement<DesiredProductResponse> jaxb = resource.path(opportunityId).accept(MediaType.APPLICATION_JSON).get(generic);
//		DesiredProductResponse desiredProductResponse = jaxb.getValue();
//		System.out.println(desiredProductResponse);
		
		DesiredProductResponse desiredProduct = new DesiredProductResponse();
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
		
			CrudServices<Opportunity> crudOpp = new CrudServices<Opportunity>(Opportunity.class, true);
			crudOpp.setHsession(session);
			Opportunity opp = crudOpp.get(1);  //get openerp
			
			System.out.println(opp.toString());
			
			//init test data
			opp.whatIsYourLendingGoal="Prequalify";
			opp.livingInProperty = "Renter";
			
			opp.job5Years = "5";
			opp.incomeDecreasedWorried = "Somewhat_Disagree";
			opp.buyNewVehicle = "Choose_not_to_Answer";
			opp.lifestyleChange = "Choose_not_to_Answer";
			opp.financialRiskTaker = "Somewhat_Disagree";
			opp.propertyLessThen5Years = "Somewhat_Disagree";
			opp.futureFamily = "Choose_not_to_Answer";
			opp.isCottageRecProperty = opp.isCottageRecProperty == null ? false : true;
			opp.downPaymentComingFrom = "Monthly";
			opp.renterPayHeating = "HeatSeparate";
			opp.preapprovedImLookingFora = "CondoMobileHome";
			opp.city = "a";
			
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
			opp.applicants = applicants;
			//end init test data
			
			AlgoOpportunity algoOpp = new AlgoOpportunity(opp);
			
			//init test data
			//algoOpp.preapprovedImLookingFora = AlgoOpportunity.Looking.DontKnow;
			//end
			
			DesiredProductAlgorithm desiredProductAlgorithm = new DesiredProductAlgorithm(algoOpp, session);
			
			desiredProduct.setAssistantNotes(desiredProductAlgorithm.assistantTasks);
			desiredProduct.setBrokerNotes(desiredProductAlgorithm.brokerTasks);
			desiredProduct.setOpportunityId(opp.getId());
			desiredProduct.setProductId(desiredProductAlgorithm.potentialProduct.getId());
			
			System.out.println("desiredProduct:" + desiredProduct);
			tx.rollback();
		}catch (Exception e) {
			e.printStackTrace();
//			if(tx!=null)
//				tx.rollback();
		}
		finally
		{
			if(session.isOpen())
				session.close();
		}
		System.out.println("end");
	}
	
}
