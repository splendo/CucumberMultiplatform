package com.corrado4eyes.cucumber.tests

import com.corrado4eyes.cucumberplayground.cucumber.CucumberDefinition
import com.corrado4eyes.cucumberplayground.cucumber.GherkinLambda
import com.corrado4eyes.cucumber.cucumber.Given
import com.corrado4eyes.cucumber.cucumber.Then
import com.corrado4eyes.cucumber.cucumber.When

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
    sealed class Common : TestCase() {
        class ScreenIsVisible(override val lambda: GherkinLambda) : Common() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Given("I am in the \\\"(.*)\\\" screen")
        }

        class TitleIsVisible(override val lambda: GherkinLambda) : Common() {
            override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see \\\"(.*)\\\" text")
        }
    }
}