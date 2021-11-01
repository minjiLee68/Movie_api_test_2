package com.sophia.movie_api_test_2.adapter

interface OnMovieListener {
    fun onMovieClick(position: Int)
    fun onCategoryClick(category: String)
}