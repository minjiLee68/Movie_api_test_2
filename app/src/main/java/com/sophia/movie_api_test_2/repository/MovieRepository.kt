package com.sophia.movie_api_test_2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.request.MovieApiClient

class MovieRepository {

    private val movieApiClient: MovieApiClient = MovieApiClient.getInstance()

    fun getMovies(): LiveData<List<MovieModel>> = movieApiClient.getMovies()

    companion object {
        private lateinit var instance: MovieRepository

        @JvmName("getInstance1")
        fun getInstance(): MovieRepository {
            if (instance == null ){
                instance = MovieRepository()
            }
            return instance
        }
    }

}