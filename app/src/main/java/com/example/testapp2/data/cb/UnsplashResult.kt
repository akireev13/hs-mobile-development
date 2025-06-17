package com.example.testapp2.data.cb

import com.example.testapp2.data.SearchResponse
import com.example.testapp2.data.UnsplashItem

interface UnsplashResult {
    fun onSuccessFetchImages(images: List<UnsplashItem>)

    fun onFailFetchImages()

    fun onSuccessSearchImages(result: SearchResponse)

    fun onFailSearchImages()
}