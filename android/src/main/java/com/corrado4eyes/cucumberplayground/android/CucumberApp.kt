package com.splendo.cucumberplayground.android

import android.app.Application
import com.splendo.cucumberplayground.di.initKoin
import com.splendo.kaluga.base.ApplicationHolder

class CucumberApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationHolder.application = this
        initKoin()
    }
}