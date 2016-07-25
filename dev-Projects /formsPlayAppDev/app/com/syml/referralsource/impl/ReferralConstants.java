package com.syml.referralsource.impl;

public class ReferralConstants {
	
	public static final String REFERRAL_SOURCE_FORM="Visdom_Referral";

	public static final String REFERRAL_SOURCE_FORM_COUHBASE_KEY="Visdom_Referral_";
	public static final int REFERAL_INVOLVED_STAGE_ID=6;  //dev=6 //prod=14
	public static final String REFERRAL_AGREEMENT_COUCHBASE_KEY=	"doc_ReferralAgreemetfile_";
	public static final String REFERRAL_TRIGGER_DATA_COUCHBASE_KEY="ReferralTrigerData_";
	public static final String REFERRAL_PDF_DATA_CONFIG_PROPERTIES_FILE="visdomReferral.properties";
	public static final String REFERRAL_AGREEMENT_NAME="ReferralAgreement_";
	public static final String REFERRAL_AGREMENT_FILE_EXTENTION=".pdf";
	public static final String CLIENT="Client";
	
	public static final String REALTOR="Realtor";
	public static final String FINANCIAL_PLANNER="Financial Planner";
	public static final String PROFESSIONAL="Professional";
	
	public static final String COUCHBASE_ERROR="RS1";
	public static final String POSTGRES_ERROR="RS4";
	public static final String MAIL_ERROR="RS7";
	public static final String PDF_GENERATION_ERROR="RS5";
	
	
}
