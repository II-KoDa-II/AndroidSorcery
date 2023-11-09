package com.example.internettest

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnHTTP)
        val url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"
        button.setOnClickListener{
            thread {
                val content = getContent((url))
                if (content != null) {
                    Log.d("Flickr cats", content)
                }
            }
        }

        val okButton = findViewById<Button>(R.id.btnOkHTTP)
        okButton.setOnClickListener{
            thread {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
                    .build()
                try {
                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            throw IOException("Server request error:" +
                                    " ${response.code} ${response.message}")
                        }
                        val content = response.body!!.string()
                        Log.i("Flickr OkCats", content)
                    }
                } catch (e: IOException) {
                    println("Connection error: $e")
                }
            }
        }
    }

    private fun getContent(url: String, timeout: Int = 10000): String? {
        var c: HttpURLConnection? = null
        try {
            val u = URL(url)
            c = u.openConnection() as HttpURLConnection
            c.setRequestMethod("GET")
            c.setRequestProperty("Content-length", "0")
            c.setUseCaches(false)
            c.setAllowUserInteraction(false)
            c.setConnectTimeout(timeout)
            c.setReadTimeout(timeout)
            c.connect()
            val status: Int = c.getResponseCode()
            when (status) {
                200, 201 -> {
                    val streamReader = InputStreamReader(c.inputStream)
                    var text = ""
                    streamReader.use {
                        text = it.readText()
                    }
                    return text
                }
            }
        } catch (ex: MalformedURLException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } finally {
            if (c != null) {
                try {
                    c.disconnect()
                } catch (ex: Exception) {
                    Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
                }
            }
        }
        return null
    }
}