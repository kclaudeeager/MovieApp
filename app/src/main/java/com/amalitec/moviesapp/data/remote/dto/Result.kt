package com.amalitec.moviesapp.data.remote.dto

import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.util.MovieType

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
fun Result.toMovie(type:String): Movie {
   val movieType:MovieType = when(type){
        "featured" -> MovieType.Featured
         else -> MovieType.TopRated
   }
    return Movie(
        id=id,
        title=title,
        description = overview,
        popularity=popularity,
        posterPath = poster_path,
        backdropPath = backdrop_path,
        type = movieType,
        releaseDate = release_date
    )

}