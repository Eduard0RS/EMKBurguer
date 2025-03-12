package com.devedotech.emkburguer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devedotech.emkburguer.ui.navigation.NavGraph
import com.devedotech.emkburguer.ui.theme.EMKBurguerTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EMKBurguerTheme {
               NavGraph()
            }
        }
    }
}