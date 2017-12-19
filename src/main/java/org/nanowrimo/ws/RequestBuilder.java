package org.nanowrimo.ws;

import static org.nanowrimo.ws.ServiceApiConstants.*;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 * On: 12/27/12 at 2:03 AM
 */
@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
public class RequestBuilder<T extends WordCountSummary> {
    private String theName = null;
    private Integer theId = null;
    private Boolean user = Boolean.TRUE;

    public static org.nanowrimo.ws.RequestBuilder<UserWordCount> user() {
        return new org.nanowrimo.ws.RequestBuilder<UserWordCount>().forUser();
    }

    public static org.nanowrimo.ws.RequestBuilder<RegionWordCount> region() {
        return new org.nanowrimo.ws.RequestBuilder<RegionWordCount>().forRegion();
    }

    public Request<T> byName(String name) {
        this.theName = name;
        return this.create();
    }

    public Request<T> byId(int id) {
        this.theId = id;
        return this.create();
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private org.nanowrimo.ws.RequestBuilder<T> forUser() {
        this.user = Boolean.TRUE;
        return this;
    }

    private org.nanowrimo.ws.RequestBuilder<T> forRegion() {
        this.user = Boolean.FALSE;
        return this;
    }

    private Request<T> create() {
        if (theName == null && theId == null) {
            throw new IllegalStateException("Request needs one of either the user name or ID");
        }

        Class<? extends WordCountHandler<T>> handlerClass =
                (Class<? extends WordCountHandler<T>>) (user ? UserWordCountHandler.class : RegionWordCountHandler.class);

        return (theId != null) ? buildRequestById(handlerClass) : buildRequestByName(handlerClass);
    }

    private Request<T> buildRequestById(Class<? extends WordCountHandler<T>> handlerClass) {
        return user
                ? new Request<>(theId, handlerClass, USER_SUMMARY_URL, USER_SUMMARY_NAME_URL, USER_HISTORY_URL, USER_HISTORY_NAME_URL)
                : new Request<>(theId, handlerClass, REGION_SUMMARY_URL, REGION_SUMMARY_NAME_URL, REGION_HISTORY_URL, REGION_HISTORY_NAME_URL);
    }

    private Request<T> buildRequestByName(Class<? extends WordCountHandler<T>> handlerClass) {
        return user
                ? new Request<>(theName, handlerClass, USER_SUMMARY_URL, USER_SUMMARY_NAME_URL, USER_HISTORY_URL, USER_HISTORY_NAME_URL)
                : new Request<>(theName, handlerClass, REGION_SUMMARY_URL, REGION_SUMMARY_NAME_URL, REGION_HISTORY_URL, REGION_HISTORY_NAME_URL);
    }
}
