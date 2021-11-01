package com.sophia.movie_api_test_2.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (
    val title: String?,
    val poster_path: String?,
    val release_date: String?,
    val movie_id: Int?,
    val vote_average: Float?,
    val movie_overview: String?,
    val original_language: String?
): Parcelable