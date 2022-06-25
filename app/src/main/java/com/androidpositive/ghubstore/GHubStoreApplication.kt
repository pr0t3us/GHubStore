package com.androidpositive.ghubstore

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GHubStoreApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
        }
    }
}

