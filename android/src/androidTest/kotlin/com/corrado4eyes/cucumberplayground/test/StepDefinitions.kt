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
import io.cucumber.core.api.Scenario
import io.cucumber.java.After
import io.cucumber.java.AfterStep
import io.cucumber.java.Before
import io.cucumber.java.BeforeStep
import io.cucumber.java8.En
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class StepDefinitions : En {

    private val arguments = mutableMapOf<String, String>()
    private var scenario: ActivityScenario<*>? = null

    @get:Rule(order = 0)
    val testRule = createComposeRule()

    @Before(order = 0)
    fun beforeScenarioStart(scenario: Scenario) {
        // Will run before each scenario
        println("-----------------Start of Scenario ${scenario.name}-----------------")
    }

    @Before(order = 1)
    fun beforeScenario(scenario: Scenario) {
        // Will run before each scenario but second in order
        println("Running steps:")
    }

    @After(order = 1)
    fun afterScenarioFinish(scenario: Scenario) {
        // Will run after each scenario
        println("Steps completed")
    }

    @After(order = 0)
    fun afterScenario(scenario: Scenario) {
        // Will run after each scenario but second in order
        println("-----------------End of Scenario ${scenario.name}-----------------")
    }

    // TODO figure out for specific step, crashes the app with exception if you try @BeforeStep("some step")
    @BeforeStep
    fun beforeStep(scenario: Scenario) {
        // Run stuff before each scenario step
    }

    @AfterStep
    fun afterStep(scenario: Scenario) {
        // Run stuff after each scenario step
    }

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
        TestCase.Login.PressLoginButton(
            GherkinLambda0 {
                testRule.onNodeWithText("Login").assertIsDisplayed().performClick()
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

    private fun <T : Activity> launch(intent: Intent) {
        scenario = ActivityScenario.launch<T>(intent)
    }
}