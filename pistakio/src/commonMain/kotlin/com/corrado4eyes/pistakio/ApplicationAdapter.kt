package com.corrado4eyes.pistakio

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow

class CMAppLaunchedAlreadyException : Throwable() {
    override val message: String = "The app is already launched"
}

class CMAppNotLaunchedYetException : Throwable() {
    override val message: String = "The app was not launched"
}

enum class TimeoutDuration(val duration: Duration) {
    SHORT(10.seconds),
    MEDIUM(30.seconds),
    LONG(60.seconds)
}

typealias ApplicationArguments = Map<String, String>?

interface ApplicationAdapter {

    fun launch(identifier: String? = null, arguments: Map<String, String>)
    fun findView(tag: String): Node
    fun tearDown()

    fun assert(assertionResult: AssertionResult)
    fun assertUntil(timeout: TimeoutDuration, blockAssertionResult: () -> AssertionResult)
    fun assertAll(assertions: List<AssertionResult>)

    interface TearDownHandler {
        fun tearDown()
    }
}

abstract class BaseApplicationAdapter : ApplicationAdapter {
    private val isAppLaunched = MutableStateFlow(false)

    override fun launch(identifier: String?, arguments: Map<String, String>) {
        if (isAppLaunched.value) {
            throw CMAppLaunchedAlreadyException()
        }
        isAppLaunched.value = true
    }

    override fun tearDown() {
        isAppLaunched.value = false
    }

    protected fun assertAppIsRunning() {
        if (!isAppLaunched.value) {
            throw CMAppNotLaunchedYetException()
        }
    }
}

expect class DefaultApplicationAdapter: BaseApplicationAdapter
