package com.example.zayatspexelsapp.app.ui.presentation.home

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zayatspexelsapp.R
import com.example.zayatspexelsapp.app.ui.presentation.CustomAppSearchBar
import com.example.zayatspexelsapp.app.ui.presentation.ErrorScreen
import com.example.zayatspexelsapp.app.ui.presentation.LoadingProgressBar
import com.example.zayatspexelsapp.app.ui.presentation.ViewModelErrorType
import com.example.zayatspexelsapp.common.hasInternetConnection
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    navController: NavController,
    viewModel: HomePageViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val window = (context as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    Column {
        CustomAppSearchBar(
            text = uiState.value.searchQuery,
            onQueryChange = {
                viewModel.changeSearchQuery(it)

                if(it.isNotBlank()) {
                    coroutineScope.launch {
                        val collectionId = viewModel.findFeaturedCollectionIdByTitle(it)
                        if (collectionId != null) {
                            viewModel.changeSelectedId(collectionId)
                        }
                        viewModel.searchPhotos(it)
                    }
                }
            },
            onSearch = {
                coroutineScope.launch {
                    if (it.isNotBlank()) {
                        val collectionId = viewModel.findFeaturedCollectionIdByTitle(it)
                        if (collectionId != null) {
                            viewModel.changeSelectedId(collectionId)
                        }
                        viewModel.searchPhotos(it)
                    } else {
                        viewModel.getCuratedPhotos()
                    }
                }
            },
        )

        FeaturedCollections(
            selectedId = uiState.value.selectedFeaturedCollectionId,
            items = uiState.value.featuredCollections ?: listOf(),
            changeSearchText = { query, id ->
                viewModel.changeSearchQuery(query)
                viewModel.changeSelectedId(id)
                coroutineScope.launch {
                    viewModel.searchPhotos(query)
                }
            }
        )

        LoadingProgressBar(
            loading = uiState.value.isLoading && (uiState.value.errorType == null)
        )

        if (hasInternetConnection(LocalContext.current)) {
            when (uiState.value.errorType) {
                is ViewModelErrorType.NoInternetConnection -> {
                    ErrorScreen(
                        errorType = uiState.value.errorType!!,
                        onRetryClicked = {
                            coroutineScope.launch {
                                if (uiState.value.searchQuery.isNotBlank()) {
                                    viewModel.searchPhotos(uiState.value.searchQuery)
                                } else {
                                    viewModel.getCuratedPhotos()
                                }
                            }
                        }
                    )
                }

                is ViewModelErrorType.NoResultsFound -> {
                    ErrorScreen(
                        errorType = uiState.value.errorType!!,
                        onRetryClicked = {
                            coroutineScope.launch {
                                viewModel.changeSearchQuery("")
                                viewModel.getCuratedPhotos()
                            }
                        }
                    )
                }

                else -> {
                    PhotosBlock(
                        photos = uiState.value.photos,
                        viewModel = viewModel,
                        onPhotoClicked = {
                            navController.navigate("details/$it")
                        }
                    )
                }
            }
        } else {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.no_connection_toast),
                Toast.LENGTH_LONG
            ).show()

            ErrorScreen(
                errorType = ViewModelErrorType.NoInternetConnection(),
                onRetryClicked = {
                    coroutineScope.launch {
                        if (uiState.value.searchQuery.isNotBlank()) {
                            viewModel.searchPhotos(uiState.value.searchQuery)
                        } else {
                            viewModel.getCuratedPhotos()
                        }
                    }
                }
            )
        }

//        if(!uiState.value.noResults) {
//            CuratedPhotosBlock(
//                photos = uiState.value.photos,
//                viewModel = viewModel,
//                onPhotoClicked = {
//                    navController.navigate("details/$it")
//                }
//            )
//        } else {
//            ErrorScreen(
//                message = stringResource(id = R.string.no_results),
//                errorType = ViewModelErrorType.NoResultsFound(),
//                onRetryClicked = {
//                    coroutineScope.launch {
//                        if (uiState.value.searchQuery.isNotBlank()) {
//                            viewModel.searchPhotos(uiState.value.searchQuery)
//                        } else {
//                            viewModel.getCuratedPhotos()
//                        }
//                    }
//                }
//            )
//        }
    }
}