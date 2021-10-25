package com.sophia.movie_api_test_2.request

import com.sophia.movie_api_test_2.utils.Credentials
import com.sophia.movie_api_test_2.utils.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {
    private val retrofitBuilder: Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private val retrifit = retrofitBuilder.build()

    private val movieApi = retrifit.create(MovieApi::class.java)

    fun getMovieApi(): MovieApi {
        return movieApi
    }
 }