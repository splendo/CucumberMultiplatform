package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.Definition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumberplayground.models.Strings
import com.corrado4eyes.pistakio.ApplicationAdapter
import com.corrado4eyes.pistakio.ApplicationArguments
import com.corrado4eyes.pistakio.AssertionResult
import com.corrado4eyes.pistakio.Node
import com.corrado4eyes.pistakio.TestCase
import com.corrado4eyes.pistakio.TimeoutDuration
import com.corrado4eyes.pistakio.errors.UIElementException

sealed class AppDefinitions : TestCase {
    abstract val definition: Definition

    companion object {
        val defaultApplicationAdapter = object : ApplicationAdapter {
            override val applicationArguments: ApplicationArguments
                get() = TODO("Not yet implemented")

            override fun launch(identifier: String?, arguments: Map<String, String>) {
                TODO("Not yet implemented")
            }

            override fun findView(tag: String): Node {
                TODO("Not yet implemented")
            }

            override fun tearDown() {
                TODO("Not yet implemented")
            }

            override fun assert(assertionResult: AssertionResult) {
                TODO("Not yet implemented")
            }

            override fun assertUntil(
                timeout: TimeoutDuration,
                blockAssertionResult: () -> AssertionResult
            ) {
                TODO("Not yet implemented")
            }

            override fun assertAll(assertions: List<AssertionResult>) {
                TODO("Not yet implemented")
            }

            override fun get(key: String): String? {
                TODO("Not yet implemented")
            }

            override fun set(key: String, value: String) {
                TODO("Not yet implemented")
            }
        }

        val allCases = listOf(
            CrossPlatform.IAmInScreen("", defaultApplicationAdapter, listOf()),
            CrossPlatform.IPressTheButton(defaultApplicationAdapter, listOf()),
            CrossPlatform.ISeeButton(defaultApplicationAdapter, listOf()),
            CrossPlatform.ISeeScreen(defaultApplicationAdapter, listOf()),
            CrossPlatform.ISeeText(defaultApplicationAdapter, listOf()),
            CrossPlatform.ISeeTextFieldWithText(defaultApplicationAdapter, listOf()),
            CrossPlatform.ITypeTextIntoTextField(defaultApplicationAdapter, listOf()),
            CrossPlatform.SetLoggedInUserEmail(defaultApplicationAdapter, listOf()),
            Platform.ISeeValueInScrollView
        )
    }

    sealed class CrossPlatform(
        protected val application: ApplicationAdapter,
        args: List<String>?
    ) : TestCase.CrossPlatform, AppDefinitions() {

        protected val args = args ?: emptyList()

        class IAmInScreen(
            private val launchScreenName: String? = null,
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
                application.launch(launchScreenName, application.applicationArguments)
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
        object ISeeValueInScrollView : Platform() {
            override val definition: Definition = CucumberDefinition.Step.Then(
                "I see $EXPECT_VALUE_STRING in the scrollview"
            )
        }
    }
}
