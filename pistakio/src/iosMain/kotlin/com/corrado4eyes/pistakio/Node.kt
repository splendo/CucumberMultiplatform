package com.corrado4eyes.pistakio

import kotlin.time.DurationUnit
import platform.Foundation.NSPredicate
import platform.XCTest.XCTWaiter
import platform.XCTest.XCTWaiterResultCompleted
import platform.XCTest.XCTestCase
import platform.XCTest.XCUIElement
import platform.XCTest.expectationForPredicate
import platform.XCTest.tap
import platform.XCTest.typeText

actual class DefaultNode(private val element: XCUIElement) : Node {

    override fun exists(): AssertionResult {
        val predicate = NSPredicate.predicateWithFormat("exists == true")
        val expectation = XCTestCase().expectationForPredicate(
            predicate = predicate,
            evaluatedWithObject = element,
            handler = null
        )

        val result = XCTWaiter().waitForExpectations(
            expectations = listOf(expectation),
            timeout = TimeoutDuration.SHORT.duration.toDouble(DurationUnit.SECONDS)
        )

        return assertionResultFor { result == XCTWaiterResultCompleted }
    }

    override fun waitExists(timeout: TimeoutDuration): AssertionResult = assertionResultFor {
        element.waitForExistenceWithTimeout(
            timeout.duration.toDouble(DurationUnit.SECONDS)
        )
    }

    override fun isVisible(): AssertionResult = waitExists(TimeoutDuration.SHORT)

    override fun isButton(): AssertionResult = assertionResultFor(element::isHittable)

    override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { isTextEqualOrContains(value, contains) }

    override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { isTextEqualOrContains(value, contains) }

    override fun typeText(text: String) {
        element.tap()
        element.typeText(text)
    }

    override fun tap() {
        element.tap()
    }

    private fun isTextEqualOrContains(value: String, contains: Boolean): Boolean = if (contains) {
        (element.value as? String)?.contains(value) == true
    } else {
        element.value == value
    }
}

actual typealias AssertionBlockReturnType = Boolean
actual fun  DefaultNode.assertionResultFor(
    assertionBlock: () -> AssertionBlockReturnType
): AssertionResult = if (assertionBlock()) {
    AssertionResult.Success()
} else {
    AssertionResult.Failure(AssertionError())
}
