//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.23 at 03:36:43 PM MDT 
//


package com.syml.morweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeCustomerAddress">
 *       &lt;sequence>
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}CustomerReference" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}CustomerLiabilityRealEstate" maxOccurs="99999" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}AddressOccupancyOwnerOccupied"/>
 *           &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}AddressOccupancyPartialOwnerOccupied"/>
 *           &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}AddressOccupancyRental"/>
 *           &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}AddressOccupancyTenant"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="fromDate" use="required" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeDate" />
 *       &lt;attribute name="toDate" use="required" type="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeDate" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class CustomerAddressPreviousResidence
    extends TypeCustomerAddress
{


}
