package com.corrado4eyes.cucumberplayground.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.corrado4eyes.cucumberplayground.models.Strings
import com.corrado4eyes.cucumbershared.tests.AppDefinitions
import com.corrado4eyes.pistakio.DefaultApplicationAdapter
import io.cucumber.java8.En
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class StepDefinitions : En {
    @get:Rule(order = 0)
    val testRule = createComposeRule()
    private val application = DefaultApplicationAdapter(testRule)

    init {
        AppDefinitions.allCases.forEach {
            val definitionString = it.definition.definitionString
            when (it) {
                is AppDefinitions.CrossPlatform.IAmInScreen -> Given(definitionString) { screenName: String ->
                    val assertions = AppDefinitions.CrossPlatform.IAmInScreen(
                        "MainActivity",
                        application,
                        listOf(screenName)
                    ).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                is AppDefinitions.CrossPlatform.ISeeText -> Then(definitionString) { textViewTitle: String ->
                    val assertions = AppDefinitions.CrossPlatform.ISeeText(application, listOf(textViewTitle)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                is AppDefinitions.CrossPlatform.ISeeButton -> Then(definitionString) { buttonTitle: String ->
                    val assertions = AppDefinitions.CrossPlatform.ISeeButton(application, listOf(buttonTitle)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                is AppDefinitions.CrossPlatform.ISeeScreen -> Then(definitionString) { screenTitle: String ->
                    val assertions = AppDefinitions.CrossPlatform.ISeeScreen(application, listOf(screenTitle)).runAndGetAssertions()
                    application.assertAll(assertions)
                }

                is AppDefinitions.CrossPlatform.ISeeTextFieldWithText -> Then(definitionString) { textFieldTag: String, textFieldText: String ->
                    val assertions = AppDefinitions.CrossPlatform.ISeeTextFieldWithText(application, listOf(textFieldTag, textFieldText)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                is AppDefinitions.CrossPlatform.ITypeTextIntoTextField -> When(definitionString) { textInput: String, tag: String,  ->
                    val assertions = AppDefinitions.CrossPlatform.ITypeTextIntoTextField(application, listOf(textInput, tag)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                is AppDefinitions.CrossPlatform.IPressTheButton -> When(definitionString) { buttonTag: String ->
                    val assertions = AppDefinitions.CrossPlatform.IPressTheButton(application, listOf(buttonTag)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                is AppDefinitions.CrossPlatform.SetLoggedInUserEmail -> Given(definitionString) { loggedInEmail: String ->
                    application.assertAll(AppDefinitions.CrossPlatform.SetLoggedInUserEmail(application, listOf(loggedInEmail)).runAndGetAssertions())
                }
                is AppDefinitions.Platform.ISeeValueInScrollView -> When(definitionString) { index: String ->

                    // Platform implementation
                    testRule
                        .onNodeWithTag(Strings.ScrollView.Tag.homeScrollView)
                        .performScrollToIndex(index.toInt())

                    testRule.onNodeWithText(index).assertIsDisplayed()

                    // Cross-platform implementation
//                    val element = application.findView(Strings.ScrollView.Tag.homeScrollView)
//                    element.swipeUntilIndex(index.toInt())
//                    val text = application.findView(index)
//                    application.assert(text.isVisible())
                }

            }
        }
    }
}