package com.corrado4eyes.cucumberplayground.android.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.viewModels.login.LoginViewModel
import com.splendo.kaluga.architecture.compose.state
import com.splendo.kaluga.architecture.compose.viewModel.ViewModelComposable
import com.splendo.kaluga.architecture.observable.StateFlowInitializedSubject
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginLayout() {
    val viewModel = koinViewModel<LoginViewModel>()
    ViewModelComposable(viewModel) {
        val isLoading by this.isLoading.state()
        Column {
            Text(text = this@ViewModelComposable.screenTitle, modifier = Modifier.testTag("Login screen"))
            CustomTextField(
                value = this@ViewModelComposable.emailText,
                label = "Email",
                modifier = Modifier.testTag("Email")
            )
            val emailErrorText by this@ViewModelComposable.emailErrorText.state()
            Text(text = emailErrorText, color = Color.Red)
            CustomTextField(
                value = this@ViewModelComposable.passwordText,
                label = "Password",
                modifier = Modifier.testTag("Password")
            )
            val passwordErrorText by this@ViewModelComposable.passwordErrorText.state()
            Text(text = passwordErrorText, color = Color.Red)

            val formFooterErrorText by this@ViewModelComposable.formFooterError.state()
            Text(text = formFooterErrorText, color = Color.Red)
            if (isLoading) {
                CircularProgressIndicator()
            }

            Button(
                this@ViewModelComposable::login,
                modifier = Modifier.testTag("Login")
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: StateFlowInitializedSubject<String>,
    label: String,
    modifier: Modifier = Modifier
) {
    val text = value.state()
    val textValue = remember { mutableStateOf(TextFieldValue(text.value)) }
    TextField(
        value = textValue.value,
        onValueChange = {
            value.post(it.text)
            textValue.value = it
        },
        label = {
            Text(label)
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun LoginLayoutPreview(loginEvent: (String, String) -> Unit) {
    LoginLayout()
}
