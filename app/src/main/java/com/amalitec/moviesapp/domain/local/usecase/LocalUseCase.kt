package com.amalitec.moviesapp.domain.local.usecase

data class LocalUseCase (
    val getMovieById: GetMovieById,
    val getMovieByType: GetMovieByType,
    val getMovies: GetMovies,
    val deleteMovie: DeleteMovie,
        )