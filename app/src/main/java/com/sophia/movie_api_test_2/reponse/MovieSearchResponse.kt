package com.sophia.movie_api_test_2.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sophia.movie_api_test_2.models.MovieModel

// 이 클래스는 여러 영화(영화 목록) - 인기 영화를 가져오기 위한 것입니다.
data class MovieSearchResponse(
    @SerializedName("total_results") var total_count: Int,
    @SerializedName("results") var movies: List<MovieModel>
)