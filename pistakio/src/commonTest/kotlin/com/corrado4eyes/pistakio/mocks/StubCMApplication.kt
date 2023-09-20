package com.corrado4eyes.pistakio.mocks

import com.corrado4eyes.pistakio.BaseApplicationAdapter

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

    var tearDown = 0
    override fun tearDown() {
        super.tearDown()
        tearDown++
    }
}
