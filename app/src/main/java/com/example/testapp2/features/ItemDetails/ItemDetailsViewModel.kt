package com.example.itemdetails

import UnsplashDetailsResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp2.core.api.UnsplashApiProvider
import com.example.testapp2.data.unsplash.UnsplashDetailedItem

class ItemDetailsViewModel :
    ViewModel(),
    UnsplashDetailsResult {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val photo: UnsplashDetailedItem) : UiState()
        object Error : UiState()
    }

    private val apiProvider = UnsplashApiProvider()

    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> = _state

    fun loadPhoto(id: String) {
        _state.value = UiState.Loading
        apiProvider.fetchImageDetails(this, id)
    }

    override fun onSuccessFetchImageDetails(image: UnsplashDetailedItem) {
        _state.postValue(UiState.Success(image))
    }

    override fun onFailFetchImageDetails() {
        _state.postValue(UiState.Error)
    }
}
