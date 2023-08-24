package com.corrado4eyes.cucumberplayground.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.android.home.HomeLayout
import com.corrado4eyes.cucumberplayground.android.login.LoginLayout
import com.corrado4eyes.cucumberplayground.models.DefaultTestConfiguration
import com.corrado4eyes.cucumberplayground.models.TestConfiguration
import com.corrado4eyes.cucumberplayground.viewModels.main.AppNavigator
import com.corrado4eyes.cucumberplayground.viewModels.main.MainViewModel
import com.splendo.kaluga.architecture.compose.state
import com.splendo.kaluga.architecture.compose.viewModel.ViewModelComposable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainActivityLayout(testConfiguration: TestConfiguration? = null) {
    MyApplicationTheme {
        val viewModel = koinViewModel<MainViewModel> {
            parametersOf(testConfiguration)
        }
        ViewModelComposable(viewModel) {
            // Nothing executes in here :(
            // Suspended?
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val navState by this.navState.state()
                when (navState) {
                    is AppNavigator.Home -> HomeLayout()
                    is AppNavigator.Loading -> {
                        Text("LOADING")
                    }

                    is AppNavigator.Login -> LoginLayout()
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainActivityLayout(DefaultTestConfiguration(mapOf()))
    }
}
