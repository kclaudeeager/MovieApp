package com.amalitec.moviesapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitec.moviesapp.common.Resource
import com.amalitec.moviesapp.data.local.toMovie
import com.amalitec.moviesapp.domain.local.usecase.LocalUseCase
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.model.toMovieEntity
import com.amalitec.moviesapp.domain.remote.usecase.RemoteUseCase
import com.amalitec.moviesapp.presentation.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
    private val remoteUseCase: RemoteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> get() = _state.asStateFlow()

    init {
        getRemoteAndSaveLocally()
        getAllMovies()
    }

    fun getMoviesByType(type: String) {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            val movieEntities = localUseCase.getMovieByType(type)
            movieEntities.collect { entityList ->
                val movies = entityList.map { it.toMovie() }
                _state.value = state.value.copy(
                    movies = movies,
                    isLoading = false
                )
            }
        }
    }


  private fun getAllMovies() {
      _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            localUseCase.getMovies().collect { movieEntities ->
                val movies = movieEntities.map { it.toMovie() }
                _state.value =state.value.copy(
                    movies = movies
                )

            }
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            localUseCase.deleteMovie(movie.toMovieEntity())
        }
    }
    private fun getRemoteAndSaveLocally() {
        viewModelScope.launch(Dispatchers.IO) {
            remoteUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            if(result.data?.isEmpty() == false){
                                _state.value =state.value.copy(movies = result.data,isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        withContext(Dispatchers.Main) {
                            _state.value =state.value.copy(error = result.message ?: "Unexpected error occurred",isLoading = false)
                        }

                    }
                    is Resource.Loading -> {
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

}
