package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.Definition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinTestCase
import com.corrado4eyes.cucumberplayground.models.Strings
import com.corrado4eyes.pistakio.ApplicationAdapter
import com.corrado4eyes.pistakio.AssertionResult
import com.corrado4eyes.pistakio.TestCase
import com.corrado4eyes.pistakio.TimeoutDuration
import com.corrado4eyes.pistakio.errors.UIElementException

enum class Definitions(override val definition: Definition): GherkinTestCase<Definition> {
    I_AM_IN_THE_EXPECT_VALUE_STRING_SCREEN(
        CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")
    ),
    I_SEE_EXPECT_VALUE_STRING_TEXT(
        CucumberDefinition.Step.Then("I see $EXPECT_VALUE_STRING text")
    ),
    I_SEE_THE_EXPECT_VALUE_STRING_BUTTON(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING button")
    ),
    I_SEE_THE_EXPECT_VALUE_STRING_SCREEN(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING screen")
    ),
    I_SEE_THE_EXPECT_VALUE_STRING_TEXT_FIELD_WITH_TEXT_EXPECT_VALUE_STRING(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")
    ),
    I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_TEXT_FIELD(
        CucumberDefinition.Step.When("I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING textfield")
    ),
    I_PRESS_THE_EXPECT_VALUE_STRING_BUTTON(
        CucumberDefinition.Step.When("I press the $EXPECT_VALUE_STRING button")
    ),
    EMAIL_IS_EXPECT_VALUE_STRING(
        CucumberDefinition.Step.Given("Email is $EXPECT_VALUE_STRING")
    ),
    I_SEE_EXPECT_VALUE_STRING_IN_THE_SCROLLVIEW(
        CucumberDefinition.Step.Then("I see $EXPECT_VALUE_STRING in the scrollview")
    );

    companion object {
        val allCases: List<Definitions> = Definitions.values().toList()
    }
}

sealed class AppDefinitions : TestCase {
    abstract val definition: Definition

    sealed class CrossPlatform(
        protected val application: ApplicationAdapter,
        args: List<String>?
    ) : TestCase.CrossPlatform, AppDefinitions() {

        protected val args = args ?: emptyList()

        class IAmInScreen(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")

            override fun runAndGetAssertions(): List<AssertionResult> {

                val screenTitleTag = when (val screenName = args[0]) {
                    Strings.Screen.Title.login -> {
                        application["isLoggedIn"] = "false"
                        application["testEmail"] = ""
                        Strings.Screen.Tag.login
                    }

                    Strings.Screen.Title.home -> {
                        application["isLoggedIn"] = "true"
                        Strings.Screen.Tag.home
                    }
                    else -> throw UIElementException.Screen.NotFound(screenName)
                }
                application.launch("MainActivity", application.applicationArguments)
                val element = application.findView(screenTitleTag)
                return listOf(
                    element.waitExists(TimeoutDuration.SHORT)
                )
            }
        }

        class ISeeText(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.Then(
                "I see $EXPECT_VALUE_STRING text"
            )

            override fun runAndGetAssertions(): List<AssertionResult> {
                val textViewTitle = args[0]
                val element = application.findView(textViewTitle)
                return listOf(element.waitExists(TimeoutDuration.SHORT), element.isVisible())
            }
        }

        class ISeeButton(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.Then(
                "I see the $EXPECT_VALUE_STRING button"
            )

            override fun runAndGetAssertions(): List<AssertionResult> {
                val element = when (val buttonTitle = args[0]) {
                    Strings.Button.Title.login -> application.findView(Strings.Button.Tag.login)
                    Strings.Button.Title.logout -> application.findView(Strings.Button.Tag.logout)
                    else -> throw UIElementException.Button.NotFound(buttonTitle)
                }
                return listOf(
                    element.waitExists(TimeoutDuration.SHORT),
                    element.isVisible(),
                    element.isButton()
                )
            }

        }
        class ISeeScreen(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING screen")

            override fun runAndGetAssertions(): List<AssertionResult> {
                val element  = when (val screenTitle = args[0]) {
                    Strings.Screen.Title.login -> application.findView(Strings.Screen.Tag.login)
                    Strings.Screen.Title.home -> application.findView(Strings.Screen.Tag.home)
                    else -> throw UIElementException.Screen.NotFound(screenTitle)
                }
                return listOf(element.exists())
            }
        }

        class ISeeTextFieldWithText(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")

            override fun runAndGetAssertions(): List<AssertionResult> {
                val textFieldTag = args[1]
                val textFieldText = args[1]
                val element = application.findView(textFieldTag)
                return listOf(
                    element.waitExists(TimeoutDuration.SHORT),
                    element.isHintEqualTo(textFieldText, true)
                )
            }
        }

        class ITypeTextIntoTextField(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.When(
                "I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING textfield"
            )

            override fun runAndGetAssertions(): List<AssertionResult> {
                val textInput = args[0]
                val tag = args[1]
                application
                    .findView(tag)
                    .typeText(textInput)
                return emptyList()
            }
        }

        class IPressTheButton(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.When(
                "I press the $EXPECT_VALUE_STRING button"
            )

            override fun runAndGetAssertions(): List<AssertionResult> {
                val element = when(val buttonTag = args[0]) {
                    Strings.Button.Title.login -> application.findView(Strings.Button.Tag.login)
                    Strings.Button.Title.logout -> application.findView(Strings.Button.Tag.logout)
                    else -> throw UIElementException.Button.NotFound(buttonTag)
                }
                application.assert(element.waitExists(TimeoutDuration.SHORT))
                element.tap()
                return emptyList()
            }
        }

        class SetLoggedInUserEmail(
            application: ApplicationAdapter,
            args: List<String>?
        ) : CrossPlatform(application, args) {
            override val definition: Definition = CucumberDefinition.Step.Given(
                "Email is $EXPECT_VALUE_STRING"
            )

            override fun runAndGetAssertions(): List<AssertionResult> {
                val loggedInEmail = args[0]
                application["testEmail"] = loggedInEmail
                return emptyList()
            }
        }
    }

    sealed class Platform : TestCase.Platform, AppDefinitions() {
        class ISeeValueInScrollView : Platform() {
            override val definition: Definition = CucumberDefinition.Step.Then(
                "I see $EXPECT_VALUE_STRING in the scrollview"
            )
        }
    }
}
