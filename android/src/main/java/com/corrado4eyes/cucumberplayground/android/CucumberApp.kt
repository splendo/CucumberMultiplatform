package com.corrado4eyes.cucumberplayground.android

import android.app.Application
import com.corrado4eyes.cucumberplayground.di.initKoin
import com.splendo.kaluga.base.ApplicationHolder

class CucumberApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationHolder.application = this
        initKoin()
    }
}