package com.corrado4eyes.cucumberplayground.home

import com.corrado4eyes.cucumberplayground.home.HomeViewState.Loading
import com.corrado4eyes.cucumberplayground.login.AuthServiceImpl
import com.splendo.kaluga.architecture.observable.toInitializedObservable
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class HomeViewState {
    data class Valid(val userEmail: String) : HomeViewState()
    data class Error(val error: String) : HomeViewState()
    object Loading : HomeViewState()
}

class HomeViewModel() : BaseLifecycleViewModel() {

    private val authService = AuthServiceImpl()

    private val _homeViewState: MutableStateFlow<HomeViewState> = MutableStateFlow(Loading)
    val homeViewState = _homeViewState.toInitializedObservable(Loading, coroutineScope)

    init {
        val user = authService.getCurrentUserIfAny()
        user?.let {
            _homeViewState.value = HomeViewState.Valid(it.email)
        } ?: run {
            _homeViewState.value = HomeViewState.Error("User not found")
        }
    }

    fun logout() {
        coroutineScope.launch {
            authService.logout()
            // TODO redirect back to login
        }
    }
}
