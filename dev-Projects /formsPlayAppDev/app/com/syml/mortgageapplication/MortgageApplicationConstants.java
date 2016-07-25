package com.syml.mortgageapplication;

public class MortgageApplicationConstants {
public static final String MORTGAGE_APPLICATION_COUCHBASE_KEY="OpportunityFormData_";
public static final String MORTGAGE_APPLICATION_DISCLOSURE_KEY="doc_Disclosures_";
public static final int OPPORTUNITY_PARTIAL_STAGE_ID=10; //dev=10  prod=14
public static final int OPPORTUNITY_COMPLETED_STAGE_ID=11;//dev=11  prod=15

public static final String DISCLOSER_CONFIGURATION_FILE_NAME="applicant.properties";
public static final String PURCHASE_LENDING_GOAL="Purchase";
public static final String PRE_APPROVAL_LENDING_GOAL="PreApproval";
public static final String REFINANCE_LENDING_GOAL="Refinance";

public static final String PURCHASE_LENDING_GOAL_ID="2";
public static final String PRE_APPROVAL_LENDING_GOAL_ID="1";
public static final String REFINANCE_LENDING_GOAL_ID="3";



public static final String TYPE="opportunity";

public static final String LIVING_IN_PROPERTY_OWNER_AND_RENTER="OwnerAndRenter";
public static final String LIVING_IN_PROPERTY_OWNER_MYSELF="Owner (Myself)";
public static final String LIVING_IN_PROPERTY_RENTER="Renter";

public static final String MLS_MLSLISTED="MLSListed";
public static final String MLS_NEWBUILD="NewBuild";
public static final String MLS_PRIVATE_SALE="PrivateSale";

public static final int HR_DEPARTMENT_ID=1; //dev =1, // prod=1

public static final int USER_ID=26;  //dev=26 ,prod= 1

public static final String EMPLOYEE="Employed";
public static final String EMPLOYEE_ID="1";
public static final String SLEF_EMPLOYEE="SlefEmployed";
public static final String SLEF_EMPLOYEE_ID="2";
public static final String INVESTMENTS="INVESTMENTS";
public static final String INVESTMENTS_ID="13";  /**other not exist  in OpenErp */
public static final String LIVING_ALLOWENCE="LIVING_ALLOWENCE";
public static final String LIVING_ALLOWENCE_ID="11";

public static final String BONUS="bonus";
public static final String BONUS_ID="8";
public static final String COMMISION_ID="4";
public static final String COMMISION="COMMISION";
public static final String VEHICAL_ALLOW="VEHICAL_ALLOWNCE";
public static final String VEHICAL_ALLOW_ID="12";
public static final String RETIREMENT="RETIREMENT";
public static final String RETIREMENT_ID="3";

public static final String OTHER="OTHER";
public static final String OTHER_ID="13";
public static final String METERNITY="METERNITY";
public static final String METERNITY_ID="14";




public static final String COUCHBASE_CONNECTION_ERROR_CODE="MA1";
public static final String COUCHBASE_FETCH_DATA_ERROR_CODE="MA2";
public static final String POSTGRESS_ERROR="MA3";
public static final String COUCHBASE_STORE_DATA_ERROR="MA4";
public static final String MAIL_ERROR="MA4";
public static final String TASK_CREATION_ERROR="MA5";


}
