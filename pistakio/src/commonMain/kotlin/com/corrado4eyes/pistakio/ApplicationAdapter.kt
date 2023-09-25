package com.corrado4eyes.pistakio

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

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

typealias ApplicationArguments = Map<String, String>

interface ApplicationAdapter {

    val applicationArguments: ApplicationArguments

    fun launch(identifier: String? = null, arguments: Map<String, String>)
    fun findView(tag: String): Node
    fun tearDown()

    fun assert(assertionResult: AssertionResult)
    fun assertUntil(timeout: TimeoutDuration, blockAssertionResult: () -> AssertionResult)
    fun assertAll(assertions: List<AssertionResult>)

    interface TearDownHandler {
        fun tearDown()
    }

    operator fun get(key: String): String?
    operator fun set(key: String, value: String)
}

abstract class BaseApplicationAdapter : ApplicationAdapter {

    private val _applicationArguments = mutableMapOf<String, String>()
    override val applicationArguments: ApplicationArguments
        get() = _applicationArguments

    private var isAppLaunched = false

    override fun launch(identifier: String?, arguments: Map<String, String>) {
        if (isAppLaunched) {
            throw CMAppLaunchedAlreadyException()
        }

        isAppLaunched = true
    }

    override fun tearDown() {
        isAppLaunched = false
    }

    protected fun assertAppIsRunning() {
        if (!isAppLaunched) {
            throw CMAppNotLaunchedYetException()
        }
    }

    override operator fun get(key: String): String? = applicationArguments[key]
    override operator fun set(key: String, value: String) {
        _applicationArguments[key] = value
    }
}

expect class DefaultApplicationAdapter: BaseApplicationAdapter
