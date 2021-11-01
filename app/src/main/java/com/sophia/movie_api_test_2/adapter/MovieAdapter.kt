package com.sophia.movie_api_test_2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sophia.movie_api_test_2.databinding.MovieListItemBinding
import com.sophia.movie_api_test_2.models.MovieModel

const val DISPLAY_POP = 1
const val DISPLAY_SEARCH = 2

class MovieAdapter(
    private var mMovies: List<MovieModel>,
    private val onMovieListener: OnMovieListener
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            onMovieListener
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = mMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(mMovies: List<MovieModel>) {
        this.mMovies = mMovies
        notifyDataSetChanged()
    }

    //클릭한 영화의 id 얻기
    fun getSelectedMovie(position: Int): MovieModel? {
        if (mMovies != null) {
            if (mMovies.isNotEmpty()) {
                return mMovies[position]
            }
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
            ratingBar.rating = movies.vote_average!! /2
            //ImageView: Glide Library
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500/${movies.poster_path}").into(movieImg)

            root.setOnClickListener {
                onMovieListener.onMovieClick(bindingAdapterPosition)
            }
        }
    }
}