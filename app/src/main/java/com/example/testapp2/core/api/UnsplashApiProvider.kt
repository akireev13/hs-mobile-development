package com.example.testapp2.core.api

import UnsplashApi
import UnsplashDetailsResult
import com.example.testapp2.data.cb.UnsplashResult
import com.example.testapp2.data.unsplash.SearchResponse
import com.example.testapp2.data.unsplash.UnsplashDetailedItem
import com.example.testapp2.data.unsplash.UnsplashItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


private const val BASE_URL = "https://api.unsplash.com"

private val retrofit by lazy {

//    val interceptor = HttpLoggingInterceptor()
//
//    val client = OkHttpClient.Builder().addInterceptor(interceptor)

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create<UnsplashApi>()
}

class UnsplashApiProvider() {
    fun fetchImages(cb: UnsplashResult) {
        retrofit.fetchPhotos().enqueue(object: Callback<List<UnsplashItem>> {
            override fun onResponse(
                call: Call<List<UnsplashItem>>,
                response: Response<List<UnsplashItem>>
            ) {
                if (response.isSuccessful) {
                    cb.onSuccessFetchImages(response.body() ?: emptyList())
                } else {
                    cb.onFailFetchImages()
                }
            }

            override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
                cb.onFailFetchImages()
            }
        })
    }

    fun searchImages(cb: UnsplashResult, query: String) {

        val emptyResponse = SearchResponse(0, 0, emptyList())

        retrofit.searchPhotos(query).enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    cb.onSuccessSearchImages(response.body() ?: emptyResponse)
                } else {
                    cb.onFailSearchImages()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                cb.onFailSearchImages()
            }
        })
    }

    fun fetchImageDetails(cb: UnsplashDetailsResult, id: String) {

        retrofit.fetchPhotoDetails(id).enqueue(object: Callback<UnsplashDetailedItem> {
            override fun onResponse(
                call: Call<UnsplashDetailedItem>,
                response: Response<UnsplashDetailedItem>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onSuccessFetchImageDetails(response.body()!!)
                } else {
                    cb.onFailFetchImageDetails()
                }
            }

            override fun onFailure(call: Call<UnsplashDetailedItem>, t: Throwable) {
                cb.onFailFetchImageDetails()
            }
        })
    }
}