package com.movies.kintreeassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.kintreeassignment.services.model.MoviesModel
import com.movies.kintreeassignment.services.model.SearchResult
import com.movies.kintreeassignment.services.repo.MoviesRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(private val moviesRepo: MoviesRepo = MoviesRepo()) : ViewModel() {

    val moviesList = MutableLiveData<Response<SearchResult>>()
    val moviesModel=MutableLiveData<Response<MoviesModel>>()


     fun getMoviesList(search: String) {
        viewModelScope.launch(IO) {
            val response = moviesRepo.getMoviesResult(search)
            moviesList.postValue(response)
        }

    }


    fun getMovieInfo(imdbID: String) {
        viewModelScope.launch(IO) {
            val response = moviesRepo.getMovieInfo(imdbID)
            moviesModel.postValue(response)
        }
    }


}