package com.corrado4eyes.pistakio.mocks

import com.corrado4eyes.pistakio.AssertionResult
import com.corrado4eyes.pistakio.Node
import com.corrado4eyes.pistakio.SwipeDuration
import com.corrado4eyes.pistakio.TimeoutDuration

class StubNode : Node {
    override fun typeText(text: String) {
        TODO("Not yet implemented")
    }

    override fun tap() {
        TODO("Not yet implemented")
    }

    override fun exists(): AssertionResult {
        TODO("Not yet implemented")
    }

    override fun waitExists(timeout: TimeoutDuration): AssertionResult {
        TODO("Not yet implemented")
    }

    override fun isVisible(): AssertionResult {
        TODO("Not yet implemented")
    }

    override fun isButton(): AssertionResult {
        TODO("Not yet implemented")
    }

    override fun isTextEqualTo(value: String, contains: Boolean): AssertionResult {
        TODO("Not yet implemented")
    }

    override fun isHintEqualTo(value: String, contains: Boolean): AssertionResult {
        TODO("Not yet implemented")
    }

    override fun swipeUp() {
        TODO("Not yet implemented")
    }

    override fun swipeUp(swipeDuration: SwipeDuration) {
        TODO("Not yet implemented")
    }

    override fun swipeUp(startY: Float, endY: Float) {
        TODO("Not yet implemented")
    }

    override fun swipeDown() {
        TODO("Not yet implemented")
    }

    override fun swipeDown(swipeDuration: SwipeDuration) {
        TODO("Not yet implemented")
    }

    override fun swipeDown(startY: Float, endY: Float) {
        TODO("Not yet implemented")
    }

    override fun swipeUntilIndex(index: Int, velocity: Float?) {
        TODO("Not yet implemented")
    }

    override fun swipeUntilKey(key: Any, velocity: Float?) {
        TODO("Not yet implemented")
    }
}