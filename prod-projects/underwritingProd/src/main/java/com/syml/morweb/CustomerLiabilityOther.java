//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.23 at 03:36:43 PM MDT 
//


package com.syml.morweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}CustomerReference"/>
 *       &lt;/sequence>
 *       &lt;attribute name="refkeyLiability" use="required" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeReferenceKey" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "customerReference"
})
@XmlRootElement(name = "CustomerLiabilityOther")
public class CustomerLiabilityOther {

    @XmlElement(name = "CustomerReference", required = true)
    protected TypeCustomerReference customerReference;
    @XmlAttribute(name = "refkeyLiability", required = true)
    protected String refkeyLiability;

    /**
     * Gets the value of the customerReference property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCustomerReference }
     *     
     */
    public TypeCustomerReference getCustomerReference() {
        return customerReference;
    }

    /**
     * Sets the value of the customerReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCustomerReference }
     *     
     */
    public void setCustomerReference(TypeCustomerReference value) {
        this.customerReference = value;
    }

    /**
     * Gets the value of the refkeyLiability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefkeyLiability() {
        return refkeyLiability;
    }

    /**
     * Sets the value of the refkeyLiability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefkeyLiability(String value) {
        this.refkeyLiability = value;
    }

}
