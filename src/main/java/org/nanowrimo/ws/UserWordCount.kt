package org.nanowrimo.ws

/**
 * A summary of a given user - name, numeric Nanowrimo user ID, and their current wordcount.
 *
 * The *entries* property of a `UserWordCount` is filled in when retrieving their
 * history and will be empty when retrieving the user's wordcount summary.
 */
class UserWordCount(var entries: List<WordCountListEntry>? = listOf()) : WordCountSummary() {
    var userID: Int
        get() = summaryID
        set(value) {
            summaryID = value
        }
}
