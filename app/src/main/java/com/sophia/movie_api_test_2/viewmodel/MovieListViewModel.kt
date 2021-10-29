package com.sophia.movie_api_test_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sophia.movie_api_test_2.models.MovieModel

class MovieListViewModel: ViewModel() {

    private val mMovie = MutableLiveData<List<MovieModel>>()
    val getMovies: LiveData<List<MovieModel>>
        get() = mMovie

}