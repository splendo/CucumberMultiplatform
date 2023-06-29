package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinTestCase

sealed class TestCase<T: GherkinLambda> : GherkinTestCase<T> {
    sealed class Common<T: GherkinLambda> : TestCase<T>() {
        class ScreenIsVisible(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.GivenSingle("I am in the $EXPECT_VALUE_STRING screen", lambda)
        }

        class TitleIsVisible(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I see $EXPECT_VALUE_STRING text", lambda)
        }

        class ButtonIsVisible(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING button", lambda)
        }

        class NavigateToScreen(override val lambda: GherkinLambda1) : Common<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I see the $EXPECT_VALUE_STRING screen", lambda)
        }
    }

    sealed class Login <T: GherkinLambda> : TestCase<T>() {
        sealed class Common <T: GherkinLambda> : Login<T>() {
            class TextFieldIsVisible(override val lambda: GherkinLambda2) : Common<GherkinLambda2>() {
                override val step: CucumberDefinition = CucumberDefinition.Step.ThenMultiple("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING", lambda)
            }
        }
        class FillEmailTextField(override val lambda: GherkinLambda1) : Login<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I type $EXPECT_VALUE_STRING in the email field", lambda)
        }
        class FillPasswordTextField(override val lambda: GherkinLambda1) : Login<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.ThenSingle("I type $EXPECT_VALUE_STRING in the password field", lambda)
        }

        class PressLoginButton(override val lambda: GherkinLambda0) : Login<GherkinLambda0>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I press the login button", lambda)
        }
    }

    sealed class Home <T: GherkinLambda> : TestCase<T>() {
        class LoggedInEmail(override val lambda: GherkinLambda1) : Home<GherkinLambda1>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.GivenSingle("Email is $EXPECT_VALUE_STRING", lambda)
        }
        class PressLogoutButton(override val lambda: GherkinLambda0) : Login<GherkinLambda0>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I press the logout button", lambda)
        }
    }
}