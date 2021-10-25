package com.sophia.movie_api_test_2.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sophia.movie_api_test_2.models.MovieModel

//이 클래스는 단일 영화 요청을 위한 것입니다.
data class MovieResponse(
    // 1- 영화 개체 찾기
    @SerializedName("results")
    @Expose
    val movie: MovieModel,

) {
    override fun toString(): String {
        return "MovieResponse{movie = $movie}"
    }
}