package com.movies.kintreeassignment.services.repo

import com.movies.kintreeassignment.services.model.MoviesModel
import com.movies.kintreeassignment.services.model.SearchResult
import com.movies.kintreeassignment.services.network.RetroHelper
import com.movies.kintreeassignment.services.network.RetroHelper.apiInterface
import retrofit2.Response

class MoviesRepo {

    suspend fun getMoviesResult(search :
    String): Response<SearchResult> {
        return RetroHelper.api.getMovies(search)
    }

    suspend fun getMovieInfo(imdbID : String): Response<MoviesModel> {
        return RetroHelper.api.getMoviesInfo(imdbID)
    }

}