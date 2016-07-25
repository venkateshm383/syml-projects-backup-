package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Result;
import views.html.completed_application;
import views.html.expired_page;
import views.html.leadsucess;
import views.html.mortgagePage1;
import views.html.mortgagePage10Assets;
import views.html.mortgagePage11Properties;
import views.html.mortgagePage12Disclose;
import views.html.mortgagePage1a;
import views.html.mortgagePage1b;
import views.html.mortgagePage2Pre;
import views.html.mortgagePage2Pur;
import views.html.mortgagePage2Ref;
import views.html.mortgagePage3;
import views.html.mortgagePage4;
import views.html.mortgagePage5a;
import views.html.mortgagePage5b;
import views.html.mortgagePage6;
import views.html.mortgagePage6a;
import views.html.mortgagePage6b;
import views.html.mortgagePage7Address;
import views.html.mortgagePage7a;
import views.html.mortgagePage7b;
import views.html.mortgagePage8;
import views.html.mortgagePage8a;
import views.html.mortgagePage9;
import views.html.privacypolicy;
import views.html.supplementary_income;
import views.html.mortgagePage8b;

import com.syml.Constants;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.mortgageapplication.MortgageApplicationConstants;
import com.syml.mortgageapplication.backbuttonimpl.MortgageApplication11BackButtonService;
import com.syml.mortgageapplication.backbuttonimpl.MortgageApplicationPage10BackButtonService;
import com.syml.mortgageapplication.backbuttonimpl.MortgageApplicationPageOneBBackButtonService;
import com.syml.mortgageapplication.backbuttonimpl.MortgageApplicationPageOneBackButtonService;
import com.syml.mortgageapplication.impl.MortgageApplicationPage2PreService;
import com.syml.mortgageapplication.impl.MortgageApplicationPage2PurService;
import com.syml.mortgageapplication.impl.MortgageApplicationPage2RefService;
import com.syml.mortgageapplication.impl.MortgageApplicationPage2Service;
import com.syml.mortgageapplication.impl.MortgageApplicationPageElevenService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageFiveAService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageFiveBService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageFourService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageOneAService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageOneBService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;
import com.syml.mortgageapplication.impl.MortgageApplicationPageSevenAService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageSevenBService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageSevenService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageSixAService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageSixBService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageSixService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageTenService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageThreeService;
import com.syml.mortgageapplication.impl.MortgageApplicationPageTwelevService;
import com.syml.mortgageapplication.impl.MortgageApplicationPage8BService;


 
import views.html.wfg2;
import views.html.mortgagePage7a;
import views.html.mortgagePage12Discloseduplicate;
import views.html.visdomreferral2;

public class Application extends Controller {

	public static Result index() {

		//  return ok(leadsucess.render("Welcome"));
		//	return ok(wfg2.render("Realtor",232,1));
		
		return ok(mortgagePage7Address.render("yes","TestApplicantName", "TestCoapplicantName",new ApplicantAddressParameter7(),new CoApplicantAddressParameter7(),"no","no"));
		
	}
	
	public static Result mortgagePage33() {
		
		/*return ok(mortgagePage12Disclose.render("", "no", 7,
				"manoj", "venky"));*/
		
	 //return ok(mortgagePage1.render(new Opportunity(),new Applicant(),new Applicant()));
	 
	 
	 //return ok(visdomreferral2.render("Professional",991,0));
		 //return ok(mortgagePage1a.render(new Opportunity(),new Applicant()));
//		 return ok(mortgagePage1b.render(new Opportunity(),new Applicant()));

//		 return ok(mortgagePage2Pre.render("",new LendingTerm()));
		 return ok(mortgagePage2Pur.render(new LendingTerm(), "yes"));
		 //return ok(mortgagePage2Ref.render(new LendingTerm(),""));

//return ok(mortgagePage3.render("","","","","","","","",""));
		 
		//return ok(mortgagePage4.render("","","","",""));
//return ok(mortgagePage5a.render("","","","","",""));
		//return ok(mortgagePage5b.render("","","","",""));

		 //return ok(mortgagePage6a.render(new PersonalInfo()));
		//return ok(mortgagePage6b.render(new PersonalInfo()));
		/*return ok(mortgagePage6.render("yes", "xxx", "yyy", "", "", "", "",
		 "", "", "", "", "", "", "", "", "", "", "", "", "", ""));*/
		 
		
/*return ok(mortgagePage7a.render(new ApplicantAddressParameter7(),"no"));*/
		/*return ok(mortgagePage7b.render(new CoApplicantAddressParameter7(),"no"));*/
	/*return ok(mortgagePage7Address.render("yes", "xsxx", "yyy",new
		   ApplicantAddressParameter7(),new CoApplicantAddressParameter7(),"no","no"));*/
		
//return ok(mortgagePage8b.render(new Applicant()));
		 
		 //return ok(supplementary_income.render(new Applicant()));
	//return ok(mortgagePage8Duplicate.render("applicantName"));
		/*TotalAssets total= new TotalAssets();
		    return  ok(mortgagePage10Assets.render("","xxx","yyy",total));*/
		 

		 /*return ok(mortgagePage11Properties.render("dummy","xxx" ,"yyy", new
		 ArrayList<Property>()));*/

		 /*return ok(mortgagePage12Disclose.render("","yes",new
		Integer(0),"xxx","yyy"));*/
		// return
		// ok(MortgageApplicationSucess.render("Thank you for completing the Visdom Mortgage Application.  We will be in touch with you very soon."));
//return ok(mortgagePageAssetsTest.render(""));
		
		/*return ok(mortgagePage8a.render("", new Income(), new Income(), new Income(), new Income(), new Income(), new Income(),  new Income(),  new Income(),  new Income(),  new Income(),  new Income(),  new Income(),  new Income(),  new Income(),  new Income(),  new Income(), 0, 0));*/
	}
	public static Result tt3() {
		DynamicForm dynamicForm = form().bindFromRequest();
		String currentAddress = dynamicForm.get("currentAddress1");
		String inputMovedIn1 = dynamicForm.get("movedIn1");
		String currentSumMonth = dynamicForm.get("currentaddressmonthsum");
		String totalcurrentMonths = dynamicForm.get("totalcurrentmonths");
		Logger.debug("address-->"+currentAddress + "movedin "+inputMovedIn1+ " currentSumMonth"+currentSumMonth+" totalcurrentMonths"+totalcurrentMonths);
		
		String priorAddress1 = dynamicForm.get("currentAddress2");
		String inputMovedIn2 = dynamicForm.get("movedIn2");
		String priorSumMonth1 = dynamicForm.get("priormonthsum1");
		String totalpriorcurrentmonths1 = dynamicForm.get("totalpriormonths1");
		String totalpriorcurrentmonths2 = dynamicForm.get("totalpriormonths2");
		Logger.debug("priorAddress1-->"+priorAddress1 + "inputMovedIn2 "+inputMovedIn2+ " priorSumMonth1"+priorSumMonth1+" totalpriorcurrentmonths1 "+totalpriorcurrentmonths1+" totalpriorcurrentmonths2"+totalpriorcurrentmonths2);
		
		Map<String,String> thirdPriorAddressDataOfApplicant = new HashMap<String, String>();
		String priorAddress2 = dynamicForm.get("currentAddress3");
		String inputMovedIn3 = dynamicForm.get("movedIn3");
		String priorSumMonth2 = dynamicForm.get("priormonthsum2");
		Logger.debug("\n thirdPriorAddressDataOfApplicant-->"+thirdPriorAddressDataOfApplicant + "priorAddress2  "+priorAddress2+ " inputMovedIn3 "+inputMovedIn3+" priorSumMonth2 "+priorSumMonth2);
		return ok();
	}
}
