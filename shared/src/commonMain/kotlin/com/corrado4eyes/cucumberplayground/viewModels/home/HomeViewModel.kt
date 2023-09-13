package com.corrado4eyes.cucumberplayground.viewModels.home

import com.corrado4eyes.cucumberplayground.models.Strings
import com.corrado4eyes.cucumberplayground.services.AuthService
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : BaseLifecycleViewModel(), KoinComponent {

    private val authService: AuthService by inject()

    val screenTitle = Strings.homeScreenTitle
    val buttonTitle = Strings.logoutButtonText

    fun getCurrentUser() = authService.getCurrentUserIfAny()!!
    val user = authService.getCurrentUserIfAny()!!

    fun logout() {
        coroutineScope.launch {
            authService.logout()
        }
    }
}
