package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "applicant_record")
public class Applicant {

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id",unique = true)
	public Integer id;

	// applicant_name
	// @ERP( name = "applicant_name" )
	@Column(name = "applicant_name")
	public String applicant_name;

	// applicant_last_name
	// @ERP( name = "applicant_last_name" )
	@Column(name = "applicant_last_name")
	public String applicant_last_name;

	// email_personal
	// @ERP( name = "email_personal" )
	@Column(name = "email_personal", nullable = false)
	public String email_personal;

	@Column(name = "work")
	private String work;

	@Column(name = "home")
	private String home;

	@Column(name = "cell")
	private String cell;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "relationship_status")
	private String relationship_status;
	
	@Column(name = "sin")
	private String sin;
	
	@Column(name = "non_resident")
	private Boolean non_resident;
	
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "applicants")
	private
	List<Opportunity> opportunities=new ArrayList<Opportunity>();
	
	
	@OneToMany(targetEntity=Address.class, mappedBy="applicant", fetch=FetchType.LAZY)
	private List<Address> listOfAddress=new ArrayList<Address>();
	
	@OneToMany(targetEntity=Income.class, mappedBy="applicant", fetch=FetchType.LAZY)
	private List<Income> incomes = new ArrayList<Income>();

	@OneToMany(targetEntity=Assetes.class, mappedBy="applicant", fetch=FetchType.LAZY)
	private List<Assetes> assetList=new ArrayList<Assetes>();
	
	@OneToMany(targetEntity=Property.class, mappedBy="applicant", fetch=FetchType.LAZY)
	private List<Property> properties = new ArrayList<Property>();
	
	@OneToMany(targetEntity=Mortgages.class, mappedBy="applicant", fetch=FetchType.LAZY)
	private List<Mortgages> listOfMortgages = new ArrayList<Mortgages>();
	/*-------------------- FIELDS THAT ARE NOT IN DB ----------------------*/
	
	@Transient
	private String newToCannada;
	
	@Transient
	private String dependant;
	@Transient
	private String birthDate;
	
	@Transient
	private String electronicSign;
	@Transient
	private String normalSign;
	
	
	@Transient
	private String additionalReason;
	
	@Transient
	private String descOtherIncome;

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getAdditionalReason() {
		return additionalReason;
	}

	public void setAdditionalReason(String additionalReason) {
		this.additionalReason = additionalReason;
	}

	public String getElectronicSign() {
		return electronicSign;
	}

	public void setElectronicSign(String electronicSign) {
		this.electronicSign = electronicSign;
	}

	public String getNormalSign() {
		return normalSign;
	}

	public void setNormalSign(String normalSign) {
		this.normalSign = normalSign;
	}
	public Applicant() {
		// TODO Auto-generated constructor stub
	}

	public Applicant(Integer id, String applicant_name,
			String applicant_last_name, String email_personal) {
		super();
		this.id = id;
		this.applicant_name = applicant_name;
		this.applicant_last_name = applicant_last_name;
		this.email_personal = email_personal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public String getApplicant_last_name() {
		return applicant_last_name;
	}

	public void setApplicant_last_name(String applicant_last_name) {
		this.applicant_last_name = applicant_last_name;
	}

	public String getEmail_personal() {
		return email_personal;
	}

	public void setEmail_personal(String email_personal) {
		this.email_personal = email_personal;
	}

	

	@Override
	public String toString() {
		return "Applicant [id=" + id + ", applicant_name=" + applicant_name
				+ ", applicant_last_name=" + applicant_last_name
				+ ", email_personal=" + email_personal + ", work=" + work
				+ ", home=" + home + ", cell=" + cell + ", dob=" + dob
				+ ", relationship_status=" + relationship_status + ", sin="
				+ sin + ", non_resident=" + non_resident + ", opportunities="
				+ opportunities + ", listOfAddress=" + listOfAddress
				+ ", incomes=" + incomes + ", assetList=" + assetList
				+ ", properties=" + properties + ", listOfMortgages="
				+ listOfMortgages + ", movedToCannada=" + newToCannada
				+ ", dependant=" + dependant + ", electronicSign="
				+ electronicSign + ", normalSign=" + normalSign
				+ ", additionalReason=" + additionalReason + "]";
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRelationship_status() {
		return relationship_status;
	}

	public void setRelationship_status(String relationship_status) {
		this.relationship_status = relationship_status;
	}

	public String getNewToCannada() {
		return newToCannada;
	}

	public void setNewToCannada(String newToCannada) {
		this.newToCannada = newToCannada;
	}

	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public Boolean getNon_resident() {
		return non_resident;
	}

	public void setNon_resident(Boolean non_resident) {
		this.non_resident = non_resident;
	}

	public List<Address> getListOfAddress() {
		return listOfAddress;
	}

	public void setListOfAddress(List<Address> listOfAddress) {
		this.listOfAddress = listOfAddress;
	}

	public List<Income> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}

	public List<Assetes> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<Assetes> assetList) {
		this.assetList = assetList;
	}

	public String getDependant() {
		return dependant;
	}

	public void setDependant(String dependant) {
		this.dependant = dependant;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public List<Opportunity> getOpportunities() {
		return opportunities;
	}

	public void setOpportunities(List<Opportunity> opportunities) {
		this.opportunities = opportunities;
	}

	public List<Mortgages> getListOfMortgages() {
		return listOfMortgages;
	}

	public void setListOfMortgages(List<Mortgages> listOfMortgages) {
		this.listOfMortgages = listOfMortgages;
	}

	public String getDescOtherIncome() {
		return descOtherIncome;
	}

	public void setDescOtherIncome(String descOtherIncome) {
		this.descOtherIncome = descOtherIncome;
	}
	
}
