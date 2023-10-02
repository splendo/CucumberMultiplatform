package com.corrado4eyes.cucumberplayground.android.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corrado4eyes.cucumberplayground.models.Strings
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
                modifier = Modifier.testTag(Strings.Screen.Tag.home)
            )
            Text(
                this@ViewModelComposable.getCurrentUser().email,
                modifier = Modifier.testTag(this@ViewModelComposable.getCurrentUser().email)
            )
            Button(
                this@ViewModelComposable::logout,
                modifier = Modifier.testTag(Strings.Button.Tag.logout)
            ) {
                Text(this@ViewModelComposable.buttonTitle)
            }
            
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .testTag(Strings.ScrollView.Tag.homeScrollView)
            ) {
                items(
                    viewModel.scrollableItems,
                    key = { it }
                ) {
                    Text(it.toString())
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeLayoutPreview() {
    HomeLayout()
}
