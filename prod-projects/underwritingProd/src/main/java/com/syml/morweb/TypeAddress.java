//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.23 at 03:36:43 PM MDT 
//


package com.syml.morweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeAddress">
 *   &lt;complexContent>
 *     &lt;extension base="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}typeMunicipalAddress">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeAddress")
@XmlSeeAlso({
    TypeAddressCanada.class,
    TypeAddressUSA.class,
    TypeAddressOther.class
})
public abstract class TypeAddress
    extends TypeMunicipalAddress
{


}
