package com.splendo.cucumberplayground.viewModels.home

import com.splendo.cucumberplayground.models.Strings
import com.splendo.cucumberplayground.services.AuthService
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : BaseLifecycleViewModel(), KoinComponent {

    private val authService: AuthService by inject()
    val screenTitle = Strings.Screen.Title.home

    val buttonTitle = Strings.Button.Title.logout
    fun getCurrentUser() = authService.getCurrentUserIfAny()!!

    val user = authService.getCurrentUserIfAny()!!
    val scrollableItems: List<Int> = (1..20).toList()

    fun logout() {
        coroutineScope.launch {
            authService.logout()
        }
    }
}
