package com.corrado4eyes.cucumberplayground.test

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
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumbershared.tests.TestCase
import io.cucumber.java.After
import io.cucumber.java.AfterStep
import io.cucumber.java.Before
import io.cucumber.java.BeforeStep
import io.cucumber.java8.En
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class CommonStepDefinitions : En {

    private val arguments = mutableMapOf<String, String>()
    private var scenario: ActivityScenario<*>? = null

    @get:Rule(order = 0)
    val testRule = createComposeRule()

    @Before(order = 0)
    fun beforeScenarioStart1(scenario: io.cucumber.core.api.Scenario) {
        // Will run before each scenario
        println("-----------------Start of Scenario ${scenario.name}-----------------")
        when (val scenarioName = scenario.name) {
            "Failed attempt with wrong credentials",
            "Failed attempt with empty email",
            "Failed attempt with empty password",
            "Successful attempt" -> {
                arguments["isLoggedIn"] = "false"
                arguments["testEmail"] = ""
            }
            "Logout" -> {
                arguments["isLoggedIn"] = "true"
                arguments["testEmail"] = "test@test.com"
            }

            else -> throw IllegalArgumentException("Couldn't find scenario: $scenarioName ")
        }
        launchApp()
    }

    @After(order = 0)
    fun afterScenarioFinish2(cucuScenario: io.cucumber.core.api.Scenario) {
        // Will run after each scenario but first in order
        println("-----------------End of Scenario ${cucuScenario.name}-----------------")
    }

    // TODO figure out for specific step, crashes the app with exception if you try @BeforeStep("some step")
    @BeforeStep(order = 0)
    fun beforeStep(scenario: io.cucumber.core.api.Scenario) {
        // Run stuff before each scenario step
    }

    @AfterStep(order = 0)
    fun afterStep(scenario: io.cucumber.core.api.Scenario) {
        // Run stuff after each scenario step
    }

    init {
        TestCase.Common.TextIsVisible(
            GherkinLambda1 {
                testRule.onNodeWithText(it).assertIsDisplayed()
            }
        )
        TestCase.Common.ScreenIsVisible(
            GherkinLambda1 {
                testRule.onNodeWithText(it).assertIsDisplayed()
            }
        )
        TestCase.Common.ButtonIsVisible(
            GherkinLambda1 {
                testRule.onNodeWithText(it).assertIsDisplayed().assertHasClickAction()
            }
        )
        // TODO make into generic view is visible with tag is swift ui supports it
        TestCase.Common.TextFieldIsVisible(
            GherkinLambda2 { tag, text ->
                testRule.onNodeWithTag(tag).assertIsDisplayed().assertTextContains(text)
            }
        )
        TestCase.Common.FillTextField(
            GherkinLambda2 { input, tag ->
                testRule.onNodeWithText(tag).assertIsDisplayed().performTextInput(input)
            }
        )
        TestCase.Common.PressButton(
            GherkinLambda1 {
                testRule.onNodeWithText(it).assertIsDisplayed().performClick()
            }
        )
    }

    private fun launchApp() {
        // TODO figure out if destroying activity is needed after each test (try reordering Login.Feature scenarios to get into situations where this matters)
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        scenario = ActivityScenario.launch<MainActivity>(
            Intent(instrumentation.targetContext, MainActivity::class.java)
                .putExtra("isLoggedIn", arguments["isLoggedIn"])
                .putExtra("testEmail", arguments["testEmail"])
        )
    }
}
