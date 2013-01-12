package org.nanowrimo.ws;

/**
 * <p>A summary of a given user - name, numeric Nanowrimo user ID, and their current wordcount.</p>
 * <p/>
 * <p>The <i>entries</i> property of a <code>UserWordCount</code> is filled in when retrieving their
 * history and will be <code>null</code> when retrieving the user's wordcount summary.</p>
 */
public class UserWordCount extends WordCountSummary {
    private WordCountListEntry[] entries;

    public UserWordCount() {
    }

    public int getUserID() {
        return getSummaryID();
    }

    public void setUserID(int userID) {
        setSummaryID(userID);
    }

    public WordCountListEntry[] getEntries() {
        return entries;
    }

    public void setEntries(WordCountListEntry[] entries) {
        this.entries = entries;
    }
}
