package com.syml.test.junit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;





import com.couchbase.client.CouchbaseClient;
import com.syml.couchbase.CouchBaseOperation;



public class ReadJsonDataFromMorgtage {
	
	//@Test
	public static void main(String[] args)  throws JSONException, IOException   {
		CouchBaseOperation cbo=new CouchBaseOperation();
	CouchbaseClient	client1=cbo.getConnectionToCouchBase();





//ArrayList<Asset> assetList=new ArrayList<Asset>();
ArrayList<Income> incomeList=new ArrayList<Income>();
ArrayList<Income> co_incomeList=new ArrayList<Income>();
Property property1=new Property();
Property property2=new Property();
Property property3=new Property();
Property property4=new Property();
Property property5=new Property();
Property property6=new Property();


Property co_property1=new Property();
Property co_property2=new Property();
Property co_property3=new Property();
Property co_property4=new Property();
Property co_property5=new Property();
Property co_property6=new Property();

ArrayList<Property> propertyList=new ArrayList<Property>();
ArrayList<Property> co_propertyList=new ArrayList<Property>();
Applicant aplApplicant=new Applicant();
Applicant co_applApplicant= new Applicant();
		String object=(String)client1.get("Applicant_311");
		System.out.println("data "+ object);
		JSONObject jsonData = new JSONObject(object);
		
		Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = jsonData.keys();
	    Opportunity opportunity=new Opportunity();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = jsonData.get(key);
	    //	System.out.println(key+" :  "+ value.toString());
			switch (key) {
			
			
			
			case "FirstName_of_applicant":
				aplApplicant.setApplicantName(value.toString());
				
				break;
			case "ApplicantID1":
				aplApplicant.setApplicantId(value.toString());
				
				break;
				
			case "FirstName_of_co_applicant":
				co_applApplicant.setApplicantName(value.toString());
				
				break;
			case "Applicant-leadingGoal":
				opportunity.setWhatIsYourLendingGoal(value.toString());
				
				break;
			case "opporunity_id":
				
				int id=0;
				try{
					id=Integer.parseInt(value.toString());
				}catch (Exception e) {
					// TODO: handle exception
				}
				opportunity.setPartnerId(id);
				
				break;
				
			case "Applicant-propertyaddress1":
				opportunity.setAddress(value.toString());
				
				break;
			case "Applicant-imaginesamejob1":
				opportunity.setJob5Years(value.toString());
				break;
				
			case "Applicant-paymentsource1":
				opportunity.setDownPaymentComingFrom(value.toString());

				   String[] downSourcePayment=value.toString().split("\n");


					int downSourcePaymentLen=downSourcePayment.length;

					List<String> selectedValues=new ArrayList<String>();
					for(int i=0; i<=downSourcePaymentLen-1;i++ ){
						selectedValues.add(downSourcePayment[i].trim());
					}
					for(String selectedValue : selectedValues){
				   if(selectedValue.equalsIgnoreCase("Bank Account")){
					    
					    opportunity.setBankAccount(1d);
					     
					    }else if(selectedValue.equalsIgnoreCase("RRSPS")){
					    	 opportunity.setRrspAmount(1d);
					     
					    }else if (selectedValue.equalsIgnoreCase("Investments")){
					    	opportunity.setOtherAmount(1d);
					    }else if(selectedValue.equalsIgnoreCase("Borrowed")){
					    	opportunity.setBorrowedAmount(1d);

					   }else if (selectedValue.equalsIgnoreCase("Sale of Property")) {
					    
						   opportunity.setSaleOfExistingAmount(1d);


					   }else if (selectedValue.equalsIgnoreCase("Gift")) {
					    
						   opportunity.setGiftedAmount(1d);

					    
					   }else if (selectedValue.equalsIgnoreCase("Personal Cash")) {
					    
						   opportunity.setPersonalCashAmount(1d);
					    
					   } 
					   else if (selectedValue.equalsIgnoreCase("Existing Equity")) {
						    
						   opportunity.setExistingEquityAmount(1d);

					    
					   } 
					   else if (selectedValue.equalsIgnoreCase("Sweat Equity")) {
						    
						   opportunity.setSweatEquityAmount(1d);
					    
					   } 
					   else if (selectedValue.equalsIgnoreCase("2nd Mortgage")) {
						    
						   opportunity.setSecondaryFinancingAmount(1d);
					    
					   }  else if (selectedValue.equalsIgnoreCase("Other")) {
						    
						   opportunity.setOtherAmount(1d);
					    
					   } 
					}
				
				
				
				break;
				
			case "Applicant-downpayment1":
				
				double downpayment=0;
				try{
					downpayment=new Double(value.toString());
				}catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				opportunity.setDownpaymentAmount(downpayment);
				break;
				
			case "Applicant-whoWillLive":
				if(value.toString().equalsIgnoreCase("Renter")){
  				opportunity.setIsRentalProperty(true);
				}
  				break;
			case "Applicant-rentalAmount":
				double rental=0;
				try{
					rental=new Double(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				opportunity.setMonthlyRentalIncome(rental);
				break;
				
				
				
			case "Applicant-lookingfor11":
				int desiredMortgitionValue=0;
				if(value.toString().equalsIgnoreCase("Suggest")){
					desiredMortgitionValue=25;
					
				}else{
					
					try{
						desiredMortgitionValue=new Integer( value.toString().substring(0,2));
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				opportunity.setDesiredAmortization(desiredMortgitionValue);
				break;
				
				
			case "Applicant-currentMortgageTerm":
				  				opportunity.setDesiredTerm(value.toString());
				break;
				
			case "Applicant-mortgageinmind1":
  				opportunity.setDesiredMortgageType(value.toString());
  				break;
  				
  				
  				//incomes details added from here------------------------------
  				
  				
			case "Applicant-type_Pension":
  				Income income1=new Income();
  				income1.setTypeOfIncome(value.toString());
  				incomeList.add(income1);
  				
  				break;
  				
  				
			case "Applicant-type_Vehicle":
  				Income income2=new Income();
  				income2.setTypeOfIncome(value.toString());
  				incomeList.add(income2);
  				
  				break;
  				
			case "Applicant-type_Interest":
  				Income income3=new Income();
  				income3.setTypeOfIncome(value.toString());
  				incomeList.add(income3);
  				
  				break;
  				
			case "Applicant-type_Child":
  				Income income4=new Income();
  				income4.setTypeOfIncome(value.toString());
  				incomeList.add(income4);
  				
  				break;
  				
  				
  				
			case "Applicant-type_Child Support":
  				Income income5=new Income();
  				income5.setTypeOfIncome(value.toString());
  				incomeList.add(income5);
  				
  				break;
			case "Applicant-type_Spouse":
  				Income income6=new Income();
  				income6.setTypeOfIncome(value.toString());
  				incomeList.add(income6);
  				
  				break;
  				
			case "Applicant-type_Dividend":
  				Income income7=new Income();
  				income7.setTypeOfIncome(value.toString());
  				incomeList.add(income7);
  				
  				break;
  				
			case "Applicant-currentEmployee1":
  				Income income8=new Income();
  				income8.setTypeOfIncome("Employeed");
  				incomeList.add(income8);
  				
  				break;
			case "Applicant-priorEmployee1_Self-Employ":
  				Income income9=new Income();
  				income9.setTypeOfIncome("Self-Employeed");
  				incomeList.add(income9);
  				
  				break;
			case "Applicant-type_Comission":
  				Income income10=new Income();
  				income10.setTypeOfIncome("Commision");
  				incomeList.add(income10);
  				
  				break;
  				
  				
  				
  				
  				
  				
			case "CoApplicant-Pensiontype":
  				Income income11=new Income();
  				income11.setTypeOfIncome(value.toString());
  				co_incomeList.add(income11);
  				
  				break;
  				
  				
			case "CoApplicant-VehicleAllowancetype2":
  				Income income22=new Income();
  				income22.setTypeOfIncome(value.toString());
  				co_incomeList.add(income22);
  				
  				break;
  				
			case "CoApplicant-Interesttype3":
  				Income income33=new Income();
  				income33.setTypeOfIncome(value.toString());
  				co_incomeList.add(income33);
  				
  				break;
  				
			case "CoApplicant-Child Tax Credittype4":
  				Income income44=new Income();
  				income44.setTypeOfIncome(value.toString());
  				co_incomeList.add(income44);
  				
  				break;
  				
  				
  				
			case "CoApplicant-Child Supporttype5":
  				Income income55=new Income();
  				income55.setTypeOfIncome("Child Support");
  				co_incomeList.add(income55);
  				
  				break;
			case "CoApplicant-Spouse Supporttype6":
  				Income income66=new Income();
  				income66.setTypeOfIncome("Spouse Support");
  				co_incomeList.add(income66);
  				
  				break;
  				
			case "CoApplicant-Dividendtype7":
  				Income income77=new Income();
  				income77.setTypeOfIncome(value.toString());
  				co_incomeList.add(income77);
  				
  				break;
  				
			case "CoApplicant-Employedtype8":
  				Income income88=new Income();
  				income88.setTypeOfIncome("Employeed");
  				co_incomeList.add(income88);
  				
  				break;
			case "CoApplicant-Self-Employtype12":
  				Income income99=new Income();
  				income99.setTypeOfIncome("Self-Employeed");
  				co_incomeList.add(income99);
  				
  				break;
			case "CoApplicant-Comissiontype14":
  				Income income101=new Income();
  				income101.setTypeOfIncome("Commision");
  				co_incomeList.add(income101);
  				
  				break;
  				
  				
  				
  				
  				
  			/*	// assets data inserting here----------------------------------------------
  				
  				
			case "Applicant-AssettypeBank Account":
  				Asset asset1=new Asset();
  			asset1.setAssetType("Bank Account");
  				assetList.add(asset1);
  				
  				break;*/
  			//imigration date inserting.....................................
  				
  			
  				
			case "Applicant-whendidCanda":
  								try{	
  				aplApplicant.setImmigrationDate(new Date(value.toString()));
  								}catch (Exception e) {
  									e.printStackTrace();// TODO: handle exception
								}
  				
  				break;
  				
  				
		
  				
			case "Applicant-non_Resident":
				boolean val=true;
				if(value.toString().equalsIgnoreCase("yes")){
					val=false;
				}
  									
  				aplApplicant.setNonResident(val);
  				
  				break;
  				
			case "CoApplicant-whendidcametoCannada":
					try{
  				co_applApplicant.setImmigrationDate(new Date(value.toString()));
					}catch (Exception e) {
						e.printStackTrace();// TODO: handle exception
					}
  				
  				break;
  				
  				
		
  				
			case "CoApplicant-non_Resident":
				boolean val1=true;
				if(value.toString().equalsIgnoreCase("yes")){
					val1=false;
				}
  									
				co_applApplicant.setNonResident(val1);
  				
  				break;
  				
  				///------inserting applicant proprtys[---------------
			case "Applicant-address1":
				property1.setName(value.toString());
  				
  				break;
			case "Applicant-condoFees1":
				int moCondofees=0;
				try{
					moCondofees=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				property1.setMoCondoFees(moCondofees);
			
  			
  				break;
  				
  				
			case "Applicant-address2":
				property2.setName(value.toString());
  				
  				break;
			case "Applicant-condoFees2":
				int moCondofees1=0;
				try{
					moCondofees1=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				property2.setMoCondoFees(moCondofees1);
				
  				break;
  				
			case "Applicant-address3":
				property3.setName(value.toString());
  				
  				break;
			case "Applicant-condoFees3":
				int moCondofees2=0;
				try{
					moCondofees2=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();	// TODO: handle exception
				}
				property3.setMoCondoFees(moCondofees2);
			
  				break;
  				
  				
			case "Applicant-address4":
				property4.setName(value.toString());
  				
  				break;
			case "Applicant-condoFees4":
				int moCondofees3=0;
				try{
					moCondofees3=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				property4.setMoCondoFees(moCondofees3);
  				break;
  				
  				
  				
			case "Applicant-address5":
				property5.setName(value.toString());
  				
  				break;
			case "Applicant-condoFees5":
				int moCondofees4=0;
				try{
					moCondofees4=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				property5.setMoCondoFees(moCondofees4);
  				break;
  				
  				
			case "Applicant-address6":
				property6.setName(value.toString());
  				
  				break;
			case "Applicant-condoFees6":
				int moCondofees5=0;
				try{
					moCondofees5=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				property6.setMoCondoFees(moCondofees5);
  				break;
  				
			case "CoApplicant-address1":
				co_property1.setName(value.toString());
  				
  				break;
			case "CoApplicant-condoFees1":
				int moCondofees11=0;
				try{
					moCondofees11=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				co_property1.setMoCondoFees(moCondofees11);
  			
  				break;
  				
  				
			case "CoApplicant-address2":
				co_property2.setName(value.toString());
  				
  				break;
			case "CoApplicant-condoFees2":
				int moCondofees12=0;
				try{
					moCondofees12=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				co_property2.setMoCondoFees(moCondofees12);
				
  				break;
  				
			case "CoApplicant-address3":
				co_property3.setName(value.toString());
  				
  				break;
			case "CoApplicant-condoFees3":
				int moCondofees22=0;
				try{
					moCondofees22=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				co_property3.setMoCondoFees(moCondofees22);
			
  				break;
  				
  				
			case "CoApplicant-address4":
				co_property4.setName(value.toString());
  				
  				break;
			case "CoApplicant-condoFees4":
				int moCondofees33=0;
				try{
					moCondofees33=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				co_property4.setMoCondoFees(moCondofees33);
			
  				break;
  				
  				
  				
			case "CoApplicant-address5":
				co_property5.setName(value.toString());
  				
  				break;
			case "CoApplicant-condoFees5":
				int moCondofees44=0;
				try{
					moCondofees44=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				co_property5.setMoCondoFees(moCondofees44);
				
  				break;
  				
  				
			case "CoApplicant-address6":
				co_property6.setName(value.toString());
  				
  				break;
			case "CoApplicant-condoFees6":
				int moCondofees55=0;
				try{
					moCondofees55=new Integer(value.toString());
				}catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
				}
				co_property6.setMoCondoFees(moCondofees55);
				
  				break;
  				
  				
		  				
  				
			default:
				break;
			}
	        
	    }
	    
		propertyList.add(property1);
		propertyList.add(property2);
		propertyList.add(property3);
		propertyList.add(property4);
		propertyList.add(property5);
		propertyList.add(property6);
		
		co_propertyList.add(co_property1);
		co_propertyList.add(co_property2);
		co_propertyList.add(co_property3);
		co_propertyList.add(co_property4);
		co_propertyList.add(co_property5);
		co_propertyList.add(co_property6);
		aplApplicant.setProperties(propertyList);
		co_applApplicant.setProperties(co_propertyList);
aplApplicant.setIncomes(incomeList);
co_applApplicant.setIncomes(co_incomeList);
ArrayList<Applicant> applicantList=new ArrayList<Applicant>();
applicantList.add(aplApplicant);
applicantList.add(co_applApplicant);

opportunity.setApplicants(applicantList);
		
ApplicantDocument data=	new List_Of_Document_NeedD().list_Of_Document_Needmethod(opportunity);
System.out.println("data Applicant"+data.getFirstName() +" "+ data.getDocuments()+"   Co_Applicant   "+data.getFirstName() +"documents "+ data.getCo_documents() +" opprtunity "+data.getPropertyDocments() +"  "+data.getDownPayments() );


//cbo.storeDataInCouchBase(opportunity.getPartnerId().toString(),  data);
//bo.storeDataInCouchBase("123",  data);
/*JSONObject jsonData1 = new JSONObject();
//jsonData.put("FormType",formType);
Set<Map.Entry<String, String>> set = data.entrySet();
for (Map.Entry<String, String> entry : set) {
	jsonData1.put(entry.getKey(), entry.getValue());
}*/
//System.out.println(jsonData1.toString());
		//jsonData.put("FormType","mortgage");
/*JSONArray listOfApllicant=new JSONArray(data.get("Documentation").toString());
JSONObject jsonApplicant=(JSONObject) listOfApllicant.get(0);
JSONObject Aplicant=(JSONObject) jsonApplicant.get("Documents");
		//for check employed doc
//System.out.println("json"+Aplicant.toString());

FileReader filereader=new FileReader("/home/venkateshm/Desktop/SampleClientDocs1.txt");
StringBuilder sb=new StringBuilder();
 BufferedReader br=null;

 try{
br=new BufferedReader(filereader);
  String line=null;
  while((line = br.readLine()) != null)
   {
     sb.append(line);
    }
  //System.out.println(sb);
  }catch(Exception ex){
    //
  }finally{
   if(br!=null)
     br.close();
  }
// System.out.println(sb.toString());
JSONObject val=new JSONObject(sb.toString());
JSONArray jsonObject=(JSONArray) val.get("Documents");
JSONObject filejson=(JSONObject) jsonObject.get(0);
//if(jsonApplicant.getString("Applicant_name")==)
System.out.println("iiiiiiiiiiiiiiiiiiiii");
System.out.println("hello"+jsonApplicant.get("Applicant_Name").toString() +""+filejson.get("First Name").toString());
if(jsonApplicant.getString("Applicant_Name").equals(filejson.getString("First Name"))){
	
	
	
	
	System.out.println("hello");
		if(Aplicant.getString("Letter of EmploymentDoc")==jsonObject.getString("Letter of Employment")){
			System.out.println("hello");
		}
		assertEquals(Aplicant.get("Letter of EmploymentDoc").toString(),"Letter of Employment");
		
		assertEquals(Aplicant.get("payStubWeb").toString(),"PayStubs");
		//assertEquals(Aplicant.get("Confirmation of Taxes Paid").toString(),"Confirmation of Taxes Paid");
		assertEquals(Aplicant.getString("Confirmation of Taxes Paid"),"Confirmation of Taxes Paid");
		assertEquals(Aplicant.getString("Divorce/Separation Agreement"),"Divorce/Separation Agreement ");
		
}*/
	
	}


}
