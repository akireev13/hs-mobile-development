package com.example.testapp2.features.Gallery

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.testapp2.core.api.UnsplashApiProvider
import com.example.testapp2.data.cb.UnsplashResult
import com.example.testapp2.data.unsplash.SearchResponse
import com.example.testapp2.data.unsplash.UnsplashItem

class GalleryViewModel : ViewModel(), UnsplashResult {

    private val _photos: MutableState<List<UnsplashItem>> = mutableStateOf(emptyList())
    val photos: MutableState<List<UnsplashItem>> = _photos

    private val _refreshing: MutableState<Boolean> = mutableStateOf(false)
    val refreshing: MutableState<Boolean> = _refreshing

    private val provider = UnsplashApiProvider()

    fun fetchImages() {
        _refreshing.value = true
        provider.fetchImages(this)
    }
    fun searchImages(query: String) {
        _refreshing.value = true
        provider.searchImages(this, query)

    }


    // Callback for client

    override fun onSuccessFetchImages(images: List<UnsplashItem>) {
        _photos.value = images
        _refreshing.value = false
    }

    override fun onFailFetchImages() {
        _refreshing.value = false
    }

    override fun onSuccessSearchImages(result: SearchResponse) {
        _photos.value = result.results
        _refreshing.value = false

    }

    override fun onFailSearchImages() {
        _refreshing.value = false
    }

}