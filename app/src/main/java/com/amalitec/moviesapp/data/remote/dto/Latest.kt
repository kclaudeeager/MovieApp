package com.amalitec.moviesapp.data.remote.dto

import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.util.MovieType

data class Latest(
    val adult: Boolean,
    val backdrop_path: Any,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Any>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: Any? = "",
    val production_companies: List<Any>,
    val production_countries: List<Any>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
fun Latest.toMovie(): Movie {
    val movieType: MovieType = MovieType.Latest
    return Movie(
        id = id,
        title = title,
        description = overview,
        popularity = popularity,
        posterPath = poster_path as String,
        backdropPath = backdrop_path.toString(),
        type = movieType,
        releaseDate = release_date
    )
}