package com.corrado4eyes.cucumberplayground.android.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.services.AuthService
import com.corrado4eyes.cucumberplayground.services.AuthServiceImpl
import com.corrado4eyes.cucumberplayground.models.User
import com.corrado4eyes.cucumberplayground.viewModels.home.HomeViewModel
import com.splendo.kaluga.architecture.compose.viewModel.ViewModelComposable
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeLayout() {
    val viewModel = koinViewModel<HomeViewModel>()
    ViewModelComposable(viewModel) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                this@ViewModelComposable.screenTitle,
                modifier = Modifier.testTag(this@ViewModelComposable.screenTitle)
            )
            Text(this@ViewModelComposable.user.email)
            Button(
                this@ViewModelComposable::logout,
                modifier = Modifier.testTag(this@ViewModelComposable.buttonTitle)
            ) {
                Text(this@ViewModelComposable.buttonTitle)
            }
        }
    }
}

@Composable
@Preview
fun HomeLayoutPreview() {
    HomeLayout()
}
