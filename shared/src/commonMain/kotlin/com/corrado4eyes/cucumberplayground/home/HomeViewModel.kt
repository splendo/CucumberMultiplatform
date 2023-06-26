package com.corrado4eyes.cucumberplayground.home

import com.corrado4eyes.cucumberplayground.common.model.User
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel

sealed class HomeViewState {
    data class Valid(val userEmail: String) : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    object Loading : HomeViewState()
}

class HomeViewModel(
    user: User,
    private val logoutEvent: () -> Unit
) : BaseLifecycleViewModel() {

    val email = user.email

    fun logout() {
        logoutEvent()
    }
}
