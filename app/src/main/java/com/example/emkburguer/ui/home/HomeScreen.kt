package com.example.emkburguer.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController?, viewModel: HomeViewModel) {

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(navController = null, HomeViewModel(LocalContext.current))
}
