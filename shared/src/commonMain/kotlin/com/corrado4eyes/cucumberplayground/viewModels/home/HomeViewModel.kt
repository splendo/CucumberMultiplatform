package com.corrado4eyes.cucumberplayground.viewModels.home

import com.corrado4eyes.cucumberplayground.login.AuthService
import com.corrado4eyes.cucumberplayground.models.User
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    val user: User,
    private val authService: AuthService
) : BaseLifecycleViewModel() {

    val screenTitle = "Home"
    val buttonTitle = "Logout"

    fun logout() {
        coroutineScope.launch {
            authService.logout()
        }
    }
}
