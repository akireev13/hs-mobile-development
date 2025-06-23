package com.example.testapp2.features.Gallery

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp2.core.api.UnsplashApiProvider
import com.example.testapp2.core.util.toUnsplashPhotoModel
import com.example.testapp2.data.cb.UnsplashResult
import com.example.testapp2.data.unsplash.SearchResponse
import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.data.unsplash.model.UnsplashPhotoModel
import com.example.testapp2.data.unsplash.repository.AppDatabase
import com.example.testapp2.data.unsplash.repository.UnsplashPhotoRepository

class GalleryViewModel(application: Application) : UnsplashResult, AndroidViewModel(application) {

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

    // DB

    private val database = AppDatabase.getDatabase(application)

    private val repository = UnsplashPhotoRepository(database.unsplashPhotoDao())

    fun getUnsplashPhotosFromDatabase(): LiveData<List<UnsplashPhotoModel>> = repository.allPhotos

    private fun addUnsplashPhotos(unsplashPhotos : List<UnsplashItem>) {
        repository.insert(toUnsplashPhotoModel(unsplashPhotos))
    }


    // Callback for client

    override fun onSuccessFetchImages(images: List<UnsplashItem>) {
        _photos.value = images
        addUnsplashPhotos(images)
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