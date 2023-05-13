package com.amalitec.moviesapp.domain.remote.usecase

data class RemoteUseCase(
    val getFeaturedMovie: GetFeaturedMovie,
    val getLatestMovies: GetLatestMovies,
    val getTopRated: GetTopRated,
    val getTvShows: GetTvShows
)
