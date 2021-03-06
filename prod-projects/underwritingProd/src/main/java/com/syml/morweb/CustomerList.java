//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.17 at 04:35:20 AM ICT 
//


package com.syml.morweb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}CustomerBorrower"/>
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}Customer" maxOccurs="4" minOccurs="0"/>
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
    "customerBorrower",
    "customer"
})
@XmlRootElement(name = "CustomerList")
public class CustomerList {

    @XmlElement(name = "CustomerBorrower", required = true)
    protected TypeCustomer customerBorrower;
    @XmlElementRef(name = "Customer", namespace = "http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1", type = JAXBElement.class, required = false)
    protected List<JAXBElement<TypeCustomer>> customer;

    /**
     * Gets the value of the customerBorrower property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCustomer }
     *     
     */
    public TypeCustomer getCustomerBorrower() {
        return customerBorrower;
    }

    /**
     * Sets the value of the customerBorrower property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCustomer }
     *     
     */
    public void setCustomerBorrower(TypeCustomer value) {
        this.customerBorrower = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TypeCustomer }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeCustomer }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeCustomer }{@code >}
     * 
     * 
     */
    public List<JAXBElement<TypeCustomer>> getCustomer() {
        if (customer == null) {
            customer = new ArrayList<JAXBElement<TypeCustomer>>();
        }
        return this.customer;
    }

}
