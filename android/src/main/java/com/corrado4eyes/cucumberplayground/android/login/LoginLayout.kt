package com.corrado4eyes.cucumberplayground.android.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.login.LoginViewModel
import com.splendo.kaluga.architecture.compose.state
import com.splendo.kaluga.architecture.observable.SimpleInitializedSubject

@Composable
fun LoginLayout(loginEvent: (String, String) -> Unit) {
    val viewModel = remember { LoginViewModel(loginEvent) }
    Column {
        CustomTextField(
            value = viewModel.email,
            label = "Email"
        )
        CustomTextField(
            value = viewModel.password,
            label = "Password"
        )
        Button(viewModel::login) {
            Text("Login")
        }
    }
}

@Composable
fun CustomTextField(
    value: SimpleInitializedSubject<String>,
    label: String
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
        }
    )
}

@Preview
@Composable
fun LoginLayoutPreview(loginEvent: (String, String) -> Unit) {
    LoginLayout { mail, pass -> println("$mail $pass") }
}
