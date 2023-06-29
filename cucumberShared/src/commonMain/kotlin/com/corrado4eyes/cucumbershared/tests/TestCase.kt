package com.corrado4eyes.cucumber.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.GherkinLambdaMultiple
import com.corrado4eyes.cucumber.GherkinLambdaSingle
import com.corrado4eyes.cucumber.given
import com.corrado4eyes.cucumber.then
import com.corrado4eyes.cucumber.`when`

interface GherkinTestCase<T: GherkinLambda> {
    val step: CucumberDefinition
    val lambda: T
    fun toGherkin()
}

//abstract class BaseGherkinTestCase<T: GherkinLambda> : GherkinTestCase<T> {
//    override fun toGherkin() {
//        when (step) {
//            is CucumberDefinition.Step.Given -> given(this, lambda)
//            is CucumberDefinition.Step.Then -> then(step, lambda)
//            is CucumberDefinition.Step.When -> `when`(step, lambda)
//            is CucumberDefinition.Descriptive.Example,
//            is CucumberDefinition.Descriptive.Feature,
//            is CucumberDefinition.Descriptive.Rule,
//            is CucumberDefinition.SubStep.And -> TODO()
//        }
//    }
//}

sealed class TestCase<T: GherkinLambda> : GherkinTestCase<T> {
    override fun toGherkin() {
        when (step) {
            is CucumberDefinition.Descriptive.Example -> TODO()
            is CucumberDefinition.Descriptive.Feature -> TODO()
            is CucumberDefinition.Descriptive.Rule -> TODO()
            is CucumberDefinition.Step.Given -> given(this, lambda)
            is CucumberDefinition.Step.Then -> TODO()
            is CucumberDefinition.Step.When -> TODO()
            is CucumberDefinition.SubStep.And -> TODO()
        }
    }
    sealed class Common<T: GherkinLambda> : TestCase<T>() {
        class ScreenIsVisible(override val lambda: GherkinLambdaSingle) : Common<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")
        }

        class TitleIsVisible(override val lambda: GherkinLambdaSingle) : Common<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see $EXPECT_VALUE_STRING text")
        }

        class ButtonIsVisible(override val lambda: GherkinLambdaSingle) : Common<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING button")
        }

        class NavigateToScreen(override val lambda: GherkinLambdaSingle) : Common<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING screen")
        }
    }

    sealed class Login <T: GherkinLambda> : TestCase<T>() {
        sealed class Common <T: GherkinLambda> : Login<T>() {
            class TextFieldIsVisible(override val lambda: GherkinLambdaMultiple) : Common<GherkinLambdaMultiple>() {
                override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")
            }
        }
        class FillEmailTextField(override val lambda: GherkinLambdaSingle) : Login<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I type $EXPECT_VALUE_STRING in the email field")
        }
        class FillPasswordTextField(override val lambda: GherkinLambdaSingle) : Login<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I type $EXPECT_VALUE_STRING in the password field")
        }

        class PressLoginButton(override val lambda: GherkinLambdaSingle) : Login<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I press the login button")
        }
    }

    sealed class Home <T: GherkinLambda> : TestCase<T>() {
        class LoggedInEmail(override val lambda: GherkinLambdaSingle) : Home<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("Email is $EXPECT_VALUE_STRING")
        }
        class PressLogoutButton(override val lambda: GherkinLambdaSingle) : Login<GherkinLambdaSingle>() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I press the logout button")
        }
    }
}