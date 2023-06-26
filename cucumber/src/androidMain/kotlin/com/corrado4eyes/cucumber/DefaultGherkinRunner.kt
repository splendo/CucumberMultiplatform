package com.corrado4eyes.cucumber

import io.cucumber.java8.En

actual class GherkinLambda (private val lambda: (List<String>) -> Unit) : (List<String>) -> Unit {
    actual constructor() : this({ throw IllegalArgumentException("Should call the primary constructor") })
    override fun invoke(p0: List<String>) {
        lambda(p0)
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
    object : En {
        init {
            Given(regex, lambda)
        }
    }
}

actual fun `when`(regex: String, lambda: GherkinLambda) {
    object : En {
        init {
            When(regex, lambda)
        }
    }
}

actual fun then(regex: String, lambda: GherkinLambda) {
    object : En {
        init {
            Then(regex, lambda)
        }
    }
}
