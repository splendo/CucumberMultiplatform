package com.corrado4eyes.cucumberplayground.models

typealias TestConfigurationMap = Map<String, String>
data class TestConfiguration(val configuration: TestConfigurationMap) {
    val isLoggedIn = configuration["isLoggedIn"] == "true"
    val testEmail = configuration["testEmail"] ?: "test@test.com"
}
