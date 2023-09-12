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
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumbershared.tests.TestCases
import io.cucumber.java8.En
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class StepDefinitions : En {

    private val arguments = mutableMapOf<String, String>()
    private var scenario: ActivityScenario<*>? = null

    @get:Rule(order = 0)
    val testRule = createComposeRule()

    init {
        TestCases.values().forEach {
            val definitionString = it.definition.definitionString
            when (it) {
                TestCases.SCREEN_IS_VISIBLE -> Given(definitionString) { screenName: String ->
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
                TestCases.TEXT_IS_VISIBLE -> Then(definitionString) { title: String ->
                    testRule.onNodeWithText(title).assertIsDisplayed()
                }
                TestCases.BUTTON_IS_VISIBLE -> Then(definitionString) { buttonName: String ->
                    when (buttonName) {
                        "Login" -> testRule.onNodeWithText("Login").assertIsDisplayed().assertHasClickAction()
                        "Logout" -> testRule.onNodeWithTag("Logout").assertIsDisplayed().assertHasClickAction()
                        else -> throw IllegalArgumentException("Couldn't find $it button")
                    }
                }
                TestCases.NAVIGATE_TO_SCREEN -> Then(definitionString) { screenName: String ->
                    Thread.sleep(1000)
                    when (screenName) {
                        "Login" -> testRule.onNodeWithTag("Login screen").assertIsDisplayed()
                        "Home" -> testRule.onNodeWithTag("Home screen").assertIsDisplayed()
                    }
                }
                TestCases.TEXTFIELD_IS_VISIBLE -> Then(definitionString) { tag: String, text: String ->
                    testRule.onNodeWithTag(tag).assertIsDisplayed().assertTextContains(text)
                }
                TestCases.FILL_TEXTFIELD -> Then(definitionString) { textInput: String, tag: String,  ->
                    testRule.onNodeWithText(tag).performTextInput(textInput)
                }
                TestCases.FILL_SECURE_TEXTFIELD -> Then(definitionString) { textInput: String, tag: String ->
                    testRule.onNodeWithText(tag).performTextInput(textInput)
                }
                TestCases.PRESS_BUTTON -> Then(definitionString) { buttonName: String ->
                    testRule.onNodeWithTag(buttonName).assertIsDisplayed().performClick()
                }
                TestCases.USER_IS_LOGGED_IN -> Given(definitionString) { loggedInEmail: String ->
                    arguments["testEmail"] = loggedInEmail
                }
            }
        }
    }

    private fun setLaunchScreen() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        launch<MainActivity>(
            Intent(instrumentation.targetContext, MainActivity::class.java)
                .putExtra("isLoggedIn", arguments["isLoggedIn"])
                .putExtra("testEmail", arguments["testEmail"])
        )
    }

    private fun <T: Activity> launch(intent: Intent) {
        scenario = ActivityScenario.launch<T>(intent)
    }
}