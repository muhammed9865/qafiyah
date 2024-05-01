package com.salman.qafiyah.data.source

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/30/2024.
 */
class DiacritizationRemoteSource {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:5000"
        private const val TAG = "DiacritizationRemoteSou"
    }

    suspend fun diacritizeText(text: String): String = withContext(Dispatchers.IO) {
        val endpoint = "$BASE_URL/shakkel"
        val body = JSONObject().apply {
            put("text", text)
        }.toString()
        val url = prepareConnection(URL(endpoint))

        url.outputStream.write(body.toByteArray())
        val reader = url.inputStream.bufferedReader()
        val result = reader.readText()
        reader.close()
        url.disconnect()

        val json = JSONObject(result)
        val diacritizedText = json.getString("diacritized")

        Log.d(TAG, "diacritizeText: $json")
        Log.d(TAG, "diacritizeText: $diacritizedText")

        diacritizedText
    }

    private fun prepareConnection(url: URL): HttpURLConnection {
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json")
        connection.doOutput = true
        return connection
    }
}