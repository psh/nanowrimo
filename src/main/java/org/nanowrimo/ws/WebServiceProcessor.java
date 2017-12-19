package org.nanowrimo.ws;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 12/26/12 at 11:16 PM
 */
@SuppressWarnings({"unused", "WeakerAccess", "Duplicates"})
class WebServiceProcessor<T extends WordCountSummary> {
    private String urlString;
    private WordCountHandler<T> handler;

    public WebServiceProcessor(String urlString, Class<? extends WordCountHandler<T>> handlerClass) {
        this.urlString = urlString;
        try {
            this.handler = handlerClass.newInstance();
        } catch (Exception e) {
            this.handler = null;
        }
    }

    public T processCall() {
        if (handler == null) {
            return null;
        }

        try {
            Reader serverReader = getServerStreamReader(urlString);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(serverReader));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handler.getSummary();
    }

    private Reader getServerStreamReader(String urlString) throws IOException {
        return new InputStreamReader(new URL(urlString).openStream());
    }
}
