package com.corrado4eyes.pistakio.mocks

import com.corrado4eyes.pistakio.ApplicationArguments
import com.corrado4eyes.pistakio.AssertionResult
import com.corrado4eyes.pistakio.BaseApplicationAdapter
import com.corrado4eyes.pistakio.Node
import com.corrado4eyes.pistakio.TimeoutDuration

class StubApplication : BaseApplicationAdapter() {
    var launchCalled = 0
    var launchArguments: ApplicationArguments = emptyMap()
    override fun launch(identifier: String?, arguments: Map<String, String>) {
        super.launch(identifier, arguments)
        launchCalled++
        launchArguments = arguments
    }

    var findViewCalled = 0
    var findViewCalledWith: String? = null
    var nodeToReturn: Node? = null
    override fun findView(tag: String): Node {
        assertAppIsRunning()
        findViewCalled++
        findViewCalledWith = tag
        return nodeToReturn ?: throw IllegalStateException(
            "You must set first the node to return"
        )
    }

    var assertCalled = 0
    var assertCalledWith: AssertionResult? = null
    override fun assert(assertionResult: AssertionResult) {
        assertCalled++
        assertCalledWith = assertionResult
    }

    var assertUntilCalled = 0
    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) {
        assertUntilCalled++
    }

    var assertAllCalled = 0
    var assertAllRunTimes = 0
    private val _assertAllResult = mutableListOf<AssertionResult>()
    val assertAllResult = _assertAllResult.toList()
    override fun assertAll(assertions: List<AssertionResult>) {
        assertAllCalled++
        assertions.forEach {
            assertAllRunTimes++
            _assertAllResult.add(it)
        }
    }

    var tearDown = 0
    override fun tearDown() {
        super.tearDown()
        tearDown++
    }
}
