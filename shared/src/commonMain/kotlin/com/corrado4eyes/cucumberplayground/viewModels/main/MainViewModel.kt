package com.splendo.cucumberplayground.viewModels.main

import com.splendo.cucumberplayground.models.TestConfiguration
import com.splendo.cucumberplayground.models.User
import com.splendo.cucumberplayground.services.AuthService
import com.splendo.kaluga.architecture.observable.toInitializedObservable
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class AppNavigator {
    object Loading : AppNavigator()
    object Login : AppNavigator()

    data class Home(
        val user: User,
    ) : AppNavigator()
}

class MainViewModel(
    private val testConfiguration: TestConfiguration? = null
) : BaseLifecycleViewModel(), KoinComponent {

    private val authService: AuthService by inject()

    private val _navState: MutableStateFlow<AppNavigator> = MutableStateFlow(AppNavigator.Loading)
    val navState = _navState.toInitializedObservable(AppNavigator.Loading, coroutineScope)

    init {
        coroutineScope.launch {
            if (testConfiguration != null) {
                if (testConfiguration.isLoggedIn) {
                    authService.login(testConfiguration.testEmail, "1234")
                } else {
                    authService.logout()
                }
            }
            authService.observeUser.collect { user ->
                user?.let {
                    _navState.value = AppNavigator.Home(it)
                } ?: run {
                    _navState.value = AppNavigator.Login
                }
            }
        }
    }
}
