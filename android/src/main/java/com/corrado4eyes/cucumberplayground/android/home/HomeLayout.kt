package com.corrado4eyes.cucumberplayground.android.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.login.AuthService
import com.corrado4eyes.cucumberplayground.login.AuthServiceImpl
import com.corrado4eyes.cucumberplayground.models.User
import com.corrado4eyes.cucumberplayground.viewModels.home.HomeViewModel

@Composable
fun HomeLayout(
    user: User,
    authService: AuthService
) {
    val homeViewModel = remember { HomeViewModel(user, authService) }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(homeViewModel.user.email)
        Button(homeViewModel::logout) {
            Text("Logout")
        }
    }
}

@Composable
@Preview
fun HomeLayoutPreview() {
    HomeLayout(User("test@test.com", "1234"), AuthServiceImpl())
}
