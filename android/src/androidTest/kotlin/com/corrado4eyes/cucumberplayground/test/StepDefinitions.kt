package com.corrado4eyes.cucumberplayground.test

import android.app.Activity
import android.content.Intent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumbershared.tests.TestCase
import io.cucumber.java8.En
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class StepDefinitions : En {
    @get:Rule
    val testRule = createComposeRule()

    private var scenario: ActivityScenario<*>? = null

    private fun <T: Activity> launch(intent: Intent){
        scenario = ActivityScenario.launch<T>(intent)
    }

    init {
        TestCase.Common.ScreenIsVisible(
            GherkinLambda1 { screenName ->
                setLaunchScreen(screenName)
                when (screenName) {
                    "Login" -> testRule.onNodeWithTag("Login screen").assertIsDisplayed()
                    "Home" -> testRule.onNodeWithTag("Home screen").assertIsDisplayed()
                }
            }
        )
        TestCase.Common.TitleIsVisible(
            GherkinLambda1 {
                val title = it
                testRule.onNodeWithText(title).assertIsDisplayed()
            }
        )
        TestCase.Login.Common.TextFieldIsVisible(
            GherkinLambda2 { tag, text ->
                testRule.onNodeWithTag(tag).assertIsDisplayed().assertTextContains(text)
            }
        )
        TestCase.Login.FillEmailTextField(
            GherkinLambda1 {
                testRule.onNodeWithText("Email").performTextInput(it)
            }
        )
        TestCase.Login.FillPasswordTextField(
            GherkinLambda1 {
                testRule.onNodeWithText("Password").performTextInput(it)
            }
        )
        TestCase.Common.ButtonIsVisible(
            GherkinLambda1 {
                when (it) {
                    "Login" -> testRule.onNodeWithText("Login").assertIsDisplayed().assertHasClickAction()
                    else -> throw IllegalArgumentException("Couldn't find $it button")
                }
            }
        )
        TestCase.Login.PressLoginButton(
            GherkinLambda0 {
                testRule.onNodeWithText("Login").assertIsDisplayed().performClick()
            }
        )
        TestCase.Home.LoggedInEmail(
            GherkinLambda1 {

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
    }

    private fun setLaunchScreen(screenName: String, arguments: Map<String, String> = mapOf()) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        launch<MainActivity>(
            Intent(instrumentation.targetContext, MainActivity::class.java)
                .putExtra("isLoggedIn", "false")
                .putExtra("testEmail", "")
        )

//        when (screenName) {
//            "Home" -> {
//                testRule.setContent {
//                    MainActivityLayout(TestConfiguration(mapOf()))
//                }
//            }
//
//            "Login" -> {
//                testRule.setContent {
//                    MainActivityLayout(TestConfiguration(mapOf("isLoggedIn" to "true")))
//                }
//            }
//
//            else -> throw IllegalStateException("Screen $screenName not found")
//        }
    }
}