package com.corrado4eyes.cucumber

import cocoapods.Cucumberish.CCIStepBody
import cocoapods.Cucumberish.Given
import cocoapods.Cucumberish.Then
import cocoapods.Cucumberish.When

actual val EXPECT_VALUE_STRING = "\\\"(.*)\\\""

actual class GherkinLambda(private val lambda: CCIStepBody) : CCIStepBody {
    actual constructor() : this({_, _ -> throw IllegalArgumentException("Should call the primary constructor")})
    override fun invoke(p1: List<*>?, p2: Map<Any?, *>?) {
        lambda?.let { it(p1, p2) }
    }
}

actual class DefaultGherkinRunner actual constructor(
    private val lambdaMaps: TestCases
): GherkinRunner {
    override fun buildFeature() {
        lambdaMaps.forEach { it.toGherkin() }
    }
}

actual fun given(regex: String, lambda: GherkinLambda) {
    Given(regex, lambda)
}

actual fun then(regex: String, lambda: GherkinLambda) {
    Then(regex, lambda)
}

actual fun `when`(regex: String, lambda: GherkinLambda) {
    When(regex, lambda)
}