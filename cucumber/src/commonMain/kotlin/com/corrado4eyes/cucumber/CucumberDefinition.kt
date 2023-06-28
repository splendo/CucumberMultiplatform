package com.corrado4eyes.cucumber

import com.corrado4eyes.cucumber.tests.TestCase


typealias Scenario = CucumberDefinition.Descriptive.Example

sealed class CucumberDefinition(val regex: String) {

    sealed class Descriptive(regex: String) : CucumberDefinition(regex) {

        /**
         * Represents a whole feature to be described. It groups the other descriptive keywords.
         */
        class Feature(message: String) : Descriptive(message)

        /**
         * Groups more scenerio that tests a similar flow with a certain rule
         */
        class Rule(regex: String) : Descriptive(regex)

        /**
         * Represents a Scenario, hence a group of steps.
         */
        class Example(message: String) : Descriptive(message)
    }

    sealed class Step(regex: String) : CucumberDefinition(regex) {
        class Given(regex: String) : Step(regex)
        class When(regex: String) : Step(regex)
        class Then(regex: String) : Step(regex)
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

typealias TestCases = List<TestCase>

interface GherkinRunner {
    fun buildFeature()
}

expect val EXPECT_VALUE_STRING: String

expect class GherkinLambda constructor()
expect class DefaultGherkinRunner(lambdaMaps: TestCases) : GherkinRunner

expect fun given(regex: String, lambda: GherkinLambda)
infix fun GherkinLambda.Given(regex: String) {
    given(regex, this)
}

expect fun then(regex: String, lambda: GherkinLambda)

infix fun String.Then(lambda: GherkinLambda) {
    then(this, lambda)
}

expect fun `when`(regex: String, lambda: GherkinLambda)

infix fun GherkinLambda.When(regex: String) {
    `when`(regex, this)
}
