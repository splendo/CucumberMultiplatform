package com.corrado4eyes.cucumberplayground.login

import com.splendo.kaluga.architecture.observable.subjectOf
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel

class LoginViewModel(
    private val loginEvent: (email: String, pass: String) -> Unit
) : BaseLifecycleViewModel() {

    val email = subjectOf("")
    val password = subjectOf("")

    fun login() {
        val email by email
        val password by password
        loginEvent(email.value, password.value)
    }
}
