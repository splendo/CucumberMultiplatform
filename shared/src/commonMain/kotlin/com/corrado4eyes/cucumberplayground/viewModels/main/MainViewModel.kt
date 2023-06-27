package com.corrado4eyes.cucumberplayground.viewModels.main

import com.corrado4eyes.cucumberplayground.login.AuthService
import com.corrado4eyes.cucumberplayground.models.User
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.launch

sealed class HomeViewState {
    data class Valid(val userEmail: String) : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    object Loading : HomeViewState()
}

class HomeViewModel(
    val user: User,
    private val authService: AuthService
) : BaseLifecycleViewModel() {

    fun logout() {
        coroutineScope.launch {
            authService.logout()
        }
    }
}
