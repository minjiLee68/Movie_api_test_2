package com.sophia.movie_api_test_2.request

import android.annotation.SuppressLint
import android.util.Log
import com.sophia.movie_api_test_2.utils.Credentials
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sophia.movie_api_test_2.AppExecutors
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.reponse.MovieSearchResponse
import retrofit2.Call
import java.io.IOException
import java.util.concurrent.TimeUnit

class MovieApiClient {
    private val mMovies = MutableLiveData<List<MovieModel>>()

    fun getMovies(): LiveData<List<MovieModel>> = mMovies

    private var moviesRunnable: RetrieveMoviesRunnable? = null

    companion object {
        private val instance: MovieApiClient = MovieApiClient()

        fun getInstance(): MovieApiClient {
            return instance
        }
    }

    fun searchMoviesApi(query: String, pageNumber: Int) {

        if (moviesRunnable != null) {
            moviesRunnable = null
        }

        moviesRunnable = RetrieveMoviesRunnable(query, pageNumber)

        val myHandler = AppExecutors.getInstance().networkIO().submit(moviesRunnable)

        AppExecutors.getInstance().networkIO().schedule({

            myHandler.cancel(true)

        }, 5000, TimeUnit.MILLISECONDS)
    }

    inner class RetrieveMoviesRunnable(
        private var query: String,
        private var pageNumber: Int,
    ) : Runnable {

        private var cancelRequest: Boolean = false

        fun retrieveMoviesRunnable(query: String, pageNumber: Int) {
            this.query = query
            this.pageNumber = pageNumber
            cancelRequest
        }

        @SuppressLint("NullSafeMutableLiveData")
        override fun run() {
            try {
                val response = getMovies(query, pageNumber).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {
                    val list: List<MovieModel> = ArrayList()
                    if (pageNumber == 1) {
                        // Live data로 데이터 보내기
                        // PostValue: 백그라운드 스레드에 사용
                        // setValue: 배경 스레드가 아님
                        mMovies.postValue(list)
                    } else {
                        val currentMovies: ArrayList<MovieModel> = mMovies.value as ArrayList<MovieModel>
                        currentMovies.addAll(list)
                        mMovies.postValue(currentMovies)
                    }
                } else {
                    val error = response.errorBody()?.string()
                    Log.v("Tag","Error $error")
                    mMovies.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        //search Method/ query
        fun getMovies(query: String, pageNumber: Int): Call<MovieSearchResponse> {
            return Service.getMovieApi().searchMovie(
                Credentials.API_KEY,
                query,
                pageNumber
            )
        }

        private fun cancelRequest() {
            Log.v("Tag", "Cancelling Search Request")
            cancelRequest = true
        }
    }
}

