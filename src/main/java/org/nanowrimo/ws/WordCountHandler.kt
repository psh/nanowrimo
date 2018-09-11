package org.nanowrimo.ws

import org.xml.sax.ContentHandler
import org.xml.sax.DTDHandler
import org.xml.sax.EntityResolver
import org.xml.sax.ErrorHandler
import org.xml.sax.helpers.DefaultHandler

// user id
const val USER_NAME = "uname"
const val USER_ID = "uid"
const val USER_WORD_COUNT = "user_wordcount"
const val USER_WINNER = "winner"
const val USER_ERROR = "error"

// region
const val REGION_NAME = "rname"
const val REGION_ID = "rid"
const val REGION_WORD_COUNT = "region_wordcount"
const val REGION_MIN = "min"
const val REGION_MAX = "max"
const val REGION_AVERAGE = "avg"
const val REGION_STDDEV = "stddev"
const val REGION_COUNT = "count"

// shared
const val WORD_COUNT_ENTRY = "wcentry"
const val WORD_COUNT_ENTRY_DATE = "wcdate"
const val WORD_COUNT_RANK = "wcrank"
const val WORD_COUNT = "wc"
const val WORD_COUNT_GOAL = "goal"
const val WORDCOUNTS_CONTAINER = "wordcounts"

internal abstract class WordCountHandler<out T : WordCountSummary> : DefaultHandler(), EntityResolver, DTDHandler, ContentHandler, ErrorHandler {
    internal abstract val summary: T
}
