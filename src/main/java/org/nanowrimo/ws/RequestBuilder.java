package org.nanowrimo.ws;

/**
* @author Paul S. Hawke (paul.hawke@gmail.com)
*         On: 12/27/12 at 2:03 AM
*/
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

        @SuppressWarnings("unchecked") Class<? extends WordCountHandler<T>> handlerClass =
                (Class<? extends WordCountHandler<T>>) (user ? UserWordCountHandler.class : RegionWordCountHandler.class);

        return theId != null ? (user ?
                new Request<T>(theId, handlerClass,
                        ServiceApiConstants.USER_SUMMARY_URL,
                        ServiceApiConstants.USER_SUMMARY_NAME_URL,
                        ServiceApiConstants.USER_HISTORY_URL,
                        ServiceApiConstants.USER_HISTORY_NAME_URL) :
                new Request<T>(theId, handlerClass,
                        ServiceApiConstants.REGION_SUMMARY_URL,
                        ServiceApiConstants.REGION_SUMMARY_NAME_URL,
                        ServiceApiConstants.REGION_HISTORY_URL,
                        ServiceApiConstants.REGION_HISTORY_NAME_URL)
        ) : (user ?
                new Request<T>(theName, handlerClass,
                        ServiceApiConstants.USER_SUMMARY_URL,
                        ServiceApiConstants.USER_SUMMARY_NAME_URL,
                        ServiceApiConstants.USER_HISTORY_URL,
                        ServiceApiConstants.USER_HISTORY_NAME_URL) :
                new Request<T>(theName, handlerClass,
                        ServiceApiConstants.REGION_SUMMARY_URL,
                        ServiceApiConstants.REGION_SUMMARY_NAME_URL,
                        ServiceApiConstants.REGION_HISTORY_URL,
                        ServiceApiConstants.REGION_HISTORY_NAME_URL)
        );
    }
}
