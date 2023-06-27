package com.corrado4eyes.cucumber.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.Given
import com.corrado4eyes.cucumber.Then
import com.corrado4eyes.cucumber.When

interface GherkinTestCase {
    val step: CucumberDefinition
    val lambda: GherkinLambda
    fun toGherkin()
}

abstract class BaseGherkinTestCase : GherkinTestCase {
    override fun toGherkin() {
        when (step) {
            is CucumberDefinition.Step.Given -> lambda Given step.regex
            is CucumberDefinition.Step.Then -> step.regex Then lambda
            is CucumberDefinition.Step.When -> lambda When step.regex
            is CucumberDefinition.Descriptive.Example,
            is CucumberDefinition.Descriptive.Feature,
            is CucumberDefinition.Descriptive.Rule,
            is CucumberDefinition.SubStep.And -> TODO()
        }
    }
}

sealed class TestCase : BaseGherkinTestCase() {
    companion object {
        const val EXPECT_VALUE_STRING = "\\\"(.*)\\\""
    }
    sealed class Common : TestCase() {
        class ScreenIsVisible(override val lambda: GherkinLambda) : Common() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")
        }

        class TitleIsVisible(override val lambda: GherkinLambda) : Common() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see $EXPECT_VALUE_STRING text")
        }

        class IsUserAuthenticated(override val lambda: GherkinLambda) : Common() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("I am logged $EXPECT_VALUE_STRING")
        }

        class ButtonIsVisible(override val lambda: GherkinLambda) : Common() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING button")
        }
    }

    sealed class Login : TestCase() {
        sealed class Common : Login() {
            class TextFieldIsVisible(override val lambda: GherkinLambda) : Common() {
                override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")
            }
        }
        class FillEmailTextField(override val lambda: GherkinLambda) : Login() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I type an email in the email field")
        }
        class FillPasswordTextField(override val lambda: GherkinLambda) : Login() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I type a password in the password field")
        }

        class PressLoginButton(override val lambda: GherkinLambda) : Login() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I press the login button")
        }
    }
}