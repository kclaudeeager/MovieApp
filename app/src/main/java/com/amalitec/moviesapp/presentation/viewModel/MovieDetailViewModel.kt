package com.amalitec.moviesapp.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitec.moviesapp.common.Constants
import com.amalitec.moviesapp.data.local.toMovie
import com.amalitec.moviesapp.domain.local.usecase.LocalUseCase
import com.amalitec.moviesapp.presentation.MovieDetailState
import com.amalitec.moviesapp.presentation.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _state = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState> = _state
    init {
        savedStateHandle.get<String>(Constants.PARAM_MOVIE_ID)?.let { movieId ->
            getMovieById(id = movieId)
        }
    }
    private fun getMovieById(id: String) {
        viewModelScope.launch {
            val movieEntity = localUseCase.getMovieById(Integer.parseInt(id))
            val movie = movieEntity?.toMovie()
            if (movie != null) {
                _state.value = MovieDetailState(movie = movie)
            } else {
                _state.value = MovieDetailState(error = "Movie with id $id not found")
            }
        }
    }

}