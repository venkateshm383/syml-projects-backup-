
package com.mscanada;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mscanada package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mscanada
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Clipboard }
     * 
     */
    public Clipboard createClipboard() {
        return new Clipboard();
    }

    /**
     * Create an instance of {@link Clipboard.MsgRequest }
     * 
     */
    public Clipboard.MsgRequest createClipboardMsgRequest() {
        return new Clipboard.MsgRequest();
    }

    /**
     * Create an instance of {@link Clipboard.MsgRequest.MsgErrors }
     * 
     */
    public Clipboard.MsgRequest.MsgErrors createClipboardMsgRequestMsgErrors() {
        return new Clipboard.MsgRequest.MsgErrors();
    }

    /**
     * Create an instance of {@link Clipboard.Context }
     * 
     */
    public Clipboard.Context createClipboardContext() {
        return new Clipboard.Context();
    }

    /**
     * Create an instance of {@link Clipboard.ApplicationData }
     * 
     */
    public Clipboard.ApplicationData createClipboardApplicationData() {
        return new Clipboard.ApplicationData();
    }

    /**
     * Create an instance of {@link Clipboard.MsgRequest.Comment }
     * 
     */
    public Clipboard.MsgRequest.Comment createClipboardMsgRequestComment() {
        return new Clipboard.MsgRequest.Comment();
    }

    /**
     * Create an instance of {@link Clipboard.MsgRequest.MsgErrors.MsgError }
     * 
     */
    public Clipboard.MsgRequest.MsgErrors.MsgError createClipboardMsgRequestMsgErrorsMsgError() {
        return new Clipboard.MsgRequest.MsgErrors.MsgError();
    }

}
