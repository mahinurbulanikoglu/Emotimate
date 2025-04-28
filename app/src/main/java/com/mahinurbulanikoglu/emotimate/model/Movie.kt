package com.mahinurbulanikoglu.emotimate.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mahinurbulanikoglu.emotimate.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double
) : Parcelable
data class MovieResponse(
    val results: List<Movie>
)