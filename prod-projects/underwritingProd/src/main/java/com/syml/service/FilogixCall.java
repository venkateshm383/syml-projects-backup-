package com.syml.service;

import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.UnderwritePostSel;
import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.Income;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Property;
import com.syml.filogix.Address1Type;
import com.syml.filogix.ApplicantAddressType;
import com.syml.filogix.ApplicantApplicationType;
import com.syml.filogix.ApplicantApplicationType.OtherIncome;
import com.syml.filogix.ApplicantApplicationType.OtherProperty;
import com.syml.filogix.AssetType;
import com.syml.filogix.BasePersonType.Name;
import com.syml.filogix.ContactTypeAddresstype2;
import com.syml.filogix.DealType;
import com.syml.filogix.DealType.DownPaymentSource;
import com.syml.filogix.EmploymentHistoryType.SelfEmploymentDetails;
import com.syml.filogix.Address2Type;
import com.syml.filogix.EmploymentHistoryType;
import com.syml.filogix.MortgageType;
import com.syml.filogix.PhoneType;
import com.syml.filogix.PropertyType;
import com.syml.filogix.PropertyType.PropertyExpense;
import com.syml.filogix.ReferralApplicationType;
import com.syml.filogix.PropertyType.RentalIncome;
import com.syml.filogix.ReferralApplicationType.ApplicantGroup;
import com.syml.filogix.ReferralApplicationType.ReferredToAgent;
import com.syml.filogix.ReferralApplicationType.SubjectProperty;
import com.syml.util.SelectionHelper;
import com.syml.util.UnderwriteAppConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.syml.util.StringUtil.*;
import static com.syml.service.FilogixCallVO.*;


public class FilogixCall {

    private static final Logger logger = LoggerFactory.getLogger(FilogixCall.class);
    private static final UnderwriteAppConfig config = UnderwriteAppConfig.getInstance();

    public UnderwritePostSel underwrite;
    private ReferralApplicationType referralApplication;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FilogixCall(UnderwritePostSel underwrite) {
        super();
        this.underwrite = underwrite;
        
        // Create an instance of the ReferralApplication.
           try {
               ReferralApplicationType filogixApp = new ReferralApplicationType();
               
               // Propogation Order ... 
//               "referredToAgent",
//                "referralUrl",
//                "deal",
//                "applicantGroup",
//                "mortgage",
//                "subjectProperty",
//                "notes",
//                "additionalData"
               // TODO Create all objects and set all values.
               
               // Create Referred to Agent and Set Values
               ReferredToAgent agent = new ReferredToAgent();
               agent.setFirmCode(config.getFilogixFirmCode());
               agent.setUserLogin(config.getFilogixUserLogin());
               // Set Referred To Agent in  ReferralApplication
               filogixApp.setReferredToAgent(agent);
               
               // Create and set Referral URL
               String referralURL = "https://crm.visdom.ca";
               filogixApp.setReferralUrl(referralURL);
               
               // Create and set deal
               DealType deal = new DealType();                   
               // TODO Propogation Order: 
//               "applicationDate",
//                "dealPurposeDd",
//                "dealTypeDd",
//                "downPaymentSource",
//                "estimatedClosingDate",
//                "financingWaiverDate",
//                "refiImprovementAmount",
//                "refiImprovementsDesc",
//                "refiImprovementsFlag",
//                "refiPurpose",
//                "sourceApplicationId",
//                "taxPayorDd",
//                "additionalData"

               // Application Date
               XMLGregorianCalendar appDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
               deal.setApplicationDate(appDate);
               
               //dealPurposeDd
//               enumeration     0    documentation
//               Purchase
//               enumeration     1    documentation
//               Purchase + Improvements
//               enumeration     2    documentation
//               Refinance
//               enumeration     3    documentation
//               ETO
//               enumeration     4    documentation
//               Switch / Transfer
//               enumeration     5    documentation
//               Port
//               enumeration     6    documentation
//               Deficiency Sale
//               enumeration     7    documentation
//               Workout
               double  dealPurpose = 0;
               if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, underwrite.clientOpportunity.whatIsYourLendingGoal)){
                   dealPurpose = 7;
               }
               else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, underwrite.clientOpportunity.whatIsYourLendingGoal)){
                   // TODO confirm filogix selection values with Audrey
                   if (underwrite.clientOpportunity.lookingToApproved != null){        
                       if (underwrite.clientOpportunity.lookingToApproved.contains("1")){
                           dealPurpose = 3;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("2")){
                           dealPurpose = 4;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("3")){
                           dealPurpose = 5;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("4")){
                           dealPurpose = 3;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("5")){
                           dealPurpose = 0;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("6")){
                           dealPurpose = 1;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("7")){
                           dealPurpose = 1;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("8")){
                           dealPurpose = 6;
                       }
                       else if (underwrite.clientOpportunity.lookingToApproved.contains("9")){
                           dealPurpose = 5;
                       }
                   }
                   else{
                       dealPurpose = 0;
                   }
                   
               } 
               else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, underwrite.clientOpportunity.whatIsYourLendingGoal)){
                   if (underwrite.clientOpportunity.lookingToRefinance != null){        
                       
                       if (underwrite.clientOpportunity.lookingToRefinance.contains("1")){
                           dealPurpose = 3;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("2")){
                           dealPurpose = 4;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("3")){
                           dealPurpose = 5;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("4")){
                           dealPurpose = 3;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("5")){
                           dealPurpose = 0;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("6")){
                           dealPurpose = 1;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("7")){
                           dealPurpose = 1;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("8")){
                           dealPurpose = 6;
                       }
                       else if (underwrite.clientOpportunity.lookingToRefinance.contains("9")){
                           dealPurpose = 5;
                       }
                   }
                   else{
                       dealPurpose = 3;
                   }
                   
               } 
               else{
                   dealPurpose = 0;
               }
               BigDecimal dealPurposeDD = BigDecimal.valueOf(dealPurpose);
               dealPurposeDD = dealPurposeDD.setScale(0, RoundingMode.DOWN);
               deal.setDealPurposeDd(dealPurposeDD);
               
               // Set "dealTypeDd"
//               enumeration     0    documentation
//               Approval//
//               enumeration     1    documentation
//               Pre-Approval
               double dealType = 0;               
               if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, underwrite.clientOpportunity.whatIsYourLendingGoal)){
                   dealType = 1;
               }
               else{
                   dealType = 0;
               }
               BigDecimal dealTypeDD = BigDecimal.valueOf(dealType);
               dealTypeDD = dealTypeDD.setScale(0, RoundingMode.DOWN);
               deal.setDealTypeDd(dealTypeDD);
               
               // "downPaymentSource"
               if (underwrite.clientOpportunity.downpaymentAmount != null){
                   DownPaymentSource downPayment = new DownPaymentSource();
                   downPayment.setAmount(BigDecimal.valueOf(underwrite.clientOpportunity.downpaymentAmount));
                   
                   // downPaymentSourceTypeDd
//                   enumeration     0    //               Sale of Existing Property//
//                   enumeration     1    //               Personal Cash//
//                   enumeration     2    //               RRSP//
//                   enumeration     3    //               Borrowed Against Liquid Assets//
//                   enumeration     4    //               Gift//
//                   enumeration     6    //               Sweat Equity//
//                   enumeration     7    //               Other//
//                   enumeration     8    //               Existing Equity//
//                   enumeration     10    //               Secondary Financing//
//                   enumeration     11    //               Grants
                   /*double sourceOfDownpayment = 0;
                   String downpaymentDesc = "Sale of Existing Property";
                   
                   if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.BankAccount, underwrite.clientOpportunity.downPaymentComingFrom)){
                       sourceOfDownpayment = 1;
                       downpaymentDesc = "Personal Cash";
                   }
                   else if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Borrowed, underwrite.clientOpportunity.downPaymentComingFrom)){
                       sourceOfDownpayment = 10;
                       downpaymentDesc = "Secondary Financing";
                   }
                   else if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Gift, underwrite.clientOpportunity.downPaymentComingFrom)){
                       sourceOfDownpayment = 4;
                       downpaymentDesc = "Gift";
                   }
                   else if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Investments, underwrite.clientOpportunity.downPaymentComingFrom)){
                       sourceOfDownpayment = 2;
                       downpaymentDesc = "RRSP";
                   }
                   else if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Other, underwrite.clientOpportunity.downPaymentComingFrom)){
                       sourceOfDownpayment = 7;
                       downpaymentDesc = "Other";
                   }
                   else if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.SaleOfAsset, underwrite.clientOpportunity.downPaymentComingFrom)){
                       sourceOfDownpayment = 0;
                       downpaymentDesc = "Sale of Existing Property";
                   }
                   BigDecimal downPaymentSourceTypeDd = BigDecimal.valueOf(sourceOfDownpayment);
                   downPaymentSourceTypeDd = downPaymentSourceTypeDd.setScale(0, RoundingMode.DOWN);
                   downPayment.setDownPaymentSourceTypeDd(downPaymentSourceTypeDd);
                   downPayment.setDescription(downpaymentDesc);
                   deal.getDownPaymentSource().add(downPayment);
                   */
                   deal.getDownPaymentSource().addAll( createDownpaymentSourceForDeal(underwrite.clientOpportunity) );
               }
               
               
               //estimatedClosingDate
               if (underwrite.clientOpportunity.closingDate != null) {
                   final GregorianCalendar estCloseDate = new GregorianCalendar();
                   estCloseDate.setTime(underwrite.clientOpportunity.closingDate);
                   XMLGregorianCalendar estimatedClosingDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(estCloseDate);
                   deal.setEstimatedClosingDate(estimatedClosingDate);                      
               }
               
               // financingWaiverDate
               if (underwrite.clientOpportunity.conditionOfFinancingDate != null) {
                   logger.info(">>> Financing waiver: {}", underwrite.clientOpportunity.conditionOfFinancingDate);
                   final GregorianCalendar financingWaiverDate = new GregorianCalendar();
                   financingWaiverDate.setTime(underwrite.clientOpportunity.conditionOfFinancingDate);
                   XMLGregorianCalendar financeWaiveDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(financingWaiverDate);
                   deal.setFinancingWaiverDate(financeWaiveDate);                      
               }
               
               // refiImprovementAmount
               if (underwrite.clientOpportunity.renovationValue != null) {
                   deal.setRefiImprovementAmount(BigDecimal.valueOf(underwrite.clientOpportunity.renovationValue));
                   
                   if (underwrite.clientOpportunity.renovationValue > 0) {
                       deal.setRefiImprovementsFlag("Y");
                   }
               }
               
               // refiImprovementsDesc
               
               // sourceApplicationId
               deal.setSourceApplicationId(underwrite.clientOpportunity.applicationNo);
               deal.setDealPurposeDd(createFilogixDealPurposeDD(underwrite.clientOpportunity));
               
               // taxPayorDd
               
               // Set the Deal into the Referral Application ... 
               filogixApp.setDeal(deal);

               // Create Applicants Loop ... 
               // Inside loop, create a filogix applicant, map fields. 
               // Once created, add applicant into the  filogixApp.getApplicantGroup().add();
               ApplicantGroup applicantGroup = new ApplicantGroup();
               applicantGroup.setApplicantGroupTypeDd(BigDecimal.ZERO);

               for (Applicant applicant : underwrite.clientOpportunity.applicants) {
                   ApplicantApplicationType applicantApplication = new ApplicantApplicationType();
                   Name name = new Name();
                   name.setFirstName(applicant.getApplicantName());
                   name.setLastName(applicant.getApplicantLastName());
                   applicantApplication.setName(name);

                   applicantApplication.setEmailAddress(applicant.getEmailPersonal()); // UI: email
                   applicantApplication.setSocialInsuranceNumber(applicant.getSin().replace("-", "")); // UI: Social Insurance Number
                   BigDecimal maritalStatusDD = underwriteToFilogixMaritalStatus(applicant.relationshipStatus);
                   maritalStatusDD.setScale(0, RoundingMode.DOWN);
                   applicantApplication.setMaritalStatusDd(maritalStatusDD); // UI: Marital Status

                   final String dob = dateFormat.format(applicant.dob);
                   XMLGregorianCalendar applicantDOBXML = DatatypeFactory.newInstance().newXMLGregorianCalendar(dob);
                   applicantApplication.setBirthDate(applicantDOBXML); // UI: Date Of Birth

                   // UI: Phone
                   if (!isNullOrEmpty(applicant.home)) {
                       PhoneType phoneType = createFilogixPhoneType(applicant.home, 1);
                       applicantApplication.getPhone().add(phoneType);
                   }
                   if (!isNullOrEmpty(applicant.cell)) {
                       PhoneType phoneType = createFilogixPhoneType(applicant.cell, 2);
                       applicantApplication.getPhone().add(phoneType);
                   }
                   if (!isNullOrEmpty(applicant.work)) {
                       PhoneType phoneType = createFilogixPhoneType(applicant.work, 3);
                       applicantApplication.getPhone().add(phoneType);
                   }
                   
                   // applicantApplication.setPreferredContactMethodDd(new BigDecimal(applicant.getBestNumber())); // UI: Contact Method -> This is produce number format exception. Try another way.

                   // If not empty, add preferred contact method. If empty, nothing add.
                   if (!applicantApplication.getPhone().isEmpty()) {
                       applicantApplication.setPreferredContactMethodDd(createFilogixPreferredContactMethod(applicant.getBestNumber(), applicantApplication.getPhone()));
                   }
                   
                   List<ApplicantAddressType> addressDetails = createApplicantAddressDetail(applicant); // FIXME applicant address detail, once 
                   applicantApplication.getAddressDetail().addAll(addressDetails);

                   applicantGroup.getApplicant().add(applicantApplication);
                   /*
                   if (underwrite.clientOpportunity.applicants.indexOf(applicant) == 0) {
                       BigDecimal groupType = new BigDecimal("0");
                       groupType = groupType.setScale(0, RoundingMode.DOWN);
                       applicantGroup.setApplicantGroupTypeDd(groupType);
                   } else {
                       BigDecimal groupType = new BigDecimal("1");
                       groupType = groupType.setScale(0, RoundingMode.DOWN);
                       applicantGroup.setApplicantGroupTypeDd(groupType);
                   }*/

//                   "employmentHistory",
//                    "otherIncome",
//                    "asset",
//                    "liability",
//                    "otherProperty",
//                    "applicantadditionalData"
                   logger.info(">>> Income size for applicant {} is {}", applicant.getApplicantName(), applicant.incomes.size());
                   for (Income income : applicant.incomes) {
//                       "contact",
//                        "employerName",
//                        "employmentHistoryStatusDd",
//                        "employmentHistoryTypeDd",
//                        "income",
//                        "industrySectorDd",
//                        "jobTitle",
//                        "monthsOfService",
//                        "occupationDd",
//                        "selfEmploymentDetails",
//                        "timeInIndustry",
//                        "additionalData"
                       double incomeAmount = 0;
                       String incomeDescription = "";
                       
                       logger.info(">>> OpenERP typeOfIncome: {}", income.typeOfIncome);

                       if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Employed, income.typeOfIncome) || 
                           SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.SelfEmployed, income.typeOfIncome)) {

                           if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Employed, income.typeOfIncome)){
                                if (income.annualIncome != null){
                                    incomeAmount = Double.parseDouble(income.annualIncome);
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Employee"; 
                                }    
                            }
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.SelfEmployed, income.typeOfIncome)){                            
                                if (income.annualIncome != null){
                                    double grossedUpIncome = (Double.parseDouble(income.annualIncome) * (1 + (underwrite.potentialProduct.seGrossupPercent / 100)));
                                    double roundedGrossedUp = Math.round(grossedUpIncome);
                                    incomeAmount = roundedGrossedUp;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Self Employed";                              
                                }
                            }
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Bonus, income.typeOfIncome)){
                                if (income.annualIncome != null){
                                    double bonusIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = bonusIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Bonus";
                                    
                                }
                            } 
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Commission, income.typeOfIncome)){
                                if (income.annualIncome != null){
                                    double commissionIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = commissionIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Commission";
                                }
                            } 
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Overtime, income.typeOfIncome)){
                                if (income.annualIncome != null){
                                    double overtimeIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = overtimeIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Overtime";
                                }
                            }  
                           

                           EmploymentHistoryType employmentHistory = new EmploymentHistoryType();
                           employmentHistory.setEmployerName(income.business); // UI: Employer Name
                           
                           // EmploymentHistoryStatusDd
                           //enumeration     0                           Current
                           // enumeration     1                           Previous

                           // UI: Status
                           if (income.historical == true){
                               BigDecimal employmentHistoryStatusTypeDD = new BigDecimal("1");
                               employmentHistoryStatusTypeDD = employmentHistoryStatusTypeDD.setScale(0, RoundingMode.DOWN);
                               employmentHistory.setEmploymentHistoryStatusDd(employmentHistoryStatusTypeDD);   
                           }
                           else{
                               BigDecimal employmentHistoryStatusTypeDD = new BigDecimal("0");
                               employmentHistoryStatusTypeDD = employmentHistoryStatusTypeDD.setScale(0, RoundingMode.DOWN);
                               employmentHistory.setEmploymentHistoryStatusDd(employmentHistoryStatusTypeDD);
                           }
                           employmentHistory.setEmploymentHistoryTypeDd(new BigDecimal(0).setScale(0, RoundingMode.DOWN));

                           Address2Type employmentAddress = new Address2Type();
                           employmentAddress.setCountryTypeDd("1");
                           ContactTypeAddresstype2 employmentcontact = new ContactTypeAddresstype2();
                           employmentcontact.setAddress(employmentAddress);
                           employmentHistory.setContact(employmentcontact);

                           employmentHistory.setJobTitle(income.position);
                           if (!isNullOrEmpty(income.typeOfIncome) && income.typeOfIncome.toLowerCase().contains("self")) {
                               SelfEmploymentDetails selfEmploymentDetails = new SelfEmploymentDetails();
                               selfEmploymentDetails.setGrossRevenue(new BigDecimal(incomeAmount));
                               employmentHistory.setSelfEmploymentDetails(selfEmploymentDetails);
                           }

                           if (income.month != null) {
                               Integer totalYear = income.month / 12;
                               Integer remainingMonth = income.month % 12;
                               employmentHistory.setTimeInIndustry(totalYear);
                               employmentHistory.setMonthsOfService(remainingMonth);
                           }
                           

                           // employmentHistoryTypeDd 
//                           enumeration     0                           Full Time
//                           enumeration     1                           Part Time
//                           enumeration     2                           Seasonal
                           // Do not have data
                           
                           // income
                           EmploymentHistoryType.Income filogixIncome = new EmploymentHistoryType.Income();
                           filogixIncome.setIncomeAmount(BigDecimal.valueOf(incomeAmount));
                           // enumeration     0                           Annual
                           // enumeration     1                           Semi-Annual
                           // enumeration     2                           Quarterly
                           // enumeration     3                           Monthly
                           // enumeration     4                           Semi-Monthly
                           // enumeration     5                           Bi-Weekly
                           // enumeration     6                           Weekly
                           BigDecimal bd = new BigDecimal("0");
                           bd = bd.setScale(0, RoundingMode.DOWN);
                           filogixIncome.setIncomePeriodDd(bd);  // All incomes have been converted to annual
                           filogixIncome.setIncomeDescription(incomeDescription);
                           BigDecimal bd2 = underwriteToFilogixIncomeType(income.getTypeOfIncome());
                           bd2.setScale(0, RoundingMode.DOWN);
                           filogixIncome.setIncomeTypeDd(bd2);
                           employmentHistory.setIncome(filogixIncome);
                           
                            // IndustrySectorDd
                            // enumeration     0    Other
                            // enumeration     1    Construction
                            // enumeration     2    Government
                            // enumeration     3    Health
                            // enumeration     4    Education
                            // enumeration     5    High-Tech
                            // enumeration     6    Retail Sales
                            // enumeration     7    Leisure/Entertainment
                            // enumeration     8    Banking/Finance
                            // enumeration     9    Transportation
                            // enumeration     10    Services
                            // enumeration     11    Manufacturing
                            // enumeration     12    Farming/Natural Resources
                            // enumeration     13    Varies                       
                           if (income.industry != null){
                               if (SelectionHelper.compareSelection(AlgoIncome.Industry.BankingFinance, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("8");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.Education, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("4");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.Government, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("2");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.Health, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("3");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.Manufacturing, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("11");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.Other, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("0");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.ResourcesTransportation, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("9");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else if (SelectionHelper.compareSelection(AlgoIncome.Industry.Services, income.industry)){
                                   BigDecimal industrySectorDD = new BigDecimal("10");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                               else{
                                   BigDecimal industrySectorDD = new BigDecimal("0");
                                   industrySectorDD = industrySectorDD.setScale(0, RoundingMode.DOWN);
                                   employmentHistory.setIndustrySectorDd(industrySectorDD);
                               }
                           }
                           
                           // jobTitle
                           if (income.position != null)
                               employmentHistory.setJobTitle(income.position);

                           // MonthsOfService
                           if (income.month != null) {
                               employmentHistory.setTimeInIndustry(income.month);
                               employmentHistory.setMonthsOfService(income.month);
                           }
                                                 
                            // enumeration     0    Other
                            // enumeration     1    Management
                            // enumeration     2    Clerical
                            // enumeration     3    Labour/Tradesperson
                            // enumeration     4    Retired
                            // enumeration     5    Professional
                            // enumeration     6    Self-Employed// 
                           if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.SelfEmployed, income.typeOfIncome)){
                               BigDecimal occupationDd = BigDecimal.valueOf(6);
                               occupationDd = occupationDd.setScale(0, RoundingMode.DOWN);
                               employmentHistory.setOccupationDd(occupationDd);
                               SelfEmploymentDetails selfEmployDetails = new SelfEmploymentDetails();
                               selfEmployDetails.setCompanyType(income.business);
                               selfEmployDetails.setGrossRevenue(BigDecimal.valueOf(Double.parseDouble(income.annualIncome)));
                               selfEmployDetails.setOperatingAs(income.business);    
                               // TODO Assistant will need to input Gross Revenue of Company in Filogix
                           }
                           else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Retired, income.typeOfIncome)){
                               BigDecimal occupationDd = BigDecimal.valueOf(4);
                               occupationDd = occupationDd.setScale(0, RoundingMode.DOWN);
                               employmentHistory.setOccupationDd(occupationDd);
                           }
                           else{
                               BigDecimal occupationDd = BigDecimal.valueOf(5);
                               occupationDd = occupationDd.setScale(0, RoundingMode.DOWN);
                               employmentHistory.setOccupationDd(occupationDd);
                           }

                           applicantApplication.getEmploymentHistory().add(employmentHistory);

                           logger.info(">>> No Other income created for income : {}", income.getTypeOfIncome());

                       } else {
                           double filogixIncomeType = 0;
                            if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.ChildTaxCredit, income.typeOfIncome)){
                                filogixIncomeType = 10;
                                if (income.annualIncome != null){
                                    double adjustedChildTaxCredit = Double.parseDouble(income.annualIncome) * (underwrite.potentialProduct.childTaxCredit / 100);
                                    incomeAmount = adjustedChildTaxCredit;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Child Tax Credit";
                                }

                            }
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.LivingAllowance, income.typeOfIncome)){
                                filogixIncomeType = 10;
                                if (income.annualIncome != null){
                                    double adjustLivingAllowance = Double.parseDouble(income.annualIncome) * (underwrite.potentialProduct.livingAllowance / 100);
                                    incomeAmount = adjustLivingAllowance;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Living Allowance";
                                            
                                }
                            }
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.VehicleAllowance, income.typeOfIncome)){
                                filogixIncomeType = 10;
                                if (income.annualIncome != null){
                                    double adjustVehicleAllow = Double.parseDouble(income.annualIncome) * (underwrite.potentialProduct.vehicleAllowance / 100);
                                    incomeAmount = adjustVehicleAllow;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Vehicle Allowance";
                                            
                                }
                            } 
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Interest, income.typeOfIncome)){
                                filogixIncomeType = 4;
                                if (income.annualIncome != null){
                                    double interestIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = interestIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Interest Income";
                                }
                            } 
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Pension, income.typeOfIncome)){
                                filogixIncomeType = 7;
                                if (income.annualIncome != null){
                                    double pensionIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = pensionIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Pension";
                                }
                            }  
                            else if (SelectionHelper.compareSelectionForAlgoIncomeFilogixApplicant(AlgoIncome.IncomeType.Retired, income.typeOfIncome)){
                                filogixIncomeType = 10;
                                if (income.annualIncome != null){
                                    double retiredIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = retiredIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Retired";
                                }
                            }
                            else {
                                filogixIncomeType = 10;
                                if (income.annualIncome != null){
                                    
                                    double otherIncome = Double.parseDouble(income.annualIncome);
                                    incomeAmount = otherIncome;
                                    if (income.business != null)
                                        incomeDescription = income.business;
                                       else
                                        incomeDescription = "Other Income";
                                }
                            }
                               
                           // Add into Applicant Other Incomes List
                           OtherIncome otherIncome = new OtherIncome();
                           otherIncome.setIncomeAmount(BigDecimal.valueOf(incomeAmount));
                           otherIncome.setIncomeDescription(incomeDescription);
                           BigDecimal incomePeriodDd = BigDecimal.valueOf(0);
                           incomePeriodDd = incomePeriodDd.setScale(0, RoundingMode.DOWN);
                           otherIncome.setIncomePeriodDd(incomePeriodDd); // 0 = Annual
                        // enumeration     4    Interest Income
                        // enumeration     5    Child Support
                        // enumeration     7    Pension
                        // enumeration     8    Alimony
                        // enumeration     10    Other
                           BigDecimal incomeTypeDd = BigDecimal.valueOf(filogixIncomeType);
                           incomeTypeDd = incomeTypeDd.setScale(0, RoundingMode.DOWN);
                           otherIncome.setIncomeTypeDd(incomeTypeDd); 
                           final int applicantIndex = applicantGroup.getApplicant().indexOf(applicantApplication);
                           applicantGroup.getApplicant().get(applicantIndex).getOtherIncome().add(otherIncome);

                           logger.info("Other income has been added to applicantGroup: {}", otherIncome);
                       }
                                      
                   }
                   // Add in Alimony / Child Support ... 
                   if (applicant.monthlychildsupport != null && applicant.monthlychildsupport > 0){
                       OtherIncome otherIncome = new OtherIncome();
                       double annualChildSupport = applicant.monthlychildsupport * 12;                        
                       String incomeDescription = "Annual Child Support Income of $" + annualChildSupport + " has been added to " + applicant.applicantName + "'s income.";
                       otherIncome.setIncomeAmount(BigDecimal.valueOf(annualChildSupport));                       
                       otherIncome.setIncomeDescription(incomeDescription);
                       BigDecimal incomePeriodDd = BigDecimal.valueOf(0);
                       incomePeriodDd = incomePeriodDd.setScale(0, RoundingMode.DOWN);
                       otherIncome.setIncomePeriodDd(incomePeriodDd); // 0 = Annual
                    // enumeration     4    Interest Income
                    // enumeration     5    Child Support
                    // enumeration     7    Pension
                    // enumeration     8    Alimony
                    // enumeration     10    Other
                       BigDecimal incomeTypeDd = BigDecimal.valueOf(5);
                       incomeTypeDd = incomeTypeDd.setScale(0, RoundingMode.DOWN);
                       otherIncome.setIncomeTypeDd(incomeTypeDd);
                       applicantApplication.getOtherIncome().add(otherIncome);
                    }
                   
                   
                   // Applicant Assets
                   
                    // enumeration     0    Savings
                    // enumeration     1    RRSP
                    // enumeration     2    Gift
                    // enumeration     4    Vehicle
                    // enumeration     6    Stocks/Bonds/Mutual
                    // enumeration     8    Other
                    // enumeration     11    Household Goods
                    // enumeration     12    Life Insurance
                    // enumeration     13    Deposit on Purchase// 
                   for (Asset asset : applicant.assets) {
                       AssetType filogixAsset = new AssetType();
                       filogixAsset.setAssetDescription(asset.name);
                       logger.info(">>>>> Asset Type: {}", asset.getAssetType());

                       filogixAsset.setAssetTypeDd( createFilogixAssetType(asset.getAssetType()) );
                       filogixAsset.setAssetValue(BigDecimal.valueOf(asset.value));
                       // Add filogix Asset to list of assets in filogix Applicant
                       applicantApplication.getAsset().add(filogixAsset);
                   }
                  // final String applicantMoney = applicant.money.trim();
                   
                   final String applicantMoney = applicant.money == null ? "0" : applicant.money.trim();
                   //final String applicantMoney = applicant.money == null ? null : applicant.money.trim();
                   logger.info("--------------applicantMoney is----------"+applicantMoney);
                   if (isInteger(applicantMoney)) {
                       AssetType money = new AssetType();
                       money.setAssetTypeDd(BigDecimal.valueOf(8));
                       money.setAssetValue(new BigDecimal(applicantMoney));
                       money.setAssetDescription("Bank Account.");
                       applicantApplication.getAsset().add(money);
                   } else {
                       logger.info(">>> Money value is not integer: {}", applicantMoney);
                   }
                   
                   // Filogix Applicant Liabilities ... (In this instance, Credit ChecK liabilities will be auto added when credit is checked.
                   // Mortgages are added as part of OtherPropertyType ... This Liabilities can be skipped.                   
                   // Other Property

                   final List<Property> properties = applicant.properties;
                    if (properties != null && properties.size() > 0) {
                        logger.info(">>> applicant named:{} have properties size: {}", applicant.getApplicantName(), properties.size());

                        for (Property currentProperty : properties) {
                            PropertyType filogixProperty = new PropertyType();
                            filogixProperty.setAddress( createPropertyAddressForCurrentProperty(currentProperty.getName()) );
                            filogixProperty.setIncludetds( currentProperty.selling ? "N" : "Y" );
                            filogixProperty.setFeesIncludeHeat(underwrite.clientOpportunity.minHeatFee > 0 ? "Y" : "N");

                            final List<PropertyExpense> propertyExpenses = new ArrayList<>();
                            final int annualTax = currentProperty.annualTax;
                            if (annualTax > 0) {
                                PropertyExpense annualTaxesExpanses = new PropertyExpense();
                                annualTaxesExpanses.setPropertyExpenseTypeDd( createPropertyExpanseType("taxes") );
                                annualTaxesExpanses.setPropertyExpensePeriodDd(BigDecimal.ZERO);
                                annualTaxesExpanses.setPropertyExpenseAmount(BigDecimal.valueOf(annualTax));

                                propertyExpenses.add(annualTaxesExpanses);
                                logger.info(">>> annualTaxValue for expanses = {}", annualTax);
                            }

                            final double minHeatCost = underwrite.clientOpportunity.minHeatFee;
                            if (minHeatCost > 0) {
                                PropertyExpense heatingExpanses = new PropertyExpense();
                                heatingExpanses.setPropertyExpenseTypeDd( createPropertyExpanseType("heating") );
                                heatingExpanses.setPropertyExpensePeriodDd(BigDecimal.valueOf(3));
                                heatingExpanses.setPropertyExpenseAmount(BigDecimal.valueOf(minHeatCost));

                                propertyExpenses.add(heatingExpanses);
                                logger.info(">>> minHeatCost for heatingExpanse = {}", minHeatCost);
                            }

                            if (!propertyExpenses.isEmpty())
                                filogixProperty.getPropertyExpense().addAll(propertyExpenses);

                            filogixProperty.setEstimatedAppraisalValue(BigDecimal.valueOf(currentProperty.value));
                            
                            int PropertyID = 0;
                            if (currentProperty.propertyId != null)
                                PropertyID = Integer.parseInt(currentProperty.propertyId);
                            if (applicant.mortgages != null && applicant.mortgages.size() > 0) {
                                for (Mortgage currenMortgage : applicant.mortgages) {
                                    MortgageType filogixMortgage = new MortgageType();
                                    filogixMortgage.setMortgageTypeDd( createMortgageTypeDDForMortgage(underwrite.clientOpportunity.chargeOnTitle) );
                                    filogixMortgage.setPayoffTypeDd( currenMortgage.selling ? BigDecimal.valueOf(2) : BigDecimal.ONE );
                                    // TODO : INFO this is to fullfil "payment" label info in: 
                                    // https://docs.google.com/drawings/d/1cl7KyNMBmdprAmU3h6lRBTZyLJWesFHyqGhpKENLyk0/edit
                                    filogixMortgage.setPAndIPaymentAmount(new BigDecimal(currenMortgage.monthlyPayment));

                                    filogixProperty.setOccupancyTypeDd(currenMortgage.monthlyRent > 0 ? BigDecimal.valueOf(3) : BigDecimal.ZERO);

                                    // RateType = clientOpportunity.potentialProduct.mortgageType
                                    // Fixed = clientOpportunity.potentialProduct.mortgageType (edited)
                                    // Variable = clientOpportunity.potentialProduct.mortgageType.Variable (edited)
                                    // Adjustable = Variable (These two will not be selected in Filogix at this time) (edited)
                                    // Capped Variable = (These two will not be selected in Filogix at this time) (edited)
                                    filogixMortgage.setInterestTypeDd( createInterestType(underwrite.clientOpportunity.mortgageType) );

                                    // TermType = clientOpportunity.potentialProduct.openclosed. (only map open and closed,  not convertible)
                                    filogixMortgage.setPaymentTermDd( createTermType(underwrite.clientOpportunity.openClosed) );

                                    // As for Maturity Data ... clientOpportunity.RenewalDate is being set already (I think) ... 
                                    // this can be passed as Maturity date.  Please check on this and let me know.

                                    final String renewal = currenMortgage.renewal;
                                    final DateFormat maturityDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                                    Date renewalInDate = null;
                                    try {
                                        renewalInDate = maturityDateFormat.parse(renewal);
                                    } catch (ParseException e) {
                                        logger.error("Cannot parse renewal string to date:{}. Filogix will get renewalDate instead.", renewal);
                                        renewalInDate = underwrite.clientOpportunity.renewaldate;
                                    } catch (NullPointerException e) {
                                        logger.error("parsing maturity date for renewal failed, because: {}. Will use 'renewalDate' instead. 'renewal' value: {}", 
                                                e.getMessage(),
                                                renewal);
                                        renewalInDate = maturityDateFormat.parse(renewal);
                                    }
                                    final GregorianCalendar maturityDate = new GregorianCalendar();
                                    maturityDate.setTime(renewalInDate);
                                    filogixMortgage.setMaturityDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(maturityDate));
                                    
                                    int MortgageID = 1;
                                    if (currenMortgage.propertyId != null)
                                        MortgageID = Integer.parseInt(currenMortgage.propertyId);
                                    
                                    if (MortgageID == PropertyID){
                                        // BigDecimal.valueOf()
                                        filogixMortgage.setBalanceRemaining(BigDecimal.valueOf(Double.parseDouble(currenMortgage.balance)));
                                        filogixMortgage.setInterestRate(BigDecimal.valueOf(Double.parseDouble(currenMortgage.interestRate)));
                                        
                                        // enumeration     0    Monthly
                                        // enumeration     1    Semi Monthly
                                        // enumeration     2    Biweekly
                                        // enumeration     3    Accelerated Biweekly
                                        // enumeration     4    Weekly
                                        // enumeration     5    Accelerated Weekly
                                        BigDecimal paymentFrequencyDD = BigDecimal.valueOf(0);
                                        paymentFrequencyDD = paymentFrequencyDD.setScale(0, RoundingMode.DOWN);
                                        filogixMortgage.setPaymentFrequencyDd(paymentFrequencyDD);
                                        filogixMortgage.setExistingMortgageHolder(currenMortgage.name);
                                        
                                        if (currenMortgage.monthlyRent > 0){
                                            
                                            if (SelectionHelper.compareSelection(AlgoProduct.TreatmentOfRental.AddToIncome, underwrite.potentialProduct.rentalIncomeTreatment))
                                            {
                                                double amountOfRent = currenMortgage.monthlyRent * (underwrite.potentialProduct.rentalIncomePercent / 100);
                                                RentalIncome filogixRent = new RentalIncome();
                                                filogixRent.setIncomeAmount(BigDecimal.valueOf(amountOfRent));
                                                String rentDescription = "Adjusted Monthly rent of " + currenMortgage.monthlyRent;
                                                filogixRent.setIncomeDescription(rentDescription.substring(0, 79)); // Filogix max length is 80 chars.
                                                // enumeration     0    Annual
                                                // enumeration     1    Semi-Annual
                                                // enumeration     2    Quarterly
                                                // enumeration     3    Monthly
                                                // enumeration     4    Semi-Monthly
                                                // enumeration     5    Bi-Weekly
                                                // enumeration     6    Weekly
                                                BigDecimal incomePeriodDD = BigDecimal.valueOf(3);
                                                incomePeriodDD = incomePeriodDD.setScale(0, RoundingMode.DOWN);
                                                filogixRent.setIncomePeriodDd(incomePeriodDD);
                                                // enumeration     9    Rental Income
                                                BigDecimal incomeTypeDD = BigDecimal.valueOf(9);
                                                incomeTypeDD = incomeTypeDD.setScale(0, RoundingMode.DOWN);
                                                filogixRent.setIncomeTypeDd(incomeTypeDD);
                                                filogixProperty.setRentalIncome(filogixRent);        
                                                // TODO Determine whether there is a flag which needs setting in order to include the income ... 
                                            }
                                            else {
                                                double allowableRentalIncome = (currenMortgage.monthlyRent * (underwrite.potentialProduct.rentalIncomePercent / 100));
                                                
                                                double MortgagePayment = Double.parseDouble(currenMortgage.monthlyPayment);
                                                double netIncomeFromProperty = allowableRentalIncome - MortgagePayment;
                                                if (netIncomeFromProperty > 0){ 
                                                
                                                    RentalIncome filogixRent = new RentalIncome();
                                                    filogixRent.setIncomeAmount(BigDecimal.valueOf(netIncomeFromProperty));
                                                    String rentDescription = "Net Monthly Rental $" 
                                                            + currenMortgage.monthlyRent;
                                                    filogixRent.setIncomeDescription(rentDescription);
                                                    // enumeration     0    Annual
                                                    // enumeration     1    Semi-Annual
                                                    // enumeration     2    Quarterly
                                                    // enumeration     3    Monthly
                                                    // enumeration     4    Semi-Monthly
                                                    // enumeration     5    Bi-Weekly
                                                    // enumeration     6    Weekly
                                                    BigDecimal incomePeriodDD = BigDecimal.valueOf(3);
                                                    incomePeriodDD = incomePeriodDD.setScale(0, RoundingMode.DOWN);
                                                    filogixRent.setIncomePeriodDd(incomePeriodDD);
                                                    // enumeration     9    Rental Income
                                                    BigDecimal incomeTypeDD = BigDecimal.valueOf(9);
                                                    incomeTypeDD = incomeTypeDD.setScale(0, RoundingMode.DOWN);
                                                    filogixRent.setIncomeTypeDd(incomeTypeDD);
                                                    filogixProperty.setRentalIncome(filogixRent);
                                                }
                                            }
                                            
                                        }
                                    }
                                //  Add Property to list of Properties in Applicant
                                    OtherProperty filogixOtherProperty = new OtherProperty();
                                    filogixOtherProperty.setProperty(filogixProperty);
                                    filogixOtherProperty.getMortgage().add(filogixMortgage);
                                    applicantApplication.getOtherProperty().add(filogixOtherProperty);
                                }
                            }
                        }
                    } else {
                        logger.warn(">>> There's no property for applicant named: {}. Property data size:{}", 
                                applicant.getApplicantName(), properties == null ? "NULL" : properties.size());
                    }
                   // "applicantadditionalData"
                   // I cannot seem to find any additional data in the ReferralApplicaiton... ? 
                   // In the event we determine this is where deal notes, should be added, I will add here .
               }
               filogixApp.getApplicantGroup().add(applicantGroup);
               filogixApp.setSubjectProperty(createSubjectProperty(underwrite));
               filogixApp.getMortgage().addAll(createFilogixMortgageTypesForSubjectProperty(underwrite));

               this.referralApplication = filogixApp;
               
                
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


    public ReferralApplicationType getReferralApplication() {
        return referralApplication;
    }


    private SubjectProperty createSubjectProperty(UnderwritePostSel underwrite) {
        final Opportunity opportunity = underwrite.clientOpportunity;

        final PropertyType property = new PropertyType();
        try{
        property.setOccupancyTypeDd(createOccupancyType(opportunity.getLivingInProperty())); // Owner, Renter, Owner and Renter, Second Home/Vacation Property
        property.setAddress(this.createPropertyAddress(opportunity));
        property.setLegalLine1(opportunity.lot); // Lot
        property.setLegalLine2(opportunity.block); // Blockprote
        property.setLegalLine3(opportunity.plan); // Townshship. See feedback from here: https://docs.google.com/drawings/d/1ZlmdIFAVmqwV97HoqCsUP1jGmgJqIQvpDHcHgyaa-2Q/edit
       
       // property.setLotSize(isInteger(opportunity.lotSize.trim()) ? Integer.parseInt(opportunity.lotSize.trim()) : 0);
        
        if (opportunity.lotSize != null)
            property.setLotSize(isInteger(opportunity.lotSize.trim()) ? Integer.parseInt(opportunity.lotSize.trim()) : 0);
        else 
            property.setLotSize(0);
        property.setMlsListingFlag(isNullOrEmpty(opportunity.mls) ? "N" : (opportunity.mls.toLowerCase().contains("Y") ? "Y" : "N"));

        // Commenting out. This is used to set "Tenure" label in UI, which is doesn't even exist!
        // property.setPropertyTypeDd(createFilogixPropertyType(opportunity.propertyType)); 

        property.setDwellingTypeDd( createFilogixDwellingType(opportunity.propertyType) );
        property.setLivingSpace(opportunity.squareFootage);
        property.setHeatTypeDd(createFilogixHeatingType(opportunity.heating));
        property.setWaterTypeDd(createFilogixWaterType(opportunity.water));
        property.setDwellingStyleDd(createFilogixPropertyStyle(opportunity.propertyStyle)); // This is actually "property style".
        property.setStructureAge(createFilogixStructureAge(opportunity.age));
        property.setGarageSizeDd(createFilogixGarageSize(opportunity.garageSize));
        property.setGarageTypeDd(createFilogixGarageType(opportunity.garageType));
        property.setSewageTypeDd(createFilogixSewageType(opportunity.sewage));

        property.setNewConstructionDd(BigDecimal.ZERO);
        
        
        
        }catch(Exception e){

        	logger.error("Error  in  setting property");
        }

        final List<PropertyExpense> propertyExpenses = createPropertyExpanses(underwrite);
        property.getPropertyExpense().addAll(propertyExpenses);

        final RentalIncome rentalIncome = createRentalIncome(underwrite);
        property.setRentalIncome(rentalIncome);

        SubjectProperty subjectProperty = new SubjectProperty();
        if (!isNullOrEmpty(opportunity.whatIsYourLendingGoal)) {
            final BigDecimal propertyValue = new BigDecimal(opportunity.propertyValue);
            final String lendingGoal = opportunity.whatIsYourLendingGoal.toLowerCase();
            logger.info("what is your lending goal is:{}", lendingGoal);

            // Changed because of this: >>> Unknown lendingGoal. Value from open erp is: 2
            // public enum LendingGoal { Prequalify, Approval, Refinance };
            /* If (clientOpportunity.LookingTo == Approval), value mapped to "Purchase Price; (equal 2)
             * else If (clientOpportunity.LookingTo == Refiance), value mapped to Estimated Value; (equal 3)
             * else If (clientOpportunity.LookingTo == Pre-Approval), value mapped to Estimated Value; (equal 1)
             */
            if (lendingGoal.contains("1")) {
                property.setEstimatedAppraisalValue(propertyValue);
            } else if (lendingGoal.contains("2")) {
                property.setPurchasePrice(propertyValue);
            } else if (lendingGoal.contains("3")) {
                property.setActualAppraisalValue(propertyValue);
            } else {
                logger.warn(">>> Unknown lendingGoal. Value from open erp is: {}", lendingGoal);
            }
        } else logger.info(">>> whatIsYourLendingGoal is null or empty.");

        subjectProperty.setProperty(property);
        subjectProperty.getExistingMortgage().addAll(createFilogixMortgageTypesForSubjectProperty(underwrite));
        

        return subjectProperty;
    }


    public Address1Type createPropertyAddress(Opportunity opportunity) {
        final String postalIntl = opportunity.getPostalCode();
        String postalFSA = null;
        String postalLDU = null;
        if (!isNullOrEmpty(postalIntl)) {
            postalFSA = postalIntl.substring(0, 3); // Is this correct?
            postalLDU = postalIntl.substring(3, 6); // is this correct?
        }
        
        final Address1Type result = new Address1Type();
        final String googleAddress = opportunity.getAddress();
        logger.info(">>> googleAddress from opportunity: {}", googleAddress);

        result.setUnitNumber(createFilogixAddressComponent(googleAddress, AddressComponentType.UNIT));
        result.setStreetNumber( createFilogixAddressComponent(googleAddress, AddressComponentType.STREET_NUMBER) );
        result.setStreetName(createFilogixAddressComponent(googleAddress, AddressComponentType.STREET_NAME));
        result.setStreetTypeDd( createFilogixAddressStreetType(googleAddress) );
        result.setStreetDirectionDd(createFilogixAddressDirection(googleAddress));

        // result.setStreetTypeDd(value);
        result.setInternationalPostalCode(postalIntl);
        result.setPostalFsa(postalFSA);
        result.setPostalLdu(postalLDU);
        result.setCity(opportunity.getCity());
        result.setProvinceDd(createFilogixProvinceIdByCode(opportunity.getProvince()));
        result.setCountryTypeDd("1");
        return result;
    }


    public Address1Type createPropertyAddressForCurrentProperty(final String googleAddress) {
        logger.info(">>> googleAddress value={}", googleAddress);
        final Address1Type result = new Address1Type();
        result.setUnitNumber(createFilogixAddressComponent(googleAddress, AddressComponentType.UNIT));
        result.setStreetNumber( createFilogixAddressComponent(googleAddress, AddressComponentType.STREET_NUMBER) );
        result.setStreetName(createFilogixAddressComponent(googleAddress, AddressComponentType.STREET_NAME));
        result.setStreetTypeDd( createFilogixAddressStreetType(googleAddress) );
        result.setStreetDirectionDd(createFilogixAddressDirection(googleAddress));
        result.setCity( createFilogixAddressComponent(googleAddress, AddressComponentType.CITY) );
        final String provinceString = createFilogixAddressComponent(googleAddress, AddressComponentType.PROVINCE);
        if (!isNullOrEmpty(provinceString)) {
            try {
                result.setProvinceDd( new BigDecimal(provinceString).setScale(0, RoundingMode.DOWN) );
            } catch (Exception e) {
                logger.error("Error: {}", e.toString());
            }
        }
        result.setCountryTypeDd("1");
        return result;
    }
}
