package controllers;

public class PersonalInfo {
	//Didn't used
	private String applicantName = "";
	private String additionalApplicant = "";
	
	private String mobilePhone="";
	private String workPhone="";
	private String homePhone="";
	private String birthDay="";
	private String socialInsurance="";
	private String relationStatus="";
	private String dependents="";
	private String areUCanadianRes="";
	private String movedCanada="";
	
	private String coApplicantName = "";
	private String coMobilePhone="";
	private String coWorkPhone="";
	private String coHomePhone="";
	private String coBirthDay="";
	private String coSocialInsurance="";
	private String coRelationStatus="";
	private String coDependents="";
	private String coAreUCanadianRes="";
	private String coMovedCanada="";
	
	//No Arg Constructor
	public PersonalInfo() {
		// TODO Auto-generated constructor stub
	}

	//Argumented Constructor
	public PersonalInfo(String applicantName,String coApplicantName,String additionalApplicant, String mobilePhone,
			String workPhone, String homePhone, String birthDay,
			String socialInsurance, String relationStatus, String dependents,
			String areUCanadianRes, String movedCanada, String coMobilePhone,
			String coWorkPhone, String coHomePhone, String coBirthDay,
			String coSocialInsurance, String coRelationStatus,
			String coDependents, String coAreUCanadianRes, String coMovedCanada) {
		super();
		this.setApplicantName(applicantName);
		this.setCoApplicantName(coApplicantName);
		this.additionalApplicant = additionalApplicant;
		this.mobilePhone = mobilePhone;
		this.workPhone = workPhone;
		this.homePhone = homePhone;
		this.birthDay = birthDay;
		this.socialInsurance = socialInsurance;
		this.relationStatus = relationStatus;
		this.dependents = dependents;
		this.areUCanadianRes = areUCanadianRes;
		this.movedCanada = movedCanada;
		this.coMobilePhone = coMobilePhone;
		this.coWorkPhone = coWorkPhone;
		this.coHomePhone = coHomePhone;
		this.coBirthDay = coBirthDay;
		this.coSocialInsurance = coSocialInsurance;
		this.coRelationStatus = coRelationStatus;
		this.coDependents = coDependents;
		this.coAreUCanadianRes = coAreUCanadianRes;
		this.coMovedCanada = coMovedCanada;
	}

	//Getters and Setters
	public String getAdditionalApplicant() {
		return additionalApplicant;
	}

	public void setAdditionalApplicant(String additionalApplicant) {
		this.additionalApplicant = additionalApplicant;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getSocialInsurance() {
		return socialInsurance;
	}

	public void setSocialInsurance(String socialInsurance) {
		this.socialInsurance = socialInsurance;
	}

	public String getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(String relationStatus) {
		this.relationStatus = relationStatus;
	}

	public String getDependents() {
		return dependents;
	}

	public void setDependents(String dependents) {
		this.dependents = dependents;
	}

	public String getAreUCanadianRes() {
		return areUCanadianRes;
	}

	public void setAreUCanadianRes(String areUCanadianRes) {
		this.areUCanadianRes = areUCanadianRes;
	}

	public String getMovedCanada() {
		return movedCanada;
	}

	public void setMovedCanada(String movedCanada) {
		this.movedCanada = movedCanada;
	}

	public String getCoMobilePhone() {
		return coMobilePhone;
	}

	public void setCoMobilePhone(String coMobilePhone) {
		this.coMobilePhone = coMobilePhone;
	}

	public String getCoWorkPhone() {
		return coWorkPhone;
	}

	public void setCoWorkPhone(String coWorkPhone) {
		this.coWorkPhone = coWorkPhone;
	}

	public String getCoHomePhone() {
		return coHomePhone;
	}

	public void setCoHomePhone(String coHomePhone) {
		this.coHomePhone = coHomePhone;
	}

	public String getCoBirthDay() {
		return coBirthDay;
	}

	public void setCoBirthDay(String coBirthDay) {
		this.coBirthDay = coBirthDay;
	}

	public String getCoSocialInsurance() {
		return coSocialInsurance;
	}

	public void setCoSocialInsurance(String coSocialInsurance) {
		this.coSocialInsurance = coSocialInsurance;
	}

	public String getCoRelationStatus() {
		return coRelationStatus;
	}

	public void setCoRelationStatus(String coRelationStatus) {
		this.coRelationStatus = coRelationStatus;
	}

	public String getCoDependents() {
		return coDependents;
	}

	public void setCoDependents(String coDependents) {
		this.coDependents = coDependents;
	}

	public String getCoAreUCanadianRes() {
		return coAreUCanadianRes;
	}

	public void setCoAreUCanadianRes(String coAreUCanadianRes) {
		this.coAreUCanadianRes = coAreUCanadianRes;
	}

	public String getCoMovedCanada() {
		return coMovedCanada;
	}

	public void setCoMovedCanada(String coMovedCanada) {
		this.coMovedCanada = coMovedCanada;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getCoApplicantName() {
		return coApplicantName;
	}

	public void setCoApplicantName(String coApplicantName) {
		this.coApplicantName = coApplicantName;
	}
	
}
