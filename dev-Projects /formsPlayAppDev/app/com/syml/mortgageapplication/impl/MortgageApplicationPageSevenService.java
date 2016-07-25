package com.syml.mortgageapplication.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syml.SplitAddress;
import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Address;
import controllers.ApplicantAddressParameter7;
import controllers.CoApplicantAddressParameter7;
import controllers.Income;
import controllers.Opportunity;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.Session;

public class MortgageApplicationPageSevenService {

	String subForm = "Mortgage Application 7";

	private MortgageApplicationPageService mortAppService = new MortgageApplicationPageService();
	private CouchBaseService couchBaseService = new CouchBaseService();
	private CouchbaseServiceHelper couchbaseServiceHelper = new CouchbaseServiceHelper();
	private PostGresDaoService postGresDaoService = new PostGresDaoService();
	private Opportunity opportunity = null;
	private Address address = null;
	private SplitAddress addressSplit = new SplitAddress();
	private Map<String, String> storeFormData = null;

	private String formInputAddress = "";
	private String formInputMovedInDate = "";
	private String formInputSumMonth = "";
	private String formInputTotalMonth = "";

	public Opportunity updateOpportunityAddresses(DynamicForm dynamicForm, Session session)
			throws MortgageApplicationPageServiceException {
		Logger.info("Inside (.) updateOpportunityAddresses of MortgageApplicationPageSevenService");
		Map<String, String> readFormData = null;
		List<Address> listOfAddress = new ArrayList<Address>();
		Integer totalNoOfMonth = null;
		try {
			int crm_LeadId = mortAppService.parseLeadId(session);

			opportunity = couchbaseServiceHelper.getCouhbaseDataByKey(crm_LeadId);

			deleteApplciantAddress(opportunity);
			if (opportunity.getIsAdditionalApplicantExist() != null
					&& opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes")) {
				deleteCoApplciantAddress(opportunity);
			}

			readFormData = getFirstPriorAddressDataOfApplicant(dynamicForm);

			totalNoOfMonth = mortAppService.convertStringToInteger(readFormData.get("totalMonth"));

			if (readFormData.get("inputAddress") != null) {
				listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(0).getId()));
			}
			Logger.debug("totalNoOfMonth 1 " + totalNoOfMonth);

			if (opportunity.getApplicants().get(0).getNewToCannada().equalsIgnoreCase("no")) {
				if (totalNoOfMonth != null && totalNoOfMonth < 36) {
					readFormData = getSecPriorAddressDataOfApplicant(dynamicForm);
					totalNoOfMonth = mortAppService.convertStringToInteger(readFormData.get("totalMonth"));
					if (readFormData.get("inputAddress") != null) {
						listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(0).getId()));
					}
					Logger.debug("totalNoOfMonth 2c " + totalNoOfMonth);
					if (totalNoOfMonth != null && totalNoOfMonth < 36) {
						readFormData = getThirdPriorAddressDataOfApplicant(dynamicForm);
						if (readFormData.get("inputAddress") != null) {
							listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(0).getId()));
						}
						Logger.debug("totalNoOfMonth 3 " + totalNoOfMonth);
						if (totalNoOfMonth != null && totalNoOfMonth < 36) {
							readFormData = getFourthPriorAddressDataOfApplicant(dynamicForm);
							if (readFormData.get("inputAddress") != null) {
								listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(0).getId()));
							}
							Logger.debug("totalNoOfMonth  4" + totalNoOfMonth);
							if (totalNoOfMonth != null && totalNoOfMonth < 36) {
								readFormData = getFifthPriorAddressDataOfApplicant(dynamicForm);
								if (readFormData.get("inputAddress") != null) {
									listOfAddress
											.add(getAddress(readFormData, opportunity.getApplicants().get(0).getId()));
								}
								Logger.debug("totalNoOfMonth  5" + totalNoOfMonth);
								if (totalNoOfMonth != null && totalNoOfMonth < 36) {
									readFormData = getSixthPriorAddressDataOfApplicant(dynamicForm);
									if (readFormData.get("inputAddress") != null) {
										listOfAddress.add(
												getAddress(readFormData, opportunity.getApplicants().get(0).getId()));
									}
									Logger.debug("totalNoOfMonth  6" + totalNoOfMonth);
								}
							}
						}

					}
				}
			}
			opportunity.getApplicants().get(0).setListOfAddress(listOfAddress);
			postGresDaoService.updateApplicantPage7(opportunity.getApplicants().get(0));
			listOfAddress = new ArrayList<Address>();

			if (opportunity.getIsAdditionalApplicantExist() != null
					&& opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes")) {
				readFormData = getFirstPriorAddressDataOfCoApplicant(dynamicForm);
				totalNoOfMonth = mortAppService.convertStringToInteger(readFormData.get("totalMonth"));

				if (readFormData.get("inputAddress") != null) {
					listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(1).getId()));
				}
				Logger.debug("totalNoOfMonth " + totalNoOfMonth);
				String checkforCanada = opportunity.getApplicants().get(1).getNewToCannada();
				if (checkforCanada == "no" || checkforCanada.equals("no") || checkforCanada.trim().equals("no")) {
					if (totalNoOfMonth != null && totalNoOfMonth < 36) {
						readFormData = getSecPriorAddressDataOfCoApplicant(dynamicForm);
						totalNoOfMonth = mortAppService.convertStringToInteger(readFormData.get("totalMonth"));
						Logger.debug("totalNoOfMonth " + totalNoOfMonth);
						if (readFormData.get("inputAddress") != null) {
							listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(1).getId()));
						}
						Logger.debug("totalNoOfMonth " + totalNoOfMonth);
						if (totalNoOfMonth != null && totalNoOfMonth < 36) {
							readFormData = getThirdPriorAddressDataOfCoApplicant(dynamicForm);

							if (readFormData.get("inputAddress") != null) {
								listOfAddress.add(getAddress(readFormData, opportunity.getApplicants().get(1).getId()));
							}

							if (totalNoOfMonth != null && totalNoOfMonth < 36) {
								readFormData = getFourthPriorAddressDataOfCoApplicant(dynamicForm);

								if (readFormData.get("inputAddress") != null) {
									listOfAddress
											.add(getAddress(readFormData, opportunity.getApplicants().get(1).getId()));
								}
								if (totalNoOfMonth != null && totalNoOfMonth < 36) {
									readFormData = getFifthPriorAddressDataOfCoApplicant(dynamicForm);
									if (readFormData.get("inputAddress") != null) {
										listOfAddress.add(
												getAddress(readFormData, opportunity.getApplicants().get(1).getId()));
									}
									if (totalNoOfMonth != null && totalNoOfMonth < 36) {
										readFormData = getSixthPriorAddressDataOfCoApplicant(dynamicForm);
										if (readFormData.get("inputAddress") != null) {
											listOfAddress.add(getAddress(readFormData,
													opportunity.getApplicants().get(1).getId()));
										}
									}
								}

							}

						}
					}
				}
				opportunity.getApplicants().get(1).setListOfAddress(listOfAddress);
				postGresDaoService.updateApplicantPage7(opportunity.getApplicants().get(1));
			}
			opportunity.setPogressStatus(65);

			couchBaseService.storeDataToCouchbase(
					MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY + crm_LeadId, opportunity);
			Logger.debug("Comming here");
		} catch (MortgageApplicationPageServiceException mortAppException) {
			throw new MortgageApplicationPageServiceException(
					"Error when reading crm_LeadId Data from Couchbase / Invalid crm_LeadId ", mortAppException);
		} catch (PostGressDaoServiceException pgException) {
			throw new MortgageApplicationPageServiceException(
					" Error when Updating Applicant Addresses into the PostgressDB ", pgException);
		} catch (CouchbaseServiceException cbException) {
			throw new MortgageApplicationPageServiceException(
					"Error when Updating Applicant Addresses into the CouchbaseDB  ", cbException);
		}
		return opportunity;
	}

	private Address getAddress(Map<String, String> readFormData, int applicantId) {
		Map<String, String> currentAddressSplit = addressSplit.getProperAddress(readFormData.get("inputAddress"));

		if (currentAddressSplit != null) {
			address = new Address();
			address.setName(currentAddressSplit.get("address1"));
			address.setCity(currentAddressSplit.get("city"));
			address.setProvience(currentAddressSplit.get("Province"));
			address.setPostalCode(currentAddressSplit.get("postalcode"));
			address.setMovedIn(convertString2Date(readFormData.get("movedInDate")));
			address.setApplicant_id(applicantId);
			address.setFullAddress(readFormData.get("inputAddress"));
			Logger.debug("readFormData =============================================================================== "
					+ readFormData);
			address.setMonths(mortAppService.convertStringToInteger(readFormData.get("sumMonth")));
			address.setTotalMonths(mortAppService.convertStringToInteger(readFormData.get("totalMonth")));
		}
		return address;
	}

	public Date convertString2Date(String inputDateString) {
		DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
		Date movedIn1 = null;
		try {
			movedIn1 = df2.parse(inputDateString);
		} catch (ParseException e) {
			Logger.error("Error in parsing string to date");
		}
		return movedIn1;
	}

	public String convertDateToString(Date dt) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		return sdf.format(dt);
	}

	/**
	 * Reading Applicant's First Prior Address Details
	 * 
	 * @param dynamicForm
	 * @return
	 */
	private Map<String, String> getFirstPriorAddressDataOfApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getFirstPriorAddressDataOfApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();
		formInputAddress = dynamicForm.get("currentAddress1");
		formInputMovedInDate = dynamicForm.get("movedIn1");
		formInputSumMonth = dynamicForm.get("currentaddressmonthsum");
		formInputTotalMonth = dynamicForm.get("totalcurrentmonths");
		Logger.debug("================================================================================");
		Logger.debug("currentAddress" + formInputAddress + "\n input MovedIn1 " + formInputMovedInDate
				+ "\n currentSumMonth" + formInputSumMonth + "\n totalcurrentMonths" + formInputTotalMonth);
		Logger.debug(
				"currentSumMonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<");
		Logger.debug("totalcurrentMonths>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth
				+ "<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	/**
	 * Reading Applicant's 2nd Prior Address Details
	 * 
	 * @param dynamicForm
	 * @return
	 */
	private Map<String, String> getSecPriorAddressDataOfApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getSecPriorAddressDataOfApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("currentAddress2");
		formInputMovedInDate = dynamicForm.get("movedIn2");
		formInputSumMonth = dynamicForm.get("priormonthsum1");
		formInputTotalMonth = dynamicForm.get("totalpriormonths1");

		Logger.debug("================================================================================");
		Logger.debug("currentAddress" + formInputAddress + "\n input MovedIn1 " + formInputMovedInDate
				+ "\n After string-to-date movedIn1 " + formInputSumMonth + "\n currentSumMonth" + formInputTotalMonth);
		Logger.debug("priorSumMonth1>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"totalpriorcurrentmonths1>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);

		return storeFormData;
	}

	/**
	 * Reading Applicant's 3rd Prior Address Details
	 * 
	 * @param dynamicForm
	 * @return
	 */
	private Map<String, String> getThirdPriorAddressDataOfApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("currentAddress3");
		formInputMovedInDate = dynamicForm.get("movedIn3");
		formInputSumMonth = dynamicForm.get("priormonthsum2");
		formInputTotalMonth = dynamicForm.get("totalpriormonths2");
		Logger.debug("================================================================================");
		Logger.debug("priorAddress2" + formInputAddress + "\n inputMovedIn3 " + formInputMovedInDate
				+ "\n priorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug("priormonthsum2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"totalpriormonths2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");

		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	private Map<String, String> getFourthPriorAddressDataOfApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("currentAddress4");
		formInputMovedInDate = dynamicForm.get("movedIn4");
		formInputSumMonth = dynamicForm.get("priormonthsum3");
		formInputTotalMonth = dynamicForm.get("totalpriormonths3");
		Logger.debug("================================================================================");
		Logger.debug("priorAddress2" + formInputAddress + "\n inputMovedIn3 " + formInputMovedInDate
				+ "\n priorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug("priormonthsum2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"totalpriormonths2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");

		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	private Map<String, String> getFifthPriorAddressDataOfApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("currentAddress5");
		formInputMovedInDate = dynamicForm.get("movedIn5");
		formInputSumMonth = dynamicForm.get("priormonthsum4");
		formInputTotalMonth = dynamicForm.get("totalpriormonths4");
		Logger.debug("================================================================================");
		Logger.debug("priorAddress2" + formInputAddress + "\n inputMovedIn3 " + formInputMovedInDate
				+ "\n priorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug("priormonthsum2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"totalpriormonths2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");

		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	private Map<String, String> getSixthPriorAddressDataOfApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("currentAddress6");
		formInputMovedInDate = dynamicForm.get("movedIn6");
		formInputSumMonth = dynamicForm.get("priormonthsum5");
		formInputTotalMonth = dynamicForm.get("totalpriormonths5");
		Logger.debug("================================================================================");
		Logger.debug("priorAddress2" + formInputAddress + "\n inputMovedIn3 " + formInputMovedInDate
				+ "\n priorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug("priormonthsum2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"totalpriormonths2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");

		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	/**
	 * Reading CoCoApplicant's 1st Prior Address Details
	 * 
	 * @param dynamicForm
	 * @return
	 */
	private Map<String, String> getFirstPriorAddressDataOfCoApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getFirstPriorAddressDataOfCoApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("CoCurrentAddress1");
		formInputMovedInDate = dynamicForm.get("CoMovedIn1");
		formInputSumMonth = dynamicForm.get("coAppcurrentaddressmonthsum");
		formInputTotalMonth = dynamicForm.get("coAppTotalcurrentMonths");
		Logger.debug("================================================================================");
		Logger.debug("coAppcurrentAddress" + formInputAddress + "\n coAppInputMovedIn1 " + formInputMovedInDate
				+ "\n coAppCurrentSumMonth" + formInputSumMonth + "\n coAppTotalcurrentMonths " + formInputTotalMonth);
		Logger.debug(
				"co first summonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"co first totalmonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	/**
	 * Reading CoCoApplicant's 2nd Prior Address Details
	 * 
	 * @param dynamicForm
	 * @return
	 */
	private Map<String, String> getSecPriorAddressDataOfCoApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getSecPriorAddressDataOfCoApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("CoCurrentAddress2");
		formInputMovedInDate = dynamicForm.get("CoMovedIn2");
		formInputSumMonth = dynamicForm.get("coAppPriorSumMonth1");
		formInputTotalMonth = dynamicForm.get("coAppTotalpriorcurrentmonths1");

		Logger.debug("================================================================================");
		Logger.debug("coAppPriorAddress1" + formInputAddress + "\n coAppInputMovedIn2 " + formInputMovedInDate
				+ "\n coAppPriorSumMonth1" + formInputSumMonth + "\n coAppTotalpriorcurrentmonths1 "
				+ formInputTotalMonth);
		Logger.debug(
				"co 2nd summonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"co 2nd totalmonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	/**
	 * Reading CoCoApplicant's 3rd Prior Address Details
	 * 
	 * @param dynamicForm
	 * @return
	 */
	private Map<String, String> getThirdPriorAddressDataOfCoApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfCoApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("CoCurrentAddress3");
		formInputMovedInDate = dynamicForm.get("CoMovedIn3");
		formInputSumMonth = dynamicForm.get("coApppriorSumMonth2");
		formInputTotalMonth = dynamicForm.get("coApptotalpriorcurrentmonths2");

		Logger.debug("================================================================================");
		Logger.debug("coApppriorAddress2" + formInputAddress + "\n coAppInputMovedIn3 " + formInputMovedInDate
				+ "\n coApppriorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug(
				"co 3rd summonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"co 3rd totalmonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	private Map<String, String> getFourthPriorAddressDataOfCoApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfCoApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("CoCurrentAddress4");
		formInputMovedInDate = dynamicForm.get("CoMovedIn4");
		formInputSumMonth = dynamicForm.get("coApppriorSumMonth3");
		formInputTotalMonth = dynamicForm.get("coApptotalpriorcurrentmonths3");

		Logger.debug("================================================================================");
		Logger.debug("coApppriorAddress2" + formInputAddress + "\n coAppInputMovedIn3 " + formInputMovedInDate
				+ "\n coApppriorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug(
				"co 3rd summonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"co 3rd totalmonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	private Map<String, String> getFifthPriorAddressDataOfCoApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfCoApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("CoCurrentAddress5");
		formInputMovedInDate = dynamicForm.get("CoMovedIn5");
		formInputSumMonth = dynamicForm.get("coApppriorSumMonth4");
		formInputTotalMonth = dynamicForm.get("coApptotalpriorcurrentmonths4");

		Logger.debug("================================================================================");
		Logger.debug("coApppriorAddress2" + formInputAddress + "\n coAppInputMovedIn3 " + formInputMovedInDate
				+ "\n coApppriorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug(
				"co 3rd summonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"co 3rd totalmonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	private Map<String, String> getSixthPriorAddressDataOfCoApplicant(DynamicForm dynamicForm) {
		Logger.info("inside (.) getThirdPriorAddressDataOfCoApplicant of MortgageApplicationPageSevenService ");
		storeFormData = new HashMap<String, String>();

		formInputAddress = dynamicForm.get("CoCurrentAddress6");
		formInputMovedInDate = dynamicForm.get("CoMovedIn6");
		formInputSumMonth = dynamicForm.get("coApppriorSumMonth5");
		formInputTotalMonth = dynamicForm.get("coApptotalpriorcurrentmonths5");

		Logger.debug("================================================================================");
		Logger.debug("coApppriorAddress2" + formInputAddress + "\n coAppInputMovedIn3 " + formInputMovedInDate
				+ "\n coApppriorSumMonth2" + formInputSumMonth + "\n formInputTotalMonth " + formInputTotalMonth);
		Logger.debug(
				"co 3rd summonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputSumMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug(
				"co 3rd totalmonth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + formInputTotalMonth + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Logger.debug("================================================================================");
		storeFormData.put("inputAddress", formInputAddress);
		storeFormData.put("movedInDate", formInputMovedInDate);
		storeFormData.put("sumMonth", formInputSumMonth);
		storeFormData.put("totalMonth", formInputTotalMonth);
		return storeFormData;
	}

	/* The Below written code for backbutton functionality */
	// This is not a proper or standard coding, need to devlope code, because of
	// Deployement we ignore
	public ApplicantAddressParameter7 getApplicantAddresses(Opportunity opportunity) {
		List<Address> listOfAddressOfApplicant = opportunity.getApplicants().get(0).getListOfAddress();
		ApplicantAddressParameter7 appAddParam = new ApplicantAddressParameter7();
		appAddParam.setApplicantName(opportunity.getApplicants().get(0).getApplicant_name());
		appAddParam.setApplicantCurrentAddress(listOfAddressOfApplicant.get(0).getFullAddress());
		appAddParam.setApplicantMovedIn1(convertDateToString(listOfAddressOfApplicant.get(0).getMovedIn()));
		appAddParam.setApplicantCurrentSumMonth(listOfAddressOfApplicant.get(0).getMonths() + "");
		appAddParam.setApplicantTotalcurrentMonths(listOfAddressOfApplicant.get(0).getTotalMonths() + "");

		if (opportunity.getApplicants().get(0).getNewToCannada().equalsIgnoreCase("no")) {
			if (listOfAddressOfApplicant.get(0).getTotalMonths() < 36) {

				appAddParam.setApplicantPriorAddress1(listOfAddressOfApplicant.get(1).getFullAddress());
				appAddParam.setApplicantMovedIn2(convertDateToString(listOfAddressOfApplicant.get(1).getMovedIn()));
				appAddParam.setApplicantPriorSumMonth1(listOfAddressOfApplicant.get(1).getMonths() + "");
				appAddParam.setApplicantTotalpriorcurrentmonths1(listOfAddressOfApplicant.get(1).getTotalMonths() + "");

				if (listOfAddressOfApplicant.get(1).getTotalMonths() < 36) {
					appAddParam.setApplicantPriorAddress2(listOfAddressOfApplicant.get(2).getFullAddress());
					appAddParam.setApplicantMovedIn3(convertDateToString(listOfAddressOfApplicant.get(2).getMovedIn()));
					appAddParam.setApplicantPriorSumMonth2(listOfAddressOfApplicant.get(2).getMonths() + "");
					appAddParam.setApplicantTotalpriorcurrentmonths2(
							listOfAddressOfApplicant.get(2).getTotalMonths() + "");

					if (listOfAddressOfApplicant.get(2).getTotalMonths() < 36) {
						appAddParam.setApplicantPriorAddress3(listOfAddressOfApplicant.get(3).getFullAddress());
						appAddParam.setApplicantMovedIn4(
								convertDateToString(listOfAddressOfApplicant.get(3).getMovedIn()));
						appAddParam.setApplicantPriorSumMonth3(listOfAddressOfApplicant.get(3).getMonths() + "");
						appAddParam.setApplicantTotalpriorcurrentmonths3(
								listOfAddressOfApplicant.get(3).getTotalMonths() + "");
						if (listOfAddressOfApplicant.get(3).getTotalMonths() < 36) {
							appAddParam.setApplicantPriorAddress4(listOfAddressOfApplicant.get(4).getFullAddress());
							appAddParam.setApplicantMovedIn5(
									convertDateToString(listOfAddressOfApplicant.get(4).getMovedIn()));
							appAddParam.setApplicantPriorSumMonth4(listOfAddressOfApplicant.get(4).getMonths() + "");
							appAddParam.setApplicantTotalpriorcurrentmonths4(
									listOfAddressOfApplicant.get(4).getTotalMonths() + "");

							if (listOfAddressOfApplicant.get(4).getTotalMonths() < 36) {
								appAddParam.setApplicantPriorAddress5(listOfAddressOfApplicant.get(5).getFullAddress());
								appAddParam.setApplicantMovedIn6(
										convertDateToString(listOfAddressOfApplicant.get(5).getMovedIn()));
								appAddParam
										.setApplicantPriorSumMonth5(listOfAddressOfApplicant.get(5).getMonths() + "");
								appAddParam.setApplicantTotalpriorcurrentmonths5(
										listOfAddressOfApplicant.get(5).getTotalMonths() + "");
							}
						}
					}
				}
			}
		}

		return appAddParam;
	}

	public CoApplicantAddressParameter7 getCoApplicantAddressParameter7(Opportunity opportunity) {
		List<Address> listOfAddressOfApplicant = opportunity.getApplicants().get(1).getListOfAddress();

		CoApplicantAddressParameter7 coAppAddParam = new CoApplicantAddressParameter7();
		coAppAddParam.setCoApplicantName(opportunity.getApplicants().get(1).getApplicant_name());
		coAppAddParam.setCoAppcurrentAddress(listOfAddressOfApplicant.get(0).getFullAddress());
		/** need to change here */
		coAppAddParam.setCoAppmovedIn1(convertDateToString(listOfAddressOfApplicant.get(0).getMovedIn()));
		coAppAddParam.setCoAppCurrentSumMonth(listOfAddressOfApplicant.get(0).getMonths() + "");
		coAppAddParam.setCoAppTotalcurrentMonths(listOfAddressOfApplicant.get(0).getTotalMonths() + "");
		Logger.debug("Canada value " + opportunity.getApplicants().get(1).getNewToCannada());
		if (opportunity.getApplicants().get(1).getNewToCannada().equalsIgnoreCase("no")) {
			if (listOfAddressOfApplicant.get(0).getTotalMonths() < 36) {

				coAppAddParam.setCoAppPriorAddress1(listOfAddressOfApplicant.get(1).getFullAddress());
				coAppAddParam.setCoMovedIn2(convertDateToString(listOfAddressOfApplicant.get(1).getMovedIn()));
				coAppAddParam.setCoPriorSumMonth1(listOfAddressOfApplicant.get(1).getMonths() + "");
				coAppAddParam.setCoTotalpriorcurrentmonths1(listOfAddressOfApplicant.get(1).getTotalMonths() + "");

				if (listOfAddressOfApplicant.get(1).getTotalMonths() < 36) {
					coAppAddParam.setCoApppriorAddress2(listOfAddressOfApplicant.get(2).getFullAddress());
					coAppAddParam.setCoMovedIn3(convertDateToString(listOfAddressOfApplicant.get(2).getMovedIn()));
					coAppAddParam.setCoApppriorSumMonth2(listOfAddressOfApplicant.get(2).getMonths() + "");
					coAppAddParam
							.setCoApptotalpriorcurrentmonths2(listOfAddressOfApplicant.get(2).getTotalMonths() + "");
					if (listOfAddressOfApplicant.get(2).getTotalMonths() < 36) {
						coAppAddParam.setCoAppPriorAddress3(listOfAddressOfApplicant.get(3).getFullAddress());
						coAppAddParam.setCoMovedIn4(convertDateToString(listOfAddressOfApplicant.get(3).getMovedIn()));
						coAppAddParam.setCoAppPriorSumMonth3(listOfAddressOfApplicant.get(3).getMonths() + "");
						coAppAddParam.setCoAppTotalpriorcurrentmonths3(
								listOfAddressOfApplicant.get(3).getTotalMonths() + "");

						if (listOfAddressOfApplicant.get(3).getTotalMonths() < 36) {
							coAppAddParam.setCoAppPriorAddress4(listOfAddressOfApplicant.get(4).getFullAddress());
							coAppAddParam
									.setCoMovedIn5(convertDateToString(listOfAddressOfApplicant.get(4).getMovedIn()));
							coAppAddParam.setCoAppPriorSumMonth4(listOfAddressOfApplicant.get(4).getMonths() + "");
							coAppAddParam.setCoAppTotalpriorcurrentmonths4(
									listOfAddressOfApplicant.get(4).getTotalMonths() + "");
							if (listOfAddressOfApplicant.get(4).getTotalMonths() < 36) {
								coAppAddParam.setCoAppriorAddress5(listOfAddressOfApplicant.get(5).getFullAddress());
								coAppAddParam.setCoMovedIn6(
										convertDateToString(listOfAddressOfApplicant.get(5).getMovedIn()));
								coAppAddParam.setCoAppPriorSumMonth5(listOfAddressOfApplicant.get(5).getMonths() + "");
								coAppAddParam.setCoAppTotalpriorcurrentmonths5(
										listOfAddressOfApplicant.get(5).getTotalMonths() + "");

							}
						}
					}

				}
			}
		}
		Logger.debug("CoApplicantAddressParameter7 " + coAppAddParam.toString());
		return coAppAddParam;
	}

	public void deleteApplciantAddress(Opportunity opportunity) throws MortgageApplicationPageServiceException {
		Logger.debug("Inside (.) deleteIncome of  MortgageApplicationPageEightService class ");
		try {
			postGresDaoService.deleteApplicantAddress(opportunity.getApplicants().get(0));
			opportunity.getApplicants().get(0).setListOfAddress(new ArrayList<Address>());

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In deleting income details From Db ", e);

		}
	}

	public void deleteCoApplciantAddress(Opportunity opportunity) throws MortgageApplicationPageServiceException {
		Logger.debug("Inside (.) deleteIncome of  MortgageApplicationPageEightService class ");
		try {
			postGresDaoService.deleteApplicantAddress(opportunity.getApplicants().get(1));
			opportunity.getApplicants().get(1).setListOfAddress(new ArrayList<Address>());

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In deleting income details From Db ", e);

		}
	}
}