package com.corrado4eyes.cucumber

//abstract class StubBaseTestCase<T: GherkinLambda> : GherkinTestCase<T> {
//}
//
//sealed class StubTestCase : StubBaseTestCase() {
//    class TestGivenCase<>(override val lambda: GherkinLambda) : StubTestCase() {
//        override val step: CucumberDefinition = CucumberDefinition.Step.Given("It is \\\"(.*)\\\"")
//    }
//}
//
//class CucumberRunnerTest {
//    @Test
//    fun test_given_case() {
//        val testCase = StubTestCase.TestGivenCase(GherkinLambda())
//        assertEquals(0, testCase.givenCalledTimes)
//        assertNull(testCase.givenCalledWithRegex)
//
//        testCase.toGherkin()
//        assertEquals(1, testCase.givenCalledTimes)
//        assertEquals("It is \\\"(.*)\\\"", testCase.givenCalledWithRegex)
//    }
//}