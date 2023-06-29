package com.corrado4eyes.cucumber

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class BaseMockGherkinLambda : () -> Unit, GherkinLambda {
    var wasCalled: Boolean = false
}

class MockGherkinLambda(private val mockLambda: () -> Unit) : BaseMockGherkinLambda() {
    override fun invoke() {
        mockLambda()
        wasCalled = true
    }
}

sealed class CucumberDefinitionMock(execute: () -> Unit) : BaseCucumberDefinition(execute) {
    class GivenMock(
        override val regex: String,
        override val lambda: MockGherkinLambda
    ) : CucumberDefinitionI.Step.Given, CucumberDefinitionMock({ lambda() })
}

sealed class StubTestCase<CD: CucumberDefinitionI, T: GherkinLambda> : GherkinTestCase<CD, T>  {
    class TestGivenCase(override val lambda: MockGherkinLambda) : StubTestCase<CucumberDefinitionMock.GivenMock, MockGherkinLambda>() {
        override val step: CucumberDefinitionMock.GivenMock = CucumberDefinitionMock.GivenMock("Test Given with GherkinLambda0", lambda)
    }
}

class CucumberRunnerTest {
    @Test
    fun test_given_case() {
        val mockLambda = MockGherkinLambda {}

        assertFalse(mockLambda.wasCalled)
        StubTestCase.TestGivenCase(
            mockLambda
        )
        assertTrue(mockLambda.wasCalled)
    }
}