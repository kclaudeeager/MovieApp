package com.amalitec.moviesapp.domain.model

import com.amalitec.moviesapp.data.local.MovieEntity
import com.amalitec.moviesapp.domain.util.MovieType

data class Movie(
    val id:Int,
    val title:String,
    val description:String,
    val popularity:Double,
    val releaseDate:String,
    val posterPath:String,
    val backdropPath:String,
    val type:MovieType
)
fun Movie.toMovieEntity():MovieEntity{
    val movieType:String = when(type){
       is MovieType.Latest -> "latest"
       is MovieType.TvShow -> "tv_show"
       is MovieType.TopRated -> "top_rated"
        is MovieType.Featured -> "featured"
    }
    return MovieEntity(id,title,description,popularity,releaseDate,posterPath,backdropPath,movieType)
}
