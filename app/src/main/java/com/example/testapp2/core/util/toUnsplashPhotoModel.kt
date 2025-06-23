package com.example.testapp2.core.util

import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.data.unsplash.model.UnsplashPhotoModel

fun toUnsplashPhotoModel(unsplashPhotos: List<UnsplashItem>): List<UnsplashPhotoModel> {

    val transformed = mutableListOf<UnsplashPhotoModel>()
    unsplashPhotos.forEach { unsplashPhoto ->

    val result = UnsplashPhotoModel(
        imageUrl = unsplashPhoto.urls?.regular ?: "",
        description = unsplashPhoto.description ?: "No description",
        authorName = unsplashPhoto.user?.name ?: "Unknown",
        id = unsplashPhoto.id ?: "Something"
    )

    transformed.add(result)
    }

    return transformed
}