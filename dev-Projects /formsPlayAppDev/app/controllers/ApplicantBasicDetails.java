package controllers;

public class ApplicantBasicDetails {
	private int progressStatus=0;
	private String crmLeadId = "";
	private String referralEmail = "";
	private String referralName = "";

	private String firstName = "";
	private String lastName = "";
	private String email = "";

	private String coAppFirstName = "";
	private String coAppLastName = "";
	private String coAppEmail = "";

	private String additionalApplicant = "";
	private String currentLendingGoal = "";
	
	private String applicantId;
	private String coApplicantId;

	private String mlsListed;
	
	// No Arg Constructor
	public ApplicantBasicDetails() {
		// TODO Auto-generated constructor stub
	}

	// Argumented Constructor
	public ApplicantBasicDetails(int progressStatus,String crmLeadId, String referralEmail,
			String referralName, String firstName, String lastName,
			String email, String coAppFirstName, String coAppLastName,
			String coAppEmail, String additionalApplicant,
			String currentLendingGoal,String applicantId,String coApplicantId,String mlsListed) {
		super();
		this.progressStatus = progressStatus;
		this.crmLeadId = crmLeadId;
		this.referralEmail = referralEmail;
		this.referralName = referralName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.coAppFirstName = coAppFirstName;
		this.coAppLastName = coAppLastName;
		this.coAppEmail = coAppEmail;
		this.additionalApplicant = additionalApplicant;
		this.currentLendingGoal = currentLendingGoal;
		this.applicantId = applicantId;
		this.coApplicantId=
		this.setMlsListed(mlsListed);;
	}

	// Getters And Setters
	public String getCrmLeadId() {
		return crmLeadId;
	}

	public void setCrmLeadId(String crmLeadId) {
		this.crmLeadId = crmLeadId;
	}

	public String getReferralEmail() {
		return referralEmail;
	}

	public void setReferralEmail(String referralEmail) {
		this.referralEmail = referralEmail;
	}

	public String getReferralName() {
		return referralName;
	}

	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCoAppFirstName() {
		return coAppFirstName;
	}

	public void setCoAppFirstName(String coAppFirstName) {
		this.coAppFirstName = coAppFirstName;
	}

	public String getCoAppLastName() {
		return coAppLastName;
	}

	public void setCoAppLastName(String coAppLastName) {
		this.coAppLastName = coAppLastName;
	}

	public String getCoAppEmail() {
		return coAppEmail;
	}

	public void setCoAppEmail(String coAppEmail) {
		this.coAppEmail = coAppEmail;
	}

	public String getAdditionalApplicant() {
		return additionalApplicant;
	}

	public void setAdditionalApplicant(String additionalApplicant) {
		this.additionalApplicant = additionalApplicant;
	}

	public String getCurrentLendingGoal() {
		return currentLendingGoal;
	}

	public void setCurrentLendingGoal(String currentLendingGoal) {
		this.currentLendingGoal = currentLendingGoal;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public int getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(int progressStatus) {
		this.progressStatus = progressStatus;
	}

	public String getCoApplicantId() {
		return coApplicantId;
	}

	public void setCoApplicantId(String coApplicantId) {
		this.coApplicantId = coApplicantId;
	}

	public String getMlsListed() {
		return mlsListed;
	}

	public String setMlsListed(String mlsListed) {
		this.mlsListed = mlsListed;
		return mlsListed;
	}

}
