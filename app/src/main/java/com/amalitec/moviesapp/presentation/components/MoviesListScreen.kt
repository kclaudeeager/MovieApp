package com.amalitec.moviesapp.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.presentation.Screen

@Composable
fun MoviesListScreen(
    featuredMovies: List<Movie?>,
    latestMovies: List<Movie?>,
    topRatedMovies: List<Movie?>,
    tvShows: List<Movie?>,
    navController: NavController
) {
    var nowPlayingMovie by mutableStateOf(featuredMovies.firstOrNull())

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxSize()
    ) {
        item {
            nowPlayingMovie?.let { movie ->
                NowPlayingView(movie = movie, onPlayClick = {}, onDetailsClick = {
                    navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                })
            }
        }
        item {
            SectionTitle(title = "Featured Movies")
            MovieList(featuredMovies) { movie ->
                nowPlayingMovie = movie
            }
        }
        item {
            SectionTitle(title = "Latest Movies")
            MovieList(latestMovies) { movie ->
                nowPlayingMovie = movie
            }
        }
        item {
            SectionTitle(title = "Top Rated Movies")
            MovieList(topRatedMovies) { movie ->
                nowPlayingMovie = movie
            }
        }
        item {
            SectionTitle(title = "TV Shows")
            MovieList(tvShows) { movie ->
                nowPlayingMovie = movie
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}


@Composable
fun MovieList(movies: List<Movie?>, onItemClick: (Movie) -> Unit) {
    LazyRow {
        items(movies) { movie ->
            if (movie != null) {
                MovieListItem(movie = movie) {
                    onItemClick(movie)
                }
            }
        }
    }
}
