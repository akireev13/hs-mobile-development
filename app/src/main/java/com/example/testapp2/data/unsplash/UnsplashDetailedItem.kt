package com.example.testapp2.data.unsplash


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UnsplashDetailedItem(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: List<CurrentUserCollection>?,
    val description: String?,
    val downloads: Int?,
    val exif: Exif?,
    val height: Int?,
    val id: String?,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val links: Links?,
    val location: Location?,
    val public_domain: Boolean?,
    val tags: List<Tag>?,
    val updated_at: String?,
    val urls: Urls?,
    val user: User?,
    val width: Int?
) : Parcelable