package com.amalitec.moviesapp.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitec.moviesapp.common.Resource
import com.amalitec.moviesapp.domain.local.usecase.LocalUseCase
import com.amalitec.moviesapp.presentation.MoviesState
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.model.toMovieEntity
import com.amalitec.moviesapp.domain.remote.usecase.RemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val remoteUseCase: RemoteUseCase,
    private val localUseCase: LocalUseCase
): ViewModel() {

    private val _state = mutableStateOf(MoviesState())
    val state: State<MoviesState> = _state

    private var sinchMoviesJob = Job()

    init {
        getRemoteAndSaveLocally()
    }
    private fun getRemoteAndSaveLocally() {
        sinchMoviesJob.cancel()
        sinchMoviesJob = Job()

        viewModelScope.launch(sinchMoviesJob) {
            suspend fun getMovies(getter: () -> Flow<Resource<List<Movie>>>) {
                getter().collect { result ->

                    when (result) {

                        is Resource.Success -> {

                            _state.value = MoviesState(movies = result.data ?: emptyList())
                            state.value.movies.forEach { movie ->
                                localUseCase.saveMovie(movie.toMovieEntity())
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

            getMovies { remoteUseCase.getFeaturedMovie() }
            getMovies { remoteUseCase.getTopRated() }
            getMovies { remoteUseCase.getTvShows() }
            getMovies { remoteUseCase.getLatestMovies() }
        }
    }
}
