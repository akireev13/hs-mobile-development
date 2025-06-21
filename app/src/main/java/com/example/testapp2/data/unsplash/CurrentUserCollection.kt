package com.example.testapp2.data.unsplash

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CurrentUserCollection(
    val cover_photo: String?,
    val id: Int?,
    val last_collected_at: String?,
    val published_at: String?,
    val title: String?,
    val updated_at: String?,
    val user: @RawValue Any?
) : Parcelable