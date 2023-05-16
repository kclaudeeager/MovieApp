package com.amalitec.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amalitec.moviesapp.domain.util.MovieType
import com.amalitec.moviesapp.presentation.Screen
import com.amalitec.moviesapp.presentation.components.MovieDetail
import com.amalitec.moviesapp.presentation.components.MoviesScreen
import com.amalitec.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MoviesScreen.route
                    ) {
                        composable(
                            route = Screen.MoviesScreen.route
                        ) {
                            MoviesScreen(
                                type = MovieType.Featured,
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.MovieDetailScreen.route + "/{movieId}"
                        ) {
                            MovieDetail()
                        }
                    }
                    Column {

                    }
                }
            }
        }
    }
}


