package com.amalitec.moviesapp.domain.util

sealed class MovieType {
    object Latest : MovieType()
    object TopRated :MovieType()
    object Featured : MovieType()
    object TvShow : MovieType()
}