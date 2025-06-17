package com.example.testapp2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileImage(
    val large: String?,
    val medium: String?,
    val small: String?
) : Parcelable