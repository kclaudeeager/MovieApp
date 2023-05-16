package com.amalitec.moviesapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.amalitec.moviesapp.domain.util.MovieType
import com.amalitec.moviesapp.presentation.viewModel.LocalViewModel
import com.amalitec.moviesapp.presentation.viewModel.RemoteViewModel
import kotlinx.coroutines.launch


@Composable
fun MoviesScreen(
    remoteViewModel: RemoteViewModel = hiltViewModel(),
    localViewModel: LocalViewModel = hiltViewModel(),
    type: MovieType,
    navController: NavController
) {
    val savingState= remoteViewModel.state.value
    var state = localViewModel.state.value
    remoteViewModel.viewModelScope.launch {
        if (savingState.isLoading){
            state=remoteViewModel.state.value.copy(isLoading = true)
        }
        else if (savingState.error.isNotBlank()){
            state=remoteViewModel.state.value.copy(error = savingState.error)
        }
        else{
            localViewModel.getAllMovies()
        }
    }
    LaunchedEffect(type) {
        when (type) {
            is MovieType.Featured -> localViewModel.getMoviesByType("featured")
            is MovieType.Latest -> localViewModel.getMoviesByType("latest")
            is MovieType.TopRated -> localViewModel.getMoviesByType("top_rated")
            is MovieType.TvShow -> localViewModel.getMoviesByType("tv_show")
        }
    }

    Scaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                state.movies.takeIf { it.isNotEmpty() }?.let { movies ->
                    // Display the featured movie at the top
                    val featuredMovies =  movies.filter { it.type == MovieType.Featured }
                    val latestMovies = movies.filter { it.type == MovieType.Latest }
                    val topRatedMovies = movies.filter { it.type == MovieType.TopRated }
                    val tvShows = movies.filter { it.type == MovieType.TvShow }
                    Box(modifier = Modifier.fillMaxSize()) {
                                MoviesListScreen(
                                    latestMovies = latestMovies,
                                    topRatedMovies = topRatedMovies,
                                    tvShows = tvShows,
                                    featuredMovies = featuredMovies,
                                    navController = navController
                                )
                            }
                }
                if (state.isLoading){
                    run {
                        showLoading()
                    }
                }
                if(state.error.isNotBlank()) {
                    run {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.error, color = Color.Red)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun showLoading(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
