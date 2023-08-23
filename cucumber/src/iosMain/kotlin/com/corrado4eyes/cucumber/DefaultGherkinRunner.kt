package com.corrado4eyes.cucumber

import cocoapods.Cucumberish.CCIStepBody
import cocoapods.Cucumberish.Given
import cocoapods.Cucumberish.Then
import cocoapods.Cucumberish.When

actual val EXPECT_VALUE_STRING = "\\\"(.*)\\\""

abstract class BaseGherkinLambda(private val lambda: CCIStepBody) : CCIStepBody {
    override fun invoke(p1: List<*>?, p2: Map<Any?, *>?) {
        lambda?.let { it(p1, p2) }
    }
}

actual class GherkinLambda0(lambda: CCIStepBody) : BaseGherkinLambda(lambda), GherkinLambda

actual class GherkinLambda1(lambda: CCIStepBody) : BaseGherkinLambda(lambda), GherkinLambda

actual class GherkinLambda2(lambda: CCIStepBody) : BaseGherkinLambda(lambda), GherkinLambda

actual fun given(regex: String, lambda: GherkinLambda0) {
    Given(regex, lambda)
}

actual fun given(regex: String, lambda: GherkinLambda1) {
    Given(regex, lambda)
}

actual fun given(regex: String, lambda: GherkinLambda2) {
    Given(regex, lambda)
}

actual fun then(regex: String, lambda: GherkinLambda0) {
    Then(regex, lambda)
}

actual fun then(regex: String, lambda: GherkinLambda1) {
    Then(regex, lambda)
}

actual fun then(regex: String, lambda: GherkinLambda2) {
    Then(regex, lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda0) {
    When(regex, lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda1) {
    When(regex, lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda2) {
    When(regex, lambda)
}
