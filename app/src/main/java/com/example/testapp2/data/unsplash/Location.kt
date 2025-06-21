package com.example.testapp2.data.unsplash


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Location(
    val city: String?,
    val country: String?,
    val position: Position?
) : Parcelable