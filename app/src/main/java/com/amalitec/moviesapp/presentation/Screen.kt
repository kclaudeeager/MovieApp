package com.amalitec.moviesapp.presentation

sealed class Screen(val route:String){
    object MoviesScreen : Screen("movies")
    object MovieDetailScreen : Screen("movie_detail_screen")

}

