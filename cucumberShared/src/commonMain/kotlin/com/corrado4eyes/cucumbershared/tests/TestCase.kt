package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumber.GherkinTestCase

sealed class TestCase<T: GherkinLambda> : GherkinTestCase<T> {
    sealed class Common<T: GherkinLambda> : TestCase<T>() {

        class TextIsVisible(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING text", lambda)
        }

        class ScreenIsVisible(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.GivenSingle("I see the $EXPECT_VALUE_STRING screen", lambda)
        }

        class ButtonIsVisible(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING button", lambda)
        }

        class PressButton(override val lambda: GherkinLambda1): Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I press the $EXPECT_VALUE_STRING button", lambda)
        }

        class TextFieldIsVisible(override val lambda: GherkinLambda2) : Common<GherkinLambda2>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenMultiple("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING", lambda)
        }
        class FillTextField(override val lambda: GherkinLambda2) : Common<GherkinLambda2>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenMultiple("I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING field", lambda)
        }

        class FillPasswordTextField(override val lambda: GherkinLambda2) : Common<GherkinLambda2>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenMultiple("I type $EXPECT_VALUE_STRING in the password field called $EXPECT_VALUE_STRING", lambda)
        }

        class NavigateToScreen(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition.Step.ThenSingle = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING screen", lambda)
        }

        class LoggedInEmail(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition.Step.GivenSingle = CucumberDefinition.Step.GivenSingle("Email is $EXPECT_VALUE_STRING", lambda)
        }
    }
}