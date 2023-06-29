package com.corrado4eyes.cucumberplayground.cucumber.viewModels

import com.corrado4eyes.cucumberplayground.cucumber.login.AuthServiceMock
import com.corrado4eyes.cucumberplayground.login.AuthService
import com.corrado4eyes.cucumberplayground.viewModels.login.LoginViewModel
import com.splendo.kaluga.test.koin.KoinUIThreadViewModelTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.get
import org.koin.dsl.module

class LoginViewModelTest : KoinUIThreadViewModelTest<LoginViewModelTest.KoinContext, LoginViewModel>() {
    class KoinContext : KoinViewModelTestContext<LoginViewModel>(
        module {
            single<AuthService> { AuthServiceMock() }
        }
    ) {
        val authService = get<AuthService>() as AuthServiceMock
        override val viewModel: LoginViewModel = LoginViewModel(authService)
    }

    @Test
    fun test_on_login_button_pressed_fail_with_empty_email() = testOnUIThread {
        viewModel.login()
        assertTrue(viewModel.emailText.stateFlow.value.isEmpty())
//        assertEquals(Colors.red, viewModel.emailTextFieldBorderColor.stateFlow.value)
        assertEquals("Missing email", viewModel.emailErrorText.stateFlow.value)
    }

    @Test
    fun test_on_login_button_pressed_fail_with_empty_password() = testOnUIThread {
        fillCredentials(withPassword = false)
        viewModel.login()
        assertFalse(viewModel.emailText.stateFlow.value.isEmpty())
        assertTrue(viewModel.passwordText.stateFlow.value.isEmpty())
        assertEquals("", viewModel.emailErrorText.stateFlow.value)
//        assertEquals(Colors.red, viewModel.passwordTextFieldBorderColor.stateFlow.value)
        assertEquals("Missing password", viewModel.passwordErrorText.stateFlow.value)
    }

    @Test
    fun test_on_login_fails_with_invalid_credentials() = testOnUIThread {
        fillCredentials(withPassword = true)
        viewModel.login()
        assertEquals("Incorrect email or password", viewModel.formFooterError.stateFlow.value)
    }

    @Test
    fun test_on_login_succeed() = testOnUIThread {
        viewModel.emailText.stateFlow.value = "corrado@corrado.com"
        viewModel.passwordText.stateFlow.value = "1234"

        viewModel.login()
//        assertEquals() Navigation goes to Home
    }

    private inline fun KoinContext.fillCredentials(withPassword: Boolean) {
        viewModel.emailText.stateFlow.value = "email@test.com"
        if (withPassword) {
            viewModel.passwordText.stateFlow.value = "password"
        }
    }

    override val createTestContext: suspend (scope: CoroutineScope) -> KoinContext
        get() = { KoinContext() }
}