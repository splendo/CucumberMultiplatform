package com.corrado4eyes.cucumberplayground.cucumber.tests

import com.corrado4eyes.cucumberplayground.cucumber.CucumberDefinition
import com.corrado4eyes.cucumberplayground.cucumber.GherkinLambda
import com.corrado4eyes.cucumberplayground.cucumber.Given
import com.corrado4eyes.cucumberplayground.cucumber.Then
import com.corrado4eyes.cucumberplayground.cucumber.When

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
    sealed class Home : TestCase() {
        class HomeScreenIsVisible(override val lambda: GherkinLambda) : Home() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("I am in the \\\"(.*)\\\" screen")
        }
        class HomeTitleIsVisible(override val lambda: GherkinLambda) : Home() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("I see \\\"(.*)\\\" text")
        }
    }
}