package com.corrado4eyes.cucumberplayground.android

import android.app.Application
import com.corrado4eyes.cucumberplayground.di.initKoin

class CucumberApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}