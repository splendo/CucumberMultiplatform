package com.corrado4eyes.cucumber

import cocoapods.Cucumberish.CCIStepBody
import cocoapods.Cucumberish.Given
import cocoapods.Cucumberish.Then
import cocoapods.Cucumberish.When

actual val EXPECT_VALUE_STRING = "\\\"(.*)\\\""


actual class GherkinLambda0(val lambda: CCIStepBody) : GherkinLambda

actual class GherkinLambda1(val lambda: CCIStepBody) : GherkinLambda

actual class GherkinLambda2(val lambda: CCIStepBody) : GherkinLambda

actual fun given(regex: String, lambda: GherkinLambda0) {
    Given(regex, lambda.lambda)
}

actual fun given(regex: String, lambda: GherkinLambda1) {
    Given(regex, lambda.lambda)
}

actual fun given(regex: String, lambda: GherkinLambda2) {
    Given(regex, lambda.lambda)
}

actual fun then(regex: String, lambda: GherkinLambda0) {
    Then(regex, lambda.lambda)
}

actual fun then(regex: String, lambda: GherkinLambda1) {
    Then(regex, lambda.lambda)
}

actual fun then(regex: String, lambda: GherkinLambda2) {
    Then(regex, lambda.lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda0) {
    When(regex, lambda.lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda1) {
    When(regex, lambda.lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda2) {
    When(regex, lambda.lambda)
}
