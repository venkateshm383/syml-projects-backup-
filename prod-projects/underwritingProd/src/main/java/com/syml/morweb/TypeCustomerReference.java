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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCustomerReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeCustomerReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="refkeyCustomer" use="required" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeReferenceKey" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeCustomerReference")
public class TypeCustomerReference {

    @XmlAttribute(name = "refkeyCustomer", required = true)
    protected String refkeyCustomer;

    /**
     * Gets the value of the refkeyCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefkeyCustomer() {
        return refkeyCustomer;
    }

    /**
     * Sets the value of the refkeyCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefkeyCustomer(String value) {
        this.refkeyCustomer = value;
    }

}
