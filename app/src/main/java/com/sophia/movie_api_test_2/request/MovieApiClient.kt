package com.sophia.movie_api_test_2.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sophia.movie_api_test_2.models.MovieModel

class MovieApiClient {
    private val mMovie = MutableLiveData<List<MovieModel>>()

    fun getMovies(): LiveData<List<MovieModel>> = mMovie

    companion object {
        private lateinit var instance: MovieApiClient

        fun getInstance(): MovieApiClient {
            if (instance == null) {
                instance = MovieApiClient()
            }
            return instance
        }
    }
}