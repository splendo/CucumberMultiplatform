package com.corrado4eyes.cucumberplayground.common.model.main

import com.corrado4eyes.cucumberplayground.login.AuthServiceImpl
import com.splendo.kaluga.architecture.observable.toInitializedObservable
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class Navigator {
    object Loading : Navigator()
    object Login : Navigator()
    data class Home(
        val email: String,
        val logoutEven: () -> Unit
    ) : Navigator()
}

class MainViewModel : BaseLifecycleViewModel() {

    private val authService = AuthServiceImpl()

    private val _navState: MutableStateFlow<Navigator> = MutableStateFlow(Navigator.Loading)
    val navState = _navState.toInitializedObservable(Navigator.Loading, coroutineScope)

    init {
        coroutineScope.launch {
            authService.observeUser().collect { user ->
                user?.let {
                    _navState.value = Navigator.Home(it.email, ::logout)
                } ?: run { _navState.value = Navigator.Login }
            }
        }
    }

    private fun logout() {
        coroutineScope.launch { authService.logout() }
    }
}
