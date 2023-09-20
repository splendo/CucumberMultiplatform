package com.corrado4eyes.pistakio

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

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
}

actual typealias AssertionBlockReturnType = SemanticsNodeInteraction
actual fun DefaultNode.assertionResultFor(assertionBlock: () -> AssertionBlockReturnType): AssertionResult = try {
    assertionBlock()
    AssertionResult.Success()
} catch (e: AssertionError) {
//     Could take screeshot with node.captureToImage()
    AssertionResult.Failure(e)
}
