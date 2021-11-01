package com.sophia.movie_api_test_2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.request.MovieApiClient

class MovieRepository {

    private val movieApiClient: MovieApiClient = MovieApiClient.getInstance()

    private lateinit var mQuery: String
    private var mPageNumber: Int = 0

    fun getMovies(): LiveData<List<MovieModel>> = movieApiClient.getMovies()
    fun getMoviesPop(): LiveData<List<MovieModel>> = movieApiClient.getMoviesPop()

    companion object {
        private val instance: MovieRepository = MovieRepository()

        fun getInstance(): MovieRepository {
            return instance
        }
    }

    fun searchMovieApi(query: String, pageNumber: Int) {
        mQuery = query
        mPageNumber = pageNumber
        movieApiClient.searchMoviesApi(query, pageNumber)
    }

    fun searchMoviePop(pageNumber: Int) {
        mPageNumber = pageNumber
        movieApiClient.searchMoviesApiPop(pageNumber)
    }

    fun searchNextPage() {
        searchMovieApi(mQuery,mPageNumber+1)
    }

}