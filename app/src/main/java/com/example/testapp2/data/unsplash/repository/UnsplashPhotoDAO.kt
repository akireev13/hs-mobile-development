package com.example.testapp2.data.unsplash.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.data.unsplash.model.UnsplashPhotoModel

@Dao
interface UnsplashPhotoDAO {

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<UnsplashPhotoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dog: UnsplashPhotoModel)

    @Query("DELETE FROM photos")
    fun deleteAll()
}