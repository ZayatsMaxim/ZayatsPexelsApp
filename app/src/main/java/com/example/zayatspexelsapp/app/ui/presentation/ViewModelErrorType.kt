package com.example.zayatspexelsapp.app.ui.presentation

import com.example.zayatspexelsapp.R

sealed class ViewModelErrorType(
    val textResourceId: Int? = null,
    val buttonTextResourceId: Int
) {
    class NoInternetConnection : ViewModelErrorType(
        buttonTextResourceId = R.string.no_connection
    )
    class NoResultsFound : ViewModelErrorType(
        textResourceId = R.string.no_results,
        buttonTextResourceId = R.string.explore
    )
    class ImageNotFound : ViewModelErrorType(
        textResourceId = R.string.image_not_found,
        buttonTextResourceId = R.string.explore
    )
    class BookmarksNotFound : ViewModelErrorType(
        textResourceId = R.string.bookmarks_not_found,
        buttonTextResourceId = R.string.explore
    )
}