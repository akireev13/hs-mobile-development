package com.example.testapp2.data.unsplash

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val bio: String?,
    val id: String?,
    val instagram_username: String?,
    val links: LinksX?,
    val location: String?,
    val name: String?,
    val portfolio_url: String?,
    val profile_image: ProfileImage?,
    val total_collections: Int?,
    val total_likes: Int?,
    val total_photos: Int?,
    val twitter_username: String?,
    val username: String?
) : Parcelable