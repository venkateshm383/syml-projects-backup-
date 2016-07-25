
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.mscanada.Clipboard;


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
 *         &lt;element ref="{http://mscanada.com}clipboard" minOccurs="0"/>
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
    "clipboard"
})
@XmlRootElement(name = "ProcessRequest", namespace="http://tempuri.org/")
public class ProcessRequest {

    @XmlElement(namespace="http://mscanada.com")
    protected Clipboard clipboard;

    /**
     * Gets the value of the clipboard property.
     * 
     * @return
     *     possible object is
     *     {@link Clipboard }
     *     
     */
    public Clipboard getClipboard() {
        return clipboard;
    }

    /**
     * Sets the value of the clipboard property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clipboard }
     *     
     */
    public void setClipboard(Clipboard value) {
        this.clipboard = value;
    }

}
