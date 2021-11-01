package com.sophia.movie_api_test_2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.request.MovieApiClient

class MovieRepository {

    private val movieApiClient: MovieApiClient = MovieApiClient.getInstance()

    fun getMovies(): LiveData<List<MovieModel>> = movieApiClient.getMovies()

    companion object {
        private val instance: MovieRepository = MovieRepository()

        fun getInstance(): MovieRepository {
            return instance
        }
    }

    fun searchMovieApi(query: String, pageNumber: Int) {
        movieApiClient.searchMoviesApi(query, pageNumber)
    }

}