package com.corrado4eyes.cucumber


typealias Scenario = CucumberDefinition.Descriptive.Example

sealed interface CucumberDefinitionI {
    val regex: String
    sealed interface Descriptive : CucumberDefinitionI {

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

    sealed interface Step : CucumberDefinitionI {
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

abstract class BaseCucumberDefinition(execute: () -> Unit) {
    init {
        execute()
    }
}

sealed class CucumberDefinition(val regex: String, execute: () -> Unit = {}): BaseCucumberDefinition(execute) {

    sealed class Descriptive(regex: String) : CucumberDefinitionI.Descriptive, CucumberDefinition(regex) {

        /**
         * Represents a whole feature to be described. It groups the other descriptive keywords.
         */
        class Feature(override val message: String) : CucumberDefinitionI.Descriptive.Feature, Descriptive(message)

        /**
         * Groups more scenerio that tests a similar flow with a certain rule
         */
        class Rule(regex: String) : CucumberDefinitionI.Descriptive.Rule, Descriptive(regex)

        /**
         * Represents a Scenario, hence a group of steps.
         */
        class Example(override val message: String) : CucumberDefinitionI.Descriptive.Example,  Descriptive(message)
    }

    sealed class Step(regex: String, execute: () -> Unit) : CucumberDefinitionI.Step, CucumberDefinition(regex, execute) {
        class Given(
            regex: String,
            override val lambda: GherkinLambda0
        ) : CucumberDefinitionI.Step.Given,
            Step(
                regex,
                { given(regex, lambda) }
            )
        class GivenSingle(
            regex: String,
            override val lambda: GherkinLambda1
        ) : CucumberDefinitionI.Step.GivenSingle,
            Step(
                regex,
                { given(regex, lambda) }
            )
        class GivenMultiple(
            regex: String,
            override val lambda: GherkinLambda2
        ) : CucumberDefinitionI.Step.GivenMultiple,
            Step(
                regex,
                { given(regex, lambda) }
            )
        class When(
            regex: String,
            override val lambda: GherkinLambda0
        ) : CucumberDefinitionI.Step.When,
            Step(
                regex,
                { `when`(regex, lambda) }
            )
        class WhenSingle(
            regex: String,
            override val lambda: GherkinLambda1
        ) :  CucumberDefinitionI.Step.WhenSingle,
             Step(
                 regex,
                 { `when`(regex, lambda) }
             )
        class WhenMultiple(
            regex: String,
            override val lambda: GherkinLambda2
        ) :  CucumberDefinitionI.Step.WhenMultiple,
             Step(
                 regex,
                 { `when`(regex, lambda) }
             )
        class Then(
            regex: String,
            override val lambda: GherkinLambda0
        ) : CucumberDefinitionI.Step.Then,
            Step(
                regex,
                { then(regex, lambda) }
            )
        class ThenSingle(
            regex: String,
            override val lambda: GherkinLambda1
        ) : CucumberDefinitionI.Step.ThenSingle,
            Step(
                regex,
                { then(regex, lambda) }
            )
        class ThenMultiple(
            regex: String,
            override val lambda: GherkinLambda2
        ) : CucumberDefinitionI.Step.ThenMultiple,
            Step(
                regex,
                { then(regex, lambda) }
            )
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

interface GherkinTestCase<CD: CucumberDefinitionI, T: GherkinLambda> {
    val step: CD
    val lambda: T
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
