package com.corrado4eyes.cucumberplayground.test.definitions

import android.content.Intent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumberplayground.test.utils.BaseScenarioHolder
import com.corrado4eyes.cucumbershared.tests.TestCase
import io.cucumber.junit.WithJunitRule

@WithJunitRule
class StepDefinitions : BaseScenarioHolder() {

    private val arguments = mutableMapOf<String, String>()

    init {
        TestCase.Common.ScreenIsVisible(
            GherkinLambda1 { screenName ->
                val screenTitleTag = when (screenName) {
                    "Login" -> {
                        arguments["isLoggedIn"] = "false"
                        arguments["testEmail"] = ""
                        "Login screen"
                    }
                    "Home" -> {
                        arguments["isLoggedIn"] = "true"
                        "Home screen"
                    }

                    else -> throw IllegalArgumentException("Couldn't find any $screenName screen")
                }
                setLaunchScreen()
                testRule.onNodeWithTag(screenTitleTag).assertIsDisplayed()
            }
        )
        TestCase.Common.TitleIsVisible(
            GherkinLambda1 {
                val title = it
                testRule.onNodeWithText(title).assertIsDisplayed()
            }
        )

        TestCase.Common.ButtonIsVisible(
            GherkinLambda1 {
                when (it) {
                    "Login" -> testRule.onNodeWithText("Login").assertIsDisplayed().assertHasClickAction()
                    "Logout" -> testRule.onNodeWithTag("Logout").assertIsDisplayed().assertHasClickAction()
                    else -> throw IllegalArgumentException("Couldn't find $it button")
                }
            }
        )
        TestCase.Common.NavigateToScreen(
            GherkinLambda1 {
                Thread.sleep(1000)
                when (it) {
                    "Login" -> testRule.onNodeWithTag("Login screen").assertIsDisplayed()
                    "Home" -> testRule.onNodeWithTag("Home screen").assertIsDisplayed()
                }
            }
        )
        TestCase.Home.PressLogoutButton(
            GherkinLambda0 {
                testRule.onNodeWithTag("Logout").assertIsDisplayed().performClick()
            }
        )
        TestCase.Home.LoggedInEmail(
            GherkinLambda1 {
                arguments["testEmail"] = "test@test.com"
            }
        )
    }

    private fun setLaunchScreen() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        launch<MainActivity>(
            Intent(instrumentation.targetContext, MainActivity::class.java)
                .putExtra("isLoggedIn", arguments["isLoggedIn"])
                .putExtra("testEmail", arguments["testEmail"])
        )
    }
}