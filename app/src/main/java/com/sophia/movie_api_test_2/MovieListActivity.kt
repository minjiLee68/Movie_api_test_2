package com.sophia.movie_api_test_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.sophia.movie_api_test_2.databinding.ActivityMainBinding
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.reponse.MovieSearchResponse
import com.sophia.movie_api_test_2.request.Service
import com.sophia.movie_api_test_2.utils.Credentials
import com.sophia.movie_api_test_2.utils.MovieApi
import com.sophia.movie_api_test_2.viewmodel.MovieListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //ViewModel
    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

        observeAnyChange()

        binding.btn.setOnClickListener {
            searchMovieApi("fast", 1)
        }
    }
    // Observing any data change = 모든 데이터 변경 관찰
    private fun observeAnyChange() {
        movieListViewModel.getMovies().observe(this, {

            if (it != null) {
                for (movieModel: MovieModel in it) {
                    //Get the data in log
                    Log.v("Tag","onChanged: ${movieModel.title}")
                }
            }

        })
    }
    private fun searchMovieApi(query: String, pageNumber: Int) {
        movieListViewModel.searchMovieApi(query, pageNumber)
    }

    private fun getRetrofitResponse() {
        val movieApi: MovieApi = Service.getMovieApi()

        val reponseCall: Call<MovieSearchResponse> = movieApi
            .searchMovie(
                Credentials.API_KEY,
                "Action",
                1
            )

        reponseCall.enqueue(object : Callback<MovieSearchResponse> {

            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.isSuccessful) {
//                    Log.d("TAG--","the response:${response.body().toString()}")

                    val movies: List<MovieModel> = ArrayList(response.body()!!.movies)

                    for (movie: MovieModel in movies) {
                        Log.d("TAG--", "Name: ${movie.title}")
                    }
                } else {
                    try {
                        Log.d("TAG--", "Error:${response.errorBody()?.string()}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {

            }

        })
    }

    private fun getRetrofitResponseAccordingToID() {
        val movieApi: MovieApi = Service.getMovieApi()
        val responseCall: Call<MovieModel> = movieApi
            .getMovie(
                438631,
                Credentials.API_KEY
            )

        responseCall.enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    val movie: MovieModel = response.body()!!
                    Log.d("Tag", "The Response: ${movie.title}")
                } else {
                    try {
                        Log.d("Tag", "Error: ${response.errorBody()?.string()}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {

            }

        })
    }
}

