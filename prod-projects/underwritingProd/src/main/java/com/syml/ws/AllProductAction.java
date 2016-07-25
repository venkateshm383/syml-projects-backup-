package com.syml.ws;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.response.AllProductResponse;
import com.syml.dao.BaseDAO;
import com.syml.dao.DAOFactory;
import com.syml.domain.Lender;
import com.syml.domain.Opportunity;
import com.syml.domain.Task;
import com.syml.service.AllProductsAlgorithm;
import com.syml.service.DesiredProductAlgorithm;
import com.syml.util.DataSynHelper;
import com.syml.util.HibernateUtil;

@Path("/allProduct")
public class AllProductAction {

	private static final Logger logger = LoggerFactory.getLogger(AllProductAction.class);

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AllProductResponse get(@PathParam("id") Integer id){

		logger.debug("accessing /allProduct with parameter id:{}", id);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction().isActive() ? session.getTransaction() : session.beginTransaction();
		BaseDAO<Lender,Integer> lenderDao = DAOFactory.getInstance().getLenderDAO(session);

		//do something....
		//get opportunity 
		//calculation
		
		
		AllProductResponse allProductResponse = new AllProductResponse();
		//allProductResponse.setTestData();
		DataSynHelper helper = new DataSynHelper();
		Opportunity opp = null;
		try {
			opp = helper.getOpportunityFromERP(id);
			opp.setOpportunityId(id);
			if (opp.applicationNo == null)
				opp.applicationNo = id.toString();
		} catch (Exception e1) {
			StackTraceElement[] stack = e1.getStackTrace();
		    String exception = "";
		    for (StackTraceElement s : stack) {
		        exception = exception + s.toString() + "\n\t\t";
		    }
		    System.out.println(exception);
		    
		    
		}
		if(opp == null)
		{
			System.out.println("Opp id not exist! return sample response");
			return allProductResponse;
		}
		AlgoOpportunity algoOpp = new AlgoOpportunity(opp); // This line is where the OpenERP Opportunity is set into an algoOpportunity Object if there are issues with Accessing variables, this is the place to look
		try{
			// tx = session.beginTransaction();
			
			AllProductsAlgorithm allProductAlgorithm = new AllProductsAlgorithm(algoOpp, session);
			allProductResponse.setAssistantTasks(allProductAlgorithm.assistantTasks);
			allProductResponse.setBrokerTasks(allProductAlgorithm.brokerTasks);
			allProductResponse.setDealNotes(allProductAlgorithm.dealNotes);
			allProductResponse.setMarketingTemplateNotes(allProductAlgorithm.marketingTemplateNotes);
			allProductResponse.setRecommendations(allProductAlgorithm.Recommendations);
			
			tx.commit();
			
		}catch (Exception e) {
			StackTraceElement[] stack = e.getStackTrace();
		    String exception = "";
		    for (StackTraceElement s : stack) {
		        exception = exception + s.toString() + "\n\t\t";
		    }
		    
		    Calendar currentDate2 = new GregorianCalendar();
			Calendar deadline2 = new GregorianCalendar();
			deadline2.add(Calendar.HOUR, 1);
			e.printStackTrace();
			Task task2 = new Task("There was an error in the All Product Action 77.  Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
					e.getMessage() + exception,
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			allProductResponse.getAssistantTasks().add(task2); 
			
			tx.rollback();
		}
		finally
		{
			if(session.isOpen())
				session.close();
		}

		logger.warn(">>> WARNING: transaction status should be inactive. is active:{}", tx.isActive());
		
		return allProductResponse;
	}
}
