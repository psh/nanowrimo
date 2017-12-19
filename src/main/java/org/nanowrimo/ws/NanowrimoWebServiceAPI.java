package org.nanowrimo.ws;

import java.text.MessageFormat;

/**
 * Java Client to the <a href="http://www.nanowrimo.org/">NaNoWriMo</a> (National Novel Writing Month) word count webservice API
 *
 * @author Paul S. Hawke
 * @version 1.0
 */
@SuppressWarnings("unused")
public class NanowrimoWebServiceAPI {

    public UserWordCount getUserSummary(int userID) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_SUMMARY_URL, userID);
        return new WebServiceProcessor<>(urlString, UserWordCountHandler.class).processCall();
    }

    public UserWordCount getUserSummary(String user) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_SUMMARY_NAME_URL, user);
        return new WebServiceProcessor<>(urlString, UserWordCountHandler.class).processCall();
    }

    public UserWordCount getUserHistory(int userID) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_HISTORY_URL, userID);
        return new WebServiceProcessor<>(urlString, UserWordCountHandler.class).processCall();
    }

    public UserWordCount getUserHistory(String userName) {
        String urlString = MessageFormat.format(ServiceApiConstants.USER_HISTORY_NAME_URL, userName);
        return new WebServiceProcessor<>(urlString, UserWordCountHandler.class).processCall();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public RegionWordCount getRegionSummary(int regionID) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_SUMMARY_URL, regionID);
        return new WebServiceProcessor<>(urlString, RegionWordCountHandler.class).processCall();
    }

    public RegionWordCount getRegionSummary(String region) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_SUMMARY_NAME_URL, region);
        return new WebServiceProcessor<>(urlString, RegionWordCountHandler.class).processCall();
    }

    public RegionWordCount getRegionHistory(int regionID) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_HISTORY_URL, regionID);
        return new WebServiceProcessor<>(urlString, RegionWordCountHandler.class).processCall();
    }

    public RegionWordCount getRegionHistory(String region) {
        String urlString = MessageFormat.format(ServiceApiConstants.REGION_HISTORY_NAME_URL, region);
        return new WebServiceProcessor<>(urlString, RegionWordCountHandler.class).processCall();
    }

}
