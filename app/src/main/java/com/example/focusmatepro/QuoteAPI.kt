
package com.example.focusmatepro

import retrofit2.Call
import retrofit2.http.GET

interface QuoteApi {

    @GET("random")

    fun getQuote():Call<Quote>

}