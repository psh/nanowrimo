package org.nanowrimo.ws

import org.xml.sax.Attributes

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

internal class UserWordCountHandler : WordCountHandler<UserWordCount>() {
    private val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val sb = StringBuffer()
    private var entryDate: Date? = null
    private var wordcount: Int = 0
    private var insideEntry: Boolean = false
    private val entries: MutableList<WordCountListEntry>
    override val summary: UserWordCount

    init {
        entries = ArrayList()
        summary = UserWordCount()
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        sb.append(ch, start, length)
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        sb.setLength(0)
        if (WORD_COUNT_ENTRY == qName) {
            insideEntry = true
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        when {
            USER_NAME == qName -> summary.name = sb.toString()
            WORD_COUNT_ENTRY == qName -> {
                insideEntry = false
                if (entryDate != null) {
                    val entry = WordCountListEntry()
                    entry.date = entryDate
                    entry.wordcount = wordcount
                    entries.add(entry)
                }
            }
            WORD_COUNT_ENTRY_DATE == qName -> try {
                entryDate = sdf.parse(sb.toString())
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            USER_ERROR == qName -> summary.error = sb.toString()
            USER_WINNER == qName -> summary.winner = sb.toString().toBoolean()
            USER_ID == qName -> try {
                summary.userID = sb.toString().toInt()
            } catch (ignored: NumberFormatException) {
            }
            WORD_COUNT == qName && insideEntry -> try {
                wordcount = sb.toString().toInt()
            } catch (ignored: NumberFormatException) {
            }
            USER_WORD_COUNT == qName -> try {
                summary.wordcount = sb.toString().toInt()
            } catch (ignored: NumberFormatException) {
            }
            WORD_COUNT_GOAL == qName -> try {
                summary.goal = sb.toString().toInt()
            } catch (ignored: NumberFormatException) {
            }
            WORDCOUNTS_CONTAINER == qName -> summary.entries = entries
        }
    }
}
