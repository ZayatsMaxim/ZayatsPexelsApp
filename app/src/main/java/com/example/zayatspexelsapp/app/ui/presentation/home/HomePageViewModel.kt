package com.example.zayatspexelsapp.app.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zayatspexelsapp.app.ui.presentation.ViewModelErrorType
import com.example.zayatspexelsapp.common.Constants.COLLECTIONS_PER_PAGE
import com.example.zayatspexelsapp.common.Constants.PHOTOS_PER_PAGE
import com.example.zayatspexelsapp.data.models.FeaturedCollection
import com.example.zayatspexelsapp.data.models.Photo
import com.example.zayatspexelsapp.data.repositories.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val photos: List<Photo> = listOf(),
    val featuredCollections: List<FeaturedCollection>? = null,
    val searchQuery: String = "",
    val selectedFeaturedCollectionId: String = "",
    val isLoading: Boolean = true,
    val noResults: Boolean = false,
    val noCollections: Boolean = false,
    val noInternetConnection: Boolean = false,
    val errorType: ViewModelErrorType? = null
)

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCuratedPhotos()
            getFeaturedCollections()
        }
    }

    suspend fun getCuratedPhotos() {
            val curatedPhotos = dataRepository.getCuratedPhotos(
            per_page = PHOTOS_PER_PAGE,
            page = 1
        )

        if(curatedPhotos.isEmpty()) {
            _uiState.update {
                it.copy(
                    errorType = ViewModelErrorType.NoInternetConnection()
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    photos = curatedPhotos,
                    noResults = curatedPhotos.isEmpty(),
                    errorType = null
                )
            }
        }
    }

    suspend fun getFeaturedCollections() {
        val featuredCollections = dataRepository.getFeaturedCollectionsList(
            per_page = COLLECTIONS_PER_PAGE,
            page = 1
        )

        _uiState.update {
            it.copy(
                featuredCollections = featuredCollections,
                noCollections = featuredCollections.isEmpty()
            )
        }
    }

    fun changeSearchQuery(searchQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = searchQuery,
                selectedFeaturedCollectionId = ""
            )
        }
    }

    fun changeSelectedId(id: String) {
        _uiState.update {
            it.copy(
                selectedFeaturedCollectionId = id
            )
        }
    }

    fun searchPhotos(searchQuery: String) {
        viewModelScope.launch {
            val searchedPhotos = dataRepository.getPhotosListBySearch(
                query = searchQuery,
                per_page = PHOTOS_PER_PAGE,
                page = 1
            )

            if(searchedPhotos.isEmpty()) {
                _uiState.update {
                    it.copy(
                        errorType = ViewModelErrorType.NoResultsFound()
                    )
                }
            } else {
                _uiState.update{
                    it.copy(
                        photos = searchedPhotos,
                        noResults = searchedPhotos.isEmpty(),
                        errorType = null
                    )
                }
            }
        }
    }

    fun changeLoadingStatus(status: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = status
            )
        }
    }

    fun findFeaturedCollectionIdByTitle(title: String): String? {
        return uiState.value.featuredCollections?.find {
            it.title.lowercase() == title.lowercase()
        }?.id
    }
}