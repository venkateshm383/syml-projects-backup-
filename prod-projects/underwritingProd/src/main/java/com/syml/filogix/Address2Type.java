//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.20 at 04:03:32 PM MST 
//


package com.syml.filogix;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Street Address summarized into two elements
 * 
 * <p>Java class for Address2Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Address2Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressLine1" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="addressLine2" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="35"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="city" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="provinceDd" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="2"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;minExclusive value="-1"/>
 *               &lt;maxExclusive value="100"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *               &lt;enumeration value="7"/>
 *               &lt;enumeration value="8"/>
 *               &lt;enumeration value="9"/>
 *               &lt;enumeration value="10"/>
 *               &lt;enumeration value="11"/>
 *               &lt;enumeration value="12"/>
 *               &lt;enumeration value="13"/>
 *               &lt;enumeration value="20"/>
 *               &lt;enumeration value="21"/>
 *               &lt;enumeration value="22"/>
 *               &lt;enumeration value="23"/>
 *               &lt;enumeration value="24"/>
 *               &lt;enumeration value="25"/>
 *               &lt;enumeration value="26"/>
 *               &lt;enumeration value="27"/>
 *               &lt;enumeration value="28"/>
 *               &lt;enumeration value="29"/>
 *               &lt;enumeration value="30"/>
 *               &lt;enumeration value="31"/>
 *               &lt;enumeration value="32"/>
 *               &lt;enumeration value="33"/>
 *               &lt;enumeration value="34"/>
 *               &lt;enumeration value="35"/>
 *               &lt;enumeration value="36"/>
 *               &lt;enumeration value="37"/>
 *               &lt;enumeration value="38"/>
 *               &lt;enumeration value="39"/>
 *               &lt;enumeration value="40"/>
 *               &lt;enumeration value="41"/>
 *               &lt;enumeration value="42"/>
 *               &lt;enumeration value="43"/>
 *               &lt;enumeration value="44"/>
 *               &lt;enumeration value="45"/>
 *               &lt;enumeration value="46"/>
 *               &lt;enumeration value="47"/>
 *               &lt;enumeration value="48"/>
 *               &lt;enumeration value="49"/>
 *               &lt;enumeration value="50"/>
 *               &lt;enumeration value="51"/>
 *               &lt;enumeration value="52"/>
 *               &lt;enumeration value="53"/>
 *               &lt;enumeration value="54"/>
 *               &lt;enumeration value="55"/>
 *               &lt;enumeration value="56"/>
 *               &lt;enumeration value="57"/>
 *               &lt;enumeration value="58"/>
 *               &lt;enumeration value="59"/>
 *               &lt;enumeration value="60"/>
 *               &lt;enumeration value="61"/>
 *               &lt;enumeration value="62"/>
 *               &lt;enumeration value="63"/>
 *               &lt;enumeration value="64"/>
 *               &lt;enumeration value="65"/>
 *               &lt;enumeration value="66"/>
 *               &lt;enumeration value="67"/>
 *               &lt;enumeration value="68"/>
 *               &lt;enumeration value="69"/>
 *               &lt;enumeration value="70"/>
 *               &lt;enumeration value="71"/>
 *               &lt;enumeration value="72"/>
 *               &lt;enumeration value="73"/>
 *               &lt;enumeration value="74"/>
 *               &lt;enumeration value="75"/>
 *               &lt;enumeration value="76"/>
 *               &lt;enumeration value="77"/>
 *               &lt;enumeration value="78"/>
 *               &lt;enumeration value="79"/>
 *               &lt;enumeration value="80"/>
 *               &lt;enumeration value="81"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="internationalPostalCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="postalFsa" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="postalLdu" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="countryTypeDd" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address2Type", propOrder = {
    "addressLine1",
    "addressLine2",
    "city",
    "provinceDd",
    "internationalPostalCode",
    "postalFsa",
    "postalLdu",
    "countryTypeDd"
})
public class Address2Type {

    protected String addressLine1;
    protected String addressLine2;
    protected String city;
    protected BigDecimal provinceDd;
    protected String internationalPostalCode;
    protected String postalFsa;
    protected String postalLdu;
    protected String countryTypeDd;

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the provinceDd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProvinceDd() {
        return provinceDd;
    }

    /**
     * Sets the value of the provinceDd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProvinceDd(BigDecimal value) {
        this.provinceDd = value;
    }

    /**
     * Gets the value of the internationalPostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternationalPostalCode() {
        return internationalPostalCode;
    }

    /**
     * Sets the value of the internationalPostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternationalPostalCode(String value) {
        this.internationalPostalCode = value;
    }

    /**
     * Gets the value of the postalFsa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalFsa() {
        return postalFsa;
    }

    /**
     * Sets the value of the postalFsa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalFsa(String value) {
        this.postalFsa = value;
    }

    /**
     * Gets the value of the postalLdu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalLdu() {
        return postalLdu;
    }

    /**
     * Sets the value of the postalLdu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalLdu(String value) {
        this.postalLdu = value;
    }

    /**
     * Gets the value of the countryTypeDd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryTypeDd() {
        return countryTypeDd;
    }

    /**
     * Sets the value of the countryTypeDd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryTypeDd(String value) {
        this.countryTypeDd = value;
    }

}