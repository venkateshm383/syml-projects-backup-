package com.syml.mortgageapplication.impl;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.Session;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Applicant;
import controllers.ApplicantOpportunityRelationShip;
import controllers.Opportunity;

public class MortgageApplicationPage2Service {
	MortgageApplicationPageService mortAppService = new MortgageApplicationPageService();
	PostGresDaoService postGresDaoService = new PostGresDaoService();
	CouchBaseService couchBaseService=null;
	CouchbaseServiceHelper couchbaseServiceHelper= new CouchbaseServiceHelper();
	Applicant applicant = null;
	/**
	 * 
	 * @param dynamicForm
	 * @param session
	 * @return
	 * @throws MortgageApplicationPageServiceException
	 * Update page2 where the data taken from Dynamic FORM and update in COCUHBASE AND POSTGRESS.	
	 */
	public Opportunity updatePage2Details(DynamicForm dynamicForm, Session session)throws  MortgageApplicationPageServiceException {
		couchBaseService= new CouchBaseService();
		Opportunity opportunity=null;
		try{
		
		opportunity	=couchbaseServiceHelper.getCouhbaseDataByKey(mortAppService.parseLeadId(session));
			opportunity.setUser_id(MortgageApplicationConstants.USER_ID);
			opportunity.setHr_department_id(MortgageApplicationConstants.HR_DEPARTMENT_ID);
			opportunity.setCreate_uid(MortgageApplicationConstants.USER_ID);

			String lendingGoal = dynamicForm.get("term");
			opportunity.setLendingGoal(lendingGoal);
			opportunity.setWhat_is_your_lending_goal(mortAppService.getLendingGoalForPG(lendingGoal));
			opportunity.setOtherArea(dynamicForm.get("otherTextArea"));
			String additionalApplicants = dynamicForm.get("additionalApplicants");
			
			opportunity.setIsAdditionalApplicantExist(getAdditionalApplicantYesNo(additionalApplicants));
			Logger.info("applicant list size = "+opportunity.getApplicants().size());
			if(opportunity.getApplicants().size()>0){
				//to get existing applicant 
				applicant=opportunity.getApplicants().get(0);
				opportunity.getApplicants().set(0, getApplicant(dynamicForm,applicant));
				
				if(opportunity.getApplicants().size()>1){
					//to get existing coapplicant
				if (opportunity.getIsAdditionalApplicantExist() != null && opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes")){
				applicant=	opportunity.getApplicants().get(1);
					opportunity.getApplicants().set(1,getCoApplicant(dynamicForm,applicant));
					
	
				}else{//to remove coapplicant from opportunity Relationship
					ApplicantOpportunityRelationShip apRelationShip = new ApplicantOpportunityRelationShip();
					apRelationShip.setApp_id(opportunity.getApplicants().get(1).getId());
					apRelationShip.setOpp_id(opportunity.getId());
					Logger.debug("delete   apRelationShip  --->"+apRelationShip);
					opportunity.getApplicants().remove(1);
					Logger.debug("delete   apRelationShip  --->"+apRelationShip);
					postGresDaoService.deleteApplicantRelationWithOpportunity(apRelationShip);
					
				}
				}else{
					Logger.info("applicant Exist "+opportunity.getIsAdditionalApplicantExist());
					if (opportunity.getIsAdditionalApplicantExist() != null && opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes")){
					applicant=null;
						opportunity.getApplicants().add( getCoApplicant(dynamicForm,applicant));
					}
				}

				
			}else{
				opportunity.getApplicants().add( getApplicant(dynamicForm,applicant));
				if (opportunity.getIsAdditionalApplicantExist() != null && opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes"))
					opportunity.getApplicants().add( getCoApplicant(dynamicForm,applicant));

			}
		
			Logger.info("applicant size at last "+opportunity.getApplicants().size());
			opportunity.setPogressStatus(10);
			opportunity.setStage_id(MortgageApplicationConstants.OPPORTUNITY_PARTIAL_STAGE_ID);
			opportunity.setType(MortgageApplicationConstants.TYPE);

			opportunity = postGresDaoService.createApplicant(opportunity);
			
			setSession(opportunity, session);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+ opportunity.getId(), opportunity);
			return opportunity;
		} catch (PostGressDaoServiceException  e ) {
			throw new MortgageApplicationPageServiceException("Error when inserting data to postgress  for given Opporunity  ="+opportunity,e);

		}catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error when inserting data to couchbase  for given Opporunity  ="+opportunity,e);
	
		} catch (MortgageApplicationPageServiceException e) {
			throw new MortgageApplicationPageServiceException("Error in parsing lead ID in session oobject to integer  crmLeadid="+opportunity,e);
		}catch (Exception e) {
			Logger.error("Error in Update Page2 Details ",e);
		}
		return opportunity;
		
	}
	/**
	 * 
	 * @param dynamicForm
	 * @param applicant
	 * @return
	 * SET THE APPLICANT BY FORM AND GET THE POJO OF APPLICANT.	
	 * 
	 * 
	 */
	private Applicant getApplicant(DynamicForm dynamicForm,Applicant applicant){
		if(applicant==null)
		applicant = new Applicant();
		String relationshipStatus = dynamicForm.get("appRelStatus");
		if (relationshipStatus != null && relationshipStatus.equalsIgnoreCase("Common-Law")) {
			relationshipStatus = "Common_Law";
		}
		
		applicant.setApplicant_name(dynamicForm.get("firstName"));
		applicant.setApplicant_last_name(dynamicForm.get("lastName"));
		applicant.setEmail_personal(dynamicForm.get("email"));
		applicant.setCell(dynamicForm.get("applMobPhone"));
		applicant.setRelationship_status(relationshipStatus);
		applicant.setAdditionalReason(dynamicForm.get("reasonNotInclude"));
		return applicant;
	}
	/**
	 * 
	 * @param dynamicForm
	 * @param applicant
	 * @return
	 * SET THE CO-APPLICANT BY FORM AND GET THE POJO OF CO-APPLICANT.
	 */
	private Applicant getCoApplicant(DynamicForm dynamicForm,Applicant applicant){
		if(applicant==null)
		applicant = new Applicant();
		String CoApprelationshipStatus = dynamicForm.get("coAppRelStatus");
		applicant.setApplicant_name(dynamicForm.get("adFirstName"));
		applicant.setApplicant_last_name(dynamicForm.get("adLastName"));
		applicant.setEmail_personal(dynamicForm.get("adEmail"));
		applicant.setCell(dynamicForm.get("coApplMobPhone"));
		if (CoApprelationshipStatus != null 
				&& CoApprelationshipStatus.equalsIgnoreCase("Common-Law")) {
			CoApprelationshipStatus = "Common_Law";
		}
		applicant.setRelationship_status(CoApprelationshipStatus);
		
		return applicant;
	}
	/**
	 *
	 * @param additionalApplicants
	 * @return
	 * If Additional CO_APPLICANT PRESENT RETURN STRING "YES" OTHERWISE "NO"
	 */
	private String getAdditionalApplicantYesNo(String additionalApplicants){
		if(additionalApplicants != null && additionalApplicants.equalsIgnoreCase("on"))
			additionalApplicants="yes";
			else
				additionalApplicants="no";
		return additionalApplicants;
	}
	/**
	 * 
	 * @param opportunity
	 * @param session
	 * SET THE SEESION FOR OPPORTUNITY.
	 * 
	 */
	private void setSession(Opportunity opportunity, Session session) {
		Logger.debug("inside (.) setSession   with opporunity = "+opportunity);
		
		Logger.debug("applicant size "+opportunity.getApplicants().size());
		try{
		session.put("additionalApplicants", opportunity.getIsAdditionalApplicantExist());
		session.put("leadingGoal", opportunity.getLendingGoal());
		session.put("applicantFirstName", opportunity.getApplicants().get(0).getApplicant_name());
		session.put("applicantLasttName", opportunity.getApplicants().get(0).getApplicant_last_name());
		session.put("applicantEmail", opportunity.getApplicants().get(0).getEmail_personal());
		session.put("applicantID", opportunity.getApplicants().get(0).getId()+ "");
		if (opportunity.getIsAdditionalApplicantExist() != null && opportunity.getIsAdditionalApplicantExist().equals("yes"))
			session.put("applicantID2", opportunity.getApplicants().get(1).getId()+ "");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
