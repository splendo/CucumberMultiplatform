package com.corrado4eyes.cucumber

import com.splendo.kaluga.test.base.mock.call
import com.splendo.kaluga.test.base.mock.parameters.mock
import com.splendo.kaluga.test.base.mock.verification.VerificationRule
import com.splendo.kaluga.test.base.mock.verify
import kotlin.test.Test

class GherkinLambdaMock(private val lambda: () -> Unit) : () -> Unit, GherkinLambda {
    val mockedMethod = ::invoke.mock()

    override fun invoke() {
        lambda()
        mockedMethod.call()
    }
}

sealed class CucumberDefinitionMock(execute: () -> Unit) : BaseCucumberDefinition(execute) {
    class GivenMock(
        override val regex: String,
        override val lambda: GherkinLambdaMock
    ) : Definition.Step.Given, CucumberDefinitionMock({ lambda() })
}

sealed class StubTestCase<D: Definition, L: GherkinLambda> : GherkinTestCase<D, L>  {
    class TestGivenCase(override val lambda: GherkinLambdaMock) : StubTestCase<CucumberDefinitionMock.GivenMock, GherkinLambdaMock>() {
        override val step: CucumberDefinitionMock.GivenMock = CucumberDefinitionMock.GivenMock("Test Given with MockGherkinLambda", lambda)
    }
}

class CucumberRunnerTest {
    @Test
    fun test_given_case() {
        val lambda = GherkinLambdaMock {}

        lambda.mockedMethod.verify(rule = VerificationRule.never())
        StubTestCase.TestGivenCase(
            lambda
        )
        lambda.mockedMethod.verify()
    }
}
