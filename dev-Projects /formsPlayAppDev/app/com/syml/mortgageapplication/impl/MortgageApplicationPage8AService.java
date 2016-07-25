package com.syml.mortgageapplication.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import play.Logger;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Income;
import controllers.Opportunity;

public class MortgageApplicationPage8AService {
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	
	
	public Opportunity listEmployeIncome(Opportunity opportunity,String [] employeBusinessName,String [] employeTotalMonths,String [] employeDesgination){
		
		for (int i = 0; i < employeDesgination.length; i++) {
	
			Income income=new Income();
			income.setName(MortgageApplicationConstants.EMPLOYEE);
			income.setTypeOfIncome(MortgageApplicationConstants.EMPLOYEE_ID);		
			income.setBusiness(employeBusinessName[i]);
			income.setMonth(Integer.parseInt(employeTotalMonths[i]));
			income.setPosition(employeDesgination[i]);
			income.setApplicantId(opportunity.getApplicants().get(0).getId());
			opportunity.getApplicants().get(0).getIncomes().add(income);
		}
		return opportunity;
	}

	
public Opportunity listSlefEmployeIncome(Opportunity opportunity,String [] slefemployEBusinessName,String [] slefEmployeTotalMonths,String [] slefEmployeDesgination){
		
		for (int i = 0; i < slefemployEBusinessName.length; i++) {
	
			Income income=new Income();
			income.setName(MortgageApplicationConstants.SLEF_EMPLOYEE);

			income.setTypeOfIncome(MortgageApplicationConstants.SLEF_EMPLOYEE_ID);
			income.setBusiness(slefemployEBusinessName[i]);
			income.setMonth(Integer.parseInt(slefEmployeTotalMonths[i]));
			income.setPosition(slefEmployeDesgination[i]);
			income.setApplicantId(opportunity.getApplicants().get(0).getId());
			opportunity.getApplicants().get(0).getIncomes().add(income);
		}
		return opportunity;
	}
/**
 * To create incomes of applicant , Update Opportunity In PostGressDB and Store Opportunity Pojo to Couchbase 
 * @param opportunity
 * @return opportunity
 * @throws MortgageApplicationPageServiceException
 */
public Opportunity createIncomeOfApplicants(Opportunity opportunity) throws MortgageApplicationPageServiceException{
	try {
		opportunity.setPogressStatus(75);
		if(opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes"))
			opportunity.setPogressStatus(70);
		posService.updateApplicantIncomePage8Or9(opportunity);
		couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

	} catch (PostGressDaoServiceException e) {
		throw new MortgageApplicationPageServiceException("Error In inserting income Details Into Db ",e);
	} catch (CouchbaseServiceException e) {
		throw new MortgageApplicationPageServiceException("Error In storing Opportunity Details Into Couchbase ",e);

	}
	
	return opportunity;
	
}
public void deleteIncome(Opportunity opportunity) throws MortgageApplicationPageServiceException  {
    Logger.debug("Inside (.) deleteIncome of  MortgageApplicationPageEightService class ");
    try {
		posService.deleteApplicantIncome(  opportunity.getApplicants().get(0));
	    opportunity.getApplicants().get(0).setIncomes(new ArrayList<Income>());

	} catch (PostGressDaoServiceException e) {
		throw new MortgageApplicationPageServiceException("Error In deleting income details From Db ",e);

	}
	}

public List<Object> setEmpIncome(List<Income> listOfEmploye){
	
	List<Object> list=new ArrayList<Object>();
	ArrayList<Income> listOfEmpIncome=new ArrayList<Income>();
	ArrayList<Income> listOfSlefEmpIncome=new ArrayList<Income>();
	for (int i = 0; i < 8; i++) {
		listOfEmpIncome.add(new Income());
		listOfSlefEmpIncome.add(new Income());
	}
int j=0;
int i=0;
	for (Iterator iterator = listOfEmploye.iterator(); iterator
			.hasNext();) {
		Income income = (Income) iterator.next();
		
		Logger.debug("income value : "+income);
		if(income.getName()!=null&& income.getName().equalsIgnoreCase(MortgageApplicationConstants.EMPLOYEE)){
			listOfEmpIncome.set(j++,income);
		}
		if(income.getName()!=null&& income.getName().equalsIgnoreCase(MortgageApplicationConstants.SLEF_EMPLOYEE)){
			listOfSlefEmpIncome.set(i++,income);
		}
		
	}
	
list.add(listOfEmpIncome);
list.add(listOfSlefEmpIncome);
list.add(j);
list.add(i);
Logger.debug("income list values : "+list );
return list;
}


 

}
