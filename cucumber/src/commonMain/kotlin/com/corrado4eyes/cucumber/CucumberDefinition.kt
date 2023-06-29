package com.corrado4eyes.cucumber


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
        class Given(regex: String, lambda: GherkinLambda0) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class GivenSingle(regex: String, lambda: GherkinLambda1) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class GivenMultiple(regex: String, lambda: GherkinLambda2) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class When(regex: String, lambda: GherkinLambda0) : Step(regex) {
            init {
                `when`(regex, lambda)
            }
        }
        class WhenSingle(regex: String, lambda: GherkinLambda1) : Step(regex) {
            init {
                `when`(regex, lambda)
            }
        }
        class WhenMultiple(regex: String, lambda: GherkinLambda2) : Step(regex) {
            init {
                `when`(regex, lambda)
            }
        }
        class Then(regex: String, lambda: GherkinLambda0) : Step(regex) {
            init {
                then(regex, lambda)
            }
        }
        class ThenSingle(regex: String, lambda: GherkinLambda1) : Step(regex) {
            init {
                then(regex, lambda)
            }
        }
        class ThenMultiple(regex: String, lambda: GherkinLambda2) : Step(regex) {
            init {
                then(regex, lambda)
            }
        }
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

/**
 * Each TestCase will be composed by a step and an action.
 */
interface GherkinTestCase<T: GherkinLambda> {
    val step: CucumberDefinition
    val lambda: T
}

/**
 * How each platform represent an expected parameter in a regex.
 */
expect val EXPECT_VALUE_STRING: String

/**
 * An interface that marks a class as a GherkingLambda, hence a lambda defined on each module that takes specific arguments, or a specific amount
 * of arguments and does some assertion on the views.
 *
 * On Android a step like Given or When will take a regex and an amount of arguments, while on iOS each step definition will always take a regex and a CCIStepBody.
 * Therefore there will be multiple GherkinLambda to reflect how Android works.
 */
interface GherkinLambda

/**
 * Lambda that takes 0 parameters
 */
expect class GherkinLambda0 : GherkinLambda

/**
 * Lambda that takes 1 parameters
 */
expect class GherkinLambda1 : GherkinLambda

/**
 * Lambda that takes 2 parameters
 */
expect class GherkinLambda2 : GherkinLambda
//expect class GherkinLambda3 : GherkinLambda
//expect class GherkinLambda4 : GherkinLambda
//expect class GherkinLambda5 : GherkinLambda
//expect class GherkinLambda6 : GherkinLambda
//expect class GherkinLambda7 : GherkinLambda
//expect class GherkinLambda8 : GherkinLambda
//expect class GherkinLambda9 : GherkinLambda


/**
 * Method that wraps the given method that takes 0 parameters
 */
expect fun given(regex: String, lambda: GherkinLambda0)

/**
 * Method that wraps the given method that takes 1 parameter
 */
expect fun given(regex: String, lambda: GherkinLambda1)

/**
 * Method that wraps the Given method that takes 2 parameters
 */
expect fun given(regex: String, lambda: GherkinLambda2)

/**
 * Method that wraps the Then method that takes 0 parameters
 */
expect fun then(regex: String, lambda: GherkinLambda0)

/**
 * Method that wraps the Then method that takes 1 parameter
 */
expect fun then(regex: String, lambda: GherkinLambda1)

/**
 * Method that wraps the Then method that takes 2 parameters
 */
expect fun then(regex: String, lambda: GherkinLambda2)

/**
 * Method that wraps the When method that takes 0 parameters
 */
expect fun `when`(regex: String, lambda: GherkinLambda0)

/**
 * Method that wraps the Then method that takes 1 parameter
 */
expect fun `when`(regex: String, lambda: GherkinLambda1)

/**
 * Method that wraps the Then method that takes 2 parameters
 */
expect fun `when`(regex: String, lambda: GherkinLambda2)
