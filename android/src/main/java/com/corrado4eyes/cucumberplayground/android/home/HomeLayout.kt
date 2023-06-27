package com.corrado4eyes.cucumberplayground.android.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.corrado4eyes.cucumberplayground.home.HomeViewModel

@Composable
fun HomeLayout(
    userMail: String,
    logoutEvent: () -> Unit
) {
    val homeViewModel = remember { HomeViewModel(userMail, logoutEvent) }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(homeViewModel.userMail)
        Button(logoutEvent) {
            Text("Logout")
        }
    }
}

@Composable
@Preview
fun HomeLayoutPreview() {
    HomeLayout(
        "test@test.com"
    ) {}
}
