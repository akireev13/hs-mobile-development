package com.example.testapp2.data.cb

import com.example.testapp2.data.UnsplashItem

interface UnsplashResult {
    fun onSuccess(images: List<UnsplashItem>)

    fun onFail()
}