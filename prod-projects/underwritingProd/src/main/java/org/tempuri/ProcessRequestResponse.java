
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.microsoft.schemas.biztalk._2003.any.Root;


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
 *         &lt;element ref="{http://schemas.microsoft.com/BizTalk/2003/Any}Root" minOccurs="0"/>
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
    "root"
})
@XmlRootElement(name = "ProcessRequestResponse")
public class ProcessRequestResponse {

    @XmlElement(name = "Root", namespace = "http://schemas.microsoft.com/BizTalk/2003/Any")
    protected Root root;

    /**
     * Gets the value of the root property.
     * 
     * @return
     *     possible object is
     *     {@link Root }
     *     
     */
    public Root getRoot() {
        return root;
    }

    /**
     * Sets the value of the root property.
     * 
     * @param value
     *     allowed object is
     *     {@link Root }
     *     
     */
    public void setRoot(Root value) {
        this.root = value;
    }

}
