package com.amalitec.moviesapp.domain.model

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