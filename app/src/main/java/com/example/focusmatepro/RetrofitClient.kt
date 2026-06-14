package com.example.focusmatepro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =

        "https://api.adviceslip.com/"


    val api:APIService by lazy {

        Retrofit.Builder()

            .baseUrl(BASE_URL)

            .addConverterFactory(

                GsonConverterFactory.create())

            .build()

            .create(APIService::class.java)

    }

}