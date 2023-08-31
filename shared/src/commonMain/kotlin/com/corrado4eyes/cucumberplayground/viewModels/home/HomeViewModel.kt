package com.corrado4eyes.cucumberplayground.viewModels.home

import com.corrado4eyes.cucumberplayground.services.AuthService
import com.splendo.kaluga.alerts.BaseAlertPresenter
import com.splendo.kaluga.alerts.buildAlert
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import com.splendo.kaluga.hud.BaseHUD
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel(
    private val alertPresenterBuilder: BaseAlertPresenter.Builder,
    private val hudBuilder: BaseHUD.Builder,
) : BaseLifecycleViewModel(alertPresenterBuilder), KoinComponent {

    private val authService: AuthService by inject()

    val screenTitle = "Home screen"
    val buttonTitle = "Logout"

    fun getCurrentUser() = authService.getCurrentUserIfAny()!!
    val user = authService.getCurrentUserIfAny()!!

    private fun displayConfirmationDialog() {
        alertPresenterBuilder.buildAlert(coroutineScope) {
            setTitle("Are you sure you want to logout?")
            setPositiveButton("Yes") {
                coroutineScope.launch {
                    authService.logout()
                }
            }
            setNegativeButton("No") {
                // Do nothing
            }
        }.showAsync()
    }

    fun logout() {
        coroutineScope.launch {
            authService.logout()
        }
        //displayConfirmationDialog()
    }
}
