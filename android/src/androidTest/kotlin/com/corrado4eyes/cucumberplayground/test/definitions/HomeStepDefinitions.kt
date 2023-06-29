package com.corrado4eyes.cucumberplayground.test.definitions

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumberplayground.test.utils.BaseScenarioHolder
import com.corrado4eyes.cucumbershared.tests.TestCase

class HomeStepDefinitions : BaseScenarioHolder() {
    init {
        TestCase.Home.PressLogoutButton(
            GherkinLambda0 {
                testRule.onNodeWithTag("Logout").assertIsDisplayed().performClick()
            }
        )
        TestCase.Home.LoggedInEmail(
            GherkinLambda1 {

            }
        )
    }
}