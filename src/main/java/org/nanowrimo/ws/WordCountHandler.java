package org.nanowrimo.ws;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 * On: Sep 17, 2007 at 4:40:43 PM
 */
abstract class WordCountHandler<T extends WordCountSummary> extends DefaultHandler implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler, ServiceApiConstants {
    abstract T getSummary();
}
