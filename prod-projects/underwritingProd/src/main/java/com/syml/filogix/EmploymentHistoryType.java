//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.20 at 04:03:32 PM MST 
//


package com.syml.filogix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmploymentHistoryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmploymentHistoryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contact" type="{http://www.filogix.com/Schema/FCXAPI/1}ContactTypeAddresstype2" minOccurs="0"/>
 *         &lt;element name="employerName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="employmentHistoryStatusDd">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="2"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;minExclusive value="-1"/>
 *               &lt;maxExclusive value="100"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="employmentHistoryTypeDd" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="13"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;minExclusive value="-1"/>
 *               &lt;maxExclusive value="10000000000000"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="income" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.filogix.com/Schema/FCXAPI/1}IncomeType">
 *                 &lt;sequence>
 *                   &lt;element name="incomeTypeDd" type="{http://www.filogix.com/Schema/FCXAPI/1}incomeTypeEmploymentDd"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="industrySectorDd" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="2"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;minExclusive value="-1"/>
 *               &lt;maxExclusive value="100"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *               &lt;enumeration value="7"/>
 *               &lt;enumeration value="8"/>
 *               &lt;enumeration value="9"/>
 *               &lt;enumeration value="10"/>
 *               &lt;enumeration value="11"/>
 *               &lt;enumeration value="12"/>
 *               &lt;enumeration value="13"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="jobTitle" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="monthsOfService" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;totalDigits value="3"/>
 *               &lt;maxExclusive value="1000"/>
 *               &lt;minExclusive value="-1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="occupationDd" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="2"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;minExclusive value="-1"/>
 *               &lt;maxExclusive value="100"/>
 *               &lt;enumeration value="0"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="selfEmploymentDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="companyType" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="35"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="grossRevenue" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;totalDigits value="13"/>
 *                         &lt;fractionDigits value="2"/>
 *                         &lt;minExclusive value="-1"/>
 *                         &lt;maxExclusive value="100000000000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="operatingAs" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="35"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="timeInIndustry" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;totalDigits value="3"/>
 *               &lt;minExclusive value="-1"/>
 *               &lt;maxExclusive value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="additionalData" type="{http://www.filogix.com/Schema/FCXAPI/1}AdditionalDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmploymentHistoryType", propOrder = {
    "contact",
    "employerName",
    "employmentHistoryStatusDd",
    "employmentHistoryTypeDd",
    "income",
    "industrySectorDd",
    "jobTitle",
    "monthsOfService",
    "occupationDd",
    "selfEmploymentDetails",
    "timeInIndustry",
    "additionalData"
})
public class EmploymentHistoryType {

    protected ContactTypeAddresstype2 contact;
    protected String employerName;
    @XmlElement(required = true)
    protected BigDecimal employmentHistoryStatusDd;
    protected BigDecimal employmentHistoryTypeDd;
    protected EmploymentHistoryType.Income income;
    protected BigDecimal industrySectorDd;
    protected String jobTitle;
    protected Integer monthsOfService;
    protected BigDecimal occupationDd;
    protected EmploymentHistoryType.SelfEmploymentDetails selfEmploymentDetails;
    protected Integer timeInIndustry;
    protected List<AdditionalDataType> additionalData;

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactTypeAddresstype2 }
     *     
     */
    public ContactTypeAddresstype2 getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactTypeAddresstype2 }
     *     
     */
    public void setContact(ContactTypeAddresstype2 value) {
        this.contact = value;
    }

    /**
     * Gets the value of the employerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployerName() {
        return employerName;
    }

    /**
     * Sets the value of the employerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployerName(String value) {
        this.employerName = value;
    }

    /**
     * Gets the value of the employmentHistoryStatusDd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEmploymentHistoryStatusDd() {
        return employmentHistoryStatusDd;
    }

    /**
     * Sets the value of the employmentHistoryStatusDd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEmploymentHistoryStatusDd(BigDecimal value) {
        this.employmentHistoryStatusDd = value;
    }

    /**
     * Gets the value of the employmentHistoryTypeDd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEmploymentHistoryTypeDd() {
        return employmentHistoryTypeDd;
    }

    /**
     * Sets the value of the employmentHistoryTypeDd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEmploymentHistoryTypeDd(BigDecimal value) {
        this.employmentHistoryTypeDd = value;
    }

    /**
     * Gets the value of the income property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentHistoryType.Income }
     *     
     */
    public EmploymentHistoryType.Income getIncome() {
        return income;
    }

    /**
     * Sets the value of the income property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentHistoryType.Income }
     *     
     */
    public void setIncome(EmploymentHistoryType.Income value) {
        this.income = value;
    }

    /**
     * Gets the value of the industrySectorDd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIndustrySectorDd() {
        return industrySectorDd;
    }

    /**
     * Sets the value of the industrySectorDd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIndustrySectorDd(BigDecimal value) {
        this.industrySectorDd = value;
    }

    /**
     * Gets the value of the jobTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the value of the jobTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    /**
     * Gets the value of the monthsOfService property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMonthsOfService() {
        return monthsOfService;
    }

    /**
     * Sets the value of the monthsOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMonthsOfService(Integer value) {
        this.monthsOfService = value;
    }

    /**
     * Gets the value of the occupationDd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOccupationDd() {
        return occupationDd;
    }

    /**
     * Sets the value of the occupationDd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOccupationDd(BigDecimal value) {
        this.occupationDd = value;
    }

    /**
     * Gets the value of the selfEmploymentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentHistoryType.SelfEmploymentDetails }
     *     
     */
    public EmploymentHistoryType.SelfEmploymentDetails getSelfEmploymentDetails() {
        return selfEmploymentDetails;
    }

    /**
     * Sets the value of the selfEmploymentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentHistoryType.SelfEmploymentDetails }
     *     
     */
    public void setSelfEmploymentDetails(EmploymentHistoryType.SelfEmploymentDetails value) {
        this.selfEmploymentDetails = value;
    }

    /**
     * Gets the value of the timeInIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeInIndustry() {
        return timeInIndustry;
    }

    /**
     * Sets the value of the timeInIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeInIndustry(Integer value) {
        this.timeInIndustry = value;
    }

    /**
     * Gets the value of the additionalData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalDataType }
     * 
     * 
     */
    public List<AdditionalDataType> getAdditionalData() {
        if (additionalData == null) {
            additionalData = new ArrayList<AdditionalDataType>();
        }
        return this.additionalData;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.filogix.com/Schema/FCXAPI/1}IncomeType">
     *       &lt;sequence>
     *         &lt;element name="incomeTypeDd" type="{http://www.filogix.com/Schema/FCXAPI/1}incomeTypeEmploymentDd"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "incomeTypeDd"
    })
    public static class Income
        extends IncomeType
    {

        @XmlElement(required = true)
        protected BigDecimal incomeTypeDd;

        /**
         * Gets the value of the incomeTypeDd property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getIncomeTypeDd() {
            return incomeTypeDd;
        }

        /**
         * Sets the value of the incomeTypeDd property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setIncomeTypeDd(BigDecimal value) {
            this.incomeTypeDd = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="companyType" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="35"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="grossRevenue" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;totalDigits value="13"/>
     *               &lt;fractionDigits value="2"/>
     *               &lt;minExclusive value="-1"/>
     *               &lt;maxExclusive value="100000000000"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="operatingAs" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="35"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "companyType",
        "grossRevenue",
        "operatingAs"
    })
    public static class SelfEmploymentDetails {

        protected String companyType;
        protected BigDecimal grossRevenue;
        protected String operatingAs;

        /**
         * Gets the value of the companyType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCompanyType() {
            return companyType;
        }

        /**
         * Sets the value of the companyType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCompanyType(String value) {
            this.companyType = value;
        }

        /**
         * Gets the value of the grossRevenue property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getGrossRevenue() {
            return grossRevenue;
        }

        /**
         * Sets the value of the grossRevenue property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setGrossRevenue(BigDecimal value) {
            this.grossRevenue = value;
        }

        /**
         * Gets the value of the operatingAs property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOperatingAs() {
            return operatingAs;
        }

        /**
         * Sets the value of the operatingAs property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOperatingAs(String value) {
            this.operatingAs = value;
        }

    }

}
