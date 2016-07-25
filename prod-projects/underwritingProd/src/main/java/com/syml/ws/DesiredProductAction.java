package com.syml.ws;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.bean.algo.AlgoNote;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.response.DesiredProductResponse;
import com.syml.constant.Constant;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Opportunity;
import com.syml.service.CrudServices;
import com.syml.service.DesiredProductAlgorithm;
import com.syml.util.DataSynHelper;
import com.syml.util.HibernateUtil;

@Path("/desiredProduct")
public class DesiredProductAction {
	
	private DesiredProductAlgorithm desiredProductAlgorithm;
	private CrudServices<Opportunity> crudOpp;
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DesiredProductResponse get(@PathParam("id") Integer id){
		
		// DesiredProductResponse desiredProduct = new DesiredProductResponse(); // Was not commented out
		//desiredProduct.setTestData();
		DataSynHelper helper = new DataSynHelper();
		Opportunity opp = null;
		try {
			opp = helper.getOpportunityFromERP(id);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(opp == null)
		{
			System.out.println("Opp id not exist! return sample response");
			// TODO Need to put in a Note that opportunity is Null ... 
			DesiredProductResponse desiredProduct = new DesiredProductResponse();
			desiredProduct.nullOpportunity();
			return desiredProduct;
		}
		
		try{
			AlgoOpportunity algoOpp = new AlgoOpportunity(opp);
			Session session = HibernateUtil.getSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				
				DesiredProductAlgorithm desiredProductAlgorithm = new DesiredProductAlgorithm(algoOpp, session);
				
//				desiredProduct.setAssistantNotes(desiredProductAlgorithm.assistantTasks); // Was UnCommented
//				desiredProduct.setBrokerNotes(desiredProductAlgorithm.brokerTasks);// Was UnCommented
//				desiredProduct.setOpportunityId(opp.getId());// Was UnCommented
//				desiredProduct.setProductId(desiredProductAlgorithm.potentialProduct.getId());// Was UnCommented
				//tx.commit(); // Was Commented out ... 
				//DesiredProductResponse desiredProduct = new DesiredProductResponse(opp.getId(), desiredProductAlgorithm.potentialProduct.getId(), desiredProductAlgorithm.dealNotes, desiredProductAlgorithm.assistantTasks, desiredProductAlgorithm.brokerTasks);
				//DesiredProductResponse desiredProduct = new DesiredProductResponse(opp.getId(), desiredProductAlgorithm.potentialProduct.getId(), desiredProductAlgorithm.dealNotes, desiredProductAlgorithm.assistantTasks, desiredProductAlgorithm.brokerTasks);
				DesiredProductResponse desiredProduct = new DesiredProductResponse(desiredProductAlgorithm.dealNotes, desiredProductAlgorithm.assistantTasks, desiredProductAlgorithm.brokerTasks);
				return desiredProduct;
			}catch (Exception e) {
				
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
								
				//e.printStackTrace();
	//			if(tx!=null)
	//				tx.rollback();
				DesiredProductResponse desiredProduct = new DesiredProductResponse(); // Inserrted to eliminate error ...
				AlgoNote note = new AlgoNote();
				note.description = "ERROR - " + exceptionAsString;
				note.noteType = AlgoNote.TypeOfNote.AssistantAction;
				note.urgency = AlgoNote.Priority.High;
				desiredProduct.getDealNotes().add(note);
				return desiredProduct;
			}
			finally
			{
				if(tx!=null)
					tx.rollback();
				// tx.rollback();
				if(session.isOpen())
					session.close();
			}
		}catch (Exception e) {
			String error1 = e.getStackTrace().toString();
			//e.printStackTrace();
//			if(tx!=null)
//				tx.rollback();
			DesiredProductResponse desiredProduct = new DesiredProductResponse();
			AlgoNote note1 = new AlgoNote();
			note1.description = "2nd Error" + error1;
			note1.noteType = AlgoNote.TypeOfNote.AssistantAction;
			note1.urgency = AlgoNote.Priority.High;
			desiredProduct.getDealNotes().add(note1);
			return desiredProduct;
		}
		
		//desiredProduct.setTestData();
		
		//return desiredProduct;
	}
}
