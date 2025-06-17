package com.example.testapp2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentUserCollection(
    val cover_photo: String?,
    val id: Int?,
    val last_collected_at: String?,
    val published_at: String?,
    val title: String?,
    val updated_at: String?,
//    val user: Any?
) : Parcelable