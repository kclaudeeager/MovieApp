package com.amalitec.moviesapp.presentation

import com.amalitec.moviesapp.domain.model.Movie

data class MovieDetailState(
    val isLoading : Boolean = false,
    val movie : Movie? = null,
    val error : String =""
)