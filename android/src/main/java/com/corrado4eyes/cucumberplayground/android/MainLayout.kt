package com.corrado4eyes.cucumberplayground.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainActivityLayout() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            // TODO nav
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        // TODO
    }
}
