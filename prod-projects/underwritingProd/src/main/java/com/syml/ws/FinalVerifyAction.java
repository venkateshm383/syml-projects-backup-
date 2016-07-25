package com.syml.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.response.FinalVerifyResponse;
import com.syml.dao.BaseDAO;
import com.syml.dao.DAOFactory;
import com.syml.domain.Lender;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.service.CrudServices;
import com.syml.service.FinalVerifyAlgorithm;
import com.syml.service.PostSelectionAlgorithm;
import com.syml.util.DataSynHelper;
import com.syml.util.HibernateUtil;

@Path("/finalVerify")
public class FinalVerifyAction {
	@Path("{opportunityId}/{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public FinalVerifyResponse get(@PathParam("opportunityId") Integer opportunityId, 
			@PathParam("productId") Integer productId){
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();


		//do something....
		//get opportunity 
		//calculation
		
		FinalVerifyResponse finalVerifyResponse = new FinalVerifyResponse();
		finalVerifyResponse.setTestData();
		
		DataSynHelper helper = new DataSynHelper();
		Opportunity opp = null;
		try {
			opp = helper.getOpportunityFromERP(opportunityId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(opp == null)
		{
			System.out.println("Opp id not exist! return sample response");
			return finalVerifyResponse;
		}
		CrudServices<Product> productCrud = new CrudServices<Product>(Product.class,true);
		productCrud.setHsession(session);
		Product product = productCrud.get(productId);
		try
		{
			AlgoOpportunity algoOpportunity = new AlgoOpportunity(opp);
			AlgoProduct algoProduct = new AlgoProduct(product);
			
			FinalVerifyAlgorithm algorithm = new FinalVerifyAlgorithm(algoOpportunity,algoProduct, session);
						
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

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return finalVerifyResponse;
	}
}
