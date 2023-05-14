package com.amalitec.moviesapp.presentation

import com.amalitec.moviesapp.domain.model.Movie

data class MoviesState (
    val isLoading : Boolean = false,
    val movies : List<Movie> = emptyList(),
    val error : String =""
        )

