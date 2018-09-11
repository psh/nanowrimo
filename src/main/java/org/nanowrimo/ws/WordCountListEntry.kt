package org.nanowrimo.ws

import java.util.Date

/**
 * This class provides a binding between a date and the wordcount of a user or region
 * at that point in time.  An array of `WordCountListEntry` objects is
 * contained in the UserWordCount, and in the RegionWordCount.  This list will be empty
 * when retrieving a summary, or will be filled in when retrieving the full history
 * for a given user or region.
 */
data class WordCountListEntry(var date: Date? = null, var wordcount: Int = 0)
