package org.nanowrimo.ws;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.MessageFormat;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 12/26/12 at 11:40 PM
 */
@SuppressWarnings({"unused", "WeakerAccess", "Duplicates"})
public class Request<T extends WordCountSummary> {

    private String summaryByIdUrl;
    private String summaryByNameUrl;
    private String historyByIdUrl;
    private String historyByNameUrl;
    private WordCountHandler<T> handler;
    private Integer theId = null;
    private String theName = null;

    public Request(Integer theId, Class<? extends WordCountHandler<T>> handlerClass,
                   String summaryByIdUrl, String summaryByNameUrl,
                   String historyByIdUrl, String historyByNameUrl) {
        this.theId = theId;
        init(handlerClass, summaryByIdUrl, summaryByNameUrl, historyByIdUrl, historyByNameUrl);
    }

    public Request(String theName, Class<? extends WordCountHandler<T>> handlerClass,
                   String summaryByIdUrl, String summaryByNameUrl,
                   String historyByIdUrl, String historyByNameUrl) {
        this.theName = theName;
        init(handlerClass, summaryByIdUrl, summaryByNameUrl, historyByIdUrl, historyByNameUrl);
    }

    private void init(Class<? extends WordCountHandler<T>> handlerClass, String summaryByIdUrl, String summaryByNameUrl, String historyByIdUrl, String historyByNameUrl) {
        this.handler = null;
        try {
            handler = handlerClass.newInstance();
        } catch (Exception ignored) {}
        this.summaryByIdUrl = summaryByIdUrl;
        this.summaryByNameUrl = summaryByNameUrl;
        this.historyByIdUrl = historyByIdUrl;
        this.historyByNameUrl = historyByNameUrl;
    }

    public T getSummary() {
        return processCall(theId != null ?
                MessageFormat.format(summaryByIdUrl, theId) :
                MessageFormat.format(summaryByNameUrl, theName));
    }

    public T getHistory() {
        return processCall(theId != null ?
                MessageFormat.format(historyByIdUrl, theId) :
                MessageFormat.format(historyByNameUrl, theName));
    }

    private T processCall(String urlString) {
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
