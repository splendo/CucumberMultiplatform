package com.corrado4eyes.pistakio

import platform.XCTest.XCUIApplication
import platform.XCTest.XCUIElementTypeAny

actual class DefaultApplicationAdapter(app: XCUIApplication?) : BaseApplicationAdapter() {
    private val app: XCUIApplication = app ?: XCUIApplication()

    @Suppress("UNCHECKED_CAST")
    override fun launch(identifier: String?, arguments: Map<String, String>) {
        super.launch(identifier, arguments)
        app.setLaunchEnvironment((arguments as Map<Any?, *>))
        app.launch()
    }

    override fun findView(tag: String): Node {
        assertAppIsRunning()
        val element = app
            .descendantsMatchingType(XCUIElementTypeAny)
            .matchingIdentifier(tag)
            .element
        return DefaultNode(element)
    }

    /**
     * Not called from iOS
     */
    override fun assert(assertionResult: AssertionResult) {
        when (assertionResult) {
            is AssertionResult.Failure -> throw assertionResult.exception
            is AssertionResult.Success -> Unit // Do nothing to succeed
        }
    }

    /**
     * Not called from iOS
     */
    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) = assert(blockAssertionResult())

    /**
     * Not called from iOS
     */
    override fun assertAll(assertions: List<AssertionResult>) = assert(assertions.first())
}