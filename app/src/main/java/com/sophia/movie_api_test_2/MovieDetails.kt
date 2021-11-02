package com.sophia.movie_api_test_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sophia.movie_api_test_2.databinding.ActivityMovieDetailsBinding
import com.sophia.movie_api_test_2.models.MovieModel

class MovieDetails : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        if (intent.hasExtra("movie")) {
            val movieModel = intent.getParcelableExtra<MovieModel>("movie")
            binding.run {
                textViewTitleDetails.text = movieModel?.title
                textviewDescDetails.text = movieModel?.movie_overview
                ratingBarDetails.rating = movieModel?.vote_average!! /2

                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w500/${movieModel.poster_path}")
                    .into(imageViewDetails)
            }
        }
    }
}