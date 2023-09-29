package com.corrado4eyes.pistakio

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class AppLaunchedAlreadyException : Throwable() {
    override val message: String = "The app is already launched"
}

class AppNotLaunchedYetException : Throwable() {
    override val message: String = "The app was not launched"
}

enum class TimeoutDuration(val duration: Duration) {
    SHORT(10.seconds),
    MEDIUM(30.seconds),
    LONG(60.seconds)
}

sealed class SwipeDuration(val duration: Duration) {
    object Short : SwipeDuration(50.milliseconds)
    object Medium : SwipeDuration(100.milliseconds)
    object Long : SwipeDuration(200.milliseconds)
    class Custom(duration: Duration) : SwipeDuration(duration)
}

typealias ApplicationArguments = Map<String, String>

interface ApplicationAdapter {

    /**
     * Map of arguments passed through the application arguments into the app MainActivity or MainView.
     * Such map contains arguments to set up the app in a state required by certain tests. Both the
     * content of the map and the implementation is up to the developer.
     */
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
            throw AppLaunchedAlreadyException()
        }

        isAppLaunched = true
    }

    override fun tearDown() {
        isAppLaunched = false
    }

    protected fun assertAppIsRunning() {
        if (!isAppLaunched) {
            throw AppNotLaunchedYetException()
        }
    }

    override operator fun get(key: String): String? = applicationArguments[key]
    override operator fun set(key: String, value: String) {
        _applicationArguments[key] = value
    }
}

expect class DefaultApplicationAdapter: BaseApplicationAdapter
