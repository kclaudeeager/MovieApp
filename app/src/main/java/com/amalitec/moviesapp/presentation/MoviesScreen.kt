package com.amalitec.moviesapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.amalitec.moviesapp.domain.util.MovieType
import com.amalitec.moviesapp.presentation.components.MoviesListScreen
import com.amalitec.moviesapp.presentation.viewModel.LocalViewModel
import com.amalitec.moviesapp.presentation.viewModel.RemoteViewModel
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MoviesScreen(
    remoteViewModel: RemoteViewModel = hiltViewModel(),
    localViewModel: LocalViewModel = hiltViewModel(),
    navController: NavController
) {
    val savingState = remoteViewModel.state.value
    var state = localViewModel.state.value
    val showError = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    remoteViewModel.viewModelScope.launch {
        if (savingState.isLoading) {
             state= MoviesState(
                 isLoading = true,
                 error = savingState.error,
                 movies = localViewModel.state.value.movies
             )
        }
       else if (savingState.error.isNotBlank()) {
           showError.value = true
           state= MoviesState(
               isLoading = localViewModel.state.value.isLoading,
               error = savingState.error,
               movies = localViewModel.state.value.movies
           )
       }
        else{
            state= MoviesState(
                movies = localViewModel.state.value.movies
            )
        }


    }

    LaunchedEffect(navController.currentBackStackEntry?.arguments?.getString(Screen.MoviesScreen.route)) {
        showError.value = false
        snackBarHostState.currentSnackbarData?.dismiss()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                state.movies.takeIf { it.isNotEmpty() }?.let { movies ->
                    val featuredMovies = movies.filter { it.type == MovieType.Featured }
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

                if (state.isLoading) {
                    ShowLoading()
                }

                if (showError.value ) {
                    LaunchedEffect(snackBarHostState) {
                        val result = snackBarHostState.showSnackbar(
                            message = "Error fetching new movies",
                            actionLabel = "Close"
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            state = remoteViewModel.state.value.copy(
                                isLoading = false,
                                movies = localViewModel.state.value.movies,
                                error = ""
                            )
                            showError.value = false
                        }
                    }
                }

            }
        }
    )
}

@Composable
fun ShowLoading(
    modifier: Modifier = Modifier,
    loadingText: String = "Loading...",
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp,
    trackColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
    strokeCap: StrokeCap = StrokeCap.Round
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = modifier,
                color = color,
                strokeWidth = strokeWidth,
                trackColor = trackColor,
                strokeCap = strokeCap
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = loadingText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

