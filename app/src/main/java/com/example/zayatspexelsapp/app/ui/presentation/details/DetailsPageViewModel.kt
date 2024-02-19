package com.example.zayatspexelsapp.app.ui.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zayatspexelsapp.app.ui.presentation.ViewModelErrorType
import com.example.zayatspexelsapp.data.models.Photo
import com.example.zayatspexelsapp.data.repositories.DataRepository
import com.example.zayatspexelsapp.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val photo: Photo? = null,
    val isNotFound: Boolean = false,
    val isPhotoFavourite: Boolean = false,
    val isLoading: Boolean = true,
    val errorType: ViewModelErrorType? = null
)

@HiltViewModel
class DetailsPageViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun changeLoadingStatus(status: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = status
            )
        }
    }

    fun getPhotoFromApi(id: Int) {
        viewModelScope.launch() {
            val photo = dataRepository.getPhotoById(id)
            countPhotosById(id)

            _uiState.update {
                it.copy(
                    photo = photo,
                    errorType = when(photo != null) {
                        true -> null
                        false -> ViewModelErrorType.ImageNotFound()
                    }
                )
            }
        }
    }

    fun getPhotoFromBookmarksScreen(id: Int?) {
        if (id != null) {
            viewModelScope.launch {
                val photo = databaseRepository.getPhotoById(id)
                countPhotosById(id)

                _uiState.update {
                    it.copy(
                        photo = photo,
                        errorType = when(photo != null) {
                            true -> null
                            false -> ViewModelErrorType.ImageNotFound()
                        }
                    )
                }
            }
        }
    }

    suspend fun countPhotosById(id: Int) {
        viewModelScope.launch {
            val count = databaseRepository.countPhotosById(id)

            _uiState.update {
                it.copy(
                    isPhotoFavourite = count != 0
                )
            }
        }
    }

    fun onFavouriteButtonClicked(photo: Photo) {
        viewModelScope.launch {
            println(photo)
            if (uiState.value.isPhotoFavourite) {
                removeFromFavourites(photo)
                _uiState.update {
                    it.copy(
                        isPhotoFavourite = false
                    )
                }
            } else {
                addToFavourites(photo)
                _uiState.update {
                    it.copy(
                        isPhotoFavourite = true
                    )
                }
            }
        }
    }

    suspend fun addToFavourites(photo: Photo) {
        viewModelScope.launch {
            databaseRepository.addFavouritePhoto(photo)
        }

    }

    suspend fun removeFromFavourites(photo: Photo) {
        viewModelScope.launch {
            databaseRepository.removeFavouritePhoto(photo)
        }
    }
}