package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.Definition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumber.GherkinTestCase

sealed class TestCase<CD: Definition, T: GherkinLambda> : GherkinTestCase<CD, T> {
    sealed class Common<CD: Definition, T: GherkinLambda> : TestCase<CD, T>() {
        class ScreenIsVisible(override val lambda: GherkinLambda1) : Common<CucumberDefinition.Step.GivenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.GivenSingle = CucumberDefinition.Step.GivenSingle("I am in the $EXPECT_VALUE_STRING screen", lambda)
        }

        class TitleIsVisible(override val lambda: GherkinLambda1) : Common<CucumberDefinition.Step.ThenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.ThenSingle = CucumberDefinition.Step.ThenSingle("I see $EXPECT_VALUE_STRING text", lambda)
        }

        class ButtonIsVisible(override val lambda: GherkinLambda1) : Common<CucumberDefinition.Step.ThenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.ThenSingle = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING button", lambda)
        }

        class NavigateToScreen(override val lambda: GherkinLambda1) : Common<CucumberDefinition.Step.ThenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.ThenSingle = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING screen", lambda)
        }
    }

    sealed class Login <CD: Definition, T: GherkinLambda> : TestCase<CD, T>() {
        sealed class Common <CD: Definition, T: GherkinLambda> : Login<CD, T>() {
            class TextFieldIsVisible(override val lambda: GherkinLambda2) : Common<CucumberDefinition.Step.ThenMultiple, GherkinLambda2>() {
                override val step: CucumberDefinition.Step.ThenMultiple = CucumberDefinition.Step.ThenMultiple("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING", lambda)
            }
        }
        class FillEmailTextField(override val lambda: GherkinLambda1) : Login<CucumberDefinition.Step.ThenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.ThenSingle = CucumberDefinition.Step.ThenSingle("I type $EXPECT_VALUE_STRING in the email field", lambda)
        }
        class FillPasswordTextField(override val lambda: GherkinLambda1) : Login<CucumberDefinition.Step.ThenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.ThenSingle = CucumberDefinition.Step.ThenSingle("I type $EXPECT_VALUE_STRING in the password field", lambda)
        }

        class PressLoginButton(override val lambda: GherkinLambda0) : Login<CucumberDefinition.Step.Then, GherkinLambda0>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I press the login button", lambda)
        }
    }

    sealed class Home <CD: Definition, T: GherkinLambda> : TestCase<CD, T>() {
        class LoggedInEmail(override val lambda: GherkinLambda1) : Home<CucumberDefinition.Step.GivenSingle, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.GivenSingle = CucumberDefinition.Step.GivenSingle("Email is $EXPECT_VALUE_STRING", lambda)
        }
        class PressLogoutButton(override val lambda: GherkinLambda0) : Login<CucumberDefinition.Step.Then, GherkinLambda0>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I press the logout button", lambda)
        }
    }
}