package org.nanowrimo.ws;

import java.text.MessageFormat;

/**
 * Java Client to the <a href="http://www.nanowrimo.org/">NaNoWriMo</a> (National Novel Writing Month) word count webservice API
 *
 * @author Paul S. Hawke
 * @version 1.0
 */
public class NanowrimoWebServiceAPI {

    public UserWordCount getUserSummary(int userID) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_SUMMARY_URL, userID);
        return processCall(urlString, UserWordCountHandler.class);
    }

    public UserWordCount getUserSummary(String user) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_SUMMARY_NAME_URL, user);
        return processCall(urlString, UserWordCountHandler.class);
    }

    public UserWordCount getUserHistory(int userID) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_HISTORY_URL, userID);
        return processCall(urlString, UserWordCountHandler.class);
    }

    public UserWordCount getUserHistory(String userName) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_HISTORY_NAME_URL, userName);
        return processCall(urlString, UserWordCountHandler.class);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public RegionWordCount getRegionSummary(int regionID) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_SUMMARY_URL, regionID);
        return processCall(urlString, RegionWordCountHandler.class);
    }

    public RegionWordCount getRegionSummary(String region) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_SUMMARY_NAME_URL, region);
        return processCall(urlString, RegionWordCountHandler.class);
    }

    public RegionWordCount getRegionHistory(int regionID) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_HISTORY_URL, regionID);
        return processCall(urlString, RegionWordCountHandler.class);
    }

    public RegionWordCount getRegionHistory(String region) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_HISTORY_NAME_URL, region);
        return processCall(urlString, RegionWordCountHandler.class);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private UserWordCount processCall(String urlString, Class<UserWordCountHandler> handlerClass) {
        return new WebServiceProcessor<UserWordCount>(urlString, handlerClass).processCall();
    }

    private RegionWordCount processCall(String urlString, Class<RegionWordCountHandler> handlerClass) {
        return new WebServiceProcessor<RegionWordCount>(urlString, handlerClass).processCall();
    }
}
