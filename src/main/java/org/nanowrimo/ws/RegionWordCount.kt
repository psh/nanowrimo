package org.nanowrimo.ws

/**
 * Summary data for a NaNoWriMo region.  This will be returned either from the
 * region summary webservice or from the region wordcount history service.  The
 * *entries* property will remain empty when retrieving just the summary,
 * but will be filled in when retrieving history.
 */
class RegionWordCount(var rank: Int = 0, var min: Int = 0, var max: Int = 0,
                      var average: Int = 0, var stddev: Int = 0, var count: Int = 0,
                      var entries: List<WordCountListEntry>? = listOf()) : WordCountSummary() {
    var regionID: Int
        get() = summaryID
        set(value) {
            summaryID = value
        }
}
