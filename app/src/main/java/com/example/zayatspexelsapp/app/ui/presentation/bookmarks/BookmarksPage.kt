package com.example.zayatspexelsapp.app.ui.presentation.bookmarks

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.zayatspexelsapp.R
import com.example.zayatspexelsapp.app.navigation.Screen
import com.example.zayatspexelsapp.app.ui.presentation.ErrorScreen
import com.example.zayatspexelsapp.app.ui.presentation.LoadingProgressBar
import com.example.zayatspexelsapp.app.ui.presentation.ViewModelErrorType
import com.example.zayatspexelsapp.app.ui.presentation.details.AppToolbar

@Composable
fun BookmarksPage(
    navController: NavHostController,
    viewModel: BookmarksPageViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    val window = (LocalContext.current as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    LaunchedEffect(Unit) {
        viewModel.getFavouritePhotos()
    }

    Scaffold(
        topBar = {
            AppToolbar(title = stringResource(R.string.bookmarks_title), hasNavigation = false)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LoadingProgressBar(loading = uiState.value.favouritePhotos.isEmpty()
                    && uiState.value.errorType !is ViewModelErrorType)

            when(uiState.value.errorType) {
                is ViewModelErrorType -> {
                    ErrorScreen(
                        errorType = uiState.value.errorType!!,
                        onRetryClicked = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                null -> {
                    BookmarksBlock(
                        photos = uiState.value.favouritePhotos,
                        onPhotoClicked = {
                            navController.navigate("details/$it")
                        }
                    )
                }
            }
        }
    }
}