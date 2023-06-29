package com.corrado4eyes.cucumberplayground.test.utils

import android.app.Activity
import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import org.junit.Rule

interface ScenarioHolder {
    fun <T: Activity> launch(intent: Intent)
}

abstract class BaseScenarioHolder : ScenarioHolder {
    @get:Rule
    protected val testRule = createComposeRule()

    private var scenario: ActivityScenario<*>? = null

    override fun <T: Activity> launch(intent: Intent) {
        scenario = ActivityScenario.launch<T>(intent)
    }
}