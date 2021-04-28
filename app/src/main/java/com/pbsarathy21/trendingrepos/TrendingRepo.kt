package com.pbsarathy21.trendingrepos

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TrendingRepo : Application() {

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//            Stetho.initializeWithDefaults(this)
//        }

        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }
}