package com.syml.mortgageapplication.impl;

import java.util.ArrayList;

import play.Logger;
import play.data.DynamicForm;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Mortgages;
import controllers.Opportunity;
import controllers.Property;

public class MortgageApplicationPageElevenService {
	PostGresDaoService posService = new PostGresDaoService();
	CouchBaseService couchBaseService = new CouchBaseService();

	/**
	 * To Convert given String values to Double  
	 * @param inputString
	 * @return Double
	 */
	private Double convertStringToDouble(String inputString) {
		Double result = null;
		if (inputString != null && !inputString.equalsIgnoreCase("")) {
			try {
				result = Double.parseDouble(inputString);
			} catch (Exception pe) {
				Logger.error("Error when parsing string to double ", pe);
			}
		}
		return result;
	}

	/**
	 * To convert given String values to Integer
	 * @param inputString
	 * @return Integer 
	 */
	private Integer convertStringToInteger(String inputString) {
		Integer result = null;
		if (inputString != null && !inputString.equalsIgnoreCase("")) {
			try {
				result = Integer.parseInt(inputString);
			} catch (Exception pe) {
				Logger.error("Error when parsing string to Integer ", pe);
			}
		}
		return result;
	}

	private Boolean getSelling(String inputSelling) {
		Boolean result = null;
		if (inputSelling != null && inputSelling.equalsIgnoreCase("on")) {
			result = true;
		}
		return result;
	}

	/**
	 * To update applicant with property and mortgage values in DB and update Opportunity Pojo into couchbase 
	 * @param opportunity
	 * @return
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity updateApplicantProperties(Opportunity opportunity)
			throws MortgageApplicationPageServiceException {

		try {
			opportunity.setPogressStatus(95);
			posService.updateApplicantPropertyPage11(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY
									+ opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException(
					"Error In updating property  Details Into Db ", e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException(
					"Error In updating Opportunity Details Into Couchbase ", e);

		}
		return opportunity;
	}

	/**
	 * 	 * To get map form values which property values to  Property pojo, Mortgage and applicant Pojo

	 * @param opportunity
	 * @param dynamicForm
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity getPropertyList(Opportunity opportunity,
			DynamicForm dynamicForm)
			throws MortgageApplicationPageServiceException {

		String howManyProperties = dynamicForm.get("howManyProperties");
		String additionalApplicantYesNo = opportunity
				.getIsAdditionalApplicantExist();

		try {
			if (howManyProperties != null
					&& howManyProperties.equalsIgnoreCase("one")) {
				getPropertyOneList(opportunity, dynamicForm,
						additionalApplicantYesNo);
			} else if (howManyProperties != null
					&& howManyProperties.equalsIgnoreCase("two")) {
				getPropertyTwoList(opportunity, dynamicForm,
						additionalApplicantYesNo);
			} else if (howManyProperties != null
					&& howManyProperties.equalsIgnoreCase("three")) {
				getPropertyThreeList(opportunity, dynamicForm,
						additionalApplicantYesNo);
			} else if (howManyProperties != null
					&& howManyProperties.equalsIgnoreCase("four")) {
				getPropertyFourList(opportunity, dynamicForm,
						additionalApplicantYesNo);
			} else if (howManyProperties != null
					&& howManyProperties.equalsIgnoreCase("more")) {
				getPropertyFiveList(opportunity, dynamicForm,
						additionalApplicantYesNo);
			}
		} catch (NullPointerException e) {
			throw new MortgageApplicationPageServiceException(
					"Error Parsing propertylist from form values  and when setting opportunity  ",
					e);
		}
		return opportunity;

	}

	/**
	 	 * To get map form values which property values to  Property pojo, Mortgage and applicant Pojo
 
	 * @param opportunity
	 * @param dynamicForm
	 * @param additionalApplicantYesNo
	 * @return Opportunity
	 */
	private Opportunity getPropertyOneList(Opportunity opportunity,
			DynamicForm dynamicForm, String additionalApplicantYesNo) {

		Property property = new Property();
		Mortgages mortgage = new Mortgages();
		String ownership1 = dynamicForm.get("ownership1");
		Integer mortgageVal = convertStringToInteger(dynamicForm
				.get("mortgage1"));
		property.setName(dynamicForm.get("address0"));
		property.setValue(convertStringToDouble(dynamicForm.get("appxValue1")));
		property.setMoCondoFees(convertStringToInteger(dynamicForm
				.get("condoFees1")));
		property.setMonthlyRent(convertStringToInteger(dynamicForm
					.get("rentMo1")));
		property.setPropertyId("1");
		property.setSelling(getSelling(dynamicForm.get("agree1")));
		property.setMortgageValue(mortgageVal);

		if (additionalApplicantYesNo != null
				&& additionalApplicantYesNo.equalsIgnoreCase("yes")
				&& opportunity.getApplicants().get(1).getApplicant_name()
						.equalsIgnoreCase(ownership1)) {
			property.setApplicantId(opportunity.getApplicants().get(1).getId());
			property.setOwnwerShip(ownership1);
		} else {
			property.setApplicantId(opportunity.getApplicants().get(0).getId());
			property.setOwnwerShip(ownership1);

		}
		if (mortgageVal != null && mortgageVal > 0) {
			mortgage.setMonthlyRent(convertStringToInteger(dynamicForm
					.get("rentMo1")));
			mortgage.setApplicantId(property.getApplicantId());
			mortgage.setPropertyId("1");
			mortgage.setSelling(getSelling(dynamicForm.get("agree1")));

		}
		opportunity.getApplicants().get(0).getListOfMortgages().add(mortgage);
		opportunity.getApplicants().get(0).getProperties().add(property);

		return opportunity;
	}

	/**
	 * To get map form values which property values to  Property pojo, Mortgage and applicant Pojo
	 * @param opportunity
	 * @param dynamicForm
	 * @param additionalApplicantYesNo
	 * @return Opportunity
	 */
	private Opportunity getPropertyTwoList(Opportunity opportunity,
			DynamicForm dynamicForm, String additionalApplicantYesNo) {

		for (int i = 1; i <= 2; i++) {
			Property property = new Property();
			Mortgages mortgage = new Mortgages();
			String ownership1 = dynamicForm.get("ownership2" + i);
			Integer mortgageVal = convertStringToInteger(dynamicForm
					.get("mortgage2" + i));
			property.setName(dynamicForm.get("currentAddress2" + i));
			property.setValue(convertStringToDouble(dynamicForm
					.get("appxValue2" + i)));
			property.setMoCondoFees(convertStringToInteger(dynamicForm
					.get("condoFees2" + i)));
			property.setPropertyId(i + "");
			property.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo2" + i)));
			property.setSelling(getSelling(dynamicForm.get("agreeTwo" + i)));
			property.setMortgageValue(mortgageVal);

			opportunity.getApplicants().get(0).getProperties().add(property);
			if (additionalApplicantYesNo != null
					&& additionalApplicantYesNo.equalsIgnoreCase("yes")
					&& opportunity.getApplicants().get(1).getApplicant_name()
							.equalsIgnoreCase(ownership1)) {
				property.setApplicantId(opportunity.getApplicants().get(1)
						.getId());
				property.setOwnwerShip(ownership1);

			} else {
				property.setApplicantId(opportunity.getApplicants().get(0)
						.getId());
				property.setOwnwerShip(ownership1);

			}
			if (mortgageVal != null && mortgageVal > 0) {
				mortgage.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo2" + i)));
				mortgage.setApplicantId(property.getApplicantId());
				mortgage.setPropertyId("" + i);
				mortgage.setSelling(getSelling(dynamicForm.get("agreeTwo" + i)));

			}
			opportunity.getApplicants().get(0).getListOfMortgages()
					.add(mortgage);

		}
		return opportunity;
	}

	/**
	 * To map form values which has three properties values to set into property pojo,Mortgage pojo , applicant  
	 * @param opportunity
	 * @param dynamicForm
	 * @param additionalApplicantYesNo
	 * @return Opportunity
	 */
	private Opportunity getPropertyThreeList(Opportunity opportunity,
			DynamicForm dynamicForm, String additionalApplicantYesNo) {

		Logger.info("inside (.) getPropertyThreeList Method ");
		for (int i = 1; i <= 3; i++) {
			Property property = new Property();
			Mortgages mortgage = new Mortgages();
			String ownership1 = dynamicForm.get("ownership3" + i);
			Integer mortgageVal = convertStringToInteger(dynamicForm
					.get("mortgage3" + i));
			Logger.info("Mortgagae " + mortgageVal);
			property.setName(dynamicForm.get("currentAddress3" + i));
			property.setValue(convertStringToDouble(dynamicForm
					.get("appxValue3" + i)));
			property.setMoCondoFees(convertStringToInteger(dynamicForm
					.get("condoFees3" + i)));
			property.setPropertyId(i + "");
			property.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo3" + i)));
			property.setSelling(getSelling(dynamicForm.get("agreeThree" + i)));
			property.setMortgageValue(mortgageVal);

			if (additionalApplicantYesNo != null
					&& additionalApplicantYesNo.equalsIgnoreCase("yes")
					&& opportunity.getApplicants().get(1).getApplicant_name()
							.equalsIgnoreCase(ownership1)) {
				property.setApplicantId(opportunity.getApplicants().get(1)
						.getId());
				property.setOwnwerShip(ownership1);

			} else {
				property.setApplicantId(opportunity.getApplicants().get(0)
						.getId());
				property.setOwnwerShip(ownership1);


			}
			if (mortgageVal != null && mortgageVal > 0) {
				mortgage.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo3" + i)));
				mortgage.setApplicantId(property.getApplicantId());
				mortgage.setPropertyId("" + i);
				mortgage.setSelling(getSelling(dynamicForm
						.get("agreeThree" + i)));

			}
			opportunity.getApplicants().get(0).getListOfMortgages()
					.add(mortgage);
			opportunity.getApplicants().get(0).getProperties().add(property);

		}
		return opportunity;

	}

	/**
	 * To Map form value which has four property list value  to Property , mortgage and applicant pojo
	 * @param opportunity
	 * @param dynamicForm
	 * @param additionalApplicantYesNo
	 * @return Opportunity
	 */
	private Opportunity getPropertyFourList(Opportunity opportunity,
			DynamicForm dynamicForm, String additionalApplicantYesNo) {

		for (int i = 1; i <= 4; i++) {
			Property property = new Property();
			Mortgages mortgage = new Mortgages();
			String ownership1 = dynamicForm.get("ownership4" + i);
			Integer mortgageVal = convertStringToInteger(dynamicForm
					.get("mortgage4" + i));
			property.setName(dynamicForm.get("currentAddress4" + i));
			property.setValue(convertStringToDouble(dynamicForm
					.get("appxValue4" + i)));
			property.setMoCondoFees(convertStringToInteger(dynamicForm
					.get("condoFees4" + i)));
			property.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo4" + i)));
			property.setPropertyId(i + "");
			property.setSelling(getSelling(dynamicForm.get("agreeFour" + i)));
			property.setMortgageValue(mortgageVal);

			if (additionalApplicantYesNo != null
					&& additionalApplicantYesNo.equalsIgnoreCase("yes")
					&& opportunity.getApplicants().get(1).getApplicant_name()
							.equalsIgnoreCase(ownership1)) {
				property.setApplicantId(opportunity.getApplicants().get(1)
						.getId());
				property.setOwnwerShip(ownership1);

			} else {
				property.setApplicantId(opportunity.getApplicants().get(0)
						.getId());
				property.setOwnwerShip(ownership1);


			}
			if (mortgageVal != null && mortgageVal > 0) {
				mortgage.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo4" + i)));
				mortgage.setApplicantId(property.getApplicantId());
				mortgage.setPropertyId("" + i);
				mortgage.setSelling(getSelling(dynamicForm.get("agreeFour" + i)));

			}
			opportunity.getApplicants().get(0).getListOfMortgages()
					.add(mortgage);
			opportunity.getApplicants().get(0).getProperties().add(property);
		}
		return opportunity;

	}
/**
 * map form property values to  property, mortgage and applicant Pojo
 * @param opportunity
 * @param dynamicForm
 * @param additionalApplicantYesNo
 * @return
 */
	private Opportunity getPropertyFiveList(Opportunity opportunity,
			DynamicForm dynamicForm, String additionalApplicantYesNo) {

		for (int i = 1; i <= 7; i++) {
			Property property = new Property();
			Mortgages mortgage = new Mortgages();
			String ownership1 = dynamicForm.get("ownership5" + i);
			Integer mortgageVal = convertStringToInteger(dynamicForm
					.get("mortgage5" + i));
			property.setName(dynamicForm.get("currentAddress5" + i));
			property.setValue(convertStringToDouble(dynamicForm
					.get("appxValue5" + i)));
			property.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo5" + i)));
			property.setMoCondoFees(convertStringToInteger(dynamicForm
					.get("condoFees5" + i)));
			property.setPropertyId(i + "");
			property.setSelling(getSelling(dynamicForm.get("agreeFive" + i)));
			property.setMortgageValue(mortgageVal);

			if (additionalApplicantYesNo != null
					&& additionalApplicantYesNo.equalsIgnoreCase("yes")
					&& opportunity.getApplicants().get(1).getApplicant_name()
							.equalsIgnoreCase(ownership1)) {
				property.setApplicantId(opportunity.getApplicants().get(1)
						.getId());
				property.setOwnwerShip(ownership1);

			} else {
				property.setApplicantId(opportunity.getApplicants().get(0)
						.getId());
				property.setOwnwerShip(ownership1);


			}
			if (mortgageVal != null && mortgageVal > 0) {
				mortgage.setMonthlyRent(convertStringToInteger(dynamicForm
						.get("rentMo5" + i)));
				mortgage.setApplicantId(property.getApplicantId());
				mortgage.setPropertyId("" + i);
				mortgage.setSelling(getSelling(dynamicForm.get("agreeFive" + i)));

			}
			opportunity.getApplicants().get(0).getListOfMortgages()
			.add(mortgage);
	opportunity.getApplicants().get(0).getProperties().add(property);
		}
		return opportunity;

	}

	public void deleteProperty(Opportunity opportunity) throws PostGressDaoServiceException {
      Logger.debug("Inside (.) deleteProperty of  MortgageApplicationPageElevenService class ");
      posService.deleteApplicantProperty(opportunity);
      opportunity.getApplicants().get(0).setProperties(new ArrayList<Property>());
      opportunity.getApplicants().get(0).setListOfMortgages(new ArrayList<Mortgages>());
	}

}
