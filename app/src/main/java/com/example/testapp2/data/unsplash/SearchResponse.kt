package com.example.testapp2.data.unsplash

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<UnsplashItem>
) : Parcelable