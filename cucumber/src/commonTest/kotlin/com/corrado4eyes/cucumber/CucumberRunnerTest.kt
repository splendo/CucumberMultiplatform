package com.corrado4eyes.cucumber

import com.corrado4eyes.cucumber.StubTestCase.TestThenCaseWithSingleParameter.Companion.EXPECTED_VALUE
import com.splendo.kaluga.test.base.mock.call
import com.splendo.kaluga.test.base.mock.matcher.ParameterMatcher.Companion.eq
import com.splendo.kaluga.test.base.mock.parameters.mock
import com.splendo.kaluga.test.base.mock.verification.VerificationRule
import com.splendo.kaluga.test.base.mock.verify
import kotlin.test.Test

class GherkinLambdaMock0(private val lambda: () -> Unit) : () -> Unit, GherkinLambda {
    val mockedMethod = ::invoke.mock()

    override fun invoke() {
        lambda()
        mockedMethod.call()
    }
}

class GherkinLambdaMock1(private val lambda: (String) -> Unit) : (String) -> Unit, GherkinLambda {
    val mockedMethod = ::invoke.mock()

    override fun invoke(p1: String) {
        lambda(p1)
        mockedMethod.call(p1)
    }
}

sealed class CucumberDefinitionMock(execute: () -> Unit) : BaseCucumberDefinition(execute) {
    class GivenMock(
        override val regex: String,
        override val lambda: GherkinLambdaMock0
    ) : Definition.Step.Given, CucumberDefinitionMock({ lambda() })

    class ThenSingleMock(
        override val regex: String,
        override val lambda: GherkinLambdaMock1,
        mockParameter: String
    ) : Definition.Step.ThenSingle, BaseCucumberDefinition({ lambda(mockParameter) } )
}

sealed class StubTestCase<D: Definition, L: GherkinLambda> : GherkinTestCase<D, L>  {
    class TestGivenCase(override val lambda: GherkinLambdaMock0) : StubTestCase<CucumberDefinitionMock.GivenMock, GherkinLambdaMock0>() {
        override val step: CucumberDefinitionMock.GivenMock = CucumberDefinitionMock.GivenMock("Test Given with GherkinLambdaMock0", lambda)
    }
    class TestThenCaseWithSingleParameter(override val lambda: GherkinLambdaMock1) : StubTestCase<CucumberDefinitionMock.ThenSingleMock, GherkinLambdaMock1>() {
        companion object {
            const val EXPECTED_VALUE = "TEST_THEN"
        }
        override val step: CucumberDefinitionMock.ThenSingleMock = CucumberDefinitionMock.ThenSingleMock("Test Then with GherkinLambdaMock1", lambda, mockParameter = EXPECTED_VALUE)

    }
}

class CucumberRunnerTest {
    @Test
    fun test_given_case() {
        val lambda = GherkinLambdaMock0 {}

        lambda.mockedMethod.verify(rule = VerificationRule.never())
        StubTestCase.TestGivenCase(
            lambda
        )
        lambda.mockedMethod.verify()
    }

    @Test
    fun test_then_case_with_parameter() {
        val lambda = GherkinLambdaMock1 {}

        lambda.mockedMethod.verify(rule = VerificationRule.never())
        StubTestCase.TestThenCaseWithSingleParameter(
            lambda
        )
        lambda.mockedMethod.verify(value = eq(EXPECTED_VALUE))
    }
}
