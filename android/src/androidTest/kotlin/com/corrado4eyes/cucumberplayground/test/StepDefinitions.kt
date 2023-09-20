package com.corrado4eyes.cucumberplayground.test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import com.corrado4eyes.pistakio.DefaultApplicationAdapter
import com.corrado4eyes.pistakio.TimeoutDuration
import com.corrado4eyes.pistakio.errors.UIElementException
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
    val application = DefaultApplicationAdapter(testRule)

    init {
        Definitions.values().forEach {
            val definitionString = it.definition.definitionString
            when (it) {
                Definitions.I_AM_IN_THE_EXPECT_VALUE_STRING_SCREEN -> Given(definitionString) { screenName: String ->
                    val screenTitleTag = when (screenName) {
                        Strings.Screen.Title.login -> {
                            arguments["isLoggedIn"] = "false"
                            arguments["testEmail"] = ""
                            Strings.Screen.Tag.login
                        }

                        Strings.Screen.Title.home -> {
                            arguments["isLoggedIn"] = "true"
                            Strings.Screen.Tag.home
                        }

                        else -> throw UIElementException.Screen.NotFound(screenName)
                    }

                    application.launch("MainActivity", arguments)
                    val element = application.findView(screenTitleTag)

                    application.assert(element.waitExists(TimeoutDuration.SHORT))
                }
                Definitions.I_SEE_EXPECT_VALUE_STRING_TEXT -> Then(definitionString) { textViewTitle: String ->
                    val element = application.findView(textViewTitle)
                    application.assert(element.waitExists(TimeoutDuration.SHORT))
                    application.assert(element.isVisible())
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_BUTTON -> Then(definitionString) { buttonTitle: String ->
                    val element = when (buttonTitle) {
                        Strings.Button.Title.login -> application.findView(Strings.Button.Tag.login)/*testRule.onNodeWithText(Strings.Button.Text.login)*/
                        Strings.Button.Title.logout -> application.findView(Strings.Button.Tag.logout)/*testRule.onNodeWithTag(Strings.Button.Text.logout)*/
                        else -> throw UIElementException.Button.NotFound(buttonTitle)
                    }
                    application.assert(element.waitExists(TimeoutDuration.SHORT))
                    application.assert(element.isVisible())
                    application.assert(element.isButton())
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_SCREEN -> Then(definitionString) { screenTitle: String ->
                    val element  = when (screenTitle) {
                        Strings.Screen.Title.login -> application.findView(Strings.Screen.Tag.login)/*testRule.onNodeWithTag(Strings.Screen.Title.login).assertIsDisplayed()*/
                        Strings.Screen.Title.home -> application.findView(Strings.Screen.Tag.home)/*testRule.onNodeWithTag(Strings.Screen.Title.home).assertIsDisplayed()*/
                        else -> throw UIElementException.Screen.NotFound(screenTitle)
                    }
                    application.assertUntil(TimeoutDuration.SHORT, element::exists)
                }

                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_TEXT_FIELD_WITH_TEXT_EXPECT_VALUE_STRING -> Then(definitionString) { textFieldTag: String, textFieldText: String ->
                    val element = application.findView(textFieldTag)
                    application.assert(element.waitExists(TimeoutDuration.SHORT))
                    application.assert(element.isHintEqualTo(textFieldText, true))
                }
                Definitions.I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_TEXT_FIELD -> When(definitionString) { textInput: String, tag: String,  ->
                    application.findView(tag).typeText(textInput)
                }
                Definitions.I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_SECURE_TEXT_FIELD -> When(definitionString) { textInput: String, tag: String ->
                    application.findView(tag).typeText(textInput)
                }
                Definitions.I_PRESS_THE_EXPECT_VALUE_STRING_BUTTON -> When(definitionString) { buttonTag: String ->
                    val element = when(buttonTag) {
                        Strings.Button.Title.login -> application.findView(Strings.Button.Tag.login)
                        Strings.Button.Title.logout -> application.findView(Strings.Button.Tag.logout)
                        else -> throw UIElementException.Button.NotFound(buttonTag)
                    }
                    application.assert(element.waitExists(TimeoutDuration.SHORT))
                    element.tap()
                }
                Definitions.EMAIL_IS_EXPECT_VALUE_STRING -> Given(definitionString) { loggedInEmail: String ->
                    arguments["testEmail"] = loggedInEmail
                }
            }
        }
    }
}