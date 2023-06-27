package com.corrado4eyes.cucumberplayground.viewModels.main

import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel

sealed class HomeViewState {
    data class Valid(val userEmail: String) : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    object Loading : HomeViewState()
}

class HomeViewModel(
    val userMail: String,
    private val logoutEvent: () -> Unit
) : BaseLifecycleViewModel() {
    fun logout() {
        logoutEvent()
    }
}
