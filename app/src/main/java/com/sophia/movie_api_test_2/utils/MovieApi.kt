package com.sophia.movie_api_test_2.utils

import com.sophia.movie_api_test_2.reponse.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    //영화를 검색
    //https://api.themoviedb.org/3/search/movie?api_key=5c656e653c0e862e829028288f98815e&query=Jack+Reacher
    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Call<MovieSearchResponse>

}