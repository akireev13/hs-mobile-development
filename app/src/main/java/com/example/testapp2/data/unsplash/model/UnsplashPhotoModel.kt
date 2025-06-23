package com.example.testapp2.data.unsplash.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
class UnsplashPhotoModel(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "authorName")
    val authorName: String
)