package com.example.testapp2.data.unsplash


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Position(
    val latitude: Double?,
    val longitude: Double?
) : Parcelable