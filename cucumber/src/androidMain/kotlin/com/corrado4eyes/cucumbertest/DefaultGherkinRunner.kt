package com.corrado4eyes.cucumbertest

actual class GherkinLambda (private val lambda: () -> Unit) : () -> Unit {
    actual constructor() : this({ throw IllegalArgumentException("Should call the primary constructor") })
    override fun invoke() {
        lambda()
    }
}

actual class DefaultGherkinRunner actual constructor(
    private val lambdaMaps: TestCases
): GherkinRunner {
    override fun buildFeature() {
        lambdaMaps.forEach {
            it.toGherkin()
        }
    }
}

actual fun given(regex: String, lambda: GherkinLambda) {
    TODO("Not implemented yet")
}

actual fun `when`(regex: String, lambda: GherkinLambda) {
    TODO("Not implemented yet")
}

actual fun then(regex: String, lambda: GherkinLambda) {
    TODO("Not implemented yet")
}
