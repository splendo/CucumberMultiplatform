package com.corrado4eyes.cucumberplayground.test.definitions

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumberplayground.test.utils.BaseScenarioHolder
import com.corrado4eyes.cucumbershared.tests.TestCase

class LoginStepDefinitions : BaseScenarioHolder() {
    init {
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
    }
}