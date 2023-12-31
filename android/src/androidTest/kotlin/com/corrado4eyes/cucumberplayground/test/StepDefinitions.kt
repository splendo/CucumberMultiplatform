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
                Definitions.I_AM_IN_THE_EXPECT_VALUE_STRING_SCREEN -> Given(definitionString) { screenName: String ->
                        val screenTitleTag = when (screenName) {
                            Strings.Screen.Tag.login -> {
                                arguments["isLoggedIn"] = "false"
                                arguments["testEmail"] = ""
                                Strings.Screen.Title.login
                            }

                            Strings.Screen.Tag.home -> {
                                arguments["isLoggedIn"] = "true"
                                Strings.Screen.Title.home
                            }

                            else -> throw UIElementException.Screen.NotFound(screenName)
                        }
                        setLaunchScreen()
                        testRule.onNodeWithTag(screenTitleTag).assertIsDisplayed()
                }
                Definitions.I_SEE_EXPECT_VALUE_STRING_TEXT -> Then(definitionString) { title: String ->
                    testRule.onNodeWithText(title).assertIsDisplayed()
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_BUTTON -> Then(definitionString) { buttonName: String ->
                    when (buttonName) {
                        Strings.Button.Text.login -> testRule.onNodeWithText(Strings.Button.Text.login).assertIsDisplayed().assertHasClickAction()
                        Strings.Button.Text.logout -> testRule.onNodeWithTag(Strings.Button.Text.logout).assertIsDisplayed().assertHasClickAction()
                        else -> throw UIElementException.Button.NotFound(buttonName)
                    }
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_SCREEN -> Then(definitionString) { screenName: String ->
                    Thread.sleep(1000)
                    when (screenName) {
                        Strings.Screen.Tag.login -> testRule.onNodeWithTag(Strings.Screen.Title.login).assertIsDisplayed()
                        Strings.Screen.Tag.home -> testRule.onNodeWithTag(Strings.Screen.Title.home).assertIsDisplayed()
                        else -> throw UIElementException.Screen.NotFound(screenName)
                    }
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_TEXT_FIELD_WITH_TEXT_EXPECT_VALUE_STRING -> Then(definitionString) { tag: String, text: String ->
                    val elementNode = try {
                        testRule.onNodeWithTag(tag).assertIsDisplayed()
                    } catch (e: AssertionError) {
                        throw UIElementException.TextField.NotFound(tag)
                    }

                    elementNode.assertTextContains(text)
                }
                Definitions.I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_TEXT_FIELD -> When(definitionString) { textInput: String, tag: String,  ->
                    testRule.onNodeWithText(tag).performTextInput(textInput)
                }
                Definitions.I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_SECURE_TEXT_FIELD -> When(definitionString) { textInput: String, tag: String ->
                    testRule.onNodeWithText(tag).performTextInput(textInput)
                }
                Definitions.I_PRESS_THE_EXPECT_VALUE_STRING_BUTTON -> When(definitionString) { buttonName: String ->
                    val elementNode = try {
                        testRule.onNodeWithTag(buttonName).assertIsDisplayed()
                    } catch (e: AssertionError) {
                        throw UIElementException.TextField.NotFound(buttonName)
                    }
                    elementNode.performClick()
                }
                Definitions.EMAIL_IS_EXPECT_VALUE_STRING -> Given(definitionString) { loggedInEmail: String ->
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