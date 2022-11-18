package com.movies.kintreeassignment.services.model


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: ArrayList<MoviesModel>,
    @SerializedName("totalResults")
    val totalResults: String
)