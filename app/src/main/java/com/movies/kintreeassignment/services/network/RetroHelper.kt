package com.movies.kintreeassignment.services.network

import com.movies.kintreeassignment.services.interfaces.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroHelper {

    private var retrofit: Retrofit? = null;
    private const val BASE_URL: String = "http://www.omdbapi.com/";
    val apiInterface: ApiInterface = getRetroInstance().create(ApiInterface::class.java)

    private fun getRetroInstance(): Retrofit = synchronized(this) {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        return retrofit!!
    }

    val api:ApiInterface by lazy {
        getRetroInstance().create(ApiInterface::class.java)
    }
}