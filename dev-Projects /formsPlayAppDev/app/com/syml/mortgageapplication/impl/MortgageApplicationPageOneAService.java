package com.syml.mortgageapplication.impl;

import play.data.DynamicForm;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Applicant;
import controllers.ApplicantOpportunityRelationShip;
import controllers.Opportunity;

public class MortgageApplicationPageOneAService {
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	MortgageApplicationPageService mService=new MortgageApplicationPageService();
	
	/**
	 * to map Form Values to Opporunity Pojo , applicant
	 * @param dynamicForm
	 * @return  opportunity
	 * @throws MortgageApplicationPageServiceException 
	 * @throws PostGressDaoServiceException 
	 */
	public Opportunity loadFormData(Opportunity opportunity  ,DynamicForm dynamicForm) throws MortgageApplicationPageServiceException  {
		Applicant applicant=new Applicant();
		if(opportunity.getApplicants().size()>0){
		applicant=	opportunity.getApplicants().get(0);
		}
		applicant.setApplicant_name(dynamicForm.get("firstName"));
		applicant.setApplicant_last_name(dynamicForm.get("lastName"));
		applicant.setEmail_personal(dynamicForm.get("email"));
		applicant.setCell(dynamicForm.get("applMobPhone"));
		String relationshipStatus = dynamicForm.get("appRelStatus");
		if (relationshipStatus != null && relationshipStatus.equalsIgnoreCase("Common-Law")) {
			relationshipStatus = "Common_Law";
		}
		applicant.setRelationship_status(relationshipStatus);
		applicant.setAdditionalReason(dynamicForm.get("reasonNotInclude"));
		opportunity.setIsAdditionalApplicantExist(getAdditionalApplicantYesNo(dynamicForm.get("additionalApplicants")));
		
		opportunity.setLendingGoal(dynamicForm.get("term"));
		opportunity.setWhat_is_your_lending_goal(mService.getLendingGoalForPG(dynamicForm.get("term")));
		opportunity.setOtherArea(dynamicForm.get("otherTextArea"));
		
		opportunity.setPogressStatus(5);
		opportunity.setStage_id(MortgageApplicationConstants.OPPORTUNITY_PARTIAL_STAGE_ID);
		opportunity.setType(MortgageApplicationConstants.TYPE);
		opportunity.setUser_id(MortgageApplicationConstants.USER_ID);
		opportunity.setHr_department_id(MortgageApplicationConstants.HR_DEPARTMENT_ID);
		opportunity.setCreate_uid(MortgageApplicationConstants.USER_ID);
		if(opportunity.getApplicants().size()>0){
			//update opportunity 
				opportunity.getApplicants().set(0, applicant);
				//to Remove coapplicant Relationship with Opportunity pojo
				if(opportunity.getApplicants().size()>1){
					if(opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("No")){
					ApplicantOpportunityRelationShip apRelationShip=new ApplicantOpportunityRelationShip();
					apRelationShip.setApp_id(opportunity.getApplicants().get(1).getId());
					apRelationShip.setOpp_id(opportunity.getId());
					opportunity.getApplicants().remove(1);
					try{
					posService.deleteApplicantRelationWithOpportunity(apRelationShip);
					}catch(PostGressDaoServiceException e){
						throw new MortgageApplicationPageServiceException("Error in deleting IN DB existing coapplicant Relation with Opporunity ="+opportunity.getId(),e);
					}
					}
				}
				
			}else{
				
				opportunity.getApplicants().add(applicant);

			}

		
		return opportunity;
	}

	
	
	/**
	 * To Create applicant , Update Opportunity In PostGressDB and Store Opportunity Pojo to Couchbase 
	 * @param opportunity
	 * @return opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity createApplicants(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.createApplicant(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In inserting Applicant Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In storing Opportunity Details Into Couchbase ",e);

		}
		
		return opportunity;
		
	}
	private String getAdditionalApplicantYesNo(String additionalApplicants){
		if(additionalApplicants != null && additionalApplicants.equalsIgnoreCase("on"))
			additionalApplicants="yes";
			else
				additionalApplicants="no";
		return additionalApplicants;
	}
	
}
