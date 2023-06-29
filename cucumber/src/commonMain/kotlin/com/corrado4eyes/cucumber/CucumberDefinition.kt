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
                given(regex, lambda)
            }
        }
        class WhenSingle(regex: String, lambda: GherkinLambda1) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class WhenMultiple(regex: String, lambda: GherkinLambda2) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class Then(regex: String, lambda: GherkinLambda0) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class ThenSingle(regex: String, lambda: GherkinLambda1) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
        class ThenMultiple(regex: String, lambda: GherkinLambda2) : Step(regex) {
            init {
                given(regex, lambda)
            }
        }
    }

    sealed class SubStep(regex: String) : CucumberDefinition(regex) {
        class And(regex: String) : SubStep(regex)
    }
}

interface GherkinTestCase<T: GherkinLambda> {
    val step: CucumberDefinition
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

//infix fun GherkinLambdaMultiple.Given(regex: String) {
//    given(regex, this)
//}

expect fun then(regex: String, lambda: GherkinLambda0)
expect fun then(regex: String, lambda: GherkinLambda1)
expect fun then(regex: String, lambda: GherkinLambda2)


//infix fun String.Then(lambda: GherkinLambdaMultiple) {
//    then(this, lambda)
//}

expect fun `when`(regex: String, lambda: GherkinLambda0)
expect fun `when`(regex: String, lambda: GherkinLambda1)
expect fun `when`(regex: String, lambda: GherkinLambda2)

//infix fun GherkinLambdaMultiple.When(regex: String) {
//    `when`(regex, this)
//}
