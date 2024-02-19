package com.example.zayatspexelsapp.app.ui.presentation.bookmarks

import androidx.lifecycle.ViewModel
import com.example.zayatspexelsapp.app.ui.presentation.ViewModelErrorType
import com.example.zayatspexelsapp.data.models.Photo
import com.example.zayatspexelsapp.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class BookmarksUiState(
    val favouritePhotos: List<Photo> = listOf(),
    val errorType: ViewModelErrorType? = null
)

@HiltViewModel
class BookmarksPageViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookmarksUiState())
    val uiState: StateFlow<BookmarksUiState> = _uiState.asStateFlow()

    suspend fun getFavouritePhotos() {
        val photos = databaseRepository.getPhotosList()

        _uiState.update {
            it.copy(
                favouritePhotos = photos,
                errorType = when(photos.isNotEmpty()) {
                    true -> null
                    false -> ViewModelErrorType.BookmarksNotFound()
                }
            )
        }
    }
}