package com.corrado4eyes.cucumber

typealias Scenario = CucumberDefinition.Descriptive.Example

sealed interface Definition {
    val regex: String
    sealed interface Descriptive : Definition {

        /**
         * Represents a whole feature to be described. It groups the other descriptive keywords.
         */
        interface Feature : Descriptive {
            val message: String
        }

        /**
         * Groups more scenerio that tests a similar flow with a certain rule
         */
        interface Rule : Descriptive {
            override val regex: String
        }

        /**
         * Represents a Scenario, hence a group of steps.
         */
        interface Example : Descriptive {
            val message: String
        }
    }

    sealed interface Step : Definition {
        override val regex: String

        interface Given : Step
        interface GivenSingle : Step
        interface GivenMultiple : Step

        interface When : Step
        interface WhenSingle : Step
        interface WhenMultiple : Step

        interface Then : Step
        interface ThenSingle : Step
        interface ThenMultiple : Step
    }
}

sealed class CucumberDefinition(val regex: String, execute: () -> Unit = {}) {

    sealed class Descriptive(regex: String) : Definition.Descriptive, CucumberDefinition(regex) {

        /**
         * Represents a whole feature to be described. It groups the other descriptive keywords.
         */
        class Feature(override val message: String) : Definition.Descriptive.Feature, Descriptive(message)

        /**
         * Groups more scenerio that tests a similar flow with a certain rule
         */
        class Rule(regex: String) : Definition.Descriptive.Rule, Descriptive(regex)

        /**
         * Represents a Scenario, hence a group of steps.
         */
        class Example(override val message: String) : Definition.Descriptive.Example,  Descriptive(message)
    }

    sealed class Step(regex: String) : Definition.Step, CucumberDefinition(regex) {
        class Given(regex: String) : Definition.Step.Given, Step(regex)
//        class GivenSingle(regex: String) : Definition.Step.GivenSingle, Step(regex)
//        class GivenMultiple(regex: String) : Definition.Step.GivenMultiple, Step(regex)
        class When(regex: String) : Definition.Step.When, Step(regex)
//        class WhenSingle(regex: String) :  Definition.Step.WhenSingle, Step(regex)
//        class WhenMultiple(regex: String) :  Definition.Step.WhenMultiple, Step(regex)
        class Then(regex: String) : Definition.Step.Then, Step(regex)
//        class ThenSingle(regex: String) : Definition.Step.ThenSingle, Step(regex)
//        class ThenMultiple(regex: String) : Definition.Step.ThenMultiple, Step(regex)
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

interface GherkinTestCase<D: Definition> {
    val step: D
}

expect val EXPECT_VALUE_STRING: String

interface GherkinLambda

expect class GherkinLambda0 : GherkinLambda
expect class GherkinLambda1 : GherkinLambda
expect class GherkinLambda2 : GherkinLambda
//expect class GherkinLambda3 : GherkinLambda
//expect class GherkinLambda4 : GherkinLambda
//expect class GherkinLambda5 : GherkinLambda
//expect class GherkinLambda6 : GherkinLambda
//expect class GherkinLambda7 : GherkinLambda
//expect class GherkinLambda8 : GherkinLambda
//expect class GherkinLambda9 : GherkinLambda


expect fun given(regex: String, lambda: GherkinLambda0)
expect fun given(regex: String, lambda: GherkinLambda1)
expect fun given(regex: String, lambda: GherkinLambda2)

expect fun then(regex: String, lambda: GherkinLambda0)
expect fun then(regex: String, lambda: GherkinLambda1)
expect fun then(regex: String, lambda: GherkinLambda2)

expect fun `when`(regex: String, lambda: GherkinLambda0)
expect fun `when`(regex: String, lambda: GherkinLambda1)
expect fun `when`(regex: String, lambda: GherkinLambda2)
