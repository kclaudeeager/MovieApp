package com.amalitec.moviesapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amalitec.moviesapp.presentation.components.LoadingImageFromInternet
import com.amalitec.moviesapp.presentation.viewModel.MovieDetailViewModel

@Composable
fun MovieDetail(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        state.movie?.let { movie ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {

                        LoadingImageFromInternet(
                            url = movie.posterPath,
                            contentDescription = movie.title,
                            modifier = Modifier.fillMaxSize()
                        )
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)){
                        Button(
                            onClick = {
                                navController.navigate(Screen.MoviePlayScreen.route+"/${movie.id}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray, RoundedCornerShape(16.dp)),
                            colors= ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayCircle,
                                contentDescription = "Play",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Play",
                                color = Color.White
                            )
                        }
                    }

                }
                item {
                    Box(modifier = Modifier.padding()){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = movie.title,
                                color= Color.Black,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = movie.description,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Popularity: ${movie.popularity}",
                                style = MaterialTheme.typography.labelLarge,
                                color =  Color.DarkGray,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Release Date: ${movie.releaseDate}",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}





