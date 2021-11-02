package com.sophia.movie_api_test_2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.sophia.movie_api_test_2.R
import com.sophia.movie_api_test_2.databinding.MovieListItemBinding
import com.sophia.movie_api_test_2.models.MovieModel
import com.sophia.movie_api_test_2.utils.Credentials

const val DISPLAY_POP = 1
const val DISPLAY_SEARCH = 2

class MovieAdapter(
    private var mMovies: List<MovieModel>,
    private val onMovieListener: OnMovieListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DISPLAY_SEARCH) {
            return MovieViewHolder(
                MovieListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onMovieListener
            )
        } else {
            return popularViewHolder(
                MovieListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMovieListener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movies = mMovies[position]
        when(getItemViewType(position)) {
            DISPLAY_SEARCH -> {
                (holder as MovieViewHolder).bind(movies)
            }
            DISPLAY_POP -> {
                (holder as popularViewHolder).bind(movies)
            }
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (Credentials.POPULAR) {
            DISPLAY_POP
        } else {
            DISPLAY_SEARCH
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(mMovies: List<MovieModel>) {
        this.mMovies = mMovies
        notifyDataSetChanged()
    }

    //클릭한 영화의 id 얻기
    fun getSelectedMovie(position: Int): MovieModel? {
        if (mMovies.isNotEmpty()) {
            return mMovies[position]
        }
        return null
    }

}

class MovieViewHolder(
    private val binding: MovieListItemBinding,
    private val onMovieListener: OnMovieListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movies: MovieModel) {
        binding.apply {
            //투표 평균이 10 이상이고 ratingBar가 별 5 개 이상: 2로 나누기
            ratingBar.rating = movies.vote_average!! / 2
            //ImageView: Glide Library
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500/${movies.poster_path}")
                .into(movieImg)

            root.setOnClickListener {
                onMovieListener.onMovieClick(bindingAdapterPosition)
            }
        }
    }
}

class popularViewHolder(
    private val binding: MovieListItemBinding,
    private val onMovieListener: OnMovieListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movies: MovieModel) {
        binding.apply {
            //투표 평균이 10 이상이고 ratingBar가 별 5 개 이상: 2로 나누기
            ratingBar.rating = movies.vote_average!! / 2
            //ImageView: Glide Library
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500/${movies.poster_path}")
                .into(movieImg)

            root.setOnClickListener {
                onMovieListener.onMovieClick(bindingAdapterPosition)
            }
        }
    }

}