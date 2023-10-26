package com.splendo.cucumberplayground.models

typealias TestConfigurationMap = Map<String, String?>

class ConfigurationNotFoundException(message: String) : Throwable(message)

interface TestConfiguration {
    val isLoggedIn: Boolean
    val testEmail: String
}

class DefaultTestConfiguration(private val configuration: TestConfigurationMap) : TestConfiguration {
    override val isLoggedIn: Boolean = getValue("isLoggedIn") == "true"
    override val testEmail = getValue("testEmail")

    private fun getValue(key: String, default: String = ""): String = configuration[key] ?: default
}
