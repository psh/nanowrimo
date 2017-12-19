package org.nanowrimo.ws;

interface ServiceApiConstants {
    // user id
    String USER_NAME = "uname";
    String USER_ID = "uid";
    String USER_WORD_COUNT = "user_wordcount";
    String USER_WINNER = "winner";
    String USER_ERROR = "error";

    // region
    String REGION_NAME = "rname";
    String REGION_ID = "rid";
    String REGION_WORD_COUNT = "region_wordcount";
    String REGION_MIN = "min";
    String REGION_MAX = "max";
    String REGION_AVERAGE = "avg";
    String REGION_STDDEV = "stddev";
    String REGION_COUNT = "count";

    // shared
    String WORD_COUNT_ENTRY = "wcentry";
    String WORD_COUNT_ENTRY_DATE = "wcdate";
    String WORD_COUNT_RANK = "wcrank";
    String WORD_COUNT = "wc";
    String WORD_COUNT_GOAL = "goal";
    String WORDCOUNTS_CONTAINER = "wordcounts";

    // URL formats for the services
    String USER_SUMMARY_URL = "http://www.nanowrimo.org/wordcount_api/wc/{0,number,#}";
    String USER_SUMMARY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wc/{0}";
    String USER_HISTORY_URL = "http://www.nanowrimo.org/wordcount_api/wchistory/{0,number,#}";
    String USER_HISTORY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wchistory/{0}";

    String REGION_SUMMARY_URL = "http://www.nanowrimo.org/wordcount_api/wcregion/{0,number,#}";
    String REGION_SUMMARY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wcregion/{0}";
    String REGION_HISTORY_URL = "http://www.nanowrimo.org/wordcount_api/wcregionhist/{0,number,#}";
    String REGION_HISTORY_NAME_URL = "http://www.nanowrimo.org/wordcount_api/wcregionhist/{0}";
}
