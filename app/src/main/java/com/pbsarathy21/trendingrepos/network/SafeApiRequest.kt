package com.pbsarathy21.trendingrepos.network

import com.pbsarathy21.trendingrepos.utils.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            Timber.e(error)
            val message = StringBuilder()
            if(error != null) {
                try {
                    message.append(JSONObject(error).getString("message"))
                } catch (e: JSONException) {
                    message.append("something went wrong")
                }
            } else message.append("something went wrong")

            when(response.code()) {
                400 -> throw BadRequestException(message.toString())
                401 -> throw AuthenticationFailureException("unauthorized")
                404 -> throw UrlNotFoundException("url not found")
                500 -> throw ApiServerException("internal server error")
                else -> throw UnknownApiException(message.toString())
            }
        }
    }
}