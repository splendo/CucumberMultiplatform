package com.corrado4eyes.cucumberplayground.cucumber

import com.corrado4eyes.cucumberplayground.cucumber.tests.GherkinTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

abstract class StubBaseTestCase : GherkinTestCase {
    var exampleCalledTimes = 0
    var exampleCalledWithMessage: String? = null

    var featureCalledTimes = 0
    var featureCalledWithMessage: String? = null

    var ruleCalledTimes = 0
    var ruleCalledWithMessage: String? = null

    var givenCalledTimes = 0
    var givenCalledWithRegex: String? = null

    var thenCalledTimes = 0
    var thenCalledWithRegex: String? = null

    var whenCalledTimes = 0
    var whenCalledWithRegex: String? = null

    var andCalledTimes = 0
    var andCalledWithRegex: String? = null

    override fun toGherkin() {
        when (step) {
            is CucumberDefinition.Descriptive.Example -> {
                exampleCalledTimes++
                exampleCalledWithMessage = step.regex
            }
            is CucumberDefinition.Descriptive.Feature -> {
                featureCalledTimes++
                featureCalledWithMessage = step.regex
            }
            is CucumberDefinition.Descriptive.Rule -> {
                ruleCalledTimes++
                ruleCalledWithMessage = step.regex
            }
            is CucumberDefinition.Step.Given -> {
                givenCalledTimes++
                givenCalledWithRegex = step.regex
            }
            is CucumberDefinition.Step.Then -> {
                thenCalledTimes++
                thenCalledWithRegex = step.regex
            }
            is CucumberDefinition.Step.When -> {
                whenCalledTimes++
                whenCalledWithRegex = step.regex
            }
            is CucumberDefinition.SubStep.And -> {
                andCalledTimes++
                andCalledWithRegex = step.regex
            }
        }
    }
}

sealed class StubTestCase : StubBaseTestCase() {
    class TestGivenCase(override val lambda: GherkinLambda) : StubTestCase() {
        override val step: CucumberDefinition = CucumberDefinition.Step.Given("It is \\\"(.*)\\\"")
    }
}

class CucumberRunnerTest {
    @Test
    fun test_given_case() {
        val testCase = StubTestCase.TestGivenCase(GherkinLambda())
        assertEquals(0, testCase.givenCalledTimes)
        assertNull(testCase.givenCalledWithRegex)

        testCase.toGherkin()
        assertEquals(1, testCase.givenCalledTimes)
        assertEquals("It is \\\"(.*)\\\"", testCase.givenCalledWithRegex)
    }
}