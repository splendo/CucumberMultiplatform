package com.corrado4eyes.pistakio.mocks

import com.corrado4eyes.pistakio.AssertionResult
import com.corrado4eyes.pistakio.BaseApplicationAdapter
import com.corrado4eyes.pistakio.TimeoutDuration

class StubCMApplication : BaseApplicationAdapter() {
    var launchCalled = 0
    override fun launch() {
        super.launch()
        launchCalled++
    }

    var findView = 0
    var findViewWithTag: String? = null
    override fun findView(tag: String) {
        super.findView(tag)
        findViewWithTag = tag
        findView++
    }

    override fun assert(assertionResult: AssertionResult) {
        TODO("Not yet implemented")
    }

    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) {
        TODO("Not yet implemented")
    }

    var tearDown = 0
    override fun tearDown() {
        super.tearDown()
        tearDown++
    }
}
