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
import com.corrado4eyes.cucumber.errors.UIElementException
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumberplayground.models.Strings
import com.corrado4eyes.cucumbershared.tests.Definitions
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
        Definitions.values().forEach {
            val definitionString = it.definition.definitionString
            when (it) {
                Definitions.SCREEN_IS_VISIBLE -> Given(definitionString) { screenName: String ->
                        val screenTitleTag = when (screenName) {
                            Strings.loginScreenTag -> {
                                arguments["isLoggedIn"] = "false"
                                arguments["testEmail"] = ""
                                Strings.loginScreenTitle
                            }

                            Strings.homeScreenTag -> {
                                arguments["isLoggedIn"] = "true"
                                Strings.homeScreenTitle
                            }

                            else -> throw UIElementException.Screen.NotFound(screenName)
                        }
                        setLaunchScreen()
                        testRule.onNodeWithTag(screenTitleTag).assertIsDisplayed()
                }
                Definitions.TEXT_IS_VISIBLE -> Then(definitionString) { title: String ->
                    testRule.onNodeWithText(title).assertIsDisplayed()
                }
                Definitions.BUTTON_IS_VISIBLE -> Then(definitionString) { buttonName: String ->
                    when (buttonName) {
                        Strings.loginButtonText -> testRule.onNodeWithText(Strings.loginButtonText).assertIsDisplayed().assertHasClickAction()
                        Strings.logoutButtonText -> testRule.onNodeWithTag(Strings.logoutButtonText).assertIsDisplayed().assertHasClickAction()
                        else -> throw UIElementException.Button.NotFound(buttonName)
                    }
                }
                Definitions.NAVIGATE_TO_SCREEN -> Then(definitionString) { screenName: String ->
                    Thread.sleep(1000)
                    when (screenName) {
                        Strings.loginScreenTag -> testRule.onNodeWithTag(Strings.loginScreenTitle).assertIsDisplayed()
                        Strings.homeScreenTag -> testRule.onNodeWithTag(Strings.homeScreenTitle).assertIsDisplayed()
                        else -> throw UIElementException.Screen.NotFound(screenName)
                    }
                }
                Definitions.TEXTFIELD_IS_VISIBLE -> Then(definitionString) { tag: String, text: String ->
                    val elementNode = try {
                        testRule.onNodeWithTag(tag).assertIsDisplayed()
                    } catch (e: AssertionError) {
                        throw UIElementException.TextField.NotFound(tag)
                    }

                    elementNode.assertTextContains(text)
                }
                Definitions.FILL_TEXTFIELD -> When(definitionString) { textInput: String, tag: String,  ->
                    testRule.onNodeWithText(tag).performTextInput(textInput)
                }
                Definitions.FILL_SECURE_TEXTFIELD -> When(definitionString) { textInput: String, tag: String ->
                    testRule.onNodeWithText(tag).performTextInput(textInput)
                }
                Definitions.PRESS_BUTTON -> When(definitionString) { buttonName: String ->
                    val elementNode = try {
                        testRule.onNodeWithTag(buttonName).assertIsDisplayed()
                    } catch (e: AssertionError) {
                        throw UIElementException.TextField.NotFound(buttonName)
                    }
                    elementNode.performClick()
                }
                Definitions.USER_IS_LOGGED_IN -> Given(definitionString) { loggedInEmail: String ->
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