package com.example.githubuserlist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.githubuserlist.ui.screens.home.HomeScreen
import com.example.githubuserlist.ui.theme.GithubUserListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUserListTheme {
                AppNavHost(
                    navController = rememberNavController()
                )
            }
        }
    }
}
