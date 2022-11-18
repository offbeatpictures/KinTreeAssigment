package com.movies.kintreeassignment.services.interfaces

import com.movies.kintreeassignment.services.model.MoviesModel
import com.movies.kintreeassignment.services.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "306a33ca"

interface ApiInterface {

    @GET("?apikey=${API_KEY}")
    suspend fun getMovies(@Query("s") search: String): Response<SearchResult>;

    @GET("?apikey=${API_KEY}")
    suspend fun getMoviesInfo(@Query("i") imdbID: String): Response<MoviesModel>;
}