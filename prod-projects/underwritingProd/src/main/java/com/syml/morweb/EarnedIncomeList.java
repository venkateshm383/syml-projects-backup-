//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.23 at 03:36:43 PM MDT 
//


package com.syml.morweb;

import java.util.ArrayList;
import java.util.List;
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
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1}EarnedIncome" maxOccurs="99999" minOccurs="0"/>
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
    "earnedIncome"
})
@XmlRootElement(name = "EarnedIncomeList")
public class EarnedIncomeList {

    @XmlElement(name = "EarnedIncome")
    protected List<EarnedIncome> earnedIncome;

    /**
     * Gets the value of the earnedIncome property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the earnedIncome property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEarnedIncome().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EarnedIncome }
     * 
     * 
     */
    public List<EarnedIncome> getEarnedIncome() {
        if (earnedIncome == null) {
            earnedIncome = new ArrayList<EarnedIncome>();
        }
        return this.earnedIncome;
    }

}