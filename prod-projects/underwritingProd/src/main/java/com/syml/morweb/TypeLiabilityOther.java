//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.23 at 03:36:43 PM MDT 
//


package com.syml.morweb;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for typeLiabilityOther complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeLiabilityOther">
 *   &lt;complexContent>
 *     &lt;extension base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeLiability">
 *       &lt;attribute name="dateEnd" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeDate" />
 *       &lt;attribute name="accountNumber">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeStringNonEmpty">
 *             &lt;maxLength value="30"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="repaymentIndicator">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="monthlyRepayment">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *             &lt;minInclusive value="0"/>
 *             &lt;maxInclusive value="999999.99"/>
 *             &lt;fractionDigits value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="creditLimit">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *             &lt;minInclusive value="0"/>
 *             &lt;maxInclusive value="999999.99"/>
 *             &lt;fractionDigits value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="outstandingBalance">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *             &lt;minInclusive value="0"/>
 *             &lt;maxInclusive value="999999.99"/>
 *             &lt;fractionDigits value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="lenderName" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeStringNonEmpty">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="46"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="creditCardType" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeCreditCardType" />
 *       &lt;attribute name="liabilityType" use="required" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeLiabilityType" />
 *       &lt;attribute name="additionalHolders">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}boolean">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="additionalHoldersDetails">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeStringNonEmpty">
 *             &lt;maxLength value="255"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeLiabilityOther")
public class TypeLiabilityOther
    extends TypeLiability
{

    @XmlAttribute(name = "dateEnd")
    protected XMLGregorianCalendar dateEnd;
    @XmlAttribute(name = "accountNumber")
    protected String accountNumber;
    @XmlAttribute(name = "repaymentIndicator")
    protected Boolean repaymentIndicator;
    @XmlAttribute(name = "monthlyRepayment")
    protected BigDecimal monthlyRepayment;
    @XmlAttribute(name = "creditLimit")
    protected BigDecimal creditLimit;
    @XmlAttribute(name = "outstandingBalance")
    protected BigDecimal outstandingBalance;
    @XmlAttribute(name = "lenderName", required = true)
    protected String lenderName;
    @XmlAttribute(name = "creditCardType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String creditCardType;
    @XmlAttribute(name = "liabilityType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String liabilityType;
    @XmlAttribute(name = "additionalHolders")
    protected Boolean additionalHolders;
    @XmlAttribute(name = "additionalHoldersDetails")
    protected String additionalHoldersDetails;

    /**
     * Gets the value of the dateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets the value of the dateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateEnd(XMLGregorianCalendar value) {
        this.dateEnd = value;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the repaymentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRepaymentIndicator() {
        return repaymentIndicator;
    }

    /**
     * Sets the value of the repaymentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRepaymentIndicator(Boolean value) {
        this.repaymentIndicator = value;
    }

    /**
     * Gets the value of the monthlyRepayment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    /**
     * Sets the value of the monthlyRepayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthlyRepayment(BigDecimal value) {
        this.monthlyRepayment = value;
    }

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditLimit(BigDecimal value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of the outstandingBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    /**
     * Sets the value of the outstandingBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOutstandingBalance(BigDecimal value) {
        this.outstandingBalance = value;
    }

    /**
     * Gets the value of the lenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLenderName() {
        return lenderName;
    }

    /**
     * Sets the value of the lenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLenderName(String value) {
        this.lenderName = value;
    }

    /**
     * Gets the value of the creditCardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardType(String value) {
        this.creditCardType = value;
    }

    /**
     * Gets the value of the liabilityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiabilityType() {
        return liabilityType;
    }

    /**
     * Sets the value of the liabilityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiabilityType(String value) {
        this.liabilityType = value;
    }

    /**
     * Gets the value of the additionalHolders property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdditionalHolders() {
        return additionalHolders;
    }

    /**
     * Sets the value of the additionalHolders property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdditionalHolders(Boolean value) {
        this.additionalHolders = value;
    }

    /**
     * Gets the value of the additionalHoldersDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalHoldersDetails() {
        return additionalHoldersDetails;
    }

    /**
     * Sets the value of the additionalHoldersDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalHoldersDetails(String value) {
        this.additionalHoldersDetails = value;
    }

}
