package com.example.testapp2.data

import UnsplashApi
import com.example.testapp2.data.UnsplashItem
import com.example.testapp2.data.cb.UnsplashResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Headers


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
}