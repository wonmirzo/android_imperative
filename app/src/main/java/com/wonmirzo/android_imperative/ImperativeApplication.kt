package com.wonmirzo.android_imperative

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImperativeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}