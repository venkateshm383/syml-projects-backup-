//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.23 at 03:36:43 PM MDT 
//


package com.syml.morweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *     &lt;extension base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeAddressOccupancy">
 *       &lt;sequence>
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}RentalDetails" minOccurs="0"/>
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
    "rentalDetails"
})
@XmlRootElement(name = "SubjectPropertyOccupancyPartialOwnerOccupied")
public class SubjectPropertyOccupancyPartialOwnerOccupied
    extends TypeAddressOccupancy
{

    @XmlElement(name = "RentalDetails")
    protected RentalDetails rentalDetails;

    /**
     * Gets the value of the rentalDetails property.
     * 
     * @return
     *     possible object is
     *     {@link RentalDetails }
     *     
     */
    public RentalDetails getRentalDetails() {
        return rentalDetails;
    }

    /**
     * Sets the value of the rentalDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link RentalDetails }
     *     
     */
    public void setRentalDetails(RentalDetails value) {
        this.rentalDetails = value;
    }

}