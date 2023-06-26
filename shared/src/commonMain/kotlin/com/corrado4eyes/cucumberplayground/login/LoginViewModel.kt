package com.corrado4eyes.cucumberplayground.login

import com.corrado4eyes.cucumberplayground.login.model.AuthResponse
import com.splendo.kaluga.architecture.observable.subjectOf
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.launch

class LoginViewModel() : BaseLifecycleViewModel() {

    private val authService = AuthServiceImpl()

    val email = subjectOf("")
    val password = subjectOf("")

    fun login() {
        coroutineScope.launch {
            val password by password
            val email by email
            when(authService.login(email.value, password.value)) {
                is AuthResponse.Error -> TODO()
                AuthResponse.Success -> TODO()
            }
        }
    }
}
