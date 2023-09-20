package com.corrado4eyes.pistakio.mocks

import com.corrado4eyes.pistakio.AssertionResult
import com.corrado4eyes.pistakio.Node
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
}