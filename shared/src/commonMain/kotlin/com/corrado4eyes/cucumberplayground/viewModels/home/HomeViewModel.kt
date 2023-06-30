package com.corrado4eyes.cucumberplayground.viewModels.home

import com.corrado4eyes.cucumberplayground.services.AuthService
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : BaseLifecycleViewModel(), KoinComponent {

    private val authService: AuthService by inject()

    val screenTitle = "Home screen"
    val buttonTitle = "Logout"
    val user = authService.user!!

    fun logout() {
        coroutineScope.launch {
            authService.logout()
        }
    }
}
