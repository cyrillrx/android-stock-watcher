package com.cyrillrx.alerter.validator

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HtmlFetcher(private val url: String) {

    private val httpClient = OkHttpClient.Builder().build()

    suspend fun getText(): String {
        val response = httpClient.sendGetRequest(url)
        return try {
            response.readSuccessfulBodyAsString()
        } catch (e: Exception) {
            Log.e(TAG, "Error while reading response", e)
            ""
        }
    }

    companion object {
        private const val TAG = "OkHttpHtmlFetcherImpl"

        suspend fun OkHttpClient.sendGetRequest(url: String): Response {
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            return sendRequest(request)
        }

        private suspend fun OkHttpClient.sendRequest(request: Request): Response = suspendCoroutine { continuation ->
            Log.v(TAG, "sendRequest - ${request.method} ${request.url}")

            try {
                newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        continuation.resume(response)
                    }
                })
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }

        fun Response.readSuccessfulBodyAsString(): String {
            use { response ->
                val responseBody = response.body ?: throw NoBodyException(response)
                responseBody.use { body ->
                    if (response.isSuccessful) {
                        return body.string()
                    } else {
                        throw UnsuccessfulException(response, body.string())
                    }
                }
            }
        }
    }
}

fun Response.requestUrl(): String = request.url.toString()

class UnsuccessfulException(response: Response, serializedBody: String) :
    Exception("${response.requestUrl()} - ${response.code} - body: $serializedBody")

class NoBodyException(response: Response) : Exception("${response.requestUrl()} - ${response.code}")
