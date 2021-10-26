package com.sophia.movie_api_test_2.request

import com.sophia.movie_api_test_2.utils.Credentials
import com.sophia.movie_api_test_2.utils.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {

    private val movieApi: MovieApi

    init {
         val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

         val retrofit = retrofitBuilder.build()

            movieApi = retrofit.create(MovieApi::class.java)
    }

    fun getMovieApi(): MovieApi {
        return movieApi
    }
}