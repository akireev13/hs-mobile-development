package com.example.testapp2.core.util

import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.data.unsplash.model.UnsplashPhotoModel

fun toUnsplashPhotoModel(unsplashPhoto: UnsplashItem): UnsplashPhotoModel {
    val imageId: Int = unsplashPhoto.id?.toIntOrNull() ?: 0 // Default to 0 if null or not a valid Int

    val result: UnsplashPhotoModel = UnsplashPhotoModel(
        imageUrl = unsplashPhoto.urls?.regular ?: "",
        description = unsplashPhoto.description ?: "No description",
        authorName = unsplashPhoto.user?.name ?: "Unknown",
        id = imageId
    )

    return result
}