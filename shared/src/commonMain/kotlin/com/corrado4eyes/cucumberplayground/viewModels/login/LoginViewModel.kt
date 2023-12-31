package com.corrado4eyes.cucumberplayground.viewModels.login

import com.corrado4eyes.cucumberplayground.login.model.AuthResponse
import com.corrado4eyes.cucumberplayground.models.Strings
import com.corrado4eyes.cucumberplayground.services.AuthService
import com.splendo.kaluga.architecture.observable.toInitializedObservable
import com.splendo.kaluga.architecture.observable.toInitializedSubject
import com.splendo.kaluga.architecture.viewmodel.BaseLifecycleViewModel
import com.splendo.kaluga.resources.KalugaColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : BaseLifecycleViewModel(), KoinComponent {

    sealed class LoginViewState {
        object Idle : LoginViewState()
        sealed class Error(val error: String) : LoginViewState() {
            enum class MissingField(val stringValue: String) {
                EMAIL(Strings.TextField.Tag.email), PASSWORD(Strings.TextField.Tag.password)
            }

            object IncorrectEmailOrPassword : Error("Incorrect email or password")
            data class EmptyField(val missingField: MissingField) :
                Error("Missing ${missingField.stringValue}")
        }

        object Loading : LoginViewState()
    }

    private val authService: AuthService by inject()

    private val viewState = MutableStateFlow<LoginViewState>(LoginViewState.Idle)

    val screenTitle = Strings.Screen.Title.login
    private val emailTextFieldState = MutableStateFlow("")
    val emailText = emailTextFieldState.toInitializedSubject(coroutineScope)
    val emailPlaceholder = Strings.TextField.Placeholder.email
    val emailErrorText = viewState
        .map {
            if (it is LoginViewState.Error.EmptyField) {
                if (it.missingField == LoginViewState.Error.MissingField.EMAIL) {
                    it.error
                } else ""
            } else ""
        }.toInitializedObservable("", coroutineScope)

    private val _emailTextFieldBorderColor = MutableStateFlow<KalugaColor?>(null)
    val emailTextFieldBorderColor =
        _emailTextFieldBorderColor.toInitializedObservable(coroutineScope)

    private val passwordTextFieldState = MutableStateFlow("")
    val passwordText = passwordTextFieldState.toInitializedSubject(coroutineScope)
    val passwordPlaceholder = Strings.TextField.Placeholder.password
    val passwordErrorText = viewState
        .map {
            if (it is LoginViewState.Error.EmptyField) {
                if (it.missingField == LoginViewState.Error.MissingField.PASSWORD) {
                    it.error
                } else ""
            } else ""
        }.toInitializedObservable("", coroutineScope)

    private val _passwordTextFieldBorderColor = MutableStateFlow<KalugaColor?>(null)
    val passwordTextFieldBorderColor =
        _passwordTextFieldBorderColor.toInitializedObservable(coroutineScope)

    val formFooterError = viewState
        .map {
            if (it is LoginViewState.Error.IncorrectEmailOrPassword) {
                it.error
            } else ""
        }.toInitializedObservable("", coroutineScope)

    val buttonTitle = Strings.Button.Text.login
    val isButtonEnabled = viewState.map { it !is LoginViewState.Loading }
        .toInitializedObservable(false, coroutineScope)
    val isLoading = viewState.map { it is LoginViewState.Loading }
        .toInitializedObservable(false, coroutineScope)

    fun login() {
        viewState.value = LoginViewState.Loading
        coroutineScope.launch {
            val password by passwordText
            val email by emailText

            when {
                email.value.isEmpty() -> {
                    viewState.value = LoginViewState.Error.EmptyField(
                        LoginViewState.Error.MissingField.EMAIL
                    )
                    return@launch
                }

                password.value.isEmpty() -> {
                    viewState.value = LoginViewState.Error.EmptyField(
                        LoginViewState.Error.MissingField.PASSWORD
                    )
                    return@launch
                }
            }
            delay(1000)
            when (authService.login(email.value, password.value)) {
                is AuthResponse.Success -> viewState.value =
                    LoginViewState.Idle // navigate to Home screen
                is AuthResponse.Error -> {
                    viewState.value = LoginViewState.Error.IncorrectEmailOrPassword
                    return@launch
                }
            }
        }
    }
}
