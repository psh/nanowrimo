package org.nanowrimo.ws

/**
 * <p>A summary of a given user or region including name, numeric Nanowrimo ID, and the current wordcount.</p>
 */
abstract class WordCountSummary(
        var summaryID: Int = 0,
        var name: String? = null,
        var error: String? = null,
        var wordcount: Int = 0,
        var winner: Boolean? = null,
        var goal: Int = 50000
)