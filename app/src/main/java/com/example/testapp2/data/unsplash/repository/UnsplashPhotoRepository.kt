package com.example.testapp2.data.unsplash.repository

import androidx.lifecycle.LiveData
import com.example.testapp2.data.unsplash.model.UnsplashPhotoModel

class UnsplashPhotoRepository(private val unsplashPhotoDao: UnsplashPhotoDAO)  {
    val allPhotos: LiveData<List<UnsplashPhotoModel>> = unsplashPhotoDao.getAllPhotos()
    fun insert(photos: List<UnsplashPhotoModel>) {
        AppDatabase.databaseWriteExecutor.execute {
            unsplashPhotoDao.insert(photos)
        }
    }
}