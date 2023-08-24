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
        val lambda: GherkinLambda

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

/**
 * Base class that takes care of taking a method to run on the initialization. The method is passed as parameter
 * instead of having it as an open method to be overriden to avoid calling a method in the base class init block while the
 * class that is extending it is probably partially initialized.
 */
abstract class BaseCucumberDefinition(execute: () -> Unit) {
    init {
        execute()
    }
}

sealed class CucumberDefinition(val regex: String, execute: () -> Unit = {}): BaseCucumberDefinition(execute) {

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

    sealed class Step(regex: String, execute: () -> Unit) : Definition.Step, CucumberDefinition(regex, execute) {
        class Given(
            regex: String,
            override val lambda: GherkinLambda0
        ) : Definition.Step.Given,
            Step(
                regex,
                { given(regex, lambda) }
            )
        class GivenSingle(
            regex: String,
            override val lambda: GherkinLambda1
        ) : Definition.Step.GivenSingle,
            Step(
                regex,
                { given(regex, lambda) }
            )
        class GivenMultiple(
            regex: String,
            override val lambda: GherkinLambda2
        ) : Definition.Step.GivenMultiple,
            Step(
                regex,
                { given(regex, lambda) }
            )
        class When(
            regex: String,
            override val lambda: GherkinLambda0
        ) : Definition.Step.When,
            Step(
                regex,
                { `when`(regex, lambda) }
            )
        class WhenSingle(
            regex: String,
            override val lambda: GherkinLambda1
        ) :  Definition.Step.WhenSingle,
             Step(
                 regex,
                 { `when`(regex, lambda) }
             )
        class WhenMultiple(
            regex: String,
            override val lambda: GherkinLambda2
        ) :  Definition.Step.WhenMultiple,
             Step(
                 regex,
                 { `when`(regex, lambda) }
             )
        class Then(
            regex: String,
            override val lambda: GherkinLambda0
        ) : Definition.Step.Then,
            Step(
                regex,
                { then(regex, lambda) }
            )
        class ThenSingle(
            regex: String,
            override val lambda: GherkinLambda1
        ) : Definition.Step.ThenSingle,
            Step(
                regex,
                { then(regex, lambda) }
            )
        class ThenMultiple(
            regex: String,
            override val lambda: GherkinLambda2
        ) : Definition.Step.ThenMultiple,
            Step(
                regex,
                { then(regex, lambda) }
            )
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

interface GherkinTestCase<L: GherkinLambda> {
    val step: CucumberDefinition
    val lambda: L
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
