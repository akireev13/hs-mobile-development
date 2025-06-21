package com.example.testapp2.data.cb

import com.example.testapp2.data.unsplash.SearchResponse
import com.example.testapp2.data.unsplash.UnsplashDetailedItem
import com.example.testapp2.data.unsplash.UnsplashItem

interface UnsplashResult {
    fun onSuccessFetchImages(images: List<UnsplashItem>)

    fun onFailFetchImages()

    fun onSuccessSearchImages(result: SearchResponse)

    fun onFailSearchImages()

}
