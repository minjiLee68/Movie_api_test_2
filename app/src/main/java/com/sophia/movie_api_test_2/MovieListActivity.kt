package com.sophia.movie_api_test_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sophia.movie_api_test_2.adapter.MovieAdapter
import com.sophia.movie_api_test_2.adapter.OnMovieListener
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

class MovieListActivity : AppCompatActivity(), OnMovieListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private var movies: List<MovieModel> = ArrayList()

    //ViewModel
    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toolbar
        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        setSupportActionBar(binding.toolbar)

        //searchView
        setupSearchView()
        configureRecyclerView()
        observeAnyChange()
    }
    // Observing any data change = 모든 데이터 변경 관찰
    private fun observeAnyChange() {
        movieListViewModel.getMovies().observe(this, {
            if (it != null) {
                for (movieModel: MovieModel in it) {
                    Log.d("Tag","onChanged: ${movieModel.title}")
                    movieAdapter.setMovies(it)
                }
            }
        })
    }
//    private fun searchMovieApi(query: String, pageNumber: Int) {
//        movieListViewModel.searchMovieApi(query, pageNumber)
//    }

    private fun configureRecyclerView() {
        movieAdapter = MovieAdapter(movies,this)
        binding.recyclerView.adapter = movieAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    //여기에서 api의 다음 페이지에 다음 검색 결과를 표시
                    movieListViewModel.searchNextPage()
                }
            }
        })
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

    override fun onMovieClick(position: Int) {
        //Toast.makeText(this, "The Position $position",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MovieDetails::class.java)
        intent.putExtra("movie",movieAdapter.getSelectedMovie(position))
        startActivity(intent)
    }

    override fun onCategoryClick(category: String) {

    }

    //Get data from searchview & query the api to get the results
    private fun setupSearchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    movieListViewModel.searchMovieApi(query,1)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}

