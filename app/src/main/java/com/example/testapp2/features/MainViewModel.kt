package com.example.testapp2.features

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.testapp2.core.util.toUnsplashPhotoModel
import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.data.unsplash.model.UnsplashPhotoModel
import com.example.testapp2.data.unsplash.repository.AppDatabase
import com.example.testapp2.data.unsplash.repository.UnsplashPhotoRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val database = AppDatabase.getDatabase(application)

    val repository = UnsplashPhotoRepository(database.unsplashPhotoDao())

    fun getUnsplashPhotosFromDatabase(): LiveData<List<UnsplashPhotoModel>> = repository.allPhotos

    fun addUnsplashPhoto(UnsplashPhoto : UnsplashItem) {
        repository.insert(toUnsplashPhotoModel(UnsplashPhoto))
    }
}