package com.example.zayatspexelsapp.app.ui.presentation.details

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zayatspexelsapp.app.downloader.PhotoDownloaderImpl
import com.example.zayatspexelsapp.app.navigation.Screen
import com.example.zayatspexelsapp.app.ui.presentation.LoadingProgressBar
import com.example.zayatspexelsapp.app.ui.presentation.ErrorScreen
import com.example.zayatspexelsapp.app.ui.presentation.ViewModelErrorType

@Composable
fun DetailsPage(
    navController: NavController,
    id: String?,
    viewModel: DetailsPageViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val downloader = PhotoDownloaderImpl(LocalContext.current)

    when (navController.previousBackStackEntry?.destination?.route) {
        Screen.Home.route -> {
            if (id != null) {
                viewModel.getPhotoFromApi(id.toInt())
            }
        }

        Screen.Bookmarks.route -> {
            viewModel.getPhotoFromBookmarksScreen(id?.toInt())
        }
    }

    val window = (LocalContext.current as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    Scaffold(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        topBar = {
            AppToolbar(
                title = uiState.value.photo?.photographer ?: "",
                hasNavigation = true,
                onNavigationItemClicked = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            LoadingProgressBar(loading = (uiState.value.photo == null || uiState.value.isLoading)
                    && uiState.value.errorType == null)

            if (uiState.value.photo != null) {
                PhotoDetails(
                    url = uiState.value.photo?.src?.original,
                    viewModel = viewModel
                )

                DetailsBottomBlock(
                    isPhotoFavourite = uiState.value.isPhotoFavourite,
                    onDownloadClicked = {
                         downloader.downloadPhoto(uiState.value.photo!!.src.original, uiState.value.photo!!.alt.trim())
                    },
                    onFavouritesClicked = {
                         viewModel.onFavouriteButtonClicked(uiState.value.photo!!)
                    }
                )
            }
        }
        if(uiState.value.errorType is ViewModelErrorType.ImageNotFound) {
            ErrorScreen(
                errorType = ViewModelErrorType.ImageNotFound(),
                onRetryClicked = { navController.popBackStack() }
            )
        }
    }
}
