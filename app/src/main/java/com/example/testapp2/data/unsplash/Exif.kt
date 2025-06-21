package com.example.testapp2.data.unsplash


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Exif(
    val aperture: String?,
    val exposure_time: String?,
    val focal_length: String?,
    val iso: Int?,
    val make: String?,
    val model: String?,
    val name: String?
) : Parcelable