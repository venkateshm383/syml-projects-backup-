//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.20 at 04:03:32 PM MST 
//


package com.syml.filogix;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.filogix.com/Schema/FCXAPI/1}BasePersonType">
 *       &lt;sequence>
 *         &lt;element name="addressList" type="{http://www.filogix.com/Schema/FCXAPI/1}QualifiedAddressType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonType", propOrder = {
    "addressList"
})
public class PersonType
    extends BasePersonType
{

    protected List<QualifiedAddressType> addressList;

    /**
     * Gets the value of the addressList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualifiedAddressType }
     * 
     * 
     */
    public List<QualifiedAddressType> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<QualifiedAddressType>();
        }
        return this.addressList;
    }

}