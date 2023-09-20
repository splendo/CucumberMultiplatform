package com.corrado4eyes.pistakio

import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.time.DurationUnit

actual class DefaultApplicationAdapter(
    testRule: ComposeContentTestRule? = null
) : BaseApplicationAdapter() {
    private val testRule = testRule ?: createComposeRule()

    override fun launch(identifier: String?, arguments: Map<String, String>) {
        super.launch(identifier, arguments)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val appPackage = instrumentation.targetContext.packageName
        val activityName = "$appPackage.MainActivity"
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setClassName(instrumentation.targetContext, activityName)

        arguments.forEach {
            intent.putExtra(it.key, it.value)
        }

        instrumentation.startActivitySync(intent)
    }

    override fun findView(tag: String): Node {
        assertAppIsRunning()
        return DefaultNode(testRule.onNodeWithTag(tag))
    }

    override fun assert(assertionResult: AssertionResult) {
        when (assertionResult) {
            is AssertionResult.Failure -> {
                throw assertionResult.exception
            }
            is AssertionResult.Success -> Unit // Do nothing to succeed
        }
    }

    override fun assertUntil(
        timeout: TimeoutDuration,
        blockAssertionResult: () -> AssertionResult
    ) {
        testRule.waitUntil(timeout.duration.toLong(DurationUnit.MILLISECONDS)) {
           blockAssertionResult() is AssertionResult.Success
        }
    }

    override fun assertAll(assertions: List<AssertionResult>) {
        assertions.forEach {
            testRule.waitUntil(TimeoutDuration.LONG.duration.toLong(DurationUnit.MILLISECONDS)) {
                it is AssertionResult.Success
            }
        }
    }
}