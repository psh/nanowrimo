package org.nanowrimo.ws;

/**
 * Summary data for a NaNoWriMo region.  This will be returned either from the
 * region summary webservice or from the region wordcount history service.  The
 * <i>entries</i> property will remain null when retriving just the summary,
 * but will be filled in when retrieving history.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class RegionWordCount extends WordCountSummary {
    private int rank;
    private int min;
    private int max;
    private int average;
    private int stddev;
    private int count;
    private WordCountListEntry[] entries;

    public RegionWordCount() {
    }

    public int getRegionID() {
        return getSummaryID();
    }

    public void setRegionID(int regionID) {
        setSummaryID(regionID);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getStddev() {
        return stddev;
    }

    public void setStddev(int stddev) {
        this.stddev = stddev;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Note: When returned from the summary service, the <i>entries</i> will be null.
     *
     * @return Array of WordCountListEntry objects representing wordcounts for specific days
     * @see org.nanowrimo.ws.WordCountListEntry
     */
    public WordCountListEntry[] getEntries() {
        return entries;
    }

    public void setEntries(WordCountListEntry[] entries) {
        this.entries = entries;
    }
}
