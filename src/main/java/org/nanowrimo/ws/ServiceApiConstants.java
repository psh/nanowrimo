package org.nanowrimo.ws;

interface ServiceApiConstants {
    // user id
    static final String USER_NAME = "uname";
    static final String USER_ID = "uid";
    static final String USER_WORD_COUNT = "user_wordcount";
    static final String USER_WINNER = "winner";
    static final String USER_ERROR = "error";

    // region
    static final String REGION_NAME = "rname";
    static final String REGION_ID = "rid";
    static final String REGION_WORD_COUNT = "region_wordcount";
    static final String REGION_MIN = "min";
    static final String REGION_MAX = "max";
    static final String REGION_AVERAGE = "avg";
    static final String REGION_STDDEV = "stddev";
    static final String REGION_COUNT = "count";

    // shared
    static final String WORD_COUNT_ENTRY = "wcentry";
    static final String WORD_COUNT_ENTRY_DATE = "wcdate";
    static final String WORD_COUNT_RANK = "wcrank";
    static final String WORD_COUNT = "wc";
    static final String WORD_COUNT_GOAL = "goal";
    static final String WORDCOUNTS_CONTAINER = "wordcounts";

    // URL formats for the services
    static final String USER_SUMMARY_URL = "http://www.nanowrimo.org/wordcount_api/wc/{0,number,#}";
    static final String USER_SUMMARY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wc/{0}";
    static final String USER_HISTORY_URL = "http://www.nanowrimo.org/wordcount_api/wchistory/{0,number,#}";
    static final String USER_HISTORY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wchistory/{0}";

    static final String REGION_SUMMARY_URL = "http://www.nanowrimo.org/wordcount_api/wcregion/{0,number,#}";
    static final String REGION_SUMMARY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wcregion/{0}";
    static final String REGION_HISTORY_URL = "http://www.nanowrimo.org/wordcount_api/wcregionhist/{0,number,#}";
    static final String REGION_HISTORY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wcregionhist/{0}";
}
