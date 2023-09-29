package com.corrado4eyes.pistakio

import kotlin.time.DurationUnit
import platform.Foundation.NSPredicate
import platform.XCTest.XCTWaiter
import platform.XCTest.XCTWaiterResultCompleted
import platform.XCTest.XCTestCase
import platform.XCTest.XCUIApplication
import platform.XCTest.XCUIElement
import platform.XCTest.XCUIElementTypeAny
import platform.XCTest.expectationForPredicate
import platform.XCTest.swipeDown
import platform.XCTest.swipeDownWithVelocity
import platform.XCTest.swipeUp
import platform.XCTest.swipeUpWithVelocity
import platform.XCTest.tap
import platform.XCTest.typeText

actual class DefaultNode(
    private val app: XCUIApplication,
    private val element: XCUIElement
) : Node {

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

    override fun swipeUntilIndex(index: Int, velocity: Float?) {
        val listElement = listItemAt(index)
        while (!listElement.isHittable()) {
            element.swipeUpWithVelocity(
                velocity = velocity?.toDouble() ?: 20.0
            )
        }
    }

    override fun swipeUntilKey(key: Any, velocity: Float?) {
        val listElement = listItemWithTag(key.toString()).element
        while (!listElement.isHittable()) {
            element.swipeUpWithVelocity(
                velocity = velocity?.toDouble() ?: 20.0
            )
        }
    }

    override fun swipeUp() {
        element.swipeUp()
    }

    override fun swipeUp(swipeDuration: SwipeDuration) {
        element.swipeUpWithVelocity(swipeDuration.duration.toDouble(DurationUnit.MILLISECONDS))
    }

    override fun swipeUp(startY: Float, endY: Float) {
        TODO("Unstable API, needs more investigation")
//        val scrollViewCoordinate = element.coordinateWithNormalizedOffset(
//            CGVectorMake(0.0, startY.toDouble())
//        )
//
//        scrollViewCoordinate.pressForDuration(
//            0.1,
//            thenDragToCoordinate = app.coordinateWithNormalizedOffset(
//                CGVectorMake(0.0, endY.toDouble().unaryMinus())
//            ),
//            withVelocity = 200.0,
//            thenHoldForDuration = 0.1
//        )
    }


    override fun swipeDown() {
        element.swipeDown()
    }

    override fun swipeDown(swipeDuration: SwipeDuration) {
        element.swipeDownWithVelocity(swipeDuration.duration.toDouble(DurationUnit.MILLISECONDS))
    }

    override fun swipeDown(startY: Float, endY: Float) {
        TODO("Unstable API, needs more investigation")
    }

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

    private fun listItemAt(index: Int) = element
            .descendantsMatchingType(XCUIElementTypeAny)
            .elementAtIndex(index.toULong())

    private fun listItemWithTag(tag: String) = element
        .descendantsMatchingType(XCUIElementTypeAny)
        .matchingIdentifier(tag)
}

actual typealias AssertionBlockReturnType = Boolean
actual fun  DefaultNode.assertionResultFor(
    assertionBlock: () -> AssertionBlockReturnType
): AssertionResult = if (assertionBlock()) {
    AssertionResult.Success()
} else {
    AssertionResult.Failure(AssertionError())
}
