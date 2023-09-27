package com.corrado4eyes.pistakio

/**
 * To be used on the shared module to represent test cases.
 * [TestCase.CrossPlatform] represents tests that can be done completely on the shared module, except for the assertion.
 * [TestCase.Platform] instead represents tests that should be implemented completely on the platform.
 */
interface TestCase {
    interface CrossPlatform : TestCase {
        fun runAndGetAssertions(): List<AssertionResult>
    }
    interface Platform : TestCase
}