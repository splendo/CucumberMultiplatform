package com.corrado4eyes.cucumber

import cocoapods.Cucumberish.CCIStepBody
import cocoapods.Cucumberish.Given
import cocoapods.Cucumberish.Then
import cocoapods.Cucumberish.When

actual val EXPECT_VALUE_STRING = "\\\"(.*)\\\""

actual class GherkinLambda0(val lambda: CCIStepBody) : GherkinLambda

actual class GherkinLambda1(val lambda: CCIStepBody) : GherkinLambda

actual class GherkinLambda2(val lambda: CCIStepBody) : GherkinLambda

actual fun given(definitionString: String, lambda: GherkinLambda0) {
    Given(definitionString, lambda.lambda)
}

actual fun given(definitionString: String, lambda: GherkinLambda1) {
    Given(definitionString, lambda.lambda)
}

actual fun given(definitionString: String, lambda: GherkinLambda2) {
    Given(definitionString, lambda.lambda)
}

actual fun then(definitionString: String, lambda: GherkinLambda0) {
    Then(definitionString, lambda.lambda)
}

actual fun then(definitionString: String, lambda: GherkinLambda1) {
    Then(definitionString, lambda.lambda)
}

actual fun then(definitionString: String, lambda: GherkinLambda2) {
    Then(definitionString, lambda.lambda)
}

actual fun `when`(definitionString: String, lambda: GherkinLambda0) {
    When(definitionString, lambda.lambda)
}

actual fun `when`(definitionString: String, lambda: GherkinLambda1) {
    When(definitionString, lambda.lambda)
}

actual fun `when`(definitionString: String, lambda: GherkinLambda2) {
    When(definitionString, lambda.lambda)
}
