package com.corrado4eyes.cucumberplayground.test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import com.corrado4eyes.cucumbershared.tests.Definitions
import com.corrado4eyes.cucumbershared.tests.SealedDefinitions
import com.corrado4eyes.pistakio.DefaultApplicationAdapter
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
                    val assertions = SealedDefinitions.IAmInScreen(application, listOf(screenName)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                Definitions.I_SEE_EXPECT_VALUE_STRING_TEXT -> Then(definitionString) { textViewTitle: String ->
                    val assertions = SealedDefinitions.ISeeText(application, listOf(textViewTitle)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_BUTTON -> Then(definitionString) { buttonTitle: String ->
                    val assertions = SealedDefinitions.ISeeButton(application, listOf(buttonTitle)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_SCREEN -> Then(definitionString) { screenTitle: String ->
                    val assertions = SealedDefinitions.ISeeScreen(application, listOf(screenTitle)).runAndGetAssertions()
                    application.assertAll(assertions)
                }

                Definitions.I_SEE_THE_EXPECT_VALUE_STRING_TEXT_FIELD_WITH_TEXT_EXPECT_VALUE_STRING -> Then(definitionString) { textFieldTag: String, textFieldText: String ->
                    val assertions = SealedDefinitions.ISeeTextFieldWithText(application, listOf(textFieldTag, textFieldText)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                Definitions.I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_TEXT_FIELD -> When(definitionString) { textInput: String, tag: String,  ->
                    val assertions = SealedDefinitions.ITypeTextIntoTextField(application, listOf(textInput, tag)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                Definitions.I_PRESS_THE_EXPECT_VALUE_STRING_BUTTON -> When(definitionString) { buttonTag: String ->
                    val assertions = SealedDefinitions.IPressTheButton(application, listOf(buttonTag)).runAndGetAssertions()
                    application.assertAll(assertions)
                }
                Definitions.EMAIL_IS_EXPECT_VALUE_STRING -> Given(definitionString) { loggedInEmail: String ->
                    application.assertAll(SealedDefinitions.SetLoggedInUserEmail(application, listOf(loggedInEmail)).runAndGetAssertions())
                }
            }
        }
    }
}