/*
 * $Id: EasyGenerationContentHandlerProxy.java,v 1.1 2013/10/21 09:38:12 eduardo Exp $
 * ============================================================================
 *                    The Apache Software License, Version 1.1
 * ============================================================================
 * 
 * Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modifica-
 * tion, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment: "This product includes software
 *    developed by the Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself, if
 *    and wherever such third-party acknowledgments normally appear.
 * 
 * 4. The names "FOP" and "Apache Software Foundation" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    apache@apache.org.
 * 
 * 5. Products derived from this software may not be called "Apache", nor may
 *    "Apache" appear in their name, without prior written permission of the
 *    Apache Software Foundation.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * APACHE SOFTWARE FOUNDATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLU-
 * DING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ============================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the Apache Software Foundation and was originally created by
 * James Tauber <jtauber@jtauber.com>. For more information on the Apache
 * Software Foundation, please see <http://www.apache.org/>.
 */ 
package impresionXML.tools;

//SAX
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * This class is an implementation of ContentHandler which acts as a proxy to
 * another ContentHandler and has the purpose to provide a few handy methods 
 * that make life easier when generating SAX events.
 * <br>
 * Note: This class is only useful for simple cases with no namespaces. 
 */

public class EasyGenerationContentHandlerProxy implements ContentHandler {

    /** An empty Attributes object used when no attributes are needed. */
    public static final Attributes EMPTY_ATTS = new AttributesImpl();

    private ContentHandler target;


    /**
     * Main constructor.
     * @param forwardTo ContentHandler to forward the SAX event to.
     */
    public EasyGenerationContentHandlerProxy(ContentHandler forwardTo) {
        this.target = forwardTo;
    }


    /**
     * Sends the notification of the beginning of an element.
     * @param name Name for the element.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void startElement(String name) throws SAXException {
        startElement(name, EMPTY_ATTS);
    }


    /**
     * Sends the notification of the beginning of an element.
     * @param name Name for the element.
     * @param atts The attributes attached to the element. If there are no 
     * attributes, it shall be an empty Attributes object. 
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void startElement(String name, Attributes atts) throws SAXException {
        startElement(null, name, name, atts);
    }


    /**
     * Send a String of character data.
     * @param s The content String
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void characters(String s) throws SAXException {
        target.characters(s.toCharArray(), 0, s.length());
    }


    /**
     * Send the notification of the end of an element.
     * @param name Name for the element.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void endElement(String name) throws SAXException {
        endElement(null, name, name);
    }


    /**
     * Sends notifications for a whole element with some String content.
     * @param name Name for the element.
     * @param value Content of the element.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void element(String name, String value) throws SAXException {
        element(name, value, EMPTY_ATTS);
    }


    /**
     * Sends notifications for a whole element with some String content.
     * @param name Name for the element.
     * @param value Content of the element.
     * @param atts The attributes attached to the element. If there are no 
     * attributes, it shall be an empty Attributes object. 
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    public void element(String name, String value, Attributes atts) throws SAXException {
        startElement(name, atts);
        if (value != null) {
            characters(value.toCharArray(), 0, value.length());
        }
        endElement(name);
    }

    /* =========== ContentHandler interface =========== */

    /**
     * t
     * @see org.xml.sax.ContentHandler#setDocumentLocator(Locator)
     * @param locator ?
     */
    public void setDocumentLocator(Locator locator) {
        target.setDocumentLocator(locator);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#startDocument()
     * @throws SAXException e
     */
    public void startDocument() throws SAXException {
        target.startDocument();
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#endDocument()
     * @throws SAXException e
     */
    public void endDocument() throws SAXException {
        target.endDocument();
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#startPrefixMapping(String, String)
     * @param prefix e 
     * @param uri e
     * @throws SAXException e
     */
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        target.startPrefixMapping(prefix, uri);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#endPrefixMapping(String) t
     * @param prefix t
     * @throws SAXException t
     */ 
    public void endPrefixMapping(String prefix) throws SAXException {
        target.endPrefixMapping(prefix);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     * @param namespaceURI 3
     * @param localName 3
     * @param qName 3
     * @param atts 3
     * @throws SAXException 3
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        target.startElement(namespaceURI, localName, qName, atts);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#endElement(String, String, String) t
     * @param namespaceURI 3
     * @param localName 3
     * @param qName 3
     * @throws SAXException 3
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        target.endElement(namespaceURI, localName, qName);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     * @param ch 3
     * @param start 3
     * @param length 3
     * @throws SAXException 3
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        target.characters(ch, start, length);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     * @param ch 3
     * @param start 3
     * @param length 3
     * @throws SAXException 3
     */
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        target.ignorableWhitespace(ch, start, length);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#processingInstruction(String, String)
     * @param target 3
     * @param data 3
     * @throws SAXException 3
     */
    public void processingInstruction(String target, String data) throws SAXException {
        this.target.processingInstruction(target, data);
    }


    /**
     * t
     * @see org.xml.sax.ContentHandler#skippedEntity(String)
     * @param name 3
     * @throws SAXException 3
     */
    public void skippedEntity(String name) throws SAXException {
        target.skippedEntity(name);
    }

}
