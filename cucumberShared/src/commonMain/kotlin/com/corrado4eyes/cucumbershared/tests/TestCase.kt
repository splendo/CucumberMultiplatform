package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.Definition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.GherkinLambda0
import com.corrado4eyes.cucumber.GherkinLambda1
import com.corrado4eyes.cucumber.GherkinLambda2
import com.corrado4eyes.cucumber.GherkinTestCase

sealed class TestCase<D: Definition> : GherkinTestCase<D> {
    sealed class Common<D: Definition, L: GherkinLambda> : TestCase<D>() {
        class ScreenIsVisible : Common<CucumberDefinition.Step.Given, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.Given = CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")
        }

        class TitleIsVisible : Common<CucumberDefinition.Step.Then, GherkinLambda1>() {
            companion object {
                val expectedString = "I see $EXPECT_VALUE_STRING text"
            }

            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then(
                expectedString
            )
        }

        class ButtonIsVisible : Common<CucumberDefinition.Step.Then, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING button")
        }

        class NavigateToScreen : Common<CucumberDefinition.Step.Then, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING screen")
        }
    }

    sealed class Login <D: Definition, L: GherkinLambda> : TestCase<D>() {
        sealed class Common <D: Definition, L: GherkinLambda> : Login<D, L>() {
            class TextFieldIsVisible : Common<CucumberDefinition.Step.Then, GherkinLambda2>() {
                override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")
            }
        }
        class FillEmailTextField : Login<CucumberDefinition.Step.Then, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I type $EXPECT_VALUE_STRING in the email field")
        }
        class FillPasswordTextField : Login<CucumberDefinition.Step.Then, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I type $EXPECT_VALUE_STRING in the password field")
        }

        class PressLoginButton : Login<CucumberDefinition.Step.Then, GherkinLambda0>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I press the login button")
        }
    }

    sealed class Home <D: Definition, L: GherkinLambda> : TestCase<D>() {
        class LoggedInEmail : Home<CucumberDefinition.Step.Given, GherkinLambda1>() {
            override val step: CucumberDefinition.Step.Given = CucumberDefinition.Step.Given("Email is $EXPECT_VALUE_STRING")
        }
        class PressLogoutButton : Login<CucumberDefinition.Step.Then, GherkinLambda0>() {
            override val step: CucumberDefinition.Step.Then = CucumberDefinition.Step.Then("I press the logout button")
        }
    }
}