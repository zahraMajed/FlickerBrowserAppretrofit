package com.example.flickerbrowserappretrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIinterface {
    @GET("services/rest/?method=flickr.photos.search&per_page=10&api_key=b73090cc5f2a93a0ee864469e1b2654c&format=json&nojsoncallback=1/")
    fun getDate(@Query("text")text:String): Call<photoData>?
}