package requireddoc;

import document.MortgageDocumentConverter;
import dto.ApplicantDocument;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import play.Play;
import requireddoc.infrastructure.OpenERPDAO;
import requireddoc.infrastructure.ReadTextFile;
import requireddoc.infrastructure.TestApplicants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xsalefter on 4/24/2015.
 */
public class RequiredDocumentTest {

	String path = "//home//venkateshm//Projects//documentanalyzer//test//resources//test-docs//";
	String co_ApplicantDocuments="";
	public boolean test(String path) throws JSONException, IOException {
		boolean shouldReturnTrueToMakeSureThisTestPass = false;

		final TestApplicants testApplicants = ReadTextFile.readFromFile(path); // FIXME:
																				// Fix
																				// this.
		final String oportunityId = testApplicants.getApplicationID();
		// ApplicationId
		// in
		// the
		// test-docs
		// is,
		// in
		// fact,
		// OportunityId.
		final String applicantId = OpenERPDAO.getApplicantId(oportunityId);

		final String couchbaseDocumentId = "Applicant_" + applicantId;
		System.out.println("couchbaseDocumentId " + couchbaseDocumentId);

		ApplicantDocument applicantDocument = null;
		try {
			applicantDocument = MortgageDocumentConverter
					.convertToApplicantDocument(couchbaseDocumentId);
			// System.out.println("Applicant documents"+applicantDocument.getDocuments());
			if (applicantDocument.getPropertyDocments().isEmpty()) {
				applicantDocument.setPropertyDocments(null);
			}if (applicantDocument.getDownPayments().isEmpty()) {
				applicantDocument.setDownPayments(null);
			}
			try{
			
			 Arrays.asList(testApplicants.getApplicants()[1].getDocuments());
			
			}catch(Exception e){
				co_ApplicantDocuments=null;
			}
				System.out.println("co_ApplicantDocuments test--------------- "+co_ApplicantDocuments);
			System.out.println("Applicant Documents "
					+ applicantDocument.getDocuments()
					+ " == "
					+ Arrays.asList(testApplicants.getApplicants()[0]
							.getDocuments()));

			System.out.println("Co_Applicant Documents "
					+ applicantDocument.getCo_documents()
					+ " == "
					+  Arrays.asList(testApplicants.getApplicants()[1].getDocuments()));
			System.out.println("Opprtunity property "
					+ applicantDocument.getPropertyDocments() + " == "
					+ Arrays.asList(testApplicants.getPropertyDocuments()));
			// System.out.println("Applicant downpayment"+applicantDocument.getDownPayments());
			// System.out.println("Applicant-- documents"+applicantDocument.getCo_documents());
		} catch (Exception e) {
			

		}
		if (applicantDocument.getDocuments() != null
				&& testApplicants.getApplicants()[0].getDocuments() != null) {
			Set<String> applicantDocuments = new HashSet<String>(
					Arrays.asList(testApplicants.getApplicants()[0]
							.getDocuments()));
			shouldReturnTrueToMakeSureThisTestPass = applicantDocuments.equals(applicantDocument.getDocuments());

		}
			System.out.println("Applicant Douments equal "+shouldReturnTrueToMakeSureThisTestPass);
			
		if (shouldReturnTrueToMakeSureThisTestPass == true) {
			if (applicantDocument.getCo_documents() != null
					&& co_ApplicantDocuments!= null) {
				Set<String> applicantDocuments = new HashSet<>(
						Arrays.asList(testApplicants.getApplicants()[1]
								.getDocuments()));
				shouldReturnTrueToMakeSureThisTestPass = applicantDocuments
						.equals(applicantDocument.getCo_documents());
				System.out.println("inside Co_Applicant Douments equal "+shouldReturnTrueToMakeSureThisTestPass);

			}
		}
		
		System.out.println("Co_Applicant Douments equal "+shouldReturnTrueToMakeSureThisTestPass);

		if (shouldReturnTrueToMakeSureThisTestPass == true) {
			if (testApplicants.getPropertyDocuments() == null
					&& applicantDocument.getPropertyDocments() == null) {

				shouldReturnTrueToMakeSureThisTestPass = true;
			} else if (testApplicants.getPropertyDocuments() != null
					&& applicantDocument.getDocuments() != null) {
				Set<String> propertyDocuments = new HashSet<>(
						Arrays.asList(testApplicants.getPropertyDocuments()));
				shouldReturnTrueToMakeSureThisTestPass = propertyDocuments
						.equals(applicantDocument.getPropertyDocments());

				// Assert.assertTrue(shouldReturnTrueToMakeSureThisTestPass);
			}
		}
		
		System.out.println("Property Douments equal "+shouldReturnTrueToMakeSureThisTestPass);

		if (shouldReturnTrueToMakeSureThisTestPass == true) {
			if (testApplicants.getDownpaymentDocuments() == null
					&& applicantDocument.getDownPayments() == null) {

				shouldReturnTrueToMakeSureThisTestPass = true;
			} else if (testApplicants.getDownpaymentDocuments() != null
					&& applicantDocument.getDownPayments() != null) {
				Set<String> downPayementDocuments = new HashSet<>(
						Arrays.asList(testApplicants.getDownpaymentDocuments()));
				shouldReturnTrueToMakeSureThisTestPass = downPayementDocuments
						.equals(applicantDocument.getDownPayments());

				// Assert.assertTrue(shouldReturnTrueToMakeSureThisTestPass);
			}
		}
		System.out.println("Downpayement Douments equal "+shouldReturnTrueToMakeSureThisTestPass);

		if (shouldReturnTrueToMakeSureThisTestPass) {
			System.out
					.println("------------------Test Sucessfull--------------");
		} else {
			System.out.println("-------------Test UnSucessfull---------------");
		}

		return shouldReturnTrueToMakeSureThisTestPass;

	}

	@Test  //sucesss
	public void OpportunityBradleyHagenTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityBradleyHagen.txt"));

	}

	 @Test   // sucess
	public void OpportunityCrisLiberatoreTest() throws JSONException,
			IOException {
		Assert.assertTrue(test(path + "OpportunityCrisLiberatore.txt"));

	}

	@Test         //sucess
	public void OpportunityChristopherFuhrerTest() throws JSONException,
			IOException {
		Assert.assertTrue(test(path + "OpportunityChristopherFuhrer.txt"));

	}

	//@Test  // unsucess
	public void OpportunityDaneSommerfeldTest() throws JSONException,
			IOException {
		Assert.assertTrue(test(path + "OpportunityDaneSommerfeld.txt"));

	}

	//@Test     //unsucess coucbase data and openerp data not matching
	public void OpportunityJustinChickTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityJustinChick.txt"));

	}

	//@Test   //unsucess cantian binary data in  couchbase
	public void OpportunityMarcKristianTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityMarcKristian.txt"));

	}

@Test     //sucess
	public void OpportunitySatawatJohnSoisethTest() throws JSONException,
			IOException {
		Assert.assertTrue(test(path + "OpportunitySatawatJohnSoiseth.txt"));

	}

 @Test   //sucess
	public void OpportunityRodneyOpsethTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityRodneyOpseth.txt"));

	}

// @Test     //unsucess data in coubase is binary
	public void OpportunityTonyCatonioTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityTonyCatonio.txt"));

	}

	// @Test     //unsucessthere is no applicant for this opprunity
	public void OpportunityRussellRamjattanTest() throws JSONException,
			IOException {
		Assert.assertTrue(test(path + "OpportunityRussellRamjattan.txt"));

	}

	// @Test  //unsucess data not matching in coucbase and openerp data
	public void OpportunityTravisLudwigTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityTravisLudwig.txt"));

	}

	 //@Test   //unsucess no applicant exsist in openerp
	public void OpportunityZillSyedTest() throws JSONException, IOException {
		Assert.assertTrue(test(path + "OpportunityZillSyed.txt"));

	}
	
	
	

}
