package com.syml.ws;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.json.JsonException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;

import org.codehaus.jettison.json.JSONException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.vbucket.ConnectionException;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.UnderwritePostSel;
import com.syml.bean.algo.XMLValidationException;
import com.syml.bean.response.PostSelectionResponse;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.domain.Task;
import com.syml.service.CouchbaseData;
import com.syml.service.CrudServices;
import com.syml.service.PostSelectionAlgorithm;
import com.syml.util.DataSynHelper;
import com.syml.util.HibernateUtil;
import com.syml.util.SelectionHelper;
import com.syml.util.XmlSerializer;

@Path("/postSelection")
public class PostSelection {

	private static final Logger logger = LoggerFactory.getLogger(PostSelection.class);

	@Path("{opportunityId}/{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PostSelectionResponse get(@PathParam("opportunityId") Integer opportunityId, 
			@PathParam("productId") Integer productId) {
		
		
		String couchbaseProductId1=null;
		String couchbaseProductId2=null;
		String productType=null;

		

		logger.info("accessing /postSelection with opportunityId={}, productId={}", opportunityId, productId);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction().isActive() ? session.getTransaction() : session.beginTransaction();
		
		//BaseDAO<Lender,Integer> lenderDao = DAOFactory.getInstance().getLenderDAO(session);

		//do something....
		//get opportunity 
		//calculation 
		PostSelectionResponse postSelectionResponse = new PostSelectionResponse();
		postSelectionResponse.setTestData();
		DataSynHelper helper = new DataSynHelper();
		Opportunity opp = null;
		try {
			opp = helper.getOpportunityFromERP(opportunityId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("accessing /postSelection with opportunityId={}, productId={}",e1.getMessage());
		}
		if(opp == null)
		{
			logger.info(">> Opp id not exist! return sample response");
			return postSelectionResponse;
		}
		try{
			logger.info("--------------------------getSelectedProduct----------------------------------------------------"+opp.getSelectedProduct());
			logger.info("--------------------------Bypass Proposal----------------------------------------------------"+opp.getIsBypassProposal());
			}catch(NullPointerException e){
				logger.error("Error  in  getting  ByPassProposal"+e.getMessage());
				
			}
		
		
		
	
		String  couchbaseProducName=null;
		CouchbaseData  data=new  CouchbaseData();
		try{
		int selected_Product=opp.getSelectedProduct();
		}catch(NullPointerException  e){
			logger.error("Eror  in  getting Slect  Product   id");
			
		}
		//get value of productId from Couchbase
		int cbProductID = 0;
		/*try{
			
			try {
				logger.info("------------------inside  couchbase Call  for getProductId------------------");

				couchbaseProductId1 = data.getDataFromCouchbase(opportunityId).getJSONArray("Recommendations").getJSONObject(0).getString("SelectedProductID");
				couchbaseProducName=data.getDataFromCouchbase(opportunityId).getJSONArray("Recommendations").getJSONObject(0).getString("ProductType");
			logger.info("####couchbaseProductId1  is---------------------------------------"+couchbaseProductId1);

			
			} catch (JSONException e) {
				
			logger.error("Recomdation  not  found added "+e);
			}
			//logger.info("select  Rec Value  is"+ data.getDataFromCouchbase(opportunityId));
			//cbProductID = Integer.parseInt(couchbaseProductId1);
		}catch(ConnectionException e){
			logger.error("Eror  connection Couchbase "+e);
		}*/

			try {
				logger.info("-------------Before if  getIsBypassProposal   -------------------------------  ");
				if(opp.getIsBypassProposal()==false){
					
					
					
					try{
						couchbaseProductId1 = data.getDataFromCouchbase(opportunityId).getJSONArray("Recommendations").getJSONObject(0).getString("selectedProductID");
						couchbaseProductId2 = data.getDataFromCouchbase(opportunityId).getJSONArray("Recommendations").getJSONObject(0).getString("selectedAdditionalProductID");
					}catch(NullPointerException e ){
						System.out.println(" null come here couchbaseProductId1  "+e.getMessage());
						}
					if("null".equals(couchbaseProductId1)){
						logger.info("---------------------couchbaseProductId1  null ---------");
						couchbaseProductId1="1932";
					}
					
					productType = data.getDataFromCouchbase(opportunityId).getJSONArray("Recommendations").getJSONObject(0).getString("productType");

				}else{
					logger.info("-------------Else  ByPass  Logic  for  couchbaseProductId1-------------------------------  ");

					couchbaseProductId1="1932";
					couchbaseProductId2="1932";
					
					
					
				}
				
				
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				logger.error("Eror  JSONException Couchbase "+e1);
			}
			//logger.info("select  Rec Value  is"+ data.getDataFromCouchbase(opportunityId));
			try{
				cbProductID = Integer.parseInt(couchbaseProductId1);
				
					
					//cbProductID = Integer.parseInt(couchbaseProductId1);

					
				
			}catch(NullPointerException e){
				logger.error("Eror  Null in  cb "+e);

			}

             
			
			
		/*}catch(NullPointerException e){
			logger.error("Error  in  Converting  Coichbase  Product");
			
		}*/

		Product product=null;
		
		CrudServices<Product> productCrud = new CrudServices<Product>(Product.class,true);
		productCrud.setHsession(session);

		
		
		int productToRetrive = 0;
		int productToRetrive2 = 0;
		if(opp.getIsBypassProposal() == true){
            logger.info("--------------------------Bypass Proposal getSelectedProduct  is null and BypassProposal is true ---------------------------------------------------");
			productToRetrive = 1932;
			productToRetrive2=1932;
			 product = productCrud.get(productToRetrive);
        }
		else {
            logger.info("--------------------------Bypass Proposal getSelectedProduct  is not null and BypassProposal is false ---------------------------------------------------");
			
           
            
			logger.info("----------------------------------------------couchbaseProducName  is------------------------"+couchbaseProducName);

			 if(productType.contains("Single")){
				 logger.info("--------------------------Single  Selected  Product ---------------------------------------------------");
	                //int cbb=Integer.parseInt(couchbaseProductId1);
	                
	                productToRetrive = Integer.parseInt(couchbaseProductId1);
	                product = productCrud.get(productToRetrive);
                
                
                

            	
            }
			 else{
                
                logger.info("--------------------------Combined  Selected  Product ---------------------------------------------------");

               // productToRetrive = opp.getSelectedProduct();

                productToRetrive= Integer.parseInt(couchbaseProductId1);
                productToRetrive2 = Integer.parseInt(couchbaseProductId2);
               


                product = productCrud.get(productToRetrive);
                product = productCrud.get(productToRetrive2);
                logger.info("--------------------------productToRetrive  Single --------------------------------------------------"+productToRetrive);
                logger.info("--------------------------productToRetrive --------------------------------------------------"+productToRetrive2);


            	
            }
            
            
            
        }
		/* */
		
		
		
	//product = productCrud.get(1932);


		
		

		AlgoOpportunity clientOpportunity1 = new AlgoOpportunity(opp);
		AlgoProduct potentialProduct1 = new AlgoProduct(product);
		
		// If all lenders are in Database, then maybe put this is a not null reference try catch block 
		// With the above String Splitting Logic in the Catch block ... 
//		CrudServices<Lender> lenderCrud = new CrudServices<Lender>(Lender.class,true);
//		lenderCrud.setHsession(session);
//		Lender lender = lenderCrud.get(product.lender);
		try
		{
			logger.info("----------------------------------------Calling  UnderwritePostSel  Algorithim Class-----------------------------------------------------");
			PostSelectionAlgorithm algorithm = new PostSelectionAlgorithm(opp,product, session);
			UnderwritePostSel underwrite2 = new UnderwritePostSel(clientOpportunity1, potentialProduct1, session);
			
			String productName = product.name;    	
			String[] productNameArray = productName.split("\\s*-\\s*");
			String LenderNamefromDB = productNameArray[0];

			// TODO set all other values from algorithm. ... 
			postSelectionResponse.setLtv(algorithm.qualifying.LTV);
			postSelectionResponse.setGDS(algorithm.qualifying.GDSRatio);
			postSelectionResponse.setTDS(algorithm.qualifying.TDSRatio);
			postSelectionResponse.setLender_name(LenderNamefromDB);
			postSelectionResponse.setPurchase_price(algorithm.qualifying.lendableValue);
			postSelectionResponse.setDownpayment_amount(algorithm.qualifying.downPaymentWithDebtReduce);
			postSelectionResponse.setTotal_mortgage_amount(algorithm.qualifying.expectedMortgageAmount);
			postSelectionResponse.setCommitment_fee((double)0);
			postSelectionResponse.setInsurer_fee(algorithm.qualifying.insuranceAmount);
			postSelectionResponse.setLender_fee(algorithm.qualifying.lenderFee);
			postSelectionResponse.setBroker_fee(algorithm.qualifying.brokerFee);
			postSelectionResponse.setTotal_one_time_fees(algorithm.qualifying.oneTimeFees);
			postSelectionResponse.setRate(algorithm.qualifying.interestRate);
			postSelectionResponse.setTerm(algorithm.qualifying.potentialProduct.term);
			postSelectionResponse.setAmortization(algorithm.qualifying.amortization);
			
			//postSelectionResponse.setFrequency(algorithm.qualifying.interestRate); // Frequency not yet accounted for ... 
			postSelectionResponse.setPayment(algorithm.qualifying.expectedFitnessPayment);
			postSelectionResponse.setMortgage_type(algorithm.qualifying.potentialProduct.mortgageType);
			postSelectionResponse.setOpen_closed(algorithm.qualifying.potentialProduct.openClosed);
			
			postSelectionResponse.setCashback(algorithm.qualifying.cashbackAmount);
			postSelectionResponse.setBase_comp_amount(algorithm.qualifying.baseCompAmount);
			postSelectionResponse.setVolume_bonus_amount(algorithm.qualifying.volumeBonusCompAmount);
			postSelectionResponse.setTrailer_comp_amount(algorithm.qualifying.trailerCompAmount);
			postSelectionResponse.setMarketing_comp_amount(algorithm.qualifying.marketingCompAmount);
			postSelectionResponse.setTotal_comp_amount(algorithm.qualifying.totalCompAmount);
			postSelectionResponse.setTotal_comp_amount(algorithm.qualifying.totalCompAmount);
			postSelectionResponse.setReferral_fee(algorithm.qualifying.referralFee);
			postSelectionResponse.setPlusMinusPrime(algorithm.qualifying.plusMinusPrime);
			postSelectionResponse.setPostedRate(algorithm.qualifying.potentialProduct.postedRate);
			postSelectionResponse.setPostedRate(algorithm.qualifying.percentVariable);
			
			if (algorithm.clientOpportunity.expectedClosingDate != null){
				Calendar closingDateCalendar = DateToCalendar(algorithm.clientOpportunity.expectedClosingDate);
				postSelectionResponse.setClosingDate(formatDate(closingDateCalendar));
			}
				
			
			if (algorithm.qualifying.potentialProduct.term.contains("1") ){
				if (algorithm.clientOpportunity.expectedClosingDate != null){							
					Calendar closingDateCalendar = DateToCalendar(algorithm.clientOpportunity.expectedClosingDate);
					closingDateCalendar.add(Calendar.MONTH, 6);
					postSelectionResponse.setRenewalDate(formatDate(closingDateCalendar));
				}
			}
			else{
				int addYears = 0;
				if (algorithm.qualifying.potentialProduct.term.contains("2") ){
					addYears = 1;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("3") ){
					addYears = 2;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("4") ){
					addYears = 3;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("5") ){
					addYears = 4;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("6") ){
					addYears = 5;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("7") ){
					addYears = 6;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("8") ){
					addYears = 7;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("9") ){
					addYears = 8;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("10") ){
					addYears = 9;
				}
				else if (algorithm.qualifying.potentialProduct.term.contains("11") ){
					addYears = 10;
				}
				if (postSelectionResponse.closingDate != null){		
					Calendar closingDateCalendar = DateToCalendar(algorithm.clientOpportunity.expectedClosingDate);
					closingDateCalendar.add(Calendar.YEAR, addYears);
					postSelectionResponse.setRenewalDate(formatDate(closingDateCalendar));
				}
			}
			
			
			if (algorithm.qualifying.potentialProduct.lender != null)
				postSelectionResponse.setLender(algorithm.qualifying.potentialProduct.lender);
			
			// This notes for the lender are set in the Underwrite2 Class line 3119
//			for (LenderNote task : algorithm.qualifying.lenderNotesForAssembly) {
//				// TODO Walk through notes and determine if there needs to be an assignment or Division into different lists.
//				Note dealNote = new Note();
//				dealNote.name = task.content;
//			}
			postSelectionResponse.setAssistantTasks(algorithm.assistantTasks);
			postSelectionResponse.setBrokerTasks(algorithm.brokerTasks);
			postSelectionResponse.setDealNotes(algorithm.dealNotes);
			
			
			// The above code Is everything required to complete the information going to OpenERP
			// Assuming the deal passes, we want to now send it to Filogix.
			if (SelectionHelper.compareSelection(AlgoProduct.ApplicationMethod.Filelogix, algorithm.qualifying.potentialProduct.applicationMethod)
					|| SelectionHelper.compareSelection(AlgoProduct.ApplicationMethod.Either, algorithm.qualifying.potentialProduct.applicationMethod)){
				// Check to ensure the deal passes ... 
				if (algorithm.qualifying.opportunityQualifies == true){
				
					// Create a Fileogix Class passing in Underwrite2 so data is available for xml writing.
					// TODO uncomment to run the filogix info.
					// FilogixCall callToFilogix = new FilogixCall(algorithm.qualifying);
					
					
					
					
				}
			}
			
		}
		catch(Exception e)
		{
			StackTraceElement[] stack = e.getStackTrace();
		    String exception = "";
		    for (StackTraceElement s : stack) {
		        exception = exception + s.toString() + "\n\t\t";
		    }
		    System.out.println(exception);
            
			Calendar currentDate2 = new GregorianCalendar();
			Calendar deadline2 = new GregorianCalendar();
			deadline2.add(Calendar.HOUR, 1);

			if (e instanceof XMLValidationException) {
				XMLValidationException ve = (XMLValidationException) e;
				ValidationEventCollector eventCollector = ve.getValidationCollector();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("WS Provider named '").append(ve.getWebServiceProviderName()).
					append("' need following data to be fixed: ");
				for (ValidationEvent validationEvent : eventCollector.getEvents()) {
					stringBuilder.append(validationEvent.getMessage()).append("; ");
				}
				Task validationTask = new Task("Validation Problem", 
						"",
						"Assistant", 
						"Opportunity.ID", 
						"Opportunity.Team", 
						deadline2,
						"",
						0d, 
						0.1, 
						currentDate2, 
						deadline2);
				postSelectionResponse.getAssistantTasks().add(validationTask);
				logger.warn(">>> Validation event occured.");
			}

			Task task2 = new Task("There was an ERROR IN THE UNDERWRITE2 CLASS ERROR SECTION 4220. Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
			e.getMessage() + exception,
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);
			postSelectionResponse.getAssistantTasks().add(task2);

			logger.error(">>> {}", e.toString());
		}
		return postSelectionResponse;
		
	}
	
	public String formatDateTime(Calendar cal){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	String strDate = sdfDate.format(cal.getTime());
        return strDate;
    }
	
	public String formatDate(Calendar cal){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
    	String strDate = sdfDate.format(cal.getTime());
        return strDate;
    }
	
	public static Calendar DateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}

}