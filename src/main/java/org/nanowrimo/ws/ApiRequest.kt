package org.nanowrimo.ws

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.xml.sax.InputSource
import java.io.IOException
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

fun requestUserSummary(id: String, callback: (UserWordCount?) -> Unit) {
    requestAsync("http://www.nanowrimo.org/wordcount_api/wc/$id", UserWordCountHandler(), callback)
}

fun requestUserHistory(id: String, callback: (UserWordCount?) -> Unit) {
    requestAsync("http://www.nanowrimo.org/wordcount_api/wchistory/$id", UserWordCountHandler(), callback)
}

fun requestRegionSummary(id: String, callback: (RegionWordCount?) -> Unit) {
    requestAsync("http://www.nanowrimo.org/wordcount_api/wcregion/$id", RegionWordCountHandler(), callback)
}

fun requestRegionHistory(id: String, callback: (RegionWordCount?) -> Unit) {
    requestAsync("http://www.nanowrimo.org/wordcount_api/wcregionhist/$id", RegionWordCountHandler(), callback)
}

private val client: OkHttpClient by lazy { OkHttpClient.Builder().build() }
private val saxParser: SAXParser by lazy { SAXParserFactory.newInstance().newSAXParser() }

private fun <T : WordCountSummary> requestAsync(endpoint: String, handler: WordCountHandler<T>, callback: (T?) -> Unit) {
    try {
        val request = Request.Builder().get().url(endpoint).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                callback(null)
            }

            override fun onResponse(call: Call?, response: Response?) {
                saxParser.xmlReader.apply {
                    contentHandler = handler
                    parse(InputSource(response?.body()?.charStream()))
                    callback(handler.summary)
                }
            }
        })
    } catch (e: Exception) {
        e.printStackTrace()
        callback(null)
    }
}