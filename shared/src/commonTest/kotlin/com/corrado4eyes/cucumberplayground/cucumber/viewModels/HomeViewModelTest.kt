package com.splendo.cucumberplayground.cucumber.viewModels

import com.splendo.cucumberplayground.cucumber.login.AuthServiceMock
import com.splendo.cucumberplayground.cucumber.login.DummyUsers.defaultTestUser
import com.splendo.cucumberplayground.models.Strings
import com.splendo.cucumberplayground.services.AuthService
import com.splendo.cucumberplayground.viewModels.home.HomeViewModel
import com.splendo.kaluga.test.base.yieldMultiple
import com.splendo.kaluga.test.koin.KoinUIThreadViewModelTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.get
import org.koin.dsl.module

class HomeViewModelTest : KoinUIThreadViewModelTest<HomeViewModelTest.KoinContext, HomeViewModel>() {
    class KoinContext : KoinViewModelTestContext<HomeViewModel>(
        module {
            single<AuthService> { AuthServiceMock(getOrNull()) }
            single { defaultTestUser }
        }
    ) {
        val authService = get<AuthService>() as AuthServiceMock
        override val viewModel: HomeViewModel = HomeViewModel()
    }

    @Test
    fun test_title_matches_value() = testOnUIThread {
        assertEquals(Strings.Screen.Title.home, viewModel.screenTitle)
    }

    @Test
    fun test_button_title_matches_value() = testOnUIThread {
        assertEquals(Strings.Button.Title.logout, viewModel.buttonTitle)
    }

    @Test
    fun test_button_action_triggers_logout() = testOnUIThread {
        assertEquals(0, authService.logoutCalledTimes)
        assertEquals(defaultTestUser, authService.getCurrentUserIfAny())
        viewModel.logout()
        yieldMultiple(3)
        assertEquals(1, authService.logoutCalledTimes)
        assertNull(authService.getCurrentUserIfAny())

    }

    @Test
    fun test_list_contains_20_items() = testOnUIThread {
        assertEquals(20, viewModel.scrollableItems.size)
    }

    override val createTestContext: suspend (scope: CoroutineScope) -> KoinContext
        get() = { KoinContext() }

}