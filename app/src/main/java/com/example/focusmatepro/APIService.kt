package com.example.focusmatepro

import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("advice")

    fun getAdvice(): Call<AdviceResponse>

}