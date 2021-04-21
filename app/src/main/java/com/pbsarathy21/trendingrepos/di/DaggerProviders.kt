package com.pbsarathy21.trendingrepos.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pbsarathy21.trendingrepos.BuildConfig
import com.pbsarathy21.trendingrepos.network.APIService
import com.pbsarathy21.trendingrepos.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerProviders {

    @Provides
    fun provideBaseURL() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(networkConnectionInterceptor)
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .addInterceptor {
                val requestBuilder: Request.Builder = it.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header("Accept", "application/json")
                it.proceed(requestBuilder.build())
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}