package com.example.zayatspexelsapp.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zayatspexelsapp.app.navigation.AppBottomBar
import com.example.zayatspexelsapp.app.navigation.NavGraphSetup
import com.example.zayatspexelsapp.app.navigation.Screen
import com.example.zayatspexelsapp.app.ui.theme.ZayatsPexelsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZayatsPexelsAppTheme {
                PexelsApplication()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PexelsApplication() {
    val navController = rememberNavController()
    val pages = listOf(Screen.Home, Screen.Bookmarks)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in pages.map { it.route }) {
                Surface(shadowElevation = 10.dp) {
                    AppBottomBar(navController = navController)

                }
            }
        }
    ) {
        NavGraphSetup(navController = navController, startDestination = Screen.Splash.route)
    }
}