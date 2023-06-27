package com.corrado4eyes.cucumberplayground.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.android.home.HomeLayout
import com.corrado4eyes.cucumberplayground.android.login.LoginLayout
import com.corrado4eyes.cucumberplayground.common.model.main.MainViewModel
import com.corrado4eyes.cucumberplayground.common.model.main.Navigator
import com.splendo.kaluga.architecture.compose.state

@Composable
fun MainActivityLayout() {
    MyApplicationTheme {
        val mainViewModel = remember { MainViewModel() }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navState by mainViewModel.navState.state()
            when(val state = navState) {
                is Navigator.Home -> HomeLayout(state.email, state.logoutEvent)
                Navigator.Loading -> {}
                is Navigator.Login -> LoginLayout(state.loginEvent)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainActivityLayout()
    }
}
