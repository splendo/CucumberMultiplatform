package com.corrado4eyes.cucumberplayground.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.corrado4eyes.cucumber.DefaultGherkinRunner
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.tests.TestCase
import com.corrado4eyes.cucumberplayground.android.MainActivity
import com.corrado4eyes.cucumberplayground.android.MainActivityLayout
import com.corrado4eyes.cucumberplayground.models.TestConfiguration
import io.cucumber.java8.En
import org.junit.Rule
import org.junit.Test

class StepDefinitions : En {
    @get:Rule
    val testRule = createComposeRule()

    init {
        DefaultGherkinRunner(
            listOf(
                TestCase.Common.ScreenIsVisible(
                    GherkinLambda {
                        val screenName = it
                        setLaunchScreen(screenName)
                        when (screenName) {
                            "Login" -> testRule.onNodeWithText("Login").assertIsDisplayed()
                            "Home" -> testRule.onNodeWithText("Home").assertIsDisplayed()
                        }
                    }
                ),
                TestCase.Common.TitleIsVisible(
                    GherkinLambda {
                        val title = it
                        testRule.onNodeWithText(title).assertIsDisplayed()
                    }
                ),
                TestCase.Login.Common.TextFieldIsVisible(
                    GherkinLambda {
                        val tag = it
                        testRule.onNodeWithTag(tag).assertIsDisplayed()
                    }
                ),
                TestCase.Login.FillEmailTextField(
                    GherkinLambda {

                    }
                ),
                TestCase.Login.FillPasswordTextField(
                    GherkinLambda {

                    }
                ),
                TestCase.Common.ButtonIsVisible(
                    GherkinLambda {

                    }
                ),
                TestCase.Login.PressLoginButton(
                    GherkinLambda {

                    }
                ),
                TestCase.Home.LoggedInEmail(
                    GherkinLambda {

                    }
                ),
                TestCase.Common.NavigateToScreen(
                    GherkinLambda {

                    }
                ),
                TestCase.Home.PressLogoutButton(
                    GherkinLambda {

                    }
                )
            )
        ).buildFeature()
    }

    private fun setLaunchScreen(screenName: String) {
        when (screenName) {
            "Home" -> {
                testRule.setContent {
                    MainActivityLayout(TestConfiguration(mapOf()))
                }
            }
            "Login" -> {
                testRule.setContent {
                    MainActivityLayout(TestConfiguration(mapOf("isLoggedIn" to "true")))
                }
            }
            else -> throw IllegalStateException("Screen $screenName not found")
        }
    }
}