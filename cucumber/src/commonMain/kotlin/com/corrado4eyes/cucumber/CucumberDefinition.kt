package com.corrado4eyes.cucumbertest

import com.corrado4eyes.cucumbertest.tests.TestCase


typealias Scenario = CucumberDefinition.Descriptive.Example

sealed class CucumberDefinition(val regex: String) {

    sealed class Descriptive(regex: String) : CucumberDefinition(regex) {

        /**
         * Represents a whole feature to be described. It groups the other descriptive keywords.
         */
        class Feature(message: String) : Descriptive(message), GherkinSyntax.Feature {
            override fun example(regex: String, lambda: Example.() -> Unit): Example = Example(regex).apply(lambda)
        }

        /**
         * Groups more scenerio that tests a similar flow with a certain rule
         */
        class Rule(regex: String) : Descriptive(regex)

        /**
         * Represents a Scenario, hence a group of steps.
         */
        class Example(message: String) : Descriptive(message), GherkinSyntax.Example {

            override fun given(regex: String, lambda: Step.Given.() -> Unit): Step.Given = Step.Given(
                regex
            ).apply(lambda)

            override fun given(regex: String): Step.Given = Step.Given(regex)

            override fun `when`(regex: String, lambda: Step.When.() -> Unit): Step.When = Step.When(
                regex
            ).apply(lambda)

            override fun `when`(regex: String): Step.When = Step.When(regex)

            override fun then(regex: String, lambda: Step.Then.() -> Unit): Step.Then = Step.Then(
                regex
            ).apply(lambda)

            override fun then(regex: String): Step.Then = Step.Then(regex)
        }
    }

    sealed class Step(regex: String) : CucumberDefinition(regex),
        GherkinSyntax.Step {
        class Given(regex: String) : Step(regex)
        class When(regex: String) : Step(regex)
        class Then(regex: String) : Step(regex)

        override fun and(regex: String): SubStep {
            TODO("Not yet implemented")
        }

        override fun or(regex: String): SubStep {
            TODO("Not yet implemented")
        }
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

interface GherkinSyntax {
    interface Feature {
        fun example(regex: String, lambda: CucumberDefinition.Descriptive.Example.() -> Unit): CucumberDefinition.Descriptive.Example
    }

    interface Example {
        fun given(regex: String, lambda: CucumberDefinition.Step.Given.() -> Unit): CucumberDefinition.Step
        fun given(regex: String): CucumberDefinition.Step.Given

        fun `when`(regex: String, lambda: CucumberDefinition.Step.When.() -> Unit): CucumberDefinition.Step
        fun `when`(regex: String): CucumberDefinition.Step.When

        fun then(regex: String, lambda: CucumberDefinition.Step.Then.() -> Unit): CucumberDefinition.Step
        fun then(regex: String): CucumberDefinition.Step.Then
    }

    interface Step {
        fun and(regex: String): CucumberDefinition.SubStep
        fun or(regex: String): CucumberDefinition.SubStep
    }
}

typealias TestCases = List<TestCase>

interface GherkinRunner {
    fun buildFeature()
}

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
