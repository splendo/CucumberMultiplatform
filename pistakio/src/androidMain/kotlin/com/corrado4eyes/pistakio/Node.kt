package com.corrado4eyes.pistakio

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToKey
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import kotlin.time.DurationUnit

actual class DefaultNode(internal val node: SemanticsNodeInteraction) : Node {
    override fun exists(): AssertionResult = assertionResultFor(node::assertExists)

    override fun waitExists(timeout: TimeoutDuration): AssertionResult =
        assertionResultFor(node:: assertExists)

    override fun isVisible(): AssertionResult = assertionResultFor(node::assertIsDisplayed)

    override fun isButton(): AssertionResult = assertionResultFor(node::assertHasClickAction)

    override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { node.assertTextEquals(value) }

    override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult =
        assertionResultFor { node.assertTextEquals(value, includeEditableText = false) }

    override fun typeText(text: String) {
        node.performTextInput(text)
    }

    override fun tap() {
        node.performClick()
    }

    override fun swipeUntilIndex(index: Int, velocity: Float?) {
        try {
            node.performScrollToIndex(index)
        } catch (e: IllegalArgumentException) {
            // catch error thrown if the index is out of bounds.
            // test should fail on the visibility assertion
        }
    }

    override fun swipeUntilKey(key: Any, velocity: Float?) {
        try {
            node.performScrollToKey(key)
        } catch (e: IllegalArgumentException) {
            // catch error thrown if the index is out of bounds.
            // test should fail on the visibility assertion
        }
    }

    override fun swipeUp() {
        node.performTouchInput {
            swipeUp()
        }
    }

    override fun swipeUp(swipeDuration: SwipeDuration) {
        node.performTouchInput {
            swipeUp(durationMillis = swipeDuration.duration.toLong(DurationUnit.MILLISECONDS))
        }
    }

    override fun swipeUp(startY: Float, endY: Float) {
        node.performTouchInput {
            swipeUp(startY = startY, endY = endY)
        }
    }

    override fun swipeDown() {
        node.performTouchInput {
            swipeDown()
        }
    }

    override fun swipeDown(swipeDuration: SwipeDuration) {
        node.performTouchInput {
            swipeDown(durationMillis = swipeDuration.duration.toLong(DurationUnit.MILLISECONDS))
        }
    }

    override fun swipeDown(startY: Float, endY: Float) {
        node.performTouchInput {
            swipeDown(startY = startY, endY = endY)
        }
    }
}

actual typealias AssertionBlockReturnType = SemanticsNodeInteraction
actual fun DefaultNode.assertionResultFor(assertionBlock: () -> AssertionBlockReturnType): AssertionResult = try {
    assertionBlock()
    AssertionResult.Success()
} catch (e: AssertionError) {
//     Could take screeshot with node.captureToImage()
    AssertionResult.Failure(e)
}
