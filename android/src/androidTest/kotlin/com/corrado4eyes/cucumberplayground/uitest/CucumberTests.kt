package com.corrado4eyes.cucumberplayground.uitest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import com.corrado4eyes.cucumber.DefaultGherkinRunner
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.tests.TestCase
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumberplayground.android.MainActivityLayout
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.corrado4eyes.cucumberplayground.uitest"]
)
class RunCucumberTest {
    @get:Rule
    val testRule = createComposeRule()

    init {
        DefaultGherkinRunner(
            listOf(
                TestCase.Common.ScreenIsVisible(
                    GherkinLambda {
                        val screenName = it.firstOrNull() ?: throw IllegalArgumentException("A screen name must be passed")

                        setLaunchScreen(screenName)

                        when (screenName) {
                            "Home" -> testRule.onNodeWithText("Home").assertIsDisplayed()
                        }
                    }
                ),
                TestCase.Common.TitleIsVisible(
                    GherkinLambda {
                        val title = it.first()
                        testRule.onNodeWithText(title).assertIsDisplayed()
                    }
                )
            )
        ).buildFeature()
    }

    private fun setLaunchScreen(screenName: String) {
        when (screenName) {
            "Home" -> {
                testRule.setContent {
                    MainActivityLayout()
                }
            }
            else -> throw IllegalStateException("Screen $screenName not found")
        }
    }
}