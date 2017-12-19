package org.nanowrimo.ws;

import java.util.Date;

/**
 * This class provides a binding between a date and the wordcount of a user or region
 * at that point in time.  An array of <code>WordCountListEntry</code> objects is
 * contained in the UserWordCount, and in the RegionWordCount.  This array will be null
 * when retrieving a summary, or will be filled in when retrieving the full history
 * for a given user or region.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class WordCountListEntry {
    private Date date;
    private int wordcount;

    public WordCountListEntry() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }
}
