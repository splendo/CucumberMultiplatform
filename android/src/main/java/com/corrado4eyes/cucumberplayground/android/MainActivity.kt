package com.corrado4eyes.cucumberplayground.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.corrado4eyes.cucumberplayground.models.DefaultTestConfiguration
import com.corrado4eyes.cucumberplayground.models.TestConfiguration

class MainActivity : AppCompatActivity() {
    private lateinit var testConfiguration: TestConfiguration

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        testConfiguration = DefaultTestConfiguration(
            mapOf(
                "isLoggedIn" to intent?.getStringExtra("isLoggedIn"),
                "testEmail" to intent?.getStringExtra("testEmail")
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityLayout(testConfiguration)
        }
    }
}
