package com.corrado4eyes.cucumber

typealias Scenario = CucumberDefinition.Descriptive.Example

sealed interface Definition {
    val definitionString: String
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
            override val definitionString: String
        }

        /**
         * Represents a Scenario, hence a group of steps.
         */
        interface Example : Descriptive {
            val message: String
        }
    }

    sealed interface Step : Definition {
        override val definitionString: String

        interface Given : Step
        interface When : Step
        interface Then : Step
    }
}

sealed class CucumberDefinition(val definitionString: String) {

    sealed class Descriptive(definitionString: String) : Definition.Descriptive, CucumberDefinition(definitionString) {

        /**
         * Represents a whole feature to be described. It groups the other descriptive keywords.
         */
        class Feature(override val message: String) : Definition.Descriptive.Feature, Descriptive(message)

        /**
         * Groups more scenerio that tests a similar flow with a certain rule
         */
        class Rule(definitionString: String) : Definition.Descriptive.Rule, Descriptive(definitionString)

        /**
         * Represents a Scenario, hence a group of steps.
         */
        class Example(override val message: String) : Definition.Descriptive.Example,  Descriptive(message)
    }

    sealed class Step(definitionString: String) : Definition.Step, CucumberDefinition(definitionString) {
        class Given(definitionString: String) : Definition.Step.Given, Step(definitionString)
        class When(definitionString: String) : Definition.Step.When, Step(definitionString)
        class Then(definitionString: String) : Definition.Step.Then, Step(definitionString)
    }

    sealed class SubStep(definitionString: String) : CucumberDefinition(definitionString) {
        class And(definitionString: String) : SubStep(definitionString)
    }
}

interface GherkinTestCase<D: Definition> {
    val definition: D
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


expect fun given(definitionString: String, lambda: GherkinLambda0)
expect fun given(definitionString: String, lambda: GherkinLambda1)
expect fun given(definitionString: String, lambda: GherkinLambda2)

expect fun then(definitionString: String, lambda: GherkinLambda0)
expect fun then(definitionString: String, lambda: GherkinLambda1)
expect fun then(definitionString: String, lambda: GherkinLambda2)

expect fun `when`(definitionString: String, lambda: GherkinLambda0)
expect fun `when`(definitionString: String, lambda: GherkinLambda1)
expect fun `when`(definitionString: String, lambda: GherkinLambda2)
