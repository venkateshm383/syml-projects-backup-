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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeAddressOccupancyPartialOwnerOccupied complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeAddressOccupancyPartialOwnerOccupied">
 *   &lt;complexContent>
 *     &lt;extension base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeAddressOccupancy">
 *       &lt;sequence>
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}RentalDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="refkeyAsset" use="required" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeReferenceKey" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeAddressOccupancyPartialOwnerOccupied", propOrder = {
    "rentalDetails"
})
public class TypeAddressOccupancyPartialOwnerOccupied
    extends TypeAddressOccupancy
{

    @XmlElement(name = "RentalDetails")
    protected RentalDetails rentalDetails;
    @XmlAttribute(name = "refkeyAsset", required = true)
    protected String refkeyAsset;

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

    /**
     * Gets the value of the refkeyAsset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefkeyAsset() {
        return refkeyAsset;
    }

    /**
     * Sets the value of the refkeyAsset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefkeyAsset(String value) {
        this.refkeyAsset = value;
    }

}
