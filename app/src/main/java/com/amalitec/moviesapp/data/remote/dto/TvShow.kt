package com.amalitec.moviesapp.data.remote.dto

import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.util.MovieType

data class TvShow(
    val adult: Boolean,
    val backdrop_path: Any = "",
    val created_by: List<Any>,
    val episode_run_time: List<Any>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    val next_episode_to_air: Any,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: Any = "",
    val production_companies: List<Any>,
    val production_countries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)
fun TvShow.toMovie(): Movie {
    val movieType: MovieType = MovieType.TvShow
    return Movie(
        id=id,
        title=name,
        description = overview,
        popularity=popularity,
        posterPath = poster_path.toString(),
        backdropPath = backdrop_path.toString(),
        type = movieType,
        releaseDate = last_air_date
    )

}