
package com.mscanada;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="context" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="applicationName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="userid" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="language" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="dateTime" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="msgRequest" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="comment"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="commentText" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="msgErrors"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="msgError"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="requestId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="provider" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="recipient" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="applicationData" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;any/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "context",
    "msgRequest",
    "applicationData"
})
@XmlRootElement(name = "clipboard")
public class Clipboard {

    protected Clipboard.Context context;
    protected Clipboard.MsgRequest msgRequest;
    protected Clipboard.ApplicationData applicationData;

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link Clipboard.Context }
     *     
     */
    public Clipboard.Context getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clipboard.Context }
     *     
     */
    public void setContext(Clipboard.Context value) {
        this.context = value;
    }

    /**
     * Gets the value of the msgRequest property.
     * 
     * @return
     *     possible object is
     *     {@link Clipboard.MsgRequest }
     *     
     */
    public Clipboard.MsgRequest getMsgRequest() {
        return msgRequest;
    }

    /**
     * Sets the value of the msgRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clipboard.MsgRequest }
     *     
     */
    public void setMsgRequest(Clipboard.MsgRequest value) {
        this.msgRequest = value;
    }

    /**
     * Gets the value of the applicationData property.
     * 
     * @return
     *     possible object is
     *     {@link Clipboard.ApplicationData }
     *     
     */
    public Clipboard.ApplicationData getApplicationData() {
        return applicationData;
    }

    /**
     * Sets the value of the applicationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clipboard.ApplicationData }
     *     
     */
    public void setApplicationData(Clipboard.ApplicationData value) {
        this.applicationData = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;any/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "content"
    })
    public static class ApplicationData {

        @XmlMixed
        @XmlAnyElement(lax = true)
        protected List<Object> content;

        /**
         * Gets the value of the content property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the content property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * {@link Object }
         * 
         * 
         */
        public List<Object> getContent() {
            if (content == null) {
                content = new ArrayList<Object>();
            }
            return this.content;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="applicationName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="userid" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="language" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="dateTime" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Context {

        @XmlAttribute(name = "applicationName")
        protected String applicationName;
        @XmlAttribute(name = "sessionId")
        protected String sessionId;
        @XmlAttribute(name = "userid")
        protected String userid;
        @XmlAttribute(name = "language")
        protected String language;
        @XmlAttribute(name = "dateTime")
        protected String dateTime;

        /**
         * Gets the value of the applicationName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApplicationName() {
            return applicationName;
        }

        /**
         * Sets the value of the applicationName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApplicationName(String value) {
            this.applicationName = value;
        }

        /**
         * Gets the value of the sessionId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSessionId() {
            return sessionId;
        }

        /**
         * Sets the value of the sessionId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSessionId(String value) {
            this.sessionId = value;
        }

        /**
         * Gets the value of the userid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUserid() {
            return userid;
        }

        /**
         * Sets the value of the userid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUserid(String value) {
            this.userid = value;
        }

        /**
         * Gets the value of the language property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Sets the value of the language property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLanguage(String value) {
            this.language = value;
        }

        /**
         * Gets the value of the dateTime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateTime() {
            return dateTime;
        }

        /**
         * Sets the value of the dateTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateTime(String value) {
            this.dateTime = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="comment"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="commentText" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="msgErrors"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="msgError"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="requestId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="provider" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="recipient" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "comment",
        "msgErrors"
    })
    public static class MsgRequest {

        @XmlElement(required = true, nillable = true)
        protected Clipboard.MsgRequest.Comment comment;
        @XmlElement(required = true, nillable = true)
        protected Clipboard.MsgRequest.MsgErrors msgErrors;
        @XmlAttribute(name = "requestId")
        protected String requestId;
        @XmlAttribute(name = "serviceType")
        protected String serviceType;
        @XmlAttribute(name = "source")
        protected String source;
        @XmlAttribute(name = "provider")
        protected String provider;
        @XmlAttribute(name = "recipient")
        protected String recipient;

        /**
         * Gets the value of the comment property.
         * 
         * @return
         *     possible object is
         *     {@link Clipboard.MsgRequest.Comment }
         *     
         */
        public Clipboard.MsgRequest.Comment getComment() {
            return comment;
        }

        /**
         * Sets the value of the comment property.
         * 
         * @param value
         *     allowed object is
         *     {@link Clipboard.MsgRequest.Comment }
         *     
         */
        public void setComment(Clipboard.MsgRequest.Comment value) {
            this.comment = value;
        }

        /**
         * Gets the value of the msgErrors property.
         * 
         * @return
         *     possible object is
         *     {@link Clipboard.MsgRequest.MsgErrors }
         *     
         */
        public Clipboard.MsgRequest.MsgErrors getMsgErrors() {
            return msgErrors;
        }

        /**
         * Sets the value of the msgErrors property.
         * 
         * @param value
         *     allowed object is
         *     {@link Clipboard.MsgRequest.MsgErrors }
         *     
         */
        public void setMsgErrors(Clipboard.MsgRequest.MsgErrors value) {
            this.msgErrors = value;
        }

        /**
         * Gets the value of the requestId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestId() {
            return requestId;
        }

        /**
         * Sets the value of the requestId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestId(String value) {
            this.requestId = value;
        }

        /**
         * Gets the value of the serviceType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceType() {
            return serviceType;
        }

        /**
         * Sets the value of the serviceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceType(String value) {
            this.serviceType = value;
        }

        /**
         * Gets the value of the source property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSource(String value) {
            this.source = value;
        }

        /**
         * Gets the value of the provider property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProvider() {
            return provider;
        }

        /**
         * Sets the value of the provider property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProvider(String value) {
            this.provider = value;
        }

        /**
         * Gets the value of the recipient property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipient() {
            return recipient;
        }

        /**
         * Sets the value of the recipient property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipient(String value) {
            this.recipient = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="commentText" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Comment {

            @XmlAttribute(name = "commentText")
            protected String commentText;

            /**
             * Gets the value of the commentText property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCommentText() {
                return commentText;
            }

            /**
             * Sets the value of the commentText property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCommentText(String value) {
                this.commentText = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="msgError"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "msgError"
        })
        public static class MsgErrors {

            @XmlElement(required = true, nillable = true)
            protected Clipboard.MsgRequest.MsgErrors.MsgError msgError;

            /**
             * Gets the value of the msgError property.
             * 
             * @return
             *     possible object is
             *     {@link Clipboard.MsgRequest.MsgErrors.MsgError }
             *     
             */
            public Clipboard.MsgRequest.MsgErrors.MsgError getMsgError() {
                return msgError;
            }

            /**
             * Sets the value of the msgError property.
             * 
             * @param value
             *     allowed object is
             *     {@link Clipboard.MsgRequest.MsgErrors.MsgError }
             *     
             */
            public void setMsgError(Clipboard.MsgRequest.MsgErrors.MsgError value) {
                this.msgError = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class MsgError {

                @XmlAttribute(name = "code")
                protected String code;
                @XmlAttribute(name = "message")
                protected String message;

                /**
                 * Gets the value of the code property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCode() {
                    return code;
                }

                /**
                 * Sets the value of the code property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCode(String value) {
                    this.code = value;
                }

                /**
                 * Gets the value of the message property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMessage() {
                    return message;
                }

                /**
                 * Sets the value of the message property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMessage(String value) {
                    this.message = value;
                }

            }

        }

    }

}
