package com.amalitec.moviesapp.domain.usecase

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.amalitec.moviesapp.common.Resource
import com.amalitec.moviesapp.domain.local.usecase.MoviesState
import com.amalitec.moviesapp.domain.local.usecase.SaveMovie
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.model.toMovieEntity
import com.amalitec.moviesapp.domain.remote.usecase.RemoteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteAndSaveLocally @Inject constructor(
    private val remoteUseCase: RemoteUseCase,
    private val saveMovie: SaveMovie
) {
    private val _state = mutableStateOf(MoviesState())
    val state: State<MoviesState> = _state

    private suspend fun getMovies(getter: () -> Flow<Resource<List<Movie>>>) {
        getter().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MoviesState(movies = result.data?: emptyList())
                    state.value.movies.forEach { movie ->
                        saveMovie(movie.toMovieEntity())
                    }
                }
                is Resource.Error -> {
                    _state.value = MoviesState(error = result.message ?: "Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = MoviesState(isLoading = true)
                }
            }
        }
    }

    suspend operator fun invoke() {
        getMovies { remoteUseCase.getLatestMovies() }
        getMovies { remoteUseCase.getFeaturedMovie() }
        getMovies { remoteUseCase.getTopRated() }
        getMovies { remoteUseCase.getTvShows() }
    }
}
