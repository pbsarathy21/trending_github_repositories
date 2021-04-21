package com.pbsarathy21.trendingrepos.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.pbsarathy21.trendingrepos.utils.ApiException
import com.pbsarathy21.trendingrepos.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoInternetException("Make sure you have Internet connection")
        } else {
            try {
                return chain.proceed(chain.request())
            } catch (e: Exception) {
                throw ApiException(e.message!!)
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                        ) -> true
                        else -> false
                    }
                }
            } else {
                it.activeNetworkInfo?.let { networkInfo ->
                    result = when (networkInfo.type) {
                        ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}