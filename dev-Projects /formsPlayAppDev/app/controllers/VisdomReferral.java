package controllers;

import static play.data.Form.form;

import java.io.IOException;
import java.util.Date;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.client;
import views.html.clientV;
import views.html.error;
import views.html.leadsucess;
import views.html.planner;
import views.html.plannerV;
import views.html.professional;
import views.html.professionalV;
import views.html.realtor;
import views.html.realtorV;
import views.html.visdomferral;
import views.html.visdomferralV;
import views.html.visdomreferral2;
import views.html.visdomreferral3;
import views.html.wfg;
import views.html.wfg2;
import views.html.wfgV;

import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.Constants;
import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.dao.impl.PostgresDAO;
import com.syml.referralsource.impl.ReferralConstants;
import com.syml.referralsource.impl.ReferralSourcePageService;
import com.syml.referralsource.impl.ReferralSourcePageServiceException;

@SuppressWarnings("unused")
public class VisdomReferral extends Controller {
	// dev------
	public static final String SUBMIT_REFERRAL_LINK = "http://dev-forms.visdom.ca/clientrefV?referralId=";
	// prod
	// private final String
	// SUBMIT_REFERRAL_LINK="http://forms.visdom.ca/clientrefV?referralId=";

	// DEv
public	static final String DOC_VIEWER_LINK = "https://dev-doc.visdom.ca/getid?id=doc_ReferralAgreemetfile_";

	// prod

	// static final String
	// DOC_VIEWER_LINK="https://doc.visdom.ca/getid?id=doc_ReferralAgreemetfile_";


/**
 * To render BookMark page
 * @return
 */
	public static Result bookmark() {
		Logger.debug("inside  bookmark() in VisdomReferral controller"); 
		return ok(visdomreferral3.render(""));

	}

	/**
	 * To Render Referral Page 
	 * @return
	 */
	public static Result referral() {
		
		Logger.debug("inside  referral() in VisdomReferral controller"); 

		return ok(visdomferral.render(""));

	}

	/**
	 * To Render Wfg video page 
	 * @return
	 */
	public static Result wfgV() {
		Logger.debug("inside  wfgV() in VisdomReferral controller");
		return ok(wfgV.render(""));
	}

	/**
	 * To render wfg non Video Page
	 * @return
	 */
	public static Result wfg() {
		Logger.debug("inside  wfg() in VisdomReferral controller");
		return ok(wfg.render(""));
	}

	/**
	 * To render Referral video Page 
	 * @return
	 */
	public static Result referralV() {
		Logger.debug("inside  referralV() in VisdomReferral controller");
		return ok(visdomferralV.render(""));

	}

	/**
	 * To render Realtor non Video page 
	 * @return
	 */
	public static Result realtor() {
		Logger.debug("inside  realtor() in VisdomReferral controller");
		return ok(realtor.render(""));

	}

	/**
	 * To render Realtor video page 
	 * @return
	 */
	public static Result realtorV() {
		Logger.debug("inside  realtorV() in VisdomReferral controller");
		return ok(realtorV.render(""));

	}

	/**
	 * To render Professional non video page
	 * @return
	 */
	public static Result professional() {
		Logger.debug("inside  professional() in VisdomReferral controller");
		return ok(professional.render(""));

	}

	/**
	 * To render professional video page
	 * @return
	 */
	public static Result professionalV() {
		Logger.debug("inside  professionalV() in VisdomReferral controller");
		return ok(professionalV.render(""));

	}

	/**
	 * To render planner Non video page 
	 * @return
	 */
	public static Result planner() {
		Logger.debug("inside  planner() in VisdomReferral controller");
		return ok(planner.render(""));

	}

	/**
	 * To render planner Non video page 
	 * @return
	 */
	public static Result plannerV() {
		Logger.debug("inside  plannerV() in VisdomReferral controller");
		return ok(plannerV.render(""));

	}

	/**
	 * to render client non video page
	 * @return
	 */
	public static Result client() {
		Logger.debug("inside  client() in VisdomReferral controller");
		return ok(client.render(""));

	}

	/**
	 * To render client Non video page 
	 * @return
	 */
	public static Result clientV() {
		Logger.debug("inside  clientV() in VisdomReferral controller");
		return ok(clientV.render(""));

	}

	
	/**
	 * To get Referral Form details , create referral if not exist else update Referral
	 * stored data couchbase 
	 * 
	 * @return
	 */
	public static Result visdomReferral() {
		Logger.debug("inside  visdomReferral() in VisdomReferral controller");
		DynamicForm dynamicForm = form().bindFromRequest();
		int couchbaseSucessValue = 0;
		int referralExist = 0;
		CouchBaseService storeData = new CouchBaseService();
		String []  arrayOfAddress=	request().body().asFormUrlEncoded().get("formatted_address");
		ReferralSourcePageService refService=new ReferralSourcePageService();
		IPostGresDaoService iPostGresDaoService = new PostgresDAO();
		Referral_Source referral_Source = refService.loadReferralSourceForm(dynamicForm,arrayOfAddress);
		try {
		Contact contact =refService.checkContactExist(dynamicForm);
		referral_Source.setActive(true);
	
				referral_Source=	refService.createReferralSource(contact, referral_Source);

			referral_Source.setType_Referral(ReferralConstants.REFERRAL_SOURCE_FORM);
			referral_Source.setReferralUrl(referral_Source.getId());
			storeData.storeDataToCouchbase(
					ReferralConstants.REFERRAL_SOURCE_FORM_COUHBASE_KEY
							+ referral_Source.getId(), referral_Source);

		}catch(CouchbaseServiceException e){
			Logger.error("error in storing data to couchbase ", e);
			return ok(error.render(Constants.ERROR_MESSAGE
					
					, ReferralConstants.COUCHBASE_ERROR
					));
		} catch (PostGressDaoServiceException e) {

			Logger.error("Error in inserting Referral Details into Postgress DB = "+e);
			return ok(error.render(Constants.ERROR_MESSAGE
				
					, ReferralConstants.POSTGRES_ERROR
					));
		} 

	
			return ok(visdomreferral2.render(referral_Source.getRoleName(),
					referral_Source.getId(), referralExist));

		
	}
	
	/**
	 * To get Referral form 2 page values , generate Referral Agreement PDF
	 * send referral Agreement  mail to REferral (broker or agent ) 
	 * update referral with new update details 
	 * @return
	 */
	public static Result visdomReferralForm2() {
		
		Logger.debug("inside  visdomReferralForm2() in VisdomReferral controller");
		int couchbaseSucessValue = 0;
	
		ReferralSourcePageService  reService=new ReferralSourcePageService();

		DynamicForm dynamicForm = form().bindFromRequest();
		String referrelId = dynamicForm.get("referrelID");
		Referral_Source referral_Source = null;
		CouchBaseService storeData = new CouchBaseService();
		IPostGresDaoService iPostGresDaoService = new PostgresDAO();
		try{
		JsonObject json = storeData.getCouhbaseDataByKey(ReferralConstants.REFERRAL_SOURCE_FORM_COUHBASE_KEY+ referrelId);
		referral_Source = new ObjectMapper().readValue(json.toString(),	Referral_Source.class);

		referral_Source=reService.loadSecondFormData(dynamicForm,referral_Source);
		referral_Source.setStage_id(ReferralConstants.REFERAL_INVOLVED_STAGE_ID);
		referral_Source.setAgreement_date(new Date());
		referral_Source.setActive(true);
		iPostGresDaoService.updateReferral(referral_Source);

		String filePath=reService.sendMailofReferralAgrement(referral_Source);
		referral_Source=reService.storeReferralAgrementToCouchbase(filePath, referral_Source);
		reService.deleteFile(filePath);

		storeData.storeDataToCouchbase(ReferralConstants.REFERRAL_SOURCE_FORM_COUHBASE_KEY	+ referrelId, referral_Source);
		String agreementURL = DOC_VIEWER_LINK + referrelId;
		int ontraPortRoleId=reService.getOntharaportId(referral_Source);
		reService.createContactInOntharaPort(referral_Source,
					ontraPortRoleId, agreementURL);
			
			/**
			 * #TODO un comment before deploy to production
			 */
				//new ReferralTaskCreationRestCall(referral_Source).start();			

		}catch(CouchbaseServiceException  e){
		
			Logger.error("errro while processing second Referral source form ",e);
			return ok(error.render(Constants.ERROR_MESSAGE
			,
					ReferralConstants.COUCHBASE_ERROR
					));
		}catch (PostGressDaoServiceException |NullPointerException e) {

			Logger.error("Error in updating Referral Details into Postgress DB ",e);
			return ok(error.render(Constants.ERROR_MESSAGE
					
					, ReferralConstants.POSTGRES_ERROR
					));
		} catch (ReferralSourcePageServiceException e) {
			Logger.error("Error in generating Referral Agreement PDF ",e);
			return ok(error.render(Constants.ERROR_MESSAGE
					,
					 ReferralConstants.PDF_GENERATION_ERROR
					));
		} catch (IOException e) {
			Logger.error("Error in sending Mail of REferral Agreement PDF ",e);

			return ok(error.render(Constants.ERROR_MESSAGE
					
					, ReferralConstants.MAIL_ERROR
					));
		} 

		
			if (referral_Source.getReferralExist() == 0) {
				return ok(leadsucess
						.render("Thank you for becoming involved in the Visdom Referral Program. We have sent a copy of the referral agreement to the email provided. In the event you do not see it please check your spam folder in case your provider accidentally miscategorized it."));
			} else {
				return ok(leadsucess
						.render("Thank you for your participation in the Visdom Referral Program.  Your information has been successfully updated. We have sent a copy of the referral agreement to the email provided. In the event you do not see it please check your spam folder in case your provider accidentally miscategorized it."));

			}
		

	}



	/**
	 * To get wfg1 form pages details , create referral if not exist else update Referral
	 * stored data couchbase 
	 * @return
	 */
	public static Result wfg1() {
		Logger.debug("inside wfg1() in VisdomReferral controller .");
		
		DynamicForm dynamicForm = form().bindFromRequest();
		int couchbaseSucessValue = 0;
		int referralExist = 0;
		CouchBaseService storeData = new CouchBaseService();
		String []  arrayOfAddress=	request().body().asFormUrlEncoded().get("formatted_address");
		ReferralSourcePageService refService=new ReferralSourcePageService();
		IPostGresDaoService iPostGresDaoService = new PostgresDAO();
		Referral_Source referral_Source = refService.loadReferralSourceForm(dynamicForm,arrayOfAddress);
		referral_Source.setCompany("WFG");
		referral_Source.setCompensation("Company");
		try {
		Contact contact =refService.checkContactExist(dynamicForm);
		referral_Source.setActive(true);
	
			
		
			referral_Source=	refService.createReferralSource(contact, referral_Source);
			referral_Source.setType_Referral(ReferralConstants.REFERRAL_SOURCE_FORM);
			referral_Source.setReferralUrl(referral_Source.getId());
			storeData.storeDataToCouchbase(
					ReferralConstants.REFERRAL_SOURCE_FORM_COUHBASE_KEY
							+ referral_Source.getId(), referral_Source);

		}catch(CouchbaseServiceException  e){
			Logger.error("error in storing data to couchbase ", e);
			return ok(error.render(Constants.ERROR_MESSAGE
					,
					 ReferralConstants.COUCHBASE_ERROR
					));
		} catch (PostGressDaoServiceException e) {

			Logger.error("Error in inserting Referral Details into Postgress DB = "+e);
			return ok(error.render(Constants.ERROR_MESSAGE
					
					, ReferralConstants.POSTGRES_ERROR
					));
		} 

			
				return ok(wfg2.render(referral_Source.getRoleName(),
					referral_Source.getId(), referralExist));
		

	}
	/**
	 * To get wfg form 2 page values , generate Referral Agreement PDF
	 * send referral Agreement  mail to REferral (broker or agent ) 
	 * update referral with new update details 
	 * @return
	 */
	public static Result wfg2() {
		Logger.debug("inside wfg2() in VisdomReferral controller .");
		int couchbaseSucessValue = 0;
	
		ReferralSourcePageService  reService=new ReferralSourcePageService();

		DynamicForm dynamicForm = form().bindFromRequest();
		String referrelId = dynamicForm.get("referrelID");
		Referral_Source referral_Source = null;
		CouchBaseService storeData = new CouchBaseService();
		IPostGresDaoService iPostGresDaoService = new PostgresDAO();
		try{
		JsonObject json = storeData.getCouhbaseDataByKey(ReferralConstants.REFERRAL_SOURCE_FORM_COUHBASE_KEY+ referrelId);
		referral_Source = new ObjectMapper().readValue(json.toString(),	Referral_Source.class);

		referral_Source=reService.loadSecondFormData(dynamicForm,referral_Source);
		
	
		referral_Source.setStage_id(ReferralConstants.REFERAL_INVOLVED_STAGE_ID);
		referral_Source.setAgreement_date(new Date());
		referral_Source.setActive(true);
		iPostGresDaoService.updateReferral(referral_Source);

		String filePath=reService.sendMailofReferralAgrementForWfg(referral_Source);
		referral_Source=reService.storeReferralAgrementToCouchbase(filePath, referral_Source);
		reService.deleteFile(filePath);

		storeData.storeDataToCouchbase(ReferralConstants.REFERRAL_SOURCE_FORM_COUHBASE_KEY	+ referrelId, referral_Source);
		String agreementURL = DOC_VIEWER_LINK + referrelId;
		int ontraPortRoleId=reService.getOntharaportId(referral_Source);
		reService.createContactInOntharaPort(referral_Source,
					ontraPortRoleId, agreementURL);
			
			/**
			 * #TODO un comment before deploy to production add Exception on task
			 */
				//new ReferralTaskCreationRestCall(referral_Source).start();			

		}catch(CouchbaseServiceException  e){
			
			Logger.error("errro while processing second Referral source form ",e);
			return ok(error.render(Constants.ERROR_MESSAGE
				
					, ReferralConstants.COUCHBASE_ERROR
					));
		}catch (PostGressDaoServiceException |NullPointerException e) {

			Logger.error("Error in updating Referral Details into Postgress DB ",e);
			return ok(error.render(Constants.ERROR_MESSAGE
					,
					 ReferralConstants.POSTGRES_ERROR
					));
		} catch (ReferralSourcePageServiceException e) {
			Logger.error("Error in generating Referral Agreement PDF ",e);
			return ok(error.render(Constants.ERROR_MESSAGE
					,
					 ReferralConstants.PDF_GENERATION_ERROR
					));
		} catch (IOException e) {
			Logger.error("Error in sending Mail of REferral Agreement PDF ",e);

			return ok(error.render(Constants.ERROR_MESSAGE
					,
					 ReferralConstants.MAIL_ERROR
					));
		} 

	
					if (referral_Source.getReferralExist() == 0) {
				return ok(leadsucess
						.render("Thank you for becoming involved in the Visdom Referral Program. We have sent a copy of the referral agreement to the email provided. In the event you do not see it please check your spam folder in case your provider accidentally miscategorized it."));
			} else {
				return ok(leadsucess
						.render("Thank you for your participation in the Visdom Referral Program.  Your information has been successfully updated. We have sent a copy of the referral agreement to the email provided. In the event you do not see it please check your spam folder in case your provider accidentally miscategorized it."));

			}
		

	}

	

	
	

}
