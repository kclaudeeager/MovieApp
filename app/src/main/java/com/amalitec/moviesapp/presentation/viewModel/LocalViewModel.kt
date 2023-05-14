package com.amalitec.moviesapp.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitec.moviesapp.data.local.toMovie
import com.amalitec.moviesapp.domain.local.usecase.LocalUseCase
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.model.toMovieEntity
import com.amalitec.moviesapp.presentation.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val localUseCase: LocalUseCase

) : ViewModel() {

    private val _state = mutableStateOf(MoviesState())
    val state: State<MoviesState> = _state

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            val movieEntity = localUseCase.getMovieById(id)
            val movie = movieEntity?.toMovie()
            if (movie != null) {
                _state.value = MoviesState(movies = listOf(movie))
            } else {
                _state.value = MoviesState(error = "Movie with id $id not found")
            }
        }
    }

    fun getMoviesByType(type: String) {
        viewModelScope.launch {
            val movieEntities = localUseCase.getMovieByType(type)
            movieEntities.collect { entityList ->
                val movies = entityList.map { it.toMovie() }
                _state.value = MoviesState(movies = movies)
            }
        }
    }


    fun getAllMovies() {
        viewModelScope.launch {
            localUseCase. getMovies().collect { movieEntities ->
                val movies = movieEntities.map { it.toMovie() }
                _state.value = MoviesState(movies = movies)
            }
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            localUseCase.deleteMovie(movie.toMovieEntity())
        }
    }

}
