package com.sophia.movie_api_test_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sophia.movie_api_test_2.databinding.ActivityMainBinding
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.reponse.MovieSearchResponse
import com.sophia.movie_api_test_2.request.Service
import com.sophia.movie_api_test_2.utils.Credentials
import com.sophia.movie_api_test_2.utils.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            getRetrofitResponse()
        }
    }

    private fun getRetrofitResponse() {
        val movieApi: MovieApi = Service.getMovieApi()

        val reponseCall: Call<MovieSearchResponse> = movieApi
            .searchMovie(
                Credentials.API_KEY,
                "Jack Reacher",
                "1"
            )

        reponseCall.enqueue(object : Callback<MovieSearchResponse> {

            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if ( response.isSuccessful ) {
                    Log.d("TAG--","the response:${response.body().toString()}")

                    val movies: List<MovieModel> = ArrayList(response.body()!!.movies)

                    for (movie: MovieModel in movies) {
                        Log.d("TAG--","The release date:${movie.release_date}")
                    }
                } else {
                    try {
                        Log.d("TAG--","Error:${response.errorBody()?.string()}")
                    } catch (e: IOException) { e.printStackTrace() }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {

            }

        })
    }
}