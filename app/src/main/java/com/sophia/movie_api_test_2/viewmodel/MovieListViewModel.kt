package com.sophia.movie_api_test_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.repository.MovieRepository

class MovieListViewModel: ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository.getInstance()

    fun getMovies(): LiveData<List<MovieModel>> = movieRepository.getMovies()

    fun searchMovieApi(query: String, pageNumber: Int) {
        movieRepository.searchMovieApi(query, pageNumber)
    }

}